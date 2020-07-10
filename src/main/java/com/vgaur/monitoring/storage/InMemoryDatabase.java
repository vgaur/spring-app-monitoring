package com.vgaur.monitoring.storage;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.dizitart.no2.filters.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Act as a repo for storing and fetching the data <p>
 * Use Embeded {@link Nitrite} disk based database.
 */
public final class InMemoryDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDatabase.class);

    private Nitrite db;
    NitriteCollection collection;


    public enum INSTANCE {
        SINGLE_INSTANCE;

        private InMemoryDatabase inMemoryDatabase = new InMemoryDatabase();

        public InMemoryDatabase getInMemoryDatabase() {
            return inMemoryDatabase;
        }
    }

    private InMemoryDatabase() {
        Nitrite db = Nitrite.builder().compressed().filePath("/tmp/test.db")
            .openOrCreate("user", "password");
        collection = db.getCollection("test");

    }


    public void load(List<Map<String, String>> csvRow) {
        if (csvRow == null || csvRow.isEmpty()) {
            return;
        }
        Document document = null;
        for (Map<String, String> row : csvRow) {
            if (row == null || row.isEmpty()) {
                continue;
            }

            List<Map.Entry<String, String>> entry = new ArrayList(row.entrySet());
            for (int index = 0; index < entry.size(); index++) {

                if (index == 0) {
                    document = Document
                        .createDocument(entry.get(index).getKey(), entry.get(index).getValue());
                }
                LOGGER.error("Adding {} as key and {} as value", entry.get(index).getKey(),
                    entry.get(index).getValue());
                document.put(entry.get(index).getKey(), entry.get(index).getValue());
            }

        }
        collection.insert(document);

    }

    public String find(String key, String value) {
        Cursor cursor = collection.find(
            // and clause
            Filters.and(
                // firstName == John
                Filters.eq(key, value)));

        for (Document document : cursor) {
            return document.toString();
        }
        return null;
    }



}
