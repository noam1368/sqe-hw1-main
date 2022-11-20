package sise.sqe;

public interface Supermarket {
    /**
     * Gets the price of a specific product.
     * @param productId the product's ID
     * @return the product's price
     */
    double getPrice(String productId);

    /**
     * Calculates the delivery fee
     * @param miles the number of miles from the market to the destination
     * @param numOfProduct number of products to be delivered
     * @return the delivery fee
     */
    double calcDeliveryFee(int miles, int numOfProduct);
}
