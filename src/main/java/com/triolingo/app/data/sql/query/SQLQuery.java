package com.triolingo.app.data.sql.query;

import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLOperation;

import java.sql.PreparedStatement;

public class SQLQuery implements SQLOperation {

	protected SQLQuery() {}

	@Override
	public String generateStatementTemplate() {
		return null;
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access) {
		return null;
	}
}
