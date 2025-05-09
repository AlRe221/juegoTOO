package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import Inventario.Inventario;
import Inventario.Objeto;

public class ManejadorTeclas extends KeyAdapter {

    private final GamePanel gP;


    // MOVIMIENTO Y ACCIONES
    private boolean teclaArriba, teclaAbajo, teclaIzquierda, teclaDerecha;
    private boolean teclaInventario, teclaArribaInv, teclaAbajoInv, teclaEnter, teclaIzqInvCol, teclaDerInvCol;
    private boolean teclaCorrer;
 
	
    private static final int OPCIONES_MENU_INICIO = 4;
    private static final int OPCIONES_MENU_PAUSA  = 2;

    
   
    public ManejadorTeclas(GamePanel gP) {
        this.gP = gP;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	UI ui = gP.getUi();
        int estado = gP.getGameState();

        if (estado == gP.getPantallaInicio()) {
            manejarMenuInicio(e);
        } 
        else if (estado == gP.getPauseState()) {
            manejarMenuPausa(e);
        } 
        else if (estado == gP.getPlayState()) {
            manejarJuego(e);
            
            if (ui.getInventorOpen()) {
                manejarInventario(e);
            }            
            else {
                manejarJuego(e);
            }
        }
    }
    
 // Alterna entre estado de juego y pausa 
    private void alternarPausa() {
        if (gP.getGameState() == gP.getPlayState()) {
            gP.setGameState(gP.getPauseState());
        } else if (gP.getGameState() == gP.getPauseState()) {
            gP.setGameState(gP.getPlayState());
        }
    }
    
    private void seleccionarObjeto() {
        UI ui   = gP.getUi();
        Inventario inv = gP.getJugador().getInventario();

        inv.removeObjetoEn(ui.espacioRen, ui.espacioCol);
           
       // gP.getJugador().usarObjeto(obj);
    }

    
    //se agrego aca lo de alternar pausa, ya funciona
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W     -> teclaArriba     = false;
            case KeyEvent.VK_S     -> teclaAbajo      = false;
            case KeyEvent.VK_A     -> teclaIzquierda  = false;
            case KeyEvent.VK_D     -> teclaDerecha    = false;
            case KeyEvent.VK_I     -> teclaInventario = false;
            case KeyEvent.VK_UP    -> teclaArribaInv  = false;
            case KeyEvent.VK_DOWN  -> teclaAbajoInv   = false;
            case KeyEvent.VK_LEFT  -> teclaIzqInvCol  = false;          
            case KeyEvent.VK_RIGHT -> teclaDerInvCol  = false;
            case KeyEvent.VK_ENTER -> teclaEnter      = false;
            case KeyEvent.VK_Q     -> teclaCorrer     = false;
            case KeyEvent.VK_ESCAPE -> alternarPausa();  
        }
    }

    // NAVEGACIÓN MENÚ DE INICIO
    private void manejarMenuInicio(KeyEvent e) {
        UI ui = gP.getUi();
        int sel = ui.getNumCom();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                sel = (sel - 1 + OPCIONES_MENU_INICIO) % OPCIONES_MENU_INICIO;
                ui.setNUmCom(sel);
            }
            case KeyEvent.VK_S -> {
                sel = (sel + 1) % OPCIONES_MENU_INICIO;
                ui.setNUmCom(sel);
            }
            case KeyEvent.VK_ENTER -> {
                switch (sel) {
                    case 0 -> { // INICIAR
                        gP.stopMusic();
                        gP.setGameState(gP.getPlayState());
                        gP.playMusic(2);
                    }
                    case 1 -> { 
                        // TODO: Implementar pantalla de SETTINGS
                    }
                    case 2 -> { 
                        // TODO: Implementar pantalla de INFO
                    }
                    case 3 -> { // EXIT
                        System.exit(0);
                    }
                }
            }
        }
    }

    // NAVEGACIÓN MENÚ DE PAUSE 
    private void manejarMenuPausa(KeyEvent e) {
        UI ui = gP.getUi();
        int sel = ui.getNumCom();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                sel = (sel - 1 + OPCIONES_MENU_PAUSA) % OPCIONES_MENU_PAUSA;
                ui.setNUmCom(sel);
            }
            case KeyEvent.VK_S -> {
                sel = (sel + 1) % OPCIONES_MENU_PAUSA;
                ui.setNUmCom(sel);
            }
            case KeyEvent.VK_ENTER -> {
                switch (sel) {
                    case 0 -> { // EXIT
                        gP.stopMusic();
                        gP.setGameState(gP.getPantallaInicio());
                        gP.playMusic(4);
                    }
                    case 1 -> { // CONTINUE
                        gP.setGameState(gP.getPlayState());
                    }
                }
            }
        }
    }
    
    private void manejarInventario(KeyEvent e) {
    	
    	UI ui = gP.getUi();
    	switch (e.getKeyCode()) {
        case KeyEvent.VK_UP    -> ui.espacioRen--;
        case KeyEvent.VK_DOWN  -> ui.espacioRen++;
        case KeyEvent.VK_LEFT  -> ui.espacioCol--;
        case KeyEvent.VK_RIGHT -> ui.espacioCol++;
        case KeyEvent.VK_ENTER -> seleccionarObjeto();
    }
    // Asegurar que los índices están dentro de los límites
    ui.espacioRen = Math.max(0, Math.min(ui.espacioRen, UI.MAX_REN - 1));
    ui.espacioCol = Math.max(0, Math.min(ui.espacioCol, UI.MAX_COL - 1));
}


    // CONTROLES JUEGO
    //se movio el alternar pausa a la principal de teclas, para que funcionara
    private void manejarJuego(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W      -> teclaArriba     = true;
            case KeyEvent.VK_S      -> teclaAbajo      = true;
            case KeyEvent.VK_A      -> teclaIzquierda  = true;
            case KeyEvent.VK_D      -> teclaDerecha    = true;
            case KeyEvent.VK_I      -> teclaInventario = true;
            case KeyEvent.VK_UP     -> teclaArribaInv  = true;
            case KeyEvent.VK_DOWN   -> teclaAbajoInv   = true;
            case KeyEvent.VK_LEFT   -> teclaIzqInvCol  = true;          
            case KeyEvent.VK_RIGHT  -> teclaDerInvCol  = true;
            case KeyEvent.VK_ENTER  -> teclaEnter      = true;
            case KeyEvent.VK_Q      -> teclaCorrer     = true;
        }
    }

    // Alterna entre estado de juego y pausa 
    /*private void alternarPausa() {
        if (gP.getGameState() == gP.getPlayState()) {
            gP.setGameState(gP.getPauseState());
        } else if (gP.getGameState() == gP.getPauseState()) {
            gP.setGameState(gP.getPlayState());
        }
    }*/

    public boolean getTeclaArriba()    { return teclaArriba; }
    public boolean getTeclaAbajo()     { return teclaAbajo; }
    public boolean getTeclaIzquierda() { return teclaIzquierda; }
    public boolean getTeclaDerecha()   { return teclaDerecha; }
    public boolean getTeclaInventario(){ return teclaInventario; }
    public boolean isTeclaCorrer()     { return teclaCorrer; }

    public void setTeclaInventario(boolean b) { this.teclaInventario = b; }
    public void setTeclaCorrer(boolean b)     { this.teclaCorrer     = b; }
}
