package tdt4140.gr1878.app.ui.db;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A utility class that parses sql files into strings.
 */
public class SqlQuery2 {
	
	private SqlQuery2() {} // Do not instantiate this
	
	/**
	 * A private helper method that fetches the file contents but strips away mysql comments
	 * and newlines.
	 * 
	 * @param path a path to the resource
	 * @return a stringbuilder instance with the file contents, minus comments and newlines
	 * @throws IOException
	 */
	private static StringBuilder fetchSql(String path) throws IOException {
		if (path.charAt(0) != '/') {
			throw new IllegalArgumentException("Path does not start with /, was: "+path);
		}
		InputStream is = SqlQuery2.class.getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			line = line.split("--")[0]; // -- is used as comment, multi-line-comment /* */ yet to be supported
			sb.append(line.trim());
			sb.append(' ');
		}
		return sb;
	}

	/**
	 * Returns the mysql query within the given file as a single line.
	 * 
	 * <p>Example usage:
	 * <blockquote>
	 * <code>
	 * String myQuery = {@link SqlQuery2}.parseSqlFile("/myquery.sql");
	 * </code>
	 * </blockquote>
	 * 
	 * @param path the path to the sql resource
	 * @return a single-line mysql query
	 * @throws IOException
	 */
	public static String parseSqlFile(String path) throws IOException {
		StringBuilder sb = fetchSql(path);
		return sb.toString();
	}
	
	/**
	 * Returns the mysql query within the given file as a single line, with each instance of
	 * <code>?</code> replaced with the object parameters.
	 * 
	 * <p>Uses {@link String#format(String, Object...)} internally.
	 * 
	 * <p>Example usage:
	 * <blockquote>
	 * <code>
	 * String myQuery = {@link SqlQuery2}.parseSqlFileWithParameters("/myquery.sql", 5, "Petter Hanssen");
	 * </code>
	 * </blockquote>
	 * 
	 * @param path the path to the sql resource
	 * @param objects arguments to invoke .toString upon, when encountering a <code>?</code> character
	 * @return a single-line mysql query with instances of <code>?</code> replaced
	 * @throws IOException
	 */
	public static String parseSqlFileWithParameters(String path, Object... objects) throws IOException {
		StringBuilder sb = fetchSql(path);
		String query = sb.toString();
		query = query.replaceAll("\\?", "%s");
		query = String.format(query, objects);
		return query;
	}

}
