package mypack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyClassTest {

    @Test
    public void testProduct() {
       int a = 10;
       int b = 20;

       int result = MyClass.product(a, b);

       assertEquals(200, result);
    }
}
