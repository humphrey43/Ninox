package de.pch.dataobject.itrepository

import de.pch.dataobject.itrepository.basis.JobBasis
import de.pch.jetstreamdb.JetstreamDatabase

class Job extends JobBasis {
	Job(JetstreamDatabase jetstreamDatabase, long _id) {
		super(jetstreamDatabase, _id)
	}
}
