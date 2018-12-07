package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance
import de.pch.dataobject.basic.DataObject
import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamRoot

class NinoxDataObject extends DataObject {
	long _id        
	long _sequence
	Date _createdAt
	String createdBy
	Date _modifiedAt
	String modifiedBy
	Map<String,Object> fields = new LinkedHashMap<>()  
	NinoxDataObject(JetstreamDatabase jetstreamDatabase, long _id) {
		super(jetstreamDatabase, _id)
	}
	
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(fields)
		super.saveDB(instance)
	}
	
	@Override
	public String getObjectKey() {
		return Long.toString(_id);
	}
	
	@Override
	public String getObjectName() {
		return this.class.simpleName
	}
}
