package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ImagenesEscaladas;
import tile.MemoriaSprite;

public abstract class Entidad 
{
	GamePanel gP;
	protected int tipoE; //0 jugador, 1, zombie, 2,proyectilJug, 3 proyectilJefe
	protected int mundoX, mundoY;
	protected int velocidad; 
	
	protected BufferedImage arriba1, arriba2, abajo1, abajo2, izquierda1,
							izquierda2, derecha1, derecha2,estatico1,estatico2,
							estaticoA1,estaticoA2,estaticoD1,estaticoD2,
							estaticoI1,estaticoI2, saltar1,saltar2; //saltar es solo para combate 
	protected String direccion;
	
	
	protected BufferedImage estaticoCombate1,estaticoCombate2,derCombate1,derCombate2,
	                        izqCombate1,izqCombate2;
	protected int contadorSprites = 0;
	protected int numeroSprite = 1;
	protected int cambiaSprite = 10;
	
	protected Rectangle solidArea;
	protected int solidAreaDefaultX, solidAreaDefaultY;
	protected boolean colisionOn = false;
	
	protected int actionLockCounter = 0;
	
	protected double vidaMaxima = 100; 
	protected double vida = vidaMaxima;
	
	//COMBATE
	protected double ataque = 5;
	protected boolean vivo = true;
	
	 // ---> NUEVAS VARIABLES PARA EL FEEDBACK VISUAL DE DAÑO <---
    protected boolean fueGolpeadoRecientemente = false;
    protected int contadorFlashDaño = 0;
    protected final int DURACION_FLASH_DAÑO = 15; // Duración del flash en frames (aprox. 0.25 seg a 60FPS)
    // ---> FIN DE NUEVAS VARIABLES <---
	
	//PROYECTILES
	protected int maxProy; 
	protected int proy;
	protected Proyectil pro; 
	protected int useCost;
	

	
	protected BufferedImage i;
	
	public Entidad(GamePanel gp) {
        this.gP = gp;

        // Inicializa solidArea con un rectángulo por defecto.
        // coordenadas (0,0) y el tamaño (32,32) según sea necesario  
        this.solidArea = new Rectangle(0, 0, 32, 32); 
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;       
    }
	
	
	protected BufferedImage setup1(String rutaBase) {
	    return MemoriaSprite.load(
	        rutaBase,
	        gP.getTamanioTile(),
	        gP.getTamanioTile()
	    );
	}
	
	public void contadorSprites() {
		  this.contadorSprites++;
		    
		    if (this.contadorSprites > this.cambiaSprite) {
		        if (this.numeroSprite == 1)
		            this.numeroSprite = 2;
		        else
		            this.numeroSprite = 1;
		        this.contadorSprites = 0;
		    }
	}
	public void recibirDaño(double daño) {
        this.vida -= daño;
        if (this.vida <= 0) {
            this.vida = 0;
            this.vivo = false; // La entidad ha sido derrotada
        }
     // ---> AÑADIR ESTAS LÍNEAS PARA ACTIVAR EL FLASH <---
        if (daño > 0) { // Solo flashea si realmente hubo daño
            this.fueGolpeadoRecientemente = true;
            this.contadorFlashDaño = DURACION_FLASH_DAÑO;
        }
        // ---> FIN DE LÍNEAS AÑADIDAS <---
    }
	
	public void curar(double cantidad) {
        this.vida += cantidad;
        if (this.vida > this.vidaMaxima) {
            this.vida = this.vidaMaxima;
        }
    }
	
	public void restaurarVidaCompleta() {
        this.vida = this.vidaMaxima;
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
	public void setDireccion(String d) {
		this.direccion = d;
	}


	
	//velocidad
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public double getVida() {
		return this.vida;
	}
	
	public double getVM() {
		return this.vidaMaxima;
	}
	
	public boolean getVivo() {
		return this.vivo;
	}
	

	
	
}
