package org.example.Utilities;

import java.io.File;

public class DirectoryCleaner {

    public static void deleteFolderContents(String folderPath) {
        File folder = new File(folderPath);

        File[] files = folder.listFiles();

        // Check if the folder is empty
        if (files == null || files.length == 0) {
            System.out.println("The folder is empty.");
            return; // Exit if the folder is empty
        }

        // Iterate over the files in the folder
        for (File file : files) {
            if (file.isDirectory()) {
                // Recursively delete contents of subdirectories
                deleteFolderContents(file.getAbsolutePath());
                System.out.println("Deleting existing file: " + file.getAbsolutePath());
            }


            // Attempt to delete the file or folder
            if (!file.delete()) {
                System.out.println("Failed to delete: " + file.getAbsolutePath());
            }
        }
    }


}
