package pl.jakubdudek.minesweeper;

import pl.jakubdudek.minesweeper.view.Board;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Board().setVisible(true);
        });
    }
}

//TODO - Counter - gettery i settery
//TODO - Zasada pojedynczej odpowiedzialnośći