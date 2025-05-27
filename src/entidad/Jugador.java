package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Inventario.Arma;
import Inventario.Equipable;
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
	
	private boolean danioAplicadoEnEsteAtaque = false;
	private boolean atacando = false;
    private int contadorAnimacionAtaque = 0;
    private final int DURACION_FRAME_ATAQUE = 15; // Duración de cada frame del sprite de ataque 
    private final int FRAME_DE_DAÑO = 2; // El segundo sprite es el que hace daño
    private long tiempoUltimoAtaque = 0;
    private final long COOLDOWN_ATAQUE = 500; // 0.5 segundos
    private Rectangle areaAtaque; // Alcance del golpe
    private final long COOLDOWN_DISPARO = 1000; // Cooldown de 1 segundo
    private long tiempoUltimoDisparo = 0;
    
    // BufferedImages para los sprites de ataque
    protected BufferedImage ataqueAbajo1, ataqueAbajo2; 
    protected BufferedImage ataqueArriba1, ataqueArriba2;
    protected BufferedImage ataqueIzquierda1, ataqueIzquierda2;
    protected BufferedImage ataqueDerecha1, ataqueDerecha2;
	
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
		this.areaAtaque = new Rectangle(0, 0, 0, 0);
		
		configuracionInicial();
		getSpritesJugador("normal");

	}
	public void configuracionInicial()
	{
		this.mundoX = gP.getTamanioTile() * 22;
		this.mundoY = gP.getTamanioTile() * 92;
		
		this.velocidad = velocidadBase;
		this.direccion = "abajo";
		restaurarVidaCompleta(); 
		
	}
	
	public void equipar(String key) {
	    if (key == null) key = "normal";
	    if (key.equals(spriteKeyActual)) return;     // ya lo tengo puesto
	    spriteKeyActual = key;
	    getSpritesJugador(spriteKeyActual);          // recarga imágenes
	}
	
	
	public void reestablecerSprites() {
		
	}
	public void getSpritesJugador(String key)
	{
		String carpeta, prefijo, prefijoAtaque = null;

	    if ("normal".equals(key)) {
	        carpeta = "/spritesjugador/";
	        prefijo = "paco";
	    } else {
	        char pref = Character.toUpperCase(key.charAt(0));    // E, C, L, M…
	        System.out.println(pref);
	        carpeta = "/paquitoCobjetos/" + key + "/";
	        prefijo = "paco" + pref;
	        prefijoAtaque = "Pegar/";  
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
	    
	    // Cargar sprites de ataque (ejemplo para abajo)
	    if(!("normal".equals(key))) {
	    ataqueAbajo1     = setup1(carpeta + prefijoAtaque + prefijo + "Abajo1"); 
	    ataqueAbajo2     = setup1(carpeta + prefijoAtaque + prefijo + "Abajo2");
	    ataqueArriba1    = setup1(carpeta + prefijoAtaque + prefijo + "Arriba1");
	    ataqueArriba2    = setup1(carpeta + prefijoAtaque + prefijo + "Arriba2");
	    ataqueIzquierda1 = setup1(carpeta + prefijo + "EstaticoI1");
	    ataqueIzquierda2 = setup1(carpeta + prefijoAtaque + prefijo + "Izquierda2");
	    ataqueDerecha1   = setup1(carpeta + prefijo + "EstaticoD1");
	    ataqueDerecha2   = setup1(carpeta + prefijoAtaque + prefijo + "Derecha2");
	    }
    }
	
	int index=0;

	public void update() {	 
	    if (atacando) {
	        contadorAnimacionAtaque++;
	        contadorSprites(); 	        

	        // Si estamos en el frame de ataque (el segundo sprite) Y aún no hemos aplicado daño en este golpe
	        if (numeroSprite == FRAME_DE_DAÑO && !danioAplicadoEnEsteAtaque) {
	            aplicarDañoAZombiesCercanos();
	            danioAplicadoEnEsteAtaque = true; // Marcamos que el daño ya fue aplicado
	        }
	        
	        // Terminar la animación 
	        if (contadorAnimacionAtaque >= DURACION_FRAME_ATAQUE * 2) {
	            atacando = false;
	            contadorAnimacionAtaque = 0;
	            danioAplicadoEnEsteAtaque = false;	            
	        }	        
	        return; 
	    }

	    boolean moviendo = false;

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
 
	    // Iniciar Ataque 
	    if (mT.getTeclaAtacar() && (System.currentTimeMillis() - tiempoUltimoAtaque > COOLDOWN_ATAQUE)) {
	        atacando = true;
	        moviendo = false; 
	        danioAplicadoEnEsteAtaque = false;
	        contadorAnimacionAtaque = 0;
	        numeroSprite = 1;          
	        contadorSprites = 0;       
	        tiempoUltimoAtaque = System.currentTimeMillis();
	        //gP.playSE(5); 	        	      	        
	        return; 
	    }

	    // Correr 
	    if (modoRapido) {
	        contadorRapido++;
	        if (contadorRapido >= maxCiclosRapido) {
	            this.velocidad = velocidadBase;
	            modoRapido = false;
	            contadorRapido = 0;
	        }
	    } else if (mT.isTeclaCorrer() && moviendo) {
	        this.velocidad = velocidadBase + 5;
	    } else {
	        this.velocidad = velocidadBase;
	    }	    
	    am.sonidoCamina(moviendo, colisionOn, 0);
	    
	    if (!moviendo) {
	        if (this.direccion.equals("arriba")) this.direccion = "estaticoArriba";
	        else if (this.direccion.equals("abajo")) this.direccion = "estatico";
	        else if (this.direccion.equals("derecha")) this.direccion = "estaticoDerecha";
	        else if (this.direccion.equals("izquierda")) this.direccion = "estaticoIzquierda";
	    }

	    // Colisiones
	    this.colisionOn = false; 
	    gP.getchecadorColision().checkTile(this);
	    int obind = gP.getchecadorColision().checkObjeto(this, true);
	    meterInventario(obind); 
	    int zomind = gP.getchecadorColision().checarEntidad(this, gP.getZombie());
	    // Aquí podrías añadir lógica si un zombie te golpea: if(zomind != 999) { recibirDaño(...); }
	    
	    cambiarPantallaCombate(); 

	    
	    if (!colisionOn && moviendo) {
	        switch (direccion) {
	            case "arriba":
	                if (this.mundoY - this.velocidad >= 0) this.mundoY -= this.velocidad;
	                else this.mundoY = 0;
	                break;
	            case "abajo":	               
	                if (this.mundoY + this.velocidad + gP.getTamanioTile() <= gP.altoMundo) this.mundoY += this.velocidad;
	                else this.mundoY = gP.altoMundo - gP.getTamanioTile();
	                break;
	            case "izquierda":
	                if (this.mundoX - this.velocidad >= 0) this.mundoX -= this.velocidad;
	                else this.mundoX = 0;
	                break;
	            case "derecha":	                
	                if (this.mundoX + this.velocidad + gP.getTamanioTile() <= gP.anchoMundo) this.mundoX += this.velocidad;
	                else this.mundoX = gP.anchoMundo - gP.getTamanioTile();
	                break;
	        }
	    }
	    
	    contadorSprites();
	}
	  
	
    //SALTO   
	
	private int velocidadY = 0;     // Velocidad vertical
	private boolean saltando = false; // Si está saltando
	private final int sueloY = 400;     // Piso (posicion Y donde está el suelo)
	private final int fuerzaSalto = -15; // Velocidad inicial al saltar (negativo para subir)
	private final int gravedad = 1;  
		
	public void updateCombate() {
	    boolean moviendoHorizontalmente = false; 
	    

	    // LEER ENTRADAS DEL TECLADO PARA COMBATE
	    if (mT.getTeclaIzquierda()) {
	        this.direccion = "izquierda";
	        moviendoHorizontalmente = true;
	    } else if (mT.getTeclaDerecha()) {
	        this.direccion = "derecha";
	        moviendoHorizontalmente = true;
	    }

	  
	    if (!moviendoHorizontalmente) {
	        if (this.direccion.equals("izquierda") || this.direccion.equals("derecha")) {
	            this.direccion = "estatico";
	        }
	    }
	    
	    // LÓGICA DE MOVIMIENTO HORIZONTAL EN COMBATE && COLICIONES
	    switch (this.direccion) {
	        case "izquierda":	            
	            if (this.mundoX - velocidad >= 0) {
	                this.mundoX -= velocidad;
	            } else {
	                this.mundoX = 0; 
	            }
	            break;
	        case "derecha":
	            if (this.mundoX + velocidad + gP.getTamanioTile() <= gP.getAnchoPantalla()) {
	                this.mundoX += velocidad;
	            } else {
	                this.mundoX = gP.getAnchoPantalla() - gP.getTamanioTile(); // No puede pasar del borde derecho
	            }
	            break;
	    }

	    // LÓGICA DE SALTO
	    if (mT.getTeclaSaltar() && !saltando) { 
	        this.direccion = "saltar"; 
	        velocidadY = fuerzaSalto;
	        saltando = true;
	        // gP.playSE(); // Sonido de salto
	    }

	    mundoY += velocidadY;
	    if (saltando) {
	        velocidadY += gravedad;
	    }

	    if (mundoY >= sueloY) {
	        mundoY = sueloY;
	        velocidadY = 0;
	        if (saltando) { 
	            saltando = false;	            
	            if (!moviendoHorizontalmente) {
	                this.direccion = "estatico";
	            }
	        }
	    }
	    
	    // DISPARO DE PROYECTILES
	    if (mT.getTeclaDisparar() && (System.currentTimeMillis() - tiempoUltimoDisparo > COOLDOWN_DISPARO)) {
	        tiempoUltimoDisparo = System.currentTimeMillis();

	        // 1. Crear el proyectil inactivo
	        Proyectil newP = new Proyectil(gP);
	        // 2. Activarlo con sus propiedades
	        newP.set(this, "/ProyectilesCombate/poderPaco", 2, 5, "derecha");
	        // 3. Añadirlo al juego
	        gP.getListaProyectilJugador().add(newP);
	    }	    	    
	    contadorSprites();
	}
	
	private void aplicarDañoAZombiesCercanos() {
	   
	    int attackAreaSize = gP.getTamanioTile(); // Cuán lejos y ancho es el golpe
	    int offsetX = 0;
	    int offsetY = 0;
	    
	    String dirBase = this.direccion;
	    if (atacando) { 	                   
	        if (this.direccion.contains("Arriba")) dirBase = "arriba";
	        else if (this.direccion.contains("Abajo")) dirBase = "abajo";
	        else if (this.direccion.contains("Izquierda")) dirBase = "izquierda";
	        else if (this.direccion.contains("Derecha")) dirBase = "derecha";
	    }


	    switch (dirBase) {
	        case "arriba":
	        case "estaticoArriba":
	            areaAtaque.setBounds(mundoX + solidArea.x, mundoY + solidArea.y - attackAreaSize, solidArea.width, attackAreaSize);
	            break;
	        case "abajo":
	        case "estatico":
	            areaAtaque.setBounds(mundoX + solidArea.x, mundoY + solidArea.y + solidArea.height, solidArea.width, attackAreaSize);
	            break;
	        case "izquierda":
	        case "estaticoIzquierda":
	            areaAtaque.setBounds(mundoX + solidArea.x - attackAreaSize, mundoY + solidArea.y, attackAreaSize, solidArea.height);
	            break;
	        case "derecha":
	        case "estaticoDerecha":
	            areaAtaque.setBounds(mundoX + solidArea.x + solidArea.width, mundoY + solidArea.y, attackAreaSize, solidArea.height);
	            break;
	    }

	   
	    double dañoInfligido = this.ataque; 
	    if (!"normal".equals(spriteKeyActual)) {
	       
	        Objeto itemEquipado = null;
	        for(Objeto obj : inventario.getObjetos()){ 
	            if(obj instanceof Equipable && ((Equipable)obj).getSpriteKey().equals(spriteKeyActual)){
	                itemEquipado = obj;
	                break;
	            }
	        }
	        if(itemEquipado instanceof Arma){
	            dañoInfligido = ((Arma)itemEquipado).getCantidadDanio();
	        }
	    }
	    

	    // Iterar sobre los zombis y aplicar daño
	    for (Zombie zombie : gP.getZombie()) { 
	        if (zombie != null && zombie.getVivo()) {
	            Rectangle zombieHitbox = new Rectangle(
	                zombie.getMundoX() + zombie.getSolidAreaDefaultX(),
	                zombie.getMundoY() + zombie.getSolidAreaDefaultY(),
	                zombie.getSolidArea().width,
	                zombie.getSolidArea().height
	            );

	            if (areaAtaque.intersects(zombieHitbox)) {
	            	gP.playSE(21);
	                zombie.recibirDaño(dañoInfligido);
	                System.out.println("Zombie golpeado! Vida restante: " + zombie.getVida()); // Para depuración
	            }
	        }
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
	
	private int pantallaX_previa;
	private int pantallaY_previa;
	private int mundoX_previo; 
	private int mundoY_previo;
	
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
	    		 //SE GUARDAN LOS VALORES ORIGINALES DE LAS PANTALLAS Y LOS MUNDOS 
	    		 this.vida = this.vidaMaxima;
	    		 cambiarSpriteCombate();       
	    		 direccion = "estatico";     
	    		 numeroSprite = 1; 
	    		 pantallaX_previa = this.pantallaX;
	    		 pantallaY_previa = this.pantallaY;
	    		 mundoY_previo = this.mundoY;
	    		 mundoX_previo = this.mundoX;
	    		 
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
		String dirActual = this.direccion;
		
		 if (atacando) {
		        // Determinar qué sprite de ataque usar basado en la dirección original y numeroSprite
		        String dirBase = "abajo"; // Dirección base por defecto
		        if (this.direccion.contains("Arriba")) dirBase = "arriba";
		        else if (this.direccion.contains("Abajo") || this.direccion.equals("estatico")) dirBase = "abajo"; // estatico por defecto mira abajo
		        else if (this.direccion.contains("Izquierda")) dirBase = "izquierda";
		        else if (this.direccion.contains("Derecha")) dirBase = "derecha";

		        switch (dirBase) {
		            case "arriba": sprite = (numeroSprite == 1) ? ataqueArriba1 : ataqueArriba2; break;
		            case "abajo": sprite = (numeroSprite == 1) ? ataqueAbajo1 : ataqueAbajo2; break;
		            case "izquierda": sprite = (numeroSprite == 1) ? ataqueIzquierda1 : ataqueIzquierda2; break;
		            case "derecha": sprite = (numeroSprite == 1) ? ataqueDerecha1 : ataqueDerecha2; break;
		            default: sprite = (numeroSprite == 1) ? ataqueAbajo1 : ataqueAbajo2; 
		        }
		    } else {
		        // Lógica de dibujado de movimiento/estático existente
		        switch (dirActual) {
		            case "arriba": sprite = (numeroSprite == 1) ? arriba1 : arriba2; break;
		            case "abajo": sprite = (numeroSprite == 1) ? abajo1 : abajo2; break;
		            case "izquierda": sprite = (numeroSprite == 1) ? izquierda1 : izquierda2; break;
		            case "derecha": sprite = (numeroSprite == 1) ? derecha1 : derecha2; break;
		            case "estatico": sprite = (numeroSprite == 1) ? estatico1 : estatico2; break;
		            case "estaticoArriba": sprite = (numeroSprite == 1) ? estaticoA1 : estaticoA2; break;
		            case "estaticoDerecha": sprite = (numeroSprite == 1) ? estaticoD1 : estaticoD2; break;
		            case "estaticoIzquierda": sprite = (numeroSprite == 1) ? estaticoI1 : estaticoI2; break;
		            default: sprite = estatico1; 
		        }
		    }
		
		int x = this.pantallaX; 
		int y = this.pantallaY; 
		
		bordesPantalla(x,y,sprite,g2);
	
		//g2.drawImage(sprite, x, y, gP.getTamanioTile(), gP.getTamanioTile(),null);
	}
	
	
	public void drawEnCombate(Graphics2D g2) {
		BufferedImage sprite = direcciones(); 
		
		
		pantallaY = mundoY;
		
		int x = this.pantallaX; 
		int y = this.pantallaY; 
		
		
		
		bordesPantalla(x,y,sprite,g2);
	
	}
	
	//SE AGREGARON VARIABLES DE COMBATE PARA NO MODIFICAR LAS ORIGINALES
	public void cambiarSpriteCombate() {
		this.estaticoCombate1 = setup1("/spritesjugador/paquitoCombate1");
		this.estaticoCombate2 = setup1("/spritesjugador/paquitoCombate2");
		this.derCombate1 = setup1("/spritesjugador/paquitoCombate1");
		this.derCombate2 = setup1("/spritesjugador/paquitoCombate2");
		this.izqCombate1 = setup1("/spritesjugador/paquitoCombate1");
		this.izqCombate2 =setup1("/spritesjugador/paquitoCombate2");
		this.saltar1 = setup1("/spritesjugador/paquitoCombate1");
		this.saltar2 =setup1("/spritesjugador/paquitoCombate2");
	}
	
	public BufferedImage direcciones() {
		BufferedImage sprite = null;
		
		switch(this.direccion) {
		case "estatico" : 
			if(this.numeroSprite == 1)
				sprite = this.estaticoCombate1; 
			if(this.numeroSprite == 2)
				sprite = this.estaticoCombate2; 
			break;	
		case "izquierda" : 
			if(this.numeroSprite == 1)
				sprite = this.izqCombate1; 
			if(this.numeroSprite == 2)
				sprite = this.izqCombate2; 
			break;
		case "derecha" : 
			if(this.numeroSprite == 1)
				sprite = this.derCombate1; 
			if(this.numeroSprite == 2)
				sprite = this.derCombate2; 
			break;	
		case "saltar" : 
			if(this.numeroSprite == 1)
				sprite = this.saltar1; 
			if(this.numeroSprite == 2)
				sprite = this.saltar2; 
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
						g2.drawImage(sprite, x, y,200,200, null);
					}else {
						g2.drawImage(sprite, x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
					}
					
				}else {
					if(pantallaY > this.mundoX ||
							pantallaY > this.mundoY ||
							rOffs > gP.anchoMundo - this.mundoX||
							bottomOffs > gP.altoMundo - this.mundoY) {
						
						if(gP.getGameState() == gP.getFightState()) {
							g2.drawImage(sprite, x, y,200,200, null);
						
						}else {
							g2.drawImage(sprite, x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
						}
						
						
						
						}
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
    
    
    //GUARDAR LOS VALORES PREVIOS DE LAS PANTALLAS Y LOS MUNDOS PARA QUE, CUANDO SE REGRESE AL MODO EXPLORATORIO
    //NO MODIFIQUE NADOTA
	public int getPantallaX_previa() {
		return pantallaX_previa;
	}
	public void setPantallaX_previa(int pantallaX_previa) {
		this.pantallaX_previa = pantallaX_previa;
	}
	public int getPantallaY_previa() {
		return pantallaY_previa;
	}
	public void setPantallaY_previa(int pantallaY_previa) {
		this.pantallaY_previa = pantallaY_previa;
	}
	public int getMundoX_previo() {
		return mundoX_previo;
	}
	public void setMundoX_previo(int mundoX_previo) {
		this.mundoX_previo = mundoX_previo;
	}
	public int getMundoY_previo() {
		return mundoY_previo;
	}
	public void setMundoY_previo(int mundoY_previo) {
		this.mundoY_previo = mundoY_previo;
	}
	
    
    

}
