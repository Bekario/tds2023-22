package modelo;

import java.time.LocalDate;
import java.time.Period;

public class DescuentoEdad implements Descuento{
	//Descuento aplicado por edad
	private final double DESCUENTO = 0.4;
	
	//Edades entre las que se aplica el descuento
	private final int EDAD_MIN = 18;
	private final int EDAD_MAX = 25;
	
	@Override
	public float aplicarDescuento(Usuario usuario) {
		int edad = Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears();
		if(edad >= EDAD_MIN && edad <= EDAD_MAX) {
			return (float) (Variables.precioPremium - (Variables.precioPremium * DESCUENTO));
		}
		return Variables.precioPremium;
	}
	
}
