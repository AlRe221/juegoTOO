package Inventario;


public class Comida extends Objeto {
	protected int vidaCom;
	 public Comida(double tiempoVida, boolean haceSonido, double duracion, String descripcion) {
	        super("Comida", descripcion, tiempoVida, haceSonido, duracion);
	        this.vidaCom = 0;
	    }
    
    

    // Muestra datos básicos de la comida
    public void mostrarComida() {
        System.out.println("Comida → duración=" + duracion + ", tiempoVida=" + tiempoVida);
    }
   
}
