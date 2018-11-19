package de.pch.ninox.test
;

import com.jetstreamdb.JetstreamDBInstance
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.ninox.NinoxConnection
import net.sf.json.groovy.JsonSlurper

class Test2 {

	static main(args) {
		NinoxConnection connection = new NinoxConnection()
		connection.apiKey = '772a6480-d2e9-11e8-a659-e78cc785609a'
		connection.ninoxUrl = 'https://api.ninoxdb.de/v1/'
		
//		JetstreamDBInstance<LocalMetaDatabase> jetstreamMetaDB = JetstreamDBInstance.New(LocalMetaDatabase.class)
//		jetstreamMetaDB.properties().setStorageChannelCount(4)
//		jetstreamMetaDB.properties().setStorageDataFileEvaluatorMaxFileSize(2000000) //(int) SizeUnit.GB.toBytes(2))
//		LocalMetaDatabase localMetaDB = jetstreamMetaDB.root();
//		
//		connection.readMetamodel(localMetaDB, 'ITTeam', 'ITRepository')
//		connection.readMetamodel(localMetaDB, 'ITTeam', 'Rezepte')
//		
//		connection.generateDatabaseModel(localMetaDB, 'ITRepository', 'C:/WorkspaceNeon/de.pch.ninox', 'de.pch.dataobject.itrepository')
//		
//		connection.readTableContent("Program")
//		
		def i = 42
	}
}
