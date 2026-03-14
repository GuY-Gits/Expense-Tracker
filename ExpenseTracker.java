import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses(); // Load existing data when the program starts
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Welcome to the Expense Tracker ===");

        while (running) {
            System.out.println("\n1. Add an Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addExpense(sc);
                    break;
                case "2":
                    viewExpenses();
                    break;
                case "3":
                    System.out.println("Saving and exiting... Good luck at the hackathon!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter Date (DD/MM/YYYY): ");
        String date = scanner.nextLine();
        
        System.out.print("Enter Category (e.g., Food, Travel): ");
        String category = scanner.nextLine();
        
        System.out.print("Enter Amount: ");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a number.");
            return;
        }

        Expense newExpense = new Expense(date, category, amount);
        expenses.add(newExpense);
        saveExpenseToFile(newExpense);
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses logged yet.");
            return;
        }
        System.out.println("\n--- Your Expenses ---");
        double total = 0;
        for (Expense exp : expenses) {
            System.out.println(exp.toString());
            total += exp.getAmount();
        }
        System.out.println("---------------------");
        System.out.println("Total Spent: Rs." + total);
    }

    // Appends a single new expense to the text file
    private static void saveExpenseToFile(Expense expense) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(expense.toFileFormat());
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Loads expenses from the text file into the ArrayList at startup
    private static void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // No file yet, nothing to load

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    expenses.add(new Expense(date, category, amount));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading existing expenses.");
        }
    }
}