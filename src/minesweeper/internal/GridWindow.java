package minesweeper.internal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author EliHeisey
 */
public class GridWindow {

    private JFrame frame;
    private BlockButton[][] buttons;
    private BlockButtonListener blockListener;
    private Grid grid;
    private JTextArea flagCount;
    private JTextArea clock;
    private Timer timer;
    private int flags;
    private int time;

    public GridWindow(int level){
        grid = new Grid(level);
        int blockSize = 50;
        Icons.scaleIcons(blockSize);
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(grid.getXSize() * blockSize, grid.getYSize() * blockSize);
        frame.setLocation(60, 60);
        frame.setIconImage(Icons.BOMB.getImage());
        frame.setResizable(false);
        flags = 0;
        flagCount = new JTextArea(" "+flags+" / "+grid.bombs());
        flagCount.setEnabled(false);
        flagCount.setDisabledTextColor(Color.DARK_GRAY);
        flagCount.setFont(new Font(Font.DIALOG, Font.BOLD, 38));
        time = 0;
        clock = new JTextArea("Time: "+time);
        clock.setFocusable(false);
        clock.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        timer = new Timer(1000, e -> clock.setText("Time: "+(++time)));
        buttons = new BlockButton[grid.getYSize()][grid.getXSize()];
        blockListener = new BlockButtonListener();
        JPanel column = new JPanel(new GridLayout(grid.getYSize()+1, 1));
        JPanel[] rows = new JPanel[grid.getYSize()+1];
        for(int i = 0; i < grid.getYSize(); i++){
            rows[i+1] = new JPanel(new GridLayout(1, grid.getXSize()));
            for(int j = 0; j < grid.getXSize(); j++){
                buttons[i][j] = new BlockButton(grid.getBlock(i, j), i, j);
                rows[i+1].add(buttons[i][j]);
            }
        }
        rows[0] = new JPanel(new GridLayout(1, 3));
        rows[0].add(flagCount);
        rows[0].add(clock);
        for(JPanel x : rows) column.add(x);
        frame.add(column);
    }

    public void show(){
        frame.setVisible(true);
    }
    
    private void loss(){
        timer.stop();
        flagCount.setDisabledTextColor(Color.RED);
        flagCount.setText("Game Lost");
        for(BlockButton[] x : buttons){
            for(BlockButton y : x){
                if(!y.isChecked){
                    if(y.block.isBomb()){
                        y.setDisabledIcon(Icons.BOMB);
                    } else {
                        y.setDisabledIcon(Icons.BLANK);
                    }
                }
                y.setEnabled(false);
            }
        }
    }
    
    private void checkWin(){
        for(BlockButton[] x : buttons){
            for(BlockButton y : x){
                if(!y.isChecked^y.block.isBomb()) return;
            }
        }
        for(BlockButton[] x : buttons){
            for(BlockButton y : x){
                if(y.block.isBomb()){
                    if(y.block.isFlagged()){
                        y.setDisabledIcon(Icons.FLAG);
                    } else{
                        y.setDisabledIcon(Icons.BLANK);
                    }
                }
                y.setEnabled(false);
            }
        }
        timer.stop();
        flagCount.setDisabledTextColor(Color.GREEN);
        flagCount.setText("Game Won");
    }
    
    private void updateFlagCount(){
        flagCount.setText(" "+flags+" / "+grid.bombs());
    }

    private class BlockButton extends JToggleButton {

        private Block block;
        private boolean isChecked;
        private int x, y;

        private BlockButton(Block b, int y, int x){
            super(Icons.BLANK);
            setFocusable(false);
            addMouseListener(blockListener);
            this.y = y;
            this.x = x;
            isChecked = false;
            block = b;
        }
        
        private void changeFlag(){
            if(!isEnabled()||isChecked) return;
            block.changeFlag();
            if(block.isFlagged()){
                setIcon(Icons.FLAG);
                flags++;
            } else{
                setIcon(Icons.BLANK);
                flags--;
            }
            updateFlagCount();
        }
        
        private void checkBlock(){
            if(!isEnabled() || block.isFlagged()) return;
            isChecked = true;
            if(block.isBomb()){
                setDisabledIcon(Icons.BOMB);
                setEnabled(false);
            } else{
                setIcon(Icons.NUMBERS[block.adjacentBombs()]);
                setDisabledIcon(Icons.NUMBERS[block.adjacentBombs()]);
            }
            if(block.isBomb()){
                loss();
                return;
            }
            if(block.adjacentBombs()==0) checkAdjacent();
        }
        
        private int adjacentFlags(){
            int flags = 0;
            try{
                if(buttons[y-1][x-1].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y-1][x].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y-1][x+1].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y][x-1].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y][x+1].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y+1][x-1].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y+1][x].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            try{
                if(buttons[y+1][x+1].block.isFlagged()) flags++;
            } catch(IndexOutOfBoundsException e){}
            return flags;
        }
        
        private void checkAdjacent(){
            if(block.adjacentBombs()!=adjacentFlags()) return;
            setEnabled(false);
            try{
                buttons[y-1][x-1].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y-1][x].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y-1][x+1].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y][x-1].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y][x+1].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y+1][x-1].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y+1][x].checkBlock();
            } catch(IndexOutOfBoundsException e){}
            try{
                buttons[y+1][x+1].checkBlock();
            } catch(IndexOutOfBoundsException e){}
        }
    }

    private class BlockButtonListener extends MouseAdapter {

        private boolean firstClick = true;
        
        @Override
        public void mouseReleased(MouseEvent e){
            BlockButton button = (BlockButton)e.getSource();
            if(firstClick){
                firstClick = false;
                timer.start();
                grid.setBombs(button.x, button.y);
            }
            if(e.getButton()==MouseEvent.BUTTON1){
                if(!button.isChecked){
                    button.checkBlock();
                } else{
                    button.checkAdjacent();
                }
                checkWin();
            }
            else if(e.getButton()==MouseEvent.BUTTON3){
                button.changeFlag();
            }
        }
    }
}
