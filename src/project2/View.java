/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

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
    protected javax.swing.JButton toStatsBtn;
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
    protected javax.swing.JButton backBtn3;

    // DatabaseGUI Components
    protected javax.swing.JButton addWordBtn;
    protected javax.swing.JButton backBtn2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    protected javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    protected javax.swing.JButton logoutBtn3;
    protected javax.swing.JTextField newEnglishWord;
    protected javax.swing.JTextField newSpanishWord;
    protected javax.swing.JRadioButton orderInSpanishBtn;
    protected DefaultListModel<String> model;
    protected javax.swing.JButton removeWordBtn;
    protected String lastSelectedItem = "";
    
    // StatsGUI Components
    protected javax.swing.JButton backBtn1;
    private javax.swing.JLabel jLabel6;
    protected javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JButton logoutBtn4;

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

            } else if (data.isInDb) {
                if (data.listUpdated) {
                    updateList();
                } else {
                    this.getContentPane().removeAll();
                    initDatabaseMenuComponents();
                }

            } else {
                this.getContentPane().removeAll();
                initMainMenuComponents();
            }

        } else {
            this.getContentPane().removeAll();
            initLoginComponents();
        }
    }

    protected void initLoginComponents() {
        jPanel1 = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        exitBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        backBtn3 = new javax.swing.JButton();
        backBtn2 = new javax.swing.JButton();

        logoutBtn3 = new javax.swing.JButton();
        logoutBtn2 = new javax.swing.JButton();
        logoutBtn4 = new javax.swing.JButton();
        backBtn1 = new javax.swing.JButton();

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
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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

        pack();

        this.loginBtn.addActionListener(this.actionListener);
        this.exitBtn.addActionListener(this.actionListener);

        setVisible(true);
    }

    protected void initMainMenuComponents() {
        jPanel2 = new javax.swing.JPanel();
        toStatsBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        toDatabaseBtn = new javax.swing.JButton();
        toPlayBtn = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel2.setBackground(new java.awt.Color(102, 153, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        toStatsBtn.setText("STATS");
        toStatsBtn.addActionListener(new java.awt.event.ActionListener() {
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
                        .addComponent(toStatsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                                .addComponent(toStatsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(43, 43, 43)))
                        .addComponent(logoutBtn))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(164, Short.MAX_VALUE)
                                .addComponent(toPlayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout); // edited out
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
        this.toDatabaseBtn.addActionListener(this.actionListener);
        this.toStatsBtn.addActionListener(this.actionListener);

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
        backBtn3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cardWord.setEnabled(false);

        backBtn3.setForeground(new java.awt.Color(0, 0, 0));
        backBtn3.setText("Back");

        jPanel6.setBackground(new java.awt.Color(102, 153, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 255, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Game Config");
        jLabel7.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        logoutBtn2.setText("Log out");
        logoutBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

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

        backBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
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
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(backBtn3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                .addComponent(cardLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(54, 54, 54)
                                                                                .addComponent(isRevisionBtn))
                                                                        .addComponent(jLabel1))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(startGameBtn))))
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
                                                                        .addGap(59, 59, 59))))))
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
                                .addGap(0, 68, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(logoutBtn2)
                                        .addComponent(backBtn3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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
        this.backBtn3.addActionListener(this.actionListener);

        setVisible(true);

    }

    private void initStatsMenuComponents() {
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        logoutBtn4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        backBtn1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel9.setBackground(new java.awt.Color(102, 153, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 255, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Statistics");
        jLabel6.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        logoutBtn4.setText("Log out");
        logoutBtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        backBtn1.setText("Back");
        backBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(backBtn1)
                        .addGap(105, 105, 105)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(logoutBtn4))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(logoutBtn4)
                            .addComponent(backBtn1)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        this.logoutBtn4.addActionListener(this.actionListener);
        this.backBtn1.addActionListener(this.actionListener);
        
        setVisible(true);
    }

    private void initDatabaseMenuComponents() {
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        logoutBtn3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        model = new DefaultListModel<>();
        orderInSpanishBtn = new javax.swing.JRadioButton();
        newEnglishWord = new javax.swing.JTextField();
        newSpanishWord = new javax.swing.JTextField();
        addWordBtn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        backBtn2 = new javax.swing.JButton();
        removeWordBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        model.clear();
        int currentNum = 1;
        for (Word word : data.words) {
            model.addElement(currentNum + ") " + word.getEnglish() + " - " + word.getSpanish());
            currentNum++;
        }

        jList1 = new javax.swing.JList<String>(model);
        jList1.addListSelectionListener((ListSelectionEvent e) -> {
            System.out.println("LIST: "+jList1.getSelectedValue());
        });

        jPanel7.setBackground(new java.awt.Color(102, 153, 0));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 255, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Words Database");
        jLabel8.setBorder(new javax.swing.border.MatteBorder(null));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        logoutBtn3.setText("Log out");
        logoutBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        orderInSpanishBtn.setText("Order in Spanish");
        orderInSpanishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderInSpanishWordActionPerformed(evt);
            }
        });

        if (!data.wordAdded) {
            newEnglishWord.setText("english");
            newSpanishWord.setText("spanish");
        }

        removeWordBtn.setForeground(new java.awt.Color(0, 0, 0));
        removeWordBtn.setText("Remove Word");
        removeWordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWordBtnWordActionPerformed(evt);
            }
        });

        addWordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newWordBtnActionPerformed(evt);
            }
        });

        addWordBtn.setText("Add word");

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Add a word to our database:");

        backBtn2.setText("Back");
        backBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(backBtn2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logoutBtn3))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(188, 188, 188)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(orderInSpanishBtn))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(97, 97, 97)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(13, 13, 13)
                                                                .addComponent(newEnglishWord, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(newSpanishWord, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(83, 83, 83)
                                                                .addComponent(addWordBtn))
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(46, 46, 46)
                                                                .addComponent(jLabel9))
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(removeWordBtn)))))
                                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(orderInSpanishBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(0, 80, Short.MAX_VALUE)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(newEnglishWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(newSpanishWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(addWordBtn)
                                                                .addGap(90, 90, 90)
                                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(logoutBtn3)
                                                                        .addComponent(backBtn2)))
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(112, 112, 112)
                                                                .addComponent(removeWordBtn))))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1)
                                                .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        this.logoutBtn3.addActionListener(this.actionListener);
        this.backBtn2.addActionListener(this.actionListener);
        this.addWordBtn.addActionListener(this.actionListener);
        this.orderInSpanishBtn.addActionListener(this.actionListener);
        this.removeWordBtn.addActionListener(this.actionListener);

        setVisible(true);
    }

    // may also need function to update certain views
    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;

