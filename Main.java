import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        printInstructions();
        String playerCoordinate;
        int[] playerCoordinateInt = new int[2];
        int missCounter = 0;

        while(true) {
            System.out.println("\n\nWould you like to start? (y/n): ");
            String start = scan.nextLine();
            if(start.equals("n")) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
            generateShips();
            while (missCounter < 20) {
                System.out.println("Ships left:");
                checkSink();
                printBoard();
                System.out.println("Misses: " + missCounter);
                boolean invalidInput;
                do {
                    try {
                        System.out.println("\nEnter a coordinate (e.g. 1,a): ");
                        playerCoordinate = scan.nextLine();
                        playerCoordinateInt[0] = ((int) (playerCoordinate.split(",")[1].charAt(0))) - 97;
                        playerCoordinateInt[1] = Integer.parseInt((playerCoordinate.split(","))[0]) - 1;
                        if (board[coordinateToBoardNumber(playerCoordinateInt)] == '#' ||
                                Character.isLetter(board[coordinateToBoardNumber(playerCoordinateInt)]) ||
                                board[coordinateToBoardNumber(playerCoordinateInt)] == '@') {
                            System.out.println("Coordinate already marked\n" +
                                                "Please pick another");
                            invalidInput = true;
                            continue;
                        }
                        invalidInput = false;

                    } catch (Exception e) {
                        System.out.println("\nInvalid input\n Please try again");
                        invalidInput = true;
                    }
                } while (invalidInput);
                System.out.println();
                if (Character.isDigit(board[coordinateToBoardNumber(playerCoordinateInt)])) {
                    board[coordinateToBoardNumber(playerCoordinateInt)]
                            = (char) ((int)board[coordinateToBoardNumber(playerCoordinateInt)]+48);
                    System.out.println("Hit!");
                }
                else {
                    board[coordinateToBoardNumber(playerCoordinateInt)] = '#';
                    System.out.println("Miss!");
                    missCounter++;
                }
                if (missCounter >= 20) {
                    System.out.println("You Lose!");
                }
                if (checkWin()) {
                    System.out.println("You Win!");
                    break;
                }
            }
        }
    }

    public static char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
                                    ' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};

    public static void printBoard() {
        String printedBoard = " ";
        for (int j = 1; j <= 10; j++) {
            printedBoard += " " + j + "  ";
        }
        printedBoard += "\n";
        for (int i = 0; i < board.length; i++) {
            if ((i+1) % 10 == 1) {
                printedBoard += (char) (((int) i/10)+97);
            }
            if (Character.isLetter(board[i])) {
                printedBoard += " " + "$" + " ";
            }
            else if (board[i] != '@' && board[i] != '#') {
                printedBoard += " " + " " + " ";
            }
            else {
                printedBoard += " " + board[i] + " ";
            }
            if (((i+1) % 10 == 0) && i != 0 && i != 99) {
                printedBoard += "\n ----------------------------------------\n";
            }
            else if (i == 99) {
                printedBoard += "";
            }
            else {
                printedBoard += "|";
            }
        }
        System.out.println(printedBoard);
    }
    public static int coordinateToBoardNumber(int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];
        String stringCoordinate = String.valueOf(x)+String.valueOf(y);
        return Integer.parseInt(stringCoordinate);
    }
    public static boolean checkLeft(int boardNumber, int shipSize) {
        for (int i = 1; i <= shipSize; i++) {
            if (boardNumber-i < 0 || ((int) boardNumber/10) != (int) (boardNumber-i)/10) {
                return false;
            }
            if (board[(boardNumber-i)] != ' ') {
                return false;
            }
        }
        return true;
    }
    public static boolean checkRight(int boardNumber, int shipSize) {
        for (int i = 1; i <= shipSize; i++) {
            if (boardNumber+i > 99 || ((int) boardNumber/10) != (int) (boardNumber+i)/10) {
                return false;
            }
            if (board[(boardNumber+i)] != ' ') {
                return false;
            }
        }
        return true;
    }
    public static boolean checkUp(int boardNumber, int shipSize) {
        for (int i = 1; i <= shipSize; i++) {
            if (boardNumber-(i*10) < 0) {
                return false;
            }
            if (board[boardNumber-(i*10)] != ' ') {
                return false;
            }
        }
        return true;
    }
    public static boolean checkDown(int boardNumber, int shipSize) {
        for (int i = 1; i <= shipSize; i++) {
            if (boardNumber+(i*10) > 99) {
                return false;
            }
            if (board[boardNumber+(i*10)] != ' ') {
                return false;
            }
        }
        return true;
    }
    public static void generateShips() {
        int[] shipSizes = {2,3,3,4,5};
        Random rand = new Random();
        for (int j = 0; j < shipSizes.length; j++) {
            while (true) {
                int boardNumber = rand.nextInt(0, 99);
                if (board[boardNumber] == ' ') {
                    int n = rand.nextInt(1,5);
                    if (n == 1) {
                        if (checkLeft(boardNumber, shipSizes[j])){
                            for (int i = 0; i < shipSizes[j]; i++) {
                                board[boardNumber-i] = (char) (j+1+48);
                            }
                            break;
                        }
                    }
                    else if (n == 2) {
                        if (checkRight(boardNumber, shipSizes[j])){
                            for (int i = 0; i < shipSizes[j]; i++) {
                                board[boardNumber+i] = (char) (j+1+48);
                            }
                            break;
                        }
                    }
                    else if (n == 3) {
                        if (checkUp(boardNumber, shipSizes[j])){
                            for (int i = 0; i < shipSizes[j]; i++) {
                                board[boardNumber-(i*10)] = (char) (j+1+48);
                            }
                            break;
                        }
                    }
                    else {
                        if (checkDown(boardNumber, shipSizes[j])){
                            for (int i = 0; i < shipSizes[j]; i++) {
                                board[boardNumber+(i*10)] = (char) (j+1+48);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    public static void printInstructions() {
        System.out.println("Welcome to Battleships\n\n" +
                "Rules/Instructions:\n" +
                "Input coordinates to guess the location of enemy battleships in the format number,letter\n" +
                "$ indicates a hit\n" +
                "# indicates a miss\n" +
                "@ indicates a sink" +
                "There will be 5 ships each with a different size:\n" +
                "11\n222\n333\n4444\n55555\n" +
                "Each ship is hidden on the board and placed randomly (either horizontally or vertically)\n" +
                "Enter coordinates to send a missile to strike the coordinate\n" +
                "Find all 5 ships in less than 20 missed to win");
    }
    public static boolean checkWin() {
        for (char c : board) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public static boolean arrayContains(char[] array, char c) {
        for (char a : array) {
            if (a == c) {
                return false;
            }
        }
        return true;
    }
    public static void replaceAll(char[] array, char x, char y) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                array[i] = y;
            }
        }
    }
    public static void checkSink() {
        if (arrayContains(board, '1')) {
            replaceAll(board, 'a', '@');
            System.out.println("@@");
        }
        else {
            System.out.println("11");
        }
        if (arrayContains(board, '2')) {
            replaceAll(board, 'b', '@');
            System.out.println("@@@");
        }
        else {
            System.out.println("222");
        }
        if (arrayContains(board, '3')) {
            replaceAll(board, 'c', '@');
            System.out.println("@@@");
        }
        else {
            System.out.println("333");
        }
        if (arrayContains(board, '4')) {
            replaceAll(board, 'd', '@');
            System.out.println("@@@@");
        }
        else {
            System.out.println("4444");
        }
        if (arrayContains(board, '5')) {
            replaceAll(board, 'e', '@');
            System.out.println("@@@@@");
        }
        else {
            System.out.println("55555");
        }
    }
}