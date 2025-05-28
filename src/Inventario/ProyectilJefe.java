package Inventario;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.GamePanel;
import entidad.Proyectil;

public class ProyectilJefe extends Proyectil{

	GamePanel gP;
	
	public ProyectilJefe(int i, GamePanel gP) {
		super(gP);		
        this.gP = gP;	
		this.tipoE = 3; 
		this.velocidad = 5; 
		this.vidaMaxima = 80; 
		this.vida = this.vidaMaxima;
		this.ataque = 2;
		this.useCost = 1; 
		this.vivo = false;
		getImage(i);
	}

	public void getImage(int i) {
		switch(i) {
		case 1:  this.izqCombate1 = setup1("/ProyectilesCombate/poderMiguelito");
		break; 
		case 2: this.izqCombate1 = setup1("/ProyectilesCombate/poderEloy");
		break;
		case 3: this.izqCombate1 = setup1("/ProyectilesCombate/poderNacho");
		break;
		}
	}
	
	@Override
	public void dibujar(Graphics2D g2) {
		g2.setColor(Color.ORANGE);
		g2.fillRect(this.mundoX,this.mundoY, 40, 40);
	}

	
	public String getDireccion() {
		return this.direccion;
	}
}
