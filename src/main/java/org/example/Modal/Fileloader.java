package org.example.Modal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Fileloader {
    String showTitle;
        // Generic load method
        public Presentation load(String folderPath) {
            File folder = new File(folderPath);
            List<Slide> slideList = new ArrayList<>();

            if (folder.isDirectory()) {
                File[] files = folder.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            // Recursively load presentations from subdirectories (if necessary)
                            Presentation presentation = load(file.getAbsolutePath());
                            if (presentation != null) {
                                slideList.addAll(presentation.getSlides());
                            }
                        } else if (file.getName().endsWith(".json")) {
                            System.out.println("Loading:.. "+file.getAbsolutePath());
                            // Load JSON presentation
                            JsonParser jsonParser = new JsonParser();
                            Presentation presentation = jsonParser.parseFile(file.getAbsolutePath());
                            if (presentation != null) {
                                slideList.addAll(presentation.getSlides());
                                showTitle = presentation.getShowTitle();
                            }
                            jsonParser.displayPresentation(presentation);
                        } else if (file.getName().endsWith(".xml")) {
                            System.out.println("Loading:..."+ file.getAbsolutePath());
                            // Load XML presentation
                            XmlParser xmlParser = new XmlParser();
                             Presentation presentation = xmlParser.parseFile(file.getAbsolutePath());
                            if (presentation != null) {
                                slideList.addAll(presentation.getSlides());
                                showTitle = presentation.getShowTitle();
                            }
                            xmlParser.parseTest(file.getAbsolutePath());
                        }
                    }
                }
            }

            return slideList.isEmpty() ? null : new Presentation(showTitle, slideList);
        }

    public void unzip(String zipFile, String destFolder) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destFolder + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }

    public void deleteFolderContents(String folderPath) {
        File folder = new File(folderPath);  // Convert the String path to a File object
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolderContents(file.getAbsolutePath());  // Recursive call to delete subfolders
                }
                if (!file.delete()) {
                    System.out.println("Failed to delete: " + file.getAbsolutePath());
                }
            }
        }
    }

}
