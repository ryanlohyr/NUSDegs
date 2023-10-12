package seedu.duke.controllers;
import seedu.duke.views.CommandLineView;

import java.util.Scanner;

public class ModulePlannerController {
    private CommandLineView view;
    public ModulePlannerController(){
        this.view = new CommandLineView();
    }
    public void start(){
        view.displayWelcome();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        while(!userInput.equals("Bye")){

            String[] words = userInput.split(" ");

            String initialWord = words[0];

            switch(initialWord){
            case "hi":{
                view.displayMessage("can put the commands here");
                break;
            }
            case "hello":{
                view.displayMessage("yup");
                break;
            }
            default:{
                view.displayMessage("Invalid Input");
                break;
            }

            }
            userInput = in.nextLine();
        }
    }
}
