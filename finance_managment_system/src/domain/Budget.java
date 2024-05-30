package domain;

import java.util.ArrayList;
import java.util.List;

public class Budget {
    private String name;
    private double amount;
    private List<Transaction> transactions;

    // Constructor to initialize the budget with a name and amount
    public Budget(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.transactions = new ArrayList<>();
    }

    // Getter for the name of the budget
    public String getName() {
        return name;
    }

    // Getter for the amount of the budget
    public double getAmount() {
        return amount;
    }

    // Getter for the transactions of the budget
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    // Method to add a transaction to the budget
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Method to remove a transaction by its name
    public void removeTransaction(String transactionName) {
        transactions.removeIf(transaction -> transaction.getName().equals(transactionName));
    }
}



