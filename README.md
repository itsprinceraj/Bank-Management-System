

# Bank Management System 🏦

Welcome to the **Bank Management System** built using **Java**. This system allows users to manage their bank accounts, perform transactions like deposits and withdrawals, and track account balances.

## Features ⚙️

- **Account Creation**: Create a new bank account with details like name, email, and security pin.
- **Deposit Money** 💰: Add funds to your account.
- **Withdraw Money** 💸: Transfer money from your account to others.
- **Balance Check** 📊: View current account balance.
- **Transaction History** 📜: Track your account's transaction history.
- **Secure Access** 🔐: Transactions are protected with a security pin.

## Tech Stack 🛠️

- **Java**: Core programming language used for the application.
- **JDBC**: Used for database connectivity and executing SQL queries.
- **MySQL**: Relational database for storing user data and transactions.

## Installation 📥

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/bank-management-system.git
   ```

2. Navigate to the project folder:
   ```bash
   cd bank-management-system
   ```

3. Install necessary dependencies (if any) or ensure that the required MySQL database is set up.

4. Import the project into your IDE (e.g., IntelliJ IDEA, Eclipse).

5. Update the **database connection details** in the code to match your local MySQL server.

## Setup Database 🗄️

The system requires a MySQL database with tables to store user and transaction data. You can set up the required database using the following SQL script:

```sql
CREATE DATABASE bank_system;

USE bank_system;

CREATE TABLE accounts (
    acc_num BIGINT NOT NULL PRIMARY KEY,
    full_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    balance INT NOT NULL,
    security_pin CHAR(4) NOT NULL
);

CREATE TABLE users (
        full_name varchar(30) not null, 
        email varchar(50) not null primary key, 
        password varchar(30) not null 
    );

```

## Usage 🖥️

1. Run the application.
2. Interact with the system through the console.
3. Perform actions like creating accounts, depositing money, and transferring funds between accounts.

### Example Commands:
- Create a new account
- Deposit funds into an account
- Transfer money between accounts
- Check account balance

## Contributing 🤝

Contributiors are welcome! If you'd like to improve the project or fix a bug, feel free to fork the repository and create a pull request.


Enjoy using the **Bank Management System**! 💳
```
