package main;

import java.awt.EventQueue;

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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}