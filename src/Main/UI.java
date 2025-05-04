package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.List;

import Inventario.Coins;
import Inventario.Objeto;

public class UI {
   protected GamePanel gP; 
   protected Graphics2D g2;
   protected BufferedImage image;
   protected boolean inventoryOpen = false;
   protected int     inventoryCursor = 0;
   protected Font arial_30;
   protected double playTime; 
   DecimalFormat dFormat = new DecimalFormat("#0.00");
   private boolean tiempoActivo = true;  // variable de control
   protected int numCommand = 0;
   public int espacioCol = 0;
   
   

   
   public UI(GamePanel gP) {
	   this.gP = gP;
	   arial_30 = Tipografia.cargaFuente(20f);
	    
   }
   
   public void draw(Graphics2D g2) {
	   this.g2 = g2;
	   g2.setFont(arial_30);
	   g2.setColor(Color.white);
	   
	   //pantalla inicio
	   if(gP.getGameState() == gP.getPantallaInicio()) {
		   mostrarPantallaInicio(g2);
	   }
	   
	   
	   if(gP.getGameState() == gP.getPlayState()) {
		   activarTiempo();
		   mostrarTiempo(g2);
		   mostrarBarraVida(g2);
	   }
	   
	   if(gP.getGameState() == gP.getPauseState()) {
		   detenerTiempo();
		   mostrarTiempo(g2);
		   mostrarPantallaPausa();
		   
	   }
	   
   }
   
   public void mostrarPantallaInicio(Graphics2D g2) {
	   //esto solo es de prueba, voy a hacer un dibujo para el fondo y lo voy a poner como 
	   //background
	   g2.setFont(Tipografia.cargaFuente(50F));
	   String text = "UASLP APOCALYPSIS- PART 1"; 
	   int x = getXparaCentro(text); 
	   int y = gP.getTamanioTile() * 3;
	   g2.setColor(Color.white);
	   g2.drawString(text, x, y);
	   
	   //menu
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
	   text = "START";
	   x = getXparaCentro(text); 
	   y += gP.getTamanioTile() * 4;
	   g2.drawString(text, x, y);
	   
	   if(numCommand == 0) {
		   g2.drawString("-", x - gP.getTamanioTile(), y);
	   }
	   
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
	   text = "SETTINGS";
	   x = getXparaCentro(text); 
	   y += gP.getTamanioTile() *2;
	   g2.drawString(text, x, y);
	   
	   
	   if(numCommand == 1) {
		   g2.drawString("-", x - gP.getTamanioTile(), y);
	   }
	   
	   g2.setFont(Tipografia.cargaFuente(30F));
	   text = "INFO";
	   x = getXparaCentro(text); 
	   y += gP.getTamanioTile()*2;
	   g2.drawString(text, x, y);
	   
	   
	   if(numCommand == 2) {
		   g2.drawString("-", x - gP.getTamanioTile(), y);
	   }
	   
	   
	   g2.setFont(Tipografia.cargaFuente(20F));
	   text = "EXIT";
	   x = gP.getTamanioTile() ; 
	   y += gP.getTamanioTile() * 3;
	   g2.drawString(text, x, y);
	   
	   
	   if(numCommand == 3) {
		   g2.drawString("  -", x - gP.getTamanioTile(), y);
	   }
	   
	  
   }
   
   public void mostrarPantallaPausa() {
	   int x = 120, y = 100, w =1000, h = 500;
	    g2.setColor(new Color(0, 0, 0, 180));
	    g2.fillRect(x, y, w, h);
	    g2.setColor(Color.WHITE);
	    g2.drawRect(x, y, w, h);
	   g2.setFont(Tipografia.cargaFuente(80f));
	   String text ="PAUSED"; 
	   int x2 = getXparaCentro(text) ;
	   int y2 = gP.getTamanioTile() * 5;  
	   g2.drawString(text, x2, y2);
	   
	   g2.setFont(Tipografia.cargaFuente(40f));
	   text = "EXIT";
	   x2 = getXparaCentro(text); 
	   y2 += gP.getTamanioTile() * 4;
	   g2.drawString(text, x2, y2);
	   
	   if(numCommand == 0) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   }
	   
