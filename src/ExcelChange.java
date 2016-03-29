import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 1/7/14
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelChange {
    public static void main(String args[]) {
        try {
            File input = new File(args[0]);
            //NPOIFSFileSystem npoi = new NPOIFSFileSystem(input);
            FileInputStream file = new FileInputStream(new File("D:\\tmp\\input.xls"));

            //FileInputStream file = new FileInputStream(npoi);
            //Get the workbook instance for XLS file



            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            int count = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if (count > 4) {
                    //System.out.println(sheet.getRow(count).getLastCellNum());
                    if(sheet.getRow(count).getLastCellNum()>0){
                        //System.out.print("\"");
                        int CellCount=0;
                    while (cellIterator.hasNext()) {
                        CellCount++;
                        Cell cell = cellIterator.next();

                        if(CellCount ==5){
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    System.out.print(cell.getBooleanCellValue() + ",");
                                    break;

                                case Cell.CELL_TYPE_NUMERIC:
                                    System.out.print((double)Math.round(cell.getNumericCellValue()*100)/100 + ",");
                                    break;

                                case Cell.CELL_TYPE_STRING:
                                    System.out.print(cell.getStringCellValue() + ",");
                                    break;


                            }
                        }else{
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + ",");
                                break;

                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.print(cell.getNumericCellValue() + ",");
                                break;

                            case Cell.CELL_TYPE_STRING:
                                System.out.print(cell.getStringCellValue() + ",");
                                break;


                        }
                        }
                    }
                    //System.out.print("\"");
                    System.out.println("");

                }
                }
                count++;
            }
            file.close();
           /* FileOutputStream out =
                    new FileOutputStream(new File("D:\\tmp\\out.xls"));
            workbook.write(out);
            out.close()*/;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}