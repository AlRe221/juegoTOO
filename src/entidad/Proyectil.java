package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Main.GamePanel;

public class Proyectil extends Entidad {

    Entidad usuario;
    BufferedImage proyectilSprite;

    /**
     * Constructor simple. Crea un proyectil "inactivo".
     */
    public Proyectil(GamePanel gp) {
        super(gp);
        this.vivo = false; // El proyectil nace "muerto" y se activa con set()
    }

    /**
     * Configura y activa el proyectil en el momento del disparo.
     * Esta es la lógica clave que recuperamos del sistema original.
     * @param usuario La entidad que dispara (Jugador o Jefe).
     * @param spritePath La ruta a la imagen.
     * @param ataque El daño del proyectil.
     * @param velocidad La velocidad del proyectil.
     * @param direccion La dirección en que viaja.
     */
    public void set(Entidad usuario, String spritePath, double ataque, int velocidad, String direccion) {
        this.usuario = usuario;
        this.velocidad = velocidad;
        this.ataque = ataque;
        this.direccion = direccion;
        
        // Toma las coordenadas dinámicas del usuario en el momento del disparo.
        this.mundoX = usuario.getMundoX();
        this.mundoY = usuario.getMundoY();

        this.solidArea = new Rectangle(0, 0, 48, 48); // Hitbox por defecto.
        getImage(spritePath); // Carga la imagen correcta.
        
        this.vivo = true; // ¡Activamos el proyectil!
    }
    
    private void getImage(String path) {
        this.proyectilSprite = setup1(path);
    }
    
    public void update() {
        if (!this.vivo) return;

        // 1. Mover
        switch (direccion) {
            case "derecha": mundoX += velocidad; break;
            case "izquierda": mundoX -= velocidad; break;
        }

        // 2. Actualizar hitbox
        this.solidArea.x = this.mundoX;
        this.solidArea.y = this.mundoY;

        // 3. Comprobar colisión
        if (usuario.tipoE == 0) { // Disparo del Jugador
            JefePorNivel jefe = gP.getJefeActualEnCombate();
            if (jefe != null && jefe.getVivo()) {
                Rectangle hitboxJefe = new Rectangle(1000, 400, jefe.getSolidArea().width, jefe.getSolidArea().height);
                if (this.solidArea.intersects(hitboxJefe)) {
                    jefe.recibirDaño(this.ataque);
                    this.vivo = false;
                }
            }
        } else { // Disparo del Jefe
            Rectangle hitboxJugador = new Rectangle(gP.getJugador().getMundoX(), gP.getJugador().getMundoY(), gP.getJugador().getSolidArea().width, gP.getJugador().getSolidArea().height);
            if (this.solidArea.intersects(hitboxJugador)) {
                gP.getJugador().recibirDaño(this.ataque);
                this.vivo = false;
            }
        }

        // 4. Comprobar límites de pantalla
        if (mundoX < 0 || mundoX > gP.getAnchoPantalla()) {
            this.vivo = false;
        }
    }

    public void dibujar(Graphics2D g2) {
        if (vivo && proyectilSprite != null) {
            g2.drawImage(proyectilSprite, mundoX, mundoY, 120, 120, null);
        }
    }

    @Override
    public void setColisionOn(boolean colisionOn) {
        // No es necesario para este proyectil
    }
}