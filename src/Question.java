//Each question to ask a user will be stored as an instance of this Question class
//A question object consists of the question text (i.e., "Tell me about yourself"), and keywords to categorize the question (i.e., leadership)

public class Question {
    
    private String text;
    private String keywords;
    private String anecdotes;

    //Constructor - a question is initialized with strings for its text and keywords
    public Question(String text, String keywords, String anecdotes) {
        this.text = text;
        this.keywords = keywords;
        this.anecdotes = anecdotes;
    }
    
    //Return the questions text
    public String getText() {
        return this.text;
    }
    
    //Return the question's keywords
    public String getKeywords() {
        return this.keywords;
    }
    
    //Return the question's anecdotes
    public String getAnecdotes() {
        return this.anecdotes;
    }
       
}
