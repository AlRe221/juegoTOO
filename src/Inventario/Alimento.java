package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Alimento extends Comida{
    String nombreComida;
 
	public Alimento(double tiempoVida, boolean haceSonido, double duracion,String nomC) {
		super(tiempoVida, haceSonido, duracion);
		this.nombreComida = nomC;
		try {
        	
        		this.image = ImageIO.read(getClass().getResourceAsStream("/objetosV/torta1.png"));
        	
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }

		
}

