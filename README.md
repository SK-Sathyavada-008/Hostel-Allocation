# Hostel Room and Bed Allocation System

A robust, Java-based Hostel Allocation Management System designed for college dormitories and campus residential facilities.

---

## 1. Problem Statement & Mission Blueprint

Colleges face significant logistical challenges during annual student hostel allotment, including duplicate allocations, room capacity overflows, gender-segregated ward tracking, and record loss. This project delivers an automated, menu-driven allocation system that enforces strict business rules, manages bed matrices in memory, and writes audit logs to persistent storage.

### System Architecture & Component Design

```
┌────────────────────────────────────────────────────────┐
│             Student Registration Interface             │
│   (Console CLI with input validation & exception guard) │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│               Hostel Allocation Controller             │
│   - Gender-based ward routing (Boys: B1-B3, Girls: G1-G3)│
│   - 2D Bed Matrix allocation tracker (3 Wards x 5 Beds)│
│   - Duplicate ID prevention                            │
└─────────────┬────────────────────────────┬─────────────┘
              │                            │
              ▼                            ▼
┌───────────────────────────┐┌───────────────────────────┐
│     Analytics & Report    ││     Persistent Storage    │
│  - Total & occupied beds  ││  - Transaction audit log  │
│  - Percentage utilization ││  - students.txt records   │
└───────────────────────────┘└───────────────────────────┘
```

---

## 2. Key Modules & Domain Specifications

1. **Student Entities (`Student2`, `Boy`, `Girl`)**:
   - Represents student attributes: ID, Name, Gender, Assigned Ward, Bed Number, and Enrollment Status.
2. **Bed Matrix Allocation Engine**:
   - Fixed matrix of 3 room types with 5 beds per room (30 beds total capacity).
   - Fast lookup and occupancy state checking.
3. **Domain Exception Hierarchy**:
   - `InvalidGenderException`: Enforces valid gender identifiers ('M'/'F').
   - `InvalidRoomTypeException`: Guards ward range (1 to 3).
   - `HostelFullException`: Raised when ward capacity is reached.
   - `NoBedAvailableException`: Raised when selected room type has no vacancies.
   - `StudentNotFoundException`: Raised when querying nonexistent student IDs.
4. **Persistent Audit Trail**:
   - Appends all confirmed allocations to `students.txt` with formatted columns.
5. **Analytics & Search**:
   - Search by student ID across boys and girls rosters.
   - Real-time percentage occupancy metrics.

---

## 3. Setup and Execution Instructions

### Prerequisites
- Java Development Kit (JDK 8 or higher)
- Terminal or Command Prompt

### Compilation
```bash
javac -d . Hostel_allocate.java
```

### Execution
```bash
java P1.Hostel_allocate
```

---

## 4. Testing & Verification

Automated regression and functional testing can be executed via the test suite:
```bash
javac -d . TestHostelAllocate.java
java P1.TestHostelAllocate
```