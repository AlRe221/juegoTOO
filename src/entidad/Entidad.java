package entidad;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entidad 
{
	protected int mundoX, mundoY;
	protected int velocidad;
	
	protected BufferedImage arriba1, arriba2, abajo1, abajo2, izquierda1,
							izquierda2, derecha1, derecha2,estatico1,estatico2,
							estaticoA1,estaticoA2,estaticoD1,estaticoD2,
							estaticoI1,estaticoI2;
	protected String direccion;
	
	protected int contadorSprites = 0;
	protected int numeroSprite = 1;
	protected int cambiaSprite = 10;
	
	protected Rectangle solidArea;
	protected int solidAreaDefaultX, solidAreaDefaultY;
	protected boolean colisionOn = false;
	
	
	public boolean isColisionOn() {
		return colisionOn;
	}
	public void setColisionOn(boolean colisionOn) {
		this.colisionOn = colisionOn;
	}
	
	
	
	public Rectangle getSolidArea() {
		return solidArea;
	}
	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}
	
	
	public int getSolidAreaDefaultX() {
		return solidAreaDefaultX;
	}
	public void setSolidAreaDefaultX(int solidAreaDefaultX) {
		this.solidAreaDefaultX = solidAreaDefaultX;
	}
	public int getSolidAreaDefaultY() {
		return solidAreaDefaultY;
	}
	public void setSolidAreaDefaultY(int solidAreaDefaultY) {
		this.solidAreaDefaultY = solidAreaDefaultY;
	}	
	
	
}
