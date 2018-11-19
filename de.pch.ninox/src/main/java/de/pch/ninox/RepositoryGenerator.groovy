package de.pch.ninox

import net.sf.json.JSON
import net.sf.json.groovy.JsonSlurper

class RepositoryGenerator {
	
	void generateDatabaseModel(String databaseName, String projectPath, String packageName) {
		NinoxDatabase database = localMetaDB.databases[databaseName]
		if (database != null) {
			database.tables.each {String tableName, NinoxTable table ->
				generateBasis(localMetaDB, table, projectPath, packageName)
				generateClass(localMetaDB, table, projectPath, packageName)
			}
		} else {
			System.out.println("unknown database: $databaseName")
		}
		
	}
	
	void generateBasis(NinoxTable table, String projectPath, String packageName) {
		String outputPath = projectPath + "/src/main/java/" + packageName.replace('.', '/') + "/basis/" + table.name + "Basis.groovy"
		File outputFile = new File(outputPath)
		outputFile.parentFile.mkdirs()
		BufferedWriter output = new BufferedWriter(new FileWriter(outputFile))
		output.writeLine("package ${packageName}.basis")
		output.newLine()
		output.writeLine("import de.pch.dataobject.NinoxDataObject")
		output.newLine()
		output.writeLine("class ${table.name}Basis extends NinoxDataObject {")
		output.writeLine("}")
		output.close()

	}
	
	void generateClass(NinoxTable table, String projectPath, String packageName) {
		String outputPath = projectPath + "/src/main/java/" + packageName.replace('.', '/') + "/" + table.name + ".groovy"
		File outputFile = new File(outputPath)
		outputFile.parentFile.mkdirs()
		BufferedWriter output = new BufferedWriter(new FileWriter(outputFile))
		output.writeLine("package $packageName")
		output.newLine()
		output.writeLine("import ${packageName}.basis.${table.name}Basis")
		output.newLine()
		output.writeLine("class $table.name extends ${table.name}Basis {")
		output.writeLine("}")
		output.close()

	}
	
}
