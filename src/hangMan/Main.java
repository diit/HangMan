package hangMan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame implements ActionListener{

	//GLOBALS
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	Image gallow;
	JButton[] input = new JButton[26];
	String currentWord=null;
	ArrayList<String> words = new ArrayList<String>();
	int score = 0;
	int wrongGuesses = 0;
	int wins = 0;

	//Swing Globals
	JLabel output = new JLabel();
	JLabel gallows = new JLabel();
	JButton guess;
	ImageIcon[] gallowViews = {
			new ImageIcon("img/0.png"),
			new ImageIcon("img/1.png"),
			new ImageIcon("img/2.png"),
			new ImageIcon("img/3.png"),
			new ImageIcon("img/4.png"),
			new ImageIcon("img/5.png"),
			new ImageIcon("img/6.png")};

	public Main() {
		setupFrame(); //Setup Frame Attributes
		loadWords();

		JPanel container = new JPanel(new BorderLayout());

		/*
		// Output for Correct Guesses
		JPanel topView = new JPanel(new GridLayout(1,10));
		for(int i=0;i<25;i++){
			output[i] = new JLabel("__");
			topView.add(output[i]);
		}
		 */

		//FINAL BUILD
		JPanel topInner = new JPanel(new GridLayout(2,1));
		topInner.add(header());
		topInner.add(output());

		container.add(topInner, BorderLayout.NORTH);
		container.add(main(), BorderLayout.CENTER);
		container.add(input(), BorderLayout.SOUTH);
		add(container);
	}

	// PANELS
	public JPanel header(){
		JPanel pnl=new JPanel();
		pnl.setBackground(new Color(2, 119, 158));

		JLabel title = new JLabel("Hang Man: The Game");
		title.setForeground(new Color(238,252,255));

		pnl.add(title);

		return pnl;
	}
	public JPanel output(){
		JPanel pnl=new JPanel();
		pnl.setBackground(new Color(87,121,135));

		output = new JLabel("Click \"New Game\" to begin.");
		output.setForeground(new Color(238,252,255));

		pnl.add(output);

		return pnl;
	}
	public JPanel main(){
		JPanel pnl=new JPanel(new GridLayout(1,2));
		pnl.setBackground(new Color(238,252,255));
		
		gallows.setIcon(gallowViews[0]);

		JPanel pnl_left=new JPanel(new GridLayout(3,1));
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(this);
		JButton random = new JButton("Random Mode");
		random.addActionListener(this);
		guess = new JButton("Guess");
		guess.addActionListener(this);
		guess.setEnabled(false);

		pnl_left.add(newGame);
		pnl_left.add(random);
		pnl_left.add(guess);

		pnl.add(gallows);
		pnl.add(pnl_left);

		return pnl;
	}
	public JPanel input(){
		JPanel pnl=new JPanel(new GridLayout(3,10));

		for(int i=0;i<26;i++){
			input[i] = new JButton(String.valueOf(alphabet[i]));
			input[i].addActionListener(this);
			pnl.add(input[i]);
		}

		return pnl;
	}

	public void setupFrame(){
		setTitle("Hangman - Davis Amyot");
		setSize(491, 487);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); //This will center the JFrame in the middle of the screen
		setVisible(true);
	}

	//Miscellaneous Operations
	public void setOutput(String str){
		String gen = "";
		for(char s:str.toCharArray()){
			if(String.valueOf(s).equals(" ")){
				gen+="/ ";
			}else{
				gen+="_ ";
			}
		}

		output.setText(gen);
	}
	public void loadWords(){
		words.add("Central Secondary");
		words.add("Java");
		words.add("Anthropology");
		words.add("Thunderstorm");
		words.add("Arithmetic");
	}
	public void clearSession(){
		//Re-enable Input Buttons
		for(int i=0;i<26;i++){
			input[i].setEnabled(true);
		}
		
		//clear score
		score=0;
		wrongGuesses=0;
		
		//reset Hangman
		gallows.setIcon(gallowViews[0]);
	}
	
	//Answer Methods
	public void wrongAnswer(){
		wrongGuesses++;
		if(wrongGuesses==6){
			lose();
		}else{
			gallows.setIcon(gallowViews[wrongGuesses]);
		}
	}
	public void rightAnswer(String ltr){
		score++;
		if(score==currentWord.length()-currentWord.replaceAll("[a-z]", "").length()){
			win();
		}else{
			StringBuilder tmp = new StringBuilder(output.getText()); //Allows updating string by index
			if(currentWord.toLowerCase().indexOf(ltr)==0){ //Fixes out of bounds exception for first letter guess
				tmp.setCharAt((currentWord.toLowerCase().indexOf(ltr)), ltr.charAt(0)); // Updates String to show correct letter
			}else{
				tmp.setCharAt((currentWord.toLowerCase().indexOf(ltr))*2, ltr.charAt(0)); // Updates String to show correct letter
			}

			output.setText(String.valueOf(tmp));
		}
	}
	public void check(String ltr){
		if(currentWord.toLowerCase().indexOf(ltr)<0){
			wrongAnswer();
		}else{
			rightAnswer(ltr);
		}
		input["abcdefghijklmnopqrstuvwxyz".indexOf(ltr)].setEnabled(false);
	}
	
	//Win/Lose Methods
	public void win(){
		output.setText("YOU WIN! press \"New Game\" to play again.");
		JOptionPane.showMessageDialog(this, "YOU WIN! press \"New Game\" to play again.");
		wins++;
		setTitle("Hangman - Davis Amyot - Score:"+wins);
	}
	public void lose(){
		output.setText("Sorry, you lost. press \"New Game\" to play again.");
		JOptionPane.showMessageDialog(this, "Sorry, you lost. press \"New Game\" to play again.");
	}

	// Respond to button Action
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Command - "+e.getActionCommand());
		String cmd=e.getActionCommand();

		if(cmd.equals("New Game")){
			clearSession(); //Reset game
			
			try{ //Prevents string being set to a null object;
				currentWord = (String)JOptionPane.showInputDialog(this, "Enter your word: ", "Hang Man - New Game", 1).replaceAll("[!@#$%^&*()?/|\\{}_~`:;'\",.><1234567890]", "").toLowerCase();
				setOutput(currentWord);
			}catch(NullPointerException xe){
				output.setText("You need to pick a word first to play. Click \"Start Game\" To try again.");
			}
			guess.setEnabled(true); //Allows user to guess word
		}else if(cmd.equals("Random Mode")){
			clearSession(); //Reset game

			//get Random Question
			currentWord = (String) words.get(new Random().nextInt((words.size()-1) - 0 + 1));
			setOutput(currentWord);
		}else if(cmd.equals("Guess")){
			try{ //Prevents string being set to a null object;
				String guessWord = (String)JOptionPane.showInputDialog(this, "Enter your guess: ", "Hang Man - Guess", 1).toLowerCase();
				if(guessWord.equals(currentWord)){
					win();
				}else{
					JOptionPane.showMessageDialog(this, "Sorry, you didnt guess the correct question.");
				}
			}catch(NullPointerException xe){}//User closed dialogue with no input	
		}else{ //Letter picked
			check(cmd);
		}
	}

	public static void main(String[] args){
		//Launch Application Container
		new Main();

	}
}