	   g2.setFont(Tipografia.cargaFuente(40f));
	   text = "CONTINUE";
	   x2 = getXparaCentro(text); 
	   y2 += gP.getTamanioTile() *2;
	   g2.drawString(text, x2, y2);
	   
	   
	   if(numCommand == 1) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   }
	   
   }
   
   
   public int getXparaCentro(String text) {
	   int tam = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	   int x = gP.getAnchoPantalla()/2 - tam/2;
	   return x;
   }
   
   public void mostrarTiempo(Graphics2D g2) {
	   g2.setFont(arial_30);
	   g2.setColor(Color.WHITE);
	   
	   if(tiempoActivo == true) {
	   playTime += (double)1/60;
	   }
	   
	   g2.drawString("TIME: " + dFormat.format(playTime), gP.getTamanioTile()*21,66);
   }
   
   public void detenerTiempo() {
	  this.tiempoActivo = false; 
   }
   
   public void activarTiempo() {
	  this.tiempoActivo = true; 
   }
   
   
   public void mostrarBarraVida(Graphics2D g2) {
	   g2.setFont(Tipografia.cargaFuente(15f));
	   g2.setColor(Color.WHITE);
	   g2.drawString("LIFE ",20,38);
	   
	   int x = 80, y = 20, width = 300, heigth = 20;
	   
	   int vidaActual = gP.getJugador().getVida();
	   int vidM = gP.getJugador().getVidaMax(); 
	   int fillWi =(int)((double)vidaActual / vidM * width);
	   
	   g2.setColor(Color.GRAY); 
	   g2.fillRect(x, y, width, heigth);
	   
	   g2.setColor(Color.RED);
	   g2.fillRect(x, y, fillWi, heigth);
	   
	   g2.setColor(Color.BLACK);
	   g2.drawRect(x, y, width, heigth);
   }
   
  
   
   public void drawInventory(Graphics2D g2) {

	    // CUADRADO
	    int x = gP.getTamanioTile() * 14; // empieza 14 tiles desde la izquierda
	    int y = gP.getTamanioTile() * 2;  // un poquito más abajo (ajústalo a tu gusto)
	    int ancho = gP.getAnchoPantalla() - (gP.getTamanioTile() * 19); // ancho de pantalla menos 19 tiles
	    int alto = gP.getTamanioTile() * 5;
	    
	    dibujarVentana(g2, x, y, ancho, alto);

	    // ESPACIOS
	    final int casillaXInicio = x + 20;
	    final int casillaYInicio = y + 20;

	    int casillaX = casillaXInicio;
	    int casillaY = casillaYInicio;

	    int cursorX = casillaXInicio + (gP.getTamanioTile() * espacioCol);
	    int cursorY = casillaYInicio + (gP.getTamanioTile() * espacioCol);
	    int cursorAncho = gP.getTamanioTile();
	    int cursorAlto = gP.getTamanioTile();

	    g2.setColor(Color.white);
	    g2.setStroke(new BasicStroke(3));
	    g2.drawRoundRect(cursorX, cursorY, cursorAncho, cursorAlto, 10, 10);
	}

	    /*int x = 50, y = 50, w = 300, h = 200;
	    g2.setFont(Tipografia.cargaFuente(10f));
	    g2.setColor(new Color(0, 0, 0, 180));
	    g2.fillRect(x, y, w, h);
	    g2.setColor(Color.WHITE);
	    g2.drawRect(x, y, w, h);
	    g2.drawString("INVENTARIO", x + 10, y + 20);

	    List<Objeto> objs = gP.getJugador().getInventario().getObjetos();
	    int offsetY = 40;
	    int offsety2 = 5;

	    if (objs.isEmpty()) {
	        g2.drawString("   (vacío)", x + 10, y + offsetY);
	        return;
	    }
	    for (int i = 0; i < objs.size(); i++) {
	        if (i == inventoryCursor) {
	            g2.drawString("-", x + 5, y + offsetY + i * 20);
	        }
	        g2.drawString((i+1) + ". " + objs.get(i).getTipoObjeto(), x + 20, y + offsetY + i * 20);
	    }
	}*/
	
   private void dibujarVentana(Graphics2D g2, int x, int y, int ancho, int alto) {
	    Color bg = new Color(0, 0, 0, 210);
	    g2.setColor(bg);
	    g2.fillRoundRect(x, y, ancho, alto, 35, 35);

	    g2.setStroke(new BasicStroke(5));
	    g2.setColor(Color.white);
	    g2.drawRoundRect(x + 5, y + 5, ancho - 10, alto - 10, 25, 25);
	}

	public void setInventorOpen(boolean valor) {
		this.inventoryOpen = valor;
	}
	
	public boolean getInventorOpen() {
		return this.inventoryOpen;
	}
	
	public void setInventorCursor(int val) {
		this.inventoryCursor = val;
	}
	
	public int getInventorCursor(){
		return this.inventoryCursor;
	}
	
	public int getNumCom() {
		return this.numCommand;
	}
	public void setNUmCom(int v) {
		this.numCommand =v;
	}
}
