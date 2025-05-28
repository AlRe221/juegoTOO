package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entidad.JefePorNivel;
import entidad.Jugador;
import entidad.Proyectil;

public class FightGame {
	protected GamePanel gP; 
	protected Jugador jug; 
	protected JefePorNivel jN;
	
	private long tiempoUltimoAtaqueJefe = 0;
	private final long COOLDOWN_ATAQUE_JEFE = 2000;
	private boolean peleaTerminada = false; 
	private boolean gano = false;
	
	private long tiempoTotalUpdateNS = 0;
	private long tiempoTotalDrawNS = 0;
	private int contadorUpdates = 0;
	private int contadorDraws = 0;
	private final int INTERVALO_REPORTE_PERFILADO = 60; // Reportar cada 60 frames (aprox. 1 segundo)
	
	public FightGame(GamePanel gP, Jugador jug, JefePorNivel jN) {
		this.gP = gP;
		this.jug = jug;
		this.jN = jN;
	} 
	
	
	public void iniciaCombate() {
	}
	
	public void update() {
		long inicioUpdate = System.nanoTime();

		if (peleaTerminada) {
			gP.stopMusic();
		    gP.getJugador().setMundoX(gP.getJugador().getMundoX_previo());
		    gP.getJugador().setMundoY(gP.getJugador().getMundoY_previo());
		    gP.getJugador().setPantallaX(gP.getJugador().getPantallaX_previa());
		    gP.getJugador().setPantallaY(gP.getJugador().getPantallaY_previa());
		    // System.out.println("[FightGame] DESPUÉS DEL COMBATE (tras restaurar): mundoX=" + gP.getJugador().getMundoX() + ", mundoY=" + gP.getJugador().getMundoY() + ", pantallaX=" + gP.getJugador().getPantallaX() + ", pantallaY=" + gP.getJugador().getPantallaY());
		    gP.getJugador().setDireccion("estatico");        
	        
	        // Limpiamos la lista de proyectiles del jugador
	        gP.getListaProyectilJugador().clear();
	        // Reseteamos el pool de proyectiles del jefe
	        gP.resetearPoolProyectilesJefe();
	        
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
	  
	// En FightGame.java
		  if (System.currentTimeMillis() - tiempoUltimoAtaqueJefe > COOLDOWN_ATAQUE_JEFE) {
		      tiempoUltimoAtaqueJefe = System.currentTimeMillis();

		      Proyectil proyectilJefe = gP.getProyectilJefeDelPool(); 

		      if (proyectilJefe != null) { // Solo proceder si obtuvimos un proyectil del pool
		          String spritePathJefe = "";
		          double ataqueJefe = 0;
		          int velocidadJefe = 0;
		          String direccionJefe = "izquierda";

		          // --- NUEVO: Coordenadas de origen para el proyectil del jefe en la pantalla de combate ---
		          int origenXJefe = 1000; // La X donde se dibuja el jefe en combate
		          int origenYJefe = 400 + (200 / 2) - (48 / 2); // La Y donde se dibuja el jefe + la mitad de su alto - la mitad del alto del proyectil (para centrarlo un poco)

		          switch (jN.getIdNivel()) {
		              case 1: // Miguelito
		                  spritePathJefe = "/ProyectilesCombate/poderMiguelito";
		                  ataqueJefe = 5;
		                  velocidadJefe = 4;
		                  // NO usamos jN directamente para set, sino las coordenadas de origen
		                  // y creamos una Entidad "dummy" o pasamos null si Proyectil.set lo maneja.
		                  // Por ahora, vamos a modificar Proyectil.set para que acepte coordenadas directamente.
		                  break;
		              case 2: // Eloy
		                  spritePathJefe = "/ProyectilesCombate/poderEloy";
		                  ataqueJefe = 7;
		                  velocidadJefe = 5;
		                  break;
		              case 3: // Nacho
		                  spritePathJefe = "/ProyectilesCombate/poderNacho";
		                  ataqueJefe = 6;
		                  velocidadJefe = 6;
		                  break;
		          }
		          
		          // ANTES: proyectilJefe.set(jN, spritePathJefe, ataqueJefe, velocidadJefe, direccionJefe);
		          // NECESITAREMOS MODIFICAR Proyectil.set o crear uno nuevo para pasar coordenadas directamente
		          // O, pasar el Jefe (jN) pero luego SOBREESCRIBIR mundoX y mundoY del proyectil.
		          proyectilJefe.set(jN, spritePathJefe, ataqueJefe, velocidadJefe, direccionJefe); // Mantenemos esto por ahora por el 'usuario'
		          proyectilJefe.setMundoX(origenXJefe); 
		          proyectilJefe.setMundoY(origenYJefe); 

		          // gP.getListaProyectilJefe().add(proyectilJefe); // ESTA LÍNEA YA NO ES NECESARIA, el proyectil ya está en el pool, solo se activa.
		      } // Fin de if (proyectilJefe != null)
		  }
		  
		  // Actualizar proyectiles del Jugador
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
	  
	  // Actualizar proyectiles del Jefe (iterar sobre el pool y solo actualizar los activos)
	  for (Proyectil p : gP.getPoolProyectilesJefe()) {
		    if (p != null && p.getVivo()) {
		        p.update();
		        // La lógica de "p.getVivo() == false" dentro de p.update() ya lo marcará como no vivo.
		        // No necesitamos removerlo del pool aquí.
		    }
		}
  	
    if (jug.getVida() <= 0) {
        terminaCombate(false); // El jugador pierde
    } else if (jN.getVida() <= 0) { 
        terminaCombate(true); // El jugador gana (jefe derrotado)
    }

    long finUpdate = System.nanoTime();
    tiempoTotalUpdateNS += (finUpdate - inicioUpdate);
    contadorUpdates++;

    if (contadorUpdates >= INTERVALO_REPORTE_PERFILADO) {
        tiempoTotalUpdateNS = 0;
        contadorUpdates = 0;
    }		
	}
	
	
	public void draw(Graphics2D g2) {
		long inicioDraw = System.nanoTime();

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

	    long finDraw = System.nanoTime();
	    tiempoTotalDrawNS += (finDraw - inicioDraw);
	    contadorDraws++;

	    if (contadorDraws >= INTERVALO_REPORTE_PERFILADO) {
	        tiempoTotalDrawNS = 0;
	        contadorDraws = 0;
	    }
	}
	
	public void jefeAparecer() {
		jN.contadorSprites();
	}
	
	
	public void pintarProyectil(Graphics2D g2) {
		// Dibujar proyectiles del Jugador
		for(int i = 0; i < gP.getListaProyectilJugador().size(); i++) {
			  if(gP.getListaProyectilJugador().get(i) != null) {
				  gP.getListaProyectilJugador().get(i).dibujar(g2);
			  }
		  }
		// Dibujar proyectiles del Jefe (iterar sobre el pool y solo dibujar los activos)
		for(Proyectil p : gP.getPoolProyectilesJefe()) {
			  if(p != null && p.getVivo()) {
				  p.dibujar(g2);
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
	            
	            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
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

