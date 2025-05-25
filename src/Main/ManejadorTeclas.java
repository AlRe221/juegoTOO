package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import Inventario.Alimento;
import Inventario.Bebida;
import Inventario.Equipable;
import Inventario.Inventario;
import Inventario.Objeto;

public class ManejadorTeclas extends KeyAdapter {

    private final GamePanel gP;


    // MOVIMIENTO Y ACCIONES
    private boolean teclaArriba, teclaAbajo, teclaIzquierda, teclaDerecha;
    private boolean teclaInventario, teclaArribaInv, teclaAbajoInv, teclaEnter, teclaIzqInvCol, teclaDerInvCol;
    private boolean teclaCorrer,teclaSaltar,tecladisparar;
 
	
    private static final int OPCIONES_MENU_INICIO = 4;
    private static final int OPCIONES_MENU_PAUSA  = 2;
    private static final int OPCIONES_PANTALLA_DECISION  = 3;

    
   
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
        }else if(estado == gP.getPantalaSetting()) {
        	manejarMenuSettings(e);
        }else if(estado == gP.getPantallaInfo()) {
        	manejarMenuInfo(e);
        }else if(estado == gP.getPantallaDecision()) {
        	manejarPantallaDecision(e);
        }else if(estado == gP.getWin() || estado == gP.getgameOver1() || estado == gP.getgameOver2()) {
        	manejarWin_GameOver(e);
        }
        else if (estado == gP.getPlayState()) {
            manejarJuego(e);
            
            if (ui.getInventorOpen()) {
                manejarInventario(e);
            }            
            else {
                manejarJuego(e);
            }
        }else if(estado == gP.getFightState()) {
        	manejarCombate(e);
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
        int ren = ui.espacioRen, col = ui.espacioCol;

        Objeto obj = inv.getObjetoEn(ren, col);
        if (obj != null) {
        	if (obj instanceof Equipable equipable) {
        	    gP.getJugador().equipar(equipable.getSpriteKey());
                inv.removeObjetoEn(ren, col);         // quitarlo (opcional)
                gP.playSE(14);                        // sonido “equipar”
                ui.setInventorOpen(false);
                return;
            }

            // Si es alimento, curamos un 10% y hacemos desaparecer el alimento
            if (obj instanceof Alimento) {
            	aumentaVida();
                gP.playSE(9);
                inv.removeObjetoEn(ren, col);
            }else if( obj instanceof Bebida) {
            	aumentaVida();
                gP.playSE(12);
                
                inv.removeObjetoEn(ren, col);
            }
            
        }
        
    }
    
    public void aumentaVida() {
    	double vidaActual = gP.getJugador().getVida();
        double vidaMax    = gP.getJugador().getVidaMax();
        double recupera   = vidaMax * 0.10;   // 10%
        double nuevaVida  = Math.min(vidaActual + recupera, vidaMax);
        gP.getJugador().setVida(nuevaVida);
       
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
            case KeyEvent.VK_ESCAPE-> alternarPausa();
            case KeyEvent.VK_SPACE -> teclaSaltar     = false;
            case KeyEvent.VK_B     -> tecladisparar   = false;
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
                    	 gP.setGameState(gP.getPantalaSetting());
                    }
                    case 2 -> { 
                        // TODO: Implementar pantalla de INFO
                    	gP.setGameState(gP.getPantallaInfo());
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
    
    //MANEJAR MENU DE SETTINGS
    private void manejarMenuSettings(KeyEvent e) {
    	switch(e.getKeyCode()) {
    	case KeyEvent.VK_ENTER -> {
    		gP.playSE(13);
        	if(gP.getUi().getNumCom() == 0) {
            gP.setGameState(gP.getPantallaInicio());  	
    	   }
         break;
        }
      }
    }
    
    //MANEJAR MENU DE INFO
    private void manejarMenuInfo(KeyEvent e) {
    	switch(e.getKeyCode()) {
        case KeyEvent.VK_ENTER -> {
        	gP.playSE(13);
        	if(gP.getUi().getNumCom() == 0) {
        		gP.setGameState(gP.getPantallaInicio());
           }
        	break;
        }
	  }
    }
    
    
    //MANEJAR PANTALLA DECISION 
    private void manejarPantallaDecision(KeyEvent e) {
        UI ui = gP.getUi();
        int sel = ui.getNumCom();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                sel = (sel - 1 + OPCIONES_PANTALLA_DECISION) % OPCIONES_PANTALLA_DECISION;
                ui.setNUmCom(sel);
            }
            case KeyEvent.VK_S -> {
                sel = (sel + 1) % OPCIONES_PANTALLA_DECISION;
                ui.setNUmCom(sel);
            }
            case KeyEvent.VK_ENTER -> {
                switch (sel) {
                    case 0 -> { // WIN
                        gP.stopMusic();
                        gP.setGameState(gP.getWin());
                        gP.playSE(17);
                    }
                    case 1 -> { //GAMEOVER
                    	 gP.stopMusic();
                    	 gP.setGameState(gP.getgameOver1());
                    	 ui.setNUmCom(0);
                    	 gP.playSE(18);
                    }
                    case 2 -> { 
                        // GAMEOVER
                    	gP.stopMusic();
                    	gP.setGameState(gP.getgameOver2());
                    	ui.setNUmCom(0);
                    	gP.playSE(18);
                    }
                    
                }
            }
        }
    }
    
    //PANTALLA WIN o GAME OVER
    private void manejarWin_GameOver(KeyEvent e) {
    	switch(e.getKeyCode()) {
    	case KeyEvent.VK_ENTER -> {
    		gP.playSE(13);
        	if(gP.getUi().getNumCom() == 0) {
            gP.setupGame();                          // reposiciona jugador, zombies, objetos, inventario…            gP.setGameState(gP.getPantallaInicio());
            gP.getUi().setNUmCom(0); // RESETEA LA SELECCIÓN DEL MENÚ
            gP.playMusic(4);
    	   }
         break;
        }
      }
    }
    

    
    
    //MANEJAR INVENTARIO
    private void manejarInventario(KeyEvent e) {
    	
    	UI ui = gP.getUi();
    	switch (e.getKeyCode()) {
        case KeyEvent.VK_UP    -> ui.espacioRen--;
        case KeyEvent.VK_DOWN  -> ui.espacioRen++;
        case KeyEvent.VK_LEFT  -> ui.espacioCol--;
        case KeyEvent.VK_RIGHT -> ui.espacioCol++;
        case KeyEvent.VK_ENTER -> seleccionarObjeto();
        case KeyEvent.VK_R -> gP.getJugador().equipar(null);   // manos vacías
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
    
    private void manejarCombate(KeyEvent e) {
    	 switch (e.getKeyCode()) {
         case KeyEvent.VK_A      -> teclaIzquierda  = true;
         case KeyEvent.VK_D      -> teclaDerecha    = true;
         case KeyEvent.VK_Q      -> teclaCorrer     = true;
         case KeyEvent.VK_SPACE   -> teclaSaltar    = true;
         case KeyEvent.VK_B -> tecladisparar       = true;
     }
    }
    


    public boolean getTeclaArriba()    { return teclaArriba; }
    public boolean getTeclaAbajo()     { return teclaAbajo; }
    public boolean getTeclaIzquierda() { return teclaIzquierda; }
    public boolean getTeclaDerecha()   { return teclaDerecha; }
    public boolean getTeclaInventario(){ return teclaInventario; }
    public boolean isTeclaCorrer()     { return teclaCorrer; }
    public boolean getTeclaSaltar()     { return teclaSaltar; }
    public boolean getTeclaDisparar()   { return tecladisparar; }

    public void setTeclaInventario(boolean b) { this.teclaInventario = b; }
    public void setTeclaCorrer(boolean b)     { this.teclaCorrer     = b; }
}
