package com.wadhams.image.renamer.app

import java.text.SimpleDateFormat

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import com.drew.metadata.avi.AviDirectory
import com.drew.metadata.exif.ExifSubIFDDirectory

class AviImageMetadataApp {
	SimpleDateFormat sdf = new SimpleDateFormat('EEE MMM dd hh:mm:ss yyyy')
	
	static main(args) {
		println 'AviImageMetadataApp started...'
		println ''

		AviImageMetadataApp app = new AviImageMetadataApp()
		app.execute()

		println 'AviImageMetadataApp ended.'
	}

	def execute() {
		File dataDir = new File('data/')
		dataDir.eachFileMatch(~/(?i).*\.avi/) {		//case-insensitive
			println it.name
			printMetadata(it)
		}
	}

	def printMetadata(File avi) {
		println avi.getAbsolutePath()
		String originalFilename = avi.getName()
		println "Original Filename: $originalFilename"
		println ''
		
		Metadata metadata = ImageMetadataReader.readMetadata(avi)
		
		for (Directory dir : metadata.getDirectories()) {
			println dir.getClass()
			for (Tag tag : dir.getTags()) {
				println tag
			}
		}
		println ''
		
		AviDirectory directory = metadata.getFirstDirectoryOfType(AviDirectory.class)
		Collection<Tag> tags = directory.getTags()
		println "Tag count: ${tags.size()}"
//		Tag t = null
//		t.tagName
//		t.tagType
//		t.hasTagName()
		tags.each {t ->
			println t
			println "\t${t.tagName}"
			println "\t${t.tagType}"
			println "\t${t.hasTagName()}"
		}
		println ''
		
//		println "AviDirectory.TAG_DATETIME_ORIGINAL..........: ${AviDirectory.TAG_DATETIME_ORIGINAL}"
//		println "ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL...: ${ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL}"
//		println ''
		
//		Date date = directory.getDate(AviDirectory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault())
//		Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault())
//		Date date = directory.getDate(320, TimeZone.getDefault())
//		Date date = directory.getDate(320)
//		println "TAG_DATETIME_ORIGINAL...: $date"
		
//		Object obj1 = directory.getObject(320)
//		println "obj1 is null: ${(obj1)?'false':'true'}"
//		println "obj1 class: ${obj1.class.name}"
//		println "obj1: $obj1"
//		println ''
		
		String sDate = (String)directory.getObject(AviDirectory.TAG_DATETIME_ORIGINAL)
		println "sDate................: $sDate"
		Date date = sdf.parse(sDate)
		println "Date/Time Original...: $date"
		println ''
	}

}
