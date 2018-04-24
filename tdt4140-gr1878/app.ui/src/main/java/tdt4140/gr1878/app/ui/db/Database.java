package tdt4140.gr1878.app.ui.db;

import java.sql.ResultSet;
import java.util.function.Consumer;
import javax.sql.rowset.CachedRowSet;

/**
 * An API for the connected database.
 * 
 * <p>Users of this interface must implement methods for consuming queries.
 */
public interface Database {
	
	
	/**
	 * Executes the mySql query, then caches the resultset into a {@link CachedRowSet} that
	 * can be parsed after the database connection is closed.
	 * 
	 * @param query the mysql query to be sent to the database
	 * @return a cached resultset that can be iterated over like any other resultset
	 */
	public CachedRowSet query(String query);
	
	
	/**
	 * Executes the mySql query, and passes the returned {@link ResultSet} into a consumer.
	 * 
	 * <p>The connection is not closed until the consumer returns. The connection is closed
	 * once this method returns.
	 * 
	 * @param query the mysql query to be sent to the database
	 * @param consumer the functional interface implementation that will consume the resultset
	 */
	public void consume_query(String query, Consumer<ResultSet> consumer);
	
	
	/**
	 * Sends an update query to the database, may be one of <code>INSERT</code>, <code>UPDATE</code> or
     * <code>DELETE</code>; or an SQL statement that returns nothing,
	 * 
	 * @param query a mysql query that does not ask for a result
	 */
	public void query_update(String query);
}
