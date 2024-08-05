import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class StockManagement {
	//method to prompt the user to enter the maximum number of products to store
	public static int maxProducts(Scanner input) {
        int max = 0;
        do {
            System.out.print("\nEnter the maximum number of products to store: ");
            if (!input.hasNextInt()) {
                System.out.println("Please enter a valid positive integer");
                input.next();
                continue;
            }
            max = input.nextInt();
            if (max <= 0) {
                System.out.println("Please enter a positive integer greater than 0");
            }
        } while (max <= 0);
        return max;
    }

	//method to display the list of products
    public static void viewProducts(ArrayList<Product> arr) {
        System.out.println("\n___List of Products___\n");
        for (int i = 0 ; i < arr.size(); i++) {
            Class<?> strClass = arr.get(i).getClass();
            System.out.println("Product No\t\t: " + (i + 1));
            System.out.println("Type\t\t\t: " + strClass.getSimpleName());
            System.out.print(arr.get(i).toString());
            System.out.println();
        }
    }

    //method to select a product
    public static int selectProduct(ArrayList<Product> arr, Scanner input) {
        for (int i = 0 ; i < arr.size(); i++) {
            System.out.println("Product " + (i + 1) + ": " + arr.get(i).getName());
        }
        System.out.println("(0 - Back to Menu)");
        int choice = 0;
        do {
            System.out.print("\nSelect a product: ");
            if (!input.hasNextInt()) {
                System.out.println("Please enter a valid integer");
                input.next();
                continue;
            }
            choice = input.nextInt();
            if (choice == 0) {
                return 0;
            }
            if (choice < 1 || choice > arr.size()) {
                System.out.println("Please enter a valid index within range");
            }
        } while (choice < 1 || choice > arr.size());
        return choice;
    }

    //method to display the main menu
    public static int displayMenu(Scanner input) {
        System.out.println("\n\n___MENU___");
        System.out.println("1. View products");
        System.out.println("2. Add stock");
        System.out.println("3. Deduct stock");
        System.out.println("4. Discontinue product");
        System.out.println("0. Exit");

        int choice = -1;
        do {
            System.out.print("Please enter a menu option: ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid Input");
                input.next();
                continue;
            }
            choice = input.nextInt();
            if (choice < 0 || choice > 4) {
                System.out.println("Invalid Input");
            }
        } while (choice < 0 || choice > 4);
        return choice;
    }

    //method to add stock to a product
    public static void addStock(ArrayList<Product> arr, Scanner input) {
        System.out.println("\n___ADD STOCK___");
        int num = -1;
        int choice = selectProduct(arr, input);

        if (choice != 0 && arr.get(choice - 1).isStatus()) {
            do {
                System.out.print("How many products to add: ");
                if (!input.hasNextInt()) {
                    System.out.println("Invalid Input");
                    input.next();
                    continue;
                }
                num = input.nextInt();
                if (num < 0) {
                    System.out.println("Invalid Input");
                }
            } while (num < 0);
            arr.get(choice - 1).addStock(num);
            System.out.println(num + " added successfully");
        }
        else {
            System.out.println("Product Discontinued");
        }
    }

    //method to deduct stock from a product
    public static void deductStock(ArrayList<Product> arr, Scanner input) {
        System.out.println("\n___DEDUCT STOCK___");
        int num = -1;
        int choice = selectProduct(arr, input);

        if (choice != 0) {
            do {
                System.out.print("How many products to deduct: ");
                if (!input.hasNextInt()) {
                    System.out.println("Invalid Input");
                    input.next();
                    continue;
                }
                num = input.nextInt();
                if (num < 0 || num > arr.get(choice - 1).getQuantityAvailable()) {
                    System.out.println("Invalid Input");
                }
            } while (num < 0 || num > arr.get(choice - 1).getQuantityAvailable());
            arr.get(choice - 1).deductStock(num);
            System.out.println(num + " deducted successfully");
        }
        else {
            System.out.println("Product Discontinued");
        }
    }

    //method to change the status of the product (active or discontinued)
    public static void discontinued(ArrayList<Product> arr, Scanner input) {
        System.out.println("\n___SET STATUS___");
        int choice = selectProduct(arr, input);
        int num = -1;

        if (choice != 0) {
            do {
                System.out.print("Active(1) or Discontinued(0): ");
                if (!input.hasNextInt()) {
                    System.out.println("Invalid Input");
                    input.next();
                    continue;
                }
                num = input.nextInt();
                if (num < 0 || num > 1) {
                    System.out.println("Invalid Input");
                }
                else if (num == 0) {
                    arr.get(choice - 1).setStatus(false);
                }
                else {
                    arr.get(choice - 1).setStatus(true);
                }
            } while (num < 0 || num > 1);
            System.out.println("Status updated");
        }

    }

    //method to select the type of product to add
    public static int selectProduct(Scanner input) {
        int choice = 0;
        System.out.println("\n___Type of Product___");
        System.out.println("1. Refrigerator");
        System.out.println("2. TV");
        System.out.println("3. Washing Machine");

        do {
            System.out.print("\nChoose a product: ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid Input");
                input.next();
                continue;
            }
            choice = input.nextInt();
            if (choice < 1 || choice > 3) {
                System.out.println("ONLY NUMBER 1 - 3 ARE ALLOWED");
            }
        } while (choice < 1 || choice > 3);
        input.nextLine();
        return choice;
    }

    //method to add a product
    public static void addProduct(ArrayList<Product> arr, Scanner input) {
        int choice = selectProduct(input);
        if (choice == 1) {
            addRefrigerator(arr, input);
        }
        else if (choice == 2) {
            addTV(arr, input);
        }
        else {
        	addWashingMachine(arr, input);
        }
    }
    
    //method to add a refrigerator product
    public static void addRefrigerator(ArrayList<Product> arr, Scanner input) {
        while (true) {
        	System.out.println("\n___REFRIGERATOR___");
            
            try {
            	System.out.print("Product name\t\t: ");
                String name = input.nextLine();
                System.out.print("Door design\t\t: ");
                String doorDesign = input.nextLine();
                System.out.print("Color\t\t\t: ");
                String color = input.nextLine();
                System.out.print("Capacity (in Litres)\t: ");
                int capacity = input.nextInt();
                System.out.print("Quantity available\t: ");
                int quantityAvailable = input.nextInt();
                System.out.print("Price (RM)\t\t: ");
                double price = input.nextDouble();
                System.out.print("Item number\t\t: ");
                int itemNum = input.nextInt();

                arr.add(new Refrigerator(itemNum, name, quantityAvailable, price, doorDesign, color, capacity));
                input.nextLine();
                System.out.println("Added successfully");
                break;
            }
            catch (InputMismatchException ex) {
                // Handle input mismatch exception
                System.err.println("Input mismatch: " + ex.getMessage());
                // Optionally, display an error message to the user
                System.out.println("Invalid input. Please enter valid values.");
                // Clear the input buffer
                input.nextLine();
            }
        }
    }

    //method to add a TV product
    public static void addTV(ArrayList<Product> arr, Scanner input) {
    	while (true) {
        	System.out.println("\n___TV___");
            
            try {
            	System.out.print("Product name\t\t: ");
                String name = input.nextLine();
                System.out.print("Screen type\t\t: ");
                String screenType = input.nextLine();
                System.out.print("Resolution\t\t: ");
                String resolution = input.nextLine();
                System.out.print("Display size\t\t: ");
                int displaySize = input.nextInt();
                System.out.print("Quantity available\t: ");
                int quantityAvailable = input.nextInt();
                System.out.print("Price (RM)\t\t: ");
                double price = input.nextDouble();
                System.out.print("Item number\t\t: ");
                int itemNum = input.nextInt();

                arr.add(new TV(itemNum, name, quantityAvailable, price, screenType, resolution, displaySize));
                input.nextLine();
                System.out.println("Added successfully");
                break;
            }
            catch (InputMismatchException ex) {
                // Handle input mismatch exception
                System.err.println("Input mismatch: " + ex.getMessage());
                // Optionally, display an error message to the user
                System.out.println("Invalid input. Please enter valid values.");
                // Clear the input buffer
                input.nextLine();
            }
    	}
    }
    
    //method to add a washing machine product
    public static void addWashingMachine(ArrayList<Product> arr, Scanner input) {
        while (true) {
        	System.out.println("\n___WASHING MACHINE___");
        	
        	try {
        		System.out.print("Product name\t\t: ");
                String name = input.nextLine();
                System.out.print("Machine Type\t\t: ");
                String doorDesign = input.nextLine();
                System.out.print("Color\t\t\t: ");
                String color = input.nextLine();
                System.out.print("Capacity (in KG)\t: ");
                int capacity = input.nextInt();
                System.out.print("Quantity available\t: ");
                int quantityAvailable = input.nextInt();
                System.out.print("Price (RM)\t\t: ");
                double price = input.nextDouble();
                System.out.print("Item number\t\t: ");
                int itemNum = input.nextInt();

                arr.add(new WashingMachine(itemNum, name, quantityAvailable, price, doorDesign, color, capacity));
                input.nextLine();
                System.out.println("Added successfully");
                break;
        	}
        	catch (InputMismatchException ex) {
                // Handle input mismatch exception
                System.err.println("Input mismatch: " + ex.getMessage());
                // Optionally, display an error message to the user
                System.out.println("Invalid input. Please enter valid values.");
                // Clear the input buffer
                input.nextLine();
            }
        	
        }
    }

    //method to ask user whether to store products
    public static ArrayList<Product> storeProduct(Scanner input) {
        ArrayList<Product> tmp = new ArrayList<>();
        int choice = -1;
        do {
            System.out.print("Would you like to add products? (1-yes / 0-no): ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid Input");
                input.next();
                continue;
            }
            choice = input.nextInt();
            if (choice < 0 || choice > 1) {
                System.out.println("Invalid Input (1-yes / 0-no)");
            }
        } while (choice < 0 || choice > 1);

        if (choice == 1) {
            int max = maxProducts(input);
            for (int i = 0; i < max; i++) {
                addProduct(tmp, input);
            }
        }
        return tmp;
    }

    //method to display the welcome message
    public static void welcomeMessage() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("WELCOME TO SMS");
        System.out.println(formattedDateTime);
        System.out.println(" ");
        System.out.println("_GROUP 100_");
        System.out.println("1. Ang Jia Pei");
        System.out.println("2. Lean Jin Hao");
        System.out.println("3. Tang Jia Hui");
        System.out.println("4. Ting Jun Jing");

    }


    public static void main(String[] args) {
      	Scanner input = new Scanner(System.in);
        int choice;

        welcomeMessage();

        UserInfo person = new UserInfo();
        person.promptName(input);

        System.out.println();

        ArrayList<Product> arr = storeProduct(input);
        if (arr.isEmpty()) {
            System.out.println();
            System.out.println(person.toString());
            System.out.println("Exiting the system");
            System.exit(0);
        }

        boolean end = false;
        do {
            choice = displayMenu(input);
            switch (choice) {
                case 1:
                    viewProducts(arr);
                    break;

                case 2:
                    addStock(arr, input);
                    break;

                case 3:
                    deductStock(arr, input);
                    break;

                case 4:
                    discontinued(arr, input);
                    break;

                case 0:
                    end = true;
                    break;
            }
        } while (!end);

        System.out.println();
        System.out.println(person.toString());
        System.out.println("Exiting the system");

        input.close();
    }

    

}
