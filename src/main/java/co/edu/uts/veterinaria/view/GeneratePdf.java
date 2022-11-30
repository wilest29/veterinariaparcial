package co.edu.uts.veterinaria.view;

import co.edu.uts.veterinaria.entity.Reservation;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("GeneratePdf")
public class GeneratePdf extends AbstractPdfView
{
    private static final String[] header = { "Id", "Cliente", "Surcusal", "Tipo", "Fecha", "Hora" };

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        @SuppressWarnings("unchecked")
        List<Reservation> listReservations = (List<Reservation>) model.get("reserves");
        document.setPageSize(PageSize.LETTER.rotate());
        document.open();
        PdfPTable tableTitle = new PdfPTable(1);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Listado de Reservaciones"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTitle.addCell(cell);
        tableTitle.setSpacingAfter(30);
        PdfPTable tableReservations = new PdfPTable(6);
        for (String s : header)
        {
            tableReservations.addCell(s);
        }
        listReservations.forEach(reservation -> {
            tableReservations.addCell(reservation.getId().toString());
            tableReservations.addCell(reservation.getUser().getName());
            tableReservations.addCell(reservation.getFlight().getSource());
            tableReservations.addCell(reservation.getFlight().getDestiny());
            tableReservations.addCell(reservation.getFlight().getDate().toString());
            tableReservations.addCell(reservation.getFlight().getTime());
        });
        document.add(tableTitle);
        document.add(tableReservations);
    }
}