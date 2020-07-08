
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu {
    public static void main(String[] args) {
        new MenuFrame();
    }
}

class MenuFrame extends JFrame {

    //integers:
    int gameType = 0;

    //action listeners:
    ButtonListener b = new ButtonListener();

    //images:
    ImageIcon icon = new ImageIcon("icon.png");
    ImageIcon logo = new ImageIcon("logo.png");
    ImageIcon logo2 = new ImageIcon("Java Yahtzee 2 Logo.png");

    //menu jpanels:
    JPanel imagePanel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel radioPanel = new JPanel();
    JPanel radioPanel2 = new JPanel();
    JPanel playerNamePanel = new JPanel();
    JPanel startPanel = new JPanel();
    JPanel brPanel = new JPanel();
    JPanel brPanel2 = new JPanel();

    //menu jlabels:
    JLabel imageLabel = new JLabel(logo2);
    JLabel title = new JLabel("JAVA YAHTZEE 2!!");
    JLabel playersText = new JLabel("How many people are playing?");
    JLabel name1Text = new JLabel("Enter your name: ");
    JLabel playerOneText = new JLabel("Player One Name: ");
    JLabel playerTwoText = new JLabel("Player Two Name: ");
    JLabel playerThreeText = new JLabel("Player Three Name: ");
    JLabel playerFourText = new JLabel("Player Four Name: ");
    JLabel[] playerNameLabels = {playerOneText, playerTwoText, playerThreeText, playerFourText};

    //menu jbuttons:
    JButton startSingle = new JButton("Single-Player Game");
    JButton startMulti = new JButton("Multi-Player Game");
    JButton instruct = new JButton("Instructions");
    JButton credits = new JButton("Credits");
    JButton back = new JButton("Back");
    JButton startGame = new JButton("Start Game!");
    JButton setPlayersNum = new JButton("Set this many players");
    JButton[] menuButtons = {startSingle, startMulti, instruct, credits};


    //combo boxes:
    JComboBox<String> players = new JComboBox<>();
    JComboBox<String> scoresCombo = new JComboBox<>();

    //Text fields:
    JTextField p1Name = new JTextField(20);
    JTextField playerOne = new JTextField(15);
    JTextField playerTwo = new JTextField(15);
    JTextField playerThree = new JTextField(15);
    JTextField playerFour = new JTextField(15);
    JTextField[] playerNameInputs = {playerOne, playerTwo, playerThree, playerFour};

    //Tables:
    JTable scorecardTable;

    //fonts:
    Font titleFont = new Font("Courier", Font.BOLD, 50);
    Font statusFont = new Font("Courier", Font.PLAIN, 20);

    public MenuFrame () {

        System.out.println("Loading game...");
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
        setSize(1000, 1000);
        setTitle("Java Yahtzee 2 by MSGuy01 BETA");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());

        add(brPanel);
        add(brPanel2);

        title.setFont(titleFont);
        titlePanel.add(title);
        imageLabel.setBounds(10, 10, 10, 10);
        imagePanel.add(imageLabel);
        add(imagePanel);

        players.addItem("2");
        players.addItem("3");
        players.addItem("4");

        initializeButton(back, "back");
        initializeButton(startGame, "start");
        initializeButton(setPlayersNum, "setNum");

        for (int i = 0; i < menuButtons.length; i++) {
            initializeButton(menuButtons[i], Integer.toString(i));
            buttonPanel.add(menuButtons[i]);
        }
        add(buttonPanel);

        initializeButton(instruct, "2");
        initializeButton(credits, "3");

        pack();
        setVisible(true);
    }
    void initializeButton(JButton button, String actionCommand) {
        button.addActionListener(b);
        button.setActionCommand(actionCommand);
    }

    void showSingle() {
        clearScreen();
        add(radioPanel);
        add(radioPanel2);
        add(startPanel);
        gameType = 1;
        title.setText("Change Settings");
        radioPanel2.add(name1Text);
        radioPanel2.add(p1Name);
        startPanel.add(startGame);
    }

    void showMulti() {
        clearScreen();
        add(radioPanel);
        add(radioPanel2);
        gameType = 2;
        title.setText("Change Settings");
        radioPanel2.add(playersText);
        radioPanel2.add(players);
        radioPanel2.add(setPlayersNum);

    }

    void showInstruct() {
        clearScreen();
        title.setText("Instructions");
    }

    void showCredits() {
        clearScreen();
        title.setText("Java Yahtzee 2 Credits");
    }

    void clearScreen() {
        remove(brPanel);
        remove(brPanel2);
        remove(buttonPanel);
        add(titlePanel);
        add(buttonPanel);
        imagePanel.remove(imageLabel);
        buttonPanel.add(back);
        for (int i = 0; i < menuButtons.length; i++) {
            buttonPanel.remove(menuButtons[i]);
        }
    }

    void menu() {
        setVisible(false);
        new MenuFrame();
    }

    void game(boolean multi) {
        setVisible(false);
        System.out.println("Multiplayer: " + multi);
        String[] playerNames = {playerOne.getText().toUpperCase(), playerTwo.getText().toUpperCase(), playerThree.getText().toUpperCase(), playerFour.getText().toUpperCase()};
        if (multi) {
            int[][] defaultScorecardSaver = {{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}, {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}, {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}, {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}};
            //REMEMBER TO CHANGE TURNSDONE BACK TO ZERO FROM 24
            new MultiFrame(playerNames, players.getSelectedIndex() + 2, 1, 0, defaultScorecardSaver);
        } else {
            int[] defaultScorecard = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
            new PlayerFrame(p1Name.getText().toUpperCase(), defaultScorecard, defaultScorecard, false);
        }
    }
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "0":
                    showSingle();
                    break;
                case "1":
                    showMulti();
                    break;
                case "2":
                    showInstruct();
                    break;
                case "3":
                    showCredits();
                    break;
                case "back":
                    menu();
                    break;
                case "start":
                    if (gameType == 1) {
                        game(false);
                    } else {
                        game(true);
                    }
                    break;
                case "setNum":
                    for (int i = 0; i < playerNameInputs.length; i++) {
                        playerNamePanel.remove(playerNameInputs[i]);
                    }
                    for (int i = 0; i < playerNameLabels.length; i++) {
                        playerNamePanel.remove(playerNameLabels[i]);
                    }
                    for (int i = 0; i < players.getSelectedIndex() + 2; i++) {
                        playerNamePanel.add(playerNameLabels[i]);
                        playerNamePanel.add(playerNameInputs[i]);
                    }
                    add(playerNamePanel);
                    add(startPanel);
                    startPanel.add(startGame);
                    setVisible(true);
                    break;
            }
        }
    }
}

