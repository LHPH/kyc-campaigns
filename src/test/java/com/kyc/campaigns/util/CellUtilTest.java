package com.kyc.campaigns.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CellUtilTest {


    @Test
    public void getCells_retrieveCellsWithNotNullRow_returnArrayOfCells(){
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short)0);
        // Create a cell and put a value in it.
        row.createCell((short)0).setCellValue("1");
        row.createCell((short)1).setCellValue("1");
        row.createCell((short)2).setCellValue("1");
        row.createCell((short)3).setCellValue("1");
        row.createCell((short)4).setCellValue("1");
        row.createCell((short)5).setCellValue("1");
        row.createCell((short)6).setCellValue("1");
        row.createCell((short)7).setCellValue("1");
        row.createCell((short)8).setCellValue("1");
        row.createCell((short)9).setCellValue("1");

        Cell [] cells = CellUtil.getCells(row);
        Assertions.assertEquals(cells[0].getStringCellValue(),"1");
        Assertions.assertEquals(cells[9].getStringCellValue(),"1");

    }

    @Test
    public void getCells_rowNull_returnArrayOfCellsWithNullValues(){

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = null;

        Cell [] cells = CellUtil.getCells(row);
        Assertions.assertNull(cells[0]);
        Assertions.assertNull(cells[9]);
    }

    @Test
    public void allNullOrBlankCells_allBlank_returnTrue(){

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short)0);
        // Create a cell and put a value in it.
        row.createCell((short)0).setBlank();
        row.createCell((short)1).setBlank();
        row.createCell((short)2).setBlank();
        row.createCell((short)3).setBlank();
        row.createCell((short)4).setBlank();
        row.createCell((short)5).setBlank();
        row.createCell((short)6).setBlank();
        row.createCell((short)7).setBlank();
        row.createCell((short)8).setBlank();
        row.createCell((short)9).setBlank();

        Cell [] cells = CellUtil.getCells(row);
        Assertions.assertTrue(CellUtil.allNullOrBlankCells(cells));
    }

    @Test
    public void allNullOrBlankCells_halfBlank_returnFalse(){

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short)0);
        // Create a cell and put a value in it.
        row.createCell((short)0).setBlank();
        row.createCell((short)1).setBlank();
        row.createCell((short)2).setBlank();
        row.createCell((short)3).setBlank();
        row.createCell((short)4).setBlank();
        row.createCell((short)5).setCellValue("value");
        row.createCell((short)6).setCellValue("value");
        row.createCell((short)7).setCellValue("value");
        row.createCell((short)8).setCellValue("value");
        row.createCell((short)9).setCellValue("value");

        Cell [] cells = CellUtil.getCells(row);
        Assertions.assertFalse(CellUtil.allNullOrBlankCells(cells));
    }
}
