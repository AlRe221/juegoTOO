package Inventario;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public abstract class Objeto {
    protected String tipoObjeto;
    protected double tiempoVida;
    protected boolean haceSonido;
    protected double duracion;
    private BufferedImage image; 
    protected boolean colision; 
    protected int worldX, worldY;
    protected Rectangle solidArea = new Rectangle(0,0,48,48);
    protected int solidAreaDefaultX = 0;
    protected int solidAreaDefaultY = 0;

    public Objeto(String tipoObjeto, double tiempoVida, boolean haceSonido, double duracion) {
        this.tipoObjeto  = tipoObjeto;
        this.tiempoVida  = tiempoVida;
        this.haceSonido  = haceSonido;
        this.duracion    = duracion;
    }

    
    
    
    public void draw(Graphics2D g2, GamePanel gP) {
    	
    	int pantallaX = worldX -gP.getJugador().getX() + gP.getJugador().getPantallaX();
		int pantallaY = worldY - gP.getJugador().getY() + gP.getJugador().getPantallaY();
		
		//detener camara
		if(gP.getJugador().getX() < gP.getJugador().getPantallaX()) {
			pantallaX = worldX;
		}
		if(gP.getJugador().getY() < gP.getJugador().getPantallaY()) {
			pantallaY = worldY;
		}
		
		int rOffs = gP.getAnchoPantalla() - gP.getJugador().getPantallaX(); 
		if(rOffs > gP.getAnchoMundo() - gP.getJugador().getX()) {
			pantallaX = gP.getAnchoPantalla() - (gP.anchoMundo - worldX); 	
		}
		
		int bOffs = gP.getAltoPantalla() - gP.getJugador().getPantallaY(); 
		if(bOffs > gP.getAltoMundo() - gP.getJugador().getY()) {
			pantallaY = gP.getAltoPantalla() - (gP.altoMundo - worldY); 	
		}
		
		
		
		if(worldX + gP.getTamanioTile() > gP.getJugador().getX() - gP.getJugador().getPantallaX() &&
		   worldX - gP.getTamanioTile() < gP.getJugador().getX() + gP.getJugador().getPantallaX() &&
		   worldY + gP.getTamanioTile() > gP.getJugador().getY() - gP.getJugador().getPantallaY() &&
		   worldY - gP.getTamanioTile() < gP.getJugador().getY() + gP.getJugador().getPantallaY()) {
			
			g2.drawImage(getImage(), pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile(), null);
		}else {
			if(gP.getJugador().getX() < gP.getJugador().getPantallaX() ||
				gP.getJugador().getY() < gP.getJugador().getPantallaY() ||
				rOffs > gP.getAnchoMundo() - gP.getJugador().getX() ||
				bOffs > gP.getAltoMundo() - gP.getJugador().getY()) {
				g2.drawImage(getImage(), pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile(), null);
			}
		}
    }
    
    
    public String getTipoObjeto(){ 
    	String tipO=null;
    	if(this instanceof Coins) {
    		tipO = ((Coins)this).tipoObjeto;
    	}else if(this instanceof Comida) {
    		if(((Comida)this) instanceof Alimento) {
    			Alimento a = (Alimento)this;
    			tipO = a.nombreComida;
    		}else if(((Comida)this) instanceof Bebida) {
    			Bebida b = (Bebida)this; 
    			tipO = b.bebida;
    		}
    	}else if(this instanceof Arma) {
    		if(((Arma)this) instanceof Mochila) {
    			Mochila m = (Mochila)this;
    			tipO = m.nombreArma;
    		}else if(((Arma)this) instanceof Celular) {
    			Celular c = (Celular)this;
    			tipO = c.nombreArma;
    		}else if(((Arma)this) instanceof Laptop) {
    			Laptop c = (Laptop)this;
    			tipO = c.nombreArma;
    		}else if(((Arma)this) instanceof Extintor) {
    			Extintor e = (Extintor)this;
    			tipO = e.nombreArma;
    		}
    	}
    	return tipO; 
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
    public int getWorldX() {
    	return this.worldX;
    }
    
    
    public void setWorldY(int y) {
    	this.worldY = y;
    }
    public int getWorldY() {
    	return this.worldY;
    }
    
    
    public Rectangle getSolidArea() {
		return solidArea;
	}

	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}


	public void setAreaSolidaXO(int valor) {
    	this.solidArea.x = valor;
    }
    public int getAreaSolidaXO() {
    	return this.solidArea.x;
    }
    
    
    public void setAreaSolidaYO(int valor) {
    	this.solidArea.y = valor;
    }
    public int getAreaSolidaYO() {
    	return this.solidArea.y;
    }
    
    public int getDefautlAreaX() {
    	return this.solidAreaDefaultX;
    }
    public int getDefautlAreaY() {
    	return this.solidAreaDefaultY;
    }
    
    
    public boolean getColisionO() {
    	return this.colision;
    }
    
    public void setColsionO(boolean val) {
    	this.colision = val;
    }




	public BufferedImage getImage() {
		return image;
	}




	public void setImage(BufferedImage image) {
		this.image = image;
	}
}

