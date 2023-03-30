package com.triolingo.app.data.sql.attributes;

import com.triolingo.app.data.sql.attributes.data.SQLDateTime;

public class SQLDateTimeAttribute extends SQLAttribute<SQLDateTime>{
	public String parseToString()
	{
		return super.getData().getDateTime();
	}
}
