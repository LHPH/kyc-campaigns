package com.kyc.campaigns.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import static com.kyc.core.util.GeneralUtil.getValue;

public final class CellUtil {

    public static Cell[] getCells(Row row){

        Cell [] cells = new Cell[10];

        if(row!=null){

            cells[0] = row.getCell(0);
            cells[1] = row.getCell(1);
            cells[2] = row.getCell(2);
            cells[3] = row.getCell(3);
            cells[4] = row.getCell(4);
            cells[5] = row.getCell(5);
            cells[6] = row.getCell(6);
            cells[7] = row.getCell(7);
            cells[8] = row.getCell(8);
            cells[9] = row.getCell(9);
        }

        return cells;
    }

    public static boolean allNullOrBlankCells(Cell [] cells){

        boolean [] validCells = new boolean[10];

        CellType cellTypeCell0 = ObjectUtils.defaultIfNull(getValue(cells[0],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell1 = ObjectUtils.defaultIfNull(getValue(cells[1],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell2 = ObjectUtils.defaultIfNull(getValue(cells[2],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell3 = ObjectUtils.defaultIfNull(getValue(cells[3],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell4 = ObjectUtils.defaultIfNull(getValue(cells[4],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell5 = ObjectUtils.defaultIfNull(getValue(cells[5],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell6 = ObjectUtils.defaultIfNull(getValue(cells[6],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell7 = ObjectUtils.defaultIfNull(getValue(cells[7],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell8 = ObjectUtils.defaultIfNull(getValue(cells[8],Cell::getCellType),CellType.BLANK);
        CellType cellTypeCell9 = ObjectUtils.defaultIfNull(getValue(cells[9],Cell::getCellType),CellType.BLANK);

        validCells[0] = CellType.BLANK.equals(cellTypeCell0);
        validCells[1] = CellType.BLANK.equals(cellTypeCell1);
        validCells[2] = CellType.BLANK.equals(cellTypeCell2);
        validCells[3] = CellType.BLANK.equals(cellTypeCell3);
        validCells[4] = CellType.BLANK.equals(cellTypeCell4);
        validCells[5] = CellType.BLANK.equals(cellTypeCell5);
        validCells[6] = CellType.BLANK.equals(cellTypeCell6);
        validCells[7] = CellType.BLANK.equals(cellTypeCell7);
        validCells[8] = CellType.BLANK.equals(cellTypeCell8);
        validCells[9] = CellType.BLANK.equals(cellTypeCell9);

        return BooleanUtils.and(validCells);
    }
}
