/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

import javax.swing.JOptionPane;

/**
 *
 * @author rcortez
 */
public class ProcessQuestion 
{
    private String question, answer, category;
    private boolean correct,dailyDouble;
    private int points, pointsWagered;
    
    //this constructor gets all of the information about a new question
    public ProcessQuestion(String newQuestion,String newAnswer,String categoryName, int newPoints){
        question = newQuestion;
        answer= newAnswer;
        category = categoryName;
        points = newPoints;
    }
    
    //returns the question
    public String returnQuestion(){
        return question;
    }
    
    //checks if the answer is correct or not. if the question is a daily double, 
    //the method uses the daily double value as the amount of points to add or subtract
    public int checkAnswer(String ans){
        if(ans.equalsIgnoreCase(answer)){
            correct = true;
            if(dailyDouble){
                return pointsWagered;
            }
            return points;
        }else{
            correct = false;
            if(dailyDouble){
                return (pointsWagered*-1);
            }
            return(points*-1);
        }
    }
    
    //returns if the player got the answer correct
    public boolean returnCorrect(){
        return correct;
    }
    
    //returns the answer to the question
    public String returnAnswer(){
        return answer;
    }
    
    //if the question is a daily double, the user gets to wager points and the daily double method is set to true
    //so the checkAnswer method knows that the question is a daily double.
    public void dailyDouble(int userPoints){
        pointsWagered = Integer.parseInt(JOptionPane.showInputDialog("How many points would you like to wager? It must be between 100 and 500 points."));
        if(pointsWagered > 500  || pointsWagered < 100){
            pointsWagered = Integer.parseInt(JOptionPane.showInputDialog("Please enter a valid amount of points."));
        }
        dailyDouble = true;
    }
   
}
