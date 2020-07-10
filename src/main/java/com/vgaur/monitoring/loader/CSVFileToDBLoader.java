package com.vgaur.monitoring.loader;

import com.vgaur.monitoring.storage.InMemoryDatabase;
import com.vgaur.monitoring.util.FileIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Load the file data into embedded database
 */
@Component @ManagedBean @org.springframework.jmx.export.annotation.ManagedResource
public class CSVFileToDBLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVFileToDBLoader.class);

    InMemoryDatabase inMemoryDatabase;

    /**
     * Accepts the file
     * @param fileIdentifier
     */
    public void load(String fileIdentifier) {

        inMemoryDatabase = InMemoryDatabase.INSTANCE.SINGLE_INSTANCE.getInMemoryDatabase();

        List<Map<String, String>> rows = new ArrayList<>();
        try {
            String fileLocation = FileIdentifier.lookUp(fileIdentifier);
            LOGGER.error("loaded file - {}", fileLocation);
            InputStream is = getClass().getResourceAsStream("/files/"+fileLocation);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            int rowNum = 0;
            List<String> headers = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                int colNum = 0;
                Map<String, String> columnHeader = new HashMap<>();
                while (stringTokenizer.hasMoreTokens()) {

                    String token = stringTokenizer.nextToken();
                    if (rowNum == 0) {
                        headers.add(token);
                    }

                    columnHeader.put(headers.get(colNum), token);
                    colNum++;
                }
                rows.add(columnHeader);
                rowNum++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        inMemoryDatabase.load(rows);
    }


    public String findValue(String key, String value) {
        return inMemoryDatabase.find(key, value);
    }

}
