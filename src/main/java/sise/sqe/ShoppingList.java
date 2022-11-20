package sise.sqe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
  private final Supermarket supermarket;

  private final List<Product> products = new ArrayList<>();

  public ShoppingList(Supermarket superMarket) {
    supermarket = superMarket;
  }

  /*
   * Adds a Single product to the shopping list
   */
  public void addProduct(Product product) {
    this.products.add(product);
  }

  /**
   * Calculates the total price of the products in the shopping list.
   * @return the total price
   */
  public double getMarketPrice() {
    double price = 0.0;

    // Calculates the sum of all prices
    for (Product product : products) {
      price += supermarket.getPrice(product.productId) * product.getQuantity();
    }
    // Calculates the price after discount
    return price * getDiscount(price);
  }

  /**
   * Calculates the discount based on the total price.
   * @param price the total price
   * @return the discount
   */
  public double getDiscount(double price) {
    // Negative price
    if(price < 0)
      throw new IllegalArgumentException("Price cannot be negative");

    // 1000 < price
    if (price > 1000)
      return 0.85;

    // 750 < price <= 1000
    else if (price > 750)
      return 0.9;

    // 500 < price <= 750
    else if (price > 500)
      return 0.95;

    // price < 500 -> no discount!
    else
      return 1;
  }

  /**
   * Calculates the total price of the shopping list with delivery fee.
   * @param miles the distance from the supermarket to the customer
   * @return the total price with delivery fee
   */
  public double priceWithDelivery(int miles) throws IllegalArgumentException {
    // Negative miles
    if (miles < 0)
      throw new IllegalArgumentException("Miles cannot be negative");

    // calculates delivery fee
    int numOfProducts = products.size();
    double deliveryFee = supermarket.calcDeliveryFee(miles, numOfProducts);
    // calculates price
    double price = getMarketPrice();
    // total price = deliveryFee + price
    return price + deliveryFee;
  }

  /**
   * Changes quantity of a product
   * @param quantity the new quantity of the product
   * @param productId the product's ID
   * @return the total price with delivery fee
   */
  public void changeQuantity(int quantity, String productId) throws IllegalArgumentException {
    // Negative quantity
    if(quantity < 0)
      throw new IllegalArgumentException("Quantity cannot be negative");

    // 0 < quantity
    else {
      // Find the product
      for (Product product : products) {
        if (product.getId().equals(productId)){
          // Remove product
          if(quantity == 0)
            products.remove(product);
          // Change quantity
          else
            product.setQuantity(quantity);
          break;
        }
      }
    }
  }
}