package tile;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;

import Main.ImagenesEscaladas;

public class MemoriaSprite {

    private static final HashMap<String, BufferedImage> cache = new HashMap<>();

    public static BufferedImage load(String ruta, int w, int h) {
        return cache.computeIfAbsent(ruta, r -> {
            try {
                var is = MemoriaSprite.class.getResourceAsStream(r + ".png");
                if (is == null) {
                    System.err.println("⚠ No encontrado: " + r + ".png");
                    return null;
                }
                BufferedImage img = ImageIO.read(is);
                return new ImagenesEscaladas().scaleImage(img, w, h);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
