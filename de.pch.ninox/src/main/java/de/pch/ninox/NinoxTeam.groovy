package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamRoot

class NinoxTeam extends NinoxCatalogObject {
	
	Map<String, NinoxDatabase> databases = [:]
	
	NinoxTeam(JetstreamDatabase jetstreamDatabase, String _name, String _id) {
		super(jetstreamDatabase, _name, _id)
	}
	
	String collectPath() {
		return 'teams/' + _id
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(databases)
    	super.saveDB(instance)
	}
}
