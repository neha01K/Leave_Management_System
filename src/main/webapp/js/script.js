let currentEmployee = null;

function loginEmployee() {
    const employeeID = $("#employeeID").val();
    const employeeLoginPassword = $("#employeeLoginPassword").val();

    $.ajax({
        url: "Login",
        type: "POST",
        data: { employeeID: employeeID, employeeLoginPassword: employeeLoginPassword },
        success: function (response) {
            if (response.trim() === "SUCCESS") {
                currentEmployee = {
                    employeeID: employeeID,
                    employeeName: employeeID
                };
                showDashboardAfterLogin();
                loadLeaveBalance();
                loadLeaveHistory();

                getEmployeeInfo();
            } else if (response.trim() === "INVALID") {
                $("#loginError").text("Invalid credentials").removeClass("hidden");
            } else {
                $("#loginError").text("Something went wrong. Please try again").removeClass("hidden");
            }
        },
        error: function () {
            $("#loginError").text("Something went wrong on server").removeClass("hidden");
        }
    });
}

function getEmployeeInfo() {
    $.ajax({
        url: "GetEmployeeInfo",
        type: "GET",
        dataType: "json",
        success: function(data) {
            if (handleSessionTimeout(data)) return;

            console.log("GetEmployeeInfo response:", data);
            currentEmployee.employeeName = data.employeeName;
            $("#welcomeName").text(currentEmployee.employeeName);
        },
        error: function(xhr, status, error) {
            console.error("GetEmployeeInfo failed:", status, error);
        }
    });
}

function logout() {
    currentEmployee = null;

    $.ajax({
        url: "Logout",
        type: "POST",
        success: function(){
            $("#leavesDashboard").addClass("hidden");
            $("#employeeLoginForm").removeClass("hidden");
        }
    });
}

function registerEmployeeDetails() {
    const employeeName = $("#registerEmployeeName").val();
    const employeeDesignation = $("#registerDesignation").val();
    const employeeEmail = $("#registerEmail").val();
    const employeeJoiningDate = $("#registerJoiningDate").val();

    if (!employeeName || !employeeDesignation || !employeeEmail || !employeeJoiningDate) {
        $("#registerError").text("Please fill all fields").removeClass("hidden");
        return;
    }

    $.ajax({
        url: "Register",
        type: "POST",
        data: {
            registerEmployeeName: employeeName,
            registerDesignation: employeeDesignation,
            registerEmail: employeeEmail,
            registerJoiningDate: employeeJoiningDate
        },
        success: function (response) {
            if (response.trim() !== "ERROR") {
                $("#registerError").addClass("hidden");
                $("#registerSuccess").text("Registration successful! Your ID is " + response.trim() + " and default password is 12345.").removeClass("hidden");
            } else {
                $("#registerError").text("Error in registration").removeClass("hidden");
            }
        },
        error: function () {
            $("#registerError").text("Server error, try again later").removeClass("hidden");
        }
    });
}

function showDashboardAfterLogin() {
    $("#employeeLoginForm").addClass("hidden");
    $("#registrationForm").addClass("hidden");
    $("#leavesDashboard").removeClass("hidden");

    $("#welcomeName").text(currentEmployee.employeeName || currentEmployee.employeeID);
    $("#welcomeID").text(currentEmployee.employeeID);
}

function loadLeaveBalance() {
    if (!currentEmployee) return;

    $.ajax({
        url: "LeaveBalance",
        type: "GET",
        dataType: "json",
        data: { employeeID: currentEmployee.employeeID },
        success: function (data) {
           if (handleSessionTimeout(data)) return;

            const leaveData = data;
            $("#sickLeaveBalance").text(leaveData.sickLeave);
            $("#casualLeaveBalance").text(leaveData.casualLeave);
            $("#annualLeaveBalance").text(leaveData.annualLeave);
        },
        error: function(){
            console.error("Error loading leave balance");
        }
    });
}

