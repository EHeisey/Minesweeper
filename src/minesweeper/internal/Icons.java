package minesweeper.internal;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Contains references to the images used for the GUI minesweeper blocks.
 * @author EliHeisey
 */
class Icons {

    static final ImageIcon BLANK = new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/blank.png"));
    static final ImageIcon BOMB = new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/bomb.png"));
    static final ImageIcon FLAG = new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/flag.png"));
    static final ImageIcon[] NUMBERS = new ImageIcon[]{new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/zero.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/one.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/two.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/three.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/four.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/five.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/six.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/seven.png")),
                                                               new ImageIcon(Icons.class.getResource("/minesweeper/internal/icons/eight.png"))};

    /**
     * Hides the constructor.
     */
    private Icons(){}
    
    /**
     * Sets all the images to the specified size.
     * @param size the desired image size
     */
    static void scaleIcons(int size){
        BLANK.setImage(BLANK.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
        BOMB.setImage(BOMB.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
        FLAG.setImage(FLAG.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
        for(ImageIcon x : NUMBERS){
            x.setImage(x.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
        }
    }
}
