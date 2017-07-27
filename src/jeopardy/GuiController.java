/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeopardy;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author rcortez
 */
public class GuiController implements Initializable {
    
    private boolean canEnter = false;
    private boolean canEnter2 = false;
    private boolean canBuzz1=false;
    private boolean canBuzz2=false;
    private boolean canBuzz3=false;
    private int pturn;
    private int playerControl=0;
    private int questionNumber;
    private int dailyDouble;
    private String ans1;
    ArrayList<ProcessPlayers> players = new ArrayList<>();
    ArrayList<ProcessQuestion> questions = new ArrayList<>();
    
    JButton C1L1Button = new JButton("C1L1");
    JButton C1L2Button = new JButton("C1L2");
    
    
    @FXML
    private Label lblName1, lblName2, lblName3, lblScore1, lblScore2, lblScore3, categoryLabel1, categoryLabel2, categoryLabel3, 
            categoryLabel4,categoryLabel5;
    
    @FXML
    private Label lblQuestion, lblMessages;

    @FXML
    private TextField txt1,txt2,txt3,txt4;
    
    @FXML
    private GridPane gpane,gpane2,gpane3;
    
    //handles what happens when the enter button is pressed for buttons
    @FXML
    private void handleAnswerSubmit(){
        gpane3.setVisible(false);
        gpane.setVisible(true);
        ans1= txt4.getText();
        getAnswer(questionNumber,pturn,ans1); 
    }   
    
    //starts the game by adding all of the questions and finding the daily double question
    @FXML
    private void handleStart() 
    {
        dailyDouble = (int)(Math.random()*24+1);
        canEnter = true;
        gpane.setVisible(false);
        gpane2.setVisible(true);
        
        //questions
        questions.add(new ProcessQuestion("What is 2+2?","4", "math",100));
        questions.add(new ProcessQuestion("What is 2*4?","8", "math",200));
        questions.add(new ProcessQuestion("What is the square root of 9?","3", "math",300));
        questions.add(new ProcessQuestion("What is 5 squared","25", "math",400));
        questions.add(new ProcessQuestion("What is the log of 10","1", "math",500));
        
        questions.add(new ProcessQuestion("What is the largest planet on the solar system?","jupiter", "science",100));
        questions.add(new ProcessQuestion("What is the fastest animal in the world?","cheetah", "science",200));
        questions.add(new ProcessQuestion("What is the name of the supercontinent that existed on Earth?","pangaea", "science",300));
        questions.add(new ProcessQuestion("How many bones are in a shark's body?","0", "science",400));
        questions.add(new ProcessQuestion("What is the largest animal in the world?","blue whale", "science",500)); 
        
        questions.add(new ProcessQuestion("Who was the lead singer of the Jackson 5?","michael jackson", "music",100));
        questions.add(new ProcessQuestion("What is the name of the West Coast rapper who was killed at age 25?","tupac", "music",200));
        questions.add(new ProcessQuestion("Who released the song \"Superstition\"?","stevie wonder", "music",300));
        questions.add(new ProcessQuestion("Name the \"King of Rock and Roll\"", "elvis presley", "music",400));
        questions.add(new ProcessQuestion("Who is the Colombian singer known for the song \"Hips Don't Lie\"?","shakira", "music",500));
        
        questions.add(new ProcessQuestion("What is the capital of Illinois?","springfield", "geography",100));
        questions.add(new ProcessQuestion("Name of the small country next to India where Buddha was born","nepal", "geography",200));
        questions.add(new ProcessQuestion("What is the name of the smallest continent?","australia", "geography",300));
        questions.add(new ProcessQuestion("Which continent does the Nile river run through?", "africa", "geography",400));
        questions.add(new ProcessQuestion("In which country is the Boreal forest located?","canada", "geography",500));
        
        questions.add(new ProcessQuestion("How many U.S. states border the Gulf of Mexico?","5", "misc",100));
        questions.add(new ProcessQuestion("What continent is cut into two fairly equal halves by the Tropic of Capricorn?","australia", "misc",200));
        questions.add(new ProcessQuestion("What continent has the fewest flowering plants?","antarctica", "misc",300));
        questions.add(new ProcessQuestion("How many days does a cat usually stay in heat?","5", "misc",400));
        questions.add(new ProcessQuestion("What was the first planet to be discovered using the telescope, in 1781?","uranus", "misc",500));
    }

