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
			
			int maxFilas = gP.mTi.getMaxFilas(); 
			
			entidadBotRen = Math.min(entidadBotRen, maxFilas -1);
			
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
	
	public int checkObjeto(Entidad entity, boolean jug) {
		int index = 999; 
		
		for(int i = 0; i < gP.getObjetoInv().length; i++) {
			if(gP.getObjetoInv()[i] != null) {
				if(entity instanceof Jugador) {
					//obtener la posicion del area solida de la entidad (con la que choca pue)
					((Jugador)entity).setSolidAreaX(((Jugador)entity).getX() + ((Jugador)entity).getAreaSolidaX());
					((Jugador)entity).setSolidAreaY(((Jugador)entity).getY() + ((Jugador)entity).getAreaSolidaY());
				}
				
				//obtener la posicion del area solida del objeto (con la que chocará pue)
				gP.getObjetoInv()[i].setAreaSolidaXO(gP.getObjetoInv()[i].getWorldX() + gP.getObjetoInv()[i].getAreaSolidaXO());
				gP.getObjetoInv()[i].setAreaSolidaYO(gP.getObjetoInv()[i].getWorldY() + gP.getObjetoInv()[i].getAreaSolidaYO());
				
				switch(((Jugador)entity).getDireccion()) {
				case "arriba":
					((Jugador)entity).setSolidAreaY(((Jugador)entity).getAreaSolidaY() - ((Jugador)entity).getVelocidad());
					if(((Jugador)entity).getSolidArea().intersects(gP.getObjetoInv()[i].getSolidArea())) { //aquí checamos si ambos rectangulos se estan tocando
						if(gP.getObjetoInv()[i].getColisionO() == true) {
							entity.setColisionOn(true);
						}
						if(jug ==true) {
							index = i;
						}
					}
					break;
				case "abajo": 
					((Jugador)entity).setSolidAreaY(((Jugador)entity).getAreaSolidaY() + ((Jugador)entity).getVelocidad());
					if(((Jugador)entity).getSolidArea().intersects(gP.getObjetoInv()[i].getSolidArea())) { //aquí checamos si ambos rectangulos se estan tocando
						if(gP.getObjetoInv()[i].getColisionO() == true) {
							entity.setColisionOn(true);
						}
						if(jug ==true) {
							index = i;
						}
					}
					break;
				case "derecha": 
					((Jugador)entity).setSolidAreaX(((Jugador)entity).getAreaSolidaX() - ((Jugador)entity).getVelocidad());
					if(((Jugador)entity).getSolidArea().intersects(gP.getObjetoInv()[i].getSolidArea())) { //aquí checamos si ambos rectangulos se estan tocando
						if(gP.getObjetoInv()[i].getColisionO() == true) {
							entity.setColisionOn(true);
						}
						if(jug ==true) {
							index = i;
						}
					}
					break;
				case "izquierda": 
					((Jugador)entity).setSolidAreaX(((Jugador)entity).getAreaSolidaX() + ((Jugador)entity).getVelocidad());
					if(((Jugador)entity).getSolidArea().intersects(gP.getObjetoInv()[i].getSolidArea())) { //aquí checamos si ambos rectangulos se estan tocando
						if(gP.getObjetoInv()[i].getColisionO() == true) {
							entity.setColisionOn(true);
						}
						if(jug ==true) {
							index = i;
						}
					}
					break;
				}
				((Jugador)entity).setSolidAreaX(entity.getSolidAreaDefaultX());
				((Jugador)entity).setSolidAreaY(entity.getSolidAreaDefaultY());
				
				gP.getObjetoInv()[i].setAreaSolidaXO(gP.getObjetoInv()[i].getDefautlAreaX());
				gP.getObjetoInv()[i].setAreaSolidaYO(gP.getObjetoInv()[i].getDefautlAreaY());
			
			}
		}
		return index;
	}
}

