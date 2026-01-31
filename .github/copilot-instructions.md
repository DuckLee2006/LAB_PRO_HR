# Lab HR System - AI Coding Instructions
# Hệ Thống HR - Hướng Dẫn Coding cho AI

## Architecture Overview / Tổng Quan Kiến Trúc

This is a layered Java HR (Human Resources) management system with clear separation of concerns:

**Đây là một hệ thống quản lý Nhân sự (HR) Java phân lớp với sự tách biệt rõ ràng:**

- **Model** (`src/Model/`): Entity classes representing domain objects / Lớp thực thể biểu diễn các đối tượng miền
  - `Employee` (abstract base), `FullTimeEmployee`, `PartTimeEmployee`
  - Supporting enums: `EmployeeType`, `EmployeeStatus`, `Department`
  - Records: `AttendanceRecord`, `SalaryRecord`

- **Manager** (`src/Manager/`): Business logic layer / Lớp logic kinh doanh
  - `EmployeeManager`: Manages employee CRUD operations via `Map<String, Employee>`
  - `AttendanceManager`: Tracks attendance records per employee / Theo dõi bản ghi điểm danh
  - `SalaryManager`: Manages salary records using attendance data / Quản lý bảng lương từ dữ liệu điểm danh
  - `Report`: Generates reports across all managers

- **CommonUtility** (`src/CommonUtility/`): Shared utilities / Các tiện ích chung
  - `InputChecker`: Validates user input (names, dates, enum values) with case-insensitive checks
  - `IdGenerator`: Creates employee IDs as `EMP + TypeCode + DeptCode + RandomNumber`
  - `Display`: Console output formatting

- **UserInterface** (`src/UserInterface/`): Presentation layer / Lớp giao diện người dùng
  - `MenuConsole`: Main application controller managing Map collections for employees, attendance, salary
  - `EmployeeMenu`: Employee-specific menu operations
  - `main`: Application entry point demonstrating employee creation flow

## Key Patterns & Conventions / Các Mẫu & Quy Ước Chính

### Employee Type Hierarchy / Phân Cấp Loại Nhân Viên
- `Employee` is abstract with concrete implementations: `FullTimeEmployee` (OT_Salary = 80000), `PartTimeEmployee`
- Employment creation uses **factory pattern** via switch statement in `main.java` based on `EmployeeType`

### Data Management / Quản Lý Dữ Liệu
- All data stored in `Map<String, Entity>` collections (no database/persistence layer)
- Managers wrap these maps and provide CRUD/business logic operations
- Example: `EmployeeManager.updateEmployee()` finds employee by ID, then updates individual fields

### ID Generation
- Employee IDs are deterministic strings: format `EMP[TypeCode][DeptCode][4-digit-random]`
- Use `InputChecker.employeeTypeCheck()`, `InputChecker.departmentCheck()` to validate enum strings
- These methods handle case-insensitive input (e.g., "AcTIVE" → `ACTIVE`)

### Input Validation / Xác Thực Đầu Vào
- `InputChecker` is a utility class with private constructor (static methods only)
- All user input goes through validation before creating/updating entities
- Regex patterns used (e.g., names must match `^[a-zA-Z\\s]+$`)

### Record Management / Quản Lý Bản Ghi
- `AttendanceRecord` and `SalaryRecord` stored as `Map<EmployeeID, List<Records>>`
- Records are date-aware; duplicate checking prevents same-date entries
- `SalaryManager` depends on `AttendanceManager` to calculate salary based on attendance

## Development Workflow

### Building & Running
```bash
# Compile (IntelliJ-based project)
javac -d out src/**/*.java

# Run
java -cp out UserInterface.main
```

### Testing Patterns
- No dedicated test framework visible; validation tested via `InputChecker` methods
- Manual testing in `main.java` demonstrates typical workflows

### Code Organization Notes
- Package names use PascalCase (non-standard, typically lowercase)
- Comments in Vietnamese indicate local development team
- `StorageDate` directory exists but is unused (likely placeholder for future persistence)

## Integration Points

1. **Employee Creation Flow**: `main` → `IdGenerator` + `InputChecker` → `EmployeeManager.addEmployee()`
2. **Attendance Flow**: Input validation → `AttendanceManager.addRecord()` → `AttendanceRecord` storage
3. **Salary Calculation**: `SalaryManager` reads `AttendanceManager` data
4. **Reporting**: `Report` aggregates data from all three managers

## Common Modifications

- **Adding new manager**: Follow pattern of `EmployeeManager` (wrap Map collection, implement CRUD)
- **New enum values**: Add to appropriate enum, update `InputChecker` validation methods
- **Persistence**: Replace Map collections with database layer (e.g., in `MenuConsole` initialization)
- **Menu options**: Extend `MenuConsole.menu` HashMap with new `Integer → Runnable` entries
