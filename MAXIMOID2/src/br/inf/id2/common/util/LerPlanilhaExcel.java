package br.inf.id2.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LerPlanilhaExcel {

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })

	
        public static List readExcel(String caminhoPlanilha) throws IOException{
		
		if (caminhoPlanilha.toUpperCase().indexOf(".XLSX") != -1) {
			return readExcelXlsx(caminhoPlanilha);
		}
		
		String DATE_FORMAT = "dd/MM/yy";
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    	System.out.println("READEXCEL :" + caminhoPlanilha);
    	
        List sheetData = new ArrayList();
        String value = "";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(caminhoPlanilha);
            
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            
            HSSFSheet sheet = workbook.getSheetAt(0);
            int totalLinhas = sheet.getPhysicalNumberOfRows();
            Iterator rows = sheet.rowIterator();
            
            //ExcelExtractor xxx = new ExcelExtractor(workbook);
            
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                List data = new ArrayList();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();

                    switch (cell.getCellType()) {
	                    case HSSFCell.CELL_TYPE_STRING:
	                    	value = cell.getStringCellValue();
	                    	break;
	
	                    case HSSFCell.CELL_TYPE_FORMULA:
	                    	value = cell.getCellFormula();
	                    	break;
	
	                    case HSSFCell.CELL_TYPE_NUMERIC:
	                    	//if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                    	//	value = sdf.format(cell.getDateCellValue()).toString();
	                    	//} else {
	                    	value = NumberToTextConverter.toText(cell.getNumericCellValue());
	                    	//}
	                    	break;
	                    case HSSFCell.CELL_TYPE_BLANK:
	                    	value = "";
	                    	break;
	
	                    case HSSFCell.CELL_TYPE_BOOLEAN:
	                    	value = Boolean.toString(cell.getBooleanCellValue());
	                    	break;
                    }
                    data.add(value);
                }
                sheetData.add(data);                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return sheetData;
	}
	
	
    public static List readExcelXlsx(String caminhoPlanilha) throws IOException{
	String DATE_FORMAT = "dd/MM/yy";
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	System.out.println("READEXCEL :" + caminhoPlanilha);
	
    List sheetData = new ArrayList();
    String value = "";
    FileInputStream fis = null;
    try {
        fis = new FileInputStream(caminhoPlanilha);
        
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        
        XSSFSheet sheet = workbook.getSheetAt(0);
        int totalLinhas = sheet.getPhysicalNumberOfRows();
        Iterator rows = sheet.rowIterator();
        
        //ExcelExtractor xxx = new ExcelExtractor(workbook);
        
        while (rows.hasNext()) {
            XSSFRow row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            List data = new ArrayList();
            while (cells.hasNext()) {
                XSSFCell cell = (XSSFCell) cells.next();

                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                    	value = cell.getStringCellValue();
                    	break;

                    case XSSFCell.CELL_TYPE_FORMULA:
                    	value = cell.getCellFormula();
                    	break;

                    case XSSFCell.CELL_TYPE_NUMERIC:
                    	//if (XSSFDateUtil.isCellDateFormatted(cell)) {
                    	//	value = sdf.format(cell.getDateCellValue()).toString();
                    	//} else {
                    	value = NumberToTextConverter.toText(cell.getNumericCellValue());
                    	//}
                    	break;
                    case XSSFCell.CELL_TYPE_BLANK:
                    	value = "";
                    	break;

                    case XSSFCell.CELL_TYPE_BOOLEAN:
                    	value = Boolean.toString(cell.getBooleanCellValue());
                    	break;
                }
                data.add(value);
            }
            sheetData.add(data);                
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (fis != null) {
            fis.close();
        }
    }
    return sheetData;
}

}
