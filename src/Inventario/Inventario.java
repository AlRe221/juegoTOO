package Inventario;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Objeto> objetos;

    public Inventario() {
        this.objetos = new ArrayList<>();
    }

    // Añade un objeto al inventario 
    public void addObjeto(Objeto o) {
        objetos.add(o);
    }

    // Elimina un objeto del inventario 
    public void removeObjeto(Objeto o) {
        objetos.remove(o);
    }

    // Devuelve la lista de objetos 
    public List<Objeto> getObjetos() {
        return objetos;
    }

    // Limpia todo el inventario 
    public void clear() {
        objetos.clear();
    }
}
