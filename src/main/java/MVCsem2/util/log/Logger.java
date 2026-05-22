package MVCsem2.util.log;

/**
 * Defines an object that can write messages to a log.
 */
public interface Logger {
    /**
     * Writes the specified message to the log.
     *
     * @param message The message that shall be logged.
     */
    void log(String message);
}