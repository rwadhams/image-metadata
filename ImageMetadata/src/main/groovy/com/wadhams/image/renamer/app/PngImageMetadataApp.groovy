package com.wadhams.image.renamer.app

import java.text.SimpleDateFormat

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import com.drew.metadata.exif.ExifSubIFDDirectory

class PngImageMetadataApp {
	SimpleDateFormat sdf = new SimpleDateFormat('yyyy:MM:dd hh:mm:ss')
	
	static main(args) {
		println 'PngImageMetadataApp started...'
		println ''

		PngImageMetadataApp app = new PngImageMetadataApp()
		app.execute()

		println 'PngImageMetadataApp ended.'
	}

	def execute() {
		File dataDir = new File('data/')
		dataDir.eachFileMatch(~/(?i).*\.png/) {		//case-insensitive
			println it.name
			printMetadata(it)
		}
	}

	def printMetadata(File png) {
		println png.getAbsolutePath()
		String originalFilename = png.getName()
		println "Original Filename: $originalFilename"
		println ''
		
		Metadata metadata = ImageMetadataReader.readMetadata(png)
		
		for (Directory dir : metadata.getDirectories()) {
			println dir.getClass()
			for (Tag tag : dir.getTags()) {
				println tag
			}
		}
		println ''
		
		println "Original Date/Time Metadata is not stored within PNG file"
		println ''
	}

}
