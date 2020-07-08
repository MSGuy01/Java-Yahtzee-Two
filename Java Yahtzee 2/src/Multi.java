import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Multi{
    static String[] defaultPlayerNames = {"player one", "player two", "player three", "player four"};
    static int[][] defaultScorecardSaver = {{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}, {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}, {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}, {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}};
    public static void main(String[] args) {
        new MultiFrame(defaultPlayerNames, 2, 1, 0, defaultScorecardSaver);
    }
}

class MultiFrame extends JFrame{

    //objects:
    Random random = new Random();
    Checker checker = new Checker();

    //integers:
    int[] randomRolls = {0, 0, 0, 0, 0};
    int rollNum = 0;

    //Strings:
    String[] itemNames = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three Of A Kind", "Four Of A Kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance", "Upper Bonus", "Upper Total", "Lower Total", "Total"};

    //booleans:
    boolean[] randomRollsBoolean = {true, true, true, true, true};

    //Action Listener:
    ButtonListener b = new ButtonListener();

    //images:
    ImageIcon icon = new ImageIcon("icon.png");

    //JPanels:
    JPanel titlePanel = new JPanel();
    //Roll number label:
    JPanel rollNumPanel = new JPanel();
    //Die labels:
    JPanel rollLabelPanel = new JPanel();
    //Die number labels:
    JPanel diceLabelPanel = new JPanel();
    //Roll dice and score buttons:
    JPanel rollPanel = new JPanel();
    //Score selection and back to menu:
    JPanel scorePanel = new JPanel();
    //Roll/don't roll dice buttons:
    JPanel diceSelectPanel = new JPanel();
    //placement panels:
    JPanel firstPlacePanel = new JPanel();
    JPanel secondPlacePanel = new JPanel();
    JPanel thirdPlacePanel = new JPanel();
    JPanel fourthPlacePanel = new JPanel();
    JPanel[] placePanels = {firstPlacePanel, secondPlacePanel, thirdPlacePanel, fourthPlacePanel};


    //JLabels:
    JLabel title = new JLabel("JAVA YAHTZEE 2!!");
    JLabel rollLabel = new JLabel("DIE1  DIE2  DIE3  DIE4  DIE5");
    JLabel diceLabel1 = new JLabel("");
    JLabel diceLabel2 = new JLabel("");
    JLabel diceLabel3 = new JLabel("");
    JLabel diceLabel4 = new JLabel("");
    JLabel diceLabel5 = new JLabel("");
    JLabel rollNumLabel = new JLabel("Roll " + rollNum);
    JLabel firstPlace = new JLabel("FIRST PLACE: ");
    JLabel secondPlace = new JLabel("SECOND PLACE: ");
    JLabel thirdPlace = new JLabel("THIRD PLACE: ");
    JLabel fourthPlace = new JLabel("FOURTH PLACE: ");
    JLabel[] placeLabels = {firstPlace, secondPlace, thirdPlace, fourthPlace};
    JLabel[] diceLabels = {diceLabel1, diceLabel2, diceLabel3, diceLabel4, diceLabel5};
    JLabel[] diceLabelsRandom = {diceLabel1, diceLabel2, diceLabel3, diceLabel4, diceLabel5};

    //game jbuttons:
    JButton rollDice = new JButton("Roll Dice");
    JButton die1 = new JButton("Roll Die One");
    JButton die2 = new JButton("Roll Die Two");
    JButton die3 = new JButton("Roll Die Three");
    JButton die4 = new JButton("Roll Die Four");
    JButton die5 = new JButton("Roll Die Five");
    JButton scoreButton = new JButton("Score Your Roll");
    JButton scoreThisButton = new JButton("Set Score as Selection");
    JButton next = new JButton("Next");
    JButton menu = new JButton("Back to Menu");
    JButton[] diceButtons = {die1, die2, die3, die4, die5};


    //combo boxes:
    JComboBox<String> scoresCombo = new JComboBox<>();

    //Tables:
    JTable scorecardTable;

    //fonts:
    Font titleFont = new Font("Courier", Font.BOLD, 50);
    Font authFont = new Font("Courier", Font.BOLD, 30);
    Font statusFont = new Font("Courier", Font.PLAIN, 20);

    //scorecards:
    public int[] playerOneScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public int[] playerTwoScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public int[] playerThreeScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public int[] playerFourScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public int[][] scorecards = {playerOneScorecard, playerTwoScorecard, playerThreeScorecard, playerFourScorecard};

