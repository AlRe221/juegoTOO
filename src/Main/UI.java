package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
   public int espacioRen = 0;
   public static final int MAX_REN = 2; // Ejemplo: 4 filas
   public static final int MAX_COL = 4; // Ejemplo: 6 columnas
       
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
	   
	   //settings
	   if(gP.getGameState() == gP.getPantalaSetting()) {
		   mostrarPantallaSettings(g2);
	   }
	   
	   //info
	   if(gP.getGameState() == gP.getPantallaInfo()) {
		   mostrarPantallaInfo(g2);
	   }
	   
	   //pantalla de juego
	   if(gP.getGameState() == gP.getPlayState()) {
		   dibujarAlarma();
		   dibujarCuadroJ();
		   mostrarBarraVida(g2);
	   }
	   
	   //pantalla de pausa
	   if(gP.getGameState() == gP.getPauseState()) {
		   mostrarPantallaPausa();   
	   }
	   
	  //pantalla de decisión
	   if(gP.getGameState() == gP.getPantallaDecision()) {
		   mostrarPantallaDecision(g2);
	   }
	   
	   //pantalla de win
	   if(gP.getGameState() == gP.getWin()) {
		   mostrarWin(g2);
	   }
	   
	   //pantalla game over
	   if(gP.getGameState() == gP.getgameOver1()) {
		   mostrarGameOver(g2);
	   }
	   
	   if(gP.getGameState() == gP.getgameOver2()) {
		   mostrarGameOver(g2);
	   }
	   
	    
   }
   
   public void mostrarPantallaInicio(Graphics2D g2){
	   //esto solo es de prueba, voy a hacer un dibujo para el fondo y lo voy a poner como 
	   //background
	   try {
		   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/juegoPortadafinal.png"));
		   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	   }catch(Exception e) {   
	   }
	   
	
	   
	   //menu
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
	   String text = "START";
	   int x = getXparaCentro(text); 
	   int y = gP.getTamanioTile() * 6;
	   g2.drawString(text, x, y);
	   
	   if(numCommand == 0) {
		   g2.drawString("-", x - gP.getTamanioTile(), y);
	   }
	   
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
	   text = "CONTROLES";
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
   
   public void mostrarPantallaSettings(Graphics2D g2) {
	   
	   try {
		   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/juegoPortadaControles.png"));
		   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	   }catch(Exception e) {   
	   }
	  
	   
	   g2.setFont(Tipografia.cargaFuente(20F));
	  String text = "EXIT";
	 int x = gP.getTamanioTile()*3 ; 
	   int y = gP.getTamanioTile() *13;
	   g2.drawString(text, x, y);
	   
	   setNUmCom(0);
	
	   if(numCommand == 0) {
		   g2.drawString("  -", x - gP.getTamanioTile(), y);
	   } 
   }
   
  
   public void mostrarPantallaInfo(Graphics2D g2) {
	   try {
		   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/juegoPortadaInfo.png"));
		   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	   }catch(Exception e) {   
	   }
	  
	   
	   g2.setFont(Tipografia.cargaFuente(20F));
	  String text = "EXIT";
	 int x = gP.getTamanioTile()*3 ; 
	   int y = gP.getTamanioTile() *14;
	   g2.drawString(text, x, y);
	   
	   setNUmCom(0);
	
	   if(numCommand == 0) {
		   g2.drawString("  -", x - gP.getTamanioTile(), y);
	   } 
   }
   
   public void mostrarPantallaPausa() {
	   cuadroCentro();
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
   
   public void  mostrarPantallaDecision(Graphics2D g2) {
	   g2.setColor(Color.BLACK);
	   g2.fillRect(0, 0, gP.getWidth(), gP.getHeight());
	  cuadroCentro();
	  
	  g2.setFont(Tipografia.cargaFuente(30f));
	   String text ="¿Como se origino el contagio?"; 
	   int x2 = getXparaCentro(text) ;
	   int y2 = gP.getTamanioTile() * 4;  
	   g2.drawString(text, x2, y2);
	   
	   g2.setFont(Tipografia.cargaFuente(15f));
	   text = "Falta de agua creo un hongo en el baño de hombres.";
	   x2 = getXparaCentro(text); 
	   y2 += gP.getTamanioTile() * 3;
	   g2.drawString(text, x2, y2);
	   
	   if(numCommand == 0) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   }
	   
	   g2.setFont(Tipografia.cargaFuente(15f));
	   text = "Bolsas de atun viejas en el DFM causaron caos.";
	   
	   x2 = getXparaCentro(text); 
	   y2 += gP.getTamanioTile() *2;
	   g2.drawString(text, x2, y2);
	   
	   
	   if(numCommand == 1) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   }
	   
	   g2.setFont(Tipografia.cargaFuente(15f));
	   text = "Estrés de fin de semestre propago una infeccion.";
	   x2 = getXparaCentro(text); 
	   y2 += gP.getTamanioTile() *2;
	   g2.drawString(text, x2, y2);
	   
	   
	   if(numCommand == 2) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   }
	   
   }
   
   public void mostrarWin(Graphics2D g2) {
	   try {
		   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/pantallaWin.png"));
		   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	   }catch(Exception e) {   
	   }
	  
	   
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
	   String text = "VOLVER AL INICIO";
	   int x2= getXparaCentro(text); 
	   int y2 = gP.getTamanioTile() * 11;
	   g2.drawString(text, x2, y2);
	   
	   if(numCommand == 0) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   } 
   }
   
   public void mostrarGameOver(Graphics2D g2) {
	   try {
		   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/gameOver.png"));
		   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	   }catch(Exception e) {   
	   }
	  
	   
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
	   String text = "VOLVER AL INICIO";
	   int x2= getXparaCentro(text); 
	   int y2 = gP.getTamanioTile() * 11;
	   g2.drawString(text, x2, y2);
	   
	   if(numCommand == 0) {
		   g2.drawString("-", x2 - gP.getTamanioTile(), y2);
	   } 
	}
   
      
  
   public void cuadroCentro() {
	   int x = 120, y = 100, w =1000, h = 500;
	    g2.setColor(new Color(0, 0, 0, 180));
	    g2.fillRect(x, y, w, h);
	    g2.setColor(Color.WHITE);
	    g2.drawRect(x, y, w, h);
   }
   
   public int getXparaCentro(String text) {
	   int tam = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	   int x = gP.getAnchoPantalla()/2 - tam/2;
	   return x;
   }
     
   
   public void mostrarBarraVida(Graphics2D g2) {
	   g2.setFont(Tipografia.cargaFuente(15f));
	   g2.setColor(Color.WHITE);
	   g2.drawString("LIFE ",20,38);
	   
	   int x = 80, y = 20, width = 300, heigth = 20;
	   
	   double vidaActual = gP.getJugador().getVida();
	   double vidM = gP.getJugador().getVidaMax(); 
	   if(vidaActual > vidM) {
		   vidaActual = vidM;
	   }
	   
	   double rawPct = (vidaActual / vidM) * 100.0;
	   int pct = (int) (Math.round(rawPct / 5.0) * 5);
	   int fillWi =(int)((double)vidaActual / vidM * width);
	 
	   
	   g2.setColor(Color.GRAY); 
	   g2.fillRect(x, y, width, heigth);
	   
	   g2.setColor(Color.RED);
	   g2.fillRect(x, y, fillWi, heigth);
	   
	   g2.setColor(Color.BLACK);
	   g2.drawRect(x, y, width, heigth);
	   
	   g2.setColor(Color.WHITE);
    g2.drawString(pct + "%", x + width + 10, y + heigth - 2);
   }
   
  
   
   public void dibujarInventario(Graphics2D g2) {

	    // CUADRADO
	    int x = gP.getTamanioTile() * 13; // empieza 14 tiles desde la izquierda
	    int y = gP.getTamanioTile() * 4;  // un poquito más abajo (ajústalo a tu gusto)
	    int ancho = gP.getAnchoPantalla() - (gP.getTamanioTile() * 21); // ancho de pantalla menos 19 tiles
	    int alto = gP.getTamanioTile() * 3;
	    
	    dibujarVentana(g2, x, y, ancho, alto);
	    
	    // CURSOR
	    
	    List<Objeto> lista = gP.getJugador().getInventario().getObjetos();

	    // 2) Posición inicial de la primera casilla
	    final int casillaXInicio = x + 20;
	    final int casillaYInicio = y + 20;
	    int slotX = casillaXInicio;
	    int slotY = casillaYInicio;

	    // 3) Recorremos y dibujamos
	    for (int i = 0; i < lista.size(); i++) {
	        Objeto obj = lista.get(i);
	        BufferedImage icon = obj.getImage();              // o obj.getImage() si lo tienes getter
	        g2.drawImage(icon, slotX, slotY, 
	                     gP.getTamanioTile(), gP.getTamanioTile(), null);
	        
        // avanzamos columna
        slotX += gP.getTamanioTile();
        // si llegamos al final de la fila, saltamos a la siguiente
        if ((i + 1) % MAX_COL == 0) {
            slotX = casillaXInicio;
            slotY += gP.getTamanioTile();
	        }
        
	    // ESPACIOS
	    int cursorX = casillaXInicio + (gP.getTamanioTile() * espacioCol);
	    int cursorY = casillaYInicio + (gP.getTamanioTile() * espacioRen);
	    int cursorAncho = gP.getTamanioTile();
	    int cursorAlto = gP.getTamanioTile();

	    g2.setColor(Color.white);
	    g2.setStroke(new BasicStroke(3));
	    g2.drawRoundRect(cursorX, cursorY, cursorAncho, cursorAlto, 10, 10);
	    
	    // DESCRIPCIÓN 

        //4) Obtener descripción del objeto bajo el cursor 
        Objeto seleccionado = null;
        int idx = espacioRen * MAX_COL + espacioCol;
        if (idx >= 0 && idx < lista.size()) {
            seleccionado = lista.get(idx);
        }

        // 5) Dibujar cuadro dinámico con esa descripción 
        if (seleccionado != null) {
            String textoDesc = seleccionado.getDescripcion();
            int cuadroX = x;
            int cuadroY = y + alto + 10;
            int cuadroAncho = ancho;
            dibujarCuadroConTexto(g2, textoDesc, cuadroX, cuadroY, cuadroAncho);
	    }
    }
	    
}
   
   // Dibuja cuadro dinámico según el contenido.

   private void dibujarCuadroConTexto(Graphics2D g2, String texto, int xVentana, int yVentana, int anchoVent) {
       if (texto == null || texto.isEmpty()) return;

       Font fuenteTexto  = Tipografia.cargaFuente(10f);
       FontMetrics fm    = g2.getFontMetrics(fuenteTexto);

       // 2) Márgenes internos (padding)
       int paddingX      = 20;
       int paddingY      = 12;
       int anchoMaxTexto = anchoVent - 2 * paddingX;

       // 3) Word-wrap: dividir en líneas que quepan en anchoMaxTexto
       List<String> lineas = new ArrayList<>();
       for (String parrafo : texto.split("\n")) {
           StringBuilder linea = new StringBuilder();
           for (String palabra : parrafo.split(" ")) {
               String prueba = linea.length() == 0 ? palabra : linea + " " + palabra;
               if (fm.stringWidth(prueba) > anchoMaxTexto) {
                   lineas.add(linea.toString());
                   linea = new StringBuilder(palabra);
               } else {
                   linea = new StringBuilder(prueba);
               }
           }
           lineas.add(linea.toString());
       }

       // 4) Calcular el alto del cuadro según número de líneas
       int altoLinea   = fm.getHeight();
       int altoVentana = paddingY * 2 + lineas.size() * altoLinea;

       // 5) Dibujar la ventana de fondo y su borde
       dibujarVentana(g2, xVentana, yVentana, anchoVent, altoVentana);

       // 6) Pintar cada línea de texto dentro del cuadro
       int xTexto = xVentana + paddingX;
       int yTexto = yVentana + paddingY + fm.getAscent();
       g2.setFont(fuenteTexto);
       g2.setColor(Color.WHITE);
       for (String ln : lineas) {
           g2.drawString(ln, xTexto, yTexto);
           yTexto += altoLinea;
       }
   }


   private void dibujarVentana(Graphics2D g2, int x, int y, int ancho, int alto) {
	    Color bg = new Color(0, 0, 0, 210);
	    g2.setColor(bg);
	    g2.fillRoundRect(x, y, ancho, alto, 35, 35);

	    g2.setStroke(new BasicStroke(5));
	    g2.setColor(Color.white);
	    g2.drawRoundRect(x + 5, y + 5, ancho - 10, alto - 10, 25, 25);
	}
   
   
   private double tiempoA = 0; 
   private boolean mostrarA = true;
   
   //El cuadro de consigue comida parpadea.
   public void dibujarAlarma() {
	   if(gP.getGameState() == gP.getPlayState() && gP.getJugador().getVida() <= 20) {
		   cuadroDeAdvertencia();
	   }
	}
   
   public void cuadroDeAdvertencia() {
	   tiempoA+=1.0/60.0; 
	   if(tiempoA >= 0.2) { //para que parpade 
		   mostrarA = !mostrarA;
		   tiempoA = 0;
	   }
	   
	   if(mostrarA) {
	   dibujarCuadroConTexto(g2, "CONSIGUE ALIMENTO!",20,50,120);
	   }
  }
   
   
   public void dibujarCuadroJ() {
	   if(gP.getGameState() == gP.getPlayState()) {
		   cuadroDeJefe();
	   }
   }
   
   public void cuadroDeJefe() {
	   if(gP.getAssS().getNotificacion()) {
		   int i = gP.getAssS().getidJFN();
		   dibujarCuadroConTexto(g2, "DIRIGETE A" + gP.getJF()[i].getLocation(),20,50,120);
		   
		   //DESAPARECER CUADRO EN X TIEMPO
		   double nt = gP.getAssS().getTiempoN() + (1.0/60.0);
		   gP.getAssS().setTiempoN(nt);
		   
		   if(gP.getAssS().getTiempoN() >= 2.0) {
			   gP.getAssS().setNotificacion(false);
			   gP.getAssS().setTiempoN(0);
		   }
		   
	   }
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
