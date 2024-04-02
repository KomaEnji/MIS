import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test {
    public static void main(String[] args) {
        // Создание JFrame
        JFrame frame = new JFrame("Пример замены JPanel");

// Создание первого JPanel
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Это первый JPanel"));

// Создание второго JPanel
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Это второй JPanel"));

// Создание кнопки, которая будет заменять JPanel
        JButton button = new JButton("Заменить JPanel");
        button.addActionListener(e -> {
            frame.getContentPane().remove(panel1);
            frame.getContentPane().add(panel2);
            frame.revalidate();
            frame.repaint();
        });

// Добавление JPanel и кнопки на JFrame
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

// Настройка JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);

    }
}