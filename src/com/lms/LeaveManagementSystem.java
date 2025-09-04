package com.lms;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LeaveManagementSystem {

    Scanner scanner;
    EmployeeService employeeService;
    LeaveService leaveService;
    ValidationService validateService;

    public LeaveManagementSystem(){
        scanner = new Scanner(System.in);
        employeeService = new EmployeeService();
        leaveService = new LeaveService(employeeService);
        validateService = new ValidationService();
    }

    public void showMainMenu() throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange{
        while(true){
            System.out.println("------Leave Management System------");
            System.out.println("1. Register Employee");
            System.out.println("2. Login Employee");
            System.out.println("3. Show Employees");
            System.out.println("4. Exit");
            System.out.print("Enter option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    registerEmployee();
                    break;
                case 2:
                    employeeLogin();
                    break;
                case 3:
                    showEmployees();
                    break;
                case 4:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    void registerEmployee(){
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println("Type: 1.Executive 2.Lead 3.Manager");
        int type = scanner.nextInt();
        scanner.nextLine();

        EmployeeType employeeType;
        switch(type){
            case 1: employeeType = EmployeeType.EXECUTIVE; break;
            case 2: employeeType = EmployeeType.LEAD; break;
            case 3: employeeType = EmployeeType.MANAGER; break;
            default: employeeType = EmployeeType.EXECUTIVE;
                System.out.println("Invalid choice so default Executive");
        }

        System.out.print("Joining date (YYYY-MM-DD): ");
        LocalDate joiningDate = LocalDate.parse(scanner.nextLine());

        Employee employee = employeeService.createEmployee(name,email,employeeType,joiningDate);

        com.lms.utils.EmployeePropertiesUtil.saveEmployee(employee);

        System.out.println("Registered! ID: " + employee.getEmployeeId());
    }

    void employeeLogin() throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange{
        System.out.print("Enter Employee id: ");
        String id = scanner.nextLine();
        Employee employee = employeeService.getEmployee(id);
        if(employee==null){
            throw new EmployeeNotFound("No Employee present with this ID");
        }
        System.out.println("Welcome! " + employee.getName());
        employeeMenu(id);
    }

    void employeeMenu(String id) throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange {
        Employee employee = employeeService.getEmployee(id);
        while(true){
            System.out.println("-----Employee Menu-----");
            System.out.println("1. Request Leave");
            System.out.println("2. Leave Balance");
            System.out.println("3. Leave History");
            if(employee.getType()==EmployeeType.LEAD || employee.getType()==EmployeeType.MANAGER){
                System.out.println("4. Process Leaves");
            }
            if(employee.getType()==EmployeeType.MANAGER){
                System.out.println("5. Show Employees");
            }
            System.out.println("0. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    askLeave(id);
                    break;
                case 2:
                    leaveBalance(id);
                    break;
                case 3:
                    leaveHistory(id);
                    break;
                case 4:
                    if(employee.getType()==EmployeeType.LEAD || employee.getType()==EmployeeType.MANAGER){
                        processLeaves(id);
                    } else {
                        System.out.println("Invalid");
                    }
                    break;
                case 5:
                    if(employee.getType()==EmployeeType.MANAGER){
                        showEmployees();
                    } else {
                        System.out.println("Invalid");
                    }
                    break;
                case 0:
                    System.out.println("Logout done");
                    return;
                default:
                    System.out.println("Invalid");
            }
        }
    }

    void askLeave(String id) throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange {
        Employee employee = employeeService.getEmployee(id);
        if(employee==null){
            System.out.println("Not Found!!");
            return;
        }
        System.out.println("Leave Types:");
        LeaveType[] arr = LeaveType.values();
        for(int i=0;i<arr.length;i++){
            System.out.println((i+1)+". "+arr[i]);
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        if(choice<1 || choice>arr.length){
            System.out.println("Invalid");
            return;
        }
        LeaveType leaveType = arr[choice-1];
        if(!validateService.validateLeaveTypeForEmployee(employee, leaveType)){
            return;
        }
        System.out.print("Start Date: ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.print("End Date: ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Reason: ");
        String reason = scanner.nextLine();

        if(!validateService.validateLeaveRequest(employee,leaveType,startDate,endDate)){
            return;
        }
        LeaveRequest request = new LeaveRequest(id, leaveType, startDate, endDate, reason);
        if(leaveType==LeaveType.SICK_LEAVE || leaveType==LeaveType.MATERNITY_LEAVE){
            System.out.print("Medical Certificate: ");
            request.setMedicalCertificate(scanner.nextLine());
        }
        if(leaveType==LeaveType.PARENTAL_LEAVE){
            System.out.print("Parent Certificate: ");
            request.setParenthoodCertificate(scanner.nextLine());
        }
        leaveService.submitLeaveRequest(request);
        System.out.println("Submitted Request ID: "+request.getRequestId());
    }

    void processLeaves(String approverId) throws EmployeeNotFound{
        List<LeaveRequest> pendingRequestsForApprover = leaveService.getPendingRequestsForApprover(approverId);
        if(pendingRequestsForApprover.isEmpty()){
            System.out.println("None");
            return;
        }
        for(int i=0;i<pendingRequestsForApprover.size();i++){
            System.out.println((i+1)+". "+pendingRequestsForApprover.get(i));
        }
        System.out.print("Pick no: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if(choice<1 || choice>pendingRequestsForApprover.size()){
            System.out.println("Invalid");
            return;
        }
        LeaveRequest request = pendingRequestsForApprover.get(choice-1);
        System.out.println("1. Approve 2. Reject");
        int action = scanner.nextInt();
        scanner.nextLine();
        switch(action){
            case 1:
                leaveService.approveLeave(request, approverId);
                System.out.println("Approved");
                break;
            case 2:
                leaveService.rejectLeave(request, approverId);
                System.out.println("Rejected");
                break;
            default:
                System.out.println("Invalid");
        }
    }

    void leaveBalance(String empId) throws EmployeeNotFound{
        Employee employee = employeeService.getEmployee(empId);
        if(employee==null){
            System.out.println("Not found!");
            return;
        }
        for(Map.Entry<LeaveType,Integer> employeeLeaveBalance : employee.getLeaveBalance().entrySet()){
            System.out.println(employeeLeaveBalance.getKey()+" = "+employeeLeaveBalance.getValue());
        }
    }

    void leaveHistory(String employeeId) throws EmployeeNotFound{
        Employee employee = employeeService.getEmployee(employeeId);
        if(employee==null){
            System.out.println("Not found!");
            return;
        }
        List<LeaveRequest> history = leaveService.getLeaveHistoryForEmployee(employeeId);
        if(history.isEmpty()){
            System.out.println("No history!");
        } else {
            for(LeaveRequest leaveRequest : history){
                System.out.println(leaveRequest);
            }
        }
    }

    void showEmployees(){
        List<Employee> employeeList = com.lms.utils.EmployeePropertiesUtil.loadEmployees();

        if (employeeList.isEmpty()) {
            System.out.println("No employees found!");
        } else {
            for (Employee employee : employeeList) {
                System.out.println(employee);
            }
        }
    }

    public static void main(String[] args) throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange{
        LeaveManagementSystem obj = new LeaveManagementSystem();
        obj.showMainMenu();
    }
}
