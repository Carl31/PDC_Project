/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author carls
 */
public class View extends JFrame implements Observer {

    private ActionListener actionListener;
    protected ModelData data;

    // LoginGUI Components
    protected javax.swing.JButton exitBtn;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    protected javax.swing.JButton loginBtn;
    protected javax.swing.JTextField username;
    private javax.swing.JLabel usernameLabel;

    // MainMenuGUI Components
    protected javax.swing.JButton tosStatsBtn;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    protected javax.swing.JButton logoutBtn;
    protected javax.swing.JButton toPlayBtn;
    protected javax.swing.JButton toDatabaseBtn;

    // PlayGameGUI Components
    protected javax.swing.JLabel cardCountLabel;
    protected javax.swing.JComboBox<String> cardLang;
    protected javax.swing.JLabel cardWord;
    protected javax.swing.JRadioButton isRevisionBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    protected javax.swing.JButton logoutBtn2;
    protected javax.swing.JTextField numCards;
    protected javax.swing.JButton startGameBtn;

    public View() {
        // frame options
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Verb Cards");
        setMinimumSize(new java.awt.Dimension(575, 375));
        setResizable(false);
        data = new ModelData();
    }

    @Override
    public void update(Observable o, Object arg) {
        data = (ModelData) arg;

        if (data.isLoggedIn()) { // if player is logged in
            if (data.isPlaying()) { // if player is in the play/game menu
                if (data.isGameEnded()) { // if current game has ended
                    if (isRevisionBtn.isSelected()) { // if current game is a revision game
                        data.setIsPlaying(false);
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "Do you want to play again?", "QUESTION",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        } else {
                            data.setIsPlaying(false);
                        }
                    }
                } else if (data.displayWarning) {
                    JOptionPane.showMessageDialog(null, data.message);
                } else {
                    if (data.isWaiting) {
                        if (data.configEnabled == false) {
                            deactivateConfig();

                            JOptionPane.showMessageDialog(null, "GAME STARTING!");
                        } else {
                            if (data.displayCard) {
                                updateCard();
                                do { // get user input
                                    data.userAnswer = javax.swing.JOptionPane.showInputDialog("What is \"" + data.currentCard.getQuestion() + "\" in " + data.currentCard.getLang() + "?");
                                    if (data.userAnswer == null) {
                                        if (JOptionPane.showConfirmDialog(null, "Do you want to quit?", "QUESTION",
                                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                            data.setIsPlaying(false);
                                        }
                                    }
                                } while (data.userAnswer == null && data.isPlaying() == true);
                            }

                            if (data.hasAnswered) {
                                JOptionPane.showMessageDialog(null, data.message);
                            }

                        }
                    } else {
                        this.getContentPane().removeAll();
                        initGameMenuComponents();
                    }
                }

            } else {
                this.getContentPane().removeAll();
                initMainMenuComponents();
            }

        } else {
            this.getContentPane().removeAll();
            initLoginComponents();
        }

//        if (data.quitFlag) {
//            System.exit(0);
//        } else if (data.battleWonFlag) {
//            this.updateBattleView(data.player, data.robot, data.rounds);
//            this.roundNumberLabel.setText(data.winningTitle);
//            this.roundMessageLabel.setText(data.winningSet);
//            this.cardButton1.setEnabled(false);
//
//            for (int i = 0; i < Player.HAND_SIZE; i++) {
//                this.cardButtons.get(i).setEnabled(false);
//            }
//
//        } else if (!data.loginFlag) {
//            this.loginUsernameField.setText("");
//            this.loginPasswordField.setText("");
//        } else if (data.loginFlag && !data.battleStartedFlag) {
//            this.getContentPane().removeAll();
//            initBattleComponents();
//            this.updateBattleView(data.player, data.robot, data.rounds);
//        } else if (data.battleStartedFlag) {
//            this.updateBattleView(data.player, data.robot, data.rounds);
//        }
    }

