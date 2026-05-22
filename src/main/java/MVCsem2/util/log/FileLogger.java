package MVCsem2.util.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes log messages to a file in the current working directory.
 */
public class FileLogger implements Logger {
    private static final String LOG_FILE_NAME = "bike-repair-error-log.txt";
    private PrintWriter logStream;

    /**
     * Creates a logger and opens the log file.
     */
    public FileLogger() {
        this(LOG_FILE_NAME);
    }

    /**
     * Creates a logger and opens the specified log file.
     */
    public FileLogger(String fileName) {
        try {
            logStream = new PrintWriter(new FileWriter(fileName, true), true);
        } catch (IOException ioe) {
            System.err.println("Could not open log file: " + fileName);
            ioe.printStackTrace(System.err);
        }
    }

    /**
     * Writes the specified message to the log file.
     *
     * @param message The message that should be logged.
     */
    @Override
    public void log(String message) {
        if (logStream != null) {
            logStream.println(message);
        }
    }
}
