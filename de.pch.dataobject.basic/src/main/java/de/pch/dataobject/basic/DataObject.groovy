package de.pch.dataobject.basic

import com.jetstreamdb.JetstreamDBInstance

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamObject
import de.pch.jetstreamdb.JetstreamRoot
import de.pch.repository.RepositoryObject

abstract class DataObject implements JetstreamObject, RepositoryObject {

	@Override
	void save(JetstreamDatabase database) {
		database.setObjectChanged(this)
	}

	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
		instance.store(this)
	}
}
