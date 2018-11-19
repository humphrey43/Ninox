package de.pch.jetstreamdb

import com.jetstreamdb.JetstreamDBInstance

interface JetstreamObject {
	String getObjectName()
	String getObjectKey()
	void save(JetstreamDatabase database)
	void saveDB(JetstreamDBInstance<JetstreamRoot> instance)
//	public abstract boolean updateFrom(JetstreamObject other)
//	
//	boolean updateMapFrom(Map<String, Object> oldObject, Map<String, Object> newObject) {
//		return false
//	}
}
