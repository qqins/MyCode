package Scan;

import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;

public class WriteCsvForScan {
    private String filePath;
    CsvWriter csvWriter = null;

    public WriteCsvForScan(String csvPath) {
        try {
            filePath = csvPath;
            csvWriter = new CsvWriter(filePath, ',', Charset.forName("UTF-8"));
            String[] headers = {"MAC", "Is home device?"};
            csvWriter.writeRecord(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String[] record) {
        try {
            csvWriter.writeRecord(record);
//            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        csvWriter.close();
    }
}
