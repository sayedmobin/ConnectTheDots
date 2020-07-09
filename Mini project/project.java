/*


/ Author: Sayed Mobin, Zhuguang Jiang


Date 10/14/2017


Mini-Project

*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class project extends JFrame
{  
   private JRadioButton[][] buttonArray = new JRadioButton[8][8];
   private boolean h[][] = new boolean[8][8];
	private boolean v[][] = new boolean[8][8];
   private String playername1;
	private String playername2;
	private JLabel player1;
	private JLabel player2;
   private int playerscore1 = 0;
	private int playerscore2 = 0;
   private boolean keepgoing = true;
   private int count = 0;
   private int win[][] = new int[8][8];
   private int count2;
   private int count3=0;
   private String name = "PlayerData.bin";
   
   public project()
   {
      setSize(900,700);
      setLocationRelativeTo(null);
      setTitle("Connect the dot");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      JMenuBar jmb = new JMenuBar();
      JMenu file = new JMenu("File");
      jmb.add(file);
      JMenuItem newgame = new JMenuItem("New Game");
      JMenuItem save = new JMenuItem("Save");
      JMenuItem xit = new JMenuItem("Exit");
      JMenuItem help = new JMenuItem("Rule");
      JMenuItem load = new JMenuItem("Load");
      file.add(newgame);
      file.add(save);
      file.add(load);
      file.add(help);
      file.add(xit);
      
      // setup the GUI
      
      Player players = new Player();
		add(players, BorderLayout.NORTH);
      //The player class add to border North
      
      Game game = new Game();
      add(game, BorderLayout.CENTER);
      Thread th = new Thread(game);
      th.start();
      //The Game class add to border center
      //and making the the Game class become thread
      
      xit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            getWinner();
            JOptionPane.showMessageDialog(null,"You are welcome in advance! Team Sayed & Jason");
            System.exit(0);
         }// the you click on the exit button, will calculated the winner and show up the message
      
      });
      
      addWindowListener(new WindowAdapter()
      {
      public void windowClosing(WindowEvent we)
      {  
            getWinner();
           JOptionPane.showMessageDialog(null, "Anything free is worth appreciation, why would you close it!");
         }  }); //when people click on the red X to close the program will doing same thing as the exit button
      
      
      help.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            JOptionPane.showMessageDialog(null,"1. The rule is to connect the two 2 dot become a line if you connect the dots to a box then you gain one point."
                                                +"\n 2. The current player will be red word."
                                                +"\n 3. When you gained one point and you got extra term."
                                                +"\n 4. The save button and save the game for next time, when you click on the load you will access the archive");
         }
      
      });//actionListener for rule 
      
      newgame.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            dispose();
            new project();
         }
      
      });
      
      //close the old game to open the new game
      
      save.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
         try{
            
       
             FileOutputStream fos = new FileOutputStream(name);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(bos);
             
             dos.writeUTF(playername1);
             dos.writeUTF(playername2);
             dos.writeInt(playerscore1);
             dos.writeInt(playerscore2);
             dos.writeInt(count);
             dos.writeInt(count2);
             dos.writeInt(count3);
             for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
               dos.writeBoolean(h[i][j]);
               dos.writeBoolean(v[i][j]);
               dos.writeInt(win[i][j]);
            }
          }
            dos.flush();
            dos.close();
            
         
         }catch(Exception e)
      {
         System.out.println(e);
      }
         
      }
      });
      
      //the save button will save the data to the binary file

       load.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
         try{
             
            FileInputStream fis = new FileInputStream(name);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            
            playername1 = dis.readUTF();
            playername2 = dis.readUTF();
            playerscore1 = dis.readInt();
            playerscore2 = dis.readInt();
            
            player1.setText(playername1 + ": " + playerscore1 + "                              ");
			   player2.setText(playername2 + ": " + playerscore2);
            count = dis.readInt();
            count2 = dis.readInt();
            count3 = dis.readInt();
            
            for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
              h[i][j] =  dis.readBoolean();
              v[i][j] =  dis.readBoolean();
              win[i][j] = dis.readInt(); 
            }
          
          invalidate();
           validate();
           repaint();
          }
            
            }catch(Exception e)
      {
         System.out.println(e);
      }

                        
         }
      
      });//the load button will read the data and repaint the GUI
      
      
      
      
      
      setJMenuBar(jmb);
      setResizable(false);
      setVisible(true);
   
   }
   public void getWinner()
   {
       if (playerscore1 > playerscore2) {
						JOptionPane.showMessageDialog(null,"Player 1 "+ playername1 + " is the winner!");
					}
					else if (playerscore1 < playerscore2) {
						JOptionPane.showMessageDialog(null,"Player 2 " +playername2 + " is the winner!");
					}
					else if (playerscore1 == playerscore2) {
						JOptionPane.showMessageDialog(null, "The game is a tie with " + playerscore1 + " points!");
					}

         
   }
   //calculated the winner by score
   public static void main(String [] args)
   {
      new project();
   }
   //main method 
  protected class Player extends JPanel {

		private Font font = new Font("Serif", Font.BOLD, 30);
      private int allow=0;
      private int allow2=0;
      //make the different Font 
		public Player() {
			playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
            if(playername1.equals(""))
         {
            while(allow==0)
            {
               JOptionPane.showMessageDialog(null,"You have to enter name!");
               playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
               if(!playername1.equals(""))
               {
               allow=1;
               }
            }  
         }
         // player 1 must input the name
			playername2 = JOptionPane.showInputDialog("Player 2 enter your name: ");
         if(playername2.equals(""))
         {
            while(allow2==0)
            {
               JOptionPane.showMessageDialog(null,"You have to enter name!");
               playername2 = JOptionPane.showInputDialog("Player 2 enter your name: ");
               if(!playername2.equals(""))
               {
               allow2=1;
               }
            }  
         }
         //player 2 must input the name
			player1 = new JLabel(playername1+": "+playerscore1+"                              ");
			player2 = new JLabel(playername2+": "+playerscore2);
			player1.setFont(font);
			player2.setFont(font);
			add(player1);	
			add(player2);
         //setup the player information and add to the GUI
		}

	}
   protected class Game extends JPanel implements Runnable
   {
    public Game()
    {
      setLayout(new GridLayout(8,8));
      for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					buttonArray[i][j] = new JRadioButton();
               buttonArray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
               h[i][j]=false;
               v[i][j]=false;
               win[i][j]=0;
					add(buttonArray[i][j]);
				}
      }
      //The setup the GUI for the Game using GridLayout
      
    }
    public void paint(Graphics g) {

			super.paint(g);
         
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 7; j++) {
					if (buttonArray[i][j].isSelected() && buttonArray[i][j + 1].isSelected()&&h[i][j]==false) {
	               h[i][j] = true;
                  count++;
                  count3++;
						deselect();
					} else if(buttonArray[i][j].isSelected() && buttonArray[i][j + 1].isSelected()&&h[i][j]==true){
                   deselect();
                  JOptionPane.showMessageDialog(null,"You can not connect this two!");
					}
				}//When two horizon dot are selected will draw line
             //And the count is to make it thing whitch player is playing
             //count 3 is total line drawed
            
            for (int i = 0; i < 7; i++)
				for (int j = 0; j < 8; j++) {
					if (buttonArray[i][j].isSelected() && buttonArray[i+1][j].isSelected()&&v[i][j]==false) {
	               v[i][j] = true;
                  count++;
                  count3++;
						deselect();
					} else if(buttonArray[i][j].isSelected() && buttonArray[i+1][j].isSelected()&&v[i][j]==true){
                   deselect();
                  JOptionPane.showMessageDialog(null,"You can not connect this two!");
					}
				}
            //Any two vertical dot are selected will draw line like horizon line
            
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
            if(h[i][j]==true){
            g.drawLine(buttonArray[i][j].getX()+55,buttonArray[i][j].getY()+38,buttonArray[i][j].getX()+168,buttonArray[i][j].getY()+38);
            }}}
            for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 8; j++) {
            if(v[i][j]==true){
            g.drawLine(buttonArray[i][j].getX()+55,buttonArray[i][j].getY()+38,buttonArray[i][j].getX()+55,buttonArray[i][j].getY()+113);
            }}}
        //draw line using getX and getY if the horizon array is true or vertical array is true
  }//paint

   public void deselect() {

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					buttonArray[i][j].setSelected(false);
				}

			}
     }
     //deselect everything after you selected 2 dot will deselect everything
   
   
 
    public void run()
    {
      while(true)//using while loop to check everytime
      {
         if(count%2==0)
         {
           player2.setForeground(Color.BLACK);
           player1.setForeground(Color.RED);
           count2=1;
         }else if(count%2==1)
         {
           player1.setForeground(Color.BLACK);
           player2.setForeground(Color.RED);
           count2=2;
         }
         //the currentplayer will be red
         repaint();
               for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
               if(h[i][j] == true && h[i+1][j] == true && v[i][j] == true && v[i][j+1] == true && count2==1&&win[i][j]==0)
               {
                                playerscore1++;
               player1.setText(playername1 + ": " + playerscore1 + "                              ");
               win[i][j]=1;
               count--;
               }//if the dot make a box will get 1 point and change the win array to 1 so will not will duplicate the score
               else if(h[i][j] == true && h[i+1][j] == true && v[i][j] == true && v[i][j+1] == true && count2==2&&win[i][j]==0)
               {
                                playerscore2++;
               player2.setText(playername2 + ": " + playerscore2);
               win[i][j]=1;
               count--;
               }
               // the score for player 2
               }}
               
               if(count3==112)
               {
                    
                  getWinner();
               
               }
               // if the all line is drawed so will calculcated the winner 
                    
      }
      
      
      
    }
   }
   
}