    //when the enter key is pressed, this function gets all of the names of the users and creates a player for all of them
    @FXML
    private void handleEnter(){
        canEnter = false;
        lblName1.setText(txt1.getText());
        lblName2.setText(txt2.getText());
        lblName3.setText(txt3.getText());
        lblScore1.setText("0");
        lblScore2.setText("0");
        lblScore3.setText("0");
        players.add(new ProcessPlayers(txt1.getText(),0,"A"));
        players.add(new ProcessPlayers(txt2.getText(),0,"G"));
        players.add(new ProcessPlayers(txt3.getText(),0,"L"));
        lblMessages.setText(players.get(0).returnname() +" wil use A to buzz in. " + players.get(1).returnname() + " will use G to "
                + "buzz in. " + players.get(2).returnname() + " will use L to buzz in. "+ players.get(0).returnname() +" has control.");
        gpane2.setVisible(false);
        gpane.setVisible(true);
    }
    
    //this method checks which player buzzed in first
    private void setTurnLabel()
    {
        switch (pturn) {
            case 0:
                lblMessages.setText(players.get(0).returnname() + " buzzed in first");
                break;
            case 1:
                lblMessages.setText(players.get(1).returnname() + " buzzed in first");
                break;
            case 2:
                lblMessages.setText(players.get(2).returnname() + " buzzed in first");
                break;
            default:
                break;
        }
    }
    
    //this method checks if the user is correct or not. if the user is not correct, the other players are allowed to buzz in. if
    //everyone gets the question wrong, the answer is written out and no one gets the points.
    private void getAnswer(int questionIndex, int playerIndex, String answer){
        players.get(playerIndex).changePoints(questions.get(questionIndex).checkAnswer(answer));
        switch(playerIndex){
            case 0:
                lblScore1.setText(Integer.toString(players.get(playerIndex).returnPoints()));
                break;
            case 1:
                lblScore2.setText(Integer.toString(players.get(playerIndex).returnPoints()));
                break;
            default: 
                lblScore3.setText(Integer.toString(players.get(playerIndex).returnPoints()));
                break;
        }
        if(questions.get(questionIndex).returnCorrect()){
            lblMessages.setText("You are correct. " + players.get(playerIndex).returnname()+ " has control");
            playerControl = playerIndex;
            canBuzz1=false;
            canBuzz2=false;
            canBuzz3=false;
        }else{
            lblMessages.setText("You are incorrect. The other players may buzz in.");
            if(playerIndex ==0){
                canBuzz1 = false;
            }
            if(playerIndex == 1){
                canBuzz2 = false;
            }
            if(playerIndex ==2){
                canBuzz3 = false;
            }   
            if(canBuzz1==false && canBuzz2==false && canBuzz3==false){
                lblMessages.setText("All of you guessed incorrect. The correct answer was " + questions.get(questionIndex).returnAnswer() +
                        ". " + players.get(playerIndex).returnname()+ " has control");
            }
        }

        txt4.setText("");
    }
    
    //if the user chose the daily double question, they are the only user that can buzz into the question and 
    //get to wager up to 500 points that they can either win or lose depeding if they get the answer right or not.
    private void checkDailyDouble(int questionIndex, int playerIndex){
        if(questionIndex == dailyDouble){
            lblMessages.setText("You got the daily double! You are the only player that is allowed to buzz in at this time.");
            if(playerIndex == 0){
                canBuzz2=false;
                canBuzz3=false;
            }
            if(playerIndex ==1){
                canBuzz1=false;
                canBuzz3=false;
            }
            if(playerIndex == 2){
                canBuzz1=false;
                canBuzz2=false;
            }
            questions.get(questionIndex).dailyDouble(players.get(playerIndex).returnPoints());
        }
    }
    
