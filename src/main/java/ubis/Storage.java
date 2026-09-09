package ubis;

import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

/**
 * Handles saving and loading task data to and from the local storage file.
 */
public class Storage {
    private static final Path SAVE_PATH = Paths.get("data", "data.txt");

    /**
     * Saves the task list into local file storage.
     *
     * @param taskList Task list to be saved.
     * @return True if the task list was saved successfully, false otherwise.
     */
    public static boolean save(TaskList taskList) {
        return save(taskList, SAVE_PATH);
    }

    /**
     * Saves the task list to a specified path. This overload allows storage failures to be tested safely.
     *
     * @param taskList Task list to be saved.
     * @param savePath File to save the task list to.
     * @return True if the task list was saved successfully, false otherwise.
     */
    static boolean save(TaskList taskList, Path savePath) {
        if (!verifyAndCreatePath(savePath)) {
            System.out.println("Failed to verify or create path. Aborting save.");
            return false;
        }

        Path temporaryFile = null;
        try {
            Path directory = getDirectoryPath(savePath);
            temporaryFile = Files.createTempFile(directory, "data", ".tmp");
            Files.writeString(temporaryFile, taskList.toString());
            replaceSaveFile(temporaryFile, savePath);
            return true;
        } catch (IOException | SecurityException e) {
            System.out.println("Failed to write to save path: " + e);
            return false;
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException | SecurityException e) {
                    System.out.println("Failed to remove temporary save file: " + e);
                }
            }
        }
    }

    /**
     * Replaces the save file atomically when supported by the file system.
     *
     * @param temporaryFile Fully written temporary file.
     * @param savePath Destination save file.
     * @throws IOException If the temporary file cannot be moved into place.
     */
    private static void replaceSaveFile(Path temporaryFile, Path savePath) throws IOException {
        try {
            Files.move(temporaryFile, savePath,
                    StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException e) {
            Files.move(temporaryFile, savePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Loads and returns the task list from local file storage.
     *
     * @return Task list loaded from storage, or an empty task list if loading fails.
     */
    public static TaskList load() {
        return load(SAVE_PATH);
    }

    /**
     * Loads tasks from a specified path. This overload allows storage failures to be tested safely.
     *
     * @param savePath File to load tasks from.
     * @return Task list loaded from storage, or an empty task list if loading fails.
     */
    static TaskList load(Path savePath) {
        TaskList tasks = new TaskList();

        if (!verifyAndCreatePath(savePath)) {
            System.out.println("Failed to verify or create path. Aborting load.");
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(savePath);

            for (String line : lines) {
                String[] segments = line.split("[{}]");
                segments = Arrays.stream(segments)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);

                tasks.addTask(Task.initialise(segments), false);
            }
        } catch (IOException | SecurityException e) {
            System.out.println("Error reading file: " + e);
            return tasks;
        }

        return tasks;
    }

    /**
     * Checks if the save directory and file exist, creating them if missing.
     *
     * @return True if the file and directory are valid and accessible, false otherwise.
     */
    private static boolean verifyAndCreatePath(Path savePath) {
        Path directoryPath = getDirectoryPath(savePath);

        // First check if directory "./data" exists, try to create it if not.
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException | SecurityException e) {
                System.out.println("Could not create directory for saving data: " + e);
                return false;
            }
        } else if (!Files.isDirectory(directoryPath)) {
            System.out.println(directoryPath + " is not a directory.");
            return false;
        }

        // Now directory exists, check if file exists. Create it if it doesn't
        if (!Files.exists(savePath)) {
            try {
                Files.createFile(savePath);
            } catch (IOException | SecurityException e) {
                System.out.println("Couldn't create data.txt file: " + e);
                return false;
            }
        } else if (!Files.isRegularFile(savePath)) {
            System.out.println(savePath + " is not a regular file.");
            return false;
        }

        return true;
    }

    /**
     * Returns the directory containing the save file, or the working directory for a bare file name.
     *
     * @param savePath Save file path.
     * @return Directory containing the save file.
     */
    private static Path getDirectoryPath(Path savePath) {
        Path parent = savePath.getParent();
        return parent == null ? Paths.get(".") : parent;
    }
}
