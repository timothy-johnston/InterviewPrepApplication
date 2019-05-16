
package com.tj.VirtualInterview;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.io.Writer;
//import java.io.FileWriter;


public class Main {  
    
    //--------------------------------------------------------------------------
    //Main program
    //--------------------------------------------------------------------------
    public static void main(String[] args) throws FileNotFoundException {
        
        //Here I declare reader for user input, questionsLibrary (instance of Qlibrary) for holding the arraylist of instances of Question
        //I also declare variabls holding the path to the questions and keywords file.
        Scanner reader = new Scanner(System.in);
        QLibrary questionsLibrary = new QLibrary();
        File questionsFile = new File("data\\questions.txt");
        File keywordsFile = new File("data\\keywords.txt");
        File anecdotesFile = new File("data\\anecdotes.txt");
        
        //Here I initialize the program. If user has no saved data, new files will be created to hold questions and keywords
        //If user already has saved data, files will be loaded and objects will be created from them
        initialize(questionsFile, keywordsFile, anecdotesFile, questionsLibrary);
        
        //Here the program shows the main menu and interacts with the user
        //User will enter commands that trigger relevant methods
        System.out.println("\n------------------------------\n| Welcome to interview prep! |\n------------------------------");
        while (true) {
            //Prompt for command
            System.out.println("\n----------------------------------------------------------------------------------");
            System.out.println("How would you like to proceed? Choose an option below.\n");
            System.out.println("1.) Begin - Type \"begin\" or \"1\" to begin practice.");
            System.out.println("2.) View - Type \"view\" or \"2\" to view your questions, keywords, and stories.");
            System.out.println("3.) Edit - Type \"edit\" or \"3\" to add or edit questions, keywords, and stories.");
            System.out.println("4.) Instructions - Type \"instructions\" or \"4\" to learn how to use this program.");
            System.out.println("5.) Quit - Type \"quit\" or \"5\" to add or exit the program.\n");
            
            //Get command
            String command = reader.nextLine();
            
            //Call a method based on user's command.
            if (commandEquivalency(command, "BEGIN", "1")) {
                questionsLibrary.askQuestions();
            } else if (commandEquivalency(command, "VIEW", "2")) {
                questionsLibrary.printQuestionsAndKeywords(reader, false);
            }
            else if (commandEquivalency(command, "EDIT", "3")) {
                questionsLibrary.editQuestionsAndKeywords();
            } else if (commandEquivalency(command, "INSTRUCTIONS", "4")) {
                displayInstructions();
            }
            else if (commandEquivalency(command, "QUIT", "5")) {
                break;
            } else {
                    System.out.println("Command not recognized. Please re-enter a command: ");
            }
    
        }
    }
    
    //--------------------------------------------------------------------------
    //Helper methods
    //--------------------------------------------------------------------------
    
    //initialize:  Initializes the program & user's data
    //Check if "questions.txt" exists. If so, load its data and data from keywords.txt into instances of Question stored in an arraylist in QLibrary
    //If not, create new instances of QLibrary (questions and keywords)
    public static void initialize(File questionsFile, File keywordsFile, File anecdotesFile, QLibrary questionsLibrary) {
         
        //If data already exists, load it. Otherwise, create blank files
        if (dataExists(questionsFile) && dataExists(keywordsFile) && dataExists(anecdotesFile)) {           
            //Reads in data. For each record of question, create an instance of Question
            loadData(questionsFile, keywordsFile, anecdotesFile, questionsLibrary);           
        } else {
            //Creates blank files
            initializeFiles();   
        }
    }
    
    
    //loadData: Loads existing data into the program
    //Called from initialize
    public static void loadData(File questionsFile, File keywordsFile, File anecdotesFile, QLibrary questionsLibrary){
        //arrayLists hold data read in from file
        ArrayList<String> questions = new ArrayList<String>();
        ArrayList<String> keywords = new ArrayList<String>();
        ArrayList<String> anecdotes = new ArrayList<String>();
        
        //Reads in questions from questions file, adds it to questions arraylist
        try
        {
            Scanner questionsReader = new Scanner(questionsFile);
                
            while (questionsReader.hasNextLine()) {
                String line = questionsReader.nextLine();
                questions.add(line);
            }
            
            questionsReader.close();
            
        }
        catch (FileNotFoundException ex)
        {
           System.out.println("Error:  No questions file found!");
        }
        
        //Reads in keywords from keywords file, adds it to keywords arraylist
        try
        {
            Scanner keywordsReader = new Scanner(keywordsFile);
            
            while (keywordsReader.hasNextLine()) {
                String line = keywordsReader.nextLine();
                keywords.add(line);
            }
            
            keywordsReader.close();
            
        }
        catch (FileNotFoundException ex)
        {
           System.out.println("Error:  No keywords file found!");
        }
        
        //Reads in anecdotes from keywords file, adds it to anecdotes arraylist
        try
        {
            Scanner anecdotesReader = new Scanner(anecdotesFile);
            
            while (anecdotesReader.hasNextLine()) {
                String line = anecdotesReader.nextLine();
                anecdotes.add(line);
            }
            
            anecdotesReader.close();
            
        }
        catch (FileNotFoundException ex)
        {
           System.out.println("Error:  No anecdotes file found!");
        }
        
        //Call addQuestionsFromFile to create Questions object from questions & keyword, adds it to arraylist in questionsLibray
        questionsLibrary.addQuestionsFromFile(questions, keywords, anecdotes);
        
    }
    
