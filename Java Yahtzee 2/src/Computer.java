import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Computer{
    static int[] defaultScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public static void main(String[] args) {
        new ComputerFrame(1, "DEFAULT", defaultScorecard, defaultScorecard);
    }
}
class ComputerFrame extends JFrame {
    Random random = new Random();
    Checker checker = new Checker();

    int[] randomRolls = {0, 0, 0, 0, 0};
    int rollNum = 0;

    boolean[] randomRollsBoolean = {true, true, true, true, true};

    ButtonListener b = new ButtonListener();

    ImageIcon icon = new ImageIcon("icon.png");

    JPanel titlePanel = new JPanel();

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

    JLabel title = new JLabel("JAVA YAHTZEE 2!!");

    JLabel rollLabel = new JLabel("DIE1  DIE2  DIE3  DIE4  DIE5");
    JLabel diceLabel1 = new JLabel("");
    JLabel diceLabel2 = new JLabel("");
    JLabel diceLabel3 = new JLabel("");
    JLabel diceLabel4 = new JLabel("");
    JLabel diceLabel5 = new JLabel("");
    JLabel rollNumLabel = new JLabel("Roll " + rollNum);
    JLabel scoreLabel = new JLabel("Score: ");
    JLabel[] diceLabels = {diceLabel1, diceLabel2, diceLabel3, diceLabel4, diceLabel5};
    JLabel[] diceLabelsRandom = {diceLabel1, diceLabel2, diceLabel3, diceLabel4, diceLabel5};

    JButton rollDice = new JButton("Roll Dice");
    JButton next = new JButton("Next");
    JButton nextTurn = new JButton("Next");


    JComboBox<String> scoresCombo = new JComboBox<>();

    JTable scorecardTable;

