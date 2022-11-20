
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListTest {
    static Supermarket supermarket;
    static ShoppingList shoppingList;
    static Product product;
    static String result;

    @BeforeAll
    public static void initShoppingList() {

        supermarket = Mockito.mock(Supermarket.class);
        shoppingList = new ShoppingList(supermarket);
        shoppingList=null;
    }

    @Test
    public void addProductSuccess() throws NoSuchFieldException, IllegalAccessException{
        Product p1 = new Product("001", "poptarts", 1);
        shoppingList.addProduct(p1);
        Field field = shoppingList.getClass().getDeclaredField("products");
        field.setAccessible(true);
        List products = (List)field.get(shoppingList);
        assertEquals(products.size(), 1);
    }

    @Test
    public void addProductFailure() throws NoSuchFieldException, IllegalAccessException{
        Product p1 = new Product("abc", "poptarts", 1);
        shoppingList.addProduct(p1);
        Field field = shoppingList.getClass().getDeclaredField("products");
        field.setAccessible(true);
        List products = (List)field.get(shoppingList);
        assertEquals(products.size(), 1);
    }


    @Test
    public void getPriceTest(){
        result = product.getId();
        assertEquals("001", result);
    }

    @Test
    void getMarketPriceTestIfSumIsCorrectSuccess() {
        Product p1 = new Product("1", "bamba", 2);
        Product p2 = new Product("1", "bisli", 2);
        when(supermarket.getPrice(anyString())).thenReturn(2.0);
        shoppingList.addProduct(p1);
        shoppingList.addProduct(p2);
        assertEquals(8, shoppingList.getMarketPrice());
    }
}