//        //initDatabaseMenuComponents();
//        initMainMenuComponents();
//        this.getContentPane().removeAll();
//        initGameMenuComponents();
//        this.getContentPane().removeAll();
        initLoginComponents(); // start login screen

        setVisible(true);
    }

    // Button handler code
    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {
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

    private void startGameBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.isCanStart()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Number of cards must be between 1-30 inclusive.");
            numCards.setText("10");
        }
    }

    private void newWordBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.wordAdded) {
            JOptionPane.showMessageDialog(null, "Unable to be added. Ensure word is alphanumeric without spaces and not already within database then try again.");
        } else {
            JOptionPane.showMessageDialog(null, "Word Added: " + newSpanishWord.getText() + " - to " + newEnglishWord.getText());
            data.wordAdded = false;
        }

//        newSpanishWord.setText("");
//        newEnglishWord.setText("");
    }

    private void orderInSpanishWordActionPerformed(java.awt.event.ActionEvent evt) {
        removeWordBtn.setEnabled(!removeWordBtn.isEnabled());
    }

    private void removeWordBtnWordActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.wordRemoved) {
            JOptionPane.showMessageDialog(null, "Error: Please select a word to remove.");
        } else {
            JOptionPane.showMessageDialog(null, "Word removed: " + this.lastSelectedItem);
        }

        //jList1.setSelectedIndex(0);
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

    private void updateList() {
        int currentNum = 1;
        model.clear();
        if (orderInSpanishBtn.isSelected()) {
            for (Word word : data.words) {
                model.addElement(currentNum + ") " + word.getSpanish() + " - " + word.getEnglish());
                currentNum++;
            }
        } else {
            for (Word word : data.words) {
                model.addElement(currentNum + ") " + word.getEnglish() + " - " + word.getSpanish());
                currentNum++;
            }
        }

        jList1.setModel(model);
    }
}
