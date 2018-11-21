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
		
		// get team (no changes possible, so no oldteam)
		String path = 'teams'
		NinoxTeam team = metaDatabase.getObject(NINOX_TEAM, teamName)
		JSON jsonData = readNinox(path)
		jsonData.each {jsonTeam ->
			if (jsonTeam.name == teamName) {
				if (team == null) {
					team = new NinoxTeam()
					team.name = jsonTeam.name
					team.id = jsonTeam.id
					metaDatabase.setObject(NINOX_TEAM, teamName, team)
					metaDatabase.setDatabaseChanged()
					team.save(metaDatabase)
				}
				return true     // break
			} 
			return false
		}

		// read databases
		if (team != null) {
			path = path + '/' + team.id + '/databases'
			jsonData = readNinox(path)
			jsonData.find {jsonDatabase ->
				if (jsonDatabase.name == databaseName) {
					NinoxDatabase database = team.databases[databaseName]
					if (database == null) {
						database = new NinoxDatabase()
						database.id = jsonDatabase.id
						database.name = jsonDatabase.name
						database.team = team
						team.databases[database.name] = database
						metaDatabase.setDatabaseChanged()
						team.save(metaDatabase)
						database.save(metaDatabase)
					}
					
					// readTables
					path = path + '/' + database.id + '/tables'
					jsonData = readNinox(path)
					jsonData.each {jsonTable ->
						NinoxTable table = database.tablesId[jsonTable.id]
						if (table == null) {
							table = new NinoxTable()
							table.id = jsonTable.id
							table.name = jsonTable.name
							table.database = database
							database.tables[table.name] = table
							database.tablesId[table.id] = table
						} else {
						}
						table.id = jsonTable.id
						table.name = jsonTable.name
						table.database = database
						database.tables[table.name] = table
						database.tablesId[table.id] = table
						tables[table.name] = table
						
						// readFields
						jsonTable.fields.each {jsonField -> 
							NinoxField field = new NinoxField()
							field.id = jsonField.id
							field.name = jsonField.name
							field.type = jsonField.type
							if (field.type == 'ref') {
								field.referencedTableId = jsonField.referenceToTable
								field.reverseFieldId = jsonField.reverseField
							}
							field.table = table
							table.fields[field.name] = field
						}
					}
					
					// link referencedTables
					database.tables.forEach {String tableName, NinoxTable table ->
						table.fields.forEach {String fieldName, NinoxField field ->
							if (field.type == 'ref') {
								NinoxTable refTable = database.tablesId[field.referencedTableId]
								field.referencedTable = refTable
								System.out.println tableName + '.' + fieldName + ' -> ' + refTable.name
							}
						}
					}
					
					return true	// break
				}
				return false
			}
			int i = 42
		}
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
