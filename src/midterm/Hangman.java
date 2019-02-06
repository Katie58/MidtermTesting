package midterm;

import java.util.ArrayList;
import java.util.Random;

public class Hangman {

	private String userName;
	
	public String category = "default";
	public String difficulty = "default";
	public String word;
	public char[] wordArray;
	public char[] correctArray;
	public ArrayList<Character> missesArray;
	public int missesMax;
	public int misses;
	public char guess;
	public boolean win;
	public int time;
	
	public Hangman() {
	}
	
	public void setGuess(char guess) {
		this.guess = guess;
	}
	
	public char getGuess() {
		return guess;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setDefaults() {
		if (category.equals("default")) {
			CategoryFiles.categoryList();
			Random r = new Random();
			int random = r.nextInt(CategoryFiles.categories.size());
			for (int i = 0; i < CategoryFiles.categories.size(); i++) {
				if (i == random) {
					category = CategoryFiles.categories.get(i);
					break;
				}
			}
			CategoryFiles.categoryList = CategoryFiles.readFile("categories/" + category);
		}
		if (difficulty.equals("default")) {
			missesMax = 6;
		}
	}
		
	public void randomWord() {
		Random r = new Random();
		int random = r.nextInt(CategoryFiles.categoryList.size());
		for (int i = 0; i < CategoryFiles.categoryList.size(); i++) {
			if (i == random) {
				word = CategoryFiles.categoryList.get(i);
				break;
			}
		}
	}
	
}
