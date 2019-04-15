package com.project.bidding.utils;

import com.project.bidding.entity.Nomenclature;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XLSUtil {

    public static List<Nomenclature> getNomeclaturesFromFile(File file) throws IOException {
        List<Nomenclature> nomenclatures = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");

        try(FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new HSSFWorkbook(fis);

            for (Row row : workbook.getSheetAt(0)) {
                Nomenclature nomenclature = new Nomenclature();
                int i = 0;
                for (Cell cell : row) {
                    switch (i) {
                        case 0:
                            nomenclature.setMmn(cell.getStringCellValue());
                            i++;
                            break;
                        case 1:
                            nomenclature.setName(cell.getStringCellValue());
                            i++;
                            break;
                        case 2:
                            nomenclature.setManufacture(cell.getStringCellValue());
                            i++;
                            break;
                        case 3:
                            nomenclature.setVital(cell.getStringCellValue());
                            i++;
                            break;
                        case 4:
                            nomenclature.setCost_vital(cell.getNumericCellValue());
                            i++;
                            break;
                        case 5:
                            nomenclature.setQuantity(cell.getNumericCellValue());
                            i++;
                            break;
                        case 6:
                            nomenclature.setCost(cell.getNumericCellValue());
                            i++;
                            break;
                        case 7:
                            nomenclature.setExpiration(dateFormat.format(cell.getDateCellValue()));
                            i++;
                            break;
                        case 8:
                            nomenclature.setProrogation(cell.getNumericCellValue());
                            i++;
                            break;
                    }

                }
                nomenclatures.add(nomenclature);
            }
        }

        return nomenclatures;
    }


}
