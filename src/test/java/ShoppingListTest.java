
import org.junit.jupiter.api.*;
import sise.sqe.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListTest {
    static Supermarket supermarket;
    static ShoppingList shoppingList;
    static Product product;
    static String result;

    public ShoppingList filedWithProduct(ShoppingList shoppingList){
        List<Product> list = new ArrayList<Product>();
        list.add(new Product("001","Poptarts",1));
        list.add(new Product("002","Lays",2));
        list.add(new Product("003", "Oreo", 3));

        for (Product value : list) {
            shoppingList.addProduct(value);
        }
        return ShoppingListTest.shoppingList;
    }
    @BeforeEach
    public void initShoppingList() {

        supermarket = Mockito.mock(Supermarket.class);
        shoppingList = new ShoppingList(supermarket);

    }

    @ParameterizedTest
    @MethodSource("paramForAddProductSuccess")
    public void addProductSuccess(List<Product> list, int numOfPro) throws NoSuchFieldException, IllegalAccessException{
        for (Product value : list) {
            shoppingList.addProduct(value);
        }
        Field field = shoppingList.getClass().getDeclaredField("products");
        field.setAccessible(true);
        List products = (List)field.get(shoppingList);

        assertEquals(products.size(), numOfPro);
    }
    private static Stream<Arguments> paramForAddProductSuccess(){
        /** I must say that you have poorly implemented the function of adding a product
         when you add a new product, you need to check before the product no longer exists
         in the store and maybe you just need to change the quantity.
         but that's what's there and with that we'll work */
        List<Product> test1 = new ArrayList<Product>();
        test1.add(new Product("001","Poptarts",1));

        List<Product> test2 = new ArrayList<Product>();
        test2.add(new Product("001","Poptarts",1));
        test2.add(new Product("002","Lays",1));

        List<Product> test3 = new ArrayList<Product>();
        test3.add(new Product("001","Poptarts",1));
        test3.add(new Product("002","Lays",1));
        test3.add(new Product("003","Oreo",1));

        List<Product> test4 = new ArrayList<Product>();
        test4.add(new Product("001","Poptarts",1));
        test4.add(new Product("002","Lays",1));
        test4.add(new Product("003","Oreo",1));
        test4.add(new Product("004","Popcorn",1));

        List<Product> test5 = new ArrayList<Product>();
        test5.add(new Product("001","Poptarts",1));
        test5.add(new Product("002","Lays",1));
        test5.add(new Product("003","Oreo",1));
        test5.add(new Product("004","Popcorn",1));
        test5.add(new Product("005","Nuts",1));

        List<Product> test6 = new ArrayList<Product>();
        test6.add(new Product("001","Poptarts",1));
        test6.add(new Product("002","Lays",1));
        test6.add(new Product("003","Oreo",1));
        test6.add(new Product("004","Popcorn",1));
        test6.add(new Product("005","Nuts",1));
        test6.add(new Product("006","MeatSticks",1));

        List<Product> test6I = new ArrayList<Product>();
        test6I.add(new Product("001","Poptarts",1));
        test6I.add(new Product("001","Poptarts",1));
        test6I.add(new Product("001","Poptarts",10));
        test6I.add(new Product("001","Popcorn",1));
        test6I.add(new Product("001","Nuts",1));
        test6I.add(new Product("001","MeatSticks",1));

        return Stream.of(Arguments.of(test1,1),
                Arguments.of(test2,2),
                Arguments.of(test3,3),
                Arguments.of(test4,4),
                Arguments.of(test5,5),
                Arguments.of(test6,6),
                Arguments.of(test6I,6));
    }



   // @Test
    public void getPriceTest(){
        result = product.getId();
        assertEquals("001", result);
    }

    @Test
    void getMarketPriceTestIfSumIsCorrectSuccess()  {
        shoppingList = filedWithProduct(shoppingList);
        when(supermarket.getPrice(anyString())).thenReturn(2.0);
        ShoppingList shoppingListSpy = spy(shoppingList);
        when(shoppingListSpy.getDiscount(anyInt())).thenReturn(1.0);
        assertEquals(12, shoppingList.getMarketPrice());
    }
    @Test
    void getDiscountNegativeNumber() throws IllegalArgumentException
    {

       assertThrows(IllegalArgumentException.class, ()->{shoppingList.getDiscount(-1.0);});
        assertThrows(IllegalArgumentException.class, ()->{shoppingList.getDiscount(Integer.MIN_VALUE);});
    }
    @Test
    void getDiscountLowerThan500()
    {
     assertEquals(shoppingList.getDiscount(500),1.0);
    }
    @Test
    void getDiscountLowerThan750()
    {
        assertEquals(shoppingList.getDiscount(750),0.95);
    }
    @Test
    void getDiscountGreaterThan1000()
    {
        assertEquals(shoppingList.getDiscount(1001),0.85);
        assertEquals(shoppingList.getDiscount(Integer.MAX_VALUE),0.85);
    }
    @Test
    void priceWithDeliveryNegativeNumber() throws IllegalArgumentException
    {

        assertThrows(IllegalArgumentException.class, ()->{shoppingList.priceWithDelivery(-1);});
        assertThrows(IllegalArgumentException.class, ()->{shoppingList.priceWithDelivery(Integer.MIN_VALUE);});
    }
    @Test
    void priceWithDeliverySuccess()
    {
        //when(supermarket.getPrice(anyString())).thenReturn(1.0);
        ShoppingList shoppingListSpy = spy(shoppingList);
        when(shoppingListSpy.getMarketPrice()).thenReturn(2.0);
        when(supermarket.calcDeliveryFee(anyInt(),anyInt())).thenReturn(2.0);
        assertEquals(4.0,shoppingListSpy.priceWithDelivery(anyInt()));

    }
    @Test
    public void changeQuantityZero() throws NoSuchFieldException, IllegalAccessException {
        shoppingList = filedWithProduct(shoppingList);
        shoppingList.changeQuantity(0,"001");
        Field field = shoppingList.getClass().getDeclaredField("products");
        field.setAccessible(true);
        List products = (List)field.get(shoppingList);
        assertEquals(2, products.size());

        shoppingList.changeQuantity(0,"002");
        assertEquals(1, products.size());

        shoppingList.changeQuantity(0,"003");
        assertEquals(0, products.size());
    }

    @Test
    public void changeQuantityNegativeMess() throws IllegalArgumentException{
        shoppingList = filedWithProduct(shoppingList);
        assertThrows(IllegalArgumentException.class, ()->{shoppingList.changeQuantity(-1,"001");});
        assertThrows(IllegalArgumentException.class, ()->{shoppingList.changeQuantity(Integer.MIN_VALUE,"001");});

    }

    @Test
    public void changeQuantityTest() {
        shoppingList = filedWithProduct(shoppingList);
        Product my_product= new Product("6","daniela",4);
        Product myProductSpy= Mockito.spy(my_product);
        shoppingList.addProduct(myProductSpy);
        shoppingList.changeQuantity(3,"6");
        Mockito.verify(myProductSpy).setQuantity(3);
        assertEquals(3,myProductSpy.getQuantity());
    }
}
