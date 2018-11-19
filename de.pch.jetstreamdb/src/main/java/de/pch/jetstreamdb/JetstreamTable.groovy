package de.pch.jetstreamdb

class JetstreamTable {
	
	Map<String, JetstreamObject> datatable = new LinkedHashMap<>()
	String tableName
	
	JetstreamTable(String tableName) {
		this.tableName = tableName
	}
}
