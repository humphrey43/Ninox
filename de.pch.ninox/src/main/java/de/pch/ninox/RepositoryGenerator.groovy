package de.pch.ninox

import de.pch.jetstreamdb.JetstreamDatabase
import de.pch.jetstreamdb.JetstreamDBHandler

class RepositoryGenerator {

	void generateDatabaseModel(JetstreamDatabase jetstreamDatabase, String teamName, String databaseName, String projectPath, String packageName) {
		NinoxDatabase database = jetstreamDatabase.getObject(NinoxConnection.NINOX_DATABASE, databaseName)
		if (database != null) {
			database.tables.each {String tablename, NinoxTable table ->
				generateBasis(table, projectPath, packageName)
				generateClass(table, projectPath, packageName)
			}
		} else {
			System.out.println("unknown database: $databaseName")
		}
		
	}
	
	void generateBasis(NinoxTable table, String projectPath, String packageName) {
		String outputPath = projectPath + "/src/main/java/" + packageName.replace('.', '/') + "/basis/" + table._name + "Basis.groovy"
		File outputFile = new File(outputPath)
		outputFile.parentFile.mkdirs()
		BufferedWriter output = new BufferedWriter(new FileWriter(outputFile))
		output.writeLine("package ${packageName}.basis")
		output.newLine()
		output.writeLine("import de.pch.ninox.NinoxDataObject")
		output.writeLine("import de.pch.jetstreamdb.JetstreamDatabase")
		output.newLine()
		output.writeLine("class ${table._name}Basis extends NinoxDataObject {")
		output.newLine()
		output.writeLine("\t${table._name}Basis(JetstreamDatabase jetstreamDatabase, long _id) {")
		output.writeLine("\t\tsuper(jetstreamDatabase, _id)")
		output.writeLine("\t}")
		output.newLine()
		output.writeLine("}")
		output.close()

	}
	
	void generateClass(NinoxTable table, String projectPath, String packageName) {
		String outputPath = projectPath + "/src/main/java/" + packageName.replace('.', '/') + "/" + table._name + ".groovy"
		File outputFile = new File(outputPath)
		outputFile.parentFile.mkdirs()
		BufferedWriter output = new BufferedWriter(new FileWriter(outputFile))
		output.writeLine("package $packageName")
		output.newLine()
		output.writeLine("import ${packageName}.basis.${table._name}Basis")
		output.writeLine("import de.pch.jetstreamdb.JetstreamDatabase")
		output.newLine()
		output.writeLine("class $table._name extends ${table._name}Basis {")
		output.newLine()
		output.writeLine("\t$table._name(JetstreamDatabase jetstreamDatabase, long _id) {")
		output.writeLine("\t\tsuper(jetstreamDatabase, _id)")
		output.writeLine("\t}")
		output.newLine()
		output.writeLine("}")
		output.close()

	}
	
}
