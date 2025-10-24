package org.example.hrmOrange.allure;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * Utility class for file operations.
 */
public class FileUtils {

    private static final AllureLogger logger = new AllureLogger(FileUtils.class);

    /**
     * Copies a file from source to destination.
     * If the destination file exists, it will be replaced.
     *
     * @param sourceFile the source file
     * @param destFile   the destination file
     * @throws IOException if an I/O error occurs
     */
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (sourceFile == null || destFile == null) {
            throw new IllegalArgumentException("Source and destination files must not be null");
        }

        // Create parent directories if they don't exist
        File parentDir = destFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        Files.copy(
            sourceFile.toPath(),
            destFile.toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );
    }

    /**
     * Deletes a directory and all its contents recursively.
     *
     * @param directory the directory to delete
     * @throws IOException if an I/O error occurs
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (directory == null) {
            return;
        }

        if (!directory.exists()) {
            return;
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    if (!file.delete()) {
                        throw new IOException("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        }

        if (!directory.delete()) {
            throw new IOException("Failed to delete directory: " + directory.getAbsolutePath());
        }
        
        logger.debug("Deleted directory: {}", directory.getAbsolutePath());
    }

    /**
     * Creates a directory if it doesn't exist.
     *
     * @param path the directory path
     * @return the created or existing directory
     * @throws IOException if an I/O error occurs
     */
    public static File createDirectoryIfNotExists(Path path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        }

        File dir = path.toFile();
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
            }
            logger.debug("Created directory: {}", dir.getAbsolutePath());
        }
        return dir;
    }

    /**
     * Copies a directory and all its contents to a target directory.
     *
     * @param sourceDir the source directory to copy from
     * @param targetDir the target directory to copy to
     * @throws IOException if an I/O error occurs
     */
    public static void copyDirectory(File sourceDir, File targetDir) throws IOException {
        if (sourceDir == null || targetDir == null) {
            throw new IllegalArgumentException("Source and target directories must not be null");
        }

        if (!sourceDir.exists()) {
            throw new IOException("Source directory does not exist: " + sourceDir.getAbsolutePath());
        }

        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("Source is not a directory: " + sourceDir.getAbsolutePath());
        }

        // Create target directory if it doesn't exist
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            throw new IOException("Failed to create target directory: " + targetDir.getAbsolutePath());
        }

        // Get all files and directories from source directory
        File[] files = sourceDir.listFiles();
        if (files == null) {
            // If source is not a directory or an I/O error occurs
            throw new IOException("Unable to list files in source directory: " + sourceDir.getAbsolutePath());
        }

        for (File file : files) {
            File destFile = new File(targetDir, file.getName());
            
            if (file.isDirectory()) {
                // Recursively copy subdirectories
                copyDirectory(file, destFile);
            } else {
                // Copy file
                Files.copy(
                    file.toPath(),
                    destFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
                );
            }
        }
    }

    /**
     * Copies a directory using NIO for better performance on large directories.
     *
     * @param source the source directory
     * @param target the target directory
     * @throws IOException if an I/O error occurs
     */
    public static void copyDirectory(Path source, Path target) throws IOException {
        if (Files.notExists(source)) {
            throw new IOException("Source directory does not exist: " + source);
        }

        if (!Files.isDirectory(source)) {
            throw new IllegalArgumentException("Source is not a directory: " + source);
        }

        // Create target directory if it doesn't exist
        if (Files.notExists(target)) {
            Files.createDirectories(target);
        }

        // Copy directory tree
        Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
            new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = target.resolve(source.relativize(dir));
                    if (Files.notExists(targetDir)) {
                        Files.createDirectories(targetDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path targetFile = target.resolve(source.relativize(file));
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    return FileVisitResult.CONTINUE;
                }
            });
    }
}
