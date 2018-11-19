package de.pch.jetstreamdb.test

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamObject
import de.pch.jetstreamdb.JetstreamDBHandler
import java.util.Map;
import org.junit.Test;

public class JetstreamTest {

//	private JetstreamDBInstance<DataRoot> dbinstance;
	private JetstreamDatabase database = null;
	public final static String DBNAME = "customer-db";
	public final static String CUSTOMER = "CUSTOMER";
	
	@Test
	public void Test1() {
		
		// provide fresh database
		database = JetstreamDBHandler.useDatabase(DBNAME, true);

		// read customer from database, should be null
		database.startTransaction();
		Customer customer = (Customer) database.getObjects(CUSTOMER).get("Doe");
		assertNull(customer);
	
		// create first customer
		customer = new Customer();
		customer.setFirstname("John");
		customer.setLastname("Doe");
		customer.setAge(26);
		customer.setValue("Hobby", "Surfing");
		database.getObjects(CUSTOMER).put(customer.getLastname(), customer);
		database.setDatabaseChanged();
		database.setObjectChanged(customer);
		
		// save it and close database
		database.endTransaction();
		
		database.closeDatabase();

		// open database again
		database = JetstreamDBHandler.useDatabase(DBNAME);
		
		// read customer from database, should exist now
		Map<String, JetstreamObject> customers = database.getObjects(CUSTOMER);
		customer = (Customer) database.getObjects(CUSTOMER).get("Doe");
		assertNotNull(customer);
		assertEquals("Doe", customer.getLastname());
		assertEquals("John", customer.getFirstname());
		assertEquals(26, customer.getAge());
		assertEquals("Surfing", customer.getValue("Hobby"));
		
		// change some values in place -> no new object
		customer.setFirstname("Mary");
		customer.setAge(17);
		assertEquals("Mary", customer.getFirstname());
		assertEquals(17, customer.getAge());
		customer.setValue("Hobby", "Biking");
		customer.setValue("Job", "Consultant");
		customer.save(database);
		
		database.closeDatabase();

		// open database again
		database = JetstreamDBHandler.useDatabase(DBNAME);
		
		// read customer from database, should exist
		customer = (Customer) database.getObjects(CUSTOMER).get("Doe");
		assertNotNull(customer);
		assertEquals("Doe", customer.getLastname());
		assertEquals("Mary", customer.getFirstname());
		assertEquals(17, customer.getAge());
		assertEquals("Consultant", customer.getValue("Job"));
		assertEquals("Biking", customer.getValue("Hobby"));
		
		// finally close database
		database.closeDatabase();
	}
}
