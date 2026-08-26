package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the creation, validation, and serialization of Deadline tasks.
 */
public class DeadlineTest {

    @Test
    public void initialise_invalidDateFormat_returnsNull() {
        assertNull(new Deadline().initialise("submit report /by 2026-13-01"));
    }

    @Test
    public void initialise_validDate_returnsDeadline() {
        assertNotEquals(null, new Deadline().initialise("submit report /by 2026-09-01"));
    }

    @Test
    public void stringify_unmarkedAndMarkedDeadline_returnsCorrectStorageString() {
        assertEquals("{D}{0}{submit report}{2026-09-01}",
                new Deadline().initialise("submit report /by 2026-09-01").stringify());

        Task deadline = new Deadline().initialise("submit report /by 2026-09-01");
        assertEquals("{D}{0}{submit report}{2026-09-01}", deadline.stringify());
        deadline.mark();
        assertEquals("{D}{1}{submit report}{2026-09-01}", deadline.stringify());
    }
}