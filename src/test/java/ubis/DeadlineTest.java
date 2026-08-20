package ubis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {
    @Test
    public void deadlineDateTest() {
        assertNull(new Deadline().initialise("submit report /by 2026-13-01"));
        assertNotEquals(null, new Deadline().initialise("submit report /by 2026-09-01"));
    }

    @Test
    public void stringifyTest() {
        assertEquals("{D}{0}{submit report}{2026-09-01}",
                new Deadline().initialise("submit report /by 2026-09-01").stringify());

        Task deadline = new Deadline().initialise("submit report /by 2026-09-01");
        assertEquals("{D}{0}{submit report}{2026-09-01}", deadline.stringify());
        deadline.mark();
        assertEquals("{D}{1}{submit report}{2026-09-01}", deadline.stringify());
    }
}