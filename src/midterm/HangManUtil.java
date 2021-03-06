package midterm;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class HangManUtil {
	static Scanner scnr = new Scanner(System.in);
	static Hangman player = new Hangman();
	static char[][] hangMan = new char[13][30];
	static boolean exit = false;
	static int center = 60;
	static String print = "";
	

	/////////////////////////////// GREETING ////////////////////////////////////
	public static void greeting() {//get user name, store in object		
		boolean valid = false;
		while(!valid) {
			centerTerminal();
			System.out.print(padding(20, ' ') + "Welcome to Hangman what is your name?: ");
			player.setUserName(scnr.nextLine());
			centerTerminal();			
			valid = askUserYN(player.getUserName() + ", is that correct?");
			for (int i = 0; i < 14; i++) {
				System.out.println();
			}	
		}
	}
	////////////////////////////// MAIN MENU ////////////////////////////////////
	public static void menu() {/* ENTRY FROM MAIN METHOD */
		int selection = 0;
		boolean retry = true;
		
		while(retry && !exit) {	
			clearTerminal();
			System.out.println(padding(center - 9, ' ') + "1) Game Menu");
			System.out.println(padding(center - 9, ' ') + "2) High Scores");
			System.out.println(padding(center - 9, ' ') + "3) Credits");
			System.out.println(padding(center - 9, ' ') + "4) Exit\n");
			centerTerminal();
			print = player.getUserName() + ", what would you like to do?: ";
			System.out.print(padding(center - (print.length() / 2), ' ') + print);
			selection = validateMenu(4);
			switch(selection) {
			case 1: playMenu();
				break;
			case 2: highScores();
				break;
			case 3: credits();
				break;
			case 4: retry = false;
				break;
			}
		}	
	}

	public static void highScores() {	
		ArrayList<String> highScore = CategoryFiles.readFile("HighScores");
		int length = 0;
		for (String timeName : highScore) {
			if (timeName.length() > length) {
				length = timeName.length();
			}
		}
		TreeMap<Integer, String> ordered = new TreeMap<Integer, String>();
		for (String score : highScore) {
			String[] timeName = score.split(":");
			int t = Integer.parseInt(timeName[0]);
			ordered.put(t, timeName[1]);
		}
		centerTerminal();	
		print = padding((length + 16) - 1, '=');
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
		print = "HIGH SCORES";
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
		print = padding((length + 16) - 1, '=');
		System.out.println(padding(center - (print.length() / 2), ' ') + print);		
		int count = 1;
		for (Map.Entry<Integer, String> set : ordered.entrySet()) {
			print = count + ". " + set.getValue() + padding(length - set.getValue().length() - set.getKey().toString().length() - Integer.toString(count).length() + 5, ' ') + set.getKey() + " seconds";
			System.out.println(padding(center - (print.length() / 2), ' ') + print);
			count++;
		}
		for (int i = 0; i < (25 - highScore.size()); i++) {
			System.out.println();
		}		
		print = "Enter any key to continue... ";
		System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
		scnr.nextLine();
	}
	
	public static void credits() {	
		clearTerminal();
		print = "JAVA DEV TEAM:\n";
		System.out.println("\n" + padding(center - (print.length() / 2), ' ') + print);
		print = "~Vell~\n";
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
		print = "~Katie~";
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
		Timer timer = new Timer(5);
		print = "Enter any key to continue... ";
		System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
		scnr.nextLine();
		timer.setTime(30);
	}
	/////////////////////////////// PLAY MENU ///////////////////////////////////
	public static void playMenu() {/* ENTRY FROM MAIN MENU */
		int selection = 0;
		boolean retry = true;
		
		while(retry) {
			clearTerminal();
			System.out.println(padding(center - 9, ' ') + "1) Play Game");
			System.out.println(padding(center - 9, ' ') + "2) Difficulty");
			System.out.println(padding(center - 9, ' ') + "3) Select Category");
			System.out.println(padding(center - 9, ' ') + "4) Main Menu");
			System.out.println(padding(center - 9, ' ') + "5) Exit\n");
			centerTerminal();
			print = player.getUserName() + ", what would you like to do?: ";
			System.out.print(padding(center - (print.length() / 2), ' ') + print);	
			selection = validateMenu(5);
			switch(selection) {
			case 1: play();
				break;
			case 2: difficulty();
				break;
			case 3: selectCategory();
				break;
			case 4: retry = false;
				break;
			case 5: retry = false; exit = true;
			break;
			}	
		}
	}
	
	public static void difficulty() {	
		String[] difficulties = new String[] { "easy", "intermediate", "hard", "extreme", "custom" };
		System.out.println();
		boolean retry = true;
		while(retry) {	
			clearTerminal();
			for (int i = 0; i < difficulties.length; i++) {
				System.out.println(padding(center - 9, ' ') + (i + 1) + ") " + difficulties[i]);
			}
			centerTerminal();
			print = "Choose your difficulty level: ";
			System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
			int select = validateMenu(difficulties.length);
			player.difficulty = difficulties[select - 1];
			switch(player.difficulty) {
			case "easy": player.missesMax = 10;
			break;
			case "intermediate": player.missesMax = 5;
			break;
			case "hard": player.missesMax = 3;
			break;
			case "extreme": player.missesMax = 1;
			break;
			case "custom": customMisses();
			break;
			}			
			retry = !askUserYN("You selected " + player.difficulty + " which gives you " + player.missesMax + " tries, is this correct? ");	
		}
	}
	
	public static void customMisses() {
		boolean retry = true;
		while(retry) {	
			print = "How many tries do you plan to give yourself "+ player.getUserName() + "? ";
			System.out.print(padding(center - (print.length() / 2), ' ') + print);
			String input = scnr.nextLine();
			if (validateInt(input)) {
				player.missesMax = Integer.parseInt(input);
				if (player.missesMax < 1) {
					print = "That's impossible " + player.getUserName() + "!!!! try again...";
					System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
					continue;
				}
				if (player.missesMax > 26) {
					print = "There are only 26 letters in the alphabet " + player.getUserName() + ", try again...";
					System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
					continue;
				}
			} else {
				print = "Is your numlock on? try again... ";
				System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
				continue;
			}
			retry = !askUserYN(player.getUserName() + ", are you sure you can win in " + player.missesMax + " tries???");
		}		
	}
	
	public static void selectCategory() {
		CategoryFiles.categories.clear();
		CategoryFiles.categoryList();//populate category list from files
		System.out.println();
		boolean retry = true;
		while(retry) {	
			clearTerminal();
			int count = 0;
			int defaultCount = 0;
			for (String category : CategoryFiles.categories) {//print categories
				count++;
				category = category.replace(".txt", "");
				print = count + ") " + category;
				System.out.println(padding(center - 9, ' ') + print);
			}
			defaultCount = count + 1;
			print = defaultCount + ")" + " default (random)";
			System.out.println(padding(center - 9, ' ') + print);
			centerTerminal();
			print = "Select a category: ";
			System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
			int select = validateMenu(CategoryFiles.categories.size() + 1);//user select category
			count = 0;
			if (select == defaultCount) {
				player.category = "default";
			} else {
				for (String category : CategoryFiles.categories) {//set category
					count++;
					if (select == count) {
						player.category = category;
						break;
					}
				}				
			}		
			retry = !askUserYN("You selected " + player.category.replace(".txt", "") + ", is this correct? ");//verify
		}	
		if (!player.category.equalsIgnoreCase("default")) {
			CategoryFiles.categoryList = CategoryFiles.readFile("categories/" + player.category);
		}		
	}
	/////////////////////////////// PLAY GAME ///////////////////////////////////	
	public static void setVariables() {
		player.misses = 0;
		player.setDefaults();
		player.randomWord();//get/set random word from category
		player.wordArray = player.word.toCharArray();
		player.correctArray = new char[player.wordArray.length];
		player.missesArray = new ArrayList<Character>();
		for (int i = 0; i < player.correctArray.length; i++) {
			if (validateCharAlpha(player.wordArray[i])) {
				player.correctArray[i] = '_';
			} else {
				player.correctArray[i] = player.wordArray[i];
			}			
		}
		player.win = false;		
	}
	
	public static void play() {/* ENTRY FROM PLAY MENU */
		setVariables();
		Timer timer = new Timer();
		boolean gameOver = false;
		while(!gameOver) {
			displayGame();
			guess();
			gameOver = checkForWin();
		}
		displayGame();
		if (player.win) {
			player.time = timer.getTime();
			timer.setTime(300);
			System.out.println();
			print = "That's CORRECT, the word is " + player.word + "!";
			System.out.println(padding(center - (print.length() / 2), ' ') + print);
			print = "Congratulations " + player.getUserName() + "! YOU WON!!! in " + player.time + " seconds!";
			System.out.println(padding(center - (print.length() / 2), ' ') + print);
			if (addHighScore()) {
				print = "*****NEW HIGH SCORE*****";
				System.out.println(padding(center - (print.length() / 2), ' ') + print);
			}
		} else {
			System.out.println();
			print = "Sorry, the word is " + player.word + "!";
			System.out.println("\n" + padding(center - (print.length() / 2), ' ') + print);
			print = "YOU LOST " + player.getUserName() + " :(";
			System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
			System.out.println();
		}	
		print = "Enter any key to continue... ";
		System.out.print(padding(center - (print.length() / 2), ' ') + print);
		scnr.nextLine();
	}	
	
	public static void displayGame() {	
		clearTerminal();
		print = "Category: " + player.category.replace(".txt", "");
		System.out.println("\n" + padding(center - (print.length() / 2), ' ') + print);
		hangMan();
		print = "wrong guesses: ";
		System.out.print("\n" + padding(center - print.length(), ' ') + print);
		for (char character : player.missesArray) {
			System.out.print(character + " ");
		}
		System.out.println("\n\n");
		System.out.print(padding(center - player.correctArray.length, ' '));
		for (char character : player.correctArray) {
			System.out.print(character + " ");
		}
		print = (player.missesMax - player.misses) + " tries left\n\n";
		System.out.println("\n\t" + padding(center - (print.length() / 2), ' ') + print);
	}
	
	public static void guess() {
		boolean retry = false;
		boolean valid = false;
		player.guess = ' ';
		while(!valid) {
			retry = false;
			print = "Enter letter: ";
			System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
			String input = scnr.nextLine().trim();
			if (!input.isEmpty() && input.length() == 1) {
				char guess = input.charAt(0);
				if (validateCharAlpha(guess)) {
					player.setGuess(guess);
					boolean miss = true;
					int count = 0;
					char guessLow = input.toLowerCase().charAt(0);
					char guessUpper = input.toUpperCase().charAt(0);
					for (char letter : player.correctArray) {
						if (letter == guessLow || letter == guessUpper) {
							print = guess + " already on the board, try again...";
							System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
							miss = false;
							retry = true;
							break;
						}
					} 
					if (!retry) {
						if (player.misses != 0) {
							for (char letter : player.missesArray) {
								if (letter == guessLow || letter == guessUpper) {
									print = guess + " already guessed that, try again...";
									System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
									miss = false;
									retry = true;
									break;
								}
							}						
						}						
					}
					if (!retry) {
						for (char letter : player.wordArray) {
							if (letter == guessLow || letter == guessUpper) {
								player.correctArray[count] = player.wordArray[count];
								miss = false;
								valid = true;
							}
							count++;
						}						
					}
					if (miss) {
						randomMissDisplay(guess);
						player.missesArray.add(guess);
						player.misses++;
						valid = true;
					}
				} else {
					continue;	
				}
			} else {
				print = player.getUserName() + " what was that? try again...";
				System.err.println("\n" + padding(center - (print.length() / 2), ' ') + print);
				continue;
			}
		}	
	}
	
	public static void randomMissDisplay(char guess) {
		boolean valid = false;
		while(!valid) {
			Random i = new Random();
			int x = i.nextInt(9);
			Random j = new Random();
			int y = j.nextInt(12) + 18;
			if (!validateCharAlpha(hangMan[x][y])) {
				hangMan[x][y] = guess;
				valid = true;
			}			
		}
	}
	
	public static boolean checkForWin() {
		if (player.misses == player.missesMax) {
			return true;
		}
		for (int i = 0; i < player.wordArray.length; i++) {
			if (player.correctArray[i] != player.wordArray[i]) {
				return false;
			}
		}	
		player.win = true;
		return true;
	}
	
	public static boolean addHighScore() {
		ArrayList<String> highScore = CategoryFiles.readFile("HighScores");
		if (highScore.size() < 20) {
			highScore.add(player.time + ":" + player.getUserName());
			CategoryFiles.writeToFile(highScore, "HighScores");
			return true;
		} 
		int i = 0;
		for (String score : highScore) {
			String[] scoreName = score.split(":");
			int time = Integer.parseInt(scoreName[0]);
			if (player.time < time) {
				highScore.set(i, player.time + ":" + player.getUserName());
				CategoryFiles.writeToFile(highScore, "HighScores");
				return true;
			}
			i++;
		}
		return false;
	}
	/////////////////////////////// VISUALS ////////////////////////////////////
	public static String padding(int multiplier, char character) {
		String multiples = "";
		for (int i = 1; i <= multiplier; i++) {
			multiples += character;
		}
		return multiples;
	}
	
	public static void centerTerminal() {
		int multiplier = 14;
		for (int i = 0; i < multiplier;i++) {
			System.out.println(" ");
		}
	}
		
	public static void clearTerminal() {
		int multiplier = 30;
		for (int i = 0; i < multiplier;i++) {
			System.out.println(" ");
		}
	}	
//	public static void displayGameOver() {
//		/* extended challenge ascii art */
//	}
	////////////////////////////// HANGING MAN /////////////////////////////////
	public static void hangMan() {
		if (player.misses == 0) {
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 30; j++) {
					if (i == 0 && j == 8) {
						hangMan[i][j] = '╔';
					} else if (i == 0 && (j > 8 && j < 16)) {
						hangMan[i][j] = '═';
					} else if (i == 0 && j == 16) {
						hangMan[i][j] = '╗';
					} else if (i == 0 && j == 17) {
						hangMan[i][j] = '┐';
					} else if ((i == 1 && j == 8) || (i > 0 && i < 9 && j == 16)) {
						hangMan[i][j] = '║';
					} else if (i > 0 && i < 9 && j == 17) {
						hangMan[i][j] = '│';
					} else if (i == 2 && j == 8) {
						hangMan[i][j] = 'Ü';
					} else if (i == 9) {
						hangMan[i][j] = '█';
					} else if (i == 10) {
						hangMan[i][j] = '▓';
					} else if (i == 11) {
						hangMan[i][j] = '▒';
					} else if (i == 12) {
						hangMan[i][j] = '░';
					} else {
						hangMan[i][j] = ' ';
					}	
				}
			}	
			if (player.difficulty == "custom") {
				for (int i = 8; i < 14; i++) {
					hangMan[0][i] = ' ';
				}
				for (int i = 0; i < 3; i++) {
					hangMan[i][8] = ' ';
				}
			}
		}
		switch(player.difficulty) {
		case "easy": hangManEasy(hangMan);
		break;
		case "default": hangManDefault(hangMan);
		break;
		case "intermediate": hangManIntermediate(hangMan);
		break;
		case "hard": hangManHard(hangMan);
		break;
		case "extreme": hangManExtreme(hangMan);
		break;
		case "custom": hangManCustom(hangMan);
		break;
		}
		for (int i = 0; i < 13; i++) {
			System.out.print(padding(center - 15, ' '));
			for (int j = 0; j < 30; j++) {
				System.out.print(hangMan[i][j]);
			}
			System.out.print("\n");
		}
	}
	
	public static char[][] hangManEasy(char[][] hangMan) {//10
		switch(player.misses) {
		case 1: hangMan[2][7] = '(';//head
				hangMan[2][8] = 'Ö';
				hangMan[2][9] = ')';
				break;
		case 2: hangMan[3][8] = '¥';//neck
				break;
		case 3: hangMan[4][8] = '║';//spine
				break;
		case 4: hangMan[4][7] = '║';//torso
				hangMan[4][9] = '║';
				break;
		case 5: hangMan[3][6] = '╚';//left arm
				hangMan[3][7] = '╗';
				break;
		case 6: hangMan[3][9] = '╔';//right arm
				hangMan[3][10] = '╝';
				break;
		case 7: hangMan[5][7] = '╠';//legs
				hangMan[5][8] = '╩';
				hangMan[5][9] = '╣';
				break;
		case 8: hangMan[6][7] = '╝';//left foot
				break;
		case 9: hangMan[6][9] = '╚';//right foot
				break;
		case 10:hangMan[2][6] = '□';//hands
				hangMan[2][10] = '□';
				break;
		}
		return hangMan;
	}
	
	public static char[][] hangManDefault(char[][] hangMan) {//6
		switch(player.misses) {
		case 1: hangMan[2][7] = '(';//head
				hangMan[2][8] = 'Ö';
				hangMan[2][9] = ')';
				break;
		case 2: hangMan[3][8] = '¥';//neck
				break;
		case 4: hangMan[4][8] = '║';//spine
				hangMan[4][7] = '║';//torso
				hangMan[4][9] = '║';
				break;
		case 3: hangMan[3][6] = '╚';//left arm
				hangMan[3][7] = '╗';
				hangMan[3][9] = '╔';//right arm
				hangMan[3][10] = '╝';
				break;
		case 5: hangMan[5][7] = '╠';//legs
				hangMan[5][8] = '╩';
				hangMan[5][9] = '╣';
				break;
		case 6: hangMan[6][7] = '╝';//left foot
				hangMan[6][9] = '╚';//right foot
				hangMan[2][6] = '□';//hands
				hangMan[2][10] = '□';
				break;
		}
		return hangMan;
	}
	
	public static char[][] hangManIntermediate(char[][] hangMan) {//5
		switch(player.misses) {
		case 1: hangMan[2][7] = '(';//head
				hangMan[2][8] = 'Ö';
				hangMan[2][9] = ')';
				hangMan[3][8] = '¥';//neck
				break;
		case 3: hangMan[4][8] = '║';//spine
				hangMan[4][7] = '║';//torso
				hangMan[4][9] = '║';
				break;
		case 2: hangMan[3][6] = '╚';//left arm
				hangMan[3][7] = '╗';
				hangMan[3][9] = '╔';//right arm
				hangMan[3][10] = '╝';
				break;
		case 4: hangMan[5][7] = '╠';//legs
				hangMan[5][8] = '╩';
				hangMan[5][9] = '╣';
				break;
		case 5: hangMan[6][7] = '╝';//left foot
				hangMan[6][9] = '╚';//right foot
				hangMan[2][6] = '□';//hands
				hangMan[2][10] = '□';
				break;
		}
		return hangMan;
	}

	public static char[][] hangManHard(char[][] hangMan) {//3
		switch(player.misses) {
		case 1: hangMan[2][7] = '(';//head
				hangMan[2][8] = 'Ö';
				hangMan[2][9] = ')';
				hangMan[3][8] = '¥';//neck
				break;
		case 2: hangMan[4][8] = '║';//spine
				hangMan[4][7] = '║';//torso
				hangMan[4][9] = '║';
				hangMan[3][6] = '╚';//left arm
				hangMan[3][7] = '╗';
				hangMan[3][9] = '╔';//right arm
				hangMan[3][10] = '╝';
				break;
		case 3: hangMan[5][7] = '╠';//legs
				hangMan[5][8] = '╩';
				hangMan[5][9] = '╣';
				hangMan[6][7] = '╝';//left foot
				hangMan[6][9] = '╚';//right foot
				hangMan[2][6] = '□';//hands
				hangMan[2][10] = '□';
				break;
		}
		return hangMan;
	}
	
	public static char[][] hangManExtreme(char[][] hangMan) {//1
		switch(player.misses) {
		case 1: hangMan[2][7] = '(';//head
				hangMan[2][8] = 'Ö';
				hangMan[2][9] = ')';
				hangMan[3][8] = '¥';//neck
				hangMan[4][8] = '║';//spine
				hangMan[4][7] = '║';//torso
				hangMan[4][9] = '║';
				hangMan[3][6] = '╚';//left arm
				hangMan[3][7] = '╗';
				hangMan[3][9] = '╔';//right arm
				hangMan[3][10] = '╝';
				hangMan[5][7] = '╠';//legs
				hangMan[5][8] = '╩';
				hangMan[5][9] = '╣';
				hangMan[6][7] = '╝';//left foot
				hangMan[6][9] = '╚';//right foot
				hangMan[2][6] = '□';//hands
				hangMan[2][10] = '□';
				break;
		}
		return hangMan;
	}
	
	public static char[][] hangManCustom(char[][] hangMan) {//0-26
		int loop = (26 / player.missesMax) + 1;
		int loops = loop;
		if ((26 - player.misses) < loop) {
			loops = 26 - player.misses;
		}
		for (int i = 0; i < loops; i++) {
			int reveal = loop * player.misses + i;
			switch(reveal) {
			case 1: hangMan[0][13] = '═';
					break;
			case 2: hangMan[0][12] = '═';
					break;
			case 3: hangMan[0][11] = '═';
					break;
			case 4: hangMan[0][10] = '═';
					break;
			case 5: hangMan[0][9] = '═';
					break;
			case 6: hangMan[0][8] = '╔';
					break;
			case 7: hangMan[1][8] = '║';
					break;
			case 8: hangMan[2][8] = 'Ü';
					break;
			case 9: hangMan[2][7] = '(';//head
					break;
			case 10: hangMan[2][8] = 'Ö';
					break;
			case 11: hangMan[2][9] = ')';
					break;
			case 12: hangMan[3][8] = '¥';//neck
					break;
			case 13: hangMan[4][8] = '║';//spine
					break;
			case 14: hangMan[4][7] = '║';//torso
					break;
			case 15: hangMan[4][9] = '║';
					break;
			case 16: hangMan[3][6] = '╚';//left arm
					break;
			case 17: hangMan[3][7] = '╗';
					break;
			case 18: hangMan[3][9] = '╔';//right arm
					break;
			case 19: hangMan[3][10] = '╝';
					break;
			case 20: hangMan[5][7] = '╠';//legs
					break;
			case 21: hangMan[5][8] = '╩';
					break;
			case 22: hangMan[5][9] = '╣';
					break;
			case 23: hangMan[6][7] = '╝';//left foot
					break;
			case 24: hangMan[6][9] = '╚';//right foot
					break;
			case 25: hangMan[2][6] = '□';//hands
					break;
			case 26: hangMan[2][10] = '□';
					break;
			}			
		}

		return hangMan;
	}
	////////////////////////////// VALIDATORS //////////////////////////////////
	public static int validateMenu(int menuCount) {
		int input = 0;
		boolean valid = false;
		while(!valid) {
			String in = scnr.nextLine().trim();
			if (in.isEmpty()) {
				print = "Perhaps check your numlock " + player.getUserName() + ", try again... ";
				System.err.print(padding(center - (print.length() / 2), ' ') + print);
				continue;					
			} else if (in.matches("[0-9]")) {
				input = Integer.parseInt(in);
				if (input >= 1 && input <= menuCount) {
					return input;
				} else {
					print = "Sorry " + player.getUserName() + ", " + input + " is not a menu option, try again... ";
					System.err.print(padding(center - (print.length() / 2), ' ') + print);
					continue;
				}
			} else {
				print = "Looking for numbers " + player.getUserName() + ", try again... ";
				System.err.print(padding(center - (print.length() / 2), ' ') + print);
				continue;
			}
		}
		return input;
	}
	
	public static boolean validateInt(String input) {
		if (input.isEmpty()) {
			return false;
		}
		if (input.matches("[-0-9]*")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validateCharAlpha(char character) {
		String charString = Character.toString(character);
		if (charString.matches("[a-zA-Z]")) {
			return true;
		} else {
			return false;
		}			
	}
	/////////////////////////////// YES | NO ////////////////////////////////////
 	public static boolean askUserYN(String question) {//ask user a yes/no question
 		char select = ' ';
 		print = question + " (y/n) ";
		System.out.print("\n" + padding(center - (print.length() / 2), ' ') + print);
		if (scnr.hasNextLine()) {
			select = scnr.nextLine().charAt(0);
		}
		return validateYesNo(select);
	}
	
	public static boolean validateYesNo(char input) {//validate yes/no user input
		while (input != 'y' && input != 'Y' && input != 'n' && input != 'N') {
			print = "This is a simple yes or no question, try again...";
			System.err.print(padding(center - (print.length() / 2), ' ') + print);
			if (scnr.hasNextLine()) {
				input = scnr.nextLine().charAt(0);
			}	
		}
		return (input == 'y' || input == 'Y');
	}
	//////////////////////////////// EXIT //////////////////////////////////////
	public static void exit() {
		print = "Nice playing with you " + player.getUserName() + ", catch you later!";
		System.out.println(padding(center - (print.length() / 2), ' ') + print);
		scnr.close();
		if (player.word != null) {
			CategoryFiles.closeReader();
		}	
		System.exit(0);
	}
	
}

