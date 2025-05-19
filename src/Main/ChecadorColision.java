package Main;

import entidad.Entidad;
import entidad.Jugador;
import entidad.Zombie;

public class ChecadorColision {
	
	private GamePanel gP;
	public ChecadorColision(GamePanel gP) {
		this.gP = gP;
	}
	
	public void checkTile(Entidad entidad) {
			
		int izqMundoXentidad = entidad.getMundoX() + entidad.getAreaSolidaX();
		int derMundoXentidad = entidad.getMundoX() + entidad.getAreaSolidaX() + entidad.getAreaSolidaWidth();
		int topMundoYentidad = entidad.getMundoY() + entidad.getAreaSolidaY();
		int bottomMundoYentidad = entidad.getMundoY() + entidad.getAreaSolidaY() + entidad.getAreaSolidaHeigth();
		
		int entidadIzqCol = izqMundoXentidad / gP.getTamanioTile();
		int entidadDerCol = derMundoXentidad / gP.getTamanioTile(); 
		int entidadTopRen = topMundoYentidad / gP.getTamanioTile();
		int entidadBotRen = bottomMundoYentidad / gP.getTamanioTile();
		
		int tileN1,tileN2;
		
		
		switch(entidad.getDireccion()) {
		case "arriba" :{
			entidadTopRen = (topMundoYentidad - entidad.getVelocidad()) /this.gP.getTamanioTile();
			
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadIzqCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadDerCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				entidad.setColisionOn(true);
			}
		break;
		}
		case "abajo" :{
			entidadBotRen = (bottomMundoYentidad + entidad.getVelocidad()) / this.gP.getTamanioTile();
			
			int maxFilas = gP.mTi.getMaxFilas(); 
			
			entidadBotRen = Math.min(entidadBotRen, maxFilas -1);
			
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadIzqCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadDerCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				entidad.setColisionOn(true);
			}
		break;
		}
		case "izquierda" :{
			entidadIzqCol = (izqMundoXentidad - entidad.getVelocidad()) / this.gP.getTamanioTile();
			
			if(entidadIzqCol < 109 && entidadBotRen < 109) {
			tileN1 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadIzqCol);
			tileN2 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadIzqCol);
			
