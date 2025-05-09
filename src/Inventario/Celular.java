package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Celular extends Arma{
	String nombreArma;
	public Celular(int cantidadDanio, double tiempoVida, boolean haceSonido, double duracion, String nombreArma, String descripcion) {
        super(cantidadDanio, tiempoVida, haceSonido, duracion, descripcion);
        this.nombreArma = nombreArma;
		
		try {
		       
        	this.setImage(ImageIO.read(getClass().getResourceAsStream("/objetosV/celular1.png")));
        	
        }catch(IOException e) {
        	e.printStackTrace();
        }

	}
	
	public String getNombreArma() {
		return nombreArma;
	}
	public void setNombreArma(String nombreArma) {
		this.nombreArma = nombreArma;
	}

	
}
