/**
 * @NCI x15029042
 * @author Arkadijs Makarenko
 * Hangman project 11/04/16
 *  1.Generate random word from String[] words;
    2.Convert word (replace each symbol to *); 
    3.Show game status to user;
    4.User enter letter try to guess the word. Looping if letter has been used;
    5.Compare letter provided by user if letters is in the current word; 
    6.If user guess letter then replace star * in word to show, otherwise decrement -1 lives;
    7.Check if all letters are guess and set completed to true;
    8.Loop working until lives >0 and word not completed;
    9.Show user result of the game ( lost, won ) and update games statistics;
    10.Ask user to continue a game or end game;
    11.If user press Y then continue loop;
    12.If not display game statistics and finish program.
 */
import java.util.Scanner;

public class HangmanDemo {
    public static void main(String[] args) {  
        //variables
        String currentWord;
        String wordToShow;
        String[] words = {"Boston Celtics", "Brooklyn Nets", "New York Knicks", "Philadelphia 76ers", "Toronto Raptors","Golden State Warriors","Los Angeles Clippers", "Los Angeles Lakers", "Phoenix Suns", "Sacramento Kings",
                         "Chicago Bulls", "Cleveland Cavaliers", "Detroit Pistons", "Indiana Pacers", "Milwaukee Bucks", "Dallas Mavericks", "Houston Rockets", "Memphis Grizzlies", "New Orleans Pelicans",
                         "San Antonio Spurs", "Atlanta Hawks", "Charlotte Hornets", "Miami Heat", "Orlando Magic", "Washington Wizards", "Denver Nuggets", "Minnesota Timberwolves","Oklahoma City Thunder", "Portland Trail Blazers", "Utah Jazz"};
        
        int lives;
        boolean completed;
        boolean letterMatched;
        char currentLetter;
        char playAgain;
        
        HangmanStats stats = new HangmanStats(); //initialized game stats
        System.out.println("Hello, welcome to my Hangman project NBA basketball teams game!");

        do{
            Scanner scan = new Scanner(System.in);
            System.out.println("Press ENTER to START the game");
            scan.nextLine();// http://stackoverflow.com/questions/26184409/java-console-prompt-for-enter-input-before-moving-on
            //#1 Generate random word from String[] words
            currentWord = words[(char) (Math.random() * words.length)];//Pick a random word 

            wordToShow = convertWord(currentWord);//#2 Convert word (replace each symbol to *); 
            lives = 8;
            completed = false;
            HangmanLetters usedLetters = new HangmanLetters();//initialized empty usedLetters

            //#8 Loop working until lives >0 and word is not completed;
            while (lives > 0 && !completed){
                //the number of lives
                //#3 Show game status to user;
                System.out.println("\n"+ "Lives:" + lives);
                //#3 Show game status to user;
                //Show the currentWord with the known letters
                System.out.println("Word: " + wordToShow);
                 //#3 Show game status to user;   
                //Show used letters
                System.out.print("Leters used: ");

                if (usedLetters.getLength() == 0){
                    System.out.println("Waiting for your first letter!");
                } else{
                    usedLetters.display();
                    System.out.println();
                }
                //ask a letter and decrement -1 lives if letter is not in the word, otherwise change the proper wordToShow
                currentLetter = ' ';//#4 User enter letter try to guess the word. Looping if letter has been used;
                boolean letterUsed = false;  //http://stackoverflow.com/questions/13981779/class-error-cannot-be-identified
                do {
                    if (letterUsed){
                        System.out.println("Letter is alredy used: "+ currentLetter);
                    }
                    System.out.print("Guess a letter: ");
                    currentLetter = scan.next().charAt(0);//http://stackoverflow.com/questions/21147319/how-to-convert-input-char-to-uppercase-automatically-in-java
                    currentLetter = (char) Character.toUpperCase((char) currentLetter);
                    letterUsed = usedLetters.letterIsUsed (currentLetter);//checking if letter is already used
                } while (letterUsed || currentLetter < 'A' || currentLetter > 'Z');

                letterMatched = false;
                //#5 Compare letter provided by user if letters is in the current word; 
                char[] showChars = wordToShow.toCharArray();//convert word to char Array
                for (char i = 0; i < showChars.length; i++){//i is a *
                    if (Character.toUpperCase(currentWord.charAt(i)) == currentLetter) { //compare letter with char in currentWord
                        showChars[i] = currentLetter;
                        letterMatched = true;
                    }
                }
                //#6 If user guess letter, then replace star * in word to show, otherwise decrement -1 lives;
                wordToShow = String.valueOf(showChars);//convert char Array back to String
                
                if (!letterMatched){
                    lives--;
                }
                //Add currentLetter to usedLetters
                usedLetters.addNewLetter(currentLetter);
                //Check whether user has won or not
                //#7 Check if all letters are guess and set completed to true;
                completed = true;
                for (char i = 0; i < wordToShow.length(); i++) {
                    if (wordToShow.charAt(i) == '*'){
                        completed = false;
                    }
                }
            }           
            //#9 Show user result of the game (lost, won) and update games statistics;
            if (lives == 0){
                System.out.println(0 + " You lost, sorry my friend :)");
                System.out.println("You couldn't find the word " + currentWord.toUpperCase());
                stats.incrementGamesLost();
            } else{//User won the game (completed = true)
                System.out.println("You WON!\nYou found the word: " + wordToShow + "\nWith " + (lives - 1) + " more live(s) left ");
                stats.incrementGamesWon();
            }
            // Ask if user wants to play again
            //#10 Ask user to continue a game or end game;
            do{
                System.out.print("Are you ready for another game (y/n): ");
                playAgain = scan.next().charAt(0);
            } while (!"YN".contains(String.valueOf(playAgain).toUpperCase()));

            //http://stackoverflow.com/questions/16815279/difference-between-casting-to-string-and-string-valueof
        } while (!"N".contains(String.valueOf(playAgain).toUpperCase()));//#11 If user press Y then continue loop;
        //end game + show the game stats
        //#12 If not display game statistics and finish program.
        System.out.println("Game Statistics: ");
        System.out.println("Game played: " + stats.getGamesTotal());
        System.out.println("Game won: "+ stats.getGamesWon());
        System.out.println("Game lost: "+ stats.getGamesLost());
        System.out.println("Have a nice day!");
    }
    //method to convert each symbol to char using StringBuilder 
    public static String convertWord(String word) {
        StringBuilder result = new StringBuilder(); //https://docs.oracle.com/javase/tutorial/java/data/buffers.html      
        for (int i = 0; i < word.length(); i++){//each symbol in curentWord converted to char
            if (word.charAt(i) == ' ') {
                result.append(' ');
            } else {
                result.append('*');//add
            }
        }
        return result.toString();
    }
}