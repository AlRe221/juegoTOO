package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entidad.Jugador;
import tile.ManejadorTiles;

public class GamePanel extends JPanel implements Runnable
{
	//Configuracion de pantalla
		private final int tamanioOriginalTile = 16;
		private final int escala = 3;
		private final int tamanioTile = tamanioOriginalTile * escala;
		private final int maxRenPantalla = 15;
		private final int maxColPantalla = 26;
		private final int anchoPantalla = tamanioTile * maxColPantalla;
		private final int altoPantalla = tamanioTile * maxRenPantalla;
		
		Thread hebraJuego;
		
		ManejadorTeclas mT = new ManejadorTeclas();
		Jugador jugador = new Jugador(this, mT);
		ManejadorTiles mTi =new ManejadorTiles(this);
		ChecadorColision cC = new ChecadorColision(this);
		
		//world settings
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
			jugador.update();
		}
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			mTi.draw(g2);
			jugador.draw(g2);
			g2.dispose();
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
		
}
