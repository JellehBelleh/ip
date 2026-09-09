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
    public void initialise_nonExistentDate_returnsNull() {
        assertNull(new Event().initialise("trip /from 2026-02-30 /to 2026-03-02"));
    }

    @Test
    public void initialise_reversedOrEqualDates_returnsNull() {
        assertNull(new Event().initialise("trip /from 2026-09-03 /to 2026-09-01"));
        assertNull(new Event().initialise("trip /from 2026-09-03 /to 2026-09-03"));
    }

    @Test
    public void initialise_repeatedReorderedOrTrailingParameters_returnsNull() {
        assertNull(new Event().initialise(
                "trip /from 2026-09-01 /from 2026-09-02 /to 2026-09-03"));
        assertNull(new Event().initialise("trip /to 2026-09-03 /from 2026-09-01"));
        assertNull(new Event().initialise("trip /from 2026-09-01 /to 2026-09-03 unexpected"));
    }

    @Test
    public void initialise_multipleSpaces_trimsAndAcceptsEvent() {
        Task event = new Event().initialise("  trip   /from   2026-09-01   /to   2026-09-03  ");
        assertEquals("{E}{0}{trip}{2026-09-01}{2026-09-03}", event.stringify());
    }

    @Test
    public void stringify_unmarkedAndMarkedEvent_returnsCorrectStorageString() {
        Task event = new Event().initialise("busy times /from 2026-09-01 /to 2026-09-12");
        assertEquals("{E}{0}{busy times}{2026-09-01}{2026-09-12}", event.stringify());
        event.mark();
        assertEquals("{E}{1}{busy times}{2026-09-01}{2026-09-12}", event.stringify());
    }
}
