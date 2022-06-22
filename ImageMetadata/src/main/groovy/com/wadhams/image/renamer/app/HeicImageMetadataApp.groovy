package com.wadhams.image.renamer.app

import java.text.SimpleDateFormat

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import com.drew.metadata.exif.ExifSubIFDDirectory

class HeicImageMetadataApp {
	SimpleDateFormat sdf = new SimpleDateFormat('yyyy:MM:dd hh:mm:ss')
	
	static main(args) {
		println 'HeicImageMetadataApp started...'
		println ''

		HeicImageMetadataApp app = new HeicImageMetadataApp()
		app.execute()

		println 'HeicImageMetadataApp ended.'
	}

	def execute() {
		File dataDir = new File('data/')
		dataDir.eachFileMatch(~/(?i).*\.heic/) {		//case-insensitive
			println it.name
			printMetadata(it)
		}
	}

	def printMetadata(File heic) {
		println heic.getAbsolutePath()
		String originalFilename = heic.getName()
		println "Original Filename: $originalFilename"
		println ''
		
		Metadata metadata = ImageMetadataReader.readMetadata(heic)
		
		for (Directory dir : metadata.getDirectories()) {
			println dir.getClass()
			for (Tag tag : dir.getTags()) {
				println tag
			}
		}
		println ''
		
		ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class)
		Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault())
		println "TAG_DATETIME_ORIGINAL...: $date"
		println ''
	}

}
