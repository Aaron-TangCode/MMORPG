package com.game.skill.excel;

import com.game.excel.annotation.ExcelAnnotation;
import com.game.skill.bean.ConcreteSkill;
import com.game.utils.ExcelUtils;
import com.game.utils.MapUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName ReadNPC
 * @Description 读取skill数据
 * @Author DELL
 * @Date 2019/6/11 11:23
 * @Version 1.0
 */
@ExcelAnnotation
@Component
public class ReadSkill {
    private static final String FILEPATH = "src/main/resources/excel/skill.xls";
    /**
     * 读取excel
     * @return
     */
    @ExcelAnnotation
    public static void readFromXLSX2007() throws IOException, InvalidFormatException {
            //获取相应的sheet
            Sheet sheet = ExcelUtils.retrunSheet(FILEPATH);
            // 开始循环遍历行，表头不处理，从1开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                // 获取行对象
                Row row = sheet.getRow(i);
                //如果为空，不处理
                if (row == null) {
                    continue;
                }
                //单元格，最终按字符串处理
                String cellStr = null;
                // 循环遍历单元格(每一列)
                //实例化对象
                ConcreteSkill concreteSkill = new ConcreteSkill();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 获取单元格对象
                    Cell cell = row.getCell(j);
                    //处理excel格式
                    cellStr = ExcelUtils.returnCellStr(cell);
                    // 封装数据到bean
                    if (j == 0) {
                        concreteSkill.setId(String.valueOf(new Double(cellStr).intValue()));
                    } else if (j == 1) {
                        concreteSkill.setName(cellStr);
                    } else if (j == 2) {
                        concreteSkill.setMp(new Double(cellStr).intValue());
                    }else if (j == 3) {
                        concreteSkill.setCd(new Double(cellStr).intValue());
                    }else if (j == 4) {
                        concreteSkill.setHurt(new Double(cellStr).intValue());
                    }else if (j == 5) {
                        concreteSkill.setDescription(cellStr);
                    }
                }
                // 数据装入List
                MapUtils.getSkillMap_keyId().put(concreteSkill.getId(),concreteSkill);
                MapUtils.getSkillMap_keyName().put(concreteSkill.getName(),concreteSkill);
            }
            System.out.println("Skill静态数据加载完毕");
        }
}
