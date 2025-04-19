package Inventario;

public class Comida extends Objeto {
    public Comida(float tiempoVida, boolean haceSonido, float duracion) {
        super("Comida", tiempoVida, haceSonido, duracion);
    }

    // Muestra datos básicos de la comida
    public void mostrarComida() {
        System.out.println("Comida → duración=" + duracion + ", tiempoVida=" + tiempoVida);
    }
}
