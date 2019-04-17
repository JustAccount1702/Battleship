import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class MainFrame extends Frame
{
    MainFrame(int x, int y)
    {
        setTitle("Battleship Main");
        setLayout(null);

        Font f = new Font("Arial", Font.BOLD,11);
        setFont(f);

        Button btn = new Button("Start new game?");
        btn.setBounds(50,100,200,30);
        add(btn);

        setBounds(x, y,300, 200);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        btn.addActionListener(new newGameButtonPressed());
        setVisible(true);
    }

}

class WarningFrame extends Frame
{
    WarningFrame(String message)
    {
        setTitle("Warning");
        Font f = new Font("Arial", Font.BOLD,11);
        setFont(f);
        setBounds(500,500,200,50);
        add(new TextField(message));
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                dispose();
                newGameButtonPressed.warningIsActive = false;
            }
        });
        setVisible(true);
    }
}
class newGameButtonPressed implements ActionListener
{
    public static boolean warningIsActive = false;
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (!GameFrame.isGameStarted)
        {
            new GameFrame();
            GameFrame.isGameStarted = true;
        }
        else if (!warningIsActive)
        {
            new WarningFrame("Game is already running");
            warningIsActive = true;
        }
    }
}

class CheckCellButtonPressed implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //TODO
    }
}
class GameFrame extends Frame
{
    public static boolean isGameStarted = false;
    public static boolean[][] playerField = new boolean[10][10];
    GameFrame()
    {
        setTitle("Battleship Main");
        setLayout(null);
        setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 600,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 600,
                520,
                600);

        Font f = new Font("Arial", Font.BOLD, 11);
        setFont(f);

        Random rnd = new Random();
        for (int i = 0; i < 10; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                playerField[i][j] = rnd.nextBoolean();
                Button btn = new Button((playerField[i][j] ? "1" : "0"));

                btn.setBounds(i * 50 + 10, j * 50 + 40, 50, 50);

                btn.addActionListener(new CheckCellButtonPressed());
                add(btn);
            }
            setVisible(true);
        }
    }
}


public class Main
{

    public static void main(String[] args)
    {
        MainFrame mainFrame = new MainFrame(400,300);

    }
}