    String[] playerNames;
    int players;
    int turn;
    int turnsDone;
    public MultiFrame(String[] playerNames, int players, int turn, int turnsDone, int[][] scorecardSaver) {
        for (int j = 0; j < scorecardSaver.length; j++) {
            for (int i = 0; i < scorecardSaver[j].length; i++) {
                scorecards[j][i] = scorecardSaver[j][i];
            }
        }
        this.turnsDone = turnsDone;
        this.playerNames = playerNames;
        this.players = players;
        this.turn = turn;
        for (int i = 0; i < playerNames.length; i++) {
            System.out.println(playerNames[i]);
        }
        System.out.println("players: " + players);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
        setSize(1000, 1000);
        setTitle("Java Yahtzee 2 by MSGuy01");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());
        System.out.println("TURNS DONE: " + turnsDone);
        if (turnsDone != players * 13) {
            String[] column = {"SCORE TYPE", "SCORE"};
            String[][] data = {{"Ones", "0"},
                    {"Twos", "0"},
                    {"Threes", "0"},
                    {"Fours", "0"},
                    {"Fives", "0"},
                    {"Sixes", "0"},
                    {"Three Of A Kind", "0"},
                    {"Four Of A Kind", "0"},
                    {"Full House", "0"},
                    {"Small Straight", "0"},
                    {"Large Straight", "0"},
                    {"Yahtzee", "0"},
                    {"Chance", "0"},
                    {"Yahtzee Bonus", "0"}};
            scorecardTable = new JTable(data, column);
            scorecardTable.setDefaultEditor(Object.class, null);
            for (int i = 0; i < scorecards[turn - 1].length; i++) {
                if (scorecards[turn - 1][i] == 100) {
                    scoresCombo.addItem(itemNames[i]);
                } else {
                    scoresCombo.addItem("");
                }
            }

            title.setFont(titleFont);
            title.setText(playerNames[turn - 1] + "'S TURN");
            titlePanel.add(title);
            add(titlePanel);

            initializeButton(rollDice, "rollDice");

            initializeButton(scoreButton, "score");
            initializeButton(scoreThisButton, "scoreThis");

            rollNumLabel.setFont(statusFont);
            rollNumPanel.add(rollNumLabel);
            add(rollNumPanel);

            rollLabel.setFont(authFont);
            rollLabelPanel.add(rollLabel);
            add(rollLabelPanel);

            for (int i = 0; i < diceLabels.length; i++) {
                diceLabels[i].setFont(authFont);
                diceLabels[i].setForeground(Color.black);
                diceLabelPanel.add(diceLabels[i]);
            }
            add(diceLabelPanel);

            rollPanel.add(rollDice);
            add(rollPanel);

            for (int i = 0; i < diceButtons.length; i++) {
                diceSelectPanel.add(diceButtons[i]);
                initializeButton(diceButtons[i], "die" + (i + 1));
            }

            add(scorePanel);

            initializeButton(next, "next");
        }
        else{
            int[] notOrdered = {0, 0, 0, 0};
            int[] finalScores = {0, 0, 0, 0};
            String[] bestPlayers = {"", "", "", ""};
            for (int i = 0; i < players; i++) {
                for (int j = 0; j < scorecards[i].length; j++) {
                    if (scorecards[i][j] != 100) {
                        System.out.println("THIS SCORECARD VALUE: " + scorecards[i][j]);
                        notOrdered[i] += scorecards[i][j];
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                finalScores[i] = notOrdered[findGreatest(notOrdered)];
                bestPlayers[i] = playerNames[findGreatest(notOrdered)];
                notOrdered[findGreatest(notOrdered)] = 0;
            }
            for (int i = 0; i < finalScores.length; i++) {
                System.out.println("FINAL SCORE ONE: " + finalScores[i]);
                System.out.println((i + 1) + " PLACE: " + bestPlayers[i]);
            }
            for (int i = 0; i < players; i++) {
                placeLabels[i].setForeground(Color.green);
                placeLabels[i].setFont(titleFont);
                placeLabels[i].setText(placeLabels[i].getText() + bestPlayers[i] + " [" + finalScores[i] + " points]");
                placePanels[i].add(placeLabels[i]);
                add(placePanels[i]);
            }
            initializeButton(menu, "menu");
            scorePanel.add(menu);
            add(scorePanel);
        }
        pack();
        setVisible(true);
    }
    int findGreatest(int[] data) {
        int greatest = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= data[greatest]) {
                greatest = i;
            }
        }
        return greatest;
    }
    void initializeButton(JButton button, String actionCommand) {
        button.addActionListener(b);
        button.setActionCommand(actionCommand);
    }

    //FINISH SETTING UP TOTALS ON SCORECARD AND UPPER BONUS:

    void printScorecard() {
        String[] column = {"SCORE TYPE", "SCORE"};
        String[][] data = {{"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}};
        System.out.println("print scorecard");
        int upperTotal = 0;
        for (int j = 0; j < 6; j++) {
            System.out.println(scorecards[turn-1][j]);
            if (scorecards[turn - 1][j] != 100) {
                upperTotal += scorecards[turn - 1][j];
            }
        }
        for (int i = 0; i < itemNames.length; i++) {
            data[i][0] = itemNames[i];
            switch(itemNames[i]) {
                case "Upper Total":
                    data[i][1] = Integer.toString(upperTotal);
                    break;
                case "Upper Bonus":
                    System.out.println(upperTotal);
                    if (upperTotal >= 63) {
                        scorecards[turn - 1][13] = 35;
                        data[i][1] = "35";
                    }
                    else{
                        scorecards[turn - 1][13] = 0;
                        data[i][1] = "0";
                    }
                    break;
                case "Lower Total":
                    int lowerTotal = 0;
                    for (int j = 6; j < scorecards[turn - 1].length - 1; j++) {
                        System.out.println(scorecards[turn-1][j]);
                        if (scorecards[turn - 1][j] != 100) {
                            lowerTotal += scorecards[turn - 1][j];
                        }
                    }
                    data[i][1] = Integer.toString(lowerTotal);
                    break;
                case "Total":
                    int total = 0;
                    for (int j = 0; j < scorecards[turn - 1].length; j++) {
                        System.out.println(scorecards[turn-1][j]);
                        if (scorecards[turn - 1][j] != 100 && ! itemNames[j].equals("Upper Total") && ! itemNames[j].equals("Lower Total")) {
                            total += scorecards[turn - 1][j];
                        }
                    }
                    data[i][1] = Integer.toString(total);
                    break;
                default:
                    if (scorecards[turn - 1][i] != 100) {
                        data[i][1] = Integer.toString(scorecards[turn - 1][i]);
                    } else {
                        data[i][1] = "Unscored";
                    }
                    break;
            }
        }
        scorecardTable = new JTable(data, column);
        scorecardTable.setDefaultEditor(Object.class, null);
        rollLabelPanel.add(scorecardTable);
    }

    class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "rollDice":
                    boolean canRoll = false;
                    int currentRandom;
                    if (rollNum < 3) {
                        if (rollNum == 0) {
                            add(diceSelectPanel);
                            rollPanel.add(scoreButton);
                        }
                        for (int i = 0; i < randomRollsBoolean.length; i++) {
                            if (randomRollsBoolean[i]) {
                                canRoll = true;
                                break;
                            }
                        }
                        if (canRoll) {
                            rollNum++;
                            rollNumLabel.setForeground(Color.black);
                            rollNumLabel.setText("Roll " + rollNum);
                            for (int i = 0; i < diceLabelsRandom.length; i++) {
                                if (randomRollsBoolean[i]) {
                                    currentRandom = random.nextInt(6) + 1;
                                    diceLabelsRandom[i].setText(currentRandom + "      ");
                                    randomRolls[i] = currentRandom;
                                }
                            }
                            for (int i = 0; i < diceLabels.length; i++) {
                                diceLabels[i].setForeground(Color.black);
                            }
                            for (int i = 0; i < diceButtons.length; i++) {
                                diceButtons[i].setText("Roll Die " + (i + 1));
                            }
                            for (int i = 0; i < randomRollsBoolean.length; i++) {
                                randomRollsBoolean[i] = false;
                            }
                            if (rollNum == 3) {
                                for (int i = 0; i < diceButtons.length; i++) {
                                    diceSelectPanel.remove(diceButtons[i]);
                                }
                                rollPanel.remove(rollDice);
                                rollNumLabel.setForeground(Color.black);
                                rollNumLabel.setText("Score");
                            }
                        } else {
                            rollNumLabel.setForeground(Color.red);
                            rollNumLabel.setText("Please Select Dice to Roll");
                        }
                    }
                    break;
                case "die1":
                    if (diceLabel1.getForeground() == Color.black) {
                        diceLabel1.setForeground(Color.red);
                        die1.setText("Don't Roll Die 1");
                        randomRollsBoolean[0] = true;
                    } else {
                        diceLabel1.setForeground(Color.black);
                        die1.setText("Roll Die 1");
                        randomRollsBoolean[0] = false;
                    }
                    break;
                case "die2":
                    if (diceLabel2.getForeground() == Color.black) {
                        diceLabel2.setForeground(Color.red);
                        die2.setText("Don't Roll Die 2");
                        randomRollsBoolean[1] = true;
                    } else {
                        diceLabel2.setForeground(Color.black);
                        die2.setText("Roll Die 2");
                        randomRollsBoolean[1] = false;
                    }
                    break;
                case "die3":
                    if (diceLabel3.getForeground() == Color.black) {
                        diceLabel3.setForeground(Color.red);
                        die3.setText("Don't Roll Die 3");
                        randomRollsBoolean[2] = true;
                    } else {
                        diceLabel3.setForeground(Color.black);
                        die3.setText("Roll Die 3");
                        randomRollsBoolean[2] = false;
                    }
                    break;
                case "die4":
                    if (diceLabel4.getForeground() == Color.black) {
                        diceLabel4.setForeground(Color.red);
                        die4.setText("Don't Roll Die 4");
                        randomRollsBoolean[3] = true;
                    } else {
                        diceLabel4.setForeground(Color.black);
                        die4.setText("Roll Die 4");
                        randomRollsBoolean[3] = false;
                    }
                    break;
                case "die5":
                    if (diceLabel5.getForeground() == Color.black) {
                        diceLabel5.setForeground(Color.red);
                        die5.setText("Don't Roll Die 5");
                        randomRollsBoolean[4] = true;
                    } else {
                        diceLabel5.setForeground(Color.black);
                        die5.setText("Roll Die 5");
                        randomRollsBoolean[4] = false;
                    }
                    break;
                case "score":
                    if (scoreButton.getText().equals("Score Your Roll")) {
                        for (int i = 0; i < diceButtons.length; i++) {
                            diceSelectPanel.remove(diceButtons[i]);
                        }
                        rollPanel.remove(rollDice);
                        scorePanel.add(scoresCombo);
                        scorePanel.add(scoreThisButton);
                        scoreButton.setText("Hide Score Selection");
                    } else {
                        if (rollNum <= 2) {
                            System.out.println("rfanf awe  " + rollNum);
                            rollPanel.add(rollDice);
                            for (int i = 0; i < diceButtons.length; i++) {
                                diceSelectPanel.add(diceButtons[i]);
                            }
                        }
                        scorePanel.remove(scoresCombo);
                        scorePanel.remove(scoreThisButton);
                        scoreButton.setText("Score Your Roll");
                    }
                    break;
                case "scoreThis":
                    rollNumLabel.setForeground(Color.black);
                    rollNumLabel.setText("You scored " + checker.check(scoresCombo.getSelectedIndex() + 1, randomRolls) + " on " + scoresCombo.getSelectedItem() + "!");
                    if (scorecards[turn - 1][scoresCombo.getSelectedIndex()] == 100) {
                        scorecards[turn - 1][scoresCombo.getSelectedIndex()] = checker.check(scoresCombo.getSelectedIndex() + 1, randomRolls);
                        scorePanel.remove(scoresCombo);
                        scorePanel.remove(scoreThisButton);
                        rollPanel.remove(scoreButton);
                        rollPanel.remove(rollDice);
                        rollLabelPanel.remove(rollLabel);
                        for (int i = 0; i < diceLabels.length; i++) {
                            diceLabelPanel.remove(diceLabels[i]);
                        }

                        //WORK ON THIS
                        printScorecard();
                        rollNumPanel.add(next);
                    }
                    else{
                        rollNumLabel.setForeground(Color.red);
                        rollNumLabel.setText("Please select a valid option.");
                    }
                    break;
                case "next":
                    System.out.println("NEXT");
                    setVisible(false);
                    if (turn != players) {
                        new MultiFrame(playerNames, players, turn + 1, turnsDone + 1, scorecards);
                    }
                    else{
                        new MultiFrame(playerNames, players, 1, turnsDone + 1, scorecards);
                    }
                    break;
                case "menu":
                    new MenuFrame();
                    break;
            }
        }
    }
}