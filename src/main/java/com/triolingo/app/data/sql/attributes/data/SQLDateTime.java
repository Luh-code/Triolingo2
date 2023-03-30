package com.triolingo.app.data.sql.attributes.data;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;

public class SQLDateTime {
	private java.sql.Date date;

	private java.sql.Time time;

	public SQLDateTime(Date date, Time time) {
		this.date = date;
		this.time = time;
	}

	public SQLDateTime()
	{
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		this.date = Date.valueOf(currentTime);
		this.time = Time.valueOf(currentTime);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDateTime()
	{
		return String.format("%s %s", date.toString(), time.toString());
	}
}
