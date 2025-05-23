package Inventario;

import java.awt.Graphics2D;

import Main.GamePanel;
import entidad.Proyectil;

public class ProyectilPaco extends Proyectil{

	GamePanel gP;
	public ProyectilPaco(GamePanel gP) {
		super(gP);
		this.gP = gP;
		
		this.tipoE = 2; 
		this.velocidad = 5; 
		this.vidaMaxima = 80; 
		this.vida = this.vidaMaxima;
		this.ataque = 2;
		this.useCost = 1; 
		this.vivo = false;
		getImage();
		// TODO Auto-generated constructor stub
	}
	
	public void getImage() {
		try {
			this.derCombate1 = setup1("/ProyectilesCombate/poderPaco");
		}catch(Exception e) {
			
		}
	}
	
	@Override
	public void dibujar(Graphics2D g2) {
		
		if(vivo && derCombate1 != null) {
			g2.drawImage(derCombate1, mundoX, mundoY,120,120, null);
		}
	}

}
