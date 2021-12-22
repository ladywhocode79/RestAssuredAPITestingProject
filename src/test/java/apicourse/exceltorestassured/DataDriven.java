package apicourse.exceltorestassured;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataDriven {

    ArrayList<String> getData(String columnName,String sheetName) throws IOException {
        ArrayList<String>cellArray = new ArrayList<String>();
        FileInputStream file = new FileInputStream
                ("https://docs.google.com/spreadsheets/d/16QqlR0pnNeQnBQNr0y-V6hVuWxvRn988sliDgUBOOqA/edit?usp=sharing");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int noOfSheets = workbook.getNumberOfSheets();
        for(int i=0;i<noOfSheets;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)){
            XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();//sheet is collection of rows
                Row firstRow = rows.next();
                Iterator<Cell> cell = firstRow.cellIterator(); //row is collection of cells
                int k =0;
                int column =0;
                //hasNext checks if there is a next cell
                while(cell.hasNext()){
                    //Identify Testcases column by scanning the entire list row
                    //to move to next cell
                    Cell values = cell.next();
                   if(values.getStringCellValue().equalsIgnoreCase("TestCases")){
                    //to capture column number for the searched value
                    column =k;
                   }
                k++;

                }
                System.out.println(column);
                //Once column is identified then scan entire testcase column to identify the purchase testcase row
                while(rows.hasNext()){
                   Row r= rows.next();
                    if(r.getCell(column).getStringCellValue().equalsIgnoreCase(columnName)){
                        //once matched is found get all the data of rows or cell contents
                        Iterator<Cell> expectedCell =r.cellIterator();
                        while (expectedCell.hasNext()){
                            //get all cell values of row purchase
                           // System.out.println(expectedCell.next().getStringCellValue());
                            //store data in array list
                            Cell cellValueType = expectedCell.next();
                            if(cellValueType.getCellType() == CellType.STRING ){
                            cellArray.add(expectedCell.next().getStringCellValue());
                            }else{

                                cellArray.add( NumberToTextConverter.toText(expectedCell.next().getNumericCellValue()));
                            }

                        }
                    }
                }

            }
        }
        return cellArray;
    }
}
