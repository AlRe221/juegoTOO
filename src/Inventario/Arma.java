package Inventario;

public class Arma extends Objeto {
    private int  cantidadDanio;

    public Arma(int cantidadDanio, double tiempoVida, boolean haceSonido, double duracion, String descripcion) {
    super("Arma", descripcion, tiempoVida, haceSonido, duracion);
    this.cantidadDanio = cantidadDanio;
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
