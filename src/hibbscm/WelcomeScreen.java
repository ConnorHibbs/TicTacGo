package hibbscm;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JPanel {

    JFrame jframe;
    boolean openNewGame = false;

    public static void main(String[] args) {
        new WelcomeScreen();
    }

    public WelcomeScreen() {
        super();

        jframe = new JFrame();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JButton createGame = new JButton("Create New Game");
        // TODO get their info, and then open the lobby
        createGame.addActionListener(e -> createGame());

        JButton joinGame = new JButton("Join Existing Game");
        // TODO pick an existing lobby, then enter your info
        joinGame.addActionListener(e -> joinGame());

        add(createGame);
        add(joinGame);

        jframe.add(this);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setVisible(true);
    }

    public void createGame() {
        removeAll();
        revalidate();

        JPanel namePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        namePanel.add(new JLabel("Name: "));
        namePanel.add(nameField);
        add(namePanel);

        JPanel durationPanel = new JPanel(new BorderLayout());
        JPanel durationButtonPanel = new JPanel();

        JPanel durationOptionsPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();

        durationOptionsPanel.setLayout(cardLayout);

        JPanel turnDurationOptions = new JPanel();
        turnDurationOptions.add(new JLabel("Number of Turns:"));
        JTextField numbeOfTurnsField = new JTextField(6);
        turnDurationOptions.add(numbeOfTurnsField);
        durationOptionsPanel.add("Turn Based", turnDurationOptions);

        JPanel scoreDurationOptions = new JPanel();
        scoreDurationOptions.add(new JLabel("Winning Score:"));
        JTextField scoreField = new JTextField(6);
        scoreDurationOptions.add(scoreField);
        durationOptionsPanel.add("Score Based", scoreDurationOptions);

        JPanel infiniteDurationOptions = new JPanel();
        durationOptionsPanel.add("Infinite", infiniteDurationOptions);

        ButtonGroup gameDurationGroup = new ButtonGroup();
        for(String text : new String[]{"Turn Based", "Score Based", "Infinite"}) {
            JRadioButton radioButton = new JRadioButton(text);
            radioButton.setActionCommand(text);
            gameDurationGroup.add(radioButton);
            durationButtonPanel.add(radioButton);
            radioButton.addActionListener(e -> cardLayout.show(durationOptionsPanel, text));
        }
        durationPanel.add(durationButtonPanel, BorderLayout.WEST);
        durationPanel.add(durationOptionsPanel, BorderLayout.CENTER);
        add(durationPanel);

        JColorChooser colorChooser = new JColorChooser();
        add(colorChooser);

        JButton createGame = new JButton("Create Game");
        createGame.addActionListener(e -> {
            Settings settings = Settings.getInstance();
            settings.name = nameField.getText();
            String durationMethod = gameDurationGroup.getSelection().getActionCommand();

            if(durationMethod.equals("Turn Based")) {
                settings.durationMethod = Settings.DurationMethod.TURNS;
                settings.numTurns = Integer.parseInt(numbeOfTurnsField.getText());
            } else if(durationMethod.equals("Score Based")) {
                settings.durationMethod = Settings.DurationMethod.SCORE;
                settings.maxScore = Integer.parseInt(scoreField.getText());
            } else {
                settings.durationMethod = Settings.DurationMethod.INFINITE;
            }

            settings.playerColor = colorChooser.getColor();

            // TODO get the other settings
            openLobby();
        });
        add(createGame);

        jframe.pack();
        jframe.revalidate();
    }

    public void openLobby() {
        JOptionPane.showMessageDialog(this, "Opening Lobby");

        Settings settings = Settings.getInstance();

        System.out.println("Your name is " + settings.name + ", and your color is " + Integer.toHexString(settings.playerColor.getRGB()));
        System.out.print("You are opening ");

        switch(settings.durationMethod) {
            case TURNS:
                System.out.println("a " + settings.numTurns + " turn game");
                break;
            case SCORE:
                System.out.println("a game to " + settings.maxScore + " points");
                break;
            case INFINITE:
                System.out.println("an infinite game");
                break;
        }



        // TODO wait for players
    }

    public void joinGame() {

    }
}

