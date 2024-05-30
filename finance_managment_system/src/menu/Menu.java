package menu;

import domain.Budget;
import domain.Category;
import domain.SimpleUser;
import domain.Transaction;
import domain.User;

import javasave.Javasave;
import javasave.Ocamlsave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    
    private static String categoriesPath = "./crud/data/categories.csv";
    private static String budgetsPath = "./crud/data/budgets.csv";
    private static String transactionsPath = "./crud/data/transactions.csv";
    protected static String javaCategoriesPath = "./crud/javadata/categories.csv";
    protected static String javaBudgetsPath = "./crud/javadata/budgets.csv";
    protected static String javaTransactionsPath = "./crud/javadata/transactions.csv";
    private static String crudCsv = "./crud/src/crud_csv.byte";
    
    /**
     * Displays the main menu and handles user input for various actions.
     *
     * @param category the root category
     * @param user     the current user
     */
    public static void showMenu(Category category, User user) {
        int choice;
        do {
            System.out.println("User status: " + (user.isVIP() ? "VIP" : "Simple"));
            System.out.println("\nMenu:");
            System.out.println("1. Add Category");
            System.out.println("2. Delete Category");
            System.out.println("3. Add Budget");
            System.out.println("4. Delete Budget");
            System.out.println("5. Add Transaction");
            System.out.println("6. Delete Transaction");
            System.out.println("7. View Tree Structure");
            System.out.println("8. Save Data (Java)");
            System.out.println("9. Save Data (OCaml)");
            System.out.println("10. Load Data (Java)");
            System.out.println("11. Load Data (OCaml)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addCategory(category);
                    break;
                case 2:
                    deleteCategory(category);
                    break;
                case 3:
                    addBudget(category);
                    break;
                case 4:
                    deleteBudget(category);
                    break;
                case 5:
                    addTransaction(category, user);
                    break;
                case 6:
                    deleteTransaction(category);
                    break;
                case 7:
                    viewTreeStructure(category, "");
                    break;
                case 8:
                    Javasave.saveData(category, javaCategoriesPath, javaBudgetsPath, javaTransactionsPath);
                    System.out.println("Data saved successfully using Java.");
                    break;
                case 9:
                    saveDataOCaml(category);
                    break;
                case 10:
                    category = Javasave.loadData(javaCategoriesPath, javaBudgetsPath, javaTransactionsPath);
                    System.out.println("Data loaded successfully using Java.");
                    break;
                case 11:
                    loadDataOCaml(category);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 11.");
            }
        } while (choice != 0);
    }

    /**
     * Adds a new category to the given category.
     *
     * @param category the category to which a new category is added
     */
    protected static void addCategory(Category category) {
        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();
        category.addCategory(new Category(categoryName));
    }

    /**
     * Deletes a category from the given category.
     *
     * @param category the category from which a category is deleted
     */
    protected static void deleteCategory(Category category) {
        System.out.print("Enter category name to delete: ");
        String categoryName = scanner.nextLine();
        category.removeCategory(categoryName);
        System.out.println("Category deleted successfully.");
    }

    /**
     * Adds a new budget to a specified category.
     *
     * @param category the root category
     */
    protected static void addBudget(Category category) {
        System.out.print("Enter category name to add budget: ");
        String categoryName = scanner.nextLine();
        Category foundCategory = category.findCategory(categoryName);
        if (foundCategory != null) {
            System.out.print("Enter budget name: ");
            String budgetName = scanner.nextLine();
            System.out.print("Enter budget amount: ");
            double budgetAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character
            foundCategory.addBudget(new Budget(budgetName, budgetAmount));
        } else {
            System.out.println("Category not found.");
        }
    }

    /**
     * Deletes a budget from a specified category.
     *
     * @param category the root category
     */
    protected static void deleteBudget(Category category) {
        System.out.print("Enter category name to delete budget: ");
        String categoryName = scanner.nextLine();
        Category foundCategory = category.findCategory(categoryName);
        if (foundCategory != null) {
            System.out.print("Enter budget name to delete: ");
            String budgetName = scanner.nextLine();
            foundCategory.removeBudget(budgetName);
            System.out.println("Budget deleted successfully.");
        } else {
            System.out.println("Category not found.");
        }
    }

    /**
     * Adds a new transaction to a specified budget within a category.
     *
     * @param category the root category
     * @param user     the current user
     */
    protected static void addTransaction(Category category, User user) {
        System.out.print("Enter category name to add transaction: ");
        String categoryName = scanner.nextLine();
        Category foundCategory = category.findCategory(categoryName);
        if (foundCategory != null) {
            System.out.print("Enter budget name to add transaction: ");
            String budgetName = scanner.nextLine();
            Budget foundBudget = foundCategory.findBudget(budgetName);
            if (foundBudget != null) {
                if (user instanceof SimpleUser) {
                    if (foundBudget.getTransactions().size() >= 3) {
                        System.out.println("You have reached the maximum limit of transactions for this budget.");
                        return;
                    }
                }
                System.out.print("Enter transaction name: ");
                String transactionName = scanner.nextLine();
                System.out.print("Enter transaction amount: ");
                double transactionAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline character
                foundBudget.addTransaction(new Transaction(transactionName, transactionAmount));
            } else {
                System.out.println("Budget not found.");
            }
        } else {
            System.out.println("Category not found.");
        }
    }

    /**
     * Deletes a transaction from a specified budget within a category.
     *
     * @param category the root category
     */
    protected static void deleteTransaction(Category category) {
        System.out.print("Enter category name to delete transaction: ");
        String categoryName = scanner.nextLine();
        Category foundCategory = category.findCategory(categoryName);
        if (foundCategory != null) {
            System.out.print("Enter budget name to delete transaction: ");
            String budgetName = scanner.nextLine();
            Budget foundBudget = foundCategory.findBudget(budgetName);
            if (foundBudget != null) {
                System.out.print("Enter transaction name to delete: ");
                String transactionName = scanner.nextLine();
                foundBudget.removeTransaction(transactionName);
                System.out.println("Transaction deleted successfully.");
            } else {
                System.out.println("Budget not found.");
            }
        } else {
            System.out.println("Category not found.");
        }
    }

    /**
     * Displays the tree structure of categories, budgets, and transactions.
     *
     * @param category the root category
     * @param prefix   the prefix for indentation
     */
    protected static void viewTreeStructure(Category category, String prefix) {
        System.out.println(prefix + category.getName());
        for (Budget budget : category.getBudgets()) {
            System.out.println(prefix + "  - " + budget.getName() + ", " + budget.getAmount());
            for (Transaction transaction : budget.getTransactions()) {
                System.out.println(prefix + "    - " + transaction.getName() + ", " + transaction.getAmount());
            }
        }
        for (Category subCategory : category.getCategories()) {
            viewTreeStructure(subCategory, prefix + "  ");
        }
    }

    /**
     * Saves data to files using OCaml.
     *
     * @param category the root category
     */
    protected static void saveDataOCaml(Category category) {
        List<String> categoriesData = new ArrayList<>();
        List<String> budgetsData = new ArrayList<>();
        List<String> transactionsData = new ArrayList<>();
        
        // Collect data
        collectData(category, categoriesData, budgetsData, transactionsData);

        // Save data using OCaml
        Ocamlsave.saveDataOCaml(categoriesData, budgetsData, transactionsData, categoriesPath, budgetsPath, transactionsPath);
    }

    /**
     * Loads data from files using OCaml.
     *
     * @param category the root category
     */
    protected static void loadDataOCaml(Category category) {
        List<String> loadedData = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("ocamlrun", crudCsv, "load", categoriesPath, budgetsPath, transactionsPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                loadedData.add(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Data loaded successfully using OCaml.");
            } else {
                System.out.println("Failed to load data using OCaml.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        for (String data : loadedData) {
            String[] parts = data.split(": ");
            if (parts.length == 2) {
                String type = parts[0];
                String value = parts[1];
                switch (type) {
                    case "Category":
                        category.addCategory(new Category(value));
                        break;
                    case "Budget":
                        String[] budgetInfo = value.split(",");
                        category.findCategory(budgetInfo[0]).addBudget(new Budget(budgetInfo[1], Double.parseDouble(budgetInfo[2])));
                        break;
                    case "Transaction":
                        String[] transactionInfo = value.split(",");
                        category.findCategory(transactionInfo[0]).findBudget(transactionInfo[1]).addTransaction(new Transaction(transactionInfo[2], Double.parseDouble(transactionInfo[3])));
                        break;
                    default:
                        System.out.println("Unknown data type.");
                        break;
                }
            } else {
                System.out.println("Invalid data format.");
            }
        }
    }

    /**
     * Collects data from categories, budgets, and transactions into lists.
     *
     * @param category           the root category
     * @param categoriesData     the list to store categories data
     * @param budgetsData        the list to store budgets data
     * @param transactionsData   the list to store transactions data
     */
    private static void collectData(Category category, List<String> categoriesData, List<String> budgetsData, List<String> transactionsData) {
        categoriesData.add(category.getName());

        for (Budget budget : category.getBudgets()) {
            budgetsData.add(category.getName() + "," + budget.getName() + "," + budget.getAmount());

            for (Transaction transaction : budget.getTransactions()) {
                transactionsData.add(category.getName() + "," + budget.getName() + "," + transaction.getName() + "," + transaction.getAmount());
            }
        }

        for (Category subCategory : category.getCategories()) {
            collectData(subCategory, categoriesData, budgetsData, transactionsData);
        }
    }
}


