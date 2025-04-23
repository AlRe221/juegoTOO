package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Inventario.Arma;
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
	private Inventario inventario; // Inventario del juagador  
	private Ambientacion am;
	
	private boolean modoRapido = false;
	private int velocidadBase = 5;
	private int contadorRapido = 0;
	private int maxCiclosRapido = 100; 
	
	public Jugador(GamePanel gP, ManejadorTeclas mT, Ambientacion am)
	{
		this.gP = gP;
		this.mT = mT;
		this.am = am;
		this.inventario = new Inventario();
		
		this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamanioTile()/2);
		this.pantallaY = gP.getAltoPantalla() / 2 - (gP.getTamanioTile()/2);
		
		this.solidArea = new Rectangle(8,16,32,32); 
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		
		configuracionInicial();
		getSpritesJugador();
	}
	public void configuracionInicial()
	{
		this.mundoX = gP.getTamanioTile() * 23;
		this.mundoY = gP.getTamanioTile() * 21;
		this.velocidad = 4;
		this.direccion = "abajo";
	}
	public void getSpritesJugador()
	{
		try {
			
			
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
		}catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	 // Recoge un objeto y lo mete en el inventario
    public void pickUpObjeto(Objeto obj) {
        inventario.addObjeto(obj);
        
        System.out.println(">> Recogido: " + obj.getTipoObjeto());
    }

    // Usa la primera Comida que encuentre: la muestra y la retira 
    public void usarComida() {
        for (Objeto o : inventario.getObjetos()) {
            if (o instanceof Comida) {
                Comida c = (Comida)o;
                c.mostrarComida();
                inventario.removeObjeto(c);
                System.out.println(">> Comida usada y retirada del inventario.");
                return;
            }
        }
        System.out.println(">> No hay comida en el inventario.");
    }

    // Equipa (muestra) la primera Arma que encuentre 
    public void equiparArma() {
        for (Objeto o : inventario.getObjetos()) {
            if (o instanceof Arma) {
                Arma a = (Arma)o;
                System.out.print(
                  ">> Arma equipada: ");
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
    }

    // Getter para acceder al inventario desde fuera 
    public Inventario getInventario() {
        return inventario;
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
	    
	    
	    //checar colision contra objeto
	   int obind = gP.getchecadorColision().checkObjeto(this, true);
	    meterInventario(obind);
	   
	    //si no hubo colisión
	    if(colisionOn == false) {
	    	switch(direccion) {
	    	case "arriba":{
	    		if(getY() - getVelocidad() >= 0) {
	    			setY(getY() - getVelocidad());
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
	    		}
	    		break;
	    		} 
	    	case "derecha" :{
	    		if(getX() + getVelocidad() + gP.getTamanioTile() <= gP.anchoMundo) {
	    			setX(getX() + getVelocidad()); 
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
	}
	
	
	
	

	
	/*public void update()
	{
	   //aquí se hizo la modicación para que se mueva estando estatico, si no les gusta, pueden: 
		//borrar las variables de estatico (tanto en getSprite como aqui como en draw y de la clase entidad)
		//implementar el if que esta en comentarios 
		//meter todo el codigo restante dentro de este if. 
		/*if(mT.getTeclaArriba() == true || mT.getTeclaAbajo() == true ||
			mT.getTeclaIzquierda() == true || mT.getTeclaDerecha() == true) {
			this.contadorSprites++;
			}
		
		this.direccion = "estatico";
		if(mT.getTeclaArriba())
		{
			setY(getY() - getVelocidad());
			this.direccion = "arriba";
		} else 
			if(mT.getTeclaAbajo())
			{
				setY(getY() + getVelocidad());
				this.direccion = "abajo";
			}else 
				if(mT.getTeclaIzquierda())
				{
					setX(getX() - getVelocidad());
					this.direccion = "izquierda";
				}else 
					if(mT.getTeclaDerecha())
					{
						setX(getX() + getVelocidad());
						this.direccion = "derecha";
					}
			
			this.contadorSprites++;
		 
				if(this.contadorSprites > this.cambiaSprite)
				{
					if(this.numeroSprite == 1)
						this.numeroSprite = 2;
					else
						this.numeroSprite = 1;
					this.contadorSprites = 0;
				}
		}*/		
	
	public void meterInventario(int index) {
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
					    this.velocidad = velocidadBase +2;
					}
				}else {
					inventario.addObjeto(gP.getObjetoInv()[index]);
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
					case "Comida":
						gP.playSE(6);
						break;
					}
					gP.getObjetoInv()[index] = null;
				}
			}	
		}
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
		
	
		g2.drawImage(sprite, x, y, gP.getTamanioTile(), gP.getTamanioTile(),null);
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
	
}

