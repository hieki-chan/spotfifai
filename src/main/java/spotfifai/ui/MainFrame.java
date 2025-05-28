package spotfifai.ui;

import spotfifai.controller.TabViewSystem;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import spotfifai.controller.*;
import spotfifai.controller.MusicPlayerController;
import spotfifai.models.Playlist;
import spotfifai.models.Song;
import spotfifai.ui.auth.AccountManagementForm;
import spotfifai.ui.auth.AuthFrame;
import spotfifai.util.SpotfifaiDialog;
import spotfifai.util.TimeUtil;
import spotfifai.util.located.ResourceLocator;
import spotfifai.util.located.ServiceLocator;

public class MainFrame extends javax.swing.JFrame implements IAuthListener
{

    TabViewSystem tabSystem;

    PlaylistsController playlistController;
    MusicPlayerController musicPlayerController;

    public MainFrame()
    {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null); // Frame Center
        setIconImage(((ImageIcon) ResourceLocator.getIcon("fifai.png")).getImage());

        SpotfifaiAuth.current().addListener(this);
        this.playlistController = ServiceLocator.get(PlaylistsController.class);
        this.musicPlayerController = ServiceLocator.get(MusicPlayerController.class);

        // playDelayed progress
        sliderPlayProgress.getModel().setMaximum(1000);
        sliderPlayProgress.getModel().setMinimum(0);
        musicPlayerController.setListener(new IMusicListenter()
        {
            @Override
            public void onOpen(Song song, float duration)
            {
                setSelectedSong(song, duration);
            }

            @Override
            public void onProgress(float ratio, float progressInSeconds)
            {
                //System.out.println(ratio);
                int progress = (int) (ratio * sliderPlayProgress.getModel().getMaximum());
                sliderPlayProgress.getModel().setValue(progress);
                //System.out.println(progress);
                if (progress == sliderPlayProgress.getModel().getMaximum() - 1)
                {
                    buttonPlay.setIcon(ResourceLocator.getIcon("play_icon.png"));
                }
                labelProgress.setText(TimeUtil.getTimeInString(progressInSeconds));
            }
        });

        sliderPlayProgress.addChangeListener(new ChangeListener()
        {
            private boolean wasPressed = false;

            @Override
            public void stateChanged(ChangeEvent e)
            {
                if (sliderPlayProgress.getModel().getValueIsAdjusting())
                {
                    wasPressed = true;
                } else if (wasPressed)
                {
                    wasPressed = false;
                    musicPlayerController.seek(sliderPlayProgress.getValue() / (float) sliderPlayProgress.getModel().getMaximum());
                    //playProgress.getModel().setValue(playProgress.getValue());
                }
            }
        });

