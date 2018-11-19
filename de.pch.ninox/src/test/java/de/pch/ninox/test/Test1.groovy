package de.pch.ninox.test

import de.pch.ninox.NinoxConnection
import net.sf.json.groovy.JsonSlurper

class Test1 {

	static main(args) {
		NinoxConnection connection = new NinoxConnection()
		connection.apiKey = '772a6480-d2e9-11e8-a659-e78cc785609a'
		connection.ninoxUrl = 'https://api.ninoxdb.de/v1/'
		
		SslRest sslRest = new SslRest()
		String teams = 'teams'
        String json = sslRest.getJson(teams)
        System.out.println json
		
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(json)
		def object2 = object[0]
		assert object instanceof List
		assert object2 instanceof Map
		assert object2.id == 'rwpHkd8tSkDuEjjs2'
		String teamId = object2.id
		String teamName = object2.name
		
		String databases = teams + '/' + teamId + '/databases'
        json = sslRest.getJson(databases)
        System.out.println json
		object = jsonSlurper.parseText(json)
		object2 = object[0]
		assert object instanceof List
		assert object2 instanceof Map
		assert object2.id == 'td3hms1jcbm6'
		
		String databaseId = object2.id
		String databaseName = object2.name
		String tables = databases + '/' + databaseId + '/tables'
        json = sslRest.getJson(tables)
        System.out.println json

		def i = 42
	}
}
