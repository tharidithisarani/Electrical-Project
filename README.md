# CN Electricals - Electrical Company Management System

A comprehensive desktop application for electrical services companies, designed to streamline operations from customer management to financial reporting.

## Project Overview

CN Electricals is an all-in-one management system tailored for electrical businesses based in Galle, Sri Lanka. The system covers customer relations, technician and inventory management, supplier coordination, and financial tracking—ensuring smooth, secure, and efficient business workflows.

---

## System Features

### 🔐 Authentication & Security
- Secure login system with password recovery
- User role-based access control (Administrator, Manager, Technician, Customer)
- Fixed code authentication for password reset

### 👥 Customer Management
- Customer classification: Ordinary Buyers & Permanent Buyers
- Profile management: ID, name, address, contact
- Payment tracking and remaining balance calculation
- Order history and transaction records

### 🔧 Technician Management
- Technician registration and profile management
- Attendance tracking system
- Salary calculation (basic & overtime)
- Bank account details management
- Configurable salary and overtime pay

### 📦 Inventory & Stock Management
- Item catalog with dynamic pricing
- Stock quantity tracking, status updates
- Price adjustments

### 🏭 Supplier Management
- Supplier profile and company information
- Product catalog and item code association

### ⚡ Machine & Equipment Management
- Machine code registration and equipment tracking
- Technician assignment and machine inventory

### 💰 Order & Payment System
- Item selection, quantity management, and stock validation
- Payment calculation (full/partial payments)
- Order placement, commercial payment ID tracking

### 💸 Financial Management
- Technician salary processing
- Customer payment tracking and balance computation
- Financial summary reports

### 📊 Reporting
- Business intelligence and summary reports powered by JasperReports

---

## Technical Specifications

- **Backend:** Java 17
- **Frontend:** JavaFX 22
- **Database:** MySQL 8.3.0
- **UI Components:** JFoenix 9.0.10
- **Build Tool:** Maven
- **Reporting:** JasperReports 6.20.1
- **Utilities:** Lombok 1.18.32

---

## Project Structure

```text
electronic-company/
├── src/
│   └── main/
│       └── java/
│           └── lk/ijse/
│               ├── supermarket/
│               │   └── Launcher.java
│               └── LauncherWrapper.java
├── pom.xml
└── resources/
```

---

## Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>22</version>
    </dependency>
    <dependency>
        <groupId>com.jfoenix</groupId>
        <artifactId>jfoenix</artifactId>
        <version>9.0.10</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.32</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>net.sf.jasperreports</groupId>
        <artifactId>jasperreports</artifactId>
        <version>6.20.1</version>
    </dependency>
</dependencies>
```

---

## User Interface Analysis

### Navigation Structure
Consistent sidebar navigation with modules:
- Home (Dashboard)
- Customer
- Technician
- Suppliers
- Machines
- Items

### Key UI Components
- Form-based data entry
- Tabular data display with sorting/filtering
- Real-time financial calculations
- Status tracking
- JasperReports-based reporting

### Business Workflow
1. **User Authentication** → Role-based access
2. **Customer Registration** → Buyer classification
3. **Service Order Processing** → Item selection, payment calculation
4. **Technician Assignment** → Job allocation, attendance
5. **Inventory Management** → Stock/supplier updates
6. **Financial Processing** → Salary & payment management
7. **Reporting** → Business intelligence & summaries

---

## System Requirements

- Java Runtime Environment 17+
- MySQL Database 8.0+
- Minimum 4GB RAM
- 500MB Storage space

---

## Installation & Setup

1. **Clone or Download** the project source code.
2. **Install Java 17** and **Maven**.
3. **Configure MySQL** database connection.
4. Run:
   ```sh
   mvn clean install
   mvn javafx:run
   ```
   to build and start the application.

---

## Visual References

### Entity Relationship Diagram (ERD)
![CN Electrical Solution ER_edit](https://github.com/user-attachments/assets/2f32e36f-3d7f-49a4-a58d-f280843d8a9c)
 <!-- Replace with actual path or use repo image link -->

### Use Case Diagram
 <img width="720" height="981" alt="CN Electrical Solution Use Case-Page-1 drawio (1)" src="https://github.com/user-attachments/assets/915aacd7-fe36-4744-8baf-d3f857bf6474" />
<!-- Replace with actual path or use repo image link -->

---

## Future Enhancements

- Mobile application integration
- Online payment gateway
- Advanced analytics and reporting
- SMS notification system
- Mobile technician app for field operations

---

## UI interfaces

### Login Page : Ensuring system security
<img width="787" height="415" alt="Screenshot 2025-10-07 202527" src="https://github.com/user-attachments/assets/312eb583-a781-4b3a-b626-050aa8d7e640" />

### Home Page
<img width="872" height="485" alt="Screenshot 2025-10-07 202640" src="https://github.com/user-attachments/assets/3bacecb7-853f-454d-bdbd-ba29089c30cc" />

### Registered customerd Order form
<img width="881" height="477" alt="Screenshot 2025-10-07 202553" src="https://github.com/user-attachments/assets/a6b0415b-adca-44f7-97e0-b2b36bb16805" />

### Customer and Constructer details adding and updating form
<img width="968" height="461" alt="Screenshot 2025-10-07 202608" src="https://github.com/user-attachments/assets/5fb9f90f-3482-485e-ae36-b94453debce9" />

### Item details adding and updating form
<img width="1014" height="496" alt="Screenshot 2025-10-07 202625" src="https://github.com/user-attachments/assets/3d8df0d2-a59d-499f-ab34-b3aa5c52a387" />

### Machine details adding and updating form
<img width="853" height="480" alt="Screenshot 2025-10-07 202652" src="https://github.com/user-attachments/assets/94f7cb32-a5ec-43a4-9594-494f247383b6" />

### Suppliers details adding and updating form
<img width="861" height="484" alt="Screenshot 2025-10-07 202701" src="https://github.com/user-attachments/assets/9f7d38d7-659a-4972-8eaf-1f87366d3fb5" />

### Technisiyans details including
<img width="862" height="485" alt="Screenshot 2025-10-07 202711" src="https://github.com/user-attachments/assets/595e3f55-ca48-4eaa-b57b-3969ac590752" />

### Technician Management form
<img width="862" height="486" alt="Screenshot 2025-10-07 202724" src="https://github.com/user-attachments/assets/0bc0568b-7b1a-4422-96e7-41c089d35981" />
