# AI Museum — Museum Management System

A desktop application for managing a museum's day-to-day records: visitors, exhibit specimens, and museum sections. Built with Java Swing for the interface and JDBC with MySQL for storage, it gives museum staff a simple admin-facing way to add and review data without touching the database directly.

The repository contains two related versions of the application, each in its own folder.

---

## Features

- **Admin login** — staff authenticate against an `admin` table before accessing the system.
- **Visitor management** — register visitors (name, NID, contact, visit date) and view all visitor records.
- **Specimen management** — add specimens with a description, development date, creator, and section, and view them joined with their section and creator names.
- **Section management** — create and browse museum sections in a table view.
- **Admin menu / dashboard** — a central menu linking to the visitor and specimen screens.

## Tech Stack

| Layer    | Technology                       |
|----------|----------------------------------|
| Language | Java                             |
| UI       | Java Swing (AWT)                 |
| Database | MySQL                            |
| Access   | JDBC (MySQL Connector/J), DAO pattern |

## Project Structure

```
AI-Museum
├── Ai Museum
│   ├── src        # Admin login, admin menu, visitor & specimen screens
│   └── bin        # Compiled classes
└── Museum Managemnet
    ├── src        # Login, main window, panels, models and DAOs
    └── bin        # Compiled classes
```

- **Ai Museum** — GUI-first version: `AdminLoginGUI` → `AdminDashboard`, plus `AdminMenuGUI`, `VisitorManagementGUI`, `SpecimenManagementGUI`, and `ViewSpecimenDetailsGUI`.
- **Museum Managemnet** — structured version using models (`Section`, `Specimen`, `Visitor`), DAO classes, and Swing panels, launched from `LoginFrame` / `MuseumApp`.

## Getting Started

### Prerequisites

- JDK 8 or newer
- MySQL Server
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (the JDBC driver `.jar`)

### 1. Set up the database

Create a database named `museum_manage` and the tables the application queries. The schema below is based on the queries used in the code; adjust the column types to suit your data.

```sql
CREATE DATABASE museum_manage;
USE museum_manage;

CREATE TABLE admin (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE Creator (
    CreatorID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);

CREATE TABLE Section (
    SectionID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description TEXT
);

CREATE TABLE Specimen (
    SpecimenID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description TEXT,
    DevelopmentDate DATE,
    CreatorID INT,
    SectionID INT,
    FOREIGN KEY (CreatorID) REFERENCES Creator(CreatorID),
    FOREIGN KEY (SectionID) REFERENCES Section(SectionID)
);

CREATE TABLE Visitor (
    VisitorID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    NID VARCHAR(30),
    Contact VARCHAR(30),
    VisitDate DATE
);

-- Example admin account for testing
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');
```

### 2. Configure the connection

Edit `DatabaseConnection.java` in the folder you want to run and set your MySQL URL, username, and password:

```java
private static final String URL = "jdbc:mysql://localhost:3306/museum_manage";
private static final String USERNAME = "root";
private static final String PASSWORD = "";
```

### 3. Compile and run

From inside the `src` folder of the version you want to run (use `;` instead of `:` on Windows):

```bash
javac -cp ".:mysql-connector-j.jar" *.java
java  -cp ".:mysql-connector-j.jar" AdminLoginGUI      # Ai Museum
java  -cp ".:mysql-connector-j.jar" MuseumApp          # Museum Managemnet
```

You can also open either folder as a project in an IDE such as Eclipse or IntelliJ IDEA and add the MySQL Connector/J jar to the project libraries.

## Notes

- This is a learning project. Before using it with real data, store passwords as hashes rather than plain text, move database credentials out of the source code, and validate user input.
- Some screens in the `Ai Museum` version reference classes that are not included yet (for example the delete-visitor and view-specimens windows).

## License

This project is licensed under the MIT License. See [LICENSE.md](LICENSE.md) for details.
