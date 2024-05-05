package pl.jakubdudek.minesweeper.counter;

import pl.jakubdudek.minesweeper.config.Config;

public class Counter {
    public static int BOMBS_TO_FLAG;
    public static int FLAGS;
    public static int CELLS_TO_REVEAL;

    static {
        restart();
    }

    public static void restart() {
        BOMBS_TO_FLAG = Config.BOMBS;
        FLAGS = Config.BOMBS;
        CELLS_TO_REVEAL = Config.COLUMNS*Config.ROWS-Config.BOMBS;
    }
}
