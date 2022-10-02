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
    protected javax.swing.JButton backBtn;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    protected javax.swing.JButton logoutBtn;
    protected javax.swing.JButton playBtn;
    protected javax.swing.JButton reviseBtn;
    
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
        
        if (data.isLoggedIn()) {
            this.getContentPane().removeAll();
            initMainMenuComponents();
        }
        else {
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
        backBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        reviseBtn = new javax.swing.JButton();
        playBtn = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(102, 153, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        backBtn.setText("BACK");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
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

        reviseBtn.setText("REVISE");
        reviseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reviseBtnActionPerformed(evt);
            }
        });

        playBtn.setText("PLAY");
        playBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(logoutBtn))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reviseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(120, 120, 120)
                    .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(380, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(162, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reviseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)))
                .addComponent(logoutBtn))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(164, Short.MAX_VALUE)
                    .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }
    

    private void initGameMenuComponents() {
        // paste respective GUI code here
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
}
