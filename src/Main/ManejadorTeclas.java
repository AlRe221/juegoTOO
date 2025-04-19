package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejadorTeclas implements KeyListener
{
	private boolean teclaArriba, teclaAbajo, teclaIzquierda, teclaDerecha;
	 private boolean teclaInventario, teclaArribaInv, teclaAbajoInv, teclaEnter;

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
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
}
