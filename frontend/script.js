let employees = [
    {
        id: "EMP001",
        name: "John Doe",
        designation: "Software Engineer",
        email: "john.doe@company.com",
        joiningDate: "2023-01-15",
        leaveBalance: {
            casual: 10,
            earned: 21,
            sick: 12,
            maternity: { limit: 2, taken: 0 },
            parental: { limit: 2, taken: 0 },
            lwp: { limit: 180, taken: 0 },
            duty: 0
        },
        leaveHistory: []
    },
    {
        id: "EMP002",
        name: "Jane Smith",
        designation: "HR Manager",
        email: "jane.smith@company.com",
        joiningDate: "2022-09-10",
        leaveBalance: {
            casual: 10,
            earned: 21,
            sick: 12,
            maternity: { limit: 2, taken: 0 },
            parental: { limit: 2, taken: 0 },
            lwp: { limit: 180, taken: 0 },
            duty: 0
        },
        leaveHistory: []
    }
];

let currentEmployee = null;

const publicHolidays = [
    "2025-01-26",
    "2025-08-15",
    "2025-10-02"
];

function init() {
    showLoginForm();
    setTodayAsMinDate();
}

function getFormattedDate(date) {
    return date.toISOString().split('T')[0];
}

function setTodayAsMinDate() {
    const today = getFormattedDate(new Date());
    const startDate = document.getElementById('startDate');
    const endDate = document.getElementById('endDate');
    const joiningDate = document.getElementById('regJoiningDate');

    if (startDate) startDate.min = today;
    if (endDate) endDate.min = today;
    if (joiningDate) joiningDate.max = today;
}

function isWeekend(date) {
    const day = date.getDay();
    return day === 0 || day === 6;
}

function isPublicHoliday(date) {
    const dateStr = getFormattedDate(date);
    return publicHolidays.includes(dateStr);
}

function calculateDays(start, end, countHolidays = true) {
    let days = 0;
    let currentDate = new Date(start);
    while (currentDate <= new Date(end)) {
        if (!isWeekend(currentDate) && !isPublicHoliday(currentDate)) {
            days++;
        } else if (countHolidays) {
            days++;
        }
        currentDate.setDate(currentDate.getDate() + 1);
    }
    return days;
}

function totalDays(start, end) {
    const diffTime = Math.abs(new Date(end) - new Date(start));
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
    return diffDays;
}

function updateProratedLeave(employee) {
    const joiningDate = new Date(employee.joiningDate);
    const today = new Date();
    const startOfYear = new Date(today.getFullYear(), 0, 1);
    const monthsWorked = (today.getFullYear() - joiningDate.getFullYear()) * 12 + today.getMonth() - joiningDate.getMonth();

    const monthsRemaining = 12 - (joiningDate.getMonth());
    employee.leaveBalance.casual = Math.ceil((10 / 12) * monthsRemaining);

    employee.leaveBalance.earned = Math.floor(1.25 * monthsWorked);

    employee.leaveBalance.sick = Math.ceil((12 / 12) * monthsRemaining);
}

function showLoginForm() {
    hideAllSections();
    document.getElementById('loginForm').classList.remove('hidden');
    clearMessages();
}

function showRegisterForm() {
    hideAllSections();
    document.getElementById('registerForm').classList.remove('hidden');
    clearMessages();
    setTodayAsMinDate();
}

function showDashboard() {
    hideAllSections();
    document.getElementById('dashboard').classList.remove('hidden');
    if (currentEmployee) {
        document.getElementById('welcomeName').textContent = currentEmployee.name;
        document.getElementById('welcomeId').textContent = currentEmployee.id;
    }
}

function showApplyLeave() {
    hideAllSections();
    document.getElementById('applyLeaveForm').classList.remove('hidden');
    setTodayAsMinDate();
}

function showLeaveBalance() {
    hideAllSections();
    document.getElementById('leaveBalanceView').classList.remove('hidden');
    if (currentEmployee) {
        document.getElementById('sickLeaveBalance').textContent = currentEmployee.leaveBalance.sick;
        document.getElementById('casualLeaveBalance').textContent = currentEmployee.leaveBalance.casual;
        document.getElementById('annualLeaveBalance').textContent = currentEmployee.leaveBalance.earned;
    }
}

function showTakenLeaves() {
    hideAllSections();
    document.getElementById('takenLeavesView').classList.remove('hidden');
    displayLeaveHistory();
}

function hideAllSections() {
    const sections = ['loginForm', 'registerForm', 'dashboard', 'applyLeaveForm', 'leaveBalanceView', 'takenLeavesView'];
    sections.forEach(section => {
        document.getElementById(section).classList.add('hidden');
    });
}

