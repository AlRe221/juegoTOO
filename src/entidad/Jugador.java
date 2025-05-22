package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Inventario.Inventario;
import Inventario.ItemVelocidad;
import Inventario.Objeto;
import Main.Ambientacion;
import Main.FightGame;
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
	private String spriteKeyActual = "normal"; 
	
	public Jugador(GamePanel gP, ManejadorTeclas mT, Ambientacion am)
	{
		super(gP);
		this.mT = mT;
		this.am = am;
		this.tipoE = 0;
		
		this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamanioTile()/2);
		this.pantallaY = gP.getAltoPantalla() / 2 - (gP.getTamanioTile()/2);
		
		this.solidArea = new Rectangle(8,16,32,32); 
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		this.inventario = new Inventario();
		
		configuracionInicial();
		getSpritesJugador("normal");

	}
	public void configuracionInicial()
	{
		this.mundoX = gP.getTamanioTile() * 22;
		this.mundoY = gP.getTamanioTile() * 92;
		
 
		
		this.velocidad = velocidadBase;
		this.direccion = "abajo";
		
		
	}
	
	public void equipar(String key) {
	    if (key == null) key = "normal";
	    if (key.equals(spriteKeyActual)) return;     // ya lo tengo puesto
	    spriteKeyActual = key;
	    getSpritesJugador(spriteKeyActual);          // recarga imágenes
	}
	
	public void getSpritesJugador(String key)
	{
		String carpeta, prefijo;

	    if ("normal".equals(key)) {
	        carpeta = "/spritesjugador/";
	        prefijo = "paco";
	    } else {
	        char pref = Character.toUpperCase(key.charAt(0));    // E, C, L, M…
	        System.out.println(pref);
	        carpeta = "/paquitoCobjetos/" + key + "/";
	        prefijo = "paco" + pref;
	    }
	    arriba1    = setup1(carpeta + prefijo + "Arriba1");
	    arriba2    = setup1(carpeta + prefijo + "Arriba2");
	    abajo1     = setup1(carpeta + prefijo + "Abajo1");
	    abajo2     = setup1(carpeta + prefijo + "Abajo2");
	    izquierda1 = setup1(carpeta + prefijo + "Izquierda1");
	    izquierda2 = setup1(carpeta + prefijo + "EstaticoI2");
	    derecha1   = setup1(carpeta + prefijo + "Derecha1");
	    derecha2   = setup1(carpeta + prefijo + "EstaticoD1");

	    estatico1  = setup1(carpeta + prefijo + "Estatico1");
	    estatico2  = setup1(carpeta + prefijo + "Estatico2");
	    estaticoA1 = setup1(carpeta + prefijo + "EstaticoA1");
	    estaticoA2 = setup1(carpeta + prefijo + "EstaticoA2");
	    estaticoI1 = setup1(carpeta + prefijo + "EstaticoI1");
	    estaticoI2 = setup1(carpeta + prefijo + "EstaticoI2");
	    estaticoD1 = setup1(carpeta + prefijo + "EstaticoD1");
	    estaticoD2 = setup1(carpeta + prefijo + "EstaticoD2");
	    
	}
	
	int index=0;

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
	    
	    
	    cambiarPantallaCombate();
	 

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
	    
	   
	    contadorSprites();
	}
	  
	public void updateCombate() {
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
		    
		    if (mT.getTeclaIzquierda()) {
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
		    if (!moviendo) {
		      if (this.direccion.equals("izquierda")) {
		            this.direccion = "estatico";
		        }else if(this.direccion.equals("derecha")) {
		        	this.direccion ="estatico";
		        }
		    }
		    
		    if(colisionOn == false) {
		    	switch(direccion) {
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
		    
		    contadorSprites();
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
	                
	                aparecerItemenCiertoTiempo();
		    	}
		    }
		}
		    	
	}
	
	//REGENERAR LOS ITEMS PARA QUE EL MAPA NO SE QUEDE PELÓN
	//120000 son 2 min 
	public void aparecerItemenCiertoTiempo() {
		javax.swing.Timer timer = new javax.swing.Timer(120000, e -> {
		    gP.getObjetoInv()[index] = gP.getAssS().objetoUnico();
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	public void cambiarPantallaCombate() {
		//checar la colisión de el jugador con el jefe final 
		//si devuelve true, entonces, cambiamos a la pantalla de combate
		//si no, pues no, lol
		int jefind = gP.getchecadorColision().checarEntidad(this, gP.getJF());
	    if(jefind != 999) {
	    	 gP.getJF()[jefind].setActivaDesactivaCombate(true);
	    	 
	    	 boolean combate = gP.getJF()[jefind].getActivaDesactivaCombate();
	    	 if(combate) {
	    		 gP.playMusic(20); //se inicia la musica del combate
	    		 gP.getAssS().setidJFN(jefind);
	    		 
	    		 //SE AGREGARON SPRITES PARA LA PANTALLA DE FORMA LINEAL, POR ELLO DE CAMBIA
	    		 cambiarSpriteCombate();       
	    		 direccion = "estatico";     
	    		 numeroSprite = 1; 
	    		 
	    		 
	    		 gP.setFightGame(new FightGame(gP, this, gP.getJF()[gP.getAssS().getidJFN()]));
	    		 gP.setGameState(gP.getFightState());
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
	
	public void drawEnCombate(Graphics2D g2) {
		BufferedImage sprite = direcciones(); 
		
		switch(this.direccion)
		{
		
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
		}
		
		int x = this.pantallaX; 
		int y = this.pantallaY; 
		
		bordesPantalla(x,y,sprite,g2);
	
	}
	
	public void cambiarSpriteCombate() {
		this.estatico1 = setup1("/spritesjugador/paquitoCombate1");
		this.estatico2 = setup1("/spritesjugador/paquitoCombate2");
		this.derecha1 = setup1("/spritesjugador/paquitoCombate1");
		this.derecha2 = setup1("/spritesjugador/paquitoCombate2");
		this.izquierda1 = setup1("/spritesjugador/paquitoCombate1");
		this.izquierda2 =setup1("/spritesjugador/paquitoCombate2");
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
		}
		
		return sprite;
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
					
					if(gP.getGameState() == gP.getFightState()) {
						g2.drawImage(sprite, x, 400,200,200, null);
					}else {
						g2.drawImage(sprite, x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
					}
					
				}else {
					if(pantallaY > this.mundoX ||
							pantallaY > this.mundoY ||
							rOffs > gP.anchoMundo - this.mundoX||
							bottomOffs > gP.altoMundo - this.mundoY) {
						
						if(gP.getGameState() == gP.getFightState()) {
							g2.drawImage(sprite, x, 400,200,200, null);
						
						}else {
							g2.drawImage(sprite, x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
						}
						
						
						
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
	

}
