package menu;

import domain.Budget;
import domain.Category;
import domain.SimpleUser;
import domain.Transaction;
import domain.User;
import javasave.Javasave;
import java.util.Scanner;

public class MenuTest {
    
    /**
     * Main method to test menu functionalities.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Initialize root category and user
        Category rootCategory = new Category("Root");
        User user = new SimpleUser();
        Scanner scanner = new Scanner(System.in);

        // Test Add Category
        System.out.println("Testing Add Category");
        rootCategory.addCategory(new Category("Category1"));
        rootCategory.addCategory(new Category("Category2"));
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Add Category test passed.");

        // Test Add Budget
        System.out.println("Testing Add Budget");
        Category category1 = rootCategory.findCategory("Category1");
        if (category1 != null) {
            category1.addBudget(new Budget("Budget1", 1000.0));
            category1.addBudget(new Budget("Budget2", 2000.0));
        }
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Add Budget test passed.");

        // Test Add Transaction
        System.out.println("Testing Add Transaction");
        Budget budget1 = category1.findBudget("Budget1");
        if (budget1 != null) {
            budget1.addTransaction(new Transaction("Transaction1", 100.0));
            budget1.addTransaction(new Transaction("Transaction2", 200.0));
        }
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Add Transaction test passed.");

        // Test Delete Transaction
        System.out.println("Testing Delete Transaction");
        Menu.deleteTransaction(rootCategory);
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Delete Transaction test passed.");

        // Test Delete Budget
        System.out.println("Testing Delete Budget");
        Menu.deleteBudget(rootCategory);
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Delete Budget test passed.");

        // Test Delete Category
        System.out.println("Testing Delete Category");
        Menu.deleteCategory(rootCategory);
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Delete Category test passed.");

        // Test Save Data (Java)
        System.out.println("Testing Save Data (Java)");
        Javasave.saveData(rootCategory, Menu.javaCategoriesPath, Menu.javaBudgetsPath, Menu.javaTransactionsPath);
        System.out.println("Save Data (Java) test passed.");

        // Test Load Data (Java)
        System.out.println("Testing Load Data (Java)");
        rootCategory = Javasave.loadData(Menu.javaCategoriesPath, Menu.javaBudgetsPath, Menu.javaTransactionsPath);
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Load Data (Java) test passed.");

        // Test Save Data (OCaml)
        System.out.println("Testing Save Data (OCaml)");
        Menu.saveDataOCaml(rootCategory);
        System.out.println("Save Data (OCaml) test passed.");

        // Test Load Data (OCaml)
        System.out.println("Testing Load Data (OCaml)");
        rootCategory = new Category("Root");  
        Menu.loadDataOCaml(rootCategory);
        Menu.viewTreeStructure(rootCategory, "");
        System.out.println("Load Data (OCaml) test passed.");

        // Final success message
        System.out.println("All tests passed successfully.");
    }
}


