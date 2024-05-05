package pl.jakubdudek.minesweeper.view;

import pl.jakubdudek.minesweeper.config.Config;
import pl.jakubdudek.minesweeper.counter.Counter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

public class Board extends JFrame {
    private boolean isGameOver;

    private final Cell[][] cells;
    private final CounterBar counterBar;

    public Board() {
        super("Minesweeper");

        cells = new Cell[Config.ROWS][Config.COLUMNS];
        counterBar = new CounterBar();

        initialize();
    }

    private void initialize() {
        setSize(
                Config.COLUMNS*Config.BUTTON_SIZE + (Config.COLUMNS+1)*Config.GAP,
                Config.ROWS*Config.BUTTON_SIZE + (Config.ROWS+2)*Config.GAP + 30 + Config.TOPBAR
        );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        add(counterBar);

        initializeBoard();
        generateCells();
    }

    private void initializeBoard() {
        isGameOver = false;

        for(int i=0; i<Config.ROWS; i++) {
            for(int j=0; j<Config.COLUMNS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        setBombs();
    }

    private void setBombs() {
        Random rand = new Random();
        int bombsPlaced = 0;

        while(bombsPlaced < Config.BOMBS) {
            int row = rand.nextInt(Config.ROWS);
            int col = rand.nextInt(Config.COLUMNS);

            if(!cells[row][col].isBomb()) {
                cells[row][col].setBomb(true);
                bombsPlaced++;
            }
        }
    }

    private void generateCells() {
        for(Cell[] cellsRow : cells) {
            for(Cell cell : cellsRow) {
                cell.generate();

                //On click
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(cell.isEnabled() && !isGameOver) {
                            //Right click
                            if(e.getButton() == 3) {
                                cell.toggleFlagged(counterBar);
                            }
                            //Left click
                            else {
                                reveal(cell.getRow(), cell.getColumn());
                            }
                        }
                    }
                });

                add(cell);
            }
        }
    }

    private void gameOver(boolean isWin) {
        isGameOver = true;

        for(Cell[] cellsRow : cells) {
            for(Cell cell : cellsRow) {
                cell.showBomb();
            }
        }

        GameOverDialog gameOverDialog = new GameOverDialog(this, isWin);
        gameOverDialog.setVisible(true);
    }

    private int countNeighboringBombs(int row, int col) {
        int count = 0;
        for(int i=row-1; i<=row+1; i++) {
            for(int j=col-1; j<=col+1; j++) {
                if(i>=0 && i<Config.ROWS && j>=0 && j<Config.COLUMNS && cells[i][j].isBomb()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void reveal(int row, int col) {
        if(row<0 || row>=Config.ROWS || col<0 || col>=Config.COLUMNS || cells[row][col].isRevealed()) {
            return;
        }

        Cell cell = cells[row][col];

        cell.reveal();

        if(cell.isBomb()) {
            gameOver(false);
        }
        else {
            if(cell.isFlagged()) {
                cell.toggleFlagged(counterBar);
            }

            //Count bombs in neighboring cells
            int bombsCount = countNeighboringBombs(row, col);
            if(bombsCount > 0) {
                cell.setText(Integer.toString(bombsCount));
            }

            //If no neighboring bombs, reveal next recursively
            if(bombsCount == 0) {
                reveal(row - 1, col); //Up
                reveal(row + 1, col); //Down
                reveal(row, col - 1); //Left
                reveal(row, col + 1); //Right
                reveal(row - 1, col - 1); //Top left
                reveal(row - 1, col + 1); //Top right
                reveal(row + 1, col - 1); //Bottom left
                reveal(row + 1, col + 1); //Bottom right
            }

            if(Counter.CELLS_TO_REVEAL == 0) {
                gameOver(true);
            }
        }
    }

    public void restart() {
        counterBar.restart();

        for(Cell[] cellsRow : cells) {
            for(Cell cell : cellsRow) {
                cell.reset();
            }
        }
        setBombs();

        isGameOver = false;
    }
}