function clearMessages() {
    const messages = ['loginError', 'registerError', 'registerSuccess', 'leaveApplicationError', 'leaveApplicationSuccess'];
    messages.forEach(msg => {
        const element = document.getElementById(msg);
        if (element) {
            element.classList.add('hidden');
            element.textContent = '';
        }
    });
}

function login() {
    const employeeId = document.getElementById('employeeId').value.trim();
    const employeeName = document.getElementById('employeeName').value.trim();
    const errorElement = document.getElementById('loginError');

    if (!employeeId || !employeeName) {
        showError(errorElement, 'Please enter both Employee ID and Name');
        return;
    }

    const employee = employees.find(emp =>
        emp.id.toLowerCase() === employeeId.toLowerCase() &&
        emp.name.toLowerCase() === employeeName.toLowerCase()
    );

    if (employee) {
        currentEmployee = employee;
        updateProratedLeave(currentEmployee);
        showDashboard();
        clearForm(['employeeId', 'employeeName']);
    } else {
        showError(errorElement, 'Invalid Employee ID or Name. Please check your credentials or register as a new employee.');
    }
}

function register() {
    const employeeName = document.getElementById('regEmployeeName').value.trim();
    const designation = document.getElementById('regDesignation').value.trim();
    const email = document.getElementById('regEmail').value.trim();
    const joiningDate = document.getElementById('regJoiningDate').value;
    const errorElement = document.getElementById('registerError');
    const successElement = document.getElementById('registerSuccess');

    if (!employeeName || !designation || !email || !joiningDate) {
        showError(errorElement, 'Please fill in all fields');
        return;
    }

    const newIdNumber = employees.length > 0 ?
        parseInt(employees[employees.length - 1].id.slice(3)) + 1 : 1;
    const newEmployeeId = `EMP${String(newIdNumber).padStart(3, '0')}`;

    const newEmployee = {
        id: newEmployeeId,
        name: employeeName,
        designation: designation,
        email: email,
        joiningDate: joiningDate,
        leaveBalance: {
            casual: 10,
            earned: 0,
            sick: 12,
            maternity: { limit: 2, taken: 0 },
            parental: { limit: 2, taken: 0 },
            lwp: { limit: 180, taken: 0 },
            duty: 0
        },
        leaveHistory: []
    };

    employees.push(newEmployee);
    updateProratedLeave(newEmployee);
    showSuccess(successElement, `Registration successful! Your new Employee ID is <strong>${newEmployeeId}</strong>. Please remember this for future logins.`);
    clearForm(['regEmployeeName', 'regDesignation', 'regEmail', 'regJoiningDate']);

    setTimeout(() => {
        showLoginForm();
    }, 5000);
}

