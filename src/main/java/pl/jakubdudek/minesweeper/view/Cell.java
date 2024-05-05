package pl.jakubdudek.minesweeper.view;

import lombok.*;
import pl.jakubdudek.minesweeper.config.Config;
import pl.jakubdudek.minesweeper.counter.Counter;
import pl.jakubdudek.minesweeper.icons.Icons;

import javax.swing.*;
import java.awt.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Cell extends JButton {
    @NonNull
    private final int row;
    @NonNull
    private final int column;

    @Setter
    private boolean isBomb = false;

    private boolean isFlagged = false;
    private boolean isRevealed = false;

    @Setter
    private int bombNeighbors;

    public void generate() {
        super.setBounds(
                column*Config.BUTTON_SIZE + (column+1)*Config.GAP,
                row*Config.BUTTON_SIZE + (row+2)*Config.GAP + Config.TOPBAR,
                Config.BUTTON_SIZE,
                Config.BUTTON_SIZE
        );
        setFocusable(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    }

    public void reveal() {
        if(!isFlagged) {
            isRevealed = true;
            setEnabled(false);
            Counter.CELLS_TO_REVEAL--;
        }
    }

    public void showBomb() {
        if(isBomb) {
            setIcon(isFlagged ? Icons.CORRECT : Icons.MINE);
        }
        else {
            setIcon(isFlagged ? Icons.WRONG : null);
        }
    }

    public void toggleFlagged(CounterBar counterBar) {
        if(!(Counter.FLAGS == 0 && !isFlagged)) {
            isFlagged = !isFlagged;

            if(isFlagged) {
                setIcon(Icons.FLAG);

                if(isBomb) {
                    Counter.BOMBS_TO_FLAG--;
                }

                counterBar.updateFlags(-1);
            }
            else {
                setIcon(null);

                if(isBomb) {
                    Counter.BOMBS_TO_FLAG++;
                }

                counterBar.updateFlags(+1);
            }
        }
    }

    public void reset() {
        isBomb = false;
        isFlagged = false;
        isRevealed = false;

        setEnabled(true);
        setText("");
        setIcon(null);
    }
}