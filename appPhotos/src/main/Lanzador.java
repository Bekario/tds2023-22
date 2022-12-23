package main;

import java.awt.EventQueue;
import java.io.File;

import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;

import ventanas.Login;

public class Lanzador {
	public static void main(final String[] args){
		FlatMonokaiProIJTheme.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login ventana = new Login();
					ventana.mostrarVentana(null);
					//Creamos el directorio donde se guardan las fotos subidas
					File directorio = new File(System.getProperty("user.dir")+"/fotosSubidas");
			        if (!directorio.exists()) {
			            if (directorio.mkdirs()) {
			                System.out.println("Directorio creado");
			            } else {
			                System.out.println("Error al crear directorio");
			            }
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}