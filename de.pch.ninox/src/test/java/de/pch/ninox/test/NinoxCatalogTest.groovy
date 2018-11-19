package de.pch.ninox.test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNull

import org.junit.Test

import de.pch.jetstreamdb.Customer
import de.pch.jetstreamdb.JetstreamDBHandler
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamObject
import de.pch.ninox.NinoxConnection

public class NinoxCatalogTest {

//	private JetstreamDBInstance<DataRoot> dbinstance
	protected JetstreamDatabase metaDatabase = null
	protected JetstreamDatabase dataDatabase = null
	public final static String DBNAME_META = 'ITTeam'
	public final static String DBNAME_DATA = 'ITRepository'
	
//	@Test
	public void Test1() {
		
//		connection.readMetamodel(localMetaDB, 'ITTeam', 'ITRepository')
		NinoxConnection connection = new NinoxConnection()
		connection.apiKey = '772a6480-d2e9-11e8-a659-e78cc785609a'
		connection.ninoxUrl = 'https://api.ninoxdb.de/v1/'
		
		// provide fresh database
		metaDatabase = connection.useDatabase(DBNAME_META, true)

		connection.readMetamodel(DBNAME_META, DBNAME_DATA)
		
		// read customer from database, should be null
		database.startTransaction()
		Customer customer = (Customer) database.getObjects(CUSTOMER).get("Doe")
		assertNull(customer)
	
		// create first customer
		customer = new Customer()
		customer.setFirstname("John")
		customer.setLastname("Doe")
		customer.setAge(26)
		customer.setValue("Hobby", "Surfing")
		database.getObjects(CUSTOMER).put(customer.getLastname(), customer)
		database.setDatabaseChanged()
		database.setObjectChanged(customer)
		
		// save it and close database
		database.endTransaction()
		
		database.closeDatabase()

		// open database again
		database = JetstreamDBHandler.useDatabase(DBNAME)
		
		// read customer from database, should exist now
		Map<String, JetstreamObject> customers = database.getObjects(CUSTOMER)
		customer = (Customer) database.getObjects(CUSTOMER).get("Doe")
		assertNotNull(customer)
		assertEquals("Doe", customer.getLastname())
		assertEquals("John", customer.getFirstname())
		assertEquals(26, customer.getAge())
		assertEquals("Surfing", customer.getValue("Hobby"))
		
		// change some values in place -> no new object
		customer.setFirstname("Mary")
		customer.setAge(17)
		assertEquals("Mary", customer.getFirstname())
		assertEquals(17, customer.getAge())
		customer.setValue("Hobby", "Biking")
		customer.setValue("Job", "Consultant")
		customer.save(database)
		
		database.closeDatabase()

		// open database again
		database = JetstreamDBHandler.useDatabase(DBNAME)
		
		// read customer from database, should exist
		customer = (Customer) database.getObjects(CUSTOMER).get("Doe")
		assertNotNull(customer)
		assertEquals("Doe", customer.getLastname())
		assertEquals("Mary", customer.getFirstname())
		assertEquals(17, customer.getAge())
		assertEquals("Consultant", customer.getValue("Job"))
		assertEquals("Biking", customer.getValue("Hobby"))
		
		// finally close database
		database.closeDatabase()
	}
}
