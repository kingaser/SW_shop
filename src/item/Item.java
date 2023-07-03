package item;

public class Item {

    private int id;
    private String itemName;
    private int itemPrice;
    private int quantity;

    //    상품명, 가격 , 재고의 참조변수
    public Item(int id, String itemName, int itemPrice, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public int getId(){
        return id;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}