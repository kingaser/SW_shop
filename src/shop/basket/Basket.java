package shop.basket;

public class Basket {

    private String userName;
    private String itemName;
    private int itemPrice;

    public Basket(String userName, String itemName, int itemPrice) {
        this.userName = userName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getUserName() {
        return userName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

}
