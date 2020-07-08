import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;



public class Player {
    public int hi = 54;
    static int[] defaultScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public static void main(String[] args) {
        new PlayerFrame("PLAYER ONE", defaultScorecard, defaultScorecard, false);
    }
}

class PlayerFrame extends JFrame {
    //public int[] scores = {0, 0};
    String playerName;
    //last spot is for upper bonus
    public int[] thisPlayerScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public int[] thisComputerScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    //objects:
    Random random = new Random();
    Checker checker = new Checker();

    //integers:
    int[] randomRolls = {0, 0, 0, 0, 0};
    int rollNum = 0;

    //booleans:
    boolean[] randomRollsBoolean = {true, true, true, true, true};

    //action listeners:
    ButtonListener b = new ButtonListener();

    //images:
    ImageIcon icon = new ImageIcon("icon.png");

    //game jpanels [IN ORDER AS APPEARS ONSCREEN]:
    //Roll number label:
    JPanel rollNumPanel = new JPanel();
    //Die labels:
    JPanel rollLabelPanel = new JPanel();
    //Die number labels:
    JPanel diceLabelPanel = new JPanel();
    //Roll dice and score buttons:
    JPanel rollPanel = new JPanel();
    //Score selection:
    JPanel scorePanel = new JPanel();
    //Roll/don't roll dice buttons:
    JPanel diceSelectPanel = new JPanel();
    JPanel titlePanel = new JPanel();

    //menu jlabels:
    JLabel title = new JLabel("JAVA YAHTZEE 2!!");

    //game jlabels:
    JLabel rollLabel = new JLabel("DIE1  DIE2  DIE3  DIE4  DIE5");
    JLabel diceLabel1 = new JLabel("");
    JLabel diceLabel2 = new JLabel("");
    JLabel diceLabel3 = new JLabel("");
    JLabel diceLabel4 = new JLabel("");
    JLabel diceLabel5 = new JLabel("");
    JLabel rollNumLabel = new JLabel("Roll " + rollNum);
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
    JButton[] diceButtons = {die1, die2, die3, die4, die5};
    JButton menu = new JButton("Back to Menu");


    //combo boxes:
    JComboBox<String> scoresCombo = new JComboBox<>();

    //Tables:
    JTable scorecardTable;

    //fonts:
    Font titleFont = new Font("Courier", Font.BOLD, 50);
    Font authFont = new Font("Courier", Font.BOLD, 30);
    Font statusFont = new Font("Courier", Font.PLAIN, 20);
    public PlayerFrame(String playerName, int[] playerScorecard, int[] computerScorecard, boolean over){
            this.playerName = playerName;
            for (int i = 0; i < playerScorecard.length; i++) {
                thisPlayerScorecard[i] = playerScorecard[i];
            }
            for (int i = 0; i < computerScorecard.length; i++) {
                thisComputerScorecard[i] = computerScorecard[i];
            }
            setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
            setSize(1000, 1000);
            setTitle("Java Yahtzee 2 by MSGuy01");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setIconImage(icon.getImage());
        if (! over) {
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
            String[] itemNames = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three Of A Kind", "Four Of A Kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance", "Upper Bonus", "Upper Total", "Lower Total", "Total"};
            boolean lastTurn = true;
            for (int i = 0; i < thisPlayerScorecard.length; i++) {
                if (thisPlayerScorecard[i] == 100) {
                    scoresCombo.addItem(itemNames[i]);
                    lastTurn = false;
                } else {
                    scoresCombo.addItem("");
                }
            }
            if (lastTurn) {
                setVisible(false);
                new PlayerFrame(playerName, playerScorecard, computerScorecard, true);
            }
            else {
                title.setFont(titleFont);
                title.setText(playerName + "'S TURN");
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
                pack();
                setVisible(true);
            }
        }
        //OVER:
        else{
            int playerTotal = 0;
            int computerTotal = 0;
            for (int i = 0; i < thisComputerScorecard.length; i++) {
                playerTotal += thisPlayerScorecard[i];
                computerTotal += thisComputerScorecard[i];
            }
            if (playerTotal > computerTotal) {
                title.setText("YOU WON!");
                title.setForeground(Color.green);
            }
            else if (playerTotal < computerTotal) {
                title.setText("YOU LOST...");
                title.setForeground(Color.red);
            }
            else{
                title.setText("YOU TIED");
                title.setForeground(Color.yellow);
            }
            title.setFont(new Font("COURIER", Font.BOLD, 50));
            titlePanel.add(title);
            add(titlePanel);

            rollLabel.setText("YOUR SCORE: " + playerTotal + " | COMPUTER SCORE: " + computerTotal);
            rollLabel.setFont(new Font("COURIER", Font.BOLD, 20));
            rollPanel.add(rollLabel);
            add(rollPanel);

            initializeButton(menu, "menu");
            scorePanel.add(menu);
            add(scorePanel);

            pack();
            setVisible(true);
        }
    }

    void initializeButton(JButton button, String actionCommand) {
        button.addActionListener(b);
        button.setActionCommand(actionCommand);
    }

    void printScorecard() {
        String[] column = {"SCORE TYPE", "SCORE"};
        String[] itemNames = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three Of A Kind", "Four Of A Kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance", "Upper Bonus", "Upper Total", "Lower Total", "Total"};
        String[][] data = {{"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}};
        System.out.println("print scorecard");
        int upperTotal = 0;
        for (int j = 0; j < 6; j++) {
            System.out.println(thisPlayerScorecard[j]);
            if (thisPlayerScorecard[j] != 100) {
                upperTotal += thisPlayerScorecard[j];
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
                        thisPlayerScorecard[13] = 35;
                        data[i][1] = "35";
                    }
                    else{
                        thisPlayerScorecard[13] = 0;
                        data[i][1] = "0";
                    }
                    break;
                case "Lower Total":
                    int lowerTotal = 0;
                    for (int j = 6; j < thisPlayerScorecard.length - 1; j++) {
                        if (thisPlayerScorecard[j] != 100) {
                            lowerTotal += thisPlayerScorecard[j];
                        }
                    }
                    data[i][1] = Integer.toString(lowerTotal);
                    break;
                case "Total":
                    int total = 0;
                    for (int j = 0; j < thisPlayerScorecard.length; j++) {
                        System.out.println(thisPlayerScorecard[j]);
                        if (thisPlayerScorecard[j] != 100 && ! itemNames[j].equals("Upper Total") && ! itemNames[j].equals("Lower Total")) {
                            total += thisPlayerScorecard[j];
                        }
                    }
                    data[i][1] = Integer.toString(total);
                    break;
                default:
                    if (thisPlayerScorecard[i] != 100) {
                        data[i][1] = Integer.toString(thisPlayerScorecard[i]);
                    }
                    else{
                        data[i][1] = "Unscored";
                    }
                    break;
            }
        }
        scorecardTable = new JTable(data, column);
        scorecardTable.setDefaultEditor(Object.class, null);
        rollLabelPanel.add(scorecardTable);
    }
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
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
                    if (thisPlayerScorecard[scoresCombo.getSelectedIndex()] == 100) {
                        thisPlayerScorecard[scoresCombo.getSelectedIndex()] = checker.check(scoresCombo.getSelectedIndex() + 1, randomRolls);
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
                        rollNumLabel.setText("You cannot score that!");
                    }
                    break;
                case "next":
                    System.out.println("start computer turn");
                    setVisible(false);
                    new ComputerFrame(1, playerName, thisPlayerScorecard, thisComputerScorecard);
                    break;
                case "menu":
                    new MenuFrame();
                    break;
            }
        }
    }
}




