package shop;

public class ShoppingList {

    private String itemName;
    private int itemPrice;
    private int quantity;

    public ShoppingList(String itemName, int itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