    Font titleFont = new Font("Courier", Font.BOLD, 50);
    Font authFont = new Font("Courier", Font.BOLD, 30);
    Font statusFont = new Font("Courier", Font.PLAIN, 20);
    public String playerName;
    //last spot is for upper bonus
    public int[] thisPlayerScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public int[] thisComputerScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    public ComputerFrame(int type, String playerName, int[] playerScorecard, int[] computerScorecard){
        this.playerName = playerName;
        for (int i = 0; i < computerScorecard.length; i++) {
            thisComputerScorecard[i] = computerScorecard[i];
        }
        for (int i = 0; i < playerScorecard.length; i++) {
            thisPlayerScorecard[i] = playerScorecard[i];
        }
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
        setSize(1000, 1000);
        setTitle("Java Yahtzee 2 by MSGuy01");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());
        if (type == 1) {
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
            scoresCombo.addItem("Ones");
            scoresCombo.addItem("Twos");
            scoresCombo.addItem("Threes");
            scoresCombo.addItem("Fours");
            scoresCombo.addItem("Fives");
            scoresCombo.addItem("Sixes");
            scoresCombo.addItem("Three Of A Kind");
            scoresCombo.addItem("Four Of A Kind");
            scoresCombo.addItem("Full House");
            scoresCombo.addItem("Small Straight");
            scoresCombo.addItem("Large Straight");
            scoresCombo.addItem("Yahtzee");
            scoresCombo.addItem("Chance");

            title.setFont(titleFont);
            title.setText("COMPUTER'S TURN");
            titlePanel.add(title);
            add(titlePanel);

            initializeButton(rollDice, "rollDice");

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

            add(scorePanel);
            scorePanel.add(scoreLabel);

            initializeButton(next, "next");
        } else if (type == 2) {
            title.setFont(titleFont);
            title.setText("COMPUTER'S TURN");
            titlePanel.add(title);
            add(titlePanel);

            rollNumLabel.setFont(statusFont);
            rollNumLabel.setText("Computer Score");
            rollNumPanel.add(rollNumLabel);
            add(rollNumPanel);

            initializeButton(nextTurn, "nextTurn");
            rollNumPanel.add(nextTurn);

            add(rollLabelPanel);
            printScorecard();
        }
        pack();
        setVisible(true);
    }
    void initializeButton(JButton button, String actionCommand) {
        button.addActionListener(b);
        button.setActionCommand(actionCommand);
    }

    boolean[] selector() {
        int highestLower;
        int originHigh;
        int highestUpper;
        int highestScore;
        int minVals = 2;
        //Use this variable when returning what dice to roll:
        boolean[] finalArray = {true, true, true, true, true};
        //Set these two variables to the best possible lower score with the current dice roll:
        highestLower = checkMainItem(randomRolls);
        originHigh = checkMainItem(randomRolls);
        int[] possibilities = {0, 0, 0, 0, 0};
        int[] best = {0, 0, 0, 0, 0};
        //Set the possibilities array to the actual roll:
        for (int i = 0; i < possibilities.length; i++) {
            possibilities[i] = randomRolls[i];
        }

        /*
        FIND THE BEST POSSIBLE LOWER SECTION SCORES WITH HYPOTHETICAL ROLLS
         */

        //Iterate through each possible roll:
        for (int i = 0; i < randomRolls.length; i++) {
            for (int j = 1; j < 7; j++) {
                possibilities[i] = j;
                //See if the current hypothetical roll is better than past hypothetical rolls:
                if (checkMainItem(possibilities) > highestLower) {
                    //See what is the best possible lower score the computer can get with this hypothetical roll:
                    highestLower = checkMainItem(possibilities);
                    //Set best array to current possibilities array in order to tell the computer what dice to roll:
                    for (int k = 0; k < possibilities.length; k++) {
                        best[k] = possibilities[k];
                    }
                }
            }
            //Reset possibilities to the actual roll:
            for (int k = 0; k < possibilities.length; k++) {
                possibilities[k] = randomRolls[k];
            }
        }

        /*
        PRINT OUT THE BEST POSSIBILITIES FOR SCORING
         */

        System.out.println("Highest lower [without changes] score: " + findLowerScore(originHigh) + " (" + originHigh + ")");
        System.out.println("Highest lower [with changes] score: " + findLowerScore(highestLower) + " (" + highestLower + ")");
        for (int i = 0; i < best.length; i++) {
            System.out.println("hypothetical die " + (i + 1) + ": " + best[i]);
            System.out.println("actual die " + (i + 1) + ": " + randomRolls[i]);
        }
        System.out.println("Highest upper: item: " + checker.most(randomRolls) + " score: " + checker.check(checker.most(randomRolls), randomRolls));
        //Find best upper section score:
        highestUpper = checker.most(randomRolls);
        System.out.println("Roll: " + rollNum);

        /*
            FIND FINAL SCORE CHOICE
         */

        //If a good lower section score is available:
        if (rollNum < 3) {
            if (originHigh >= 25) {
                System.out.println("Last roll");
                scoreLabel.setText("Score: unknown");
                for (int i = 0; i < diceLabels.length; i++) {
                    diceLabels[i].setForeground(Color.green);
                }
                rollPanel.remove(rollDice);
                rollNumPanel.add(next);
                System.out.println("Score " + originHigh);
                highestScore = originHigh;
                scoreLabel.setText("Score: " + findLowerScore(highestScore));
                thisComputerScorecard[findArrayIndex(highestScore) - 1] = checker.check(findArrayIndex(highestScore), randomRolls);
            }
            //If there's a good possible upper section score:
            else if (checker.check(checker.most(randomRolls), randomRolls) / checker.most(randomRolls) >= minVals && thisComputerScorecard[highestUpper - 1] == 100) {
                System.out.println("***************************SCORE TYPE 3");
                System.out.println(minVals + " or more items for upper score");
                if (rollNum < 3) {
                    //Reroll dice that aren't the number that the computer is going for:
                    for (int i = 0; i < randomRolls.length; i++) {
                        if (randomRolls[i] != highestUpper) {
                            diceLabels[i].setForeground(Color.red);
                            finalArray[i] = true;
                        } else {
                            diceLabels[i].setForeground(Color.black);
                            finalArray[i] = false;
                        }
                    }
                }
                System.out.println("Score " + highestUpper);
                highestScore = highestUpper;
                scoreLabel.setText("Score: " + highestUpper);
            }
            //If there's a good possible lower section score:
            else if (highestLower >= 25 && thisComputerScorecard[findArrayIndex(highestLower) - 1] == 100) {
                scoreLabel.setText("Score: " + findLowerScore(highestLower) + " (possible)");
                for (int i = 0; i < best.length; i++) {
                    System.out.print("Possible die " + (i + 1) + ": " + best[i] + ", ");
                }
                System.out.println("Best possible score: " + highestLower);
                for (int i = 0; i < randomRolls.length; i++) {
                    if (randomRolls[i] == best[i]) {
                        diceLabels[i].setForeground(Color.black);
                        finalArray[i] = false;
                    } else {
                        diceLabels[i].setForeground(Color.red);
                        finalArray[i] = true;
                    }
                }
            }
            //four/three of a kind
            else if (thisComputerScorecard[6] == 100 || thisComputerScorecard[7] == 100) {
                for (int i = 0; i < randomRolls.length; i++) {
                    if (randomRolls[i] == checker.most(randomRolls)) {
                        diceLabels[i].setForeground(Color.black);
                        finalArray[i] = false;
                    }
                    else{
                        diceLabels[i].setForeground(Color.red);
                        finalArray[i] = true;
                    }
                }
            }
            //Bad roll- PICK RANDOM UPPER SECTION SCORE TO ROLL
            else {
                for (int i = 0; i < randomRolls.length; i++) {
                    diceLabels[i].setForeground(Color.red);
                }
            }
        }
        else{
            System.out.println("Last roll");
            scoreLabel.setText("Score: unknown");
            for (int i = 0; i < diceLabels.length; i++) {
                diceLabels[i].setForeground(Color.green);
            }
            rollPanel.remove(rollDice);
            rollNumPanel.add(next);
            //LOWER SCORE
            if (originHigh >= 25 && thisComputerScorecard[findArrayIndex(originHigh) - 1] == 100) {
                scoreLabel.setText("Score: " + findLowerScore(originHigh));
                //work on this
                thisComputerScorecard[findArrayIndex(originHigh) - 1] = checker.check(findArrayIndex(originHigh), randomRolls);
            }
            //GOOD UPPER SECTION SCORE
            else if (checker.check(checker.most(randomRolls), randomRolls) / checker.most(randomRolls) >= minVals && thisComputerScorecard[highestUpper - 1] == 100) {
                scoreLabel.setText("Score: " + highestUpper);
                System.out.println("Upper score is available");
                thisComputerScorecard[highestUpper - 1] = checker.check(highestUpper, randomRolls);
            }
            //NOT GOOD UPPER SECTION SCORE
            else if (checker.check(checker.most(randomRolls), randomRolls) > checker.most(randomRolls) && thisComputerScorecard[checker.most(randomRolls) - 1] == 100){
                scoreLabel.setText("Score: " + checker.most(randomRolls));
                thisComputerScorecard[checker.most(randomRolls) - 1] = checker.check(checker.most(randomRolls), randomRolls);
            }
            //FOUR OF A KIND
            else if (checker.check(checker.most(randomRolls), randomRolls) >= checker.most(randomRolls) * 4 && thisComputerScorecard[6] == 100){
                scoreLabel.setText("Score: Four of a Kind");
                thisComputerScorecard[7] = checker.check(8, randomRolls);
            }
            //THREE OF A KIND
            else if (checker.check(checker.most(randomRolls), randomRolls) >= checker.most(randomRolls) * 3 && thisComputerScorecard[6] == 100){
                scoreLabel.setText("Score: Three of a Kind");
                thisComputerScorecard[6] = checker.check(7, randomRolls);
            }
            //CHANCE
            else{
                if (thisComputerScorecard[12] == 100) {
                    scoreLabel.setText("Score: Chance");
                    thisComputerScorecard[12] = checker.check(13, randomRolls);
                }
                else{
                    boolean foundItem = false;
                    for (int i = 0; i < 6; i++) {
                        if (thisComputerScorecard[i] == 100) {
                            scoreLabel.setText("Score: " + (i + 1));
                            thisComputerScorecard[i] = checker.check(i + 1, randomRolls);
                            foundItem = true;
                            break;
                        }
                    }
                    if (! foundItem) {
                        for (int i = 11; i > -1; i--) {
                            if (thisComputerScorecard[i] == 100) {
                                scoreLabel.setText("Score 0 on: " + i);
                                thisComputerScorecard[i] = checker.check(i + 1, randomRolls);
                                foundItem = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        /*
        If no current score is selected on the final roll:
         */

        return finalArray;
    }

    String findLowerScore(int score) {
        if (score == 25) {
            return "Full House";
        } else if (score == 30) {
            return "Small Straight";
        } else if (score == 40) {
            return "Large Straight";
        } else if (score == 50) {
            return "Yahtzee";
        }
        return "none";
    }
    int checkMainItem(int[] array) {
        if (checker.check(12, array) != 0 && thisComputerScorecard[11] == 100) {
            return 50;
        }
        if (checker.check(11, array) != 0 && thisComputerScorecard[10] == 100) {
            return 40;
        }
        if (checker.check(10, array) != 0 && thisComputerScorecard[9] == 100) {
            return 30;
        }
        if (checker.check(9, array) != 0 && thisComputerScorecard[8] == 100) {
            return 25;
        }
        return 0;
    }
    int findArrayIndex(int score) {
        switch(score) {
            case 25:
                return 9;
            case 30:
                return 10;
            case 40:
                return 11;
            case 50:
                return 12;
            default:
                return 0;
        }
    }


    void printScorecard() {
        String[] column = {"SCORE TYPE", "SCORE"};
        String[] itemNames = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three Of A Kind", "Four Of A Kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance", "Upper Bonus", "Upper Total", "Lower Total", "Total"};
        String[][] data = {{"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}};
        System.out.println("print scorecard");
        int upperTotal = 0;
        for (int j = 0; j < 6; j++) {
            System.out.println(thisComputerScorecard[j]);
            if (thisComputerScorecard[j] != 100) {
                upperTotal += thisComputerScorecard[j];
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
                        thisComputerScorecard[13] = 35;
                        data[i][1] = "35";
                    }
                    else{
                        thisComputerScorecard[13] = 0;
                        data[i][1] = "0";
                    }
                    break;
                case "Lower Total":
                    int lowerTotal = 0;
                    for (int j = 6; j < thisComputerScorecard.length - 1; j++) {
                        if (thisComputerScorecard[j] != 100) {
                            lowerTotal += thisComputerScorecard[j];
                        }
                    }
                    data[i][1] = Integer.toString(lowerTotal);
                    break;
                case "Total":
                    int total = 0;
                    for (int j = 0; j < thisComputerScorecard.length; j++) {
                        System.out.println(thisComputerScorecard[j]);
                        if (thisComputerScorecard[j] != 100 && ! itemNames[j].equals("Upper Total") && ! itemNames[j].equals("Lower Total")) {
                            total += thisComputerScorecard[j];
                        }
                    }
                    data[i][1] = Integer.toString(total);
                    break;
                default:
                    if (thisComputerScorecard[i] != 100) {
                        data[i][1] = Integer.toString(thisComputerScorecard[i]);
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
                        }
                        rollNum++;
                        rollNumLabel.setText("Roll " + rollNum);
                        for (int i = 0; i < diceLabelsRandom.length; i++) {
                            if (randomRollsBoolean[i]) {
                                currentRandom = random.nextInt(6) + 1;
                                diceLabelsRandom[i].setText(currentRandom + "      ");
                                randomRolls[i] = currentRandom;
                            }
                        }
                        randomRollsBoolean = selector();
                    }
                    break;
                case "next":
                    System.out.println("next");
                    setVisible(false);
                    new ComputerFrame(2, playerName, thisPlayerScorecard, thisComputerScorecard);
                    break;
                case "nextTurn":
                    System.out.println("NEXT");
                    setVisible(false);
                    new PlayerFrame(playerName, thisPlayerScorecard, thisComputerScorecard, false);
                    break;
            }
        }
    }
}




