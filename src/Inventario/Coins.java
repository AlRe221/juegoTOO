package Inventario;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coins extends Objeto {
	    private double valorCoin;
	    private int   cantidadCoin;

	   public Coins(double valorCoin, int cantidadCoin, double tiempoVida, boolean haceSonido, double duracion) {
       super(
           "Monedas","Monedas de oro.\nCada unidad vale " + valorCoin + " monedas.",tiempoVida, haceSonido, duracion);
       this.valorCoin    = valorCoin;
       this.cantidadCoin = cantidadCoin;
       try { 
       	this.setImage(ImageIO.read(getClass().getResourceAsStream("/objetosV/dinero1.png"))); }
       catch (IOException e) { 
       	e.printStackTrace(); 
       	}
   }
	    
	    

	    public int getCoin(){ return cantidadCoin; }
	    public void setCoin(int c){
	    	this.cantidadCoin = c; 
		}
	    public void incrementoOro(){ 
	    	this.cantidadCoin++;
		}

	    public double getValorCoin(){ 
	    	return valorCoin; 
		}
	    public void  setValorCoin(float v){
	    	this.valorCoin = v;
		}

}

