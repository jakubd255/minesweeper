package pl.jakubdudek.minesweeper.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameOverDialog extends JDialog {
    private final Board board;

    public GameOverDialog(Board board, boolean isWin) {
        this.board = board;
        initialize(isWin);
    }

    private void initialize(boolean isWin) {
        setSize(300, 150);
        setLayout(new BorderLayout());

        JLabel alertLabel = new JLabel(isWin ? "You win!" : "You hit the bomb.");
        alertLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 30));
        alertLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        add(alertLabel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            board.restart();
            dispose();
        });
        restartButton.setFocusable(false);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setFocusable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(restartButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}