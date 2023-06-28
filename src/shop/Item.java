package shop;

public class Item {

    private final String itemName;
    private final int price;
    private final int stock;
    private final String itemInfo;

    Item(String itemName, int price, int stock, String itemInfo) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.itemInfo = itemInfo;
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

    public String getItemInfo() {
        return itemInfo;
    }
}