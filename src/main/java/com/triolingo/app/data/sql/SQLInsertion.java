package com.triolingo.app.data.sql;

import com.triolingo.app.data.sql.attributes.*;
import com.triolingo.app.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLInsertion implements SQLOperation {
	private String schema;
	private String table;

	private SQLAttributeBase[] attributes;

	public SQLInsertion(String schema, String table, SQLAttributeBase[] attributes) {
		this.schema = schema;
		this.table = table;
		this.attributes = attributes;
	}

	@Override
	public String generateStatementTemplate()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("insert into %s.%s values (default", this.schema, this.table));
		for (SQLAttributeBase a :
			attributes) {
			sb.append(", ?");
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access)
	{
		PreparedStatement ps;
		try {
			ps = access.getConnection().prepareStatement(generateStatementTemplate());
		} catch (SQLException e) {
			Logger.logError("PreparedStatement could not be generated!");
			throw new RuntimeException(e); // return null;
		}

		for (int i = 0; i < attributes.length; ++i) {
			SQLAttributeBase a = attributes[i];
			if (a instanceof SQLIntAttribute)
				SQLAttributeType.INT.setPreparedStatement(ps, i+1, ((SQLIntAttribute) a).getData());
				//ps.setInt(i+1, ((SQLIntAttribute) a).getData());
			else if (a instanceof SQLStringAttribute)
				SQLAttributeType.STRING.setPreparedStatement(ps, i+1, ((SQLStringAttribute) a).getData());
				//ps.setString(i+1, ((SQLStringAttribute) a).getData());
			else if (a instanceof SQLDateTimeAttribute)
				SQLAttributeType.DATETIME.setPreparedStatement(ps, i+1, ((SQLDateTimeAttribute) a).getData());
				//ps.setString(i+1, ((SQLDateTimeAttribute) a).parseToString());
			else
			{
				Logger.logError("Attribute type not recognized!");
				throw new RuntimeException();
			}
		}

		return ps;
	}
}
