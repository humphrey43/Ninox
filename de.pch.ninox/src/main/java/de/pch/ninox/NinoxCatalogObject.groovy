package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance

import de.pch.dataobject.basic.DataObject
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamRoot

class NinoxCatalogObject extends DataObject {

	String _id
	String _name

	NinoxCatalogObject(JetstreamDatabase jetstreamDatabase, String _name, String _id) {
		super(jetstreamDatabase)
		this._id = _id
		this._name = _name
	}
	
	@Override
	String getObjectKey() {
		return _name
	}

	@Override
	String getObjectName() {
		return this.class.simpleName
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(this)
		super.saveDB(instance)
	}
}
