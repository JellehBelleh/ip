import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.List;

public class Data {
    private static final Path savePath = Paths.get("data", "data.txt");
    private static final Path directoryPath = savePath.getParent();

    public static void save(TaskList taskList) {
        if (!verifyAndCreatePath()) {
            System.out.println("Failed to verify or create path. Aborting save.");
            return;
        }

        // At this point, save file exists at the directory. Just save the data
        try {
            Files.writeString(savePath, taskList.toString());
        } catch (IOException e) {
            System.out.println("Failed to write to save path: " + e);
        }
    }

    public static TaskList load() {
        TaskList tasks = new TaskList();

        if (!verifyAndCreatePath()) {
            System.out.println("Failed to verify or create path. Aborting load.");
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(savePath);

            for (String line : lines) {
                String[] segments = line.split("[{}]");
                tasks.addTask(segments);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e);
            return tasks;
        }

        return tasks;
    }

    private static boolean verifyAndCreatePath() {
        // First check if directory "./data" exists, try to create it if not.
        if (!Files.exists(directoryPath)){
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
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
            } catch (IOException e) {
                System.out.println("Couldn't create data.txt file: " + e);
                return false;
            }
        } else {
            if (!Files.isRegularFile(savePath)) {
                System.out.println(savePath + " is not a regular file.");
                return false;
            }
        }

        return true;
    }
}
