package de.pch.ninox

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.rest.SslRest
import de.pch.jetstreamdb.JetstreamDBHandler
import net.sf.json.JSON
import net.sf.json.groovy.JsonSlurper

class NinoxConnection {

	public static final String NINOX_TEAM = "NinoxTeam"
	public static final String NINOX_DATABASE = "NinoxDatabase"
	public static final String NINOX_TABLE = "NinoxTable"
	public static final String NINOX_FIELD = "NinoxField"
	
	String apiKey = ''
	String ninoxUrl = ''
	SslRest sslRest = null

	Properties properties = null;

	NinoxConnection() {
		apiKey = readProperty("APIKEY")
		ninoxUrl = 'https://api.ninoxdb.de/v1/'
	}
	
	JetstreamDatabase useDatabase(String dbName) {
		return JetstreamDBHandler.useDatabase(dbName);
	}
	
	JetstreamDatabase useDatabase(String dbName, boolean clear) {
		return JetstreamDBHandler.useDatabase(dbName, clear)
	}
	
//	readDatabase
	void readMetamodel(String teamName, String databaseName) {
		
		JetstreamDatabase metaDatabase = useDatabase(teamName)
		metaDatabase.startTransaction()
		
		// get team (no changes possible, so no oldteam)
		String path = 'teams'
		NinoxTeam team = metaDatabase.getObject(NINOX_TEAM, teamName)
		JSON jsonData = readNinox(path)
		jsonData.each {jsonTeam ->
			if (jsonTeam.name == teamName) {
				if (team == null) {
					team = new NinoxTeam(metaDatabase, jsonTeam.name, jsonTeam.id)
				}
				return true     // break
			} 
			return false
		}

		// read databases
		if (team != null) {
			path = path + '/' + team._id + '/databases'
			jsonData = readNinox(path)
			jsonData.find {jsonDatabase ->
				if (jsonDatabase.name == databaseName) {
					NinoxDatabase database = team.databases[databaseName]
					if (database == null) {
						database = new NinoxDatabase(metaDatabase, team, jsonDatabase.name, jsonDatabase.id)
					}
					
					// readTables
					path = path + '/' + database._id + '/tables'
					jsonData = readNinox(path)
					jsonData.each {jsonTable ->
						NinoxTable table = database.tablesId[jsonTable.id]
						if (table == null) {
							table = new NinoxTable(metaDatabase, database, jsonTable.name, jsonTable.id)
						} else {
						}
						
						// readFields
						jsonTable.fields.each {jsonField -> 
							NinoxField field = new NinoxField(metaDatabase, table, jsonField.name, jsonField.id)
							field.type = jsonField.type
							if (field.type == 'ref') {
								field.referencedTableId = jsonField.referenceToTable
								field.reverseFieldId = jsonField.reverseField
							}
						}
					}
					
					// link referencedTables
					database.tables.forEach {String tableName, NinoxTable table ->
						table.fields.forEach {String fieldName, NinoxField field ->
							if (field.type == 'ref') {
								NinoxTable refTable = database.tablesId[field.referencedTableId]
								field.referencedTable = refTable
								System.out.println tableName + '.' + fieldName + ' -> ' + refTable._name
							}
						}
					}
					
					return true	// break
				}
				return false
			}
			int i = 42
		}
		metaDatabase.endTransaction()
	}
	
	void readTableContent(String tableName) {
		NinoxTable table = tables[tableName]
		if (table != null) {
			readTableContent(tables[tableName])
		}
	}
	
	void readTableContent(NinoxTable table) {
		String path = table.collectPath() + '/records'
		JSON jsonData = readNinox(path)
	}

	JSON readNinox(String path) {
		assertSslRest()
		String json = sslRest.getJson(path)
		System.out.println json
			
		def jsonSlurper = new JsonSlurper()
		def jsonData = jsonSlurper.parseText(json)
		return jsonData
	}
	
	String readProperty(String key) {
		if (properties == null) {
			properties = new Properties();
			properties.load(new FileInputStream(System.getProperty("user.home") + '/NinoxConnection.properties'))
		}
		return properties[key]
	}
	void assertSslRest() {
		if (sslRest == null) {
			sslRest = new SslRest()
			sslRest.token = apiKey
			sslRest.basicUrl = ninoxUrl
		}
	}
}