    protected void initLoginComponents() {
        jPanel1 = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        exitBtn = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(102, 153, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        username.setForeground(new java.awt.Color(0, 0, 0));
        username.setText("username");

        usernameLabel.setForeground(new java.awt.Color(0, 0, 0));
        usernameLabel.setText("Username:");

        loginBtn.setText("LOGIN");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VERB CARDS");
        jLabel3.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        exitBtn.setText("Exit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(183, 183, 183)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(209, 209, 209)
                                                .addComponent(usernameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(exitBtn)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(237, 237, 237)
                                                .addComponent(loginBtn)))
                                .addContainerGap(212, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(113, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loginBtn)
                                .addGap(120, 120, 120)
                                .addComponent(exitBtn))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        //this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        this.loginBtn.addActionListener(this.actionListener);
        this.exitBtn.addActionListener(this.actionListener);

        setVisible(true);
    }

    protected void initMainMenuComponents() {
        jPanel2 = new javax.swing.JPanel();
        tosStatsBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        toDatabaseBtn = new javax.swing.JButton();
        toPlayBtn = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel2.setBackground(new java.awt.Color(102, 153, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tosStatsBtn.setText("STATS");
        tosStatsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MAIN MENU");
        jLabel5.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        logoutBtn.setText("Log out");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        toDatabaseBtn.setText("DATA BASE");
        toDatabaseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reviseBtnActionPerformed(evt);
            }
        });

        toPlayBtn.setText("PLAY");
        toPlayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(logoutBtn))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(214, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(toDatabaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tosStatsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(136, 136, 136))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(120, 120, 120)
                                        .addComponent(toPlayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(380, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addContainerGap(162, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(toDatabaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(tosStatsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(43, 43, 43)))
                                .addComponent(logoutBtn))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addContainerGap(164, Short.MAX_VALUE)
                                        .addComponent(toPlayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        //getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        this.logoutBtn.addActionListener(this.actionListener);
        this.toPlayBtn.addActionListener(this.actionListener);

        setVisible(true);
    }

    private void initGameMenuComponents() {
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        logoutBtn2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        numCards = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cardLang = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        cardWord = new javax.swing.JLabel();
        cardCountLabel = new javax.swing.JLabel();
        startGameBtn = new javax.swing.JButton();
        isRevisionBtn = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cardWord.setEnabled(false);

        jPanel6.setBackground(new java.awt.Color(102, 153, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 255, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Game Config");
        jLabel7.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        logoutBtn2.setText("Log out");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Number of flash cards:");

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Flash card language:");

        cardLang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Spanish", "English", "Random"}));
        cardLang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLangActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cardWord.setForeground(new java.awt.Color(0, 0, 0));
        cardWord.setText("word");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(cardWord)
                                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(34, Short.MAX_VALUE)
                                .addComponent(cardWord)
                                .addGap(29, 29, 29))
        );

        cardCountLabel.setText("Card:");

        numCards.setText("10");

        startGameBtn.setBackground(new java.awt.Color(153, 255, 0));
        startGameBtn.setForeground(new java.awt.Color(0, 0, 0));
        startGameBtn.setText("Start Game");
        startGameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameBtnActionPerformed(evt);
            }
        });

        isRevisionBtn.setForeground(new java.awt.Color(0, 0, 0));
        isRevisionBtn.setText("Revision");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(logoutBtn2))
                .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addGap(51, 51, 51)
                                                        .addComponent(jLabel4))
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addGap(73, 73, 73)
                                                        .addComponent(numCards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(157, 157, 157))
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(cardLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel1))
                                                        .addGap(54, 54, 54)
                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                        .addComponent(isRevisionBtn)
                                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(startGameBtn))))))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(184, 184, 184)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(cardCountLabel)
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(59, 59, 59))
                                                ))))
                        .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel1))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(numCards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cardLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(startGameBtn)
                                        .addComponent(isRevisionBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardCountLabel)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(logoutBtn2))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(0, 24, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        //getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        this.logoutBtn2.addActionListener(this.actionListener);
        this.startGameBtn.addActionListener(this.actionListener);
        this.isRevisionBtn.addActionListener(this.actionListener);

        setVisible(true);

    }

    private void initStatsMenuComponents() {
        // paste respective GUI code here
    }

    private void initDatabaseMenuComponents() {
        // paste respective GUI code here
    }

    // may also need function to update certain views
    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;

        initLoginComponents();
        setVisible(true);
    }

    // Button handler code
    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void reviseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void playBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Username must be longer than 3 character and only contain alphanumeric characters.");
        }
    }

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cardLangActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void startGameBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.isCanStart()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Number of cards must be between 1-30 inclusive.");
            numCards.setText("10");
        }
    }

    private void deactivateConfig() {
        // deactivate config options
        numCards.setEnabled(false);
        cardLang.setEnabled(false);
        isRevisionBtn.setEnabled(false);
        startGameBtn.setEnabled(false);
        cardWord.setEnabled(true);
    }

    private void updateCard() {
        cardWord.setText(data.currentCard.getQuestion());
        cardCountLabel.setText("Card: " + data.cardsRemaining);
        //JOptionPane.showMessageDialog(null, data.currentCard.getQuestion()); // for testing
    }
}
