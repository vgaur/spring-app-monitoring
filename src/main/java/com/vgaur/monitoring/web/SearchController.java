package com.vgaur.monitoring.web;

import com.vgaur.monitoring.loader.CSVFileToDBLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SearchController {

	@Autowired
	private CSVFileToDBLoader csvFileToDBLoader;

	@GetMapping("/")
	public String home(){
		return String.format("Hello %s!", "Vishal");
	}

	@GetMapping("/loadfile/{fileIdentifier}")
	public String loadData( @PathVariable("fileIdentifier") String fileIdentifier){
		csvFileToDBLoader.load(fileIdentifier);
		return String.format("File loaded %s!", fileIdentifier);
	}

	@GetMapping("/find/{key}/{value}")
	public String find(@PathVariable("key") String key, @PathVariable("value") String value){
		return csvFileToDBLoader.findValue(key, value);
	}
}
