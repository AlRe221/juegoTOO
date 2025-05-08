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
	        	
	        	this.setImage(ImageIO.read(getClass().getResourceAsStream("/objetosV/itemVelocidad1.png")));
	        	
	        }catch(IOException e) {
	        	e.printStackTrace();
	        }
	}

}
