package com.game.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName ExcelUtils
 * @Description Excel工具类
 * @Author DELL
 * @Date 2019/6/11 11:57
 * @Version 1.0
 */
public class ExcelUtils {
    /**
     * 返回sheet
     * @param FILEPATH
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static Sheet retrunSheet(String FILEPATH) throws IOException, InvalidFormatException {

        //Excel文件对象
        File excelFile = null;
        //输入流对象
        InputStream is = null;
        Sheet sheet = null;
        try {
            // 获取文件输入流
            excelFile = new File(FILEPATH);
            is = new FileInputStream(excelFile);
            Workbook workbook = WorkbookFactory.create(is);
            sheet = workbook.getSheetAt(0);
        }
        finally {
            if(is!=null){
                is.close();
            }
            return sheet;
        }
    }

    /**
     * 返回cellStr
     * @param cell
     * @return
     */
    public static String returnCellStr(Cell cell) {
        String cellStr = null;
        if (cell == null) {
            cellStr = "";
        }else{
            switch (cell.getCellTypeEnum()){
                case NUMERIC:
                    cellStr = cell.getNumericCellValue() + "";
                    break;
                case BOOLEAN:
                    cellStr = String.valueOf(cell.getBooleanCellValue());
                    break;
                default: cellStr = cell.getStringCellValue();
            }
        }
        return cellStr;
    }

}
