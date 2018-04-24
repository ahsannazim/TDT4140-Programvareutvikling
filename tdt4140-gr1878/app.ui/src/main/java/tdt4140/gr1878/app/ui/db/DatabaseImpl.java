package tdt4140.gr1878.app.ui.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.function.Consumer;
import javax.sql.rowset.CachedRowSet;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.rowset.CachedRowSetImpl;

/**
 * An implementation of the {@link tdt4140.gr1878.app.core.db.Database Database} interface.
 */
public class DatabaseImpl implements Database {
	
	private String serverName;
	private String databaseName;
	private String userName;
	private String password;
	
	private MysqlDataSource datasource = null;
	
	/**
	 * Creates a new database object.
	 * 
	 * <p>The properties passed into this constructor must contain the properties
	 * <em>db.serverName</em>, <em>db.dbName</em>, <em>db.username</em>, and <em>db.password</em>,
	 * corresponding to the database URL, database name, database account name, and database
	 * account password, respectively.
	 * 
	 * @param props properties containing the connection information
	 */
	public DatabaseImpl(Properties props) {
		readProperties(props);
	}
	
	private void readProperties(Properties props) {
		this.serverName = props.getProperty("db.serverName");
		this.databaseName = props.getProperty("db.dbName");
		this.userName = props.getProperty("db.username");
		this.password = props.getProperty("db.password");
	}
	
	public DatabaseImpl(String serverName, String databaseName, String userName, String password) {
	this.serverName = serverName;
	this.databaseName = databaseName;
	this.userName = userName;
	this.password = password;
}
	private MysqlDataSource getDataSource() {
		if (datasource == null) {
			this.datasource = new MysqlDataSource();
			datasource.setServerName(serverName);
			//dataSource.setPort(3306); // Default 3306
			datasource.setDatabaseName(databaseName);
			datasource.setUser(userName);
			datasource.setPassword(password);
		}
		return datasource;
	}

	@Override
	public synchronized CachedRowSet query(String query) {
		MysqlDataSource ds = getDataSource();
		CachedRowSet crs = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try (Connection conn = ds.getConnection()){
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null)
				try {rs.close();} catch (SQLException e) {}
			if (stmt!=null)
				try {stmt.close();} catch (SQLException e) {}
		}
		
		return crs;
	}

	@Override
	public synchronized void consume_query(String query, Consumer<ResultSet> consumer) {
		MysqlDataSource ds = getDataSource();
		Statement stmt = null;
		ResultSet rs = null;
		
		try (Connection conn = ds.getConnection()){
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			consumer.accept(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null)
				try {rs.close();} catch (SQLException e) {}
			if (stmt!=null)
				try {stmt.close();} catch (SQLException e) {}
		}
	}

	@Override
	public synchronized void query_update(String query) {
		MysqlDataSource ds = getDataSource();
		Statement stmt = null;
		
		try (Connection conn = ds.getConnection()){
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt!=null)
				try {stmt.close();} catch (SQLException e) {}
		}
	}

}
