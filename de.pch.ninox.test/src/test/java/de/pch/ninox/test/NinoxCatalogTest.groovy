package de.pch.ninox.test

import de.pch.jetstreamdb.JetstreamDBHandler
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamObject
import de.pch.ninox.NinoxConnection
import org.junit.Test

public class NinoxCatalogTest {

//	private JetstreamDBInstance<DataRoot> dbinstance
	protected JetstreamDatabase metaDatabase = null
	protected JetstreamDatabase dataDatabase = null
	public final static String DBNAME_META = 'ITTeam'
	public final static String DBNAME_DATA = 'ITRepository'
	
	@Test
	public void Test1() {
		
//		connection.readMetamodel(localMetaDB, 'ITTeam', 'ITRepository')
		NinoxConnection connection = new NinoxConnection()
		
		// provide fresh database
		metaDatabase = connection.useDatabase(DBNAME_META, true)

		connection.readMetamodel(DBNAME_META, DBNAME_DATA)
		
		metaDatabase.closeDatabase()
		
	}
}
