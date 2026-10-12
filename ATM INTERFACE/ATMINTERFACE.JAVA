import java.util.Scanner;

/**
 * TASK 3 - ATM INTERFACE
 * -----------------------
 * 1. BankAccount class -> represents the user's bank account (stores balance).
 * 2. ATM class         -> represents the ATM machine, has the menu/UI and
 *                          withdraw(), deposit(), checkBalance() methods.
 * 3. Input validation   -> ensures amounts are positive and balance is sufficient.
 * 4. Messages           -> success/failure feedback shown after every transaction.
 */

// 4. Class representing the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    // Adds money to the account (used internally by ATM.deposit)
    public void credit(double amount) {
        balance += amount;
    }

    // Removes money from the account (used internally by ATM.withdraw)
    // Returns true if the withdrawal succeeded, false if funds were insufficient
    public boolean debit(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }
}

// 1. Class representing the ATM machine itself
class ATM {
    // 5. ATM is connected to the user's BankAccount to read/modify the balance
    private final BankAccount account;
    private final Scanner scanner;

    public ATM(BankAccount account, Scanner scanner) {
        this.account = account;
        this.scanner = scanner;
    }

    // 3. Method: withdraw money
    public void withdraw(double amount) {
        // 6. Validate input
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }
        // 7. Display success/failure messages
        if (account.debit(amount)) {
            System.out.printf("Withdrawal successful! You took out $%.2f%n", amount);
            System.out.printf("Remaining balance: $%.2f%n", account.getBalance());
        } else {
            System.out.println("Transaction failed: insufficient balance.");
            System.out.printf("Your current balance is $%.2f%n", account.getBalance());
        }
    }

    // 3. Method: deposit money
    public void deposit(double amount) {
        // 6. Validate input
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }
        account.credit(amount);
        // 7. Display success message
        System.out.printf("Deposit successful! You added $%.2f%n", amount);
        System.out.printf("New balance: $%.2f%n", account.getBalance());
    }

    // 3. Method: check balance
    public void checkBalance() {
        System.out.printf("Your current balance is: $%.2f%n", account.getBalance());
    }

    // 2. The ATM's user interface / menu loop
    public void start() {
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("        WELCOME TO THE JAVA ATM         ");
        System.out.println("=========================================");

        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = readAmount();
                    withdraw(withdrawAmount);
                    break;

                case "2":
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = readAmount();
                    deposit(depositAmount);
                    break;

                case "3":
                    checkBalance();
                    break;

                case "4":
                    running = false;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    // 7. Message for invalid menu choice
                    System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
            }
        }
    }

    // Helper: safely reads a numeric amount, re-prompting on bad input
    private double readAmount() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("That's not a valid number. Please enter an amount: $");
            }
        }
    }
}

// Main driver class
public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Starting balance for the user's bank account
        BankAccount account = new BankAccount(1000.00);
        ATM atm = new ATM(account, scanner);

        atm.start();

        scanner.close();
    }
}