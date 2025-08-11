package com.lms;

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

    Scanner sc;
    EmployeeService empService;
    LeaveService lService;
    ValidationService vService;

    public LeaveManagementSystem(){
        sc = new Scanner(System.in);
        empService = new EmployeeService();
        lService = new LeaveService(empService);
        vService = new ValidationService();
    }

    public void showMainMenu(){
        while(true){
            System.out.println("------Leave Management System------");
            System.out.println("1. Register Employee");
            System.out.println("2. Login Employee");
            System.out.println("3. Show Employees");
            System.out.println("4. Exit");
            System.out.print("Enter option: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch(ch){
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
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.println("Type: 1.Executive 2.Lead 3.Manager");
        int t = sc.nextInt();
        sc.nextLine();
        EmployeeType et;
        switch(t){
            case 1:
                et = EmployeeType.EXECUTIVE;
                break;
            case 2:
                et = EmployeeType.LEAD;
                break;
            case 3:
                et = EmployeeType.MANAGER;
                break;
            default:
                et = EmployeeType.EXECUTIVE;
                System.out.println("Invalid choice so default Executive");
        }
        System.out.print("Joining date (YYYY-MM-DD): ");
        LocalDate jd = LocalDate.parse(sc.nextLine());

        Employee emp = empService.createEmployee(name,email,et,jd);
        System.out.println("Registered! ID: " + emp.getEmployeeId());
    }

    void employeeLogin(){
        System.out.print("Enter emp id: ");
        String id = sc.nextLine();
        Employee e = empService.getEmployee(id);
        if(e==null){
            System.out.println("Not found");
            return;
        }
        System.out.println("Welcome " + e.getName());
        empMenu(id);
    }

    void empMenu(String id){
        Employee e = empService.getEmployee(id);
        while(true){
            System.out.println("=== Employee Menu ===");
            System.out.println("1. Request Leave");
            System.out.println("2. Leave Balance");
            System.out.println("3. Leave History");
            if(e.getType()==EmployeeType.LEAD || e.getType()==EmployeeType.MANAGER){
                System.out.println("4. Process Leaves");
            }
            if(e.getType()==EmployeeType.MANAGER){
                System.out.println("5. Show Employees");
            }
            System.out.println("0. Logout");
            int ch = sc.nextInt();
            sc.nextLine();

            switch(ch){
                case 1:
                    askLeave(id);
                    break;
                case 2:
                    leaveBal(id);
                    break;
                case 3:
                    leaveHist(id);
                    break;
                case 4:
                    if(e.getType()==EmployeeType.LEAD || e.getType()==EmployeeType.MANAGER){
                        processLeaves(id);
                    } else {
                        System.out.println("Invalid");
                    }
                    break;
                case 5:
                    if(e.getType()==EmployeeType.MANAGER){
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

    void askLeave(String id){
        Employee e = empService.getEmployee(id);
        if(e==null){
            System.out.println("Not found");
            return;
        }
        System.out.println("Leave types:");
        LeaveType[] arr = LeaveType.values();
        for(int i=0;i<arr.length;i++){
            System.out.println((i+1)+". "+arr[i]);
        }
        int ch = sc.nextInt();
        sc.nextLine();
        if(ch<1 || ch>arr.length){
            System.out.println("Invalid");
            return;
        }
        LeaveType lt = arr[ch-1];
        if(!vService.validateLeaveTypeForEmployee(e, lt)){
            return;
        }
        System.out.print("Start date: ");
        LocalDate s = LocalDate.parse(sc.nextLine());
        System.out.print("End date: ");
        LocalDate ed = LocalDate.parse(sc.nextLine());
        System.out.print("Reason: ");
        String rsn = sc.nextLine();
        if(!vService.validateLeaveRequest(e,lt,s,ed)){
            return;
        }
        LeaveRequest req = new LeaveRequest(id, lt, s, ed, rsn);
        if(lt==LeaveType.SICK_LEAVE || lt==LeaveType.MATERNITY_LEAVE){
            System.out.print("Medical cert: ");
            req.setMedicalCertificate(sc.nextLine());
        }
        if(lt==LeaveType.PARENTAL_LEAVE){
            System.out.print("Parent cert: ");
            req.setParenthoodCertificate(sc.nextLine());
        }
        lService.submitLeaveRequest(req);
        System.out.println("Submitted. Req ID: "+req.getRequestId());
    }

    void processLeaves(String approverId){
        List<LeaveRequest> pend = lService.getPendingRequestsForApprover(approverId);
        if(pend.isEmpty()){
            System.out.println("None");
            return;
        }
        for(int i=0;i<pend.size();i++){
            System.out.println((i+1)+". "+pend.get(i));
        }
        System.out.print("Pick no: ");
        int ch = sc.nextInt();
        sc.nextLine();
        if(ch<1 || ch>pend.size()){
            System.out.println("Invalid");
            return;
        }
        LeaveRequest req = pend.get(ch-1);
        System.out.println("1. Approve 2. Reject");
        int act = sc.nextInt();
        sc.nextLine();
        switch(act){
            case 1:
                lService.approveLeave(req, approverId);
                System.out.println("Approved");
                break;
            case 2:
                lService.rejectLeave(req, approverId);
                System.out.println("Rejected");
                break;
            default:
                System.out.println("Invalid");
        }
    }

    void leaveBal(String empId){
        Employee e = empService.getEmployee(empId);
        if(e==null){
            System.out.println("Not found");
            return;
        }
        for(Map.Entry<LeaveType,Integer> en : e.getLeaveBalance().entrySet()){
            System.out.println(en.getKey()+" = "+en.getValue());
        }
    }

    void leaveHist(String empId){
        Employee e = empService.getEmployee(empId);
        if(e==null){
            System.out.println("Not found");
            return;
        }
        List<LeaveRequest> h = lService.getLeaveHistoryForEmployee(empId);
        if(h.isEmpty()){
            System.out.println("No history");
        } else {
            for(LeaveRequest lr : h){
                System.out.println(lr);
            }
        }
    }

    void showEmployees(){
        for(Employee emp : empService.getAllEmployees().values()){
            System.out.println(emp);
        }
    }

    public static void main(String[] args){
        LeaveManagementSystem obj = new LeaveManagementSystem();
        obj.showMainMenu();
    }
}
