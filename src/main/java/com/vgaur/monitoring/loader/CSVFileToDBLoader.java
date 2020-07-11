package com.vgaur.monitoring.loader;

import com.vgaur.monitoring.exception.DataLoadingError;
import com.vgaur.monitoring.exception.NoDataFoundException;
import com.vgaur.monitoring.storage.InMemoryDatabase;
import com.vgaur.monitoring.util.FileIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Load the file data into embedded database
 */
@Component @ManagedBean @org.springframework.jmx.export.annotation.ManagedResource
public class CSVFileToDBLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVFileToDBLoader.class);
    public static final String FILES = "/files/";

    InMemoryDatabase inMemoryDatabase =
        InMemoryDatabase.INSTANCE.SINGLE_INSTANCE.getInMemoryDatabase();

    /**
     * Accepts the file
     *
     * @param fileIdentifier
     */
    public void load(String fileIdentifier) throws DataLoadingError {

        List<Map<String, String>> rows = new ArrayList<>();
        try {
            String fileLocation = FileIdentifier.lookUp(fileIdentifier);
            LOGGER.info("File getting loaded in database - {}", fileLocation);
            InputStream is = new FileInputStream(FILES + fileLocation);

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
            throw new DataLoadingError("Error while loading file with identifier " + fileIdentifier,
                ex);
        }

        inMemoryDatabase.load(rows);
    }


    public String findValue(String key, String value) throws NoDataFoundException {
        String data = inMemoryDatabase.find(key, value);
        if (data == null) {
            LOGGER
                .debug("Unable to find any data corresponding to key {} and value {}", key, value);
            throw new NoDataFoundException(String.format("No Data Found for Key %s", key), null);
        }
        return data;
    }

}
