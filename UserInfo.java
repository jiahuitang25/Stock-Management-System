//This class represents user information, including the user ID, first name, and surname.
//It provides methods to prompt the user for their name and generate a user ID based on the input.

import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInfo {
    private String userID;
    private String firstName;
    private String surname;

    
    
    public Scene loginMenu(Stage stage) {

		BorderPane welcomePane = new BorderPane();
		
		//title
		HBox top = new HBox(10);
		top.setPadding(new Insets(10));
		Label sms = new Label("STOCK MANAGEMENT SYSTEM");
		sms.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
		sms.setTextFill(Color.WHITE);
		top.getChildren().add(sms);
		top.setAlignment(Pos.CENTER);
		top.setStyle("-fx-background-color: black;");
		
		//prompt for first name and surname
		GridPane center = new GridPane();
		Text fname = new Text("First Name\t: ");
		Text sname = new Text("Surname\t\t: ");
		TextField fnTf = new TextField();
		TextField snTf = new TextField();
		center.add(fname, 0, 0);
		center.add(sname, 0, 1);
		center.add(fnTf, 1, 0);
		center.add(snTf, 1, 1);
		center.setAlignment(Pos.CENTER);
		
		//login button
		HBox bottom = new HBox(10);
		bottom.setPadding(new Insets(10));
		Button loginBt = new Button("Login");
		bottom.getChildren().add(loginBt);
		bottom.setAlignment(Pos.CENTER);
		
		//action on login button
		loginBt.setOnAction(e -> {
			try {
				firstName = fnTf.getText();
				surname = snTf.getText();
				
				//check if both first name and surname is provided
				if (firstName.isEmpty() || surname.isEmpty()) {
					StockManagementUI.showError("Error", "Please enter both first name and surname");
		            return;
				}
				else if (firstName.contains(" ") || surname.contains(" ")) {
					// Generate user ID based on first character of first name and full surname
		            userID = fnTf.getText().charAt(0) + snTf.getText();
		            StockManagementUI.userInfo = toString();
		        } 
				else {
					// Set user ID as 'guest' if no spaces are found in first name and surname
		            userID = "guest";

		            StockManagementUI.userInfo = toString();
		        }
				stage.setScene(StockManagementUI.maxNumProduct(stage));
		    } 
			catch (NumberFormatException ex) {
				StockManagementUI.showError("Error", "Input mismatch: " + ex.getMessage());
	        }
		});
		
		welcomePane.setTop(top);
		welcomePane.setCenter(center);
		welcomePane.setBottom(bottom);
        
		Scene scene = new Scene(welcomePane, 300, 150);
		return scene;
	}

    //accessor method
    public String getUserID() {
    	return userID;
    }

    //prompts the user to enter their first name and surname
    //generates a user ID based on their full name
    public void promptName(Scanner input) {
    	while (true) {
    		System.out.println("\n___User Information___");
        	
        	try {
        		System.out.print("First Name: ");
        		firstName = input.nextLine();

                System.out.print("Surname: ");
                surname = input.nextLine();
                
                
                if (firstName.isEmpty() || surname.isEmpty()) {
                    System.out.println("First name and surname cannot be empty.");
                    continue;
                }

                if (firstName.contains(" ") || surname.contains(" ")) {
                    userID = firstName.charAt(0) + surname;
                } 
                else {
                    userID = "guest";
                }
                break;
        	}
        	catch (InputMismatchException ex) {
                System.err.println("Input mismatch: " + ex.getMessage());
                System.out.println("Invalid input. Please enter valid values.");

                input.nextLine();
            }
    	}
    }
    
    //overrides the toString method to provide a string representation of the user information
    public String toString() {
        return userID + " (Full Name: " + firstName + " " + surname + ")";
    }
}
