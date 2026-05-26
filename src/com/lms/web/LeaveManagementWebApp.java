package com.lms.web;

import com.lms.dao.EmployeeDAO;
import com.lms.dao.EmployeeDAOInterface;
import com.lms.dao.LeaveRequestDAO;
import com.lms.exceptions.EmployeeNotFound;
import com.lms.exceptions.InvalidDateRange;
import com.lms.exceptions.InvalidLeaveRequest;
import com.lms.models.Employee;
import com.lms.models.LeaveRequest;
import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveType;
import com.lms.services.EmployeeService;
import com.lms.services.LeaveService;
import com.lms.services.ValidationService;
import com.lms.utils.PasswordUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LeaveManagementWebApp {
    private static final int PORT = 8080;
    private static final Map<String, String> sessions = new ConcurrentHashMap<>();

    private final EmployeeDAOInterface employeeDAO = new EmployeeDAO();
    private final EmployeeService employeeService = new EmployeeService();
    private final LeaveService leaveService = new LeaveService(employeeDAO, new LeaveRequestDAO());
    private final ValidationService validationService = new ValidationService();

    public static void main(String[] args) throws IOException {
        LeaveManagementWebApp app = new LeaveManagementWebApp();
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", app::route);
        server.setExecutor(null);
        server.start();
        System.out.println("Leave Management UI running at http://localhost:" + PORT);
    }

    private void route(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("GET".equals(method) && "/".equals(path)) {
                renderHome(exchange, message(exchange));
            } else if ("POST".equals(method) && "/login".equals(path)) {
                login(exchange);
            } else if ("POST".equals(method) && "/logout".equals(path)) {
                logout(exchange);
            } else if ("POST".equals(method) && "/register".equals(path)) {
                register(exchange);
            } else if ("POST".equals(method) && "/request-leave".equals(path)) {
                requestLeave(exchange);
            } else if ("POST".equals(method) && "/process-leave".equals(path)) {
                processLeave(exchange);
            } else {
                send(exchange, 404, layout("Not found", "<main><h1>Not found</h1></main>"));
            }
        } catch (Exception exception) {
            send(exchange, 500, layout("Error", "<main><h1>Something went wrong</h1><p>" + escape(exception.getMessage()) + "</p></main>"));
        }
    }

    private void renderHome(HttpExchange exchange, String message) throws IOException {
        Employee employee = currentEmployee(exchange);
        if (employee == null) {
            send(exchange, 200, layout("Leave Management", authPage(message)));
            return;
        }
        try {
            send(exchange, 200, layout("Leave Management", dashboard(employee, message)));
        } catch (EmployeeNotFound exception) {
            send(exchange, 200, layout("Leave Management", authPage(exception.getMessage())));
        }
    }

    private String authPage(String message) {
        return """
                <main class="auth-shell">
                  %s
                  <section class="panel">
                    <h1>Leave Management</h1>
                    <form method="post" action="/login" class="stack">
                      <label>Employee ID<input name="employeeID" required></label>
                      <label>Password<input name="password" type="password" required></label>
                      <button type="submit">Login</button>
                    </form>
                  </section>
                  <section class="panel">
                    <h2>Register</h2>
                    <form method="post" action="/register" class="stack">
                      <label>Name<input name="name" required></label>
                      <label>Email<input name="email" type="email" required></label>
                      <label>Password<input name="password" type="password" required></label>
                      <label>Type<select name="type">
                        <option>EXECUTIVE</option>
                        <option>LEAD</option>
                        <option>MANAGER</option>
                      </select></label>
                      <label>Joining Date<input name="joiningDate" type="date" required></label>
                      <button type="submit">Create Employee</button>
                    </form>
                  </section>
                </main>
                """.formatted(flash(message));
    }

    private String dashboard(Employee employee, String message) throws EmployeeNotFound {
        StringBuilder html = new StringBuilder();
        html.append("<main class=\"app-shell\">")
                .append(flash(message))
                .append("<header class=\"topbar\"><div><h1>")
                .append(escape(employee.getEmployeeName()))
                .append("</h1><p>")
                .append(escape(employee.getEmployeeID()))
                .append(" / ")
                .append(employee.getEmployeeType())
                .append("</p></div><form method=\"post\" action=\"/logout\"><button>Logout</button></form></header>");

        html.append("<section class=\"grid\">")
                .append("<div class=\"panel\"><h2>Leave Balance</h2>")
                .append(balanceTable(employee.getEmployeeID()))
                .append("</div>")
                .append("<div class=\"panel\"><h2>Request Leave</h2>")
                .append(requestForm())
                .append("</div>")
                .append("</section>");

        html.append("<section class=\"panel\"><h2>Leave History</h2>")
                .append(historyTable(employee.getEmployeeID()))
                .append("</section>");

        if (employee.getEmployeeType() == EmployeeType.LEAD || employee.getEmployeeType() == EmployeeType.MANAGER) {
            html.append("<section class=\"panel\"><h2>Pending Approvals</h2>")
                    .append(pendingTable(employee.getEmployeeID()))
                    .append("</section>");
        }

        if (employee.getEmployeeType() == EmployeeType.MANAGER) {
            html.append("<section class=\"panel\"><h2>Employees</h2>")
                    .append(employeeTable())
                    .append("</section>");
        }

        html.append("</main>");
        return html.toString();
    }

    private String balanceTable(String employeeID) throws EmployeeNotFound {
        StringBuilder rows = new StringBuilder();
        for (Map.Entry<LeaveType, Integer> entry : leaveService.getCurrentLeaveBalance(employeeID).entrySet()) {
            rows.append("<tr><td>").append(entry.getKey()).append("</td><td>").append(entry.getValue()).append("</td></tr>");
        }
        return "<table><thead><tr><th>Type</th><th>Available</th></tr></thead><tbody>" + rows + "</tbody></table>";
    }

    private String requestForm() {
        StringBuilder leaveOptions = new StringBuilder();
        for (LeaveType type : LeaveType.values()) {
            leaveOptions.append("<option>").append(type).append("</option>");
        }
        return """
                <form method="post" action="/request-leave" class="stack compact">
                  <label>Leave Type<select name="leaveType">%s</select></label>
                  <label>Start Date<input name="startDate" type="date" required></label>
                  <label>End Date<input name="endDate" type="date" required></label>
                  <label>Reason<textarea name="reason" rows="3"></textarea></label>
                  <label>Medical Certificate<input name="medicalCertificate"></label>
                  <label>Parenthood Certificate<input name="parenthoodCertificate"></label>
                  <button type="submit">Submit Request</button>
                </form>
                """.formatted(leaveOptions);
    }

    private String historyTable(String employeeID) {
        List<LeaveRequest> history = leaveService.getLeaveHistoryForEmployee(employeeID);
        if (history.isEmpty()) {
            return "<p>No leave history.</p>";
        }
        StringBuilder rows = new StringBuilder();
        for (LeaveRequest request : history) {
            rows.append("<tr><td>").append(escape(request.getLeaveRequestID())).append("</td><td>")
                    .append(request.getLeaveType()).append("</td><td>")
                    .append(request.getLeaveStartDate()).append("</td><td>")
                    .append(request.getLeaveEndDate()).append("</td><td>")
                    .append(request.getNumberOfDaysOfLeave()).append("</td><td>")
                    .append(request.getLeaveStatus()).append("</td></tr>");
        }
        return "<table><thead><tr><th>ID</th><th>Type</th><th>Start</th><th>End</th><th>Days</th><th>Status</th></tr></thead><tbody>" + rows + "</tbody></table>";
    }

    private String pendingTable(String approverID) throws EmployeeNotFound {
        List<LeaveRequest> pending = leaveService.getPendingRequestsForApprover(approverID);
        if (pending.isEmpty()) {
            return "<p>No pending requests.</p>";
        }
        StringBuilder rows = new StringBuilder();
        for (LeaveRequest request : pending) {
            rows.append("<tr><td>").append(escape(request.getLeaveRequestID())).append("</td><td>")
                    .append(escape(request.getEmployeeID())).append("</td><td>")
                    .append(request.getLeaveType()).append("</td><td>")
                    .append(request.getLeaveStartDate()).append("</td><td>")
                    .append(request.getLeaveEndDate()).append("</td><td><form method=\"post\" action=\"/process-leave\" class=\"actions\">")
                    .append("<input type=\"hidden\" name=\"requestID\" value=\"").append(escape(request.getLeaveRequestID())).append("\">")
                    .append("<button name=\"action\" value=\"approve\">Approve</button>")
                    .append("<button name=\"action\" value=\"reject\">Reject</button>")
                    .append("</form></td></tr>");
        }
        return "<table><thead><tr><th>ID</th><th>Employee</th><th>Type</th><th>Start</th><th>End</th><th>Action</th></tr></thead><tbody>" + rows + "</tbody></table>";
    }

    private String employeeTable() {
        List<Employee> employees = employeeDAO.getAllEmployeesDetails();
        StringBuilder rows = new StringBuilder();
        for (Employee employee : employees) {
            rows.append("<tr><td>").append(escape(employee.getEmployeeID())).append("</td><td>")
                    .append(escape(employee.getEmployeeName())).append("</td><td>")
                    .append(escape(employee.getEmployeeEmail())).append("</td><td>")
                    .append(employee.getEmployeeType()).append("</td><td>")
                    .append(escape(employee.getManagerID())).append("</td></tr>");
        }
        return "<table><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Type</th><th>Manager</th></tr></thead><tbody>" + rows + "</tbody></table>";
    }

    private void login(HttpExchange exchange) throws IOException {
        Map<String, String> form = form(exchange);
        Employee employee = employeeDAO.getEmployeeDetailByEmployeeID(form.get("employeeID"));
        if (employee == null || !PasswordUtil.matches(form.get("password"), employee.getPasswordHash())) {
            redirect(exchange, "/?message=Invalid%20login");
            return;
        }
        String sessionID = UUID.randomUUID().toString();
        sessions.put(sessionID, employee.getEmployeeID());
        exchange.getResponseHeaders().add("Set-Cookie", "LMS_SESSION=" + sessionID + "; Path=/; HttpOnly");
        redirect(exchange, "/?message=Welcome");
    }

    private void logout(HttpExchange exchange) throws IOException {
        String sessionID = sessionID(exchange);
        if (sessionID != null) {
            sessions.remove(sessionID);
        }
        exchange.getResponseHeaders().add("Set-Cookie", "LMS_SESSION=; Path=/; Max-Age=0");
        redirect(exchange, "/?message=Logged%20out");
    }

    private void register(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> form = form(exchange);
            EmployeeType type = EmployeeType.valueOf(form.get("type"));
            Employee employee = employeeService.createEmployee(
                    form.get("name"),
                    form.get("email"),
                    type,
                    LocalDate.parse(form.get("joiningDate")),
                    form.get("password")
            );
            assignManager(employee);
            employeeDAO.saveEmployee(employee);
            redirect(exchange, "/?message=Created%20" + employee.getEmployeeID());
        } catch (IllegalArgumentException | DateTimeParseException exception) {
            redirect(exchange, "/?message=Invalid%20registration");
        }
    }

    private void requestLeave(HttpExchange exchange) throws IOException {
        Employee employee = currentEmployee(exchange);
        if (employee == null) {
            redirect(exchange, "/?message=Login%20required");
            return;
        }
        try {
            Map<String, String> form = form(exchange);
            LeaveType leaveType = LeaveType.valueOf(form.get("leaveType"));
            LocalDate startDate = LocalDate.parse(form.get("startDate"));
            LocalDate endDate = LocalDate.parse(form.get("endDate"));

            if (!validationService.validateLeaveTypeForEmployee(employee, leaveType)) {
                redirect(exchange, "/?message=Leave%20type%20not%20available");
                return;
            }
            validationService.validateLeaveRequest(employee, leaveType, startDate, endDate);

            LeaveRequest request = new LeaveRequest(employee.getEmployeeID(), leaveType, startDate, endDate, form.get("reason"));
            request.setMedicalCertificate(form.get("medicalCertificate"));
            request.setParenthoodCertificate(form.get("parenthoodCertificate"));
            leaveService.submitLeaveRequest(request);
            redirect(exchange, "/?message=Request%20submitted");
        } catch (InvalidLeaveRequest | InvalidDateRange | IllegalArgumentException exception) {
            redirect(exchange, "/?message=" + encode(exception.getMessage()));
        }
    }

    private void processLeave(HttpExchange exchange) throws IOException {
        Employee approver = currentEmployee(exchange);
        if (approver == null) {
            redirect(exchange, "/?message=Login%20required");
            return;
        }
        try {
            Map<String, String> form = form(exchange);
            String requestID = form.get("requestID");
            LeaveRequest selected = null;
            for (LeaveRequest request : leaveService.getPendingRequestsForApprover(approver.getEmployeeID())) {
                if (request.getLeaveRequestID().equals(requestID)) {
                    selected = request;
                    break;
                }
            }
            if (selected == null) {
                redirect(exchange, "/?message=Request%20not%20found");
                return;
            }
            if ("approve".equals(form.get("action"))) {
                leaveService.approveLeave(selected, approver.getEmployeeID());
                redirect(exchange, "/?message=Request%20approved");
            } else {
                leaveService.rejectLeave(selected, approver.getEmployeeID());
                redirect(exchange, "/?message=Request%20rejected");
            }
        } catch (EmployeeNotFound exception) {
            redirect(exchange, "/?message=" + encode(exception.getMessage()));
        }
    }

    private Employee currentEmployee(HttpExchange exchange) {
        String sessionID = sessionID(exchange);
        if (sessionID == null) {
            return null;
        }
        String employeeID = sessions.get(sessionID);
        if (employeeID == null) {
            return null;
        }
        return employeeDAO.getEmployeeDetailByEmployeeID(employeeID);
    }

    private void assignManager(Employee employee) {
        if (employee.getEmployeeType() == EmployeeType.EXECUTIVE) {
            employee.setManagerID(employeeDAO.findFirstEmployeeIDByType(EmployeeType.LEAD));
        } else if (employee.getEmployeeType() == EmployeeType.LEAD) {
            employee.setManagerID(employeeDAO.findFirstEmployeeIDByType(EmployeeType.MANAGER));
        }
    }

    private Map<String, String> form(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> values = new ConcurrentHashMap<>();
        if (body.isEmpty()) {
            return values;
        }
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = decode(parts[0]);
            String value = parts.length > 1 ? decode(parts[1]) : "";
            values.put(key, value);
        }
        return values;
    }

    private String sessionID(HttpExchange exchange) {
        List<String> cookies = exchange.getRequestHeaders().get("Cookie");
        if (cookies == null) {
            return null;
        }
        for (String cookieHeader : cookies) {
            for (String cookie : cookieHeader.split(";")) {
                String[] parts = cookie.trim().split("=", 2);
                if (parts.length == 2 && "LMS_SESSION".equals(parts[0])) {
                    return parts[1];
                }
            }
        }
        return null;
    }

    private String message(HttpExchange exchange) {
        String query = exchange.getRequestURI().getRawQuery();
        if (query == null) {
            return "";
        }
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2 && "message".equals(decode(parts[0]))) {
                return decode(parts[1]);
            }
        }
        return "";
    }

    private void redirect(HttpExchange exchange, String location) throws IOException {
        exchange.getResponseHeaders().add("Location", location);
        exchange.sendResponseHeaders(303, -1);
        exchange.close();
    }

    private void send(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(bytes);
        }
    }

    private String layout(String title, String body) {
        return """
                <!doctype html>
                <html lang="en">
                <head>
                  <meta charset="utf-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>%s</title>
                  <style>
                    :root { color-scheme: light; --ink:#1f2933; --muted:#52606d; --line:#d9e2ec; --panel:#ffffff; --bg:#f5f7fa; --accent:#0f766e; --accent-dark:#115e59; --danger:#b42318; }
                    * { box-sizing: border-box; }
                    body { margin:0; font-family: Arial, sans-serif; color:var(--ink); background:var(--bg); }
                    h1, h2 { margin:0 0 12px; letter-spacing:0; }
                    p { color:var(--muted); }
                    label { display:grid; gap:6px; font-size:14px; color:var(--muted); }
                    input, select, textarea { width:100%%; border:1px solid var(--line); border-radius:6px; padding:10px 12px; font:inherit; background:#fff; color:var(--ink); }
                    button { border:0; border-radius:6px; padding:10px 14px; background:var(--accent); color:white; font-weight:700; cursor:pointer; }
                    button:hover { background:var(--accent-dark); }
                    table { width:100%%; border-collapse:collapse; font-size:14px; }
                    th, td { text-align:left; border-bottom:1px solid var(--line); padding:10px; vertical-align:top; }
                    th { color:var(--muted); font-weight:700; }
                    .auth-shell { min-height:100vh; display:grid; grid-template-columns: minmax(280px, 420px) minmax(280px, 420px); gap:24px; align-content:center; justify-content:center; padding:32px; }
                    .app-shell { max-width:1180px; margin:0 auto; padding:24px; }
                    .panel { background:var(--panel); border:1px solid var(--line); border-radius:8px; padding:20px; box-shadow:0 1px 2px rgba(31,41,51,.05); }
                    .stack { display:grid; gap:14px; }
                    .compact { gap:10px; }
                    .topbar { display:flex; justify-content:space-between; align-items:center; gap:16px; margin-bottom:20px; }
                    .topbar p { margin:0; }
                    .grid { display:grid; grid-template-columns: minmax(280px, 1fr) minmax(320px, 1fr); gap:20px; margin-bottom:20px; }
                    .app-shell > .panel { margin-bottom:20px; }
                    .flash { max-width:1180px; margin:0 auto 16px; border-left:4px solid var(--accent); background:#ecfdf5; padding:12px 14px; border-radius:6px; color:var(--accent-dark); }
                    .actions { display:flex; gap:8px; flex-wrap:wrap; }
                    .actions button:last-child { background:var(--danger); }
                    @media (max-width: 760px) { .auth-shell, .grid { grid-template-columns:1fr; } .topbar { align-items:flex-start; flex-direction:column; } }
                  </style>
                </head>
                <body>%s</body>
                </html>
                """.formatted(escape(title), body);
    }

    private String flash(String message) {
        if (message == null || message.isBlank()) {
            return "";
        }
        return "<div class=\"flash\">" + escape(message) + "</div>";
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private String encode(String value) {
        return value == null ? "" : value.replace(" ", "%20");
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
