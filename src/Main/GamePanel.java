package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import Inventario.Arma;
import Inventario.Coins;
import Inventario.Comida;
import Inventario.Inventario;
import Inventario.Objeto;
import entidad.Jugador;
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
	AssetSetter asSet = new AssetSetter(this);
	UI ui = new UI(this);
		
	//GAME STATE
	protected int gameState; 
	protected final int pantallaInicio = 0;
	protected final int playState = 1;
	protected final int pauseState = 2;
		 
		
	//WORLD SETTINGS
	public final int maxColMundo = 50; 
	public final int maxRenMundo = 50; 
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
			
			new javax.swing.Timer(12000000, e -> {
				jugador.dañoInfeccion(1);
				repaint();
			}).start();
		}
		
		
		public void setupGame() {
			asSet.setObject();
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
		public void update() 
		{
			if(gameState == playState) {
				jugador.update();
			}
			
			if(gameState == pauseState) {
	
			}
			// toggle inventario
			if (mT.getTeclaInventario()) {
			    //inventoryOpen = !inventoryOpen;
				ui.setInventorOpen(!ui.inventoryOpen);
			    mT.setTeclaInventario(false);
			}

			// si está abierto, navegar con flechas y seleccionar con Enter
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
		    	jugador.draw(g2);
		    
		    	// 2) Inventario encima, si está abierto
		    	if (ui.getInventorOpen()) {
		    		ui.drawInventory(g2);
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


		public UI getUi() {
			return ui;
		}


		public void setUi(UI ui) {
			this.ui = ui;
		}
		
	
		
	
	
	
	
}
