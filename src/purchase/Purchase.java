package purchase;

public class Purchase {

    private int id;
    private String userName;
    private String itemName;
    private int itemPrice;
    private int quantity;

    public Purchase(int id, String userName, String itemName, int itemPrice, int quantity) {
        this.id = id;
        this.userName = userName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}
