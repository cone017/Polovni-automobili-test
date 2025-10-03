import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static void exportCarsToExcel(List<PolovniAutomobili.Car> cars, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // create new .xlsx file
        Sheet sheet = workbook.createSheet("Cars");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Title", "Price", "Year", "Link"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill rows with car data
        int rowNum = 1;
        for (PolovniAutomobili.Car car : cars) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(car.title);
            row.createCell(1).setCellValue(car.price);
            row.createCell(2).setCellValue(car.year);
            row.createCell(3).setCellValue(car.link);
        }

        // Autosize columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
