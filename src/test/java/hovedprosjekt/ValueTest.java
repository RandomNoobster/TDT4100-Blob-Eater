package hovedprosjekt;

import hovedprosjekt.Utils.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ValueTest {
    @Test
    @DisplayName("Test contructing a value")
    void testValueConstructor() {
        Value value = new Value();
        assertEquals(0, value.getValue());
        assertEquals(null, value.getMaxValue());
        assertEquals(null, value.getMinValue());

        Value value2 = new Value(5, 0, 10);
        assertEquals(5, value2.getValue());
        assertEquals(10, value2.getMaxValue());
        assertEquals(0, value2.getMinValue());
    }

    @Test
    @DisplayName("Test incrementing a value")
    void testIncrementValue() {
        Value value = new Value(0, 0, 10);
        value.incrementValue(5);
        assertEquals(5, value.getValue());
        assertThrows(IllegalArgumentException.class, () -> value.incrementValue(6));
    }
}
