package org.projectbuendia.sqlite;

/**
 * @author Pim de Witte
 */
public final class SQLiteUpdate implements SQLiteItem {
	private final String sql;

	public SQLiteUpdate(String sql) {
		this.sql = sql;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public final boolean execute(SQLiteConnection connection) {
		long startTime = System.currentTimeMillis();
		int result = connection.executeUpdate(sql);
		if ((System.currentTimeMillis() - startTime) >= 5000) {
			System.err.println("Update took: " + (System.currentTimeMillis() - startTime));
		}
		if (result == -1) {
			return false;
		}
		if (result == -2) {
			return true;
		}
		return true;
	}
}
