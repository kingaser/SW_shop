package basket;

public class Basket {

    private int id;
    private String userName;
    private String itemName;
    private int itemPrice;

    public Basket(int id, String userName, String itemName, int itemPrice) {
        this.id = id;
        this.userName = userName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
