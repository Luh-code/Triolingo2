package com.triolingo.app.data.sql;

import com.triolingo.app.data.sql.attributes.SQLAttributeBase;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.attributes.SQLIntAttribute;
import com.triolingo.app.data.sql.attributes.SQLStringAttribute;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import kotlin.Pair;

public class SQLTest {
	public static void main(String[] args) {
		SQLInfo info = new SQLInfo("jdbc:mysql://localhost:3306", "triolingo", "triolingodb");
		SQLAccess access = new SQLAccess();
		access.initSQLBackend(info);
//		access.insert(new SQLInsertion("triolingo", "card", new SQLAttributeBase[] {
//			new SQLStringAttribute(info, "Bottle"),
//			new SQLStringAttribute(info, "Flasche"),
//			new SQLIntAttribute(0)
//		}));
		//access.acuteQuery("SELECT * FROM triolingo.card");
		access.query(new SQLSimpleQuery(new String[] {"Word", "Translation"}, "triolingo.card", ""));
		access.printResultSet(access.getResultSet(), new SQLTableLayout(new Pair[] {
			//new Pair(SQLAttributeType.INT, "cardId"),
			new Pair(SQLAttributeType.STRING, "Word"),
			new Pair(SQLAttributeType.STRING, "Translation"),
			//new Pair(SQLAttributeType.INT, "CardSetId"),
		}));
	}
}
