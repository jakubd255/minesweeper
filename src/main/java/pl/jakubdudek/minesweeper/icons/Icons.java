package pl.jakubdudek.minesweeper.icons;

import pl.jakubdudek.minesweeper.config.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Icons {
    public static final ImageIcon FLAG;
    public static final ImageIcon MINE;
    public static final ImageIcon WRONG;
    public static final ImageIcon CORRECT;

    static {
        try {
            FLAG = getScaled(ImageIO.read(new File("src/main/resources/flag.png")));
            MINE = getScaled(ImageIO.read(new File("src/main/resources/mine.png")));
            WRONG = getScaled(ImageIO.read(new File("src/main/resources/wrong.png")));
            CORRECT = getScaled(ImageIO.read(new File("src/main/resources/correct.png")));
        }
        catch(IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    private static ImageIcon getScaled(BufferedImage image) {
        return new ImageIcon(image.getScaledInstance(
                Config.BUTTON_SIZE-20,
                Config.BUTTON_SIZE-20,
                Image.SCALE_SMOOTH
        ));
    }
}