    //if the user wants to reset the game, all the buttons are revisible and new players are added
    //to the players array
    @FXML
    private void handleReset() 
    {
        lblQuestion.setText("");
        gpane.setVisible(false);
        canEnter = true;
        players.clear();
        gpane.getChildren().get(0).setVisible(true);
        gpane.getChildren().get(1).setVisible(true);
        gpane.getChildren().get(2).setVisible(true);
        gpane.getChildren().get(3).setVisible(true);
        gpane.getChildren().get(4).setVisible(true);
        gpane.getChildren().get(5).setVisible(true);
        gpane.getChildren().get(6).setVisible(true);
        gpane.getChildren().get(7).setVisible(true);
        gpane.getChildren().get(8).setVisible(true);
        gpane.getChildren().get(9).setVisible(true);
        gpane.getChildren().get(10).setVisible(true);
        gpane.getChildren().get(11).setVisible(true);
        gpane.getChildren().get(12).setVisible(true);
        gpane.getChildren().get(13).setVisible(true);
        gpane.getChildren().get(14).setVisible(true);
        gpane.getChildren().get(15).setVisible(true);
        gpane.getChildren().get(16).setVisible(true);
        gpane.getChildren().get(17).setVisible(true);
        gpane.getChildren().get(18).setVisible(true);
        gpane.getChildren().get(19).setVisible(true);
        gpane.getChildren().get(20).setVisible(true);
        gpane.getChildren().get(21).setVisible(true);
        gpane.getChildren().get(22).setVisible(true);
        gpane.getChildren().get(23).setVisible(true);
        gpane.getChildren().get(29).setVisible(true);
        gpane2.setVisible(true);
    }
    
    
    //when the user is allowed to press certain keys on the keyboard, they can either buzz in or submit an answer
    @FXML
    private void handleKey(KeyEvent event)
    {
       if(canBuzz1){
            if(event.getCode()==KeyCode.A){
                pturn = 0;
                setTurnLabel();
                gpane3.setVisible(true);
                canEnter2 = true;
                gpane.setVisible(false);
            }
        }
       if(canBuzz2){
           if(event.getCode()==KeyCode.G){
                pturn = 1;
                setTurnLabel();
                gpane3.setVisible(true);
                canEnter2 = true;
                gpane.setVisible(false);
       
            }
       }
       if(canBuzz3){
           if(event.getCode()==KeyCode.L){
                pturn = 2;
                setTurnLabel();
                gpane3.setVisible(true);
                canEnter2 = true;
                gpane.setVisible(false);
            }
       }
       if(canEnter){
           if(event.getCode()==KeyCode.ENTER){
               handleEnter();
           }
       }
       if(canEnter2){
           if(event.getCode()==KeyCode.ENTER){
               handleAnswerSubmit();
           }
       }

    }
    
    //for the gridpane methods, once a button is clicked, the messages label is set to 
    //the question selected by calling the question method. the method prints out whether the 
    //user is correct or incorrect in the messages label.
    @FXML   
    private void handleC1L1() 
    {
        gpane.getChildren().get(3).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 0;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
        
    }
    @FXML
    private void handleC1L2() 
    {
        gpane.getChildren().get(4).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 1;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC1L3() 
    {
        gpane.getChildren().get(29).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 2;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC1L4() 
    {
        gpane.getChildren().get(5).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 3;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC1L5() 
    {
        gpane.getChildren().get(6).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 4;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC2L1() 
    {
        gpane.getChildren().get(7).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 5;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC2L2() 
    {
        gpane.getChildren().get(8).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 6;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC2L3() 
    {
        gpane.getChildren().get(9).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 7;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC2L4() 
    {
        gpane.getChildren().get(12).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 8;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC2L5() 
    {
        gpane.getChildren().get(23).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 9;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC3L1() 
    {
        gpane.getChildren().get(0).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 10;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC3L2() 
    {
        gpane.getChildren().get(10).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 11;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC3L3() 
    {
        gpane.getChildren().get(11).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 12;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC3L4() 
    {
        gpane.getChildren().get(17).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 13;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC3L5() 
    {
        gpane.getChildren().get(20).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 14;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC4L1() 
    {
        gpane.getChildren().get(1).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 15;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC4L2() 
    {
        gpane.getChildren().get(13).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 16;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC4L3() 
    {
        gpane.getChildren().get(15).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 17;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC4L4() 
    {
        gpane.getChildren().get(18).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 18;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC4L5() 
    {
        gpane.getChildren().get(21).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 19;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC5L1() 
    {
        gpane.getChildren().get(2).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 20;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC5L2() 
    {
        gpane.getChildren().get(14).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 21;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC5L3() 
    {
        gpane.getChildren().get(16).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 22;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC5L4() 
    {
        gpane.getChildren().get(19).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 23;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @FXML
    private void handleC5L5() 
    {
        gpane.getChildren().get(22).setVisible(false);
        canBuzz1 = true;
        canBuzz2= true;
        canBuzz3= true;
        questionNumber = 24;
        if(questionNumber==dailyDouble){
            checkDailyDouble(questionNumber,playerControl);
        }
        lblQuestion.setText(questions.get(questionNumber).returnQuestion());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gpane2.setVisible(false);
        gpane3.setVisible(false);
    }

}