    //dataExists:  Checks to see if user has saved data already
    //      Note, 6-4-18: Should I also check for keywords & anecdotes files?
    public static Boolean dataExists(File questionsFile) {
        if (questionsFile.exists()) {
            return true;
        }
        return false;
    }
    
    
    //initializeFiles: If user doesn't have saved data already, this creates a blank questions file, keywords file, and anecdotes file for writing to.
    public static void initializeFiles() {
        //File paths (*** can maybe pass these paths to the function from main program to avoid repetition? check)
        File questionsFile = new File("data\\questions.txt");
        File keywordsFile = new File("data\\keywords.txt");
        File anecdotesFile = new File("data\\anecdotes.txt");
        
        //Create questions file
        try {
            if (questionsFile.createNewFile()) {
                System.out.println("new questions file created");
                
                //Populate the questions file with some default questions
                BufferedWriter writer = new BufferedWriter( new FileWriter(questionsFile));
                writer.write("Tell me about yourself.\n");
                writer.write("Tell me about a time where you contributed to a team achieving a milestone.\n");
                writer.write("Have you ever failed to accomplish an iportant task on time? How did you handle this?\n");
                writer.write("How do you handle project-related disagreements?\n");
                writer.close();
                
            } else {
                System.out.println("questions file already exists");
            }
        } catch (IOException ex) {
                System.out.println("Error in initializeFiles() - creation of new questions failed.");
        }
        
        //Create keywords file
        try {
            if (keywordsFile.createNewFile()) {
                System.out.println("new keywords file created");
                
                //Populate keywords file with some default keywords
                BufferedWriter writer = new BufferedWriter( new FileWriter(keywordsFile));
                writer.write("general, background\n");
                writer.write("success, teamwork\n");
                writer.write("failure, adversity\n");
                writer.write("communication, teamwork\n");
                writer.close();
                
            } else {
                System.out.println("keywords file arleady exists");
            }
        } catch (IOException ex) {
            System.out.println("Error in initializeFiles() - creation of new keywords file failed");
        
        }
        
        //Create anecdotess file
        try {
            if (anecdotesFile.createNewFile()) {
                System.out.println("new anecdotes file created");
            } else {
                System.out.println("anecdotes file arleady exists");
            }
        } catch (IOException ex) {
            System.out.println("Error in initializeFiles() - creation of new anecdotes file failed");
        
        }
    }
    
    //commandEquivalency:  Checks if a user command equals an expected target command.
    public static Boolean commandEquivalency(String command, String target1, String target2) {
        if (command.toUpperCase().equals(target1) || command.toUpperCase().equals(target2)) {
            return true;
        }
        return false;
    }
    
    //displayInstructions:  ....displays instructions.
    public static void displayInstructions() {
        System.out.println("\nBehavioral Interviews are hard! Use this tool to practice answering interview questions. Here's how to use this program:");
        System.out.println("To start, type \"begin\" at the main menu. The program will then ask you an interview question - answer this as you would if you were in a real interview. When finished, press Enter to be asked the next question.");
        System.out.println("You can customize the questions that you would like to be asked. At the main menu, type \"Edit\", and then choose whether to add or remove questions.\n");
    }

}
