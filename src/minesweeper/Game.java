package minesweeper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class Game extends JFrame{
	JFrame frame;
	JLabel label;
	JButton[][] buttons;
	JButton[] buttons2;
	
	JPanel buttonJPanel;//main board
	JPanel buttonJPanel2;//top menu
	JPanel buttonJPanel3;//down menu
	
	JTextField counterText;//the step frequency text
	JTextField counterText2;//show the step frequency
	JTextField counterTime;//the total time text
	JTextField counterTime2;//show the total time
	
	Container container;
	int chooseLevel;
	int count,count2;//use to for loop board
	int boomCount;
	int timeCount;
	int [] boomArray;
	int remainBoom;
	
	boolean gameover;
	int gamefinish;
	List<Integer> list;
	
	public Game() {
		super("Minesweeper");
		chooseLevel=9;
		boomCount = 10;
		remainBoom = boomCount;
		
		Setting();
	}
	public void showNeighbor(int row, int col)
	{
		
		if(row >=0 && row <= chooseLevel -1 && col >=0 && col <=chooseLevel - 1)
		{
			int total = 0;
			for(int smallrow = row-1;smallrow <= row +1;smallrow++)
			{	if(smallrow <0)
				{	
					smallrow=0;
					
				}
				else if(smallrow >= chooseLevel)
				{	
					smallrow = chooseLevel-1;
					break;
				}
				for(int smallcol = col-1;smallcol <= col +1;smallcol++)
				{
					if(smallcol <0)
					{	
						smallcol=0;
						
					}
					else if(smallcol >= chooseLevel)
					{	
						smallcol = chooseLevel-1;
						break;
					}
					
					for(int boomNumber=0; boomNumber< boomCount;boomNumber++)
					{
						if((smallrow * chooseLevel + smallcol) == list.get(boomNumber))
						{
							total++;
						}
					}
				}
			}
			
			
			
			if(total == 0)
			{	
				buttons[row][col].setEnabled(false);
				
				if(row-1 >=0 && buttons[row-1][col].isEnabled() == true)
					showNeighbor(row-1,col);
				if(col-1 >=0 && buttons[row][col-1].isEnabled() == true)
					showNeighbor(row,col-1);
				if(row+1 <= chooseLevel-1 && buttons[row+1][col].isEnabled() == true)
					showNeighbor(row+1,col);
				if(col+1 <= chooseLevel-1 && buttons[row][col+1].isEnabled() == true)
					showNeighbor(row,col+1);
			}
			else
			{	
				buttons[row][col].setText(""+total);
				buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 30));
			}
		}
		
		
		
		
	}
	public void Setting()
	{
		final Timer timer  = new Timer();
		
		timeCount = 0;
		gameover = false;
		container = getContentPane();

		buttons = new JButton[chooseLevel][];
		remainBoom = boomCount;
		for(int levelCount = 0;levelCount < chooseLevel;levelCount++)
		{
			buttons[levelCount] = new JButton[chooseLevel];
		}
		
		
		buttons2 = new JButton[5];
		
		buttonJPanel = new JPanel();
		buttonJPanel.setLayout(new GridLayout(chooseLevel, chooseLevel));
		
		buttonJPanel2 = new JPanel();
		buttonJPanel2.setLayout(new GridLayout(1, 5));
	
		buttonJPanel3 = new JPanel();
		buttonJPanel3.setLayout(new GridLayout(1, 4));
		
		boomArray = new int[boomCount];
		list = new ArrayList<Integer>();
		Random rand = new Random();
		do {
			int next = rand.nextInt(chooseLevel*chooseLevel);
			if(!list.contains(next))
			{
				list.add(next);
			}
		}while(list.size()< boomCount);
		
		
		for(count = 0; count < boomCount; count++)
		{
			//System.out.print(list.get(count) + " ");
		}
		for(count = 0; count < chooseLevel; count++)
		{
			for(count2 = 0; count2 < chooseLevel; count2++)
			{
				buttons[count][count2] = new JButton("");
				buttonJPanel.add(buttons[count][count2]);
				final int row = count;
				final int col = count2;
				buttons[row][col].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e)
					{
						
						if(e.getButton() == MouseEvent.BUTTON1)//left mouse
						{
							if(timeCount==0)
							{
								timer.schedule(new TimerTask() 
								{
									public void run() {
										timeCount ++;
										counterTime2.setText(""+timeCount);
									}
								
								},0,1000);	
							}
							
						
							for(int boomNumber=0; boomNumber< boomCount;boomNumber++)
							{
								if((row * chooseLevel + col) == list.get(boomNumber))//judge if is a boom
								{
									buttons[row][col].setText("*");
									buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 30));
									gameover = true;
									break;
								}
							}
							
							
							
							
							if(gameover)//game over, then start a new game
							{
								for(int countSmall = 0; countSmall < chooseLevel; countSmall++)
								{
									for(int countSmall2 = 0; countSmall2 < chooseLevel; countSmall2++)
									{
										for(int boomNumber=0; boomNumber< boomCount;boomNumber++)
										{	//show the location of all Booms
											if((countSmall * chooseLevel + countSmall2) == list.get(boomNumber))
											{
												buttons[countSmall][countSmall2].setText("*");
												buttons[countSmall][countSmall2].setFont(new Font("Arial", Font.PLAIN, 30));
											}
										}
									}
								}
								timer.cancel();
								timer.purge();
								System.out.println("踩到地雷 遊戲結束");
								JOptionPane.showMessageDialog(null, "Game Over", "Display Message",JOptionPane.INFORMATION_MESSAGE);
								
								
								buttonJPanel.removeAll();
								buttonJPanel2.removeAll();
								Setting();//restart a new game
							}else 
							{
								showNeighbor(row, col);//show neighbor number of booms
								
								gamefinish = 0;
								
								for(int countSmall = 0; countSmall < chooseLevel; countSmall++)
								{
									for(int countSmall2 = 0; countSmall2 < chooseLevel; countSmall2++)
									{
										
										if(buttons[countSmall][countSmall2].isEnabled() == false||
										   buttons[countSmall][countSmall2].getText().equals("1") ||
										   buttons[countSmall][countSmall2].getText().equals("2") ||	
										   buttons[countSmall][countSmall2].getText().equals("3") ||
										   buttons[countSmall][countSmall2].getText().equals("4") ||
										   buttons[countSmall][countSmall2].getText().equals("5") ||	
										   buttons[countSmall][countSmall2].getText().equals("6") ||
										   buttons[countSmall][countSmall2].getText().equals("7") ||
										   buttons[countSmall][countSmall2].getText().equals("8") ||	
										   buttons[countSmall][countSmall2].getText().equals("9") ||
										   buttons[countSmall][countSmall2].getText().equals("^")
										  )	
										{
											gamefinish = 1;
										}else {
											
											gamefinish = -1; 
											break;
										}
									}
									
									if(gamefinish == -1)
										break;	
								}
								
								if(gamefinish == 1)
								{
									timer.cancel();
									timer.purge();
									System.out.println("遊戲成功");
									JOptionPane.showMessageDialog(null, "Game Finish", "Display Message",JOptionPane.INFORMATION_MESSAGE);
									
								
									buttonJPanel.removeAll();
									buttonJPanel2.removeAll();
									Setting();
								}	
							}
							
						}
						if(e.getButton() == MouseEvent.BUTTON3)//right mouse
						{
							
							
							if(buttons[row][col].getText().equals(""))//^ means this location has boom
							{
								remainBoom--;
								counterText2.setText("" + remainBoom);
								buttons[row][col].setText("^");
								buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 30));
							}
							else if(buttons[row][col].getText().equals("^"))
								buttons[row][col].setText("?");
							else if(buttons[row][col].getText().equals("?"))
							{	
								buttons[row][col].setText("");
								remainBoom++;
								counterText2.setText("" + remainBoom);	
							}
							
							gamefinish = 0;
							for(int countSmall = 0; countSmall < chooseLevel; countSmall++)
							{
								for(int countSmall2 = 0; countSmall2 < chooseLevel; countSmall2++)
								{
									if(buttons[countSmall][countSmall2].isEnabled() == false||
									   buttons[countSmall][countSmall2].getText().equals("1") ||
									   buttons[countSmall][countSmall2].getText().equals("2") ||	
									   buttons[countSmall][countSmall2].getText().equals("3") ||
									   buttons[countSmall][countSmall2].getText().equals("4") ||
									   buttons[countSmall][countSmall2].getText().equals("5") ||	
									   buttons[countSmall][countSmall2].getText().equals("6") ||
									   buttons[countSmall][countSmall2].getText().equals("7") ||
									   buttons[countSmall][countSmall2].getText().equals("8") ||	
									   buttons[countSmall][countSmall2].getText().equals("9") ||
									   buttons[countSmall][countSmall2].getText().equals("^")
									  )	
									{
										gamefinish = 1;
									}else {
										gamefinish = -1;
										break;
									}
								}
								if(gamefinish == -1)
									break;	
							}
							if(gamefinish == 1)
							{
								System.out.println("遊戲成功");
								JOptionPane.showMessageDialog(null, "Game Finish", "Display Message",JOptionPane.INFORMATION_MESSAGE);
								
							
								buttonJPanel.removeAll();
								buttonJPanel2.removeAll();
								Setting();
							}	
						}
						
					}

				});
				
				
				
			}
		}
		
		
		
		
		
		String[] input = new String[7];
		input[0]= "重新開始";
		input[1]= "初級";
		input[2]= "中級";
		input[3]= "高級";
		input[4]= "設定";
		input[5]= "剩餘地雷";
		input[6]= "時間";
		for(count = 0; count < 5;count++)
		{
			
			buttons2[count] = new JButton(input[count]);
			
			buttonJPanel2.add(buttons2[count]);
		}
		buttons2[0].addActionListener(new ReStartListener());
		buttons2[1].addActionListener(new EasyListener());
		buttons2[2].addActionListener(new MidListener());
		buttons2[3].addActionListener(new HardListener());
	/*-------------------------------------------------------------------*/	
		counterText = new JTextField(input[5]);
		counterText.setFont(new Font("\\u6B22\\u8FCE\\u4F7F\\u7528", Font.PLAIN, 30));
		counterText.setHorizontalAlignment(JTextField.RIGHT);
		
		counterText2 = new JTextField("" + boomCount);
		counterText2.setFont(new Font("Arial", Font.PLAIN, 30));
		counterText2.setHorizontalAlignment(JTextField.CENTER);
		
		counterTime = new JTextField(input[6]);
		counterTime.setFont(new Font("\\u6B22\\u8FCE\\u4F7F\\u7528", Font.PLAIN, 30));
		counterTime.setHorizontalAlignment(JTextField.RIGHT);
		
		counterTime2 = new JTextField("" + timeCount);
		counterTime2.setFont(new Font("Arial", Font.PLAIN, 30));
		counterTime2.setHorizontalAlignment(JTextField.CENTER);
		
		buttonJPanel3.add(counterText);
		buttonJPanel3.add(counterText2);
		buttonJPanel3.add(counterTime);
		buttonJPanel3.add(counterTime2);
		
		
		add(buttonJPanel, BorderLayout.CENTER);
		add(buttonJPanel2, BorderLayout.NORTH);
		add(buttonJPanel3, BorderLayout.SOUTH);
		container.validate();//??validate
		
	}
	class ReStartListener implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{
			remainBoom = boomCount;
			buttonJPanel.removeAll();
			buttonJPanel2.removeAll();
			timeCount = 0;
			Setting();
		}
	}
	class EasyListener implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{
			
			chooseLevel= 9;
			boomCount = 10;
			remainBoom = boomCount;
			
			buttonJPanel.removeAll();
			buttonJPanel2.removeAll();
			//buttonJPanel.setVisible(false);
			//buttonJPanel2.setVisible(false);
			Setting();
		}
	}
	
	class MidListener implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{
			
			chooseLevel=16;
			boomCount = 40;
			remainBoom = boomCount;
			
			buttonJPanel.removeAll();
			buttonJPanel2.removeAll();
			//buttonJPanel.setVisible(false);
			//buttonJPanel2.setVisible(false);
			Setting();
		}
	}
	class HardListener implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{
			
			chooseLevel=20;
			boomCount = 99;
			remainBoom = boomCount;
			
			buttonJPanel.removeAll();
			buttonJPanel2.removeAll();
			//buttonJPanel.setVisible(false);
			//buttonJPanel2.setVisible(false);
			Setting();
		}
	}
	
	
	public static void main(String[] args) {
		Game panelFrame = new Game();
		panelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelFrame.setSize(1200,800);
		panelFrame.setVisible(true);

	}
	
}
