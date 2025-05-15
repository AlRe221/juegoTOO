package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Inventario.Inventario;
import Inventario.ItemVelocidad;
import Inventario.Objeto;
import Main.Ambientacion;
import Main.GamePanel;
import Main.ManejadorTeclas;

public class Jugador extends Entidad
{
	
	private ManejadorTeclas mT;
	private int pantallaX;
	private int pantallaY;
	private Ambientacion am;
	
	private boolean modoRapido = false;
	private int velocidadBase = 5;
	private int contadorRapido = 0;
	private int maxCiclosRapido = 150; 
	private String tS;
	
	private Inventario inventario;
	
	public Jugador(GamePanel gP, ManejadorTeclas mT, Ambientacion am, String tipoSprite)
	{
		super(gP);
		this.mT = mT;
		this.am = am;
		this.tS = tipoSprite;
		this.tipoE = 0;
		
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
		this.mundoX = gP.getTamanioTile() * 22;
		this.mundoY = gP.getTamanioTile() * 92;
		
 
		
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
	   
	    //chechar colision contra zombie
	    int zomind = gP.getchecadorColision().checarEntidad(this,gP.getZombie());
	   
	    //si no hubo colisión
	    if(colisionOn == false) {
	    	switch(direccion) {
	    	case "arriba":{
	    		if(this.mundoY - this.velocidad >= 0) {
	    			this.setMundoY(this.mundoY - this.velocidad);
	    		 }else {
	    			 this.setMundoY(0);
	    		 }
	    		break;
	    		} 
	    	case "abajo" :{ 
	    		if(this.mundoY + this.velocidad -  gP.getTamanioTile() <= gP.altoMundo ) {
	    			this.setMundoY(this.mundoY + this.velocidad);
	    			
	    		}else {
	    			this.setMundoY(gP.altoMundo - gP.getTamanioTile());
	    		}
	    		break;
	    		}
	    	case "izquierda" :{
	    		if(this.mundoX - velocidad >= 0) {
	    			this.setMundoX(this.mundoX - velocidad);
	    		}else {
	    			this.setMundoX(0);
	    		}
	    		break;
	    		} 
	    	case "derecha" :{
	    		if(this.mundoX + velocidad + gP.getTamanioTile() <= gP.anchoMundo) {
	    			this.setMundoX(this.mundoX + this.velocidad); 
	    		}else {
	    			this.setMundoX(gP.anchoMundo - gP.getTamanioTile());
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
	    
	public void itemCorrer() {
		if(modoRapido == false) {
			modoRapido = true; 
			contadorRapido = 0;
		    this.velocidad = velocidadBase +7;
		}else {
			this.velocidad +=2;
		}	
	}
	    

	public void meterInventario(int index) {
		int objIndex = gP.getchecadorColision().checkObjeto(this, true);
		if (objIndex != 999) {
		    Objeto encontrado = gP.getObjetoInv()[objIndex];
		    if (encontrado != null) {
		    	if(encontrado instanceof ItemVelocidad) {
		    		gP.playSE(11);
		    		correrItem();
		    	}
		    	if (inventario.addObjeto(encontrado)) {
	                gP.getObjetoInv()[objIndex] = null;
	                gP.playSE(6);
		    	}
		    }
		}
		    	
	}
	
	public void correrItem() {	
		if(modoRapido == false) {
			modoRapido = true; 
			contadorRapido = 0;
		    this.velocidad = velocidadBase +7;
		}else {
			this.velocidad +=2;
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
		
		bordesPantalla(x,y,sprite,g2);
	
		//g2.drawImage(sprite, x, y, gP.getTamanioTile(), gP.getTamanioTile(),null);
	}
	
	
	public void bordesPantalla(int x, int y, BufferedImage sprite,Graphics2D g2) {
		
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
	}
	
	public void dañoInfeccion(double infeccion) {
		this.vida -= infeccion;
		if(this.vida <0) {
			this.vida = 0;
		}
	}
	
	//colision
	public boolean isColisionOn() {
		return colisionOn;
	}
	@Override
	public void setColisionOn(boolean colisionOn) {
		this.colisionOn = colisionOn;
	}
	
	//pantalla
	public int getPantallaX() {
		return pantallaX;
	}
	public int getPantallaY() {
		return pantallaY;
	}
	
	public void setPantallaX(int s) {
		this.pantallaX = s;
	}
	
	public void setPantallaY(int s) {
		this.pantallaY = s;
	}
	
	
	//vida
	public double getVida() {
		return this.vida;
	}
	
	public void setVida(double val) {
		this.vida = val;
	}
	
	public double getVidaMax() {
		return this.vidaMaxima;
	}
	
	//tipoSprite
	public void settS(String v) {
		this.tS = v;
	}

	public String gettS() {
		return this.tS;
	}
	
	  // Getter para acceder al inventario desde fuera 
    public Inventario getInventario() {
        return inventario;
    }
	

	public void usarObjeto(Objeto obj) {
		// TODO Auto-generated method stub
		
	}
}
