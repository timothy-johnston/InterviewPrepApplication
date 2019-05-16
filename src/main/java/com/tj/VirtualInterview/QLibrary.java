package com.tj.VirtualInterview;

//The QLibrary class consists of an ArrayList holding instances of the Question class
//This class also contains most of the methods used to manipulate and print instances and components of instances of the Question class

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.*;

public class QLibrary {
 
    Scanner reader = new Scanner(System.in);
    private ArrayList<Question> questions;
    
    //Constructor. QLibrary is initialized with no arguments. An empty arraylist is initialized - this will hold instances of Question.
    public QLibrary() {
        this.questions = new ArrayList<Question>();
    } 
    
    //addQuestionsFromFile:  After data is read in from the questions and keywords and anecdotes files, this creates a new Question instance.
    //The next question instance is then added to the questions arrayList.
    //Called from: loadData (in Main) 
    public void addQuestionsFromFile(ArrayList text, ArrayList keywords, ArrayList anecdotes){
        //Loop over the text and keywords arraylists, creating a new question instance each time
        for (int i = 0; i < text.size(); i ++) {
        	
        	String questionText = (i < text.size() ? text.get(i).toString() : null);
        	String keywordText = (i < keywords.size() ? keywords.get(i).toString() : null);
        	String anecdoteText = (i < anecdotes.size() ? anecdotes.get(i).toString() : null);
        	
            Question question = new Question(questionText, keywordText, anecdoteText);
            
            //Add the new Question instance to the questions arraylist
            this.questions.add(question);
        }
    }
    
    //printQuestionsAndKeywords: Prints the entire list of questions along with their corresponding keywords.
    //Note the "if" in the middle and the calledFromEdit boolean argument
    //  That block will execute if calledFromEdit == true.
    //  This method can be called in two different contexts: 
    //      1.) The list is printed when a user selects "view" from the main menu. In this context, calledFromEdit == false, and we ask the user if they want to edit anything
    //      2.) User wants to EDIT list of questions. In this context, calledFromEdit == true, and the lis is being displayed so a user can select a question to edit.
    //          See method editQuestionsAndKeywords
    public void printQuestionsAndKeywords(Scanner reader, Boolean calledFromEdit) {
        System.out.println("\nYour current questions are:\n");
        for (int i = 0; i < this.questions.size(); i ++) {
            System.out.println((i + 1) + ": " + this.questions.get(i).getText());
            System.out.println("\tKeywords: " + this.questions.get(i).getKeywords());
            System.out.println("\tAnecdotes: " + this.questions.get(i).getAnecdotes());
        }
        
        //If this method was called from editQuestionsAndKeywords, prompt the user to choose if they want to add, remove, or edit questions.
        if (!calledFromEdit) {
            System.out.println("\nTo add, remove, or edit questions and keywords, type \"edit\".\nTo return to the main menu, just press Enter.\n");
        
            if (reader.nextLine().toUpperCase().equals("EDIT")) {
                editQuestionsAndKeywords();
            }
        }   
    }
    
    //editQuestionsAndKeywords: Receive another command from the user (add, remove, keyword, or return), and call the appropriate method
    public void editQuestionsAndKeywords() {
        String command;
     
        while (true) {
            System.out.println("\nType one of the following commands:\n"
                    + "1.) \"add\": To add a new question\n "
                    + "2.) \"remove\": To remove a question\n "
                    + "3.) \"keyword\" to edit a question's keywords\n"
                    + "4.) \"anecdote\" to edit a question's anecdotes\n "
                    + "5. \"return\" to return to the main menu.\n");
            command = reader.nextLine();
            
            if (command.toUpperCase().equals("ADD")) {
                addQuestion();
            } else if (command.toUpperCase().equals("REMOVE")) {
                removeQuestion();
            } else if (command.toUpperCase().equals("KEYWORD")) {
                editKeywords();
            } else if (command.toUpperCase().equals("ANECDOTE")) {
                editAnecdotes();
            } else if (command.toUpperCase().equals("RETURN")) {
                break;
            }
        }   
    }
    
    //addQuestion: Creates a new Question instance based on user input.
    //New Question instance is added to questions arraylist and written to file
    public void addQuestion() {
        System.out.println("Enter question text: ");
        String text = reader.nextLine();
        
        System.out.println("Enter keywords, seperated by commas (To skip, just press Enter): ");
        String keywords = reader.nextLine();
        
        System.out.println("Enter anecdotes, seperated by commas (To skip, just press Enter): ");
        String anecdotes = reader.nextLine(); 
        
        Question question = new Question(text, keywords, anecdotes);
        
        this.questions.add(question);
        
        writeQuestionToFile(text, keywords, anecdotes);
    }
    
