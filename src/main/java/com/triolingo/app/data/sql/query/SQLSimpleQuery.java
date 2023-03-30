package com.triolingo.app.data.sql.query;

import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLSimpleQuery extends SQLQuery {
	private String[] attributes;
	private String table;
	private String appendix;

	public SQLSimpleQuery(String[] attributes, String table, String appendix) {
		this.attributes = attributes;
		this.table = table;
	}

	@Override
	public String generateStatementTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (String a :
			attributes) {
			sb.append(String.format("%s,", a));
		}
		sb.insert(sb.length()-1, " ");
		sb.append(String.format("FROM %s %s", table, appendix));
		return sb.toString();
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access) {
		PreparedStatement ps;
		try {
			ps = access.getConnection().prepareStatement(generateStatementTemplate());
		} catch (SQLException e) {
			Logger.logError("PreparedStatement could not be generated!");
			throw new RuntimeException(e); // return null;
		}

		return ps;
	}
}
