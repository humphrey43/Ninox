package de.pch.jetstreamdb

class JetstreamRoot {
	String databaseName = "";
	boolean databaseChanged = false;
	Map<String, JetstreamTable> tables = null;
	
	JetstreamRoot() {
		tables = new LinkedHashMap<>();
	}
}
