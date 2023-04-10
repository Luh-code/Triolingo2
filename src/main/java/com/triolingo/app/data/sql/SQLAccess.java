package com.triolingo.app.data.sql;

import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.query.SQLQuery;
import com.triolingo.app.utils.Logger;

import java.sql.*;
import java.util.function.Consumer;


public class SQLAccess {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public boolean initSQLBackend(SQLInfo data)
	{
		Logger.logInfo("Establishing connection to MySQL server...");
		try
		{
			 this.connection = DriverManager.getConnection(data.getUrl(), data.getUsername(), data.getPassword());
		} catch (SQLTimeoutException e)
		{
			Logger.logError("Connection timed out!");
			throw new RuntimeException(e); //return false;
		} catch (SQLException e)
		{
			Logger.logError("Connection attempt failed!");
			throw new RuntimeException(e); //return false;
		}

		Logger.logInfo("Connection attempt successful!");

		try {
			this.statement = this.connection.createStatement();
		} catch (SQLException e) {
			Logger.logError("Statement could not be created!");
			throw new RuntimeException(e); //return false;
		}

		return true;
	}

	public Connection getConnection() {
		return connection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public boolean insert(SQLInsertion insertion)
	{
		PreparedStatement ps = insertion.generateStatement(this);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(String.format("Insertion statement failed! Template: %s", insertion.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean delete(SQLDeletion deletion)
	{
		PreparedStatement ps = deletion.generateStatement(this);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(String.format("Query statement failed! Template: %s", deletion.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean query(SQLQuery query)
	{
		PreparedStatement ps = query.generateStatement(this);
		try {
			this.resultSet = ps.executeQuery();
		} catch (SQLException e) {
			Logger.logError(String.format("Query statement failed! Template: %s", query.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean update(SQLUpdate update)
	{
		PreparedStatement ps = update.generateStatement(this);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(String.format("Update statement failed! Template: %s", update.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean acuteQuery(String prompt)
	{
		try {
			this.resultSet = statement.executeQuery(prompt);
		} catch (SQLException e) {
			Logger.logError(String.format("Query statement failed! Prompt: %s", prompt));
			throw new RuntimeException(e); // return false;
		}
		return true;
	}

	public void printResultSet(ResultSet resultSet, SQLTableLayout layout, Consumer<String> printer)
	{
		try {
			while (resultSet.next())
			{
				for (int i = 0; i < layout.size(); ++i)
				{
					SQLAttributeType type = layout.getTypeAt(i);
					printer.accept(String.format("%s: %s", layout.getAliasAt(i), type.toString(type.getObjFromResultSet(resultSet, i + 1))));
				}
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e); // return;
		}
	}
}
