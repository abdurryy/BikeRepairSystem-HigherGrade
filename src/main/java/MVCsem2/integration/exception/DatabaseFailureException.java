package MVCsem2.integration.exception;

/**
 * Thrown when the database cannot be reached or a connection failure occurs.
 * This is used to simulate a database server that is not running.
 */
public class DatabaseFailureException extends RuntimeException {
    private final String searchValue;

    /**
     * Creates a new instance specifying which search value triggered the failure.
     *
     * @param searchValue The value that was being searched for when the database failed.
     */
    public DatabaseFailureException(String searchValue) {
        super("The database is currently unreachable while searching for: " + searchValue);
        this.searchValue = searchValue;
    }

    /**
     * Retrieves the search value that was used when the failure occurred.
     *
     * @return The value used during the failed database call.
     */
    public String getSearchValue() {
        return searchValue;
    }
}
