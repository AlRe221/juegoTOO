package Inventario;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Objeto> objetos;
    private final int capacidad;

    public Inventario() {
        this.objetos = new ArrayList<>();
        this.capacidad = 2;
    }
    
    public boolean addObjeto(Objeto o) {
        if (objetos.size() < capacidad) {
            objetos.add(o);
            return true;
        } else {
            return false;
        }
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
    
    public int size() {
        return objetos.size();
    }

    public int getCapacidad() {
        return capacidad;
    }

}