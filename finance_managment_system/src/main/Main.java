package main;

import menu.Menu;

import java.util.Scanner;

import domain.Category;
import domain.SimpleUser;
import domain.User;
import domain.VIPUser;

public class Main {

    private static User user;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Prompt the user to select their user type (VIP or Simple)
        selectUserType();
        
        // Create the root category
        Category category = new Category("Root");
        
        // Show the menu for interacting with the category and user
        Menu.showMenu(category, user);
    }

    private static void selectUserType() {
        // Ask the user if they are a VIP user
        System.out.println("Are you a VIP user? (Y/N)");
        
        // Read the user's input and convert it to lowercase for easier comparison
        String choice = scanner.nextLine().trim().toLowerCase();
        
        // Determine the user type based on the input
        if (choice.equals("y")) {
            // If the user is a VIP, create a VIPUser instance
            user = new VIPUser();
        } else {
            // Otherwise, create a SimpleUser instance
            user = new SimpleUser();
        }
    }
}

