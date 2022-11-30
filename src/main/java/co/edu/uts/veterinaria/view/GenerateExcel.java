package co.edu.uts.veterinaria.view;

import co.edu.uts.veterinaria.entity.Reservation;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("GenerateExcel")
public class GenerateExcel extends AbstractXlsxView
{
    private static final String[] header = { "Id", "Cliente", "Surcusal", "Tipo", "Fecha", "Hora" };

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        @SuppressWarnings("unchecked")
        List<Reservation> listReservations = (List<Reservation>) model.get("reserves");
        Sheet sheet = workbook.createSheet("ListReservations");
        sheet.setFitToPage(true);

        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < header.length; i++)
        {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(style);
        }
        int rowCount = 1;

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat((short) 14);
        for (Reservation reservation : listReservations)
        {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(reservation.getId());
            row.createCell(1).setCellValue(reservation.getUser().getName());
            row.createCell(2).setCellValue(reservation.getFlight().getSource());
            row.createCell(3).setCellValue(reservation.getFlight().getDestiny());
            Cell cell = row.createCell(4);
            cell.setCellValue(reservation.getFlight().getDate());
            cell.setCellStyle(dateStyle);
            row.createCell(5).setCellValue(reservation.getFlight().getTime());
        }
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(4, 4000);
        for (int i = 1; i <= 3; i++)
        {
            sheet.autoSizeColumn(i);
        }
        response.setHeader("Content-Disposition", "attachment; filename=ReservasExcel.xlsx");
    }
}