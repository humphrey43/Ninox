package de.pch.jetstreamdb.test

import com.jetstreamdb.JetstreamDBInstance
import com.jetstreamdb.swizzling.types.Lazy

import de.pch.dataobject.basic.DataObject
import de.pch.jetstreamdb.JetstreamRoot

public class Customer extends DataObject {

    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private String firstname
    private String lastname
    private int age
    private Lazy<Map<String, Object>> values = null
	public final static String DATABASE_NAME = "customer-db"
	public final static String OBJECT_NAME = "Customer"
    
    public Customer() {
    	setValues(new LinkedHashMap<String, Object>())
    }
    
	protected Map<String, Object> getValues() {
		return Lazy.get(this.values)
	}

	public void setValues(Map<String, Object> values) {
		this.values = Lazy.Reference(values)
	}

    public void setValue(String name, String value) {
     	getValues().put(name,  value)
    }
    
    public String getValue(String name) {
    	return getValue(name, null)
    }
    
    public String getValue(String name, String defaultValue) {
    	String value = (String) getValues().get(name)
    	if (value == null) {
    		value = defaultValue
    	}
    	return value
    }
    
    @Override
    public void saveDB(JetstreamDBInstance<JetstreamRoot> instance) {
    	instance.store(getValues())
    	instance.store(this)
    }
	
	@Override
	public String getObjectKey() {
		return lastname
	}

	@Override
	public String getObjectName() {
		return OBJECT_NAME;
	}

//	@Override
//	public boolean updateFrom(JetstreamObject other) {
//		boolean changed = false;
//		if (!firstname.equals(other.firstname)) {
//			firstname = other.firstname
//			changed = true;
//		}
//		if (!lastname.equals(other.lastname)) {
//			lastname = other.lastname
//			changed = true;
//		}
//		if (age != other.age) {
//			age = other.age
//			changed = true;
//		}
//		changed = updateMapFrom(getValues(), other.getValues())
//		return changed
//	}
}