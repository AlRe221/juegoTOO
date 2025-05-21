package entidad;

import Main.GamePanel;

public class JefeN3 extends JefePorNivel{
	
	public JefeN3(GamePanel gp) {
		super(gp);
		this.id_Nivel = 3;
		getImage();
	}
	
	
	public void getImage() {
		estatico1 = setup1("/JefesPorNivel/nacho1");
		estatico2 = setup1("/JefesPorNivel/nacho2");
	}


	@Override
	public String getLocation() {
		return ": EXPLANADA ENGRANAJE";
	}
}
