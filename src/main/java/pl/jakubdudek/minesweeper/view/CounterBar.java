package pl.jakubdudek.minesweeper.view;

import pl.jakubdudek.minesweeper.config.Config;
import pl.jakubdudek.minesweeper.counter.Counter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CounterBar extends JPanel {
    private JLabel flagsInfoLabel;

    public CounterBar() {
        initialize();
    }

    private void initialize() {
        super.setBounds(
                Config.GAP,
                Config.GAP,
                Config.COLUMNS*Config.BUTTON_SIZE + (Config.COLUMNS-1)*Config.GAP,
                Config.TOPBAR
        );
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BorderLayout());

        flagsInfoLabel = new JLabel(Integer.toString(Counter.FLAGS));
        flagsInfoLabel.setVerticalAlignment(JLabel.CENTER);
        flagsInfoLabel.setBorder(new EmptyBorder(0, Config.GAP, 0, 0));
        flagsInfoLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));

        add(flagsInfoLabel);
    }

    public void updateFlags(int diff) {
        Counter.FLAGS += diff;
        flagsInfoLabel.setText(Integer.toString(Counter.FLAGS));
    }

    public void restart() {
        Counter.restart();
        flagsInfoLabel.setText(Integer.toString(Counter.FLAGS));
    }
}
