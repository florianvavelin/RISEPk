public class Victory extends JFrame {

    public Victory(String winner) {
        this.setTitle("Victory");
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setvisible(true);

        JPanel victoryPanel = new JPanel();
        this.setContentPane(victoryPanel);

        JLabel winnerName = new JLabel(winner);
        victoryPanel.add(winnerName);

        JButton recommencer = new JButton("Recommencer");
        victoryPanel.add(recommencer);
    }
}