function loadLeaveHistory() {
    if (!currentEmployee) return;

    $.ajax({
        url: "LeaveHistory",
        type: "GET",
        dataType: "json",
        data: { employeeID: currentEmployee.employeeID },
        success: function (data) {
            if (handleSessionTimeout(data)) return;

            const history = data;
            const leaveHistoryDiv = $("#leaveHistory");
            leaveHistoryDiv.html("");

            if (history.length > 0) {
                history.forEach(function(leave) {
                    leaveHistoryDiv.append("<div>" + leave.leaveType + " (" + leave.leaveStartDate + " to " + leave.leaveEndDate + ") - " + leave.leaveReason + "</div>");
                });
            } else {
                leaveHistoryDiv.html('<p style="text-align: center; color: #666;">No leave applications yet.</p>');
            }
        },
        error: function(){
            console.error("Error loading leave history");
        }
    });
}

function submitLeaveApplication() {
    const leaveType = $("#leaveType").val();
    const leaveStartDate = $("#leaveStartDate").val();
    const leaveEndDate = $("#leaveEndDate").val();
    const leaveReason = $("#leaveReason").val();

    if (!leaveType || !leaveStartDate || !leaveEndDate || !leaveReason) {
        $("#leaveApplicationError").text("Please fill all fields.").removeClass("hidden");
        return;
    }

    $.ajax({
        url: "ApplyLeave",
        type: "POST",
        data: {
            employeeID: currentEmployee.employeeID,
            leaveType: leaveType,
            leaveStartDate: leaveStartDate,
            leaveEndDate: leaveEndDate,
            leaveReason: leaveReason
        },
        success: function (response) {

        console.log("Apply leave response: ", response);
        if (handleSessionTimeout(response)) return;

            if (response.trim() === "SUCCESS") {
                $("#leaveApplicationError").addClass("hidden");
                $("#leaveApplicationSuccess").text("Leave application submitted successfully!").removeClass("hidden");
                loadLeaveBalance();
                loadLeaveHistory();
            } else {
                $("#leaveApplicationError").text("Error submitting leave").removeClass("hidden");
            }
        },
        /*error: function () {
            $("#leaveApplicationError").text("Server error").removeClass("hidden");
        }*/

        error: function (xhr) {
            console.error("Server error response:", xhr.responseText);
            $("#leaveApplicationError").text("Server error: " + xhr.responseText).removeClass("hidden");
        }
    });
}

function showApplyForLeave() {
    hideAllSections();
    $("#applyLeaveSection").removeClass("hidden");
    $("#leaveApplicationError").addClass("hidden");
    $("#leaveApplicationSuccess").addClass("hidden");
}

function showLeaveBalance() {
    hideAllSections();
    $("#leaveBalanceSection").removeClass("hidden");
    loadLeaveBalance();
}

function showTakenLeaves() {
    hideAllSections();
    $("#viewHistorySection").removeClass("hidden");
    loadLeaveHistory();
}

function showChangePassword() {
    alert("Change password functionality coming soon!")
}

function backToDashboard() {
    hideAllSections();
    $("#leavesDashboard").removeClass("hidden");
}

function showRegistrationForm() {
    $("#employeeLoginForm").addClass("hidden");
    $("#registrationForm").removeClass("hidden");
}

function showLoginForm() {
    $("#registrationForm").addClass("hidden");
    $("#employeeLoginForm").removeClass("hidden");
}

function hideAllSections() {
    $(".form-container, .leavesDashboard-container").addClass("hidden");
}

function handleSessionTimeout(response) {
    if (typeof response === "string" && response.includes("Unauthorized")) {
        alert("Your session has expired. Please log in again.");
        logout();
        return true;
    }
}

$(document).ready(function() {
    console.log("jQuery loaded successfully!");
    console.log("Script.js loaded!");
    hideAllSections();
    $("#employeeLoginForm").removeClass("hidden");
});