			if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
				entidad.setColisionOn(true);
			}
			}else {
				entidad.setColisionOn(true);
			}
		break;
		}
		case "derecha" :
		{
			entidadDerCol = (derMundoXentidad + entidad.getVelocidad()) / this.gP.getTamanioTile();
			
			if(entidadDerCol < 109 && entidadBotRen < 109) {
				tileN1 = gP.mTi.getCodigoMapaTiles(entidadTopRen, entidadDerCol);
				tileN2 = gP.mTi.getCodigoMapaTiles(entidadBotRen, entidadDerCol);
				
				if(gP.mTi.getColisionDeTile(tileN1) || gP.mTi.getColisionDeTile(tileN2)) {
					entidad.setColisionOn(true);
				}
				}else {
					entidad.setColisionOn(true);
				}
		break;
		}
		default : break;
		}
		
		
	}
	
	public int checkObjeto(Entidad entity, boolean jug) {
		int index = 999; 
		
		for(int i = 0; i < gP.getObjetoInv().length; i++) {
			if(gP.getObjetoInv()[i] != null) {
				if(entity instanceof Jugador) {
					//obtener la posicion del area solida de la entidad (con la que choca pue)
					((Jugador)entity).setSolidAreaX(((Jugador)entity).getMundoX() + ((Jugador)entity).getAreaSolidaX());
					((Jugador)entity).setSolidAreaY(((Jugador)entity).getMundoY() + ((Jugador)entity).getAreaSolidaY());
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
	
	//genenar colisión entre los jugador-zombie
	public int checarEntidad(Entidad e, Entidad[] objetivo) {
	int index = 999; 
		
		for(int i = 0; i < objetivo.length; i++) {
			if(objetivo[i] != null) {
					//obtener la posicion del area solida de la entidad (con la que choca pue)
					e.setSolidAreaX(e.getMundoX() + (e.getAreaSolidaX()));
					e.setSolidAreaY(e.getMundoY() + (e.getAreaSolidaY()));

				//obtener la posicion del area solida del objeto (con la que chocará pue)
				objetivo[i].setSolidAreaX(objetivo[i].getMundoX() + objetivo[i].getAreaSolidaX());
				objetivo[i].setSolidAreaY(objetivo[i].getMundoY() + objetivo[i].getAreaSolidaY());
				
				switch(e.getDireccion()) {
				case "arriba":
					e.setSolidAreaY(e.getAreaSolidaY() - e.getVelocidad());
					break;
				case "abajo": 
					e.setSolidAreaY(e.getAreaSolidaY() + e.getVelocidad());
					break;
				case "derecha": 
					e.setSolidAreaX(e.getAreaSolidaX() + e.getVelocidad());
					break;
				case "izquierda": 
					e.setSolidAreaX(e.getAreaSolidaX() - e.getVelocidad());
					break;
				}
				
				
				if(e.getSolidArea().intersects(objetivo[i].getSolidArea())) { //aquí checamos si ambos rectangulos se estan tocando
					if(objetivo[i] != e) {
						e.setSolidAreaX(e.getAreaSolidaX() - e.getVelocidad());
						e.setSolidAreaY(e.getAreaSolidaY() - e.getVelocidad());
						e.setColisionOn(true);
						index = i;	
					}
				}
				
				e.setSolidAreaX(e.getSolidAreaDefaultX());
				e.setSolidAreaY(e.getSolidAreaDefaultY());
				
				objetivo[i].setSolidAreaX(objetivo[i].getSolidAreaDefaultX());
				objetivo[i].setSolidAreaY(objetivo[i].getSolidAreaDefaultY());
			
			
			}
		}
		return index;
	}
	
	public boolean checkJugador(Entidad e) {
		boolean areaContacto = false;
		e.setSolidAreaX(e.getMundoX() + e.getAreaSolidaX());
		e.setSolidAreaY(e.getMundoY() + e.getAreaSolidaY());

	//obtener la posicion del area solida del objeto (con la que chocará pue)
		gP.getJugador().setSolidAreaX(gP.getJugador().getMundoX() + gP.getJugador().getAreaSolidaX());
		gP.getJugador().setSolidAreaY(gP.getJugador().getMundoY() + gP.getJugador().getAreaSolidaY());
	
	
		switch(e.getDireccion()) {
		case "arriba":
			e.setSolidAreaY(e.getAreaSolidaY() - e.getVelocidad());
		break;
		case "abajo": 
			e.setSolidAreaY(e.getAreaSolidaY() + e.getVelocidad());
			
		break;
		case "derecha": 
			e.setSolidAreaX(e.getAreaSolidaX() + e.getVelocidad());
			
		break;
		case "izquierda": 
			e.setSolidAreaX(e.getAreaSolidaX() - e.getVelocidad());	
		break;
		}
		
		if(e.getSolidArea().intersects(gP.getJugador().getSolidArea())) { //aquí checamos si ambos rectangulos se estan tocando
			e.setColisionOn(true);
			areaContacto =true;
		}
			
		e.setSolidAreaX(e.getSolidAreaDefaultX());
		e.setSolidAreaY(e.getSolidAreaDefaultY());
	
		gP.getJugador().setSolidAreaX(gP.getJugador().getSolidAreaDefaultX());
		gP.getJugador().setSolidAreaY(gP.getJugador().getSolidAreaDefaultY());

		return areaContacto;
	}
	
	
	public boolean checkJefe(Entidad e) {
		//aquí hacemos lo mismo que arriba, para que jugador choquie con jefe y no lo atraviese
		//igual que jefe, haya esa colisión, para que cuando se toquen mutuamente y ambos tengan colisión true
		//cambie la pantalla a la de pelea.
		return true;
	}
		
	}

