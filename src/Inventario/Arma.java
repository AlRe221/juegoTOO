package Inventario;

public class Arma extends Objeto {
    private String tipoArma;
    private int    cantidadDanio;

    public Arma(String tipoArma, int cantidadDanio, float tiempoVida, boolean haceSonido, float duracion) {
        super("Arma", tiempoVida, haceSonido, duracion);
        this.tipoArma      = tipoArma;
        this.cantidadDanio = cantidadDanio;
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
