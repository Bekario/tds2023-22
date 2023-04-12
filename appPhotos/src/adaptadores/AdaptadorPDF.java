package adaptadores;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.Usuario;

public class AdaptadorPDF implements IAdaptadorSeguidores {

	@Override
	public void crearArchivo(List<Usuario> seguidores, String nombreArchivo) {
		Document document = new Document();
        
        try{
        	File f = new File(System.getProperty("user.dir")+"/"+nombreArchivo+".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(f));
            document.open();
            
            //Añadimos el titulo
            document.add(new Paragraph("Tus usuarios Seguidores – appPhotos", FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK)));
            
            //Ponemos un espacio en blanco
            document.add(new Paragraph("\n", FontFactory.getFont("arial", 18, Font.BOLD, BaseColor.BLACK)));
            
            // Este codigo genera una tabla de 3 columnas
            PdfPTable table = new PdfPTable(3);   
             
            // Creamos las tres celdas iniciales
            table.addCell(new Paragraph("Nombre", FontFactory.getFont("arial", 14, Font.BOLD, BaseColor.BLACK)));
            table.addCell(new Paragraph("Email", FontFactory.getFont("arial", 14, Font.BOLD, BaseColor.BLACK)));
            table.addCell(new Paragraph("Descripción", FontFactory.getFont("arial", 14, Font.BOLD, BaseColor.BLACK)));
            
            /*
            for(Usuario u : seguidores) {
            	table.addCell(new Paragraph(u.getUsuario(), FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK)));
            	table.addCell(new Paragraph(u.getEmail(), FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK)));
            	table.addCell(new Paragraph(u.getDescripcion(), FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK)));
            }*/
            
            //Añadimos una fila con los datos relevantes del seguidor
            seguidores.stream()
            	.forEach(s -> {	table.addCell(new Paragraph(s.getUsuario(), FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK)));
			                	table.addCell(new Paragraph(s.getEmail(), FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK)));
			                	table.addCell(new Paragraph(s.getDescripcion(), FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK)));
            				  });

            // Agregamos la tabla al documento            
            document.add(table);
            
            //Cerramos el documento
            document.close();
             
        }catch(Exception e) {
            System.err.println("Ocurrio un error al crear el archivo");
        }
		
	}

}
