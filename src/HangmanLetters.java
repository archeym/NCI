/**
 * @NCI x15029042
 * @author Arkadijs Makarenko
 * Hangman project 11/04/16
 * instantiable class HangmanLetetrs, check if letter is used
 */

import java.util.Arrays;

public class HangmanLetters {
    private String usedLetters = "";
    
    public boolean letterIsUsed(char letter){//http://stackoverflow.com/questions/13981779/class-error-cannot-be-identified
        for (char i = 0; i < usedLetters.length(); i++){
            if (usedLetters.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }
    
    public void addNewLetter(char currentLetter){
        usedLetters = usedLetters + currentLetter;//add current letter to the String of usedLetters
        char[] usedLettersArray = usedLetters.toCharArray();//convert usedLetters to char Array
        Arrays.sort(usedLettersArray);//sorting
        usedLetters = String.valueOf(usedLettersArray);//convert char Array back to String
    }
    
    public void display(){
        for (char i = 0; i < usedLetters.length(); i++){
            System.out.print(usedLetters.charAt(i) + " ");
        }
    }
    
    public int getLength(){
        return usedLetters.length();
    }
}
