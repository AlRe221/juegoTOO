package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ItemVelocidad extends Objeto{
	int tiempoMaxItem; 
	boolean esRapido;
	
	

	public ItemVelocidad(double tiempoVida, boolean haceSonido, double duracion) {
		super("Coquita", tiempoVida, haceSonido, duracion);
		this.tiempoMaxItem = 6;
		 try {
	        	
	        	this.image = ImageIO.read(getClass().getResourceAsStream("/objetos/manacrystal_full.png"));
	        	
	        }catch(IOException e) {
	        	e.printStackTrace();
	        }
	}

}
