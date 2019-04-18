import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class CellButtonPressed implements ActionListener {
    private JButton btn;
    private boolean hasShip;

    CellButtonPressed(JButton btn, boolean hasShip) {
        this.btn = btn;
        this.hasShip = hasShip;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hasShip) btn.setBackground(Color.GREEN);
        else btn.setBackground(Color.RED);

        Random rand = new Random();
        int i;
        int j;
        do {
            i = rand.nextInt(10);
            j = rand.nextInt(10);
        } while (!PlayerGameFrame.checked[i][j]);
        PlayerGameFrame.buttons[i][j].setBackground((PlayerGameFrame.playerField[i][j])?Color.GREEN:Color.GREEN);
        PlayerGameFrame.checked[i][j] = true;
    }
}

class ComputerGameFrame extends JFrame {
    public static boolean[][] computerField = new boolean[10][10];

    ComputerGameFrame() {
        setTitle("Computer field");
        setLayout(null);
        setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 600,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 600,
                520,
                600);

        Font f = new Font("Arial", Font.BOLD, 11);
        setFont(f);
        setResizable(false);

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Random rnd = new Random();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                computerField[i][j] = rnd.nextBoolean();
                JButton btn = new JButton();

                btn.setBounds(i * 50 + 10, j * 50 + 40, 50, 50);

                btn.addActionListener(new CellButtonPressed(btn, computerField[i][j]));
                add(btn);
            }
        }
        setVisible(true);
    }
}

class PlayerGameFrame extends JFrame {
    public static boolean[][] playerField = new boolean[10][10];
    public static boolean[][] checked = new boolean[10][10];
    public static JButton[][] buttons = new JButton[10][10];
    public static boolean isGameStarted = false;
    PlayerGameFrame() {
        setTitle("Player Field");
        setLayout(null);
        setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 + 203,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 600,
                520,
                600);
        Font f = new Font("Arial", Font.BOLD, 11);
        setFont(f);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Random rand = new Random();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {

                buttons[i][j].setBounds(i * 50 + 10, j * 50 + 40, 50, 50);
                add(buttons[i][j]);
                playerField[i][j] = rand.nextBoolean();
                checked[i][j] = false;
            }
        }
        setVisible(true);

    }
}

