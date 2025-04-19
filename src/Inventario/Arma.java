package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Arma extends Objeto {
    private String tipoArma;
    private int    cantidadDanio;

    public Arma(String tipoArma, int cantidadDanio, double tiempoVida, boolean haceSonido, double duracion) {
        super("Arma", tiempoVida, haceSonido, duracion);
        this.tipoArma      = tipoArma;
        this.cantidadDanio = cantidadDanio;
        
  try {
        	
        	this.image = ImageIO.read(getClass().getResourceAsStream("/objetos/sword_normal.png"));
        	
        }catch(IOException e) {
        	e.printStackTrace();
        }
        
    }
    
    

    public String getTipoArma(){ 
    	return tipoArma; 
	}
    public void setTipoArma(String t){
    	this.tipoArma = t; 
    }

    public int  getCantidadDanio() { 
    	return cantidadDanio; 
    }
    public void setCantidadDanio(int d){ 
    	this.cantidadDanio = d; 
	}

    public void incrementoDanio(){
    	this.cantidadDanio++; 
	}
}