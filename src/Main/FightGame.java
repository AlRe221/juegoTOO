package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entidad.JefePorNivel;
import entidad.Jugador;
import entidad.Proyectil;

public class FightGame {
	protected GamePanel gP; 
	protected Jugador jug; 
	protected JefePorNivel jN;
	protected BufferedImage image;
	
	
	private boolean peleaTerminada = false; 
	private boolean gano = false;
	
	public FightGame(GamePanel gP, Jugador jug, JefePorNivel jN) {
		this.gP = gP;
		this.jug = jug;
		this.jN = jN;
	} 
	
	
	public void iniciaCombate() {
		
	}
	
	public void update() {
	  jefeAparecer();
	  jug.updateCombate();
	  
	  for (int i = 0; i < gP.getListaProyectilJugador().size(); i++) {
		    Proyectil p = gP.getListaProyectilJugador().get(i);
		    if (p != null) {
		        if (p.getVivo()) {
		            p.update();
		        } else {
		            gP.getListaProyectilJugador().remove(i);
		            i--; // <-- para revisar correctamente el siguiente proyectil
		        }
		    }
		}

		
	}
	
	
	public void draw(Graphics2D g2) {
		mostrarFondoCombates(g2);
		if (jN != null) {
	        // Coordenadas y tamaño del jefe en pantalla
	        jN.drawEnCombate(g2, 1000, 400, 200, 200);
	    }
	    if (jug != null) {
	        jug.drawEnCombate(g2);
	    }
		gP.getUi().mostrarBarraVida(g2);
		gP.getUi().mostrarBarraVidaJ(g2, this.jN);
		pintarProyectil(g2);
		g2.setColor(Color.GRAY);
	    g2.fillRect(0, 590, gP.getWidth(), 150);
	}
	
	public void jefeAparecer() {
		jN.contadorSprites();
	}
	
	
	public void pintarProyectil(Graphics2D g2) {
		for(int i = 0; i < gP.getListaProyectilJugador().size(); i++) {
			  if(gP.getListaProyectilJugador().get(i) != null) {
				  gP.getListaProyectilJugador().get(i).dibujar(g2);
			  }
		  }
	}
	
	public void mostrarFondoCombates(Graphics2D g2) {
	    int nivelId = this.jN.getIdNivel();
	    String path = ""; // Variable para guardar la ruta de la imagen

	    // 1. Asignamos la ruta correcta según el ID del jefe
	    switch (nivelId) {
	        case 1: 
	            path = "/ImagenesPantallas/fondo_jefe_1.png"; // Jefe 1 -> Imagen 1
	            break;
	        case 2: 
	            path = "/ImagenesPantallas/fondo_jefe_2.png"; // Jefe 2 -> Imagen 2
	            break;
	        case 3: 
	            // Para el Jefe 3, podemos usar la imagen 4 que tienes, o la 1, ¡la que prefieras!
	            // Voy a usar la 4 como ejemplo. Si creas fondo_jefe_3.png, cambia el nombre aquí.
	            path = "/ImagenesPantallas/fondo_jefe_4.png"; // Jefe 3 -> Imagen 4
	            break;
	        default:
	            // Si por alguna razón el ID no es 1, 2, o 3, no hacemos nada.
	            break;
	    }

	    // 2. Intentamos cargar y dibujar la imagen desde la ruta que elegimos
	    if (!path.isEmpty()) {
	        try {
	            // Carga y dibuja la imagen de forma directa
	            image = ImageIO.read(getClass().getResourceAsStream(path));
	            g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	        } catch (Exception e) {
	            // El bloque catch está vacío como preferías.
	        }
	    }
	            
	   
	}
	
	
	public void terminaCombate(boolean win) {
		this.gano = win; 
		this.peleaTerminada = true;
	}
	

}
