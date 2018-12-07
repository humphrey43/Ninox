package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamRoot

class NinoxDatabase extends NinoxCatalogObject {
	NinoxTeam team = null
	Map<String, NinoxTable> tables = [:]
	Map<String, NinoxTable> tablesId = [:]
	
	NinoxDatabase(JetstreamDatabase jetstreamDatabase, NinoxTeam team, String _name, String _id) {
		super(jetstreamDatabase, _name, _id)
		this.team = team
		team.databases[_name] = this
		team.save(jetstreamDatabase)
	}
	
	String collectPath() {
		return team.collectPath() + '/databases/' + _id
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(tables)
    	instance.store(tablesId)
    	super.saveDB(instance);
	}
}
