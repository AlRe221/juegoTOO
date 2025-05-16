package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ImagenesEscaladas;

public abstract class Entidad 
{
	GamePanel gP;
	protected int tipoE; //0 jugador, 1, zombie
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
	
	protected int actionLockCounter = 0;
	
	protected double vidaMaxima = 100; 
	protected double vida = vidaMaxima;
	
	
	protected BufferedImage i;
	public Entidad(GamePanel gp) {
		this.gP = gp;
	}
	
	
	public BufferedImage setup1(String imageName) {
		ImagenesEscaladas iE = new ImagenesEscaladas(); 
		BufferedImage image = null; 
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            image = iE.scaleImage(image, gP.getTamanioTile(), gP.getTamanioTile());		
		}catch(IOException e)
		{
			System.out.println(e);
		}
		
		return image;
	}
	
	public boolean getColisionOn() {
		return colisionOn;
	}
	public abstract void setColisionOn(boolean colisionOn);
	
	
	//area solida
	public Rectangle getSolidArea() {
		return solidArea;
	}
	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}
	public int getAreaSolidaX() {
		return this.solidArea.x;
	}
	public int getAreaSolidaY() {
		return this.solidArea.y;
	}
	public int getAreaSolidaWidth() {
		return this.solidArea.width;
	}
	public int getAreaSolidaHeigth() {
		return this.solidArea.height;
	}
	public void setSolidAreaX(int valor) {
		this.solidArea.x = valor;
	}
	public void setSolidAreaY(int valor) {
		this.solidArea.y = valor;
	}
	
	
	//area solida x y y
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
	
	//mundox y mundoy
	public int getMundoX() {
		return this.mundoX;
	}
	public int getMundoY() {
		return this.mundoY;
	}
	public void setMundoX(int v) {
		this.mundoX = v;
	}
	public void setMundoY(int v) {
		this.mundoY = v;
	}
	
	//direccion
	public String getDireccion() {
		return this.direccion;
	}


	
	//velocidad
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	
	
	

	
	
}
