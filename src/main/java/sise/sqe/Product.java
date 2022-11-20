package sise.sqe;

public class Product {
    public final String productId;
    public final String name;
    private int quantity;

    public Product(String productId, String name, int quantity){
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}