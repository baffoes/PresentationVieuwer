import org.example.Utilities.DirectoryCleaner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryCleanerTest {

    @Test
    public void testDeleteFolderContents() throws IOException {
        // Define the folder path "unzipped"
        Path folderPath = Path.of("unzipped");

        // Create test files inside "unzipped"
        File testFile1 = new File(folderPath.toFile(), "testFile1.txt");
        File testFile2 = new File(folderPath.toFile(), "testFile2.txt");
        File subFolder = new File(folderPath.toFile(), "subFolder");
        File subFile = new File(subFolder, "subFile.txt");

        // Ensure files and folders are created successfully
        assertTrue(testFile1.createNewFile(), "Failed to create testFile1.txt");
        assertTrue(testFile2.createNewFile(), "Failed to create testFile2.txt");
        assertTrue(subFolder.mkdir(), "Failed to create subFolder");
        assertTrue(subFile.createNewFile(), "Failed to create subFile.txt");

        // Ensure files exist before cleaning
        assertTrue(testFile1.exists(), "testFile1.txt should exist before cleaning.");
        assertTrue(testFile2.exists(), "testFile2.txt should exist before cleaning.");
        assertTrue(subFolder.exists(), "subFolder should exist before cleaning.");
        assertTrue(subFile.exists(), "subFile.txt should exist before cleaning.");

        System.out.println("Folder before cleaning: " + folderPath.toAbsolutePath());

        // Run DirectoryCleaner
        DirectoryCleaner.deleteFolderContents(folderPath.toString());

        // Verify that all contents were deleted but the folder itself remains
        assertEquals(0, Objects.requireNonNull(folderPath.toFile().list()).length, "The folder should be empty after cleaning.");

        System.out.println("Test passed. Folder is empty after cleaning.");

    }

}
