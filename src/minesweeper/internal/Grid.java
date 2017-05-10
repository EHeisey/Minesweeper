package minesweeper.internal;

/**
 * Contains all the blocks and sets up the bombs and numbers associated with each.
 * @author EliHeisey
 */
class Grid {

    private Block[][] grid;
    private int xSize, ySize;
    private int bombs;

    Grid(int level){
        switch(level){
            case 1:
                xSize = ySize = 9;
                bombs = 10;
                break;
            case 2:
                xSize = ySize = 16;
                bombs = 40;
                break;
            case 3:
                ySize = 16;
                xSize = 30;
                bombs = 99;
                break;
            default:
                throw new IllegalArgumentException();
        }
        grid = new Block[ySize][xSize];
        for(int i = 0; i < ySize; i++){
            for(int j = 0; j < xSize; j++){
                grid[i][j] = new Block();
            }
        }
    }

    /**
     * 
     * @param x
     * @param y 
     */
    void setBombs(int x, int y){
        int placed = 0;
        while(placed < bombs){
            int i,j;
            do{
                i = (int)(Math.random()*xSize);
                j = (int)(Math.random()*ySize);
            } while(i == x || j ==y);
            if(grid[j][i].placeBomb()) placed++;
        }
        setAdjacentBombs();
    }

    /**
     * Sets the number of adjacent bombs to a block.
     */
    private void setAdjacentBombs(){
        for(int i = 0; i < ySize; i++){
            for(int j = 0; j < xSize; j++){
                int numBombs = 0;
                try{
                    if(grid[i - 1][j - 1].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i - 1][j].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i - 1][j + 1].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i][j - 1].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i][j + 1].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i + 1][j - 1].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i + 1][j].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                try{
                    if(grid[i + 1][j + 1].isBomb()) numBombs++;
                } catch(IndexOutOfBoundsException e){}
                grid[i][j].setAdjacentBombs(numBombs);
            }
        }
    }

    int getXSize(){ return xSize;}

    int getYSize(){ return ySize;}

    Block getBlock(int y, int x){ return grid[y][x];}
    
    int bombs(){ return bombs;}
}
