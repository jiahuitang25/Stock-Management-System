import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockManagementUI extends Application {
	private static String[] productArr = {"Refrigerator", "TV", "WashingMachine"}; //for combo box
	static ComboBox<String> cbo = new ComboBox<String>(FXCollections.observableArrayList(productArr));
	private static Text totalProduct = new Text();
	
	private static UserInfo person = new UserInfo();
	private static ArrayList<Product> arr = new ArrayList<>();
	private static int choice;
	private static int count;
	public static String userInfo;
	
	
	
	
	public void start(Stage stage) {
		stage.setScene(person.loginMenu(stage));
		stage.setTitle("Login");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
    //get max number of products user wishes to store
    public static Scene maxNumProduct(Stage stage) { 
		stage.setTitle("Number of Product");
    	BorderPane addPane = new BorderPane();
		
		HBox top = new HBox(10);
		top.setPadding(new Insets(10));
		Label sms = new Label("How many product to add?");
		top.getChildren().add(sms);
		top.setAlignment(Pos.CENTER);
		
		HBox center = new HBox(10);
		center.setPadding(new Insets(10));
		TextField num = new TextField();
		center.getChildren().add(num);
		center.setAlignment(Pos.CENTER);
		
		HBox bottom = new HBox(10);
		bottom.setPadding(new Insets(10));
		Button addBt = new Button("Add");
		Button exBt = new Button("Exit");
		bottom.getChildren().addAll(addBt, exBt);
		bottom.setAlignment(Pos.CENTER);
		
		addBt.setOnAction(e -> {
			try {
				count = Integer.parseInt(num.getText());
				if (count <= 0) {
					showError("Error", "Please enter a positive integer greater than 0");
	            }
				else {
					stage.setScene(mainMenu(stage));
				}
			}
			catch (NumberFormatException ex) {
				showError("Error", "Input mismatch: " + ex.getMessage());
	        }
		});
		
		exBt.setOnAction(e -> {
			showAlert("Exit", "Exiting the system!" + "\n" + userInfo);
	        stage.close();
		});
		
		addPane.setTop(top);
		addPane.setCenter(center);
		addPane.setBottom(bottom);
		
		Scene scene = new Scene(addPane, 300, 150);
		return scene;
	}
    
    //main menu
    public static Scene mainMenu(Stage stage) {
    	stage.setTitle("SMS");
    	
    	//main pane
    	BorderPane mainPane = new BorderPane();
    			
    	//welcome message
    	VBox topPane = welcomePane();
    			
   		//add product
   		BorderPane leftPane = addProductPane();
    	        
   		//modify product
    	VBox rightPane = modifyProductPane();
    			
    	//summary and exit
    	BorderPane bottomPane = exitPane(stage);
    		
    	//list of products
    	BorderPane centerPane = showAllProducts(mainPane);
    			
    			
    	mainPane.setTop(topPane);
    	mainPane.setLeft(leftPane);
    	mainPane.setBottom(bottomPane);
        mainPane.setRight(rightPane);
        mainPane.setCenter(centerPane);
        
        Scene scene = new Scene(mainPane, 1000, 800);
        return scene;
    }
    
    //welcome message
    public static VBox welcomePane() {
		
    	VBox top  = new VBox(10);
		top.setPadding(new Insets(10));
		top.setStyle("-fx-border-color: black");
		top.setStyle("-fx-background-color: black;");
		
		//welcome message
		Label title = new Label("WELCOME TO STOCK MANAGEMENT SYSTEM");
		title.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
		title.setTextFill(Color.WHITE);
		Label group = new Label("_Group 100_");
		group.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 12));
		group.setTextFill(Color.WHITE);
		Label member = new Label("1. Ang Jia Pei   2. Lean Jin Hao   3. Tang Jia Hui   4. Ting Jun Jing");
		member.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 12));
		member.setTextFill(Color.WHITE);
		
		//date and time
		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        Label datetime = new Label(formattedDateTime);
        datetime.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        datetime.setTextFill(Color.WHITE);
        
        //user id
        Text usr = new Text(userInfo);
        usr.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        usr.setFill(Color.WHITE);
		
        
        top.getChildren().addAll(title, group, member, datetime, usr);
        top.setAlignment(Pos.CENTER);
        
        return top;
	}
    
    //combo box to select product type
    public static VBox selectProductType() {
    	VBox left1  = new VBox(10);
		left1.setPadding(new Insets(10));
		left1.setStyle("-fx-border-color: black");
		Label addProduct = new Label("Add Product");
		addProduct.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 15));
		
		cbo.setValue(null);

		left1.getChildren().addAll(addProduct, cbo);
		return left1;
    }
    
    //add product details
    public static GridPane addProduct() {
    	GridPane left2  = new GridPane();
		left2.setPadding(new Insets(10));
		left2.setHgap(5);
		left2.setVgap(5);
		left2.setStyle("-fx-border-color: black");
		
		//allows user to enter product details
		Text number = new Text("Item Number\t\t: ");
		Text name = new Text("Product Name\t\t: ");
		Text quantity = new Text("Quantity\t\t\t: ");
		Text price = new Text("Price\t\t\t\t: ");
		Text txt1 = new Text();
		Text txt2 = new Text();
		Text txt3 = new Text();
		left2.add(number, 0, 0);
		left2.add(name, 0, 1);
		left2.add(quantity, 0, 2);
		left2.add(price, 0, 3);
		left2.add(txt1, 0, 4);
		left2.add(txt2, 0, 5);
		left2.add(txt3, 0, 6);
		
		TextField numberTf = new TextField();
		TextField nameTf = new TextField();
		TextField quantityTf = new TextField();
		TextField priceTf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		left2.add(numberTf, 1, 0);
		left2.add(nameTf, 1, 1);
		left2.add(quantityTf, 1, 2);
		left2.add(priceTf, 1, 3);
		left2.add(tf1, 1, 4);
		left2.add(tf2, 1, 5);
		left2.add(tf3, 1, 6);
		
		//action for combo box
		cbo.setOnAction(e -> {
	    	try {
	    		choice = cbo.getItems().indexOf(cbo.getValue());
		    	//refrigerator is selected
	    		if (choice == 0) {
		    		txt1.setText("Door Design\t\t: ");
		    		txt2.setText("Color\t\t\t: ");
		    		txt3.setText("Capacity (in Litres)\t: ");
		    	}
	    		//tv is selected
		    	else if (choice == 1) {
		    		txt1.setText("Screen Type\t\t: ");
		    		txt2.setText("Resolution\t\t: ");
		    		txt3.setText("Display Size\t\t: ");
		    	}
	    		//washing machine is selected
		    	else if (choice == 2) {
		    		txt1.setText("Machine Type\t\t: ");
		    		txt2.setText("Color\t\t\t: ");
		    		txt3.setText("Capacity (in KG)\t: ");
		    	}
	    	}
	    	catch (Exception ex) {
	            System.err.println("An error occurred: " + ex.getMessage());
	            ex.printStackTrace();
	    	}
	    });
		
		Button addButton = new Button("ADD");
		left2.add(addButton, 1, 7);
		
		//action for add button
	    addButton.setOnAction(e -> {
	    	try {
	    		//check if any product type is selected
	    		if (cbo.getValue() == null) {
	    			showError("Error", "Please select one type of product");
	    		}
	    		//check if item number existed or not
	    		else if (!isItemExists(Integer.parseInt(numberTf.getText())) && (count > 0)) {
	    			//add new refrigerator
		    		if (choice == 0) {
			    		arr.add(new Refrigerator(Integer.parseInt(numberTf.getText()), nameTf.getText(), Integer.parseInt(quantityTf.getText()), 
			    				Double.parseDouble(priceTf.getText()), tf1.getText(), tf2.getText(), Integer.parseInt(tf3.getText())));
			    	}
		    		//add new tv
			    	else if (choice == 1) {
			    		arr.add(new TV(Integer.parseInt(numberTf.getText()), nameTf.getText(), Integer.parseInt(quantityTf.getText()), 
			    				Double.parseDouble(priceTf.getText()), tf1.getText(), tf2.getText(), Integer.parseInt(tf3.getText())));
			    	}
		    		//add new washing machine
			    	else if (choice == 2) {
			    		arr.add(new WashingMachine(Integer.parseInt(numberTf.getText()), nameTf.getText(), Integer.parseInt(quantityTf.getText()), 
			    				Double.parseDouble(priceTf.getText()), tf1.getText(), tf2.getText(), Integer.parseInt(tf3.getText())));
			    	}
		    		showAlert("Success", "Item added successfully\nRefresh to check");
		    		count--;
		    	}
	    		//all products were added
	    		else if (count == 0) {
	    			showError("Error", "You have reached the max limits");
	    		}
		    	else {
		    		showError("Error", "Item Number already been used");

		    	}
	    	}
	    	catch (NumberFormatException ex) {
	    		showError("Error", "Input mismatch: " + ex.getMessage());

	        }
			//update the summary at the bottom of the page
	    	totalProduct.setText(Integer.toString(arr.size()));
		});
	    return left2;
    }
    
    //add product section
    public static BorderPane addProductPane() {
		BorderPane left = new BorderPane();
        left.setPadding(new Insets(10));
        
        VBox left1 = selectProductType();
        
		GridPane left2 = addProduct();

		left.setTop(left1);
		left.setCenter(left2);
		return left;
	}
    
    //add or deduct product
    public static BorderPane addDeduct() {
    	BorderPane rightTop = new BorderPane();
		rightTop.setPadding(new Insets(10));
		
		//title
		StackPane rightTop1 = new StackPane();
		rightTop1.setPadding(new Insets(10));
		rightTop1.setStyle("-fx-border-color: black");
		Label modify = new Label("Modify Stock");
		modify.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 15));
		rightTop1.getChildren().add(modify);
		
		//enter details 
        GridPane rightTop2  = new GridPane();
        rightTop2.setPadding(new Insets(10));
        rightTop2.setStyle("-fx-border-color: black");
        rightTop2.setHgap(5);
        rightTop2.setVgap(5);
        
		Text id = new Text("Product No\t: ");
		Text amount = new Text("Amount\t\t: ");
		rightTop2.add(id, 0, 0);
		rightTop2.add(amount, 0, 1);
		TextField idTf = new TextField();
		TextField amountTf = new TextField();
		rightTop2.add(idTf, 1, 0);
		rightTop2.add(amountTf, 1, 1);
		
        //radio button for selection
		HBox rightTop3 = new HBox(10);
		rightTop3.setPadding(new Insets(10));
		rightTop3.setStyle("-fx-border-color: black");
		RadioButton rd1 = new RadioButton("Add");
		RadioButton rd2 = new RadioButton("Deduct");
		Button confirm = new Button("Confirm");
		rightTop3.getChildren().addAll(rd1, rd2, confirm);
		
		ToggleGroup group1 = new ToggleGroup();
		rd1.setToggleGroup(group1);
		rd2.setToggleGroup(group1);
		
		//action on confirm button
		confirm.setOnAction(e -> {
			try {
				//quantity entered
		        int q = Integer.parseInt(amountTf.getText());
		        //product no entered
		        int n = Integer.parseInt(idTf.getText()) - 1;
		        
		        if (n < 0 || n >= arr.size()) {
		        	showError("Error", "Invalid product");
		            return;
		        }
		        
		        if (q <= 0) {
		        	showError("Error", "Quantity must be a positive integer");
		            return;
		        }

		        //none is selected
		        if (!rd1.isSelected() && !rd2.isSelected()) {
		        	showError("Error", "Please select an action.");
		            return;
		        }
		        //add stock
		        else if (rd1.isSelected()) {
		        	if (arr.get(n).isStatus()) {
		        		arr.get(n).addStock(q);
		                showAlert("Success", "Amount added successfully\nRefresh to check");
		        	}     
		        	else {
		        		showError("Error", "Product discontinued");
		        	}
		        } 
		        //deduct stock
	            else if (rd2.isSelected() && q <= arr.get(n).getQuantityAvailable()) {
		        	arr.get(n).deductStock(q);
		            showAlert("Success", "Amount deducted successfully\nRefresh to check");
	            }
		        else {
		        	showError("Error", "Quantity entered must not greater than " + arr.get(n).getQuantityAvailable());
		        }
		    } 
			catch (NumberFormatException ex) {
				showError("Error", "Invalid input. Please enter valid integers.");
		    }
		});
		
		rightTop.setTop(rightTop1);
		rightTop.setCenter(rightTop2);
		rightTop.setBottom(rightTop3);
		
		return rightTop;
    }
    
    //change product status
    public static BorderPane discontinue() {
    	BorderPane rightBottom = new BorderPane();
		rightBottom.setPadding(new Insets(10));
		
		//title
		StackPane rightBottom1 = new StackPane();
		rightBottom1.setPadding(new Insets(10));
		rightBottom1.setStyle("-fx-border-color: black");
		Label disc = new Label("Status of Stock");
		disc.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 15));
		rightBottom1.getChildren().add(disc);
		
		//enter product no
		GridPane rightBottom2  = new GridPane();
		rightBottom2.setPadding(new Insets(10));
		rightBottom2.setStyle("-fx-border-color: black");
		rightBottom2.setHgap(5);
		rightBottom2.setVgap(5);
        
		Text id1 = new Text("Product No\t: ");
		rightBottom2.add(id1, 0, 0);
		TextField idTf1 = new TextField();
		rightBottom2.add(idTf1, 1, 0);
		
		//radio button for status selection
		HBox rightBottom3 = new HBox(10);
		rightBottom3.setPadding(new Insets(10));
		rightBottom3.setStyle("-fx-border-color: black");
		RadioButton rd3 = new RadioButton("Active");
		RadioButton rd4 = new RadioButton("Discontinued");
		Button confirm1 = new Button("Confirm");
		rightBottom3.getChildren().addAll(rd3, rd4, confirm1);
		
		ToggleGroup group2 = new ToggleGroup();
		rd3.setToggleGroup(group2);
		rd4.setToggleGroup(group2);
		
		//action on confirm button
		confirm1.setOnAction(e -> {
			try {
				//product no entered
		        int n = Integer.parseInt(idTf1.getText()) - 1;
		        
		        if (n < 0 || n > arr.size() - 1) {
		        	showError("Error", "Please enter a valid product");
		        }

		        //none selected
		        if (!rd3.isSelected() && !rd4.isSelected()) {
		        	showError("Error", "Please select an action.");
		            return;
		        }
		        //set to active status
		        else if (rd3.isSelected()) {
	                arr.get(n).setStatus(true);
	                showAlert("Success", "Status updated successfully\nRefresh to check");
	            } 
		        //set to discontinued
	            else if (rd4.isSelected()) {
	                arr.get(n).setStatus(false);
	                showAlert("Success", "Status updated successfully\nRefresh to check");
	            }
		    } 
			catch (NumberFormatException ex) {
				showError("Error", "Invalid input. Please enter valid integers.");
		    }
		});
		
		rightBottom.setTop(rightBottom1);
		rightBottom.setCenter(rightBottom2);
		rightBottom.setBottom(rightBottom3);
		
		return rightBottom;
    }
    
    //modify product section
    public static VBox modifyProductPane() {
		VBox right = new VBox(10);
		right.setPadding(new Insets(10));
		
		BorderPane rightTop = addDeduct();
		
		BorderPane rightBottom = discontinue();
		
		right.getChildren().addAll(rightTop, rightBottom);
		
		return right;
	}
    
    //bottom section (summary + exit)
    public static BorderPane exitPane(Stage stage) {
		BorderPane bottom = new BorderPane();
		bottom.setPadding(new Insets(10));
        bottom.setStyle("-fx-border-color: black");
        bottom.setStyle("-fx-background-color: black;");
		
        //summary
        HBox bottom1 = new HBox(10);
		bottom1.setPadding(new Insets(10));
		Label total = new Label("Total Number of Product: ");
		total.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 15));
		total.setTextFill(Color.WHITE);
		totalProduct.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		totalProduct.setFill(Color.WHITE);
		
		bottom1.getChildren().addAll(total, totalProduct);
		
		//exit button
		Button exitButton = new Button("Exit");
		
		bottom.setCenter(bottom1);
		bottom.setRight(exitButton);
		
		//action on exit button
		exitButton.setOnAction(e -> {
			showAlert("Exit", "Exiting the system!" + "\n" + userInfo);
	        stage.close();
		});
		
		return bottom;
	}

    //show list of products in the center
    public static BorderPane showAllProducts(BorderPane mainPane) {
		BorderPane center = new BorderPane();
		center.setPadding(new Insets(10));
		
		//list of products
		TextArea center1 = new TextArea();
		center1.setWrapText(false);
		center1.setEditable(false);
		center1.setFont(new Font("Serif", 14));
	    ScrollPane scrollPane = new ScrollPane(center1);
	    
	    //title
	    Label list = new Label("List of Product");
	    list.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
	    list.setAlignment(Pos.CENTER);

	    //refresh button
	    Button refresh = new Button("Refresh");
	    refresh.setAlignment(Pos.CENTER);
	    
	    center.setTop(list);
	    center.setCenter(scrollPane);
	    center.setBottom(refresh);

	    //action on refresh button
	    refresh.setOnAction(e -> {
	    	center1.setText(null);
	    	for (int i = 0; i < arr.size(); i++) {
				Class<?> strClass = arr.get(i).getClass();
				center1.appendText("\nProduct No\t\t: " + (i + 1));
				center1.appendText("\nType\t\t\t\t: " + strClass.getSimpleName() + "\n");
				center1.appendText(arr.get(i).toString());
			}
	    });
	    
	    //bind the size of the textArea with the center of the borderPane
	    center1.prefWidthProperty().bind(mainPane.widthProperty());
        center1.prefHeightProperty().bind(mainPane.heightProperty());
	    return center;
	}
    
    //display information message
    public static void showAlert(String title, String message) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
    //display error message
    public static void showError(String title, String message) {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
    //check whether the item number already existed
	public static boolean isItemExists(int itemId) {
	    for (Product product : arr) {
	        if (product.getItemNum() == itemId) {
	            return true; 
	        }
	    }
	    return false; 
	}
    
    
    
}
