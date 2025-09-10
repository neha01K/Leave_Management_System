package com.lms;

import com.lms.dao.EmployeeDAOInterface;
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
import com.lms.dao.EmployeeDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LeaveManagementSystem {

    private Scanner scanner;
    private EmployeeService employeeService;
    private LeaveService leaveService;
    private ValidationService validateService;
    private EmployeeDAOInterface employeeDAO;
    private Employee employee;


    public LeaveManagementSystem(){
        scanner = new Scanner(System.in);
        employeeService = new EmployeeService();
        leaveService = new LeaveService(employeeService);
        validateService = new ValidationService();
        employeeDAO  =new EmployeeDAO();
        employee = null;
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
                    System.out.println("Invalid Selection");
            }
        }
    }

    void registerEmployee(){

        System.out.print("Name: ");
        String employeeName = scanner.nextLine();
        System.out.print("Email: ");
        String employeeEmail = scanner.nextLine();
        System.out.println("Type: 1.Executive 2.Lead 3.Manager");
        int employeeTypeChoice = scanner.nextInt();
        scanner.nextLine();

        EmployeeType employeeType;

        switch(employeeTypeChoice){
            case 1: employeeType = EmployeeType.EXECUTIVE; break;
            case 2: employeeType = EmployeeType.LEAD; break;
            case 3: employeeType = EmployeeType.MANAGER; break;
            default: employeeType = EmployeeType.EXECUTIVE;
                System.out.println("Invalid choice so default Executive");
        }

        System.out.print("Joining date (YYYY-MM-DD): ");
        LocalDate employeeJoiningDate = LocalDate.parse(scanner.nextLine());

        Employee employee = employeeService.createEmployee(employeeName,employeeEmail,employeeType,employeeJoiningDate);

        com.lms.utils.EmployeePropertiesUtil.saveEmployee(employee);
        employeeDAO.saveEmployee(employee);


        System.out.println("Registered! \nYour Employee ID: " + employee.getEmployeeID());
    }

    void employeeLogin() throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange{

        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();

        //Employee employee = employeeService.getEmployee(employeeID);

        try{
            employee = employeeDAO.getEmployeeDetailByEmployeeID(employeeID);
            if(employee==null) {
                throw new EmployeeNotFound("Employee with ID: "+employeeID+ " not found");
            }
        }catch(EmployeeNotFound exception){
            System.out.println(exception.getMessage());
            return;
        }

        System.out.println("Welcome! " + employee.getEmployeeName());
        employeeMenu(employeeID);
    }

    void employeeMenu(String employeeID) throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange {
        //Employee employee = employeeService.getEmployee(employeeID);

        Employee employee = employeeDAO.getEmployeeDetailByEmployeeID(employeeID);

        while(true){
            System.out.println("-----Employee Menu-----");
            System.out.println("1. Request Leave");
            System.out.println("2. Leave Balance");
            System.out.println("3. Leave History");

            if(employee.getEmployeeType()==EmployeeType.LEAD || employee.getEmployeeType()==EmployeeType.MANAGER){
                System.out.println("4. Check Leaves to Process");
            }

            if(employee.getEmployeeType()==EmployeeType.MANAGER){
                System.out.println("5. Show Employees");
            }
            System.out.println("0. Logout");

            System.out.print("Enter option here: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    requestForLeave(employeeID);
                    break;
                case 2:
                    checkLeaveBalance(employeeID);
                    break;
                case 3:
                    checkLeaveHistory(employeeID);
                    break;
                case 4:
                    if(employee.getEmployeeType()==EmployeeType.LEAD || employee.getEmployeeType()==EmployeeType.MANAGER){
                        checkLeavesToProcess(employeeID);
                    } else {
                        System.out.println("You cannot access employees details");
                    }
                    break;
                case 5:
                    if(employee.getEmployeeType()==EmployeeType.MANAGER){
                        showEmployees();
                    } else {
                        System.out.println("You cannot access employees details");
                    }
                    break;
                case 0:
                    System.out.println("Logout Done");
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    void requestForLeave(String employeeID) throws InvalidLeaveRequest, InvalidDateRange {

        try{
            employee = employeeDAO.getEmployeeDetailByEmployeeID(employeeID);
            if(employee==null) {
                throw new EmployeeNotFound("Employee with ID: "+employeeID+ " not found");
            }
        }catch(EmployeeNotFound exception){
            System.out.println(exception.getMessage());
            return;
        }

        System.out.println("Leave Types:");
        LeaveType[] leaveTypes = LeaveType.values();

        for(int i=0;i<leaveTypes.length;i++){
            System.out.println((i+1)+". "+leaveTypes[i]);
        }

        int chosenLeave = scanner.nextInt();
        scanner.nextLine();
        if(chosenLeave<1 || chosenLeave>leaveTypes.length){
            System.out.println("Invalid");
            return;
        }
        LeaveType leaveType = leaveTypes[chosenLeave-1];
        if(!validateService.validateLeaveTypeForEmployee(employee, leaveType)){
            return;
        }
        System.out.print("Start Date: ");
        LocalDate leaveStartDate = LocalDate.parse(scanner.nextLine());

        System.out.print("End Date: ");
        LocalDate leaveEndDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Reason: ");
        String leaveReason = scanner.nextLine();

        if(!validateService.validateLeaveRequest(employee,leaveType,leaveStartDate,leaveEndDate)){
            return;
        }
        LeaveRequest leaveRequest = new LeaveRequest(employeeID, leaveType, leaveStartDate, leaveEndDate, leaveReason);
        if(leaveType==LeaveType.SICK_LEAVE || leaveType==LeaveType.MATERNITY_LEAVE){
            System.out.print("Medical Certificate: ");
            leaveRequest.setMedicalCertificate(scanner.nextLine());
        }
        if(leaveType==LeaveType.PARENTAL_LEAVE){
            System.out.print("Parent Certificate: ");
            leaveRequest.setParenthoodCertificate(scanner.nextLine());
        }
        leaveService.submitLeaveRequest(leaveRequest);
        System.out.println("Submitted Leave Request ID: "+leaveRequest.getLeaveRequestID());
    }

    void checkLeavesToProcess(String approverId) throws EmployeeNotFound{

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

        LeaveRequest leaveRequest = pendingRequestsForApprover.get(choice-1);

        System.out.println("1. Approve 2. Reject");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch(action){
            case 1:
                leaveService.approveLeave(leaveRequest, approverId);
                System.out.println("Approved");
                break;
            case 2:
                leaveService.rejectLeave(leaveRequest, approverId);
                System.out.println("Rejected");
                break;
            default:
                System.out.println("Invalid");
        }
    }

    void checkLeaveBalance(String employeeID) {

        try{
            employee = employeeDAO.getEmployeeDetailByEmployeeID(employeeID);
            if(employee==null) {
                throw new EmployeeNotFound("Employee with ID: "+employeeID+ " not found");
            }
        }catch(EmployeeNotFound exception){
            System.out.println(exception.getMessage());
            return;
        }

        for(Map.Entry<LeaveType,Integer> employeeLeaveBalance : employee.getEmployeeLeaveBalance().entrySet()){
            System.out.println(employeeLeaveBalance.getKey()+" = "+employeeLeaveBalance.getValue());
        }
    }

    void checkLeaveHistory(String employeeID){

        try{
            employee = employeeDAO.getEmployeeDetailByEmployeeID(employeeID);
            if(employee==null) {
                throw new EmployeeNotFound("Employee with ID: "+employeeID+ " not found");
            }
        }catch(EmployeeNotFound exception){
            System.out.println(exception.getMessage());
            return;
        }

        List<LeaveRequest> leaveHistory = leaveService.getLeaveHistoryForEmployee(employeeID);

        if(leaveHistory.isEmpty()){
            System.out.println("No leave history!");
        } else {
            for(LeaveRequest leaveRequest : leaveHistory){
                System.out.println(leaveRequest);
            }
        }
    }

    void showEmployees(){

        List<Employee> employeesList = employeeDAO.getAllEmployeesDetails();

        //List<Employee> employeeList = com.lms.utils.EmployeePropertiesUtil.getAllEmployeesDetails();

        if (employeesList.isEmpty()) {
            System.out.println("No employees found!");
        } else {
            for (Employee employee : employeesList) {
                System.out.println(employee);
            }
        }
    }

    public static void main(String[] args) throws EmployeeNotFound, InvalidLeaveRequest, InvalidDateRange{
        LeaveManagementSystem obj = new LeaveManagementSystem();
        obj.showMainMenu();
    }
}
