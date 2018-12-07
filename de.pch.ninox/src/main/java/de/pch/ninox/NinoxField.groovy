package de.pch.ninox

import de.pch.jetstreamdb.JetstreamDatabase

class NinoxField extends NinoxCatalogObject {
	String type
	NinoxTable table
	NinoxTable referencedTable = null
	String referencedTableId = null
	String reverseFieldId = null

	NinoxField(JetstreamDatabase jetstreamDatabase, NinoxTable table, String _name, String _id) {
		super(jetstreamDatabase, _name, _id)
		this.table = table
		table.fields[_name] = this
		table.save(jetstreamDatabase)
	}
}
