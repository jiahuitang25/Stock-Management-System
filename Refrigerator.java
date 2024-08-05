//This class represents a refrigerator product, which is a type of Product.
//It includes attributes specific to refrigerators such as door design, color, and capacity.

public class Refrigerator extends Product {
    private String doorDesign;
    private String color;
    private int capacity;

    //argument constructor
    public Refrigerator(int itemNum, String name, int quantityAvailable, double price, String doorDesign, String color, int capacity) {
        super(itemNum, name, quantityAvailable, price);
        this.doorDesign = doorDesign;
        this.color = color;
        this.capacity = capacity;
    }

    //accessor methods
    public String getDoorDesign() {
        return doorDesign;
    }
    public String getColor() {
        return color;
    }
    public int getCapacity() {
        return capacity;
    }

    //mutator methods
    public void setDoorDesign(String doorDesign) {
        this.doorDesign = doorDesign;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //method to calculate the total value of the refrigerator
    public double valueStock() {
        return super.totalInventoryValue();
    }
    
    //override the toString method to provide a string representation of the refrigerator object
    @Override
    public String toString() {
        return "Item number\t\t: " + getItemNum() + "\n" +
                "Product name\t\t: " + getName() + "\n" +
                "Door design\t\t: " + doorDesign + "\n" +
                "Color\t\t\t: " + color + "\n" +
                "Capacity (in Litres)\t: " + capacity + "\n" +
                "Quantity available\t: " + getQuantityAvailable() + "\n" +
                "Price\t\t\t\t: RM" + String.format("%.2f", getPrice()) + "\n" +
                "Inventory value\t: RM" + String.format("%.2f", valueStock()) + "\n" +
                "Product status\t\t: " + isStatus() + "\n";
    }


}
