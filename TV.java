//This class represents a TV product, which is a type of Product.
//It includes attributes specific to TVs such as screen type, resolution, and display size.

public class TV extends Product {
    private String screenType;
    private String resolution;
    private int displaySize;

    //argument constructor
    public TV(int itemNum, String name, int quantityAvailable, double price, String screenType, String resolution, int displaySize) {
        super(itemNum, name, quantityAvailable, price);
        this.screenType = screenType;
        this.resolution = resolution;
        this.displaySize = displaySize;
    }

    //accessor method
    public String getScreenType() {
        return screenType;
    }
    public String getResolution() {
        return resolution;
    }
    public int getDisplaySize() {
        return displaySize;
    }

    //mutator method
    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    //method to calculate the total value of the TV
    public double valueStock() {
        return super.totalInventoryValue();
    }
    
    //override the toString method to provide a string representation of the TV object
    @Override
    public String toString() {
        return "Item number\t\t: " + getItemNum() + "\n" +
                "Product name\t\t: " + getName() + "\n" +
                "Screen type\t\t: " + screenType + "\n" +
                "Resolution\t\t: " + resolution + "\n" +
                "Display size\t\t: " + displaySize + "\n" +
                "Quantity available\t: " + getQuantityAvailable() + "\n" +
                "Price\t\t\t\t: RM" + String.format("%.2f", getPrice()) + "\n" +
                "Inventory value\t: RM" + String.format("%.2f", valueStock()) + "\n" +
                "Product status\t\t: " + isStatus() + "\n";
    }
}
