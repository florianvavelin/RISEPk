import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.AWTException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import edu.princeton.cs.introcs.StdDraw;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Main {
    public static void main(String[] args) {
        Fenetre fen = new Fenetre();
    }

    public static void map() {
        int width = 1000;
        int height = 492;
        StdDraw.setCanvasSize(width,height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("map2.jpg"));
        } catch (IOException e) {
        }

        while (true) {
            StdDraw.clear();
            StdDraw.picture(width/2, height/2, "map2.jpg");
            // mouse click
            if (StdDraw.isMousePressed()) {
                // mouse location
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                int positionX = (int) x;
                int positionY = height - (int) y; //change Y origin
                //System.out.println(positionX);
                //System.out.println(positionY);
                int c = img.getRGB(positionX, positionY);
                int red = (c>>16) & 0xff;
                int green = (c>>8) & 0xff;
                int blue = c & 0xff;
                Color color = new Color(red,green,blue);
                System.out.println(color);

                StdDraw.setPenColor(color);
                StdDraw.filledCircle(x, y, 100);
            }
            StdDraw.pause(200);
        }
    }
}
