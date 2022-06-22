package com.wadhams.image.renamer.app

import java.text.SimpleDateFormat

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import com.drew.metadata.mov.QuickTimeDirectory
import com.drew.metadata.mov.metadata.QuickTimeMetadataDirectory

class MovImageMetadataApp {
	SimpleDateFormat sdf = new SimpleDateFormat('yyyy:MM:dd hh:mm:ss')
	
	static main(args) {
		println 'MovImageMetadataApp started...'
		println ''

		MovImageMetadataApp app = new MovImageMetadataApp()
		app.execute()

		println 'MovImageMetadataApp ended.'
	}

	def execute() {
		File dataDir = new File('data/')
		dataDir.eachFileMatch(~/(?i).*\.mov/) {		//case-insensitive
			println it.name
			printMetadata(it)
		}
	}

	def printMetadata(File mov) {
		println mov.getAbsolutePath()
		String originalFilename = mov.getName()
		println "Original Filename: $originalFilename"
		println ''
		
		Metadata metadata = ImageMetadataReader.readMetadata(mov)
		
		for (Directory dir : metadata.getDirectories()) {
			println dir.getClass()
			for (Tag tag : dir.getTags()) {
				println tag
			}
		}
		println ''
		
		QuickTimeDirectory directory1 = metadata.getFirstDirectoryOfType(QuickTimeDirectory.class)
		Date date1 = directory1.getDate(QuickTimeDirectory.TAG_CREATION_TIME)
		println "TAG_CREATION_TIME...: $date1"
		println ''
		
		QuickTimeMetadataDirectory directory2 = metadata.getFirstDirectoryOfType(QuickTimeMetadataDirectory.class)
		Date date2 = directory2.getDate(QuickTimeMetadataDirectory.TAG_CREATION_DATE)
		println "TAG_CREATION_DATE...: $date2"
		println ''
	}

}
