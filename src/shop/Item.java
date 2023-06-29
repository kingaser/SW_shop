package shop;

public class Item {

    private int id;
    private String itemName;
    private int price;
    private int stock;

    public Item(int id, String itemName, int price, int stock) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

}