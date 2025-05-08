package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Inventario.Alimento;
import Inventario.Arma;
import Inventario.Bebida;
import Inventario.Celular;
import Inventario.Comida;
import Inventario.Inventario;
import Inventario.ItemVelocidad;
import Inventario.Laptop;
import Inventario.Mochila;
import Inventario.Objeto;
import Main.Ambientacion;
import Main.GamePanel;
import Main.ManejadorTeclas;

public class Jugador extends Entidad
{
	private GamePanel gP;
	private ManejadorTeclas mT;
	private final int pantallaX, pantallaY;
	private Ambientacion am;
	
	private boolean modoRapido = false;
	private int velocidadBase = 5;
	private int contadorRapido = 0;
	private int maxCiclosRapido = 150; 
	private String tS;
	
	private int vidaMaxima = 100; 
	private int vida = vidaMaxima;
	protected int contPixel = 0;
	private Inventario inventario;
	
	public Jugador(GamePanel gP, ManejadorTeclas mT, Ambientacion am, String tipoSprite)
	{
		this.gP = gP;
		this.mT = mT;
		this.am = am;
		this.tS = tipoSprite;
		
		this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamanioTile()/2);
		this.pantallaY = gP.getAltoPantalla() / 2 - (gP.getTamanioTile()/2);
		
		this.solidArea = new Rectangle(8,16,32,32); 
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		this.inventario = new Inventario();
		
