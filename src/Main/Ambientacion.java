package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Ambientacion {
	protected GamePanel gP;
	Clip clip;
	URL urlSonido [] = new URL[30];
	protected boolean activa; 
	protected int limiteVida; 
	private int contPasos = 0; 
	private final int intervaloP = 15;
	
	public Ambientacion(GamePanel gP) {
		
		urlSonido[0] = getClass().getResource("/Ambientacion/caminata.wav");
		urlSonido[1] = getClass().getResource("/Ambientacion/correr.wav");
		urlSonido[2] = getClass().getResource("/Ambientacion/musicadeexploracion.wav");
		urlSonido[3] = getClass().getResource("/Ambientacion/musicacombate.wav");
		urlSonido[4] = getClass().getResource("/Ambientacion/musicapantallainicio.wav");
		urlSonido[5] = getClass().getResource("/Ambientacion/recievedamage.wav");
		urlSonido[6] = getClass().getResource("/Ambientacion/recogerobjeto.wav");
		urlSonido[7] = getClass().getResource("/Ambientacion/sonidoabrirpuerta.wav");
		urlSonido[8] = getClass().getResource("/Ambientacion/sonidoMoneda.wav");
		urlSonido[9] = getClass().getResource("/Ambientacion/sonidodecomer.wav");
		urlSonido[10] = getClass().getResource("/Ambientacion/sonidodehablar.wav");
		urlSonido[11] = getClass().getResource("/Ambientacion/sonidoVelocidad.wav");
		urlSonido[12] = getClass().getResource("/Ambientacion/tomaragua.wav");
		urlSonido[13] = getClass().getResource("/Ambientacion/clickbotonesjuego.wav");
		urlSonido[14] = getClass().getResource("/Ambientacion/equipsound.wav");
		urlSonido[15] = getClass().getResource("/Ambientacion/alarma2.wav");
		
		this.gP =gP;
		this.activa = false; 
		this.limiteVida = 5;
	}
	
	public void setFile(int i) {
		
		try {
			AudioInputStream sis = AudioSystem.getAudioInputStream(urlSonido[i]);
			clip = AudioSystem.getClip(); 
			clip.open(sis);
			
		}catch(Exception e) {
			
		}
		
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
	   public void sonidoCamina(boolean moviendo, boolean colisionOn, int sonidoID) {
	        if (moviendo && !colisionOn) {
	            contPasos++;
	            if (contPasos >= intervaloP) {
	                gP.playSE(sonidoID); // o usa una referencia a GamePanel si es no estático
	                contPasos = 0;
	            }
	        } else {
	            contPasos = intervaloP;
	        }
	    }

	   public void activarAlerta() {	
			if(gP.getJugador().getVida() == 20) {
				gP.playSE(15);
			}
	   }
}
