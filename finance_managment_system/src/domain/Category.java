package domain;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Category> categories;
    private List<Budget> budgets;

    // Constructor to initialize the category with a name
    public Category(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.budgets = new ArrayList<>();
    }

    // Getter for the name of the category
    public String getName() {
        return name;
    }

    // Getter for the subcategories of this category
    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    // Getter for the budgets of this category
    public List<Budget> getBudgets() {
        return new ArrayList<>(budgets);
    }

    // Method to add a subcategory
    public void addCategory(Category category) {
        categories.add(category);
    }

    // Method to remove a subcategory by name
    public void removeCategory(String categoryName) {
        categories.removeIf(category -> category.getName().equals(categoryName));
    }

    // Method to add a budget
    public void addBudget(Budget budget) {
        budgets.add(budget);
    }

    // Method to remove a budget by name
    public void removeBudget(String budgetName) {
        budgets.removeIf(budget -> budget.getName().equals(budgetName));
    }

    // Method to find a subcategory by name
    public Category findCategory(String categoryName) {
        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }

    // Method to find a budget by name
    public Budget findBudget(String budgetName) {
        for (Budget budget : budgets) {
            if (budget.getName().equals(budgetName)) {
                return budget;
            }
        }
        return null;
    }
}






