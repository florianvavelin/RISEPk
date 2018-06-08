import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Victory extends JFrame {

    public Victory(String winner) {
        this.setTitle("Victory");
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);

        this.setSize(new Dimension(600, 100));

        JPanel victoryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setContentPane(victoryPanel);

        JLabel winnerName = new JLabel(winner + " félicitation tu as gagné !!");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        victoryPanel.add(winnerName, c);

        JButton recommencer = new JButton("Recommencer");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        victoryPanel.add(recommencer, c);
        recommencer.addMouseListener(new recommencer() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
            }
        });

        JButton quitter = new JButton("Quitter");
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        victoryPanel.add(quitter, c);
        quitter.addMouseListener(new quitter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
            }
        });
        validate();
    }

        abstract class recommencer implements MouseListener {

            public void mouseClicked(MouseEvent event) {
            }

            public void mouseEntered(MouseEvent event) {
            }

            public void mouseExited(MouseEvent event) {
            }

            public void mousePressed(MouseEvent event) {
            }

            public void mouseReleased(MouseEvent event) {
            }
        

        }

        abstract class quitter implements MouseListener {

        public void mouseClicked(MouseEvent event) {
            System.exit(0);
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mousePressed(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
        }


    }

}