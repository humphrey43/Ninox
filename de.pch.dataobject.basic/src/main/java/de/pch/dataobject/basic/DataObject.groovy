package de.pch.dataobject.basic

import com.jetstreamdb.JetstreamDBInstance
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamObject
import de.pch.jetstreamdb.JetstreamRoot
import de.pch.repository.RepositoryObject

abstract class DataObject implements JetstreamObject, RepositoryObject {

	DataObject(JetstreamDatabase jetstreamDatabase) {
		jetstreamDatabase.setDatabaseChanged()
		jetstreamDatabase.setObject(getObjectName(), getObjectKey(), this)
	}
	
	void save(JetstreamDatabase jetstreamDatabase) {
		jetstreamDatabase.setObjectChanged(this)
	}

	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
		instance.store(this)
	}

	abstract String getObjectKey()

	abstract String getObjectName()

}
