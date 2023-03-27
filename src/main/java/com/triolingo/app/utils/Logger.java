package com.triolingo.app.utils;

public class Logger {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";

	private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


	private static void printLine(String text, String lvl, String col) {
		lvl += "]";
		System.out.printf("%s[%7s %s%s\n", col, lvl, text, ANSI_RESET);
	}

	public static void logDebug(String text) {
		printLine(text, "Debug", ANSI_CYAN);
	}

	public static void logInfo(String text) {
		printLine(text, "Info", ANSI_RESET);
	}

	public static void logWarn(String text) {
		printLine(text, "Warn", ANSI_YELLOW);
	}

	public static void logError(String text) {
		printLine(text, "Error", ANSI_RED);
	}

	public static void logCrit(String text) {
		printLine(text, "!Crit", ANSI_RED_BACKGROUND + ANSI_BLACK);
	}
}
