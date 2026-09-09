package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests storage recovery from missing paths, invalid paths, and malformed records.
 */
public class StorageTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void load_missingFile_createsFileAndReturnsEmptyList() {
        Path savePath = temporaryDirectory.resolve("nested").resolve("data.txt");

        TaskList tasks = Storage.load(savePath);

        assertTrue(Files.isRegularFile(savePath));
        assertEquals("No tasks to show", tasks.listTasks());
    }

    @Test
    public void saveAndLoad_validTasks_preservesData() {
        Path savePath = temporaryDirectory.resolve("data.txt");
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo().initialise("read book"));

        assertTrue(Storage.save(tasks, savePath));
        assertEquals(tasks.toString(), Storage.load(savePath).toString());
    }

    @Test
    public void load_malformedRecords_skipsOnlyInvalidLines() throws IOException {
        Path savePath = temporaryDirectory.resolve("data.txt");
        String storedData = "{T}{0}{read book}\n"
                + "{T}{2}{invalid status}\n"
                + "{T}{0}{extra field}{unexpected}\n"
                + "{X}{0}{unknown type}\n"
                + "{D}{0}{invalid date}{2026-02-30}\n"
                + "prefix{T}{0}{stray text}\n"
                + "{T}{0}{}\n"
                + "{E}{1}{trip}{2026-09-01}{2026-09-03}\n";
        Files.writeString(savePath, storedData);

        TaskList tasks = Storage.load(savePath);

        assertEquals("{T}{0}{read book}\n{E}{1}{trip}{2026-09-01}{2026-09-03}\n", tasks.toString());
    }

    @Test
    public void save_pathIsDirectory_returnsFalseWithoutDeletingDirectory() throws IOException {
        Path invalidSavePath = Files.createDirectory(temporaryDirectory.resolve("data.txt"));

        assertFalse(Storage.save(new TaskList(), invalidSavePath));
        assertTrue(Files.isDirectory(invalidSavePath));
    }

    @Test
    public void load_parentPathIsFile_returnsEmptyList() throws IOException {
        Path parentFile = temporaryDirectory.resolve("not-a-directory");
        Files.writeString(parentFile, "content");

        TaskList tasks = Storage.load(parentFile.resolve("data.txt"));

        assertEquals("No tasks to show", tasks.listTasks());
    }
}
