
# Gym Management System (Java Swing)

A robust, GUI-based Gym Management System built with **Java Swing**. This project focuses on high-quality software architecture by implementing various **GoF (Gang of Four) Design Patterns** to ensure scalability, maintainability, and clean code.

## 🚀 Features

  - **Member Registration:** Add new members with specific details (Name, Age, ID).
  - **Membership Management:** Supports different plans (Monthly, VIP) using Factory logic.
  - **Add-on Services:** Dynamically add Personal Trainers or Lockers to a membership using Decorators.
  - **Undo Functionality:** Revert the last registration action using the Command pattern.
  - **Real-time Notifications:** Simulated SMS and Email alerts when a new member joins.
  - **Strategic Data Display:** View member lists grouped by their membership plans.

-----

## 🛠️ Design Patterns Used

This project serves as a practical implementation of several software design patterns:

| Pattern | Usage in Project |
| :--- | :--- |
| **Singleton** | `GymDatabase` ensures only one instance of the member list exists across the application. |
| **Builder** | `Member` class uses an inner Builder to handle complex object creation step-by-step. |
| **Factory Method** | `MembershipFactory` instantiates different plan types (Monthly, VIP) based on user input. |
| **Command** | `RegisterMemberCommand` encapsulates the registration request, allowing for **Undo** operations. |
| **Observer** | `MemberObserver` triggers SMS and Email notifications automatically when the database updates. |
| **Decorator** | `MembershipDecorator` allows adding extra features (Trainer, Locker) to a plan without altering the base class. |
| **Strategy** | `MemberDisplayStrategy` allows switching between different ways of listing/sorting members. |
| **Iterator** | Used to traverse the member list within the GUI display logic efficiently. |

-----

## 📂 Project Structure

```text
src/
├── model/
│   ├── Member.java               // Builder Pattern
│   ├── Membership.java           // Base Interface
│   ├── MonthlyPlan.java          // Concrete Implementation
│   └── VIPPlan.java              // Concrete Implementation
├── database/
│   └── GymDatabase.java          // Singleton & Observer Subject
├── patterns/
│   ├── MembershipFactory.java    // Factory Pattern
│   ├── MembershipDecorator.java  // Decorator Pattern
│   ├── Command.java              // Command Pattern Interface
│   ├── MemberObserver.java       // Observer Interface
│   └── MemberDisplayStrategy.java// Strategy Interface
└── gui/
    └── GymManagementGUI.java     // Main UI Entry Point (Swing)
```

-----

## 💻 Prerequisites

  - **Java Development Kit (JDK) 8** or higher.
  - An IDE (IntelliJ IDEA, Eclipse, or NetBeans) or a terminal.

## 🏃 How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/gym-management-system.git
    ```
2.  **Navigate to the directory:**
    ```bash
    cd gym-management-system
    ```
3.  **Compile the project:**
    ```bash
    javac gui/GymManagementGUI.java
    ```
4.  **Run the application:**
    ```bash
    java gui.GymManagementGUI
    ```

-----

## 📝 Usage Guide

1.  **Registering a Member:** Enter the name, age, and ID. Select a plan from the dropdown.
2.  **Adding Extras:** Check the "Personal Trainer" or "Locker" boxes to see the price update dynamically (Decorator Pattern).
3.  **Undo:** If you make a mistake, click "Undo" to remove the most recently added member.
4.  **View Members:** Click "Display All" to see the list organized by membership type (Strategy Pattern).

## 📄 License

This project is open-source and available under the [MIT License](https://www.google.com/search?q=LICENSE).

-----

*Developed with ❤️ by Kawser and Pallab*
