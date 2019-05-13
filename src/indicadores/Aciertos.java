package indicadores;

import java.awt.Color;

public class Aciertos extends Indicador {

	public Aciertos() {
		super("Aciertos", 0f, new Color(49, 247, 79));		
	}
	
	public Aciertos(float valor) {
		super("Aciertos", valor, new Color(49, 247, 79));		
	}
}