function submitLeaveApplication() {
    const leaveType = document.getElementById('leaveType').value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const reason = document.getElementById('reason').value.trim();
    const errorElement = document.getElementById('leaveApplicationError');
    const successElement = document.getElementById('leaveApplicationSuccess');

    if (!leaveType || !startDate || !endDate || !reason) {
        showError(errorElement, 'Please fill in all fields');
        return;
    }

    if (new Date(startDate) > new Date(endDate)) {
        showError(errorElement, 'End date cannot be earlier than start date');
        return;
    }

    let daysDiff;
    let leaveTypeKey;
    let isCl = leaveType === "Casual Leave";

    if (isCl) {
        daysDiff = calculateDays(startDate, endDate, false);
    } else {
        daysDiff = totalDays(startDate, endDate);
    }

    switch (leaveType) {
        case "Casual Leave":
            leaveTypeKey = "casual";
            if (daysDiff > 2) {
                showError(errorElement, "Casual Leave cannot be availed for more than 2 days at a time.");
                return;
            }
            if (totalDays(startDate, endDate) > 4) {
                showError(errorElement, "Total period of absence for Casual Leave (including holidays) cannot be more than 4 days.");
                return;
            }
            if (currentEmployee.leaveBalance[leaveTypeKey] < daysDiff) {
                showError(errorElement, `Insufficient Casual Leave balance. Available: ${currentEmployee.leaveBalance[leaveTypeKey]} days, Required: ${daysDiff} days`);
                return;
            }
            break;

        case "Earned Leave":
            leaveTypeKey = "earned";
            if (currentEmployee.leaveBalance[leaveTypeKey] < daysDiff && (currentEmployee.leaveBalance[leaveTypeKey] - daysDiff) < -15) {
                showError(errorElement, `Insufficient Earned Leave balance. A maximum negative balance of 15 days is allowed. Required: ${daysDiff} days.`);
                return;
            }
            break;

        case "Sick Leave":
            leaveTypeKey = "sick";
            if (currentEmployee.leaveBalance[leaveTypeKey] < daysDiff && (currentEmployee.leaveBalance[leaveTypeKey] - daysDiff) < -12) {
                showError(errorElement, `Insufficient Sick Leave balance. A maximum negative balance of 12 days is allowed. Required: ${daysDiff} days.`);
                return;
            }
            break;

        case "Maternity/Paternity Leave":
            leaveTypeKey = "maternity";
            if (currentEmployee.designation.toLowerCase() === 'female' && currentEmployee.leaveBalance.maternity.taken >= currentEmployee.leaveBalance.maternity.limit) {
                showError(errorElement, "You have already availed the maximum number of Maternity Leaves (2) during your employment.");
                return;
            } else if (currentEmployee.designation.toLowerCase() === 'male') {
                showError(errorElement, "Maternity Leave is only for female employees. Please apply for Parental Leave instead.");
                return;
            }
            break;

        case "Parental Leave":
            leaveTypeKey = "parental";
            if (daysDiff > 7) {
                showError(errorElement, "Parental Leave is limited to a maximum of 7 days.");
                return;
            }
            if (currentEmployee.leaveBalance.parental.taken >= currentEmployee.leaveBalance.parental.limit) {
                showError(errorElement, "You have already availed the maximum number of Parental Leaves (2) during your employment.");
                return;
            }
            break;

        case "Leave Without Pay":
            leaveTypeKey = "lwp";
            if (daysDiff > currentEmployee.leaveBalance.lwp.limit) {
                showError(errorElement, `Leave Without Pay cannot exceed 180 days at a stretch. Your request is for ${daysDiff} days.`);
                return;
            }
            break;

        case "Duty Leave":
            leaveTypeKey = "duty";
            break;

        default:
            showError(errorElement, "Invalid leave type selected.");
            return;
    }

    const leaveApplication = {
        id: Date.now(),
        type: leaveType,
        startDate: startDate,
        endDate: endDate,
        days: daysDiff,
        reason: reason,
        status: 'Approved',
        appliedDate: getFormattedDate(new Date())
    };

    if (leaveTypeKey) {
        if (leaveTypeKey === "lwp") {
            currentEmployee.leaveBalance[leaveTypeKey].taken += daysDiff;
        } else if (leaveTypeKey === "maternity" || leaveTypeKey === "parental") {
            currentEmployee.leaveBalance[leaveTypeKey].taken += 1;
        } else if (leaveTypeKey === "casual" || leaveTypeKey === "earned" || leaveTypeKey === "sick") {
            currentEmployee.leaveBalance[leaveTypeKey] -= daysDiff;
        }
    }

    currentEmployee.leaveHistory.push(leaveApplication);

    showSuccess(successElement, `Leave application for ${daysDiff} day(s) of ${leaveType} submitted successfully and approved.`);
    clearForm(['leaveType', 'startDate', 'endDate', 'reason']);

    setTimeout(() => {
        showDashboard();
    }, 2000);
}

function displayLeaveHistory() {
    const historyContainer = document.getElementById('leaveHistory');

    if (!currentEmployee || currentEmployee.leaveHistory.length === 0) {
        historyContainer.innerHTML = '<p style="text-align: center; color: #666;">No leave history found.</p>';
        return;
    }

    const historyHTML = currentEmployee.leaveHistory.map(leave => `
        <div class="stat-card" style="text-align: left; margin-bottom: 15px;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
                <strong>${leave.type}</strong>
                <span style="color: #27ae60; font-size: 12px; margin-left: auto;">${leave.status}</span>
            </div>
            <div style="font-size: 14px; color: #666;">
                <div>Duration: ${leave.startDate} to ${leave.endDate} (${leave.days} days)</div>
                <div>Reason: <em>${leave.reason}</em></div>
                <div>Applied: ${leave.appliedDate}</div>
            </div>
        </div>
    `).join('');

    historyContainer.innerHTML = historyHTML;
}

function showError(element, message) {
    element.textContent = message;
    element.classList.remove('hidden');
}

function showSuccess(element, message) {
    element.innerHTML = message;
    element.classList.remove('hidden');
}

function clearForm(fieldIds) {
    fieldIds.forEach(id => {
        const element = document.getElementById(id);
        if (element) element.value = '';
    });
}

function logout() {
    currentEmployee = null;
    showLoginForm();
}

document.addEventListener('DOMContentLoaded', function() {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    if (startDateInput && endDateInput) {
        startDateInput.addEventListener('change', function() {
            endDateInput.min = this.value;
            if (endDateInput.value && endDateInput.value < this.value) {
                endDateInput.value = this.value;
            }
        });
    }
});

init();