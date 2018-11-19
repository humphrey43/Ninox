package de.pch.ninox

class NinoxField extends NinoxCatalogObject {
	String type
	NinoxTable table
	NinoxTable referencedTable = null
	String referencedTableId = null
	String reverseFieldId = null
}
