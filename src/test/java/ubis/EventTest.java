package ubis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    public void testDate() {
        Task event = new Event().initialise("busy times /from 2026-09-01 /to 2026-09-12");
        assertNotEquals(null, event);
        event = new Event().initialise("busy times /from 2026-99-01 /to 2026-09-12");
        assertNull(event);
    }

    @Test
    public void testStringify() {
        Task event = new Event().initialise("busy times /from 2026-09-01 /to 2026-09-12");
        assertEquals("{E}{0}{busy times}{2026-09-01}{2026-09-12}", event.stringify());
        event.mark();
        assertEquals("{E}{1}{busy times}{2026-09-01}{2026-09-12}", event.stringify());
    }
}
