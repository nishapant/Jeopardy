/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

/**
 *
 * @author rcortez
 */
public class ProcessPlayers 
{
   private String name,button;
   private int score;
   
   //this constructor recieves all the information about one user so that it can change the point value of the user 
   public ProcessPlayers(String userName, int userScore, String userButton){
       name = userName;
       score = userScore;
       button = userButton;
   }
   
   //changes the points of the user
   public void changePoints(int pointValue){
       score += pointValue;
   }
   
   //returns the name of the user
   public String returnname(){
       return name;
   }
   
   //returns the points of the user
   public int returnPoints(){
       return score;
   }
}
