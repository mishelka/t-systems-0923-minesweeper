package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.core.Field;
import minesweeper.core.GameState;

/**
 * Console user interface.
 */
public class ConsoleUI {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    Pattern MINES_INPUT_PATTERN = Pattern.compile("(O|M)([A-Z])([1-9]+)");
    
    /**
     * Reads line of text from the reader.
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Starts the game.
     * @param field field of mines and clues
     */
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();

            if(field.getState() == GameState.SOLVED) {
                System.out.println(field.toString());
                System.out.println("Vyhral si!");
            }
            if(field.getState() == GameState.FAILED) {
                System.out.println(field.toString());
                System.out.println("Prehral si!");
            }

            //dobrovolne: zacatie novej hry v pripade, ze pouzivatel prehral alebo vyhral
        } while(field.getState() == GameState.PLAYING);
    }
    
    /**
     * Updates user interface - prints the field.
     */
    public void update() {
        System.out.println(field);
    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("Please enter your selection (X) EXIT, (MA1) MARK, (OB4) OPEN: ");
        String input = readLine().trim().toUpperCase(); //trim oseka medzery na zaciatku a konci retazca; toUpperCase zabezpeci, ze vstup sa bude dat zadavat aj malymi pismenami

        switch(input.charAt(0)) {
            case 'X':
                System.out.println("Dovidenia");
                System.exit(0);
                break;
            case 'O': case 'M':
                //try {
                    handleInput(input);
                //} catch (WrongFormatException e) {
                    //vypiseme chyby cez System.out.err
                //}
                break;
            default:
                System.out.println("Nesprávny vstup.");
        }
    }

    private void handleInput(String playerInput) { //throws WrongFormatException {
        Matcher m = MINES_INPUT_PATTERN.matcher(playerInput);
        if(!m.matches()) {
            //throw new ...
        }

        int row = m.group(2).charAt(0) - 65;
        int col = Integer.parseInt(m.group(3)) - 1;
        char operation = m.group(1).charAt(0); //O alebo M

        if(!isInputInBorderOfField(row, col)) {
            //throw new ...
        }

        doOperation(operation, row, col);
    }

    private void doOperation(char operation, int row, int col) {
        if (operation == 'M') {
            field.markTile(row, col);
        }

        if (operation == 'O') {
            field.openTile(row, col);
        }
    }

    private boolean isInputInBorderOfField(int row, int col) {
        //kontroluje, ci nevybiehame z pola, vracia true alebo false podla toho, ci ano alebo nie
        return true;
    }
}
