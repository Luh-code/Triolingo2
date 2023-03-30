package com.triolingo.app.data.sql;

import java.sql.PreparedStatement;

public interface SQLOperation {
	public String generateStatementTemplate();
	public PreparedStatement generateStatement(SQLAccess access);
}
