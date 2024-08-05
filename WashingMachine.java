//This class represents a washing machine product, which is a type of Product.
//It includes attributes specific to washing machines such as machine type, color, and capacity.

public class WashingMachine extends Product {
    private String machineType;
    private String color;
    private int capacity;

    //argument constructor
    public WashingMachine(int itemNum, String name, int quantityAvailable, double price, String machineType, String color, int capacity) {
        super(itemNum, name, quantityAvailable, price);
        this.machineType = machineType;
        this.color = color;
        this.capacity = capacity;
    }

    //accessor method
    public String getMachineType() {
        return machineType;
    }
    public String getColor() {
        return color;
    }
    public int getCapacity() {
        return capacity;
    }

    //mutator method
    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //method to calculate the total value of the washing machine
    public double valueStock() {
        return super.totalInventoryValue();
    }

    //override the toString method to provide a string representation of the washing machine object
    @Override
    public String toString() {
        return "Item number\t\t: " + getItemNum() + "\n" +
                "Product name\t\t: " + getName() + "\n" +
                "Machine Type\t\t: " + machineType + "\n" +
                "Color\t\t\t: " + color + "\n" +
                "Capacity (in KG)\t: " + capacity + "\n" +
                "Quantity available\t: " + getQuantityAvailable() + "\n" +
                "Price\t\t\t\t: RM" + String.format("%.2f", getPrice()) + "\n" +
                "Inventory value\t: RM" + String.format("%.2f", valueStock()) + "\n" +
                "Product status\t\t: " + isStatus() + "\n";
    }

}