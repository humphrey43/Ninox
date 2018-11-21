package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance

import de.pch.jetstreamdb.JetstreamRoot

class NinoxTeam extends NinoxCatalogObject {
	Map<String, NinoxDatabase> databases = [:]
	
	String collectPath() {
		return 'teams/' + id
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(databases)
    	super.saveDB(instance);
	}
}
