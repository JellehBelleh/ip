package ubis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Handles saving and loading task data to and from the local storage file.
 */
public class Storage {
    private static final Path SAVE_PATH = Paths.get("data", "data.txt");
    private static final Path DIRECTORY_PATH = SAVE_PATH.getParent();

    /**
     * Saves the task list into local file storage.
     *
     * @param taskList Task list to be saved.
     */
    public static void save(TaskList taskList) {
        if (!verifyAndCreatePath()) {
            System.out.println("Failed to verify or create path. Aborting save.");
            return;
        }

        // At this point, save file exists at the directory. Just save the data
        try {
            Files.writeString(SAVE_PATH, taskList.toString());
        } catch (IOException e) {
            System.out.println("Failed to write to save path: " + e);
        }
    }

    /**
     * Loads and returns the task list from local file storage.
     *
     * @return Task list loaded from storage, or an empty task list if loading fails.
     */
    public static TaskList load() {
        TaskList tasks = new TaskList();

        if (!verifyAndCreatePath()) {
            System.out.println("Failed to verify or create path. Aborting load.");
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(SAVE_PATH);

            for (String line : lines) {
                String[] segments = line.split("[{}]");
                segments = Arrays.stream(segments)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);

                tasks.addTask(Task.initialise(segments), false);
            }
        } catch (IOException e) {
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
    private static boolean verifyAndCreatePath() {
        // First check if directory "./data" exists, try to create it if not.
        if (!Files.exists(DIRECTORY_PATH)) {
            try {
                Files.createDirectories(DIRECTORY_PATH);
            } catch (IOException e) {
                System.out.println("Could not create directory for saving data: " + e);
                return false;
            }
        } else if (!Files.isDirectory(DIRECTORY_PATH)) {
            System.out.println(DIRECTORY_PATH + " is not a directory.");
            return false;
        }

        // Now directory exists, check if file exists. Create it if it doesn't
        if (!Files.exists(SAVE_PATH)) {
            try {
                Files.createFile(SAVE_PATH);
            } catch (IOException e) {
                System.out.println("Couldn't create data.txt file: " + e);
                return false;
            }
        } else if (!Files.isRegularFile(SAVE_PATH)) {
            System.out.println(SAVE_PATH + " is not a regular file.");
            return false;
        }

        return true;
    }
}

