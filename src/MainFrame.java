import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame extends JFrame
{
    MainFrame(int x, int y)
    {
        setTitle("Battleship Main");
        setLayout(null);

        Font f = new Font("Arial", Font.BOLD,11);
        setFont(f);

        JButton btn = new JButton("Start new game?");
        btn.setBounds(50,100,200,30);
        add(btn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(x, y,300, 200);

        btn.addActionListener(new newGameButtonPressed());
        setVisible(true);
    }

}
class newGameButtonPressed implements ActionListener
{
    public static boolean warningIsActive = false;
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (!PlayerGameFrame.isGameStarted)
        {
            new PlayerGameFrame();
            new ComputerGameFrame();
            PlayerGameFrame.isGameStarted = true;
        }
        else if (!warningIsActive)
        {
            new WarningFrame("Game is already running");
            warningIsActive = true;
        }
    }
}

class WarningFrame extends JFrame {
    WarningFrame(String message) {
        setTitle("Warning");
        Font f = new Font("Arial", Font.BOLD, 11);
        setFont(f);
        setBounds(500, 500, 200, 50);
        add(new Label(message));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                newGameButtonPressed.warningIsActive = false;
            }
        });
        setVisible(true);
    }
}