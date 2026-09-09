package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests validation and normalization of todo descriptions.
 */
public class TodoTest {
    @Test
    public void initialise_blankName_returnsNull() {
        assertNull(new Todo().initialise("   \t"));
    }

    @Test
    public void initialise_nameWithOuterWhitespace_returnsTrimmedTodo() {
        Task todo = new Todo().initialise("  read a book  ");
        assertEquals("{T}{0}{read a book}", todo.stringify());
    }
}
