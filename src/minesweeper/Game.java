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
	int [] boomArray;
	int k;
	int remainBoom;
	boolean gameover;
	public Game() {
		super("Demo");
		chooseLevel=8;
		boomCount = 10;
		remainBoom = boomCount;
		Setting();
	}
	public void Setting()
	{
		
		gameover = false;
		container = getContentPane();

		buttons = new JButton[chooseLevel][];
		
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
		List<Integer> list = new ArrayList<Integer>();
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
			//int n = (int)(Math.random()*boomCount);
			//boomArray[count] = n;
			System.out.print(list.get(count) + " ");
		}
		System.out.println();
		
		k=0;
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
							
							//System.out.println("左鍵");
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
								System.out.println("踩到地雷 遊戲結束");
								JOptionPane.showMessageDialog(null, "Game Over", "Display Message",JOptionPane.INFORMATION_MESSAGE);
								
							
								buttonJPanel.removeAll();
								buttonJPanel2.removeAll();
								Setting();
							}else 
							{
								//?????顯示周遭幾顆地雷
								int total = 0;
								for(int smallrow = row-1;smallrow <= row +1;smallrow++)
								{
									for(int smallcol = col-1;smallcol <= col +1;smallcol++)
									{
										for(int boomNumber=0; boomNumber< boomCount;boomNumber++)
										{
											if((smallrow * chooseLevel + smallcol) == list.get(boomNumber))
											{
												total++;
											}
										}
									}
								}
								buttons[row][col].setText(""+total);
								buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 30));
							}
							
						}
						if(e.getButton() == MouseEvent.BUTTON3)//right mouse
						{
							remainBoom--;
							counterText2.setText("" + remainBoom);
							
							System.out.println("右鍵");
							buttons[row][col].setText("^");
							buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 30));
						}
						/*
						buttons[row-1][col].setBackground(Color.BLUE);
						buttons[row][col-1].setBackground(Color.BLUE);
						buttons[row+1][col].setBackground(Color.BLUE);
						buttons[row][col+1].setBackground(Color.BLUE);*/
					}
//					public void mouseEntered(MouseEvent e){}
//					public void mouseExited(MouseEvent e){}
//					public void mousePressed(MouseEvent e){}
//					public void mouseReleased(MouseEvent e){}

				});
				
				
				k++;
			}
		}
		
		
		
		
		
		String[] input = new String[7];
		input[0]= "重新開始";
		input[1]= "初級";
		input[2]= "中級";
		input[3]= "高級";
		input[4]= "CHOOSE";
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
		
		counterTime2 = new JTextField("0");
		counterTime2.setFont(new Font("Arial", Font.PLAIN, 30));
		counterTime2.setHorizontalAlignment(JTextField.CENTER);
		
		buttonJPanel3.add(counterText);
		buttonJPanel3.add(counterText2);
		buttonJPanel3.add(counterTime);
		buttonJPanel3.add(counterTime2);
		
		
		add(buttonJPanel, BorderLayout.CENTER);
		add(buttonJPanel2, BorderLayout.NORTH);
		add(buttonJPanel3, BorderLayout.SOUTH);
		container.revalidate();//??validate
		
	}
	class ReStartListener implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{
			remainBoom = boomCount;
			buttonJPanel.removeAll();
			buttonJPanel2.removeAll();
			Setting();
		}
	}
	class EasyListener implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{
			
			chooseLevel= 8;
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
			
			chooseLevel=12;
			boomCount = 20;
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
			boomCount = 50;
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
