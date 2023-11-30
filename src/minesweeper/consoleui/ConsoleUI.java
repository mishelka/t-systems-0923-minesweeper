package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import minesweeper.core.Field;

/**
 * Console user interface.
 */
public class ConsoleUI {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
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

            //if hra vyrata, tak vypis vyhral si a ukonci hru
            //if hra prehrata, tak vypis prehral si a ukonci hru
        } while(true);
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
        String input = readLine();
        //"(O|M)(A)(62)", "MB25" "X"
        //zadefinovat regular ako Pattern
        //matcherom matchnut input:
        // matcher.matches(input);
        //ziskat suradnice, napr. cislo stlpca:
        //int col = Integer.parseInt(matcher.group(3));

        System.out.println("Pouzivatel napisal: " + input);

        if(input.startsWith("O")) {
            field.openTile(5, 5);
            //ukoncenie programu: System.exit(0);
        } else if (input.startsWith("M")) {
            field.markTile(1, 5);
        }
    }
}
