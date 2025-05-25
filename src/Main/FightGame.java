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
		if (peleaTerminada) {
			gP.stopMusic();
		    gP.getJugador().setMundoX(gP.getJugador().getMundoX_previo());
		    gP.getJugador().setMundoY(gP.getJugador().getMundoY_previo());
		    gP.getJugador().setPantallaX(gP.getJugador().getPantallaX_previa());
		    gP.getJugador().setPantallaY(gP.getJugador().getPantallaY_previa());
		    gP.getJugador().setDireccion("estatico");        
	        
	        // Limpiamos la lista de proyectiles
	        gP.getListaProyectilJugador().clear();

	        // Jugador ganó 
	        if (gano) {
	            
	            int indiceJefeDerrotado = gP.getAssS().getidJFN(); // Obtenemos el índice del jefe que estaba en combate	            
	            if (indiceJefeDerrotado >= 0 && indiceJefeDerrotado < gP.getJF().length) {	                
	                gP.getJF()[indiceJefeDerrotado] = null; 
	            }
	            // Opcional: podrías querer resetear idJFN en AssetSetter
	            // gP.getAssS().setidJFN(-1); // Para indicar que no hay jefe activo para notificación	            
	        }	        
	        gP.setGameState(gP.getPlayState());
	        return; 
	    }
	
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
  	
    if (jug.getVida() <= 0) {
        terminaCombate(false); // El jugador pierde
    } else if (jN.getVida() <= 0) { 
        terminaCombate(true); // El jugador gana (jefe derrotado)
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
	            path = "/ImagenesPantallas/fondo_jefe_4.png"; // Jefe 1 -> Imagen 1
	            break;
	        case 2: 
	            path = "/ImagenesPantallas/fondo_jefe_2.png"; // Jefe 2 -> Imagen 2
	            break;
	        case 3: 
	            path = "/ImagenesPantallas/fondo_jefe_1.png"; // Jefe 3 -> Imagen 4
	            break;
	        default:	           
	            break;
	    }

	    
	    if (!path.isEmpty()) {
	        try {
	            
	            image = ImageIO.read(getClass().getResourceAsStream(path));
	            g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
	        } catch (Exception e) {
	        		           
	        }
	    }
	            
	   
	}
	
	
	public void terminaCombate(boolean win) {
		this.gano = win; 
		this.peleaTerminada = true;
	}
	// Devuelve la instancia del jefe actual en combate.
	public JefePorNivel getJefe() {
        return this.jN;
    }
	

}
