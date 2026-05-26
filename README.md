# Leave Management System

A Java console application for managing employee leave requests. Employees can register, log in, apply for leave, check leave balance/history, and Leads or Managers can approve or reject pending requests.

## Features

- Employee registration with role: `EXECUTIVE`, `LEAD`, or `MANAGER`
- Password-based login using BCrypt password hashes
- MySQL-backed employee storage
- MySQL-backed leave request storage
- MySQL-backed leave balance storage
- Leave request validation for date ranges and leave-type rules
- Role-based approval flow for Leads and Managers
- Console and browser-based UI entry points
- JUnit 5 tests through Maven

## Requirements

- JDK 17 or newer
- Maven 3.9 or newer
- MySQL 8 or newer

## Database Setup

Run the schema script:

```sql
SOURCE src/main/resources/schema.sql;
```

Or manually run the SQL in `src/main/resources/schema.sql` from your MySQL client.

If you already created the old database before the UI branch changes, run:

```sql
SOURCE src/main/resources/migration-ui.sql;
```

The sample users inserted by the schema all use this password:

```text
password
```

Sample employee IDs:

- `EMP1001` - Manager
- `EMP1002` - Lead
- `EMP1003` - Executive

## App Configuration

Create a local config file from the example:

```powershell
Copy-Item src/main/resources/db.properties.example src/main/resources/db.properties
```

Then edit `src/main/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/leave_management_system_db
db.username=root
db.password=your-password
```

You can also use environment variables instead:

```powershell
$env:LMS_DB_URL="jdbc:mysql://localhost:3306/leave_management_system_db"
$env:LMS_DB_USERNAME="root"
$env:LMS_DB_PASSWORD="your-password"
```

## Run

Console app:

```powershell
mvn clean compile exec:java
```

Browser UI:

```powershell
mvn clean compile exec:java -Dexec.mainClass=com.lms.web.LeaveManagementWebApp
```

Then open:

```text
http://localhost:8080
```

## Test

```powershell
mvn test
```

## Notes

- `src/main/resources/db.properties` is ignored by Git so local database passwords are not committed.
- Leave requests are stored in the `leave_requests` table, and balances are stored in `employee_leave_balances`.
- Maven is configured for the current project layout: production code in `src` and tests in `test/java`.
