import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class PlayerField extends JFrame {
    static EnemyField enemyField;

    static boolean shipsPlaced;

    static {
        shipsPlaced = false;
    }

    boolean hasShip[][];
    boolean wasChecked[][];
    boolean isBusy[][];
    JButton buttons[][];
    int amountOfPoints = 20;

    int size = 4;
    int amount = 1;
    boolean horizontal = true;


    PlayerField(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 360;
        int sizeHeight = 400;
        int locationX = (screenSize.width - sizeWidth) / 2 - 180;
        int locationY = (screenSize.height - sizeHeight) / 2;
        setBounds(locationX, locationY, sizeWidth, sizeHeight);

        setTitle("Ваше поле");
        hasShip = new boolean[10][10];
        wasChecked = new boolean[10][10];
        isBusy = new boolean[10][10];
        amountOfPoints = 20;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                EnemyField.isGameStarted = false;
                enemyField.dispose();
                dispose();
            }
        });
        setLayout(null);
        buttons = new JButton[10][];
        for (int i = 0; i < 10; ++i)
            buttons[i] = new JButton[10];

        for (int y = 0; y < 10; ++y)
        {
            for (int x = 0; x < 10; ++x)
            {
                buttons[y][x] = new JButton();
                buttons[y][x].setBounds(10 + 35*x,10+35*y, 30,30);
                buttons[y][x].setBackground(Color.WHITE);
                buttons[y][x].addMouseListener(new MouseAdapter()
                {
                    public void mouseEntered(MouseEvent e)
                    {
                        for (int i = 0; i < 100; ++i)
                            if (e.getSource() == buttons[i/10][i%10])
                                if (horizontal && i%10 <= 10 - size || !horizontal && i/10 <= 10 - size)
                                for (int j = 0; j < size; ++j)
                                    buttons[(horizontal)?i/10:i/10+j][(horizontal)?(i+j)%10:i%10].setBackground(Color.GREEN);
                    }
                    public void mouseExited(MouseEvent e)
                    {
                        for (int i = 0; i < 100; ++i)
                            if (e.getSource() == buttons[i/10][i%10] )
                                if (horizontal && i%10 <= 10 - size || !horizontal && i/10 <= 10 - size)
                                for (int j = 0; j < size; ++j)
                                    if (!hasShip[(horizontal)?i/10:i/10+j][(horizontal)?(i+j)%10:i%10])
                                        buttons[(horizontal)?i/10:i/10+j][(horizontal)?(i+j)%10:i%10].setBackground(Color.WHITE);
                    }
                    public void mousePressed(MouseEvent e)
                    {
                        switch (e.getButton()) {
                            case MouseEvent.BUTTON3: {
                                for (int i = 0; i < 100; ++i)
                                    if (e.getSource() == buttons[i / 10][i % 10])
                                        if (horizontal && i % 10 <= 10 - size || !horizontal && i / 10 <= 10 - size)
                                            for (int j = 0; j < size; ++j) {
                                                buttons[(horizontal) ? i / 10 : i / 10 + j][(horizontal) ? (i + j) % 10 : i % 10].setBackground(Color.WHITE);
                                                buttons[(horizontal) ? i / 10 + j : i / 10][(horizontal) ? i % 10 : (i + j) % 10].setBackground(Color.GREEN);
                                            }

                                horizontal = !horizontal;
                                break;
                            }
                            case MouseEvent.BUTTON1:{

                                for (int i = 0; i < 100; ++i)
                                    if (e.getSource() == buttons[i / 10][i % 10]) {

                                        if (horizontal && i % 10 <= 10 - size || !horizontal && i / 10 <= 10 - size) {
                                            boolean mayFit = true;
                                            for (int j = 0; j < size; ++j)
                                                if (isBusy[(horizontal) ? i / 10 : i / 10 + j][(horizontal) ? (i + j) % 10 : i % 10])
                                                    mayFit = false;
                                                if (mayFit) {
                                                    for (int j = 0; j < size; ++j){
                                                        hasShip[(horizontal) ? i / 10 : i / 10 + j][(horizontal) ? (i + j) % 10 : i % 10] = true;
                                                        isBusy[(horizontal) ? i / 10 : i / 10 + j][(horizontal) ? (i + j) % 10 : i % 10] = true;
                                                        buttons[(horizontal) ? i / 10 : i / 10 + j][(horizontal) ? (i + j) % 10 : i % 10].setBackground(Color.GREEN);
                                                    }

                                                    if (horizontal) {
                                                        if (i%10 - 1 >= 0) {
                                                            isBusy[i/10][i%10 - 1] = true;
                                                            if (i/10 + 1 < 10)
                                                                isBusy[i/10 + 1][i%10 - 1] = true;
                                                            if (i/10 - 1 >= 0)
                                                                isBusy[i/10 - 1][i%10 - 1] = true;
                                                        }

                                                        if (i%10 + size < 10) {
                                                            isBusy[i/10][i%10 + size] = true;
                                                            if (i/10 + 1 < 10)
                                                                isBusy[i/10 + 1][i%10 + size] = true;
                                                            if (i/10 - 1 >= 0)
                                                                isBusy[i/10 - 1][i%10 + size] = true;
                                                        }
                                                        if (i/10 - 1 >= 0)
                                                            for (int j = 0; j < size; ++j)
                                                                isBusy[i/10 - 1][i%10 + j] = true;
                                                        if (i/10 + 1 < 10)
                                                            for (int j = 0; j < size; ++j)
                                                                isBusy[i/10 + 1][i%10 + j] = true;
                                                    } else {
                                                        if (i/10 - 1 >= 0) {
                                                            if (i%10 + 1 < 10)
                                                                isBusy[i/10 - 1][i%10 + 1] = true;
                                                            if (i%10 - 1 >= 0)
                                                                isBusy[i/10 - 1][i%10 - 1] = true;
                                                            isBusy[i/10 - 1][i%10] = true;
                                                        }
                                                        if (i/10 + size < 10) {
                                                            if (i%10 + 1 < 10)
                                                                isBusy[i/10 + size][i%10 + 1] = true;
                                                            if (i%10 - 1 >= 0)
                                                                isBusy[i/10 + size][i%10 - 1] = true;
                                                            isBusy[i/10 + size][i%10] = true;
                                                        }
                                                        if (i%10 - 1 >= 0)
                                                            for (int j = 0; j < size; ++j)
                                                                isBusy[i/10 + j][i%10 - 1] = true;
                                                        if (i%10 + 1 < 10)
                                                            for (int j = 0; j < size; ++j)
                                                                isBusy[i/10 + j][i%10 + 1] = true;
                                                    }
                                                    if (--amount == 0) {
                                                        amount = 6 - size;
                                                        size--;
                                                    }
                                                    if (size == 0)
                                                    {
                                                        amount = 0;
                                                        JOptionPane.showMessageDialog(null, "Теперь можно играть)");

                                                        shipsPlaced = true;
                                                    }
                                                }


                                        }

                                        break;
                                    }
                                break;
                            }
                        }
                    }
                    public void mouseReleased(MouseEvent evt)
                    {
                    }
                });
                add(buttons[y][x]);

            }
        }
        enemyField = new EnemyField();

        setVisible(true);
        JOptionPane.showMessageDialog(null, "Расставьте корабли на своём поле");

    }

    public boolean hit() {
        Random rand = new Random();
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);


        while (wasChecked[y][x]) {
            if (x == 9 && y == 9) {
                x = 0;
                y = 0;
            } else if (x < 9)
                x++;
            else {
                x = 0;
                y++;
            }
        }

        wasChecked[y][x] = true;
        if (hasShip[y][x]) {
            buttons[y][x].setBackground(Color.RED);
            amountOfPoints--;
        } else
            buttons[y][x].setBackground(Color.BLUE);
        return amountOfPoints == 0;
    }


}
