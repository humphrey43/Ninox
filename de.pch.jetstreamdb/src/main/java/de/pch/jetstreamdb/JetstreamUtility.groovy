package de.pch.jetstreamdb

class JetstreamUtility {
	
	/**
	 * delete folder recursively
	 *
	 * @param f	the folde to delete
	 * @throws IOException
	 */
	static void delete(File f) throws IOException {
		  if (f.isDirectory()) {
			for (File c : f.listFiles())
			  delete(c)
		  }
		  if (!f.delete()) {
			throw new FileNotFoundException("Failed to delete file: " + f)
		  }
	}
}
