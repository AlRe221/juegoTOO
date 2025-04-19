package Inventario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public abstract class Objeto {
    protected String tipoObjeto;
    protected double tiempoVida;
    protected boolean haceSonido;
    protected double duracion;
    protected BufferedImage image; 
    protected boolean colision; 
    protected int worldX, worldY;

    public Objeto(String tipoObjeto, double tiempoVida, boolean haceSonido, double duracion) {
        this.tipoObjeto  = tipoObjeto;
        this.tiempoVida  = tiempoVida;
        this.haceSonido  = haceSonido;
        this.duracion    = duracion;
    }

    
    
    public void draw(Graphics2D g2, GamePanel gP) {
    	
    	int pantallaX = worldX -gP.getJugador().getX() + gP.getJugador().getPantallaX();
		int pantallaY = worldY - gP.getJugador().getY() + gP.getJugador().getPantallaY();
		
		if(worldX + gP.getTamanioTile() > gP.getJugador().getX() - gP.getJugador().getPantallaX() &&
		   worldX - gP.getTamanioTile() < gP.getJugador().getX() + gP.getJugador().getPantallaX() &&
		   worldY + gP.getTamanioTile() > gP.getJugador().getY() - gP.getJugador().getPantallaX() &&
		   worldY - gP.getTamanioTile() < gP.getJugador().getY() + gP.getJugador().getPantallaY()) {
			
			g2.drawImage(image, pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile(), null);
    	
		}
    }
    public String getTipoObjeto(){ 
    	return tipoObjeto; 
	}
    public double getTiempoVida(){ 
    	return tiempoVida; 
	}
    public boolean isHaceSonido(){ 
    	return haceSonido; 
	}
    public double getDuracion(){ 
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
    
    
    public void setWorldX(int x) {
    	this.worldX = x;
    }
    public void setWorldY(int y) {
    	this.worldY = y;
    }
}
