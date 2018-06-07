<<<<<<< HEAD
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
=======
import javax.swing.*;
import java.awt.*;
>>>>>>> ac1a92f611761f819df87f13bc4f390fd58d940f

public class Victory extends JFrame {

    public Victory(String winner) {
        this.setTitle("Victory");
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
<<<<<<< HEAD
        this.setSize(new Dimension(600, 100));
=======
        this.setVisible(true);
>>>>>>> ac1a92f611761f819df87f13bc4f390fd58d940f

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
        validate();
        this.setVisible(true);
    }
}