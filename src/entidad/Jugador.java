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
import Inventario.Extintor;
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
	
	private ManejadorTeclas mT;
	private final int pantallaX, pantallaY;
	private Ambientacion am;
	
	private boolean modoRapido = false;
	private int velocidadBase = 5;
	private int contadorRapido = 0;
	private int maxCiclosRapido = 150; 
	private String tS;
	
	private int vidaMaxima = 100; 
	private double vida = vidaMaxima;
	protected int contPixel = 0;
	private Inventario inventario;
	
	public Jugador(GamePanel gP, ManejadorTeclas mT, Ambientacion am, String tipoSprite)
	{
		super(gP);
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
		if(o.equals("normal")) {
			
			arriba1 = setup1("/spritesjugador/pacoArriba1");
			arriba2 = setup1("/spritesjugador/pacoArriba2");
			estaticoA1 = setup1("/spritesjugador/pacoEstaticoA1");
			estaticoA2 = setup1("/spritesjugador/pacoEstaticoA2");
			
			abajo1 = setup1("/spritesjugador/pacoAbajo1");
			abajo2 = setup1("/spritesjugador/pacoAbajo2");
			estatico1 = setup1("/spritesjugador/pacoEstatico1");
			estatico2 = setup1("/spritesjugador/pacoEstatico2");
			
			estaticoI1 = setup1("/spritesjugador/pacoEstaticoiz1");
			estaticoI2 = setup1("/spritesjugador/pacoEstaticoiz2");
			izquierda1 = setup1("/spritesjugador/pacoCamI1");
			izquierda2 = setup1("/spritesjugador/pacoEstaticoiz1");
			
			estaticoD1 = setup1("/spritesjugador/pacoEstaticoD1");
			estaticoD2 = setup1("/spritesjugador/pacoEstaticoD2");
			derecha1 = setup1("/spritesjugador/pacoCamD1");
			derecha2 = setup1("/spritesjugador/pacoEstaticoD1");
	
		}
	}

    // Usa la primera Comida que encuentre: la muestra y la retira 
    /*public void usarComida() {
        for (Objeto o : inventario.getObjetos()) {
            if (o instanceof Comida) {
                Comida c = (Comida)o;
                c.mostrarComida();
                if(c instanceof Alimento) {
                gP.playSE(9);
                }else if(c instanceof Bebida) {
                	gP.playSE(12);
                }
                inventario.removeObjeto(c);
                if(c instanceof Alimento) {
                	this.vida +=3;
                }else if(c instanceof Bebida) {
                	this.vida +=6;
                }
                
                System.out.println(">> Comida usada y retirada del inventario.");
               
                
                return;
            }
        }
        System.out.println(">> No hay comida en el inventario.");
    }*/
	/*
	public void equiparArma() {
        for (Objeto o : inventario.getObjetos()) {
            if (o instanceof Arma) {
                Arma a = (Arma)o;
                System.out.print(
                  ">> Arma equipada: ");
                gP.playSE(14);
               
                if(((Arma)o) instanceof Mochila) {
                	Mochila m = (Mochila)o;
                	System.out.println(m.getNombreArma());
                }else if(((Arma)o) instanceof Laptop) {
                	Laptop l = (Laptop)o;
                	System.out.println(l.getNombreArma());
                }else if(((Arma)o) instanceof Celular) {
                	Celular c = (Celular)o;
                	System.out.println(c.getNombreArma());
                }
                return;
            }
        }
        System.out.println(">> No tienes armas para equipar.");
    } */
	
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
					    this.velocidad = velocidadBase +7;
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
					//esto hace que aparezcan y desaparezcan los items durante un tiempo
					//cuando el jugador lo toque.
					javax.swing.Timer timer = new javax.swing.Timer(12000, e -> { 
						gP.getObjetoInv()[index] = gP.getAssS().objetoUnico();
					});
					
					timer.setRepeats(false);
					timer.start();
					
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
	public void dañoInfeccion(double d) {
		this.vida -= d;
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
	
	public double getVida() {
		return this.vida;
	}
	
	public void setVida(int val) {
		this.vida = val;
	}
	
	public int getVidaMax() {
		return this.vidaMaxima;
	}
	public Inventario getInventario() {
		return inventario;
	}
}

