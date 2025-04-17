package Main;

import entidad.Entidad;
import entidad.Jugador;

public class ChecadorColision {
	
	private GamePanel gP;
	public ChecadorColision(GamePanel gP) {
		this.gP = gP;
	}
	
	public void checkTile(Entidad entidad) {
		
		if(entidad instanceof Jugador) {
			
		int izqMundoXentidad = ((Jugador)entidad).getX() + ((Jugador)entidad).getAreaSolidaX();
		int derMundoXentidad = ((Jugador)entidad).getX() + ((Jugador)entidad).getAreaSolidaX() + ((Jugador)entidad).getAreaSolidaWidth();
		int topMundoYentidad = ((Jugador)entidad).getY() + ((Jugador)entidad).getAreaSolidaY();
		int bottomMundoYentidad = ((Jugador)entidad).getY() + ((Jugador)entidad).getAreaSolidaY() + ((Jugador)entidad).getAreaSolidaHeigth();
		
		int entidadIzqCol = izqMundoXentidad / gP.getTamanioTile();
		int entidadDerCol = derMundoXentidad / gP.getTamanioTile(); 
		int entidadTopRen = topMundoYentidad / gP.getTamanioTile();
		int entidadBotRen = bottomMundoYentidad / gP.getTamanioTile();
		
		int tileN1,tileN2;
		
		
		switch(((Jugador)entidad).getDireccion()) {
		case "arriba" :{
			entidadTopRen = (topMundoYentidad - ((Jugador)entidad).getVelocidad()) /this.gP.getTamanioTile();
		
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadIzqCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadDerCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				((Jugador)entidad).setColisionOn(true);
			}
		break;
		}
		case "abajo" :{
			entidadBotRen = (bottomMundoYentidad + ((Jugador)entidad).getVelocidad()) / this.gP.getTamanioTile();
			
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadIzqCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadDerCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				((Jugador)entidad).setColisionOn(true);
			}
		break;
		}
		case "izquierda" :{
			entidadIzqCol = (izqMundoXentidad - ((Jugador)entidad).getVelocidad()) / this.gP.getTamanioTile();
			
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadIzqCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadIzqCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				((Jugador)entidad).setColisionOn(true);
			}
		break;
		}
		case "derecha" :
		{
			entidadDerCol = (derMundoXentidad + ((Jugador)entidad).getVelocidad()) / this.gP.getTamanioTile();
			
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadDerCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadDerCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				((Jugador)entidad).setColisionOn(true);
			}
		break;
		}
		default : break;
		}
		
		
		}
		
		
		
	}
}

