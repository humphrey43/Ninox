package de.pch.jetstreamdb

import com.jetstreamdb.JetstreamDBInstance
import com.jetstreamdb.util.SizeUnit

class JetstreamDatabase {
	
	JetstreamRoot root = null
	JetstreamDBInstance<JetstreamRoot> instance = null
	String name
	protected List<JetstreamObject> changedObjects = null
	protected long transactionLevel
	protected boolean inError

	JetstreamDatabase(String name) {
		openDatabase()
	}
	
	JetstreamDatabase(String name, boolean clear) {
		this.name = name
		if (clear) {
			deleteDatabaseFolder()
		}
		openDatabase()
		root.databaseChanged = clear
	}

	void openDatabase() {
		instance = JetstreamDBInstance.New(name, JetstreamRoot.class)
		instance.properties().setStorageChannelCount(4)
		instance.properties().setStorageDataFileEvaluatorMaxFileSize((int) SizeUnit.GB.toBytes(2))
		root = instance.root()
		root.databaseName = name
        initalizeDatabaseStatus()
	}
	
    /**
     * delete database folder, if exists
     */
    void deleteDatabaseFolder() {
    	String basedir = System.getProperty("user.home")
    	String dbdir = "jetstreamdb-" + name
    	File dir = new File(basedir + "/" + dbdir)
    	if (dir.exists()) {
    		try {
				JetstreamUtility.delete(dir)
			} catch (IOException e) {
				e.printStackTrace()
			}
    	}
    }
	
    void closeDatabase() {
    	endTransactionForce()
    	instance.shutdown()
    	instance = null
    	root = null
		JetstreamDBHandler.releaseDatabase(name)
    }

	void startTransaction() {
		transactionLevel++
	}
	
	void endTransactionForce() {
		endTransaction(true)
	}
	
	void writeAll() {
		endTransactionForce()
	}
	
	void endTransaction() {
		endTransaction(false)
	}
	
	void endTransaction(boolean force) {
		if (transactionLevel == 0 || force) {
			if (inError) {
				rollback()
			} else {
				commit()
			}
			initalizeDatabaseStatus()
			
		} else if (transactionLevel == 1) {
				if (inError) {
					rollback()
				} else {
					commit()
				}
				initalizeDatabaseStatus()
				
		} else {
			transactionLevel--
		}
	}
	
	boolean isChanged() {
		return (!(changedObjects == null || changedObjects.size() == 0))
	}
	
	void setDatabaseChanged() {
		root.databaseChanged = true;
	}

	void setObjectChanged(JetstreamObject jetstreamObject) {
		if (!changedObjects.contains(jetstreamObject)) changedObjects.add(jetstreamObject);
	}

	private void initalizeDatabaseStatus() {
		transactionLevel = 0
		inError = false
		if (changedObjects == null) changedObjects = new LinkedList<>()
		changedObjects.clear()
		root.databaseChanged = false
	}

	void commit() {
		if (root.databaseChanged) {
			instance.store(root.tables)
		}
		for (JetstreamObject jetstreamObject : changedObjects) {
			jetstreamObject.saveDB(instance)
		}
	}
	
	void rollback() {
	}
	
	void setInError() {
		this.inError = true
	}
	
	void setInError(boolean inError) {
		this.inError = inError
	}
	
	JetstreamTable getTable(String tableName) {
		JetstreamTable table = root.tables[tableName]
		if (table == null) {
			table = new JetstreamTable(tableName)
			root.tables[tableName] = table
			setDatabaseChanged()
		}
		return table;
	}
	
	Map<String, JetstreamObject> getObjects(String tableName) {
		return getTable(tableName).datatable
	}

	JetstreamObject getObject(String tableName, String key) {
		return getObjects(tableName)[key]
	}
	
	void setObject(String key, JetstreamObject jetstreamObject) {
		setObject(jetstreamObject.getObjectName(), key, jetstreamObject)
	}

	void setObject(String tableName, String key, JetstreamObject jetstreamObject) {
		getObjects(tableName)[key] = jetstreamObject
		setDatabaseChanged()
	}
}
