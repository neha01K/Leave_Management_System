<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leave Management System</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>
<div class="container">

    <div class="header">
        <h1 style="font-family: 'Helvetica', monospace;">Leave Management</h1>
        <p style="font-family: 'Helvetica', monospace;">Employee Portal</p>
    </div>

    <div id="employeeLoginForm" class="form-container">
        <h2>Employee Login</h2>
        <h5 id="message" class="center-align"></h5>
        <form id="loginFormElement" onsubmit="return false;">
            <div class="form-group" style="text-align: left;">
                <label for="employeeID">Employee ID:</label>
                <input type="text" id="employeeID" name="employeeID" placeholder="Enter Employee ID (e.g EMP001)" required>
            </div>

            <div class="form-group" style="text-align: left;">
                <label for="employeeLoginPassword">Password:</label>
                <input type="password" id="employeeLoginPassword" name="employeeLoginPassword" placeholder="Enter password" required>
            </div>

            <button type="button" class="button button-primary" onclick="loginEmployee()">Login</button>

            <div class="form-links">
                <a href="#" onclick="showRegistrationForm()">New Employee? Register here</a>
            </div>
        </form>

        <div class="loader center-align" style="margin-top:10px; display:none">
            <div class="preloader-wrapper big active">
                <div class="spinner-layer spinner-blue">
                    <div class="circle-clipper left"><div class="circle"></div></div>
                    <div class="gap-patch"><div class="circle"></div></div>
                    <div class="circle-clipper right"><div class="circle"></div></div>
                </div>
            </div>
            <h5>Please wait...</h5>
        </div>

        <div id="loginError" class="error-message hidden"></div>
    </div>

    <div id="registrationForm" class="form-container hidden">
        <h3 style="margin-bottom: 20px; color: #333; text-align: center;">Register New Employee</h3>
        <form id="registrationFormElement" onsubmit="return false;">
            <div class="form-group">
                <label for="registerEmployeeName">Full Name</label>
                <input type="text" id="registerEmployeeName" name="registerEmployeeName" placeholder="Enter your Full Name" required>
            </div>

            <div class="form-group">
                <label for="registerDesignation">Designation</label>
                <input type="text" id="registerDesignation" name="registerDesignation" placeholder="Enter your Designation" required>
            </div>

            <div class="form-group">
                <label for="registerEmail">Email</label>
                <input type="email" id="registerEmail" name="registerEmail" placeholder="Enter your Email" required>
            </div>

            <div class="form-group">
                <label for="registerJoiningDate">Joining Date</label>
                <input type="date" id="registerJoiningDate" name="registerJoiningDate" required>
            </div>

            <button type="button" class="button" onclick="registerEmployeeDetails()">Register</button>
            <button type="button" class="button button-secondary" onclick="showLoginForm()">Back to Login</button>
        </form>

        <div id="registerError" class="error-message hidden"></div>
        <div id="registerSuccess" class="success-message hidden"></div>
    </div>

    <div id="leavesDashboard" class="leavesDashboard-container hidden">
        <div class="header">
            <h1>Welcome, <span id="welcomeName"></span>!</h1>
            <div class="user-information">
                <span>ID: <span id="welcomeID"></span></span>
            </div>
        </div>

        <div class="leavesDashboard-grid">
            <div class="leavesDashboard-card" onclick="showApplyForLeave()"><h3>Apply for Leave</h3></div>
            <div class="leavesDashboard-card" onclick="showLeaveBalance()"><h3>Leave Balance</h3></div>
            <div class="leavesDashboard-card" onclick="showTakenLeaves()"><h3>Leave History</h3></div>
            <div class="leavesDashboard-card" onclick="showChangePassword()"><h3>Change Password</h3></div>
            <div><button onclick="logout()" class="button button-secondary">Logout</button></div>
        </div>
    </div>

    <div id="applyLeaveSection" class="form-container hidden">
        <div class="leave-form">
            <h3 style="margin-bottom: 20px; color: #333;">Apply for Leave</h3>
            <div class="form-group">
                <label for="leaveType">Leave Type</label>
                <select id="leaveType" required>
                    <option value="">Select Leave Type</option>
                    <option value="Sick Leave">Sick Leave</option>
                    <option value="Casual Leave">Casual Leave</option>
                    <option value="Annual Leave">Annual Leave</option>
                    <option value="Maternity/Paternity Leave">Maternity/Paternity Leave</option>
                </select>
            </div>

            <div class="form-group"><label for="leaveStartDate">Start Date</label><input type="date" id="leaveStartDate" required></div>
            <div class="form-group"><label for="leaveEndDate">End Date</label><input type="date" id="leaveEndDate" required></div>
            <div class="form-group"><label for="leaveReason">Reason</label><textarea id="leaveReason" placeholder="Enter reason for leave" required></textarea></div>

            <button class="button" onclick="submitLeaveApplication()">Submit Application</button>
            <button onclick="backToDashboard()" class="button back-button">Back to Dashboard</button>

            <div id="leaveApplicationError" class="error-message hidden"></div>
            <div id="leaveApplicationSuccess" class="success-message hidden"></div>
        </div>
    </div>

    <div id="leaveBalanceSection" class="form-container hidden">
        <div class="leave-information">
            <h3>Leave Balance</h3>
            <div class="leave-balance">
                <div class="balance-card"><div class="balance-number" id="sickLeaveBalance">12</div><div class="balance-label">Sick Leave</div></div>
                <div class="balance-card"><div class="balance-number" id="casualLeaveBalance">15</div><div class="balance-label">Casual Leave</div></div>
                <div class="balance-card"><div class="balance-number" id="annualLeaveBalance">18</div><div class="balance-label">Annual Leave</div></div>
            </div>
        </div>
        <button onclick="backToDashboard()" class="button back-button">Back to Dashboard</button>
    </div>

    <div id="viewHistorySection" class="form-container hidden">
        <div class="leave-information">
            <h3>Leave History</h3>
            <div id="leaveHistory"></div>
            <button onclick="backToDashboard()" class="button back-button">Back to Dashboard</button>
        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
