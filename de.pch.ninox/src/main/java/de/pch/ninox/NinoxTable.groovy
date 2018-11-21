package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance

import de.pch.jetstreamdb.JetstreamRoot

class NinoxTable extends NinoxCatalogObject {
	Map<String, NinoxField> fields = new LinkedHashMap<>()
	NinoxDatabase database
	
	String collectPath() {
		return database.collectPath() + '/tables/' + id
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(fields)
    	super.saveDB(instance);
	}
}
