package de.pch.dataobject.itrepository.basis

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.ninox.NinoxDataObject

class JobBasis extends NinoxDataObject {
	
	JobBasis(JetstreamDatabase jetstreamDatabase, long _id) {
		super(jetstreamDatabase, _id)
	}
}
