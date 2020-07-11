package com.vgaur.monitoring.web;

import com.vgaur.monitoring.exception.DataLoadingError;
import com.vgaur.monitoring.exception.NoDataFoundException;
import com.vgaur.monitoring.loader.CSVFileToDBLoader;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController public class SearchController {

    @Autowired private CSVFileToDBLoader csvFileToDBLoader;

    @Autowired
    private ProducerTemplate producerTemplate;

    @GetMapping("/") public String home() {
        return String.format("Hello %s!", "Visitor");
    }

    @GetMapping("/loadfile/{fileIdentifier}")
    public String loadData(@PathVariable("fileIdentifier") String fileIdentifier)
        throws DataLoadingError {
        csvFileToDBLoader.load(fileIdentifier);
        return String.format("File loaded %s!", fileIdentifier);
    }

    @GetMapping("/find/{key}/{value}")
    public String find(@PathVariable("key") String key, @PathVariable("value") String value)
        throws NoDataFoundException {
        return csvFileToDBLoader.findValue(key, value);
    }

    @GetMapping("/loadfile/camel/{fileIdentifier}")
    public String loadDataViaCamel(@PathVariable("fileIdentifier") String fileIdentifier)
        throws DataLoadingError {
        producerTemplate.sendBody("direct:loaddata", fileIdentifier);
        return String.format("File loaded %s! via camel", fileIdentifier);
    }
}