    //removeQuestion: Promps a user to choose which question to remove.
    //Question is removed from questions arraylist and from file
    public void removeQuestion() {
        while (true) {
            System.out.println("\nYour current questions are:\n");
            printQuestionsAndKeywords(reader, true);
            System.out.println("\nType the number of the question to remove. When finished removing questions, type 0.");
            //int questionNumber = 99999;
            int questionNumber;
            
            while (true) {
                try {
                    questionNumber = Integer.parseInt(reader.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number: ");
                }
            }
                     
            if (questionNumber == 0) {
                break;
            } else if ( questionNumber > 0 && questionNumber <= this.questions.size() ) {
                System.out.println("");
                this.questions.remove(questionNumber - 1);
            }
            overwriteQuestionToFile();
        }
    }
    
    //editKeywords: Allows a user to change a question's keywords
    //NEEDS IMPLEMENTED
    public void editKeywords() {
        System.out.println("\nSorry, not implemented yet... Coming soon.\n");
    }
    
    public void editAnecdotes() {
        System.out.println("\nSorry, not implemented yet... Coming soon. \n");
    }
    
    //writeQuestionToFile: Appends a question's text and keywords to their respective files.
    public void writeQuestionToFile(String text, String keywords, String anecdotes) {
        
        //Append the passed in text string to the questions file.
        //FileWriter boolean argument true means file will be appended to rather than overwritten.
        try{
            FileWriter questionWriter = new FileWriter("data\\questions.txt",true);
            BufferedWriter out = new BufferedWriter(questionWriter);
            out.newLine();
            out.write(text);
            out.close();
        } catch (Exception e) {
            System.out.println("Error while appending to questions file");
        }
        
        //Append the passed in keyword string to the keywords file.
        //FileWriter boolean argument true means file will be appended to rather than overwritten.
        try{
            FileWriter keywordsWriter = new FileWriter("data\\keywords.txt",true);
            BufferedWriter out = new BufferedWriter(keywordsWriter);
            out.newLine();
            out.write(keywords);
            out.close();
        } catch (Exception e) {
            System.out.println("Error while appending to keywords file");
        }
        
        //Append the passed in anecdotes string to the anecdotes file.
        //FileWriter boolean argument true means file will be appended to rather than overwritten.
        try{
            FileWriter anecdotesWriter = new FileWriter("data\\anecdotes.txt",true);
            BufferedWriter out = new BufferedWriter(anecdotesWriter);
            out.newLine();
            out.write(anecdotes);
            out.close();
        } catch (Exception e) {
            System.out.println("Error while appending to anecdotes file");
        }
        
    }
    
    //overwriteQuestionToFile: This overwrites the existing questions and keywords files with the new questions and keywords AFTER a question is removed.
    //As of 5-20-18, not sure how to remove records from a file, so I instead just re-write the file after the Question instance is removed from the questions arraylist. 
    public void overwriteQuestionToFile() {
        
        //Write the questions file.
        try{
            PrintWriter questionWriter = new PrintWriter("data\\questions.txt","UTF-8");
            for (Question question : this.questions) {
                questionWriter.println(question.getText());
            }
            questionWriter.close();
        } catch (Exception e) {
            System.out.println("Error while writing new questions file after deleting a question.");
        }
        
        //Write the keywords file.
        try{
            PrintWriter keywordWriter = new PrintWriter("data\\keywords.txt","UTF-8");
            for (Question question : this.questions) {
                keywordWriter.println(question.getKeywords());
            }
            keywordWriter.close();
        } catch (Exception e) {
            System.out.println("Error while writing new keywords file after deleting a question.");
        }
        
        //Write the anecdotes file.
        try{
            PrintWriter keywordWriter = new PrintWriter("data\\anecdotes.txt","UTF-8");
            for (Question question : this.questions) {
                keywordWriter.println(question.getAnecdotes());
            }
            keywordWriter.close();
        } catch (Exception e) {
            System.out.println("Error while writing new keywords file after deleting a question.");
        }
    }
    
    //askQuestions: Show the user a question and time how long it takes them until they are ready for the next question.
    //Ask a question at random. Continue until all questions have been asked.
    //NOTE: This method is a bit complex - perhaps in the future it should be abstracted into several simpler methods.
    public void askQuestions() {
        //Determine order of questions to be asked.
        ArrayList<Integer> indexOrder = new ArrayList<Integer>();       //Hold the order of the questions to be asked in this arraylist 
        int randomTry;
        int i = 0;
        
        while (i < this.questions.size()) { 
            randomTry = ((int) Math.ceil(Math.random()*this.questions.size()-1));       //Get a random number between 0 and the last index of the questions arraylist

            if (!indexOrder.contains(randomTry)) {      //If that random number hasn't already been selected, store it in indexOrder
                indexOrder.add(randomTry);
                i++;
            }     
        }
        
        //Ask questions
        //This loop will continue until all questions have been asked.
        //Each iteration, the question storeed at the index number in the ith location of indexOrder will be asked
        String text;
        for (i = 0; i < this.questions.size(); i++) {
            text = this.questions.get(indexOrder.get(i)).getText();
            System.out.println("\nQuestion text: " + text);
            
            //Timer start
            long startTime = System.nanoTime();
            
            System.out.println(".\n.\n.\nPress Enter to continue to the next question.");
            reader.nextLine();
            
            //Timer end
            long endTime = System.nanoTime();
            System.out.println("You took " + ((endTime - startTime) / 1000000 / 1000) + " seconds to answer.");
            System.out.println("---------------------------------------------");
            
            //If at least one question remains to be asked, show a countdown.
            if (i < this.questions.size() - 1) {
                
                System.out.println("Next question will be asked in 5 seconds: ");

                int j = 5;
                while (j > 0) {
                    System.out.println(j + "...");

                    try {
                        // TimeUnit.SECONDS.sleep(1);
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("Error in delay during countdown.");
                    }

                    j--;
                }
            }
        }  
    }
    
    
}
