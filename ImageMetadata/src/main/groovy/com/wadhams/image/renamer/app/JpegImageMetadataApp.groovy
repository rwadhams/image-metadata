package com.wadhams.image.renamer.app

import java.text.SimpleDateFormat

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import com.drew.metadata.exif.ExifSubIFDDirectory

class JpegImageMetadataApp {
	SimpleDateFormat sdf = new SimpleDateFormat('yyyy:MM:dd hh:mm:ss')
	
	static main(args) {
		println 'JpegImageMetadataApp started...'
		println ''

		JpegImageMetadataApp app = new JpegImageMetadataApp()
		app.execute()

		println 'JpegImageMetadataApp ended.'
	}

	def execute() {
		File dataDir = new File('data/')
		dataDir.eachFileMatch(~/(?i).*\.jpg/) {		//case-insensitive
			println it.name
			printMetadata(it)
		}
	}

	def printMetadata(File jpg) {
		println jpg.getAbsolutePath()
		String originalFilename = jpg.getName()
		println "Original Filename: $originalFilename"
		println ''
		
		Metadata metadata = ImageMetadataReader.readMetadata(jpg)
		
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
