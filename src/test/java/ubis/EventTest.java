package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the creation, validation, and serialization of Event tasks.
 */
public class EventTest {

    @Test
    public void initialise_validDateRange_returnsEvent() {
        Task event = new Event().initialise("busy times /from 2026-09-01 /to 2026-09-12");
        assertNotEquals(null, event);
    }

    @Test
    public void initialise_invalidDateFormat_returnsNull() {
        Task event = new Event().initialise("busy times /from 2026-99-01 /to 2026-09-12");
        assertNull(event);
    }

    @Test
    public void stringify_unmarkedAndMarkedEvent_returnsCorrectStorageString() {
        Task event = new Event().initialise("busy times /from 2026-09-01 /to 2026-09-12");
        assertEquals("{E}{0}{busy times}{2026-09-01}{2026-09-12}", event.stringify());
        event.mark();
        assertEquals("{E}{1}{busy times}{2026-09-01}{2026-09-12}", event.stringify());
    }
}

