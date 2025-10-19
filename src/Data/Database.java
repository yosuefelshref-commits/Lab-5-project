package Data;

import java.io.*;
import java.util.ArrayList;

public abstract class Database<T> {
    protected String fileName;
    protected ArrayList<T> records;

    public Database(String fileName) {
        this.fileName = fileName;
        this.records = new ArrayList<>();
    }

    public ArrayList<T> returnAllRecords() {
        return records;
    }

    public void insertRecord(T record) {
        records.add(record);
    }

    public void deleteRecord(String key) {
        T record = getRecord(key);
        if (record != null) {
            records.remove(record);
        }
    }

    public T getRecord(String key) {
        for (T record : records) {
            if (recordMatchesKey(record, key)) {
                return record;
            }
        }
        return null;
    }

    protected abstract boolean recordMatchesKey(T record, String key);
    protected abstract T createRecordFrom(String line);
    protected abstract String toLine(T record);

    public void readFromFile() throws IOException {
        records.clear();
        File file = new File(this.fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    records.add(createRecordFrom(line));
                }
            }
        }
    }

    public void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T record : records) {
                writer.write(toLine(record));
                writer.newLine();
            }
        }
    }
}