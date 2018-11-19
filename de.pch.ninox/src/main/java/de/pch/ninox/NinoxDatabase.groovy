package de.pch.ninox

import de.pch.jetstreamdb.JetstreamObject
import de.pch.jetstreamdb.JetstreamRoot

class NinoxDatabase extends NinoxCatalogObject {
	NinoxTeam team = null
	Map<String, NinoxTable> tables = [:]
	Map<String, NinoxTable> tablesId = [:]
	
	NinoxDatabase(JetstreamDatabase database, NinoxTeam team, String databaseName) {
		this.team = team
		team.databases[databaseName] = this
		instance.setObject(databaseName, this)
		instance.setDatabaseChanged()
	}
	
	String collectPath() {
		return team.collectPath() + '/databases/' + id
	}
	
	@Override
	protected void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(tables)
    	instance.store(tablesId)
    	super.saveDB(instance);
	}
}
