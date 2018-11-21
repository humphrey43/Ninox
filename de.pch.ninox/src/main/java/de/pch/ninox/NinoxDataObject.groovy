package de.pch.ninox

import com.jetstreamdb.JetstreamDBInstance
import de.pch.dataobject.basic.DataObject
import de.pch.jetstreamdb.JetstreamRoot

class NinoxDataObject extends DataObject {
	long _id
	long _sequence
	Date _createdAt
	String createdBy
	Date _modifiedAt
	String modifiedBy
	Map<String,Object> fields = new LinkedHashMap<>()  
	@Override
	public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(fields)
		super.saveDB(instance)
	}
	
	@Override
	public String getObjectName() {
		return this.class.simpleName
	}
	
	@Override
	public String getObjectKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
