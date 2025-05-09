package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Bebida extends Comida{
	String bebida;
	
	public Bebida(double tiempoVida, boolean haceSonido, double duracion, String bebida, String descripcion) {
        super(tiempoVida, haceSonido, duracion, descripcion);
        this.bebida = bebida;
        try { 
        	this.setImage(ImageIO.read(getClass().getResourceAsStream("/objetosV/agua1.png"))); }
        catch (IOException e) { 
        	e.printStackTrace(); 
        	}
    }


	
}
	

