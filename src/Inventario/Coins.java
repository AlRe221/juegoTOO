
package Inventario;

public class Coins extends Objeto {
    private float valorCoin;
    private int   cantidadCoin;

    public Coins(float valorCoin, int cantidadCoin, float tiempoVida, boolean haceSonido, float duracion) {
        super("Coins", tiempoVida, haceSonido, duracion);
        this.valorCoin    = valorCoin;
        this.cantidadCoin = cantidadCoin;
    }

    public int getCoin(){ return cantidadCoin; }
    public void setCoin(int c){
    	this.cantidadCoin = c; 
	}
    public void incrementoOro(){ 
    	this.cantidadCoin++;
	}

    public float getValorCoin(){ 
    	return valorCoin; 
	}
    public void  setValorCoin(float v){
    	this.valorCoin = v;
	}
}
