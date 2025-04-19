package Inventario;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Comida extends Objeto {
    public Comida(double tiempoVida, boolean haceSonido, double duracion) {
        super("Comida", tiempoVida, haceSonido, duracion);
        
  try {
        	
        	this.image = ImageIO.read(getClass().getResourceAsStream("/objetos/potion_red.png"));
        	
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }

    // Muestra datos básicos de la comida
    public void mostrarComida() {
        System.out.println("Comida → duración=" + duracion + ", tiempoVida=" + tiempoVida);
    }
}
