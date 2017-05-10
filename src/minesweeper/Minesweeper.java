package minesweeper;

import minesweeper.internal.GridWindow;
import javax.swing.*;
import java.awt.GridLayout;

/**
 * Static utilities to start a new minesweeper game.
 * 
 * @author Eli Heisey
 */
public class Minesweeper {

    /**
     * Constant identifier for level one of minesweeper
     */
    public static final int LEVEL_ONE = 1;
    
    /**
     * Constant identifier for level two of minesweeper
     */
    public static final int LEVEL_TWO = 2;
    
    /**
     * Constant identifier for level three of minesweeper
     */
    public static final int LEVEL_THREE = 3;

    /**
     * Starts a new game of minesweeper of the specified level.
     * @param level the level of minesweeper to play
     */
    public static void play(int level){
        if(level < 1 || level > 3) throw new IllegalArgumentException("Level "+level+" is not a valid level.");
        SwingUtilities.invokeLater(() -> new GridWindow(level).show());
    }
    
    /**
     * Launches a dialog window to prompt for a level.
     */
    public static void play(){
        JFrame frame = new JFrame("Minesweeper Level Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(900, 150);
        frame.setLocation(100, 100);
        JButton[] buttons = new JButton[]{new JButton("Level 1: 9 x 9 grid (10 bombs)"), 
                                          new JButton("Level 2: 16 x 16 grid (40 bombs)"), 
                                          new JButton("Level 3: 16 x 30 grid (99 bombs)")};
        JPanel panel = new JPanel(new GridLayout(1, 3));
        for(JButton x : buttons){
            panel.add(x);
            x.addActionListener(e -> {
                int level = 1;
                for(int i = 0; i<3; i++){
                    if(e.getSource()==buttons[i]) level = i+1;
                }
                frame.setVisible(false);
                play(level);
            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
    * Hidden from public
    */
    private Minesweeper(){}

}
