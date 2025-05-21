package entidad;

import Main.GamePanel;

public class JefeN2 extends JefePorNivel{

	public JefeN2(GamePanel gp) {
		super(gp);
		this.id_Nivel = 2;
		getImage();
	}
	
	
	public void getImage() {
		estatico1 = setup1("/JefesPorNivel/eloy1");
		estatico2 = setup1("/JefesPorNivel/eloy2");
	}

	@Override
	public String getLocation() {
		return ": DFM";
	}
}