		configuracionInicial();
		getSpritesJugador(this.tS);
	}
	public void configuracionInicial()
	{
		this.mundoX = gP.getTamanioTile() * 23;
		this.mundoY = gP.getTamanioTile() * 21;
		this.velocidad = velocidadBase;
		this.direccion = "abajo";
	}
	public void getSpritesJugador(String o)
	{
		try {
			
			if(o.equals("normal")) {
				this.arriba1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoArriba1.png"));
				this.arriba2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoArriba2.png"));
				this.estaticoA1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoA1.png"));
				this.estaticoA2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoA2.png"));
				
				
				this.estatico1 =ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstatico1.png"));
				this.estatico2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstatico2.png"));
				this.abajo1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoAbajo1.png"));
				this.abajo2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoAbajo2.png"));
				
				
				this.estaticoI1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoiz1.png"));
				this.estaticoI2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoiz2.png"));		
				this.izquierda1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoCamI1.png"));
				this.izquierda2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoiz1.png"));
				
				this.estaticoD1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoD1.png"));
				this.estaticoD2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoD2.png"));		
				this.derecha1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoCamD1.png"));
				this.derecha2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoD1.png"));
				}
		}catch(IOException e)
		{
			System.out.println(e);
		}
	}
	public void update() {
	    boolean moviendo = false;
	    this.colisionOn = false;
	    
	    if (modoRapido) {
	        contadorRapido++;
	        if (contadorRapido >= maxCiclosRapido) {
	            this.velocidad = velocidadBase;
	            modoRapido = false;
	            contadorRapido = 0;
	        }
	    }
	    

	    if (mT.getTeclaArriba()) {
	        this.direccion = "arriba";
	        moviendo = true;
	    } else if (mT.getTeclaAbajo()) {
	        this.direccion = "abajo";
	        moviendo = true;
	    } else if (mT.getTeclaIzquierda()) {
	        this.direccion = "izquierda";
	        moviendo = true;
	    } else if (mT.getTeclaDerecha()) {
	        this.direccion = "derecha";
	        moviendo = true;
	    }
	    
	    if(mT.isTeclaCorrer()) {
	    	
	    	if(moviendo) {
	    		if(!modoRapido) {
	    			this.velocidad = velocidadBase +5;
	    		}
	    	}	
	    }
	    
	    
	    if (!mT.isTeclaCorrer() && !modoRapido) {
	        this.velocidad = velocidadBase;
	    }
	    
	    am.sonidoCamina(moviendo, colisionOn, 0);

	    // Si no se está moviendo, aplicar estático dependiendo de la última dirección
	    if (!moviendo) {
	        if (this.direccion.equals("arriba")) {
	            this.direccion = "estaticoArriba";
	        } else if (this.direccion.equals("abajo")) {
	            this.direccion = "estatico";
	        }else if(this.direccion.equals("derecha")) {
	        	this.direccion ="estaticoDerecha";
	        }else if(this.direccion.equals("izquierda")) {
	        	this.direccion = "estaticoIzquierda";
	        }	// Puedes agregar también estaticoIzquierda y estaticoDerecha si quieres
	        
	        
	    }
	    
	    
	    //revisa coli con tiles
	     
	    gP.getchecadorColision().checkTile(this);
	    
	    int obind = gP.getchecadorColision().checkObjeto(this, true);
	    meterInventario(obind);
	  
	    //si no hubo colisión
	    if(colisionOn == false) {
	    	switch(direccion) {
	    	case "arriba":{
	    		if(getY() - getVelocidad() >= 0) {
	    			setY(getY() - getVelocidad());
	    		 }else {
	    			 setY(0);
	    		 }
	    		break;
	    		} 
	    	case "abajo" :{ 
	    		if(getY() + getVelocidad() <= gP.altoMundo - gP.getTamanioTile()) {
	    			setY(getY() + getVelocidad());
	    		}else {
	    			setY(gP.altoMundo - gP.getTamanioTile());
	    		}
	    		break;
	    		}
	    	case "izquierda" :{
	    		if(getX() - getVelocidad() >= 0) {
	    			setX(getX() - getVelocidad());
	    		}else {
	    			setX(0);
	    		}
	    		break;
	    		} 
	    	case "derecha" :{
	    		if(getX() + getVelocidad() + gP.getTamanioTile() <= gP.anchoMundo) {
	    			setX(getX() + getVelocidad()); 
	    		}else {
	    			setX(gP.anchoMundo - gP.getTamanioTile());
	    		}
	    		break; 
	    		}
	    	}
	    }
	    
	    this.contadorSprites++;
	    
	    if (this.contadorSprites > this.cambiaSprite) {
	        if (this.numeroSprite == 1)
	            this.numeroSprite = 2;
	        else
	            this.numeroSprite = 1;
	        this.contadorSprites = 0;
	    }
	    
	    this.contPixel	+= this.velocidad;
	    if(this.contPixel == 48) {
	    	moviendo = false; 
	    	this.contPixel = 0;
	    }
	    
	    
	}
	
	// Meter inventario
	public void meterInventario(int index) {
		int objIndex = gP.getchecadorColision().checkObjeto(this, true);
		if (objIndex != 999) {
		    Objeto encontrado = gP.getObjetoInv()[objIndex];
		    if (encontrado != null) {
		    	if (inventario.addObjeto(encontrado)) {
	                gP.getObjetoInv()[objIndex] = null;
	                gP.playSE(6);
		    	}
		    }
		}
		    	
		/* 
		int maxElInv = 6; 
		int contElInv = 0;
		if(index != 999) { //si es 999, no se ha tocado ningun objeto
			if(inventario.size() < maxElInv) {
				if(gP.getObjetoInv()[index] instanceof ItemVelocidad) {
					gP.playSE(11);
					gP.getObjetoInv()[index] = null;
					if(modoRapido == false) {
						modoRapido = true; 
						contadorRapido = 0;
					    this.velocidad = velocidadBase +9;
					}else {
						this.velocidad +=2;
					}
				}else {
					this.tS = gP.getObjetoInv()[index].getTipoObjeto();
					inventario.addObjeto(gP.getObjetoInv()[index]);
					getSpritesJugador(tS);
					switch(gP.getObjetoInv()[index].getTipoObjeto()) {
					case "Mochila":
						gP.playSE(6);
						break;
					case "Laptop":
						gP.playSE(6);
						break;
					case "Celular":
						gP.playSE(6);
						break;	
					case "Coins":
						gP.playSE(8);
						break;
					case "Torta Chilaquil":
						gP.playSE(6);
						break;
					case "Aguita":
						gP.playSE(6);
						break;
					case "Extintor":
						gP.playSE(6);
						break;	
					}
					gP.getObjetoInv()[index] = null;
				}
			}	
		}*/
	}
	public void draw(Graphics2D g2)
	{
		BufferedImage sprite = null;
		switch(this.direccion)
		{
		case "arriba" : 
			if(this.numeroSprite == 1)
				sprite = this.arriba1; 
			if(this.numeroSprite == 2)
				sprite = this.arriba2; 
			break;
		case "abajo" : 
			if(this.numeroSprite == 1)
				sprite = this.abajo1; 
			if(this.numeroSprite == 2)
				sprite = this.abajo2; 
			break;
		case "izquierda" : 
			if(this.numeroSprite == 1)
				sprite = this.izquierda1; 
			if(this.numeroSprite == 2)
				sprite = this.izquierda2; 
			break;
		case "derecha" : 
			if(this.numeroSprite == 1)
				sprite = this.derecha1; 
			if(this.numeroSprite == 2)
				sprite = this.derecha2; 
			break;
		case "estatico" : 
			if(this.numeroSprite == 1)
				sprite = this.estatico1; 
			if(this.numeroSprite == 2)
				sprite = this.estatico2; 
			break;
		case "estaticoArriba" : 
			if(this.numeroSprite == 1)
				sprite = this.estaticoA1; 
			if(this.numeroSprite == 2)
				sprite = this.estaticoA2; 
			break;	
		case "estaticoDerecha" : 
			if(this.numeroSprite == 1)
				sprite = this.estaticoD1; 
			if(this.numeroSprite == 2)
				sprite = this.estaticoD2; 
			break;
		case "estaticoIzquierda" : 
			if(this.numeroSprite == 1)
				sprite = this.estaticoI1; 
			if(this.numeroSprite == 2)
				sprite = this.estaticoI2; 
			break;	
		}
		
		int x = this.pantallaX; 
		int y = this.pantallaY; 
		
		if(pantallaX > this.mundoX) {
			x = this.mundoX;
		}
		
		if(pantallaY > this.mundoY) {
			y = this.mundoY;
		}
		
		int rOffs = gP.getAnchoPantalla() - this.pantallaX;
		
		if(rOffs > gP.anchoMundo - this.mundoX) {
			x = gP.getAnchoPantalla() - (gP.anchoMundo - mundoX);
			}
		
		int bottomOffs = gP.getAltoPantalla() - this.pantallaY;
		if(bottomOffs > gP.altoMundo - this.mundoY) {
			y = gP.getAltoPantalla() - (gP.altoMundo - mundoY);
		}
		
		if(mundoX + gP.getTamanioTile() > this.mundoX - pantallaX  &&
				   mundoX - gP.getTamanioTile() < this.mundoX + pantallaX &&
				   mundoY + gP.getTamanioTile() > this.mundoY - pantallaY &&
				   mundoY - gP.getTamanioTile() < this.mundoY + pantallaY) {
					
					g2.drawImage(sprite, x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
					
				}else {
					if(pantallaY > this.mundoX ||
							pantallaY > this.mundoY ||
							rOffs > gP.anchoMundo - this.mundoX||
							bottomOffs > gP.altoMundo - this.mundoY) {
						
						g2.drawImage(sprite, x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
						}
					}
	
		//g2.drawImage(sprite, x, y, gP.getTamanioTile(), gP.getTamanioTile(),null);
	}
	public void dañoInfeccion(int infeccion) {
		this.vida -= infeccion;
		if(this.vida <0) {
			this.vida = 0;
		}
	}
	public int getX()
	{
		return this.mundoX;
	}
	public int getY()
	{
		return this.mundoY;
	}
	public int getVelocidad()
	{
		return this.velocidad;
	}
	public void setX(int valor)
	{
		this.mundoX = valor;
	}
	public void setY(int valor)
	{
		this.mundoY = valor;
	}
	
	
	public int getPantallaX() {
		return pantallaX;
	}
	public int getPantallaY() {
		return pantallaY;
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
	
	public String getDireccion() {
		return this.direccion;
	}
	
	public void setSolidAreaX(int valor) {
		this.solidArea.x = valor;
	}
	
	public void setSolidAreaY(int valor) {
		this.solidArea.y = valor;
	}
	
	public int getVida() {
		return this.vida;
	}
	
	public void setVida(int val) {
		this.vida = val;
	}
	
	public int getVidaMax() {
		return this.vidaMaxima;
	}
	
	public Inventario getInventario() {
	    return this.inventario;
	}
	
}

