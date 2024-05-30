package javasave;

import domain.Budget;
import domain.Category;
import domain.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Javasave {
    /**
     * Saves category, budget, and transaction data to separate files.
     *
     * @param category         the root category
     * @param categoriesFile   the file to save category data
     * @param budgetsFile      the file to save budget data
     * @param transactionsFile the file to save transaction data
     */
    public static void saveData(Category category, String categoriesFile, String budgetsFile, String transactionsFile) {
        try (PrintWriter categoriesWriter = new PrintWriter(new FileWriter(categoriesFile));
             PrintWriter budgetsWriter = new PrintWriter(new FileWriter(budgetsFile));
             PrintWriter transactionsWriter = new PrintWriter(new FileWriter(transactionsFile))) {

            saveCategoryData(category, "", categoriesWriter, budgetsWriter, transactionsWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveCategoryData(Category category, String prefix,
                                         PrintWriter categoriesWriter, PrintWriter budgetsWriter, PrintWriter transactionsWriter) {
        for (Category subCategory : category.getCategories()) {
            categoriesWriter.println(subCategory.getName());
            saveBudgetsAndTransactions(subCategory, "", budgetsWriter, transactionsWriter);
            saveCategoryData(subCategory, prefix + category.getName() + "/", categoriesWriter, budgetsWriter, transactionsWriter);
        }
    }

    private static void saveBudgetsAndTransactions(Category category, String prefix,
                                                    PrintWriter budgetsWriter, PrintWriter transactionsWriter) {
        for (Budget budget : category.getBudgets()) {
            budgetsWriter.print(category.getName() + ",");
            budgetsWriter.print(budget.getName() + ",");
            budgetsWriter.println(budget.getAmount());
            for (Transaction transaction : budget.getTransactions()) {
                transactionsWriter.print(category.getName() + ",");
                transactionsWriter.print(budget.getName() + ",");
                transactionsWriter.print(transaction.getName() + ",");
                transactionsWriter.println(transaction.getAmount());
            }
        }
    }

    /**
     * Loads category, budget, and transaction data from separate files.
     *
     * @param categoriesFile   the file containing category data
     * @param budgetsFile      the file containing budget data
     * @param transactionsFile the file containing transaction data
     * @return the root category with loaded data
     */
    public static Category loadData(String categoriesFile, String budgetsFile, String transactionsFile) {
        Category root = new Category("Root");
        try (BufferedReader categoriesReader = new BufferedReader(new FileReader(categoriesFile));
             BufferedReader budgetsReader = new BufferedReader(new FileReader(budgetsFile));
             BufferedReader transactionsReader = new BufferedReader(new FileReader(transactionsFile))) {

            String categoryLine;
            while ((categoryLine = categoriesReader.readLine()) != null) {
                String[] categoryParts = categoryLine.split("/");
                Category currentCategory = root;
                for (String categoryPart : categoryParts) {
                    Category foundCategory = currentCategory.findCategory(categoryPart);
                    if (foundCategory == null) {
                        foundCategory = new Category(categoryPart);
                        currentCategory.addCategory(foundCategory);
                    }
                    currentCategory = foundCategory;
                }
            }

            String budgetLine;
            while ((budgetLine = budgetsReader.readLine()) != null) {
                String[] parts = budgetLine.split(",");
                if (parts.length == 3) {
                    String categoryName = parts[0];
                    String budgetName = parts[1];
                    double budgetAmount = Double.parseDouble(parts[2]);
                    Category category = root.findCategory(categoryName);
                    if (category != null) {
                        category.addBudget(new Budget(budgetName, budgetAmount));
                    }
                }
            }

            String transactionLine;
            while ((transactionLine = transactionsReader.readLine()) != null) {
                String[] parts = transactionLine.split(",");
                if (parts.length == 4) {
                    String categoryName = parts[0];
                    String budgetName = parts[1];
                    String transactionName = parts[2];
                    double transactionAmount = Double.parseDouble(parts[3]);
                    Category category = root.findCategory(categoryName);
                    if (category != null) {
                        Budget budget = category.findBudget(budgetName);
                        if (budget != null) {
                            budget.addTransaction(new Transaction(transactionName, transactionAmount));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}


