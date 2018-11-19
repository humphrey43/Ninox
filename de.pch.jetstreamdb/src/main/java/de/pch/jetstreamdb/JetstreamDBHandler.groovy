package de.pch.jetstreamdb

class JetstreamDBHandler {
	static Map<String, JetstreamDatabase> databases = new LinkedHashMap<>()
	
	static JetstreamDatabase useDatabase(String name) {
		return useDatabase(name, false)
	}
	
	static JetstreamDatabase useDatabase(String name, boolean clear) {
		JetstreamDatabase database = null
		if (clear) {
			databases.remove(name)
		} else {
			database = databases[name]
		}
		if (database == null) {
			database = new JetstreamDatabase(name, clear)
			databases[name] = database
		}
		return database
	}

	static void releaseDatabase(String name) {
		databases.remove(name) 
	}
}
