package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Inventario.Objeto;
import entidad.Jugador;
import entidad.Zombie;
import tile.ManejadorTiles;

public class GamePanel extends JPanel implements Runnable
{
	// CONFIGURACIÓN PANTALLA
	private final int tamanioOriginalTile = 16;
	private final int escala = 3;
	private final int tamanioTile = tamanioOriginalTile * escala;
	private final int maxRenPantalla = 15;
	private final int maxColPantalla = 26;
	private final int anchoPantalla = tamanioTile * maxColPantalla;
	private final int altoPantalla = tamanioTile * maxRenPantalla;
		
		Thread hebraJuego;
		Ambientacion musica = new Ambientacion(this);
		Ambientacion se = new Ambientacion(this);
		ManejadorTeclas mT = new ManejadorTeclas(this);
		Jugador jugador = new Jugador(this, mT,se,"normal");
		ManejadorTiles mTi =new ManejadorTiles(this);
		ChecadorColision cC = new ChecadorColision(this);
		//Inventario inv = new Inventario();
		Objeto o[] = new Objeto[15];
		Zombie z[] = new Zombie[8];
		AssetSetter asSet = new AssetSetter(this);
		UI ui = new UI(this);
		
		//GAME STATE
		protected int gameState; 
		protected final int pantallaInicio = 0;
		protected final int playState = 1;
		protected final int pauseState = 2;
		protected final int pantallaSetting = 3;
		protected final int pantallaInfo = 4; 
		protected final int pantallaDecision = 5;
		
	//WORLD SETTINGS
	public final int maxColMundo = 42; 
	public final int maxRenMundo = 109; 
	public final int anchoMundo = tamanioTile * maxColMundo;
	public final int altoMundo = tamanioTile * maxRenMundo;
	
	int FPS = 60;
		
		public GamePanel()
		{
			this.setPreferredSize(new Dimension(this.anchoPantalla, this.altoPantalla));
			this.setBackground(Color.black);
			this.setDoubleBuffered(true);
			this.addKeyListener(mT);
			this.setFocusable(true);
			
			new javax.swing.Timer(100, e -> { //esto disminuye cada 20 min la vida
				jugador.dañoInfeccion(0.5);
				repaint();
			}).start();
			

		}
		
		
		public void setupGame() {
			asSet.setObject();
			asSet.setObjectZ();
			playMusic(4);
			gameState = pantallaInicio;
			
		}
		
		
		
		
		public void iniciaHebraJuego()
		{
			hebraJuego=new Thread(this);
			hebraJuego.start();
		}
		@Override
		public void run()
		{
			double intervaloDibujo = 1000000000 / FPS;
			double delta = 0;
			long ultimaVez = System.nanoTime();
			long tiempoActual;
			while(hebraJuego != null) 
			{
				tiempoActual = System.nanoTime();
				delta += (tiempoActual - ultimaVez) / intervaloDibujo;
				ultimaVez = tiempoActual;
				if(delta >=1)
				{
					update();
					repaint();
					delta --;
				}
			}
		}
		
		private boolean alarme=false;
		public void update() 
		{
			if(gameState == playState) {
				jugador.update();
				for(int i = 0; i < z.length; i++) {
					if(z[i] != null) {
						z[i].update();
					}
				}
			detenerActivarAlarma();
			}
			
			if(gameState == pauseState) {
	
			}
			// toggle inventario
			if (mT.getTeclaInventario()) {
		        ui.setInventorOpen(!ui.inventoryOpen);
		        mT.setTeclaInventario(false);
			}
			
		}
		
		//checar como hacer que, si saca x objeto, el sprite csmbie al siguente y así susecivamente hasta que quede vacio con tS = "normal"
	
       public void detenerActivarAlarma() {
    	   if(jugador.getVida() <=20) {
				if(!alarme) {
				playMusic(15);
				alarme = true;
				}
			}else {
				if(alarme) {
					stopMusic();
					alarme=false;
				}
			}
			
			if(jugador.getVida() == 0) {
				stopMusic();
				gameState = pantallaDecision;
			}
       }

		@Override
		public void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    Graphics2D g2 = (Graphics2D) g;
		    
		    //pantalla de inicio 
		    if(gameState == pantallaInicio) {
		    	ui.draw(g2);
		    }else {
		    	// 1) Mundo + jugador + objetos
		    	mTi.draw(g2);
		    	for(int i= 0; i < o.length; i++) {
		    		if(o[i] != null) {
		    			o[i].draw(g2,this);
		    		}
		    	}
		    	for(int i = 0; i < z.length; i++) {
		    		if(z[i] != null) {
		    			z[i].draw(g2);
		    		}
		    	}
		    	jugador.draw(g2);
		    
		    	// 2) Inventario encima, si está abierto
		    	if (ui.getInventorOpen()) {
		    		ui.dibujarInventario(g2);
		    	}
		    	
		    	if(gameState == pantallaSetting) {
		    	ui.draw(g2);
		    	}
		    	ui.draw(g2);
		    }
		  
		    
		    //ui.mostrarTiempo(g2);

		    
		  //  g2.dispose();
		}
		
		public void playMusic(int i) {
			musica.setFile(i);
			musica.play();
			musica.loop();
			
		}
		
		public void stopMusic() {
			musica.stop();
		}
		
		public void playSE(int i) {
			se.setFile(i);
			se.play();
		}	
		
		public int getTamanioOriginalTile()
		{
			return this.tamanioOriginalTile;
		}
		public int getEscala()
		{
			return escala;
		}
		public int getTamanioTile()
		{
			return this.tamanioOriginalTile*this.escala;
		}
		public int getMaxRenPantalla()
		{
			return maxRenPantalla;
		}
		public int getMaxColPantalla()
		{
			return maxColPantalla;
		}
		public int getAnchoPantalla()
		{
			return this.anchoPantalla;
		}
		
	
		public int getAltoPantalla()
		{
			return this.altoPantalla;
		}
		
		
		public Jugador getJugador() {
			return jugador;
		}
		public void setJugador(Jugador jugador) {
			this.jugador = jugador;
		}
		
		
		public ChecadorColision getchecadorColision() {
			return cC;
		}
		public void setcC(ChecadorColision cC) {
			this.cC = cC;
		}
		
		
		public ManejadorTiles getmTi() {
			return mTi;
		}
		public void setmTi(ManejadorTiles mTi) {
			this.mTi = mTi;
		}
		
		public Objeto[] getObjetoInv() {
			return this.o;
		}

		public Zombie[] getZombie() {
			return this.z;
		}
		

		public int getAnchoMundo() {
			return anchoMundo;
		}


		public int getAltoMundo() {
			return altoMundo;
		}

		

		public int getGameState() {
			return gameState;
		}


		public void setGameState(int gameState) {
			this.gameState = gameState;
		}


		public int getPlayState() {
			return playState;
		}


		public int getPauseState() {
			return this.pauseState;
		}
		
		public int getPantallaInicio() {
			return this.pantallaInicio;
		}

		public int getPantalaSetting() {
			return this.pantallaSetting;
		}
		
		
		public int getPantallaInfo() {
			return this.pantallaInfo;
		}
		
		public int getPantallaDecision() {
			return this.pantallaDecision;
		}
		
		public UI getUi() {
			return ui;
		}

		public void setUi(UI ui) {
			this.ui = ui;
		}
		 
		public AssetSetter getAssS() {
			return this.asSet;
		}
		
		public Ambientacion getAmbientacion() {
			return this.se;
		}
		
	
	
	
	
}
