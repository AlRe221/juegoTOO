package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public abstract class JefePorNivel extends Entidad{
	int id_Nivel;  
	boolean activa_combate;

	 public JefePorNivel(GamePanel gp) {
		super(gp);
		this.id_Nivel = 1; 
		this.activa_combate = false; 
		this.direccion = "estatico";
		this.solidArea = new Rectangle(8,16,32,32); 
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		
	}

	@Override
	public void setColisionOn(boolean colisionOn) {
		this.colisionOn = colisionOn;
		
	}
	
	public void update() {
		 contadorSprites();
		 gP.getchecadorColision().checkJugador(this);
		 
		 /*if(res) {
			 setActivaCombate();
			 System.out.println("Se activo el combate!");
		 }*/
	}
	
	
	public void draw(Graphics2D g2) {
		int pantallaX = mundoX -gP.getJugador().getMundoX() + gP.getJugador().getPantallaX();
		int pantallaY = mundoY - gP.getJugador().getMundoY() + gP.getJugador().getPantallaY();
		
	   colocarObjeto(pantallaX, pantallaY,g2,gP);
	     
	}
	
	
	public void colocarObjeto(int x,int y, Graphics2D g2, GamePanel gP) {
    	
    	if(gP.getJugador().getMundoX() < gP.getJugador().getPantallaX()) {
			x = mundoX;
		}
		if(gP.getJugador().getMundoY() < gP.getJugador().getPantallaY()) {
			y = mundoY;
		}
		
		int rOffs = gP.getAnchoPantalla() - gP.getJugador().getPantallaX(); 
		if(rOffs > gP.getAnchoMundo() - gP.getJugador().getMundoX()) {
			x = gP.getAnchoPantalla() - (gP.anchoMundo - mundoX); 	
		}
		
		int bOffs = gP.getAltoPantalla() - gP.getJugador().getPantallaY(); 
		if(bOffs > gP.getAltoMundo() - gP.getJugador().getMundoY()) {
			y = gP.getAltoPantalla() - (gP.altoMundo - mundoY); 	
		}
		
		if(mundoX + gP.getTamanioTile() > gP.getJugador().getMundoX() - gP.getJugador().getPantallaX() &&
		   mundoX - gP.getTamanioTile() < gP.getJugador().getMundoX() + gP.getJugador().getPantallaX() &&
		   mundoY + gP.getTamanioTile() > gP.getJugador().getMundoY() - gP.getJugador().getPantallaY() &&
		   mundoY- gP.getTamanioTile() < gP.getJugador().getMundoY() + gP.getJugador().getPantallaY()) {
			i = direcciones();
			g2.drawImage(i, x, y, gP.getTamanioTile(), gP.getTamanioTile(), null);
		}else {
			if(gP.getJugador().getMundoX() < gP.getJugador().getPantallaX() ||
					gP.getJugador().getMundoY() < gP.getJugador().getPantallaY() ||
					rOffs > gP.getAnchoMundo() - gP.getJugador().getMundoX() ||
					bOffs > gP.getAltoMundo() - gP.getJugador().getMundoY()) {
					g2.drawImage(i, x, y, gP.getTamanioTile(), gP.getTamanioTile(), null);
				}
		}
		
    }
	
	
	public BufferedImage direcciones() {
		BufferedImage sprite = null;
		
		switch(this.direccion) {
		case "estatico" : 
			if(this.numeroSprite == 1)
				sprite = this.estatico1; 
			if(this.numeroSprite == 2)
				sprite = this.estatico2; 
			break;
		}
		
		return sprite;
	}
	
	public int getIdNivel() {
		return this.id_Nivel;
	}
	
	public void setIdNivel(int v) {
		this.id_Nivel = v;
	}
	
	public void setActivaDesactivaCombate(boolean t) {
	   this.activa_combate = t;
	}
	
	public boolean getActivaDesactivaCombate() {
		return this.activa_combate;
	}
	
	public abstract String getLocation();

}
