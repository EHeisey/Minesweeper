package minesweeper.internal;

/**
 * A representation of a minesweeper block, that may or may not contain a bomb.
 * @author EliHeisey
 */
class Block {

    private boolean flagged;
    private boolean isBomb;
    private int adjacentBombs;

    /**
     * Creates a new block with no bomb set, or number of adjacent bombs set.
     * Flagged Status is set to false.
     */
    Block(){
        flagged = false;
    }

    /**
     * Sets this block as a bomb. Returns false if this block is already a bomb.
     * This method is only used by the grid while setting up the game.
     *
     * @return true if set as a bomb, false if already a bomb
     */
    boolean placeBomb(){
        if(!isBomb) return (isBomb = true);
        return false;
    }

    /**
     * Sets the number of bombs that are adjacent to this block. This method is
     * only used by the grid while setting up the game.
     *
     * @param bombs the number of adjacent bombs
     */
    void setAdjacentBombs(int bombs){
        adjacentBombs = bombs;
    }

    /**
     * Returns the number of bombs that are adjacent to this block.
     *
     * @return number of adjacent bombs
     */
    int adjacentBombs(){
        return adjacentBombs;
    }

    /**
     * Changes whether this block is flagged or not.
     *
     * @return the block's new status of being flagged
     */
    boolean changeFlag(){
        return (flagged = !flagged);
    }

    /**
     * Returns whether this block is currently flagged
     *
     * @return if the block is flagged
     */
    boolean isFlagged(){
        return flagged;
    }

    /**
     * Returns true if this block contains a bomb.
     * @return whether or not this block is a bomb
     */
    boolean isBomb(){
        return isBomb;
    }
}
