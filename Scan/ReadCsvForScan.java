package Scan;

import com.csvreader.CsvReader;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReadCsvForScan {
    ArrayList<String[]> csvList;

    public ReadCsvForScan(String csvPath) {

        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
            String csvFilePath = csvPath;
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));    //一般用这编码读就可以了

            reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。

            while (reader.readRecord()) { //逐行读入除表头的数据
//                System.out.println("ttttt:"+reader.getValues().length);
                if (reader.getValues().length == 7) {

                    csvList.add(reader.getValues());
                }
                /*else {
                    break;
                }*/
            }
            reader.close();

            if (csvList != null)
                this.csvList = csvList;
//			System.out.println(csvList.size());

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<String> readCsv(int cloum) {

        List<String> cells_byCloum = new ArrayList<String>();

        try {
            if (csvList != null) {
                for (int row = 1; row < csvList.size(); row++) {
                    String cell = csvList.get(row)[cloum]; //取得第row行第0列的数据
                    //System.out.println(cell);
                    cells_byCloum.add(cell);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return cells_byCloum;
    }

}

