import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Game implements Serializable 
{
	private final String SAVES_FOLDER_NAME = "saves";
	private final String SAVE_EXT = "mpsave";
	private final String RESOURCES = "resources";
	private final String MAIN_THEME = "main_theme.wav";
	private final String DICE_SOUND = "dice.wav";
	private final String MONEY_SOUND = "money_sound.wav";
	private final int DELAY_PER_PAWN_MOVE = 250;
	private String[] dieFacePaths;
	
	
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	private ArrayList<Card> pickedCards;
	private Die[] dice;
	
	private MainWindow gameWindow;
	private int currentTurn;
	private Player currentPlayer;
	private boolean determineTheStarterState;
	
	private int firstRollCounter;
	private ArrayList<Integer> firstRolledNumbers;
	private ArrayList<Player> playersToRoll;
	private Player starter;
	private GameTable gameTable;
	
	private double initialMoney;
	private boolean rollDiceState;
	private boolean diceRolledState;
	private boolean chanceCardMoveState;
	private boolean pawnPlacedState;
	
	private int moveAmount = 0;
	private int globalNewLocation;
	
	private ArrayList<Player> playersQueue = new ArrayList<Player>();
	
	private JPanel gameBoard;
	private JButton finishTheGameButton, rollDiceButton, continueButton, buildHouseButton;
	private JButton tradeButton, buyLandButton, listPropertiesButton, buildHotelButton;
	private JButton newGameButton, saveGameButton, loadGameButton, readGameManualButton;
	private JButton [] buttons;
	private JTable playerTable;
	private DefaultTableModel playerTableModel;
	private Clip mainTheme;
	
	
	private boolean[] lastButtonStates;
	
	public Game(ArrayList<Player> players, double initialMoney)
	{
		currentTurn = 1;
		this.players = players;
		determineTheStarterState = false;
		playersToRoll = players;
		this.initialMoney = initialMoney;
		dice = new Die[2];
		dice[0] = new Die();
		dice[1] = new Die();
		
		dieFacePaths = new String[6];
		for (int i = 1; i <= 6; i++) 
			dieFacePaths[i-1] = Paths.get(RESOURCES, "face"+i+".png").toFile().getPath();
		
	}
	
	public void start() 
	{
		buildTheGame();
		
		for (Player player: players) 
        {
        	Pawn p = new Pawn(0, gameTable.getSquare(0), player, player.getID());
        	player.setPawn(p);
        	player.setMoney(initialMoney);
        }

		gameWindow = new MainWindow(this);
        gameWindow.setTitle("Monopoly");
        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setVisible(true);
        
        finishTheGameButton = gameWindow.getFinishTheGameButton();
        rollDiceButton = gameWindow.getRollDiceButton();
        continueButton = gameWindow.getContinueButton();
        buildHouseButton = gameWindow.getBuildHouseButton();
        
        tradeButton = gameWindow.getTradeButton();
        buyLandButton = gameWindow.getBuyLandButton();
        listPropertiesButton = gameWindow.getListPropertiesButton();
        buildHotelButton = gameWindow.getBuildHotelButton();

        newGameButton = gameWindow.getNewGameButton();
        saveGameButton = gameWindow.getSaveGameButton();
        loadGameButton = gameWindow.getLoadGameButton();
        readGameManualButton = gameWindow.getReadGameManualButton();
        
        buttons = new JButton[] {finishTheGameButton, rollDiceButton, continueButton, buildHouseButton, tradeButton, buyLandButton, listPropertiesButton, buildHotelButton, newGameButton, saveGameButton, loadGameButton, readGameManualButton};
        
        loadGameButton.setEnabled(false);
		finishTheGameButton.setEnabled(false);
		newGameButton.setEnabled(false);
		saveGameButton.setEnabled(false);
		tradeButton.setEnabled(false);
        
        playerTable = gameWindow.getPlayerTable();
        playerTableModel = (DefaultTableModel) playerTable.getModel();
        gameBoard = gameWindow.getGameBoard();
        
        updatePlayerTable();
        
        continueButton.setEnabled(false);
        buildHouseButton.setEnabled(false);
        buildHotelButton.setEnabled(false);
        rollDiceButton.setEnabled(false);
        buyLandButton.setEnabled(false);
        
       gameBoard.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int x = e.getX();
		        int y = e.getY();
		        
		        int loc = gameTable.getLocationFromPos(x, y);
		        
		        if (loc != -1) 
		        {
		        	InformationWindow info = new InformationWindow();
		        	info.setResizable(false);
		        	info.setTitle("Information");
		        	info.setTextOfTextArea(gameTable.getInformationOf(loc));
		        	info.pack();
		        	info.setVisible(true);
		        }
		        	

		    }
		});
       
       try 
       {
    	   mainTheme = AudioSystem.getClip();
    	   AudioInputStream ais = AudioSystem.getAudioInputStream(new File(Paths.get(RESOURCES, MAIN_THEME).toFile().getPath()));
    	   mainTheme.open(ais);
    	   mainTheme.loop(Clip.LOOP_CONTINUOUSLY);
       }
       catch (LineUnavailableException e1) {
		    e1.printStackTrace();
       }
       catch (IOException e) 
       {
    	   e.printStackTrace();
       } 
       catch (UnsupportedAudioFileException e1) 
       {
    	   e1.printStackTrace();
       }
       
        determineTheStarter();
	}
	
	public void updatePlayerTable () 
	{
		playerTableModel.setRowCount(0);
		for (int i = 0; i < players.size(); i++) 
		{
			
			Player p = players.get(i);
			Object[] elements = new Object[4];
			elements[0] = ""+p.getID();
			elements[1] = p.getName();
			elements[2] = p.getPawn().getColorName();
			elements[3] = "$" + p.getBankAccount().getMoney();
			playerTableModel.addRow(elements);
		}
		
	}
	
	private int countValue(int value, ArrayList<Integer> arr) 
	{
		int counter = 0;
		for (Integer i: arr) 
		{
			if (value == (int) i) 
			{
				counter++;
			}
		}
		return counter;
	}
	
	private int countValue(double value, ArrayList<Double> arr) 
	{
		int counter = 0;
		for (Double d: arr) 
		{
			if (value == (double) d) 
			{
				counter++;
			}
		}
		return counter;
	}
	
	private int[] getIndexesOfNumber(int value, ArrayList<Integer> arr) 
	{
		int count = countValue(value, arr);
		int [] indexes = new int[count];
		
		int counter = 0;
		for (int i = 0; i < arr.size(); i++) 
		{
			if (value == (int)arr.get(i)) 
			{
				indexes[counter] = i;
				counter++;
			}
		}
		return indexes;
	}
	
	private int[] getIndexesOfNumber(double value, ArrayList<Double> arr) 
	{
		int count = countValue(value, arr);
		int [] indexes = new int[count];
		
		int counter = 0;
		for (int i = 0; i < arr.size(); i++) 
		{
			if (value == (double)arr.get(i)) 
			{
				indexes[counter] = i;
				counter++;
			}	
		}
		return indexes;
	}

	private double getMax(double[] arr) 
	{
		double max = arr[0];
		for (int i = 1; i < arr.length; i++) 
		{
			if (arr[i] > max) 
			{
				max = arr[i];
			}
		}
		return max;
	}
	
	private int getMax(ArrayList<Integer> arr) 
	{
		int max = arr.get(0);
		for (int i = 1; i < arr.size(); i++) 
		{
			if (arr.get(i) > max) 
			{
				max = arr.get(i);
			}
		}
		return max;
	}
	

	private ArrayList<Player> pickPlayers (int[] indexes, ArrayList<Player> players) 
	{
		
		ArrayList<Player> pickedPlayers = new ArrayList<Player>();

		for (int i = 0; i < indexes.length; i++) 
		{
			pickedPlayers.add(players.get(indexes[i]));	
		}
		
		return pickedPlayers;
		
	}

	private int mod (int n, int d) 
	{
		int q = floor((double)n/d);
		return n - (q*d);
	}
	
	private int floor(double n) 
	{
		int f = (int) n;
		
		if (n < 0 && ((int)n) != n)
			f = ((int)n) - 1;
		
		return f;
	}
	
	private void loop() 
	{
		
		if (rollDiceState) 
		{
			rollDiceButton.setEnabled(true);
			continueButton.setEnabled(false);
			saveGameButton.setEnabled(false);
			buildHouseButton.setEnabled(false);
			buyLandButton.setEnabled(false);
			gameWindow.addTextToTextArea(String.format("%s, please roll dice!\n", currentPlayer));
		}
		
		else 
		{
			if (diceRolledState || chanceCardMoveState) 
			{
				rollDiceButton.setEnabled(false);
				continueButton.setEnabled(true);
				saveGameButton.setEnabled(true);

				int newLocation = -1;
				int currentLocation = currentPlayer.getPawn().getLocation();
				int total;
				int direction = 1;
				
				if (diceRolledState) 
				{
					total = getTotalDiceValue();
					gameWindow.addTextToTextArea(String.format("%s rolled %d!\n", currentPlayer, total));
					
				}
				else 
				{
					total = moveAmount;
					
					if (moveAmount < 0) 
						direction = -1;

				}
				
				newLocation = currentLocation + total;
				newLocation = mod(newLocation, gameTable.getFields().size());
				this.globalNewLocation = newLocation;

				movePawnOn(currentPlayer.getPawn(), newLocation, direction);
				
				
				Timer timer = new Timer(10, new ActionListener() 
				{
				    @Override
				    public void actionPerformed(ActionEvent e) {
				    	if (pawnPlacedState) 
				    	{
				    		((Timer)e.getSource()).stop();
				    		makeDecision(globalNewLocation);
				    	}	
				    }
				});

				timer.setInitialDelay(10);
				timer.start();
				
			}
		}
	}
	
	private void makeDecision (int newLocation) 
	{
		GameObject currentObject = gameTable.getSquare(newLocation).getObject();
		if (currentObject.getType().equals("rolldice")) 
		{
			gameWindow.addTextToTextArea(String.format("%s will roll dice again! Good luck!\n", currentPlayer));
			rollDiceState = true;
			diceRolledState = false;
			loop();
		}
		if (currentObject.getType().equals("losemoney"))
		{
			double amount = ((LoseMoney)currentObject).getAmount();
			loseMoneyProcedure(amount);
			
		}
		
		if (currentObject.getType().equals("paytax")) 
		{
			double amount = ((PayTax)currentObject).getAmount();
			payTaxProcedure(amount);
		}
		
		if (currentObject.getType().equals("gotostart")) 
		{
			gameWindow.addTextToTextArea(currentPlayer + " goes to starting point!\n");
			gameTable.placePawnOn(currentPlayer.getPawn(), 0);
		}
		
		if (currentObject.getType().equals("freeparking")) 
		{
			freeParkingProcedure();
		}
		
		if (currentObject.getType().equals("land")) 
		{
			Land landObj = (Land)currentObject;
			if (landObj.isOwned()) 
			{
				if (!landObj.getOwner().equals(currentPlayer)) 
				{
					
					boolean operation = landObj.getRentFromPlayer(currentPlayer);
					double amount = landObj.getRentAmount();
					if (operation) 
					{
						gameWindow.addTextToTextArea(currentPlayer + " has paid $" +amount + " rent to " + landObj.getOwner() + "!\n");
					}
					else 
					{
						gameWindow.addTextToTextArea(currentPlayer + " does not have enough money to pay $" + amount + " rent!");
						playerGoesBankruptcy(currentPlayer, landObj.getOwner());
					}
					updatePlayerTable();
				}
			}
		}
		
		if (currentObject.getType().equals("jail")) 
		{
			jailProcedure();
		}
		
		if (currentObject.getType().equals("gotojail")) 
		{
			goToJailProcedure();
		}
		
		if (currentObject.getType().equals("chance")) 
		{
			gameWindow.addTextToTextArea(currentPlayer + " has come to the Chance field!\nA chance card will be picked randomly by computer. Good luck, " + currentPlayer.getName() + "!\n");
			Card picked = pickCard();
			if (!picked.getType().equals("standard")) 
			{ 
				((TriggeredCard) picked).trigger();
			}
			
			JOptionPane.showConfirmDialog(null, picked.getDescription(), picked.getTitle(), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			int id = picked.getID();
			switch(id) 
			{
				case 1:
					goToJailProcedure();
					break;
				case 2:
					double amount = currentPlayer.getBankAccount().getMoney()*0.1; // 10% of the money owned.
					payTaxProcedure(amount);
					break;
				case 3:
					double giftAmount = ((GiftCard) picked).getGiftAmount();
					currentPlayer.earnMoney(giftAmount);
					gameWindow.addTextToTextArea(currentPlayer + " has won $" + giftAmount + " gift!\n");
					updatePlayerTable();
					break;
				case 4:
					MoveCard moveCard = (MoveCard) picked;
					int mAmount = moveCard.getMoveAmount();
					String direction = moveCard.getDirection();
					gameWindow.addTextToTextArea(currentPlayer + " is going " + Math.abs(mAmount) + " steps " + direction + "!\n");
					moveAmount = mAmount;
					rollDiceState = false;
					diceRolledState = false;
					chanceCardMoveState = true;
					loop();
					break;
				case 5:
					boolean canAfford = currentPlayer.canAfford(100.0);
					if (canAfford) 
					{
						ArrayList<Player> playersInGame = new ArrayList<Player>();
						for(Player p: players) 
						{
							if (p.isInGame() && !p.equals(currentPlayer)) 
							{
								playersInGame.add(p);
							}
						}
						
						double[] moneyAmounts = new double[playersInGame.size()];
						for (int i = 0; i < playersInGame.size(); i++) 
						{
							moneyAmounts[i] = playersInGame.get(i).getBankAccount().getMoney();
						}
						
						double maxAmount = this.getMax(moneyAmounts);
						ArrayList<Double> mAmounts = new ArrayList<Double>();
						for (int i = 0; i < moneyAmounts.length; i++) 
						{
							mAmounts.add(moneyAmounts[i]);
						}
						int [] indexes = this.getIndexesOfNumber(maxAmount, mAmounts);
						double amountPerPlayer = 100.0/indexes.length;
						amountPerPlayer = Math.round(amountPerPlayer*100.0)/100.0;
						for (int i = 0; i < indexes.length; i++) 
						{
							currentPlayer.payRentTo(playersInGame.get(indexes[i]), amountPerPlayer);
							gameWindow.addTextToTextArea(currentPlayer + " paid $" + amountPerPlayer + " to " + playersInGame.get(indexes[i]) +"!\n");
						}
						updatePlayerTable();
					}
					else 
					{
						gameWindow.addTextToTextArea(currentPlayer + " does not have enough money to pay $100!\n");
						playerGoesBankruptcy(currentPlayer);
					}
					break;
				case 6:
					freeParkingProcedure();
					break;
				case 7: //loseCard
					LoseCard loseCard = (LoseCard) picked;
					double loseAmount = loseCard.getLoseAmount();
					loseMoneyProcedure(loseAmount);
					break;
				case 8:
					gameWindow.addTextToTextArea(currentPlayer + " goes to Free Parking field and collects all money accumulated in there!\n");
					gameTable.placePawnOn(currentPlayer.getPawn(), 20);
					gameBoard.repaint();
					freeParkingProcedure();
					break;
			}
		}
	}
	
	private void movePawnOn(Pawn pawn, int location, int direction) 
	{
		// direction : 1 --> forward
		// direction : -1 --> backward
		disableAllButtons();
		pawnPlacedState = false;
		TokenMover tokenMover = new TokenMover(this, pawn, gameTable, location, direction, gameBoard);
		Timer timer = new Timer(DELAY_PER_PAWN_MOVE, tokenMover);
		timer.setInitialDelay(15);
		timer.start();
	}
	
	public void pawnPlaced() 
	{
		recoverButtonStates();
		
		buildHouseButton.setEnabled(currentPlayer.canBuildHouse(gameTable));
		buildHotelButton.setEnabled(currentPlayer.canBuildHotel(gameTable));
		buyLandButton.setEnabled(currentPlayer.canBuy());
		pawnPlacedState = true;
	}
	
	private boolean[] getButtonStates() 
	{
		
		boolean[] states = new boolean[12];
		for (int i = 0; i < states.length; i++) 
		{
			
			states[i] = buttons[i].isEnabled();
			
		}
		
		return states;
		
	}
	
	private void disableAllButtons () 
	{
		lastButtonStates = getButtonStates();
		for(JButton b:buttons) 
		{
			b.setEnabled(false);
		}
		
	}
	
	private void recoverButtonStates () 
	{
		for (int i = 0; i < lastButtonStates.length; i++) 
		{
			buttons[i].setEnabled(lastButtonStates[i]);
		}
	}
	
	private void loseMoneyProcedure(double amount) 
	{
		
		boolean operation = currentPlayer.loseMoney(amount);
		if (operation) 
		{
			gameWindow.addTextToTextArea(currentPlayer + " lost $" + amount + "!\n");
		}
		else 
		{
			gameWindow.addTextToTextArea(currentPlayer + "does not have enough money to lose $" + amount + "!\n");
			playerGoesBankruptcy(currentPlayer);
		}
		updatePlayerTable();
		
	}
	
	private void freeParkingProcedure() 
	{
		FreeParking freeParking = getFreeParkingObject();
		double amount = (freeParking).getMoneyAmount();
		if (amount > 0.0) 
		{
			gameWindow.addTextToTextArea(currentPlayer + " earns $" + amount + " from Free Parking field.\n");
			freeParking.giveMoneyTo(currentPlayer);
			updatePlayerTable();
		}
		
		else 
		{
			gameWindow.addTextToTextArea(currentPlayer + " earns no money from Free Parking field\nsince there was no money accumulated in Free Parking field.\n");
		}
		
	}
	
	private FreeParking getFreeParkingObject() 
	{
		
		return (FreeParking)(gameTable.getSquare(20).getObject());
		
	}
	
	private void payTaxProcedure(double amount) 
	{
		boolean operation = currentPlayer.loseMoney(amount);
		FreeParking freeParking = getFreeParkingObject();
		if (operation) 
		{
			gameWindow.addTextToTextArea(currentPlayer + " paid $" + amount + " tax! $"+amount + " was put in Free Parking field.\n");
			freeParking.putMoneyIn(amount);
		}
		else 
		{
			gameWindow.addTextToTextArea(currentPlayer + " does not have enough money to pay $" + amount + " tax!\n");
			playerGoesBankruptcy(currentPlayer);
		}
		updatePlayerTable();
	}
	
	private void goToJailProcedure() 
	{
		gameWindow.addTextToTextArea("Bad luck! " + currentPlayer + " is going to jail!\n");
		gameTable.placePawnOn(currentPlayer.getPawn(), 10);
		currentPlayer.goToJail();
		gameBoard.repaint();
	}
	
	private void jailProcedure() 
	{
		gameWindow.addTextToTextArea("Bad luck! " + currentPlayer + " is now in jail!\n");
		currentPlayer.goToJail();
	}
	
	private void playerGoesBankruptcy(Player player) 
	{
		gameWindow.addTextToTextArea(player + " has gone bankruptcy and lost the game since they do not have enough money in their bank account!\n");
		player.loseTheGame();
		playersQueue.add(player);
		FreeParking freeParking = (FreeParking)(gameTable.getSquare(20).getObject());
		player.loseAllProperties(gameWindow, freeParking);
		updatePlayerTable();
	}
	
	private void playerGoesBankruptcy(Player loser, Player winner) 
	{
		gameWindow.addTextToTextArea(loser + " has gone bankruptcy and lost the game since they do not have enough money in their bank account!\n");
		loser.loseTheGame();
		playersQueue.add(loser);
		loser.giveAllPropertiesTo(winner, gameWindow);
		updatePlayerTable();
	}
	
	private void clearPawnsOfPlayersThatLost() 
	{
		
		for (Player p: playersQueue) 
		{
			p.getPawn().getSquareField().removePawn(p.getPawn());
			p.getPawn().hide();
		}
		
		playersQueue = new ArrayList<Player>();
		
	}
	
	public void finishTheGame() 
	{
		Player winner = getPlayerByID(determineTheWinner());
		if (winner == null) 
		{
			JOptionPane.showConfirmDialog(null, "There is no winner! Game has been ended.", "Game Over!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		else 
		{
			JOptionPane.showConfirmDialog(null, winner + " has won the game! Congratulations " + winner.getName() + "!", "Game Over!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		continueButton.setEnabled(false);
		finishTheGameButton.setEnabled(false);
		tradeButton.setEnabled(false);
		rollDiceButton.setEnabled(false);
		buildHouseButton.setEnabled(false);
		buildHotelButton.setEnabled(false);
		buyLandButton.setEnabled(false);
		saveGameButton.setEnabled(false);
		newGameButton.setEnabled(true);
		loadGameButton.setEnabled(true);
		
	}
	
	public void nextTurn() 
	{
		clearPawnsOfPlayersThatLost();
		gameBoard.repaint();
		currentTurn = determineTheNextPlayer();
		updatePlayerTable();
		if (currentTurn == -1) 
		{
			finishTheGame();
		}
		else 
		{
			currentPlayer = getPlayerByID(currentTurn);
			rollDiceState = true;
			diceRolledState = false;
			loop();
		}
		
	}
	
	public void rollDiceDetermineTheStarter () 
	{	
		
		dice[0].roll();
		dice[1].roll();
		animateDiceRoll(dice[0].getFace(), dice[1].getFace());

		int total = dice[0].getFace() + dice[1].getFace();
		firstRolledNumbers.add(total);
		
		gameWindow.addTextToTextArea(String.format("%s rolled %d!\n", playersToRoll.get(firstRollCounter), total));
		
		firstRollCounter++;
		
		if (firstRollCounter == playersToRoll.size()) 
		{
			int max = getMax(firstRolledNumbers);
			int count = countValue(max, firstRolledNumbers);
			
			if (count > 1) 
			{
				
				int [] indexes = getIndexesOfNumber(max, firstRolledNumbers);
				ArrayList<Player> maxPlayers = pickPlayers(indexes, playersToRoll);
				String message = "There are more than one players that have rolled the greatest number. These players are:\n";
				for (Player player : maxPlayers) 
				{
					message += player + "\n";
				}
				message += "These players will again roll dice.\n";
				gameWindow.addTextToTextArea(message);
				playersToRoll = maxPlayers;
				determineTheStarter();
			}
			
			else 
			{
				int [] indexes = getIndexesOfNumber(max, firstRolledNumbers);
				starter = pickPlayers(indexes, playersToRoll).get(0);
				gameWindow.addTextToTextArea(String.format("%s rolled the greatest number. %s will start. Congratulations %s!\n", starter, starter, starter.getName()));
				determineTheStarterState = false;
				finishTheGameButton.setEnabled(true);
				saveGameButton.setEnabled(true);
				tradeButton.setEnabled(true);
				currentTurn = starter.getID();
				currentPlayer = starter;
				
				rollDiceState = true;
				diceRolledState = false;
				loop();
			}
		}
		else 
		{
			
			gameWindow.addTextToTextArea(String.format("%s, please roll dice!\n", playersToRoll.get(firstRollCounter)));
			
		}
	}
	
	public int[] getDiceValues() 
	{
		
		int [] values = new int[2];
		
		for (int i = 0; i < 2; i++)
			values[i] = dice[i].getFace();
	
		return values;
		
	}
	
	public int getTotalDiceValue() 
	{
		
		int [] values = getDiceValues();
		return values[0] + values[1];
	}
	
	private void animateDiceRoll(int die1, int die2) 
	{
		
		RollDiceWindow rdw = new RollDiceWindow(gameWindow, true, die1, die2, dieFacePaths);
		rdw.startAnimating();
		
	}
	
	public void rollDice() 
	{
		try 
		{
			Clip rollDiceClip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(Paths.get(RESOURCES, DICE_SOUND).toFile().getPath()));
			rollDiceClip.open(ais);
			rollDiceClip.loop(0);
		} 
		catch (UnsupportedAudioFileException e) 
		{
			e.printStackTrace();
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (LineUnavailableException e) 
		{
			e.printStackTrace();
		}
		
		if (determineTheStarterState) 
		{
			rollDiceDetermineTheStarter();
		}
		else 
		{
			dice[0].roll();
			dice[1].roll();
			
			animateDiceRoll(dice[0].getFace(), dice[1].getFace());
			
			rollDiceState = false;
			diceRolledState = true;
			loop();
		}
		
	}
	
	private void determineTheStarter () 
	{
		
		firstRolledNumbers = new ArrayList<Integer>();
		firstRollCounter = 0;
		starter = null;
		determineTheStarterState = true;
		rollDiceButton.setEnabled(true);
		gameWindow.addTextToTextArea(String.format("%s, please roll dice!\n", playersToRoll.get(firstRollCounter)));
		
	}
	
	private String readFile(String path) 
	{
		boolean error = false;
		String content = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			int read = 0;
			do 
			{
				read = bufferedReader.read();
				if (read != -1) 
				{
					content += String.valueOf((char) read);
				}
			} while (read != -1); 
			bufferedReader.close();
		} catch (Exception e) 
		{
			System.out.println("Fatal error occurred: " + e.getMessage());
			error = true;
		}
		if (error) 
		{
			content = null;
		}
		return content;
	}
	
	private void buildTheGame() 
	{
		String content = readFile("squareLocations.txt");
		ArrayList<SquareField> squares = new ArrayList<SquareField>();
		String [] splitted = content.split("\n");
		int counter = 0;
		for (String s: splitted) 
		{
			String str_rightTop = s.split("\\|")[0].trim();
			String str_rightTopX = str_rightTop.split(",")[0].trim();
			String str_rightTopY = str_rightTop.split(",")[1].trim();
			str_rightTopX = str_rightTopX.substring(1);
			str_rightTopY = str_rightTopY.substring(0, str_rightTopY.length()-1);
			
			String str_leftBottom = s.split("\\|")[1].trim();
			String str_leftBottomX = str_leftBottom.split(",")[0].trim();
			String str_leftBottomY = str_leftBottom.split(",")[1].trim();
			str_leftBottomX = str_leftBottomX.substring(1);
			str_leftBottomY = str_leftBottomY.substring(0, str_leftBottomY.length()-1);
			
			int rightTopX = Integer.parseInt(str_rightTopX);
			int rightTopY = Integer.parseInt(str_rightTopY);
			int leftBottomX = Integer.parseInt(str_leftBottomX);
			int leftBottomY = Integer.parseInt(str_leftBottomY);
			
			Point rightTop = new Point(rightTopX, rightTopY);
			Point leftBottom = new Point(leftBottomX, leftBottomY);
			
			squares.add(new SquareField(counter, rightTop, leftBottom));
			counter++;
		}

		squares.get(0).setObject(new GameObject("start", "Starting Point", "This is the point where the game starts.", 0));
		squares.get(1).setObject(new Land("Antalya - Land", "Land in Antalya", 1, 450.0, 0, 30.0));
		squares.get(2).setObject(new Public("Train Station", "There are lots of trains passing by here.", 2, 900.0, 30));
		squares.get(3).setObject(new Land("Berlin - Land", "Land in Berlin", 3, 750.0, 0, 55.0));
		squares.get(4).setObject(new PayTax(4, 50));
		squares.get(5).setObject(new RollDice(5));
		squares.get(6).setObject(new Land("Singapore - Land", "Land in Singapore", 6, 1200.0, 1, 90));
		squares.get(7).setObject(new Chance(7));
		squares.get(8).setObject(new Land("London - Land", "Land in London", 8, 1500.0, 1, 130.0));
		squares.get(9).setObject(new Land("Paris - Land", "Land in Paris", 9, 900.0, 1, 80.0));
		squares.get(10).setObject(new Jail("Jail", "The player that comes here cannot make move for 2 next turns.", 10));
		squares.get(11).setObject(new Land("Los Angeles - Land", "Land in Los Angeles", 11, 1300.0, 2, 110.0));
		squares.get(12).setObject(new Public("Water Services", "The player that owns here wins $10 per turn.", 12, 180.0, 10.0));
		squares.get(13).setObject(new Land("Texas - Land", "Land in Texas", 13, 800.0, 2, 70.0));
		squares.get(14).setObject(new Land("Ankara - Land", "Land in Ankara", 14, 450.0, 2, 35.0));
		squares.get(15).setObject(new Public("Telecommunication Services", "The player that owns here wins $50 per turn.", 15, 500.0, 50.0));
		squares.get(16).setObject(new Land("Dubai - Land", "Land in Dubai", 16, 500.0, 3, 40.0));
		squares.get(17).setObject(new Chance(17));
		squares.get(18).setObject(new Land("New York - Land", "Land in New York", 18, 1000.0, 3, 90.0));
		squares.get(19).setObject(new Land("Istanbul - Land", "Land in Istanbul", 19, 500.0, 3, 40.0));
		squares.get(20).setObject(new FreeParking("Free Parking", "The player that comes here wins the money that was accumulated here.", 20));
		squares.get(21).setObject(new Land("Tokyo - Land", "Land in Tokyo", 21, 1300.0, 4, 105.0));
		squares.get(22).setObject(new Chance(22));
		squares.get(23).setObject(new Land("Dallas - Land", "Land in Dallas", 23, 2500.0, 4, 230.0));
		squares.get(24).setObject(new Land("Peru - Land", "Land in Peru", 24, 900.0, 4, 80.0));
		squares.get(25).setObject(new RollDice(25));
		squares.get(26).setObject(new Land("Rome - Land", "Land in Rome", 26, 1200.0, 5, 95.0));
		squares.get(27).setObject(new Land("Venice - Land", "Land in Venice", 27, 1200.0, 5, 100.0));
		squares.get(28).setObject(new RollDice(28));
		squares.get(29).setObject(new Land("Florence - Land", "Land in Florence", 29, 1400.0, 5, 110.0));
		squares.get(30).setObject(new GameObject("gotojail", "Go To Jail", "Whoever comes here goes to jail and stays in there for 2 next turns.", 30));
		squares.get(31).setObject(new Land("Washington - Land", "Land in Washington", 31, 1500.0, 6, 120.0));
		squares.get(32).setObject(new Land("Hong Kong - Land", "Land in Hong Kong", 32, 850.0, 6, 85.0));
		squares.get(33).setObject(new LoseMoney(33, 50));
		squares.get(34).setObject(new Land("Izmir - Land", "Land in Izmir", 34, 1000.0, 6, 110.0));
		squares.get(35).setObject(new GameObject("gotostart", "Go to Starting Point", "Whoever comes here goes to Starting Point.", 35));
		squares.get(36).setObject(new Chance(36));
		squares.get(37).setObject(new Land("San Francisco - Land", "Land in San Francisco", 37, 1900.0, 7, 190.0));
		squares.get(38).setObject(new PayTax(38, 75));
		squares.get(39).setObject(new Land("Chicago - Land", "Land in Chicago", 39, 2000.0, 7, 200.0));
		
		gameTable = new GameTable(squares);
		
		cards = new ArrayList<Card>();
		cards.add(new Card("Bad Luck!", "Go To Jail!"));
		cards.add(new Card("Luxury Tax", "Pay 10% of your money as tax."));
		cards.add(new GiftCard());
		cards.add(new MoveCard());
		cards.add(new Card("A Generous Gift To Your Friend(s)!", "Pay $100 to the player that has the greatest amount of money.\nIf there are more than one player satisfying this condition, $100 will be distributed to them equally!\nIf you are only one that has the greatest amount of money,\nthen you will pay to those who have the greatest amount of money after you!\nIf you do not have enough money, you will lose the game and your properties will be unowned."));
		cards.add(new Card("Money, Money, Money!", "Collect all money accumulated in the FreeParking field."));
		cards.add(new LoseCard());
		cards.add(new Card("A Greedy Visit To Free Parking", "Go to Free Parking field and collect all money accumulated."));
		
		pickedCards = new ArrayList<Card>();
		
	}
	
	private Card pickCard() 
	{
		if (cards.size() == 0) 
		{
			for(int i = 0; i < pickedCards.size(); i++) 
			{
				cards.add(pickedCards.get(i));
			}
			pickedCards = new ArrayList<Card>();
		}
		
		Random generator = new Random();
		int indexNum = generator.nextInt(cards.size());
		Card picked = cards.get(indexNum);
		
		pickedCards.add(picked);
		cards.remove(indexNum);
		
		return picked;
	}
	
	public void loadGame(String gameName) 
	{
		String fileName = gameName + "." + SAVE_EXT;
		
		Path p = Paths.get(SAVES_FOLDER_NAME, fileName);
		String path = p.toFile().getPath();
		
		try 
		{
			FileInputStream f = new FileInputStream(new File(path));
			ObjectInputStream o = new ObjectInputStream(f);
			
			ArrayList<Player> players = (ArrayList<Player>) (o.readObject());
			int currentTurn = (int) (o.readObject());
			Player currentPlayer = (Player) (o.readObject());
			GameTable gameTable = (GameTable) (o.readObject());
			ArrayList<Card> cards = (ArrayList<Card>) (o.readObject());
			ArrayList<Card> pickedCards = (ArrayList<Card>) (o.readObject());
			boolean rollDiceState = (boolean) (o.readObject());
			boolean diceRolledState = (boolean) (o.readObject());
			boolean chanceCardMoveState = (boolean) (o.readObject());
			boolean[] buttonStates = (boolean[]) (o.readObject());
			
			this.players = players;
			this.currentTurn = currentTurn;
			this.currentPlayer = currentPlayer;
			this.gameTable = gameTable;
			this.cards = cards;
			this.pickedCards = pickedCards;
			this.rollDiceState = rollDiceState;
			this.diceRolledState = diceRolledState;
			this.chanceCardMoveState = chanceCardMoveState;
			
			for (int i = 0; i < buttons.length; i++) 
			{
				
				buttons[i].setEnabled(buttonStates[i]);
				
			}
			updatePlayerTable();
			gameBoard.repaint();
			
			JOptionPane.showConfirmDialog(null, String.format("The game '%s' has been successfully loaded!", gameName), "Game Loaded!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			gameWindow.addTextToTextArea("Game Loaded: " + gameName + "\n");

		}
		catch (IOException | ClassNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, String.format("An error occurred while loading the game %s!", gameName), "Fatal error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.out.println("An unexpected error has occurred.");
		}

	}
	
	public void loadGameClicked() 
	{
		boolean cont = checkSaveFolder();
		if (cont) 
		{
			
			LoadGameWindow loadGameWindow = new LoadGameWindow(this, SAVES_FOLDER_NAME, SAVE_EXT);
			loadGameWindow.setVisible(true);
		
		}

	}
	
	public void newGameClicked() 
	{
		
		mainTheme.stop();
		
		gameWindow.setVisible(false);
		gameWindow.dispose();
		
		players = null;
		cards = null;
		pickedCards = null;
		dice = null;
		gameWindow = null;
		currentPlayer = null;
		playersToRoll = null;
		starter = null;
		gameTable = null;
		gameBoard =null;
		playerTable = null;
		playerTableModel = null;
		
		Monopoly.firstInteraction();
		
	}
	
	private boolean checkSaveFolder () 
	{
		
		boolean cont = true;
		if (!Files.exists(Paths.get(SAVES_FOLDER_NAME)))		
		{
			File savesFolder = new File(SAVES_FOLDER_NAME);
			savesFolder.mkdir();
		}
		else 
		{
			File savesFolder = new File(SAVES_FOLDER_NAME);
			if (!savesFolder.isDirectory()) 
			{
				cont = false;
				JOptionPane.showMessageDialog(null, "The saves folder path is occupied by a non-folder object! To solve this problem, please rename the file named " + SAVES_FOLDER_NAME + ".", "Fatal error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return cont;
		
	}
	
	public void saveGameClicked() 
	{
		boolean cont = checkSaveFolder();
		
		if (cont)
		{
		
			String name;
			Path p;
			name = JOptionPane.showInputDialog(null, "Save name:", "Save", JOptionPane.QUESTION_MESSAGE);
			p = Paths.get(SAVES_FOLDER_NAME, name + "." + SAVE_EXT);
			while (Files.exists(p)) 
			{
				JOptionPane.showMessageDialog(null, "There is already a save named " + name, "Try another name!", JOptionPane.ERROR_MESSAGE);
				name = JOptionPane.showInputDialog(null, "Save name:", "Save", JOptionPane.QUESTION_MESSAGE);
				p = Paths.get(SAVES_FOLDER_NAME, name + "." + SAVE_EXT);
			}
			String path = p.toFile().getPath();
			FileOutputStream f;
			ObjectOutputStream o;
			try 
			{
				f = new FileOutputStream(new File(path));
				o = new ObjectOutputStream(f);
				o.writeObject(players);
				o.writeObject(currentTurn);
				o.writeObject(currentPlayer);
				o.writeObject(gameTable);
				o.writeObject(cards);
				o.writeObject(pickedCards);
				o.writeObject(rollDiceState);
				o.writeObject(diceRolledState);
				o.writeObject(chanceCardMoveState);
				boolean[] buttonStates = new boolean[12];
				for (int i = 0; i < buttons.length; i++) 
				{
					buttonStates[i] = buttons[i].isEnabled();
				}
				o.writeObject(buttonStates);
				
				o.close();
	            f.close();
				
			} 
			
			catch (FileNotFoundException e2) 
			{
				e2.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	public void buyClicked() 
	{
		currentPlayer.buy();
		buyLandButton.setEnabled(currentPlayer.canBuy());
		buildHouseButton.setEnabled(currentPlayer.canBuildHouse(gameTable));
		buildHotelButton.setEnabled(currentPlayer.canBuildHotel(gameTable));
		updatePlayerTable();
		gameWindow.addTextToTextArea(currentPlayer + " bought " + currentPlayer.getPawn().getSquareField().getObject().getTitle() + "\n");
	}
	
	public void buildHouseClicked() 
	{
		currentPlayer.buildHouse();
		buildHouseButton.setEnabled(currentPlayer.canBuildHouse(gameTable));
		buildHotelButton.setEnabled(currentPlayer.canBuildHotel(gameTable));
		updatePlayerTable();
		ArrayList<House> housesOfCurrentPlayer = currentPlayer.getHouses();
		gameWindow.addTextToTextArea(currentPlayer + " built a " + housesOfCurrentPlayer.get(housesOfCurrentPlayer.size() - 1) + "\n");
	}
	
	public void buildHotelClicked() 
	{
		currentPlayer.buildHotel();
		buildHouseButton.setEnabled(currentPlayer.canBuildHouse(gameTable));
		buildHotelButton.setEnabled(currentPlayer.canBuildHotel(gameTable));
		updatePlayerTable();
		ArrayList<Hotel> hotelsOfCurrentPlayer = currentPlayer.getHotels();
		gameWindow.addTextToTextArea(currentPlayer + " built a " + hotelsOfCurrentPlayer.get(hotelsOfCurrentPlayer.size() - 1) + "\n");
	}
	
	public void listProperties() 
	{
		InformationWindow info = new InformationWindow();
    	info.setResizable(false);
    	info.setTitle("Properties of Players");
    	for (Player p: players) 
    	{
    		if (p.isInGame()) 
    		{
    			info.addTextToTextArea(p.getPropertyInformation() + "\n");
    		}
    	}
    	info.pack();
    	info.setVisible(true);
	}
	
	public void tradeClicked() 
	{
		TradeWindow tradeWindow = new TradeWindow(this);
		tradeWindow.setTitle("Monopoly - Trade");
		tradeWindow.setVisible(true);
		tradeWindow.pack();
	}
	
	public void addMessageToGameWindow(String text) 
	{
		gameWindow.addTextToTextArea(text);
	}
	
	private boolean isGameOver() 
	{
		int count = 0;
		for (Player p: players) 
		{
			if (p.isInGame()) count++;
		}
		if (count > 1) 
		{
			return false;
		}
		else 
		{
			return true;	
		}	
	}
	
	public int determineTheNextPlayer() 
	{
		int nextTurn = 0;
		if (isGameOver()) 
		{
			nextTurn = -1;
		}
		else
		{
			int counter = currentTurn-1;
			boolean determined = false;
			while (!determined) 
			{
				counter++;
				if (counter >= players.size()) counter = 0;
				Player p = players.get(counter);
				if (p.isInGame()) 
				{
					if (!p.isInJail()) 
					{
						determined = true;
						nextTurn = p.getID();
					}
					else 
					{
						gameWindow.addTextToTextArea(p + " cannot make move for this turn since they are in jail!\n");
					}
					p.doTasksPerTurn(gameWindow);
				}
			}
		}
		return nextTurn;
	
	}
	private Player getPlayerByID(int ID) 
	{
		Player p = null;
		for (Player player: players) 
			if (player.getID() == ID)  
				p = player;
		return p;
	}
	
	public void playMoneySound() 
	{
		try 
		{
			Clip moneySound = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(Paths.get(RESOURCES, MONEY_SOUND).toFile().getPath()));
			moneySound.open(ais);
			moneySound.loop(0);
		}
		
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
	
	public ArrayList<Player> getPlayersInGame() 
	{
		ArrayList<Player> playersInGame = new ArrayList<Player>();
		
		for (Player p : players) 
		{
			if (p.isInGame()) 
			{
				playersInGame.add(p);
			}
		}
		
		return playersInGame;
	}
	
	public int determineTheWinner() 
	{
		int winner = 0;
		if (!isGameOver()) 
		{
			ArrayList<Player> playersInGame = getPlayersInGame();
			double[] moneyAmounts = new double[playersInGame.size()];
			for (int i = 0; i < playersInGame.size(); i++) 
			{
				moneyAmounts[i] = playersInGame.get(i).getBankAccount().getMoney();
			}
			
			double maxAmount = this.getMax(moneyAmounts);
			ArrayList<Double> mAmounts = new ArrayList<Double>();
			for (int i = 0; i < moneyAmounts.length; i++) 
			{
				mAmounts.add(moneyAmounts[i]);
			}
			int [] indexes = this.getIndexesOfNumber(maxAmount, mAmounts);
			if (indexes.length != 1) 
			{
				winner = -1;
			}
			else 
			{
				winner = playersInGame.get(indexes[0]).getID();
			}
		}
		else 
		{
			for (Player p : players) 
			{
				if (p.isInGame()) 
				{
					winner = p.getID();
				}
			}
		}
		return winner;
	}
	
	public ArrayList<Player> getPlayers() 
	{
		return players;
	}
}
