package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance
import de.pch.jetstreamdb.JetstreamRoot

class NinoxDatabase extends NinoxCatalogObject {
	NinoxTeam team = null
	Map<String, NinoxTable> tables = [:]
	Map<String, NinoxTable> tablesId = [:]
	
	NinoxDatabase(JetstreamDBInstance<JetstreamRoot> instance, NinoxTeam team, String databaseName) {
		super()
		this.team = team
		team.databases[databaseName] = this
		instance.setObject(databaseName, this)
		instance.setDatabaseChanged()
	}
	
	String collectPath() {
		return team.collectPath() + '/databases/' + id
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(tables)
    	instance.store(tablesId)
    	super.saveDB(instance);
	}
}
