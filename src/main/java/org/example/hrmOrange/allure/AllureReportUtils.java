/**
 * Utility class for managing Allure report generation and configuration.
 * Handles report generation, environment setup, and report file management.
 */
package org.example.hrmOrange.allure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.example.hrmOrange.helpers.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureReportUtils {

    private static final Logger logger = LoggerFactory.getLogger(AllureReportUtils.class);

    public static final Path PROJECT_DIR = Paths.get(System.getProperty("user.dir"));
    public static final Path RESULT_DIR  = Paths.get("target", "allure-results");
    public static final Path ALLURE_REPORT_DIR = Paths.get("target", "site", "allure-maven-plugin");
    public static final Path REPORT_DIR  = Paths.get(ConfigReader.getProperty("EXTENT_REPORT_PATH"));

    /**
     * Generate Allure Report
     * @param suiteName Allure suite name
     */
    public static void generateAllureReport(String suiteName) {
        try {
            logger.info("Starting Allure report generation...");
            
            // Step 1: Write environment properties
            writeEnvironment();
            createDirectoryIfNotExist(REPORT_DIR);
            
            // Step 2: Generate Allure report using Maven
            logger.info("Running: mvn allure:report");
            if (!runAllureCommand("mvn -q --no-transfer-progress allure:report")) {
                logger.error("Failed to generate Allure Report via Maven");
                return;
            }
            
            // Step 3: Move report to target directory
            moveReportToTarget(suiteName);
            
            logger.info("✅ Allure Report generated successfully at: " + REPORT_DIR.toAbsolutePath());
        } catch (Exception e) {
            logger.error("Error generating Allure Report: " + e.getMessage(), e);
        }
    }

    /**
     * Runs the specified command in a new process.
     * Automatically detects OS and uses appropriate shell.
     * @param command The command to run.
     * @return true if the command exits with 0, false otherwise.
     */
    public static boolean runAllureCommand(String command) {
        ProcessBuilder processBuilder = isWindows()
                ? new ProcessBuilder("cmd.exe", "/c", command)
                : new ProcessBuilder("sh",      "-c", command);

        // Set working directory to project root
        processBuilder.directory(PROJECT_DIR.toFile());
        processBuilder.redirectErrorStream(true);

        try {
            // Execute and wait for completion
            Process process = processBuilder.start();
            return process.waitFor() == 0;

        } catch (Exception e) {
            logger.warn(String.format("Executed command %s failed - %s", command, e.getMessage()));
            return false;
        }
    }

    /**
     * Detects if the current OS is Windows.
     */
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Moves the generated Allure report to the configured report directory.
     * @param suiteName Used to name the final report folder.
     */
    private static void moveReportToTarget(String suiteName) throws IOException {
        // Resolve absolute path for source report directory
        Path absoluteSourcePath = PROJECT_DIR.resolve(ALLURE_REPORT_DIR);
        File sourceDir = absoluteSourcePath.toFile();
        
        logger.info("Looking for Allure report at: " + sourceDir.getAbsolutePath());
        
        if (!sourceDir.exists()) {
            logger.warn("⚠️ Allure report directory not found at: " + sourceDir.getAbsolutePath());
            logger.info("Checking alternative location: target/allure-report");
            
            // Try alternative location
            File altDir = new File(PROJECT_DIR.toFile(), "target/allure-report");
            if (altDir.exists()) {
                sourceDir = altDir;
                logger.info("Found report at alternative location: " + altDir.getAbsolutePath());
            } else {
                logger.error("❌ No Allure report found. Skipping export.");
                return;
            }
        }

        // Format date folder and timestamp
        String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String timestamp = new SimpleDateFormat("HHmmss").format(new Date());

        // Create destination folder: reports/yyyy-MM-dd/suiteName_HHmmss/
        Path datedReportDir = REPORT_DIR.resolve(dateFolder);
        String reportFolderName = suiteName + "_" + timestamp;
        Path destinationDir = datedReportDir.resolve(reportFolderName);

        createDirectoryIfNotExist(destinationDir);

        // Copy entire report directory to destination
        logger.info("Copying report from: " + sourceDir.getAbsolutePath());
        logger.info("Copying report to: " + destinationDir.toAbsolutePath());
        FileUtils.copyDirectory(sourceDir, destinationDir.toFile());
        logger.info("✅ Report copied successfully!");

        // Create index.html in the dated folder for easy access
        createReportIndex(datedReportDir, reportFolderName);
        
        // Copy to latest folder for GitHub
        copyToLatestFolder(sourceDir, reportFolderName);
    }

    /**
     * Copies the report to the 'latest' folder for GitHub tracking.
     */
    private static void copyToLatestFolder(File sourceDir, String reportFolderName) throws IOException {
        Path latestDir = REPORT_DIR.resolve("latest");
        
        // Delete old latest folder if exists
        if (latestDir.toFile().exists()) {
            FileUtils.deleteDirectory(latestDir.toFile());
            logger.info("Removed old 'latest' report folder");
        }
        
        // Create new latest folder
        createDirectoryIfNotExist(latestDir);
        
        // Copy report to latest
        FileUtils.copyDirectory(sourceDir, latestDir.toFile());
        logger.info("✅ Report copied to 'latest' folder for GitHub tracking");
        
        // Create README in latest folder
        createLatestReadme(latestDir, reportFolderName);
    }
    
    /**
     * Creates a README.md in the latest folder with report information.
     */
    private static void createLatestReadme(Path latestDir, String reportFolderName) throws IOException {
        Path readmePath = latestDir.resolve("README.md");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
        String readmeContent = "# Latest Allure Test Report\n\n" +
                "**Generated:** " + timestamp + "\n\n" +
                "**Report Name:** " + reportFolderName + "\n\n" +
                "## How to View\n\n" +
                "Open `index.html` in your browser to view the report.\n\n" +
                "## Note\n\n" +
                "This folder contains only the latest test execution report. " +
                "Historical reports are stored locally in `reports/yyyy-MM-dd/` folders.\n";
        
        Files.write(readmePath, readmeContent.getBytes(StandardCharsets.UTF_8));
        logger.info("README.md created in 'latest' folder");
    }

    /**
     * Creates an index.html file in the dated report folder for easy navigation.
     */
    private static void createReportIndex(Path datedReportDir, String reportFolderName) throws IOException {
        Path indexPath = datedReportDir.resolve("index.html");
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Allure Reports</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n" +
                "        .container { max-width: 800px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n" +
                "        h1 { color: #333; border-bottom: 2px solid #007bff; padding-bottom: 10px; }\n" +
                "        .report-link { display: inline-block; margin: 10px 0; padding: 10px 15px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; }\n" +
                "        .report-link:hover { background-color: #0056b3; }\n" +
                "        .timestamp { color: #666; font-size: 12px; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Allure Test Reports</h1>\n" +
                "        <p><a class=\"report-link\" href=\"" + reportFolderName + "/index.html\" target=\"_blank\">📊 View Latest Report</a></p>\n" +
                "        <p class=\"timestamp\">Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        Files.write(indexPath, htmlContent.getBytes(StandardCharsets.UTF_8));
        logger.info("Index file created at: " + indexPath.toAbsolutePath());
    }

    /**
     * Creates a directory if it does not already exist.
     */
    public static void createDirectoryIfNotExist(Path path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null!");
        }
        if (Files.notExists(path)) {
            Files.createDirectories(path);
            logger.info(String.format("Created directory %s", path));
        }
    }

    /**
     * Clean up last Allure Result to generate a report correctly
     */
    public static void deleteAllureResult() {
        try {
            if (RESULT_DIR.toFile().exists()) {
                FileUtils.deleteDirectory(RESULT_DIR.toFile());
                logger.info("Removed old report");
            }
        } catch (IOException e) {
            logger.warn("Failed to delete Allure results: " + e.getMessage());
        }
    }

    /**
     * Writes environment variables to Allure's environment.properties file.
     * Implement this method to log details like OS, browser, etc.
     */
    private static void writeEnvironment() throws IOException {
        createDirectoryIfNotExist(RESULT_DIR);

        Properties props = new Properties();

        String browser = ConfigReader.BROWSER_VERSION;
        String host = ConfigReader.HOST_NAME;
        String os = ConfigReader.OS_NAME;

        if (browser != null) props.setProperty("Browser Version", browser);
        if (host != null) props.setProperty("Host Name", host);
        if (os != null) props.setProperty("Local OS", os);

        Path envFile = RESULT_DIR.resolve("environment.properties");
        try (BufferedWriter writer = Files.newBufferedWriter(envFile, StandardCharsets.UTF_8)) {
            props.store(writer, "Allure Environment Properties");
        }
    }
}
