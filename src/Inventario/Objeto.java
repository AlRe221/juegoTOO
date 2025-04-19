package Inventario;

public abstract class Objeto {
    protected String tipoObjeto;
    protected float tiempoVida;
    protected boolean haceSonido;
    protected float duracion;

    public Objeto(String tipoObjeto, float tiempoVida, boolean haceSonido, float duracion) {
        this.tipoObjeto  = tipoObjeto;
        this.tiempoVida  = tiempoVida;
        this.haceSonido  = haceSonido;
        this.duracion    = duracion;
    }

    public String getTipoObjeto(){ 
    	return tipoObjeto; 
	}
    public float getTiempoVida(){ 
    	return tiempoVida; 
	}
    public boolean isHaceSonido(){ 
    	return haceSonido; 
	}
    public float getDuracion(){ 
    	return duracion; 
	}

    public void setTiempoVida(float tiempoVida){
    	this.tiempoVida = tiempoVida; 
    }
    public void setHaceSonido(boolean haceSonido) { 
    	this.haceSonido = haceSonido; 
	}
    public void setDuracion(float duracion) { 
    	this.duracion = duracion; 
	}
}
