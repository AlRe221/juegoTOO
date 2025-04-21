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
	//Configuracion de pantalla
		private final int tamanioOriginalTile = 16;
		private final int escala = 3;
		private final int tamanioTile = tamanioOriginalTile * escala;
		private final int maxRenPantalla = 15;
		private final int maxColPantalla = 26;
		private final int anchoPantalla = tamanioTile * maxColPantalla;
		private final int altoPantalla = tamanioTile * maxRenPantalla;
		
		
		// Inventario
		private boolean inventoryOpen = false;
		private int     inventoryCursor = 0;

		
		Thread hebraJuego;
		Ambientacion sonido = new Ambientacion(this);
		ManejadorTeclas mT = new ManejadorTeclas();
		Jugador jugador = new Jugador(this, mT,sonido);
		ManejadorTiles mTi =new ManejadorTiles(this);
		ChecadorColision cC = new ChecadorColision(this);
		//Inventario inv = new Inventario();
		Objeto o[] = new Objeto[8];
		AssetSetter asSet = new AssetSetter(this);
		
		
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
		
		
		public void setupGame() {
			asSet.setObject();
			playMusic(2);
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
			// toggle inventario
			if (mT.getTeclaInventario()) {
			    inventoryOpen = !inventoryOpen;
			    mT.setTeclaInventario(false);
			}

			// si está abierto, navegar con flechas y seleccionar con Enter
			if (inventoryOpen) {
			    List<Objeto> items = jugador.getInventario().getObjetos();
			    int size = Math.max(1, items.size());

			    if (mT.getTeclaArribaInv()) {
			        inventoryCursor = (inventoryCursor - 1 + size) % size;
			        mT.setTeclaArribaInv(false);
			    }
			    if (mT.getTeclaAbajoInv()) {
			        inventoryCursor = (inventoryCursor + 1) % size;
			        mT.setTeclaAbajoInv(false);
			    }
			    if (mT.getTeclaEnter()) {
			        // ejecuta acción según tipo
			        if (!items.isEmpty()) {
			            Objeto sel = items.get(inventoryCursor);
			            if (sel instanceof Comida) {    
			            	jugador.usarComida();
			              items.remove(sel);
			            }else if (sel instanceof Arma) {  
			            	jugador.equiparArma();
			            	items.remove(sel);
			            }else if (sel instanceof Coins) {
			                ((Coins)sel).incrementoOro();
			                System.out.println("Monedas: " + ((Coins)sel).getCoin());
			                items.remove(sel);
			            }
			        }
			        mT.setTeclaEnter(false);
			    }
			}
		}
		@Override
		public void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    Graphics2D g2 = (Graphics2D) g;

		    // 1) Mundo + jugador + objetos
		    mTi.draw(g2);
		    for(int i= 0; i < o.length; i++) {
		    	if(o[i] != null) {
		    		o[i].draw(g2,this);
		    	}
		    }
		    jugador.draw(g2);
		    
		    // 2) Inventario encima, si está abierto
		    if (inventoryOpen) {
		        drawInventory(g2);
		    }
		    

		    g2.dispose();
		}
		
		public void playMusic(int i) {
			sonido.setFile(i);
			sonido.play();
			sonido.loop();
			
		}
		
		public void stopMusic() {
			sonido.stop();
		}
		
		public void playSE(int i) {
			sonido.setFile(i);
			sonido.play();
		}
		

		private void drawInventory(Graphics2D g2) {
		    int x = 50, y = 50, w = 300, h = 200;
		    g2.setColor(new Color(0, 0, 0, 180));
		    g2.fillRect(x, y, w, h);
		    g2.setColor(Color.WHITE);
		    g2.drawRect(x, y, w, h);
		    g2.drawString("INVENTARIO", x + 10, y + 20);

		    List<Objeto> objs = jugador.getInventario().getObjetos();
		    int offsetY = 40;

		    if (objs.isEmpty()) {
		        g2.drawString("   (vacío)", x + 10, y + offsetY);
		        return;
		    }
		    for (int i = 0; i < objs.size(); i++) {
		        if (i == inventoryCursor) {
		            g2.drawString(">", x + 5, y + offsetY + i * 20);
		        }
		        g2.drawString((i + 1) + ". " + objs.get(i).getTipoObjeto(),
		                      x + 20, y + offsetY + i * 20);
		    }
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


		public Ambientacion getSonido() {
			return sonido;
		}


		public void setSonido(Ambientacion sonido) {
			this.sonido = sonido;
		}
	
	
		
		
		
	
	
	
	
	
}
