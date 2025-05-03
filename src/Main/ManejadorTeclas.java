package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejadorTeclas implements KeyListener
{
	private GamePanel gP;
	private boolean teclaArriba, teclaAbajo, teclaIzquierda, teclaDerecha;
	 private boolean teclaInventario, teclaArribaInv, teclaAbajoInv, teclaEnter;
	 private boolean teclaCorrer;

	 public ManejadorTeclas(GamePanel gP) {
		 this.gP = gP;
	 }
	 
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		//pantalla inicio
				if(gP.getGameState() == gP.getPantallaInicio()) {
					switch(e.getKeyCode()) 
					{
					case KeyEvent.VK_W : {
						gP.getUi().setNUmCom(gP.getUi().getNumCom() -1);
						if(gP.getUi().getNumCom() < 0) {
							gP.getUi().setNUmCom(3);
						}
						break;
					}
					case KeyEvent.VK_S :{
						gP.getUi().setNUmCom(gP.getUi().getNumCom() +1);
						if(gP.getUi().getNumCom() > 3) {
							gP.getUi().setNUmCom(0);
						}
						break;
					}
					 case KeyEvent.VK_ENTER:{
						 if(gP.getUi().getNumCom() == 0) {
							 gP.stopMusic();
							 gP.setGameState(gP.getPlayState());
							 gP.playMusic(4); //agregar de nuevo el archivo de musica ambientación, estaba muy pesado.
							 
						 }
						 
						 if(gP.getUi().getNumCom() == 1) {
							//settings, pantalla de controles
						 }
						 
						 if(gP.getUi().getNumCom() == 2) {
							 //info de los creadores del juego, otra pantalla
						 }
						 if(gP.getUi().getNumCom() == 3) {
							 System.exit(0);
							 
						 }
						 break;
					 }
					}	
				}
		
				//pnatalla pausa
				if(gP.getGameState() == gP.getPauseState()) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_W :{
						gP.getUi().setNUmCom(gP.getUi().getNumCom() -1);
						if(gP.getUi().getNumCom() < 0) {
							gP.getUi().setNUmCom(1);
						}
						break;
					}
					case KeyEvent.VK_S : {
						gP.getUi().setNUmCom(gP.getUi().getNumCom() +1);
						if(gP.getUi().getNumCom() > 3) {
							gP.getUi().setNUmCom(0);
						}
						break;
					}
					case KeyEvent.VK_ENTER:{
						if(gP.getUi().getNumCom() == 0) {
							 gP.stopMusic();
							 gP.setGameState(gP.getPantallaInicio());
							 gP.playMusic(4); 
						 }
						if(gP.getUi().getNumCom() == 1) {
							 gP.setGameState(gP.getPlayState());		 
						 }
					}
					}
				}
		
		//pantalla juego		
		switch(e.getKeyCode()) 
		{
		case KeyEvent.VK_W : teclaArriba = true;
		break;
		case KeyEvent.VK_S : teclaAbajo = true;
		break;
		case KeyEvent.VK_A : teclaIzquierda = true;
		break;
		case KeyEvent.VK_D : teclaDerecha = true;
		break;
		case KeyEvent.VK_I: teclaInventario = true; // I para inventario
        break;
		case KeyEvent.VK_UP:   teclaArribaInv = true; // Teclas dentro del inventario
		break;
        case KeyEvent.VK_DOWN: teclaAbajoInv  = true; 
        break;
        case KeyEvent.VK_ENTER: teclaEnter    = true; 
        break;
        case KeyEvent.VK_ESCAPE :{
        	if(gP.getGameState() == gP.getPlayState()) {
        		gP.setGameState(gP.getPauseState());
        	}else if(gP.getGameState() == gP.getPauseState()) {
        		gP.setGameState(gP.getPlayState());
        	}
        	break;
        	}
        case KeyEvent.VK_Q: teclaCorrer =true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		
		switch(e.getKeyCode()) 
		{
		case KeyEvent.VK_W : teclaArriba = false;
		break;
		case KeyEvent.VK_S : teclaAbajo = false;
		break;
		case KeyEvent.VK_A : teclaIzquierda = false;
		break;
		case KeyEvent.VK_D : teclaDerecha = false;
		break;
		case KeyEvent.VK_I : teclaInventario = false;
        break;
        case KeyEvent.VK_UP:    teclaArribaInv = false; 
        break;
        case KeyEvent.VK_DOWN:  teclaAbajoInv  = false; 
        break;
        case KeyEvent.VK_ENTER: teclaEnter     = false;
        break;
        case KeyEvent.VK_Q : teclaCorrer = false;
        break;
		}
		
	
	}
	public boolean getTeclaArriba()
	{
		return this.teclaArriba;
	}
	public boolean getTeclaAbajo()
	{
		return this.teclaAbajo;
	}
	public boolean getTeclaIzquierda()
	{
		return this.teclaIzquierda;
	}
	public boolean getTeclaDerecha()
	{
		return this.teclaDerecha;
	}
	public boolean getTeclaInventario() {
	    return teclaInventario;
	}
    public boolean getTeclaArribaInv(){ 
    	return teclaArribaInv; 
    }
    public boolean getTeclaAbajoInv(){
    	return teclaAbajoInv; 
	}
    public boolean getTeclaEnter(){ 
    	return teclaEnter; 
	}
	public void setTeclaInventario(boolean b) {
	    this.teclaInventario = b;		
	}
    public void setTeclaArribaInv(boolean b){ 
    	this.teclaArribaInv = b; 
	}
    public void setTeclaAbajoInv(boolean b){ 
    	this.teclaAbajoInv  = b; 
	}
    public void setTeclaEnter(boolean b){
    	this.teclaEnter = b; 
	}

	public boolean isTeclaCorrer() {
		return teclaCorrer;
	}

	public void setTeclaCorrer(boolean teclaCorrer) {
		this.teclaCorrer = teclaCorrer;
	}
    
}
