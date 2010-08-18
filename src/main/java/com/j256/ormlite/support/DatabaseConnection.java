package com.j256.ormlite.support;

import java.sql.SQLException;
import java.sql.Savepoint;

public interface DatabaseConnection {

	/**
	 * Return whether this database supports savepoints.
	 */
	public boolean isSupportsSavepoints() throws SQLException;

	/**
	 * Return if auto-commit is currently enabled.
	 */
	public boolean getAutoCommit() throws SQLException;

	/**
	 * Set the auto-commit to be on (true) or off (false).
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException;

	/**
	 * Set a save point with a certain name.
	 * 
	 * @return A SavePoint object with which we can release or commit in the future.
	 */
	public Savepoint setSavepoint(String name) throws SQLException;

	/**
	 * Commit all outstanding changes.
	 */
	public void commit() throws SQLException;

	/**
	 * Release a savepoint commiting any changes since the savepoint was created.
	 */
	public void releaseSavepoint(Savepoint savepoint) throws SQLException;

	/**
	 * Roll back all outstanding changes.
	 */
	public void rollback() throws SQLException;

	/**
	 * Roll back all changes since the savepoint was created.
	 */
	public void rollback(Savepoint savepoint) throws SQLException;

	/**
	 * Prepare a SQL statement.
	 */
	public PreparedStmt prepareStatement(String sql) throws SQLException;

	/**
	 * Prepare a SQL statement that returns auto-generated key information.
	 */
	public PreparedStmt prepareStatementWithAutoGeneratedKeys(String sql) throws SQLException;

	/**
	 * Prepare a SQL statement that returns a single result.
	 */
	public PreparedStmt prepareStatementForOneResult(String sql) throws SQLException;

	/**
	 * Close the connection to the database.
	 */
	public void close() throws SQLException;
}
