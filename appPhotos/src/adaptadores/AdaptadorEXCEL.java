package adaptadores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import modelo.Usuario;

public class AdaptadorEXCEL implements IAdaptadorSeguidores {

	@Override
	public void crearArchivo(List<Usuario> seguidores, String nombreArchivo) {
		HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Tus Seguidores");

        String[] headers = new String[]{
                "Usuario",
                "Email",
                "Descripción"
        };

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headers[i]);
        }
        
       for (int i = 0; i < seguidores.size(); ++i) {
            //Creamos la fila
    	   	HSSFRow dataRow = sheet.createRow(i + 1);
    	   	
    	   	Usuario u = seguidores.get(i);
    	   	
    	   	//Creamos las columnas
            dataRow.createCell(0).setCellValue(u.getUsuario());
            dataRow.createCell(1).setCellValue(u.getEmail());
            dataRow.createCell(2).setCellValue(u.getDescripcion());
        }

 
        File f = new File(System.getProperty("user.dir")+"/"+nombreArchivo+".xls");
        FileOutputStream file;
		try {
			file = new FileOutputStream(f);
			workbook.write(file);
			file.close();
			workbook.close();
		} catch (IOException e) {
			System.err.println("Error al crear fichero excel");
			e.printStackTrace();
		} 
		
	}

}
