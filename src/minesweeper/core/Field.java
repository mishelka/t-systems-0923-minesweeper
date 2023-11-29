package minesweeper.core;

import java.util.Formatter;
import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

//        //generate the field content
        generate();

//        printField();
//        markTile(4, 4);
//        printField();
//        openTile(5, 7);
//        openTile(5, 8);
//        openTile(4, 7);
//        openTile(6, 7);
//        openTile(5, 6);
//        printField();
//        markTile(4, 4);
//        printField();
    }

    public String toString() {
        Formatter f = new Formatter(); //ma v sebe StringBuilder
        //[     1   2   3   4   5 \n 1   -   -   -   -   -   - ....]

        f.format("%3s", ""); //pri StringBuilder je to append, pri Formatter sa to vola format
        for (int c = 0; c < columnCount; c++) {
            f.format("%3s", c);
        }
        f.format("\n");
        for (int r = 0; r < rowCount; r++) {
            f.format("%3s", (char)(r+65));
            for (int c = 0; c < columnCount; c++) {
                f.format("%3s", tiles[r][c]);
            }
            f.format("\n");
        }

        return f.toString();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        Tile t = tiles[row][column];
        /*
        V pripade, ze je to stav OPEN, neurobi sa nic.
        Podla diagramu prechodu stavov sa z tohto stavu uz nikam nema ist.
         */
        if(t.getState() == Tile.State.CLOSED) {
            t.setState(Tile.State.MARKED);
        } else if(t.getState() == Tile.State.MARKED) {
            t.setState(Tile.State.CLOSED);
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        generateMines();
        generateClues();
    }

    private void generateMines() {
        Random random = new Random();
        int m = 0;

        while (m < mineCount) {
            int r = random.nextInt(rowCount);
            int c = random.nextInt(columnCount);
            if (tiles[r][c] == null) {
                tiles[r][c] = new Mine();
                m++;
            }
        }
    }

    private void generateClues() {
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (tiles[r][c] == null) {
                    tiles[r][c] = new Clue(countAdjacentMines(r,c));
                }
            }
        }
    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        return ((columnCount * rowCount) - getNumberOf(Tile.State.OPEN)) == mineCount;
    }

    private int getNumberOf(Tile.State state) {
        int count = 0;

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if(tiles[r][c].getState() == state) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }
}
