package Inventario;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
	public static final int MAX_REN = 2;   
    public static final int MAX_COL = 4;   
    private List<Objeto> objetos;
    private int capacidad;

    public Inventario() {
    	this.capacidad = MAX_REN * MAX_COL;
        this.objetos  = new ArrayList<>(capacidad);
    }
    
    private int toIndex(int ren, int col) {
        return ren * MAX_COL + col;
    }
    
    public boolean addObjeto(Objeto o) {
        if (objetos.size() < capacidad) {
            objetos.add(o);
            return true;
        } else {
            return false;
        }
    }
    
    /** Recupera el objeto sin eliminarlo */
    public Objeto getObjetoEn(int ren, int col) {
        int idx = toIndex(ren, col);
        if (idx < 0 || idx >= objetos.size()) return null;
        return objetos.get(idx);
    }

    // Elimina un objeto del inventario 
    public Objeto removeObjetoEn(int ren, int col) {
        int idx = toIndex(ren, col);
        if (idx < 0 || idx >= objetos.size()) return null;
        return objetos.remove(idx);
    }

    // Devuelve la lista de objetos 
    public List<Objeto> getObjetos() {
        return objetos;
    }
    
    public int size() {
        return objetos.size();
    }

    public int getCapacidad() {
        return capacidad;
    }
    

}