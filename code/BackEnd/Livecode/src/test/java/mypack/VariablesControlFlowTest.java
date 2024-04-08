package mypack;

import expeditors.backend.app.VariablesControlFlow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VariablesControlFlowTest {

    @Test
    public void testProduct() {
       int a = 10;
       int b = 20;

       int result = VariablesControlFlow.product(a, b);

       assertEquals(200, result);
    }
}