import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.*;

public class PlayerField  extends JFrame implements ActionListener {
    static boolean isGameStarted;

    static {
        isGameStarted = false;
    }

    boolean hasShip[][];
    boolean wasChecked[][];
    JButton buttons[][];
    int amountOfPoints;

    PlayerField() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 360;
        int sizeHeight = 400;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        setBounds(locationX, locationY, sizeWidth, sizeHeight);

        setTitle("Поле противника");
        hasShip = new boolean[10][10];
        wasChecked = new boolean[10][10];
        amountOfPoints = 20;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                isGameStarted = false;
                dispose();
            }
        });
        setLayout(null);
        generateField();
        buttons = new JButton[10][];
        for (int i = 0; i < 10; ++i)
            buttons[i] = new JButton[10];

        for (int y = 0; y < 10; ++y)
        {
            for (int x = 0; x < 10; ++x)
            {
                buttons[y][x] = new JButton();
                buttons[y][x].setBounds(10 + 35*x,10+35*y, 30,30);
                buttons[y][x].addActionListener(this);
                add(buttons[y][x]);

            }
        }
        isGameStarted = true;
        setVisible(true);

    }

    void generateField() {
        Random rand = new Random();
        boolean isBusy[][] = new boolean[10][10];

        for (int size = 4; size > 0; --size)
            for (int amount = 4 - size; amount >= 0; --amount) {
                boolean foundSpot = false;
                while (!foundSpot) {
                    int startX = rand.nextInt(10);
                    int startY = rand.nextInt(10);
                    while (isBusy[startY][startX]) {
                        if (startX == 9 && startY == 9) {
                            startX = 0;
                            startY = 0;
                        } else if (startX < 9)
                            startX++;
                        else {
                            startX = 0;
                            startY++;
                        }
                    }


                    boolean mayFit = true;
                    boolean horizontal = rand.nextBoolean();
                    for (int cell = 0; cell < size; ++cell) {
                        if (startX + ((horizontal) ? cell : 0) >= 10 || startY + ((horizontal) ? 0 : cell) >= 10 || (isBusy[startY + ((horizontal) ? 0 : cell)][startX + ((horizontal) ? cell : 0)])) {
                            mayFit = false;
                            break;
                        }
                    }
                    if (mayFit) {
                        foundSpot = true;
                        for (int cell = 0; cell < size; ++cell) {
                            hasShip[startY + ((horizontal) ? 0 : cell)][startX + ((horizontal) ? cell : 0)] = true;
                            isBusy[startY + ((horizontal) ? 0 : cell)][startX + ((horizontal) ? cell : 0)] = true;
                        }
                        if (horizontal) {
                            if (startX - 1 >= 0) {
                                isBusy[startY][startX - 1] = true;
                                if (startY + 1 < 10)
                                    isBusy[startY + 1][startX - 1] = true;
                                if (startY - 1 >= 0)
                                    isBusy[startY - 1][startX - 1] = true;
                            }

                            if (startX + size < 10) {
                                isBusy[startY][startX + size] = true;
                                if (startY + 1 < 10)
                                    isBusy[startY + 1][startX + size] = true;
                                if (startY - 1 >= 0)
                                    isBusy[startY - 1][startX + size] = true;
                            }
                            if (startY - 1 >= 0)
                                for (int i = 0; i < size; ++i)
                                    isBusy[startY - 1][startX + i] = true;
                            if (startY + 1 < 10)
                                for (int i = 0; i < size; ++i)
                                    isBusy[startY + 1][startX + i] = true;
                        } else {
                            if (startY - 1 >= 0) {
                                if (startX + 1 < 10)
                                    isBusy[startY - 1][startX + 1] = true;
                                if (startX - 1 >= 0)
                                    isBusy[startY - 1][startX - 1] = true;
                                isBusy[startY - 1][startX] = true;
                            }
                            if (startY + size < 10) {
                                if (startX + 1 < 10)
                                    isBusy[startY + size][startX + 1] = true;
                                if (startX - 1 >= 0)
                                    isBusy[startY + size][startX - 1] = true;
                                isBusy[startY + size][startX] = true;
                            }
                            if (startX - 1 >= 0)
                                for (int i = 0; i < size; ++i)
                                    isBusy[startY + i][startX - 1] = true;
                            if (startX + 1 < 10)
                                for (int i = 0; i < size; ++i)
                                    isBusy[startY + i][startX + 1] = true;
                        }
                    }
                }
            }
    }

    void showField() {
        System.out.print("   ");
        for (int i = 0; i < 10; ++i)
            System.out.print(i + 1 + " ");
        System.out.println();
        for (int y = 0; y < 10; ++y) {
            if (y != 9)
                System.out.print(' ');
            System.out.print(y + 1);
            System.out.print(' ');
            for (int x = 0; x < 10; ++x) {

                System.out.print((hasShip[y][x] ? '*' : ' ') + " ");
            }
            System.out.println();
        }
    }

    void showPlayerField() {
        System.out.print("   ");
        for (int i = 0; i < 10; ++i)
            System.out.print(i + 1 + " ");
        System.out.println();
        for (int y = 0; y < 10; ++y) {
            if (y != 9)
                System.out.print(' ');
            System.out.print(y + 1);
            System.out.print(' ');
            for (int x = 0; x < 10; ++x) {

                if (wasChecked[y][x])
                    System.out.print((hasShip[y][x] ? '*' : ' ') + " ");
                else
                    System.out.print("? ");
            }
            System.out.println();
        }
    }

    boolean checkCell(int y, int x) {
        wasChecked[y][x] = true;
        if (hasShip[y][x])
            amountOfPoints--;
        return amountOfPoints == 0;
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 100; ++i)
                if (e.getSource() == buttons[i/10][i%10] && !wasChecked[i/10][i%10])
                {
                    wasChecked[i/10][i%10] = true;
                    if (hasShip[i/10][i%10])
                    {
                        buttons[i/10][i%10].setBackground(Color.GREEN);
                        amountOfPoints--;
                    }
                    else
                        buttons[i/10][i%10].setBackground(Color.BLUE);
                    break;
                }
        if (amountOfPoints == 0)
        {
            JOptionPane.showMessageDialog(null, "Вы победили!");
            dispose();
            isGameStarted = false;
        }
    }

}
