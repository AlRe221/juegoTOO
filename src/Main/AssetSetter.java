package Main;

import java.awt.Graphics2D;
import java.util.List;

import Inventario.Alimento;
import Inventario.Arma;
import Inventario.Bebida;
import Inventario.Celular;
import Inventario.Coins;
import Inventario.Comida;
import Inventario.Extintor;
import Inventario.ItemVelocidad;
import Inventario.Laptop;
import Inventario.Mochila;
import Inventario.Objeto;
import entidad.JefePorNivel;
import entidad.Zombie;
import entidad.Zombie1;
import entidad.Zombie2;
import entidad.Zombie3;

import java.util.Random;

public class AssetSetter {
	private GamePanel gP;
	private Graphics2D g2;
	private int idNivel;
	
	public AssetSetter(GamePanel gP) {
		this.gP = gP;
		this.idNivel =1; 
	}
	
	public Objeto objetoUnico() {
		Random rand = new Random();
			int tipo = rand.nextInt(8); // 0: Coins, 1: Alimento (comida), 2. Bebida (comida), 3: itemVelocidad, 4. Mochila (arma), 5. Laptop(arma), 6.Celular(arma), 7.Extintor(arma).
			Objeto o =null;

			switch (tipo) {
		    case 0: o = new Coins(10.0, 5, 6.0, true, 6.0); break;
		    case 1: o = new Alimento(5.0, true, 7.5, "Torta Chilaquil", "Una deliciosa torta de chilaquiles que restaura 20 puntos de energía."); break;
		    case 2: o = new Bebida(10.0, true, 6.0, "Aguita", "Agua fresca que restaura 15 puntos de hidratación."); break;
		    case 3: o = new ItemVelocidad(5.0, true, 6.0, "Aumenta tu velocidad un 50% por 6 segundos."); break;
		    case 4: o = new Mochila(3, 3.0, true, 6.0, "Mochila", "Aumenta tu capacidad de inventario en +4 ranuras."); break;
		    case 5: o = new Laptop(4, 10.0, true, 12.5, "Portátil", "Laptop potente que mejora tu visión del mapa."); break;
		    case 6: o = new Celular(4, 8.5, true, 10.0, "Celular", "Emite una señal que aturde a los enemigos cercanos."); break;
		    case 7: o = new Extintor(5, 7.0, true, 8.0, "Extintor", "Apaga incendios y hace 5 puntos de daño a enemigos."); break;
		}

			// posición aleatoria
			int x,y,tilen;
			do { //esto es para que los objetos no salgan en espacios donde el perosnaje no puede pasar por colisión
			x = rand.nextInt(109); // suponiendo 50 tiles en X
			y = rand.nextInt(109); // suponiendo 30 tiles en Y
			
			tilen = gP.mTi.getCodigoMapaTiles(y,x);
			
			}while(gP.mTi.getColisionDeTile(tilen) == true);
			
			o.setWorldX(x * gP.getTamanioTile());
			o.setWorldY(y * gP.getTamanioTile());
			
			return o;
		}
		  
	public void setObject(){
		for (int i = 0; i < gP.o.length; i++) {
			gP.o[i] = objetoUnico();
		}
		
	}

	public Zombie zombieUnico() {
		Random rand = new Random();
		int tipo = rand.nextInt(3); //1.z1, 2.z2, 3.z3 
		Zombie z =null;

		switch (tipo) {
			case 0:
				z = new Zombie1("inf1", gP);
				break;
			case 1:
				z = new Zombie2("inf2", gP);
				break;
			case 2 :
				z = new Zombie3("inf3", gP);
				break;	
			
		}	

		// posición aleatoria
		int x,y,tilen;
		do { //esto es para que los objetos no salgan en espacios donde el perosnaje no puede pasar por colisión
		x = rand.nextInt(109); // suponiendo 109 tiles en X
		y = rand.nextInt(109); // suponiendo 109tiles en Y
		
		tilen = gP.mTi.getCodigoMapaTiles(y,x);
		
		}while(gP.mTi.getColisionDeTile(tilen) == true);
		
		z.setMundoX(x * gP.getTamanioTile());
		z.setMundoY(y * gP.getTamanioTile());
		
		return z;
	}
	
	private double tiempoAparecer = 0;
	private boolean aparecer = false;
	private int contadorZ = 0;
	
	//los zombies se generan despues de 20 seg, para que no esten al inicio en el salon de clases con el jugador. 
	public void setObjectZ(){
		if(!aparecer) {
		tiempoAparecer += 1.0/60.0;
		if(tiempoAparecer >= 20.0) {
			aparecer = true;
		}
		}
		//se cambio el for para evitar el lag de que todos los zombies salieran de una tras los 20 seg 
		if (aparecer && contadorZ < gP.z.length) {
	        gP.z[contadorZ] = zombieUnico();
	        contadorZ++;
	    }
	}	
	
	//NOTIFICACION
	private boolean mostrarN = false; 
	private double tiempoN = 0; 
	private int idJN= -1;
	
	
	public JefePorNivel jefe() {
		JefePorNivel j = null;
		
		if(!aparecer) {
			tiempoAparecer += 1.0/60.0;
			if(tiempoAparecer >= 10.0 && idNivel <=3) {
				aparecer = true;
				tiempoAparecer = 0;
			}
		}
		
		if(aparecer && idNivel <= 3) {
	        switch(idNivel) {
	            case 1:
	                // Usamos el nuevo constructor para configurar el primer jefe
	                j = new JefePorNivel(gP, 1, "Miguelito", ": EDIFICIO I", "/JefesPorNivel/miguelito1", "/JefesPorNivel/miguelito2");
	                j.setMundoX(4 * gP.getTamanioTile());
	                j.setMundoY(53 * gP.getTamanioTile());
	                break;
	            case 2:
	                // Configuramos el segundo jefe
	                j = new JefePorNivel(gP, 2, "Eloy", ": DFM", "/JefesPorNivel/eloy1", "/JefesPorNivel/eloy2");
	                j.setMundoX(33 * gP.getTamanioTile());
	                j.setMundoY(73 * gP.getTamanioTile());
	                break;
	            case 3:
	                // Configuramos el tercer jefe
	                j = new JefePorNivel(gP, 3, "Nacho", ": EXPLANADA ENGRANAJE", "/JefesPorNivel/nacho1", "/JefesPorNivel/nacho2");
	                j.setMundoX(15 * gP.getTamanioTile());
	                j.setMundoY(41 * gP.getTamanioTile());
	                break;
	        }
		
        if (j != null) {
            mostrarN = true; 
            idJN = idNivel - 1; 
            
            idNivel++;
            aparecer = false; 
        }
		
	 }	
		return j;
	}
	
	
	public void setJF() {
		JefePorNivel NJ = jefe(); 
		if(NJ != null ) {
			gP.jF[idJN] = NJ;
		}
	}
 
	public int getIDNivel() {
		return this.idNivel;
	}
	
	public boolean getNotificacion() {
		return this.mostrarN;
	}
	
	public void setNotificacion(boolean n) {
		 this.mostrarN = n;
	}
	
	public double getTiempoN() {
		return this.tiempoN;
	}
	
	public int getidJFN() {
		return this.idJN;
	}
	
	public void setidJFN(int i) {
		this.idJN = i;
	}
	
	public void setTiempoN(double t) {
		this.tiempoN = t;
	}
}
