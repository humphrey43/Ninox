package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamRoot

class NinoxTable extends NinoxCatalogObject {
	Map<String, NinoxField> fields = new LinkedHashMap<>()
	NinoxDatabase database
	
	NinoxTable(JetstreamDatabase jetstreamDatabase, NinoxDatabase database, String _name, String _id) {
		super(jetstreamDatabase, _name, _id)
		this.database = database
		database.tables[_name] = this
		database.tablesId[_id] = this
		database.save(jetstreamDatabase)
	}
	
	String collectPath() {
		return database.collectPath() + '/tables/' + _id
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(fields)
    	super.saveDB(instance);
	}
}
