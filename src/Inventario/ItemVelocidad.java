package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ItemVelocidad extends Objeto{
	int tiempoMaxItem; 
	boolean esRapido;
	
	

    public ItemVelocidad(double tiempoVida, boolean haceSonido, double duracion, String descripcion) {
        super("ItemVelocidad", descripcion, tiempoVida, haceSonido, duracion);
        this.tiempoMaxItem = (int) duracion;
        this.esRapido = true;
		 try {
	        	
	        	this.setImage(ImageIO.read(getClass().getResourceAsStream("/objetosV/itemVelocidad1.png")));
	        	
	        }catch(IOException e) {
	        	e.printStackTrace();
	        }
	}

}
