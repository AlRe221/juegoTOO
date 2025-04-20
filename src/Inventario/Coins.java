
package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Coins extends Objeto {
    private double valorCoin;
    private int   cantidadCoin; //cuando las recolecta en el inventario

    public Coins(double valorCoin, int cantidadCoin, double tiempoVida, boolean haceSonido, double duracion) {
        super("Coins", tiempoVida, haceSonido, duracion);
        this.valorCoin    = valorCoin;
        this.cantidadCoin = cantidadCoin;
        
        try {
        	
        	this.image = ImageIO.read(getClass().getResourceAsStream("/objetos/blueheart.png"));
        	
        }catch(IOException e) {
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