        //PlaylistsForm playlistForm = new PlaylistForm();
        //playlistForm.setVisible(true);
        //showContent(playlistForm);
        initTabMenu();
    }

    private void initTabMenu()
    {
        tabSystem = new TabViewSystem(panelTabContainer, panelContentContainer);

        panelTabContainer.removeAll();
        panelTabContainer.setLayout(new BoxLayout(panelTabContainer, BoxLayout.Y_AXIS));
        panelContentContainer.setLayout(new BoxLayout(panelContentContainer, BoxLayout.Y_AXIS));

        // home menu
        var homeMenu = new MenuItemForm("Home", "Discover top songs", ResourceLocator.getIcon("home_icon.png"));
        homeMenu.setOnUserClicked(() ->
        {
            homeMenu.setContentTab(tabSystem.viewTab(HomeForm.class));
        });
        tabSystem.addMenuItem(homeMenu);

        // distribution menu
        if (SpotfifaiAuth.current().isSignedIn())
        {
            var distributionMenu = new MenuItemForm("Distribution", "Manage your songs", ResourceLocator.getIcon("menu_icon.png"));
            distributionMenu.setOnUserClicked(() ->
            {
                distributionMenu.setContentTab(tabSystem.viewTab(new DistributionForm()));
            });
            tabSystem.addMenuItem(distributionMenu);
        }

        // playlists menu
        for (var playlist : playlistController.getPlaylistDAO().getEntitiesAll())
        {
            createNewPlayListMenu(playlist);
        }
        tabSystem.setSelect(homeMenu);
    }

    private void setSelectedSong(Song song, float d)
    {
        labelSongName.setText(song.getTitle());
        labelArtistName.setText("hieu onichan");
        labelAudioLength.setText(TimeUtil.getTimeInString(d));
        buttonPlay.setIcon(ResourceLocator.getIcon("pause_icon.png"));
    }

    private void createNewPlayListMenu(Playlist playlist)
    {
        var playlistMenu = new MenuItemForm(playlist.getTitle(), "3 songs", ResourceLocator.getIcon("playlist_icon.png"));
        playlistMenu.setOnUserClicked(() ->
        {
            var playlistForm = tabSystem.viewTab(PlaylistForm.class);
            playlistForm.setPlaylist(playlist, playlistMenu);
            playlistMenu.setContentTab(playlistForm);
        });

        playlistMenu.addPropertyChangeListener("enabled", evt ->
        {
            if (!(boolean) evt.getNewValue())
            {
                tabSystem.removeMenuItem(playlistMenu);
            }
        });

        tabSystem.addMenuItem(playlistMenu);
    }

    @Override
    public void onSignedIn()
    {
        labelWelcome.setText("Welcome: " + SpotfifaiAuth.current().getCurrentUser().getUsername());
        initTabMenu();
        buttonNewPlaylist.setVisible(true);
    }

    @Override
    public void onSignedOut()
    {
        labelWelcome.setText("Sign in here");
        initTabMenu();
        buttonNewPlaylist.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        panelLeft = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buttonNewPlaylist = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTabContainer = new javax.swing.JPanel();
        panelRight = new javax.swing.JPanel();
        topbarContainer = new javax.swing.JPanel();
        buttonAccount = new javax.swing.JButton();
        labelWelcome = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelContentContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        musicIcon = new javax.swing.JLabel();
        labelSongName = new javax.swing.JLabel();
        labelArtistName = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        buttonPlay = new javax.swing.JLabel();
        sliderPlayProgress = new javax.swing.JSlider();
        labelAudioLength = new javax.swing.JLabel();
        labelProgress = new javax.swing.JLabel();
        toggleLoop = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(2, 2, 2));
        setMinimumSize(new java.awt.Dimension(300, 100));

        panelLeft.setBackground(new java.awt.Color(35, 35, 35));

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Your Library");

        buttonNewPlaylist.setBackground(new java.awt.Color(35, 35, 35));
        buttonNewPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/add_icon.png"))); // NOI18N
        buttonNewPlaylist.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonNewPlaylist.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                buttonNewPlaylistMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonNewPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNewPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(35, 35, 35));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(14, 50));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(10, 50));

        panelTabContainer.setBackground(new java.awt.Color(35, 35, 35));
        panelTabContainer.setPreferredSize(new java.awt.Dimension(10, 400));

        javax.swing.GroupLayout panelTabContainerLayout = new javax.swing.GroupLayout(panelTabContainer);
        panelTabContainer.setLayout(panelTabContainerLayout);
        panelTabContainerLayout.setHorizontalGroup(
            panelTabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );
        panelTabContainerLayout.setVerticalGroup(
            panelTabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panelTabContainer);

        javax.swing.GroupLayout panelLeftLayout = new javax.swing.GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeftLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panelRight.setBackground(new java.awt.Color(35, 35, 35));

        topbarContainer.setBackground(new java.awt.Color(30, 30, 30));
        topbarContainer.setMinimumSize(new java.awt.Dimension(0, 100));
        topbarContainer.setPreferredSize(new java.awt.Dimension(74, 45));

        buttonAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/glass.png"))); // NOI18N
        buttonAccount.setMaximumSize(new java.awt.Dimension(45, 45));
        buttonAccount.setMinimumSize(new java.awt.Dimension(45, 45));
        buttonAccount.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonAccountActionPerformed(evt);
            }
        });

        labelWelcome.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        labelWelcome.setForeground(new java.awt.Color(255, 255, 255));
        labelWelcome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelWelcome.setText("Sign in here");

        javax.swing.GroupLayout topbarContainerLayout = new javax.swing.GroupLayout(topbarContainer);
        topbarContainer.setLayout(topbarContainerLayout);
        topbarContainerLayout.setHorizontalGroup(
            topbarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topbarContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        topbarContainerLayout.setVerticalGroup(
            topbarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topbarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topbarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBackground(new java.awt.Color(35, 35, 35));
        jScrollPane2.setBorder(null);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(887, 500));

        panelContentContainer.setBackground(new java.awt.Color(35, 35, 35));

        javax.swing.GroupLayout panelContentContainerLayout = new javax.swing.GroupLayout(panelContentContainer);
        panelContentContainer.setLayout(panelContentContainerLayout);
        panelContentContainerLayout.setHorizontalGroup(
            panelContentContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 887, Short.MAX_VALUE)
        );
        panelContentContainerLayout.setVerticalGroup(
            panelContentContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(panelContentContainer);

        javax.swing.GroupLayout panelRightLayout = new javax.swing.GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
            panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topbarContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
            .addGroup(panelRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRightLayout.setVerticalGroup(
            panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRightLayout.createSequentialGroup()
                .addComponent(topbarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(2, 2, 2));

        musicIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/anime_wallpaper.jpg"))); // NOI18N

        labelSongName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelSongName.setForeground(new java.awt.Color(255, 255, 255));
        labelSongName.setText("Onichan Hentai");

        labelArtistName.setForeground(new java.awt.Color(233, 233, 233));
        labelArtistName.setText("Baka yarou");

        jPanel6.setOpaque(false);

        buttonPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/play_icon.png"))); // NOI18N
        buttonPlay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonPlay.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                buttonPlayMouseClicked(evt);
            }
        });

        sliderPlayProgress.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelAudioLength.setForeground(new java.awt.Color(255, 255, 255));
        labelAudioLength.setText("2:50");

        labelProgress.setForeground(new java.awt.Color(255, 255, 255));
        labelProgress.setText("0:14");
        labelProgress.setToolTipText("");

        toggleLoop.setBackground(new java.awt.Color(2, 2, 2));
        toggleLoop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/loop_icon.png"))); // NOI18N
        toggleLoop.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        toggleLoop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        toggleLoop.setOpaque(true);
        toggleLoop.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                toggleLoopActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(2, 2, 2));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/next_icon.png"))); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton3.setBackground(new java.awt.Color(2, 2, 2));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/previous_icon.png"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(labelProgress)
                .addGap(20, 20, 20)
                .addComponent(sliderPlayProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(labelAudioLength)
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addGap(20, 20, 20)
                .addComponent(buttonPlay)
                .addGap(20, 20, 20)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(toggleLoop)
                .addGap(145, 145, 145))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2)
                        .addComponent(toggleLoop))
                    .addComponent(jButton3)
                    .addComponent(buttonPlay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAudioLength)
                    .addComponent(labelProgress)
                    .addComponent(sliderPlayProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(musicIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelArtistName)
                    .addComponent(labelSongName, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelSongName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelArtistName))
                    .addComponent(musicIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(panelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPlayMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_buttonPlayMouseClicked
    {//GEN-HEADEREND:event_buttonPlayMouseClicked
        if (musicPlayerController.isPlaying())
        {
            buttonPlay.setIcon(ResourceLocator.getIcon("play_icon.png"));
            musicPlayerController.pause();
        } else
        {
            buttonPlay.setIcon(ResourceLocator.getIcon("pause_icon.png"));
            musicPlayerController.resume();
        }
    }//GEN-LAST:event_buttonPlayMouseClicked

    private void toggleLoopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_toggleLoopActionPerformed
    {//GEN-HEADEREND:event_toggleLoopActionPerformed
        musicPlayerController.setLoop(toggleLoop.isSelected());
    }//GEN-LAST:event_toggleLoopActionPerformed

    private void buttonNewPlaylistMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_buttonNewPlaylistMouseClicked
    {//GEN-HEADEREND:event_buttonNewPlaylistMouseClicked
        Playlist createdPlaylist = playlistController.onCreateNew();
        if (createdPlaylist != null)
        {
            createNewPlayListMenu(createdPlaylist);
        }
    }//GEN-LAST:event_buttonNewPlaylistMouseClicked

    private void buttonAccountActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonAccountActionPerformed
    {//GEN-HEADEREND:event_buttonAccountActionPerformed
        if (SpotfifaiAuth.current().isSignedIn())
            SpotfifaiDialog.show("Account Management", this, new AccountManagementForm());
        else
        {
            //this.setEnabled(false);
            new AuthFrame();
        }
    }//GEN-LAST:event_buttonAccountActionPerformed

    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAccount;
    private javax.swing.JButton buttonNewPlaylist;
    private javax.swing.JLabel buttonPlay;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelArtistName;
    private javax.swing.JLabel labelAudioLength;
    private javax.swing.JLabel labelProgress;
    private javax.swing.JLabel labelSongName;
    private javax.swing.JLabel labelWelcome;
    private javax.swing.JLabel musicIcon;
    private javax.swing.JPanel panelContentContainer;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelRight;
    private javax.swing.JPanel panelTabContainer;
    private javax.swing.JSlider sliderPlayProgress;
    private javax.swing.JToggleButton toggleLoop;
    private javax.swing.JPanel topbarContainer;
    // End of variables declaration//GEN-END:variables
}
