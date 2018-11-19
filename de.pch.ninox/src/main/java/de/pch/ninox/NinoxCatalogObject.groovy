package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance
import de.pch.dataobject.basic.DataObject
import de.pch.jetstreamdb.JetstreamObject
import de.pch.jetstreamdb.JetstreamRoot

class NinoxCatalogObject extends DataObject {
	String name
	String id
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
		instance.store(this)
	}

	@Override
	public String getObjectKey() {
		return name;
	}

	@Override
	public String getObjectName() {
		return this.class.simpleName
	}
}
