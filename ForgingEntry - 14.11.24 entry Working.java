// Java Frame for cutting

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ForgingEntry 
{

static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JTextField textfentry, textfshift, textfqty, textfshifthrs;
static JFormattedTextField fdate;
static JComboBox<String> combopress, combopart, ComboHeat;



static int fentryid;
static String partname;
static String pressname;
static String fshift;
static int heatcode;
static int fqty;
static int partid;
static int heatid;
static int pressid;
static int shifthrs;
static java.sql.Date sqlfdt;
static long milisec;
static int lastfentryid;


//Method to get data--------------------
private static void getValues()
{
	
	fqty=Integer.parseInt(textfqty.getText());

	fshift=textfshift.getText();
	shifthrs=Integer.parseInt(textfshifthrs.getText());

	java.util.Date utldt=(java.util.Date) fdate.getValue(); 
	milisec=utldt.getTime();
	sqlfdt=new java.sql.Date(milisec);
	
	
}

//Method to Connect to Database -----------------------------
static void dbcon()
{
	try
	{Class.forName("com.mysql.cj.jdbc.Driver");
	con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/pkdb","root","Parmod@123");
	JOptionPane.showMessageDialog(frame1,"Connection Extablished Successfully");
	}
	

	catch(Exception e)
	{ 
	System.out.println(e);
	}  

}

//Method to Close Connection to Database------------------------
static void dbclose()
{ 	try 
	{
	rs.close();
	con.close();
	}
	catch(Exception e)
	{System.out.println(e);}
}
		
//Method to get last Forging Entry ID
static void getLastForgingEntry()
{
			try
				{
				dbcon();
				smt=con.createStatement();
				rs=smt.executeQuery("select max(EntryID) from entry_prod");
				while (rs.next())
				{lastfentryid=rs.getInt(1);}
				
				JOptionPane.showMessageDialog(frame1,"Entry ID: "+lastfentryid+"  Added Successfully!");
				smt.close();
				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}

}



//Method to get Part id-----------------

static void getPartId()
{
			try
				{
				dbcon();
				String querypart="SELECT Part_ID FROM en_parts where Part_Name=?";
				psmt=con.prepareStatement(querypart);
				psmt.setString(1, partname);
				rs=psmt.executeQuery();
				while (rs.next())
				{partid=rs.getInt(1);}
				
				
				psmt.close();
				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}

}


//Method to get Heat Id---------------
static void getHeatId()
{
			try
				{
				dbcon();
				String queryheatid="SELECT Heat_ID FROM entry_heatcodes where Heat_No=?";
				psmt=con.prepareStatement(queryheatid);
				psmt.setInt(1, heatcode);
				rs=psmt.executeQuery();
				while (rs.next())
				{heatid=rs.getInt(1);}
				
				
				psmt.close();
				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}



}

//Method to get Heat Id---------------
static void getPressId()
{
			try
				{
				dbcon();
				String querypressid="SELECT Press_ID FROM en_presses where Press_Code=?";
				psmt=con.prepareStatement(querypressid);
				psmt.setString(1, pressname);
				rs=psmt.executeQuery();
				while (rs.next())
				{pressid=rs.getInt(1);}
				
				
				psmt.close();
				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}



}



//Main Class Begins==================================================	
		public static void main(String[] arges) 
		{
//Frame and panels
		frame1 = new JFrame("BSFL (P-10): Cutting Entry");
		frame1.setSize(800,400);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(9,2));	
		panel2 = new JPanel();	
		panel3 = new JPanel();	
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");
		frame1.add(panel3, "South");

//Prod Entry id
		JLabel  fentry = new JLabel("Forging Entry ID");
		panel1.add(fentry);
		textfentry = new JTextField(16);
		panel1.add(textfentry);

//Forging Date
		JLabel  lblfdate = new JLabel("Forging Date ( YYYY-MM-DD )");
		panel1.add(lblfdate);
		DateFormat dtype = new SimpleDateFormat("yyyy-MM-dd");
		fdate= new JFormattedTextField(dtype);
		panel1.add(fdate);

//Combo Press Name		
		JLabel  lblpressname = new JLabel("Press Name");
		panel1.add(lblpressname);
		combopress = new JComboBox();
		panel1.add(combopress);	
		ActionListener presslist = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				combopress = (JComboBox) e.getSource();	
				pressname= (String) combopress.getSelectedItem();
				}
			};

		combopress.addActionListener(presslist);

		
//Forging Shift
		JLabel  lblfshift = new JLabel("Shift (A / B)");
		panel1.add(lblfshift);
		textfshift = new JTextField(1);
		panel1.add(textfshift);

//Forging Shift Hours
		JLabel  lblfshifthrs = new JLabel("Shift Hrs ");
		panel1.add(lblfshifthrs);
		textfshifthrs = new JTextField(1);
		panel1.add(textfshifthrs);


//Combo Part Name		
		JLabel  PartName = new JLabel("PartName");
		panel1.add(PartName);
		combopart = new JComboBox();
		panel1.add(combopart);	
		ActionListener partlist = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				combopart = (JComboBox) e.getSource();	
				partname= (String) combopart.getSelectedItem();
				}
			};

		combopart.addActionListener(partlist);


//Combo Heat Code 
		JLabel  HeatCode = new JLabel("HeatCode");
		HeatCode.setBounds(100,50,100,50);
		panel1.add(HeatCode);
		ComboHeat = new JComboBox();
		panel1.add(ComboHeat);	
		ActionListener partlistco = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				ComboHeat = (JComboBox) e.getSource();	
				heatcode= Integer.parseInt((String)ComboHeat.getSelectedItem());
				}

			};

		ComboHeat.addActionListener(partlistco);

		

//Forged Qty		
		JLabel  lblfqty = new JLabel("Forging Qty");
		lblfqty.setBounds(100,50,100,50);
		panel1.add(lblfqty);
		textfqty = new JTextField(16);
		panel1.add(textfqty);


//Button Part List Refress 
		JButton btnRefPart = new JButton("Ref Parts");
		panel2.add(btnRefPart);	
		btnRefPart.addActionListener(new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{	
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Part_ID, Part_Name FROM en_parts order by Part_Name asc");
						while(rs.next()) 
						{
						combopart.addItem(rs.getString(2));
						}

					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			}
			);


//Button Heat Code List Refresh

		JButton btnRefHeat = new JButton("Ref heat");
		panel2.add(btnRefHeat);
		
		btnRefHeat.addActionListener(new ActionListener()
			{
			
			public void actionPerformed(ActionEvent e)
				{	
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Heat_No FROM entry_heatcodes order by Heat_No asc");


					while(rs.next()) 
						{
						ComboHeat.addItem(rs.getString(1));
						}

					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			}

		);

//Button Press List Refresh

		JButton btnrefpress = new JButton("Ref press");
		panel2.add(btnrefpress);
		
		btnrefpress.addActionListener(new ActionListener()
			{
			
			public void actionPerformed(ActionEvent e)
				{	
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Press_Code FROM en_presses");


					while(rs.next()) 
						{
						combopress.addItem(rs.getString(1));
						}

					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			}
			);


		

/*		
//Buttons Change Data 
		JButton btnChange = new JButton("Change");
		panel2.add(btnChange);
		btnChange.addActionListener(new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{	
				getValues();
				getHeatId();
				getPartId();
				getPartId();
				dbcon();
				
				try
					{
					
					String querychange=("Update entry_cutting set Cutt_Date=?, Part_ID=?, Heat_ID=?, Cutt_Wt=?, Cutt_qty=? where Cutt_ID=?");
					cuttingid=Integer.parseInt(textCID.getText());
					
					psmt=con.prepareStatement(querychange);
					psmt.setDate(1, sqlctdt);
					psmt.setInt(2, partid);
					psmt.setInt(3, heatid);
					psmt.setFloat(4, cutwt);
					psmt.setInt(5, cutqty);
					psmt.setInt(6, cuttingid);


					psmt.executeUpdate();
					psmt.close();
			
					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			}
			);




*/		

//Button Add Data to Database 
		
		JButton btnAdd = new JButton("Add New");
		panel2.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{	
				getValues();
				getHeatId();
				getPartId();
				getPressId();
			
				dbcon();
				
				try
					{
					
					String queryadd="Insert into entry_prod (PressID, Shift, Part_ID, ProdQty, ShiftHrs, prod_Date, HeatID) Values (?, ?, ?, ?, ?, ?, ?)";
					
					
					
					psmt=con.prepareStatement(queryadd);
					psmt.setInt(1, pressid);
					psmt.setString(2, fshift);
					psmt.setInt(3, partid);
					psmt.setInt(4, fqty);
					psmt.setInt(5, shifthrs);
					psmt.setDate(6, sqlfdt);
					psmt.setInt(7, heatid);


					psmt.executeUpdate();
					psmt.close();
					dbclose();
					getLastForgingEntry();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			}
			);


//Button Previsou 
	ActionListener previous=new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				String ttx=textfentry.getText();
				int idnot=Integer.parseInt(ttx);
				if(idnot>0)
					{
								
					idnot=idnot-1;
					String tid=Integer.toString(idnot);
					textfentry.setText(tid);
					}				
				else
					{
					JOptionPane.showMessageDialog(frame1,"Please enter Valid ID No.");
					}
				}
			};


		JButton btnPrevious = new JButton("Previous");
		panel3.add(btnPrevious);
		btnPrevious.addActionListener(previous);
		




/*
//Find Data----------------------------------------
		JButton btnFind = new JButton("Find");
		panel3.add(btnFind );
		ActionListener acfind=new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
			{
			String ttx=textfentry.getText();
			int idnum=Integer.parseInt(ttx);
				if(idnum>0)
				{
				try
				{
				dbcon();
				String qryfind=" SELECT * FROM entry_prod where EntryID=?";
				psmt=con.prepareStatement(qryfind);
				psmt.setInt(1,idnum);
				rs=psmt.executeQuery();
				while(rs.next())
				{
				textfentry.setText(Integer.parseInt(rs.getInt(1)));


				java.sql.Date fdt1=(java.sql.Date) rs.getDate(8);
				long ldt=sdt1.getTime();
				java.util.Date retriveddt= new java.util.Date(ldt);
				textfdate.setDate(retriveddt);
				//System.out.println("Part name is "+(rs.getString(7)));
				//System.out.println("Item is"+(rs.getString(8)));
				//System.out.println("wt is "+(String.valueOf(rs.getFloat(4))));
				//System.out.println("qty is "+(rs.getInt(6)));

				
				textCID.setText(Integer.toString(rs.getInt(1)));
				
				
				long dtmili=rs.getDate(2).getTime();							java.util.Date dt1=new java.util.Date(dtmili);



				cdate.setValue(dt1);
				combopart.setSelectedItem(rs.getString(7));
				ComboHeat.setSelectedItem(rs.getString(8));
				textWt.setText(String.valueOf(rs.getFloat(4)));
				textQty.setText(Integer.toString(rs.getInt(6)));
				
				}
				psmt.close();
				dbclose();
				}

				catch(Exception e1)
				{ System.out.println(e1);}
				}

				else
				{
				JOptionPane.showMessageDialog(frame1,"Please enter Valid ID No.");
				}
			}


		};
		
		btnFind.addActionListener(acfind);

*/
//Button  Next

		JButton btnNext = new JButton("Next");
		panel3.add(btnNext );	

		ActionListener next=new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				String ttx=textfentry.getText();
				int idnot=Integer.parseInt(ttx);
									
					idnot=idnot+1;
					String tid=Integer.toString(idnot);
					textfentry.setText(tid);
					
					dbcon();
				}
								


			};	
			
		
		btnNext.addActionListener(next);
		
		frame1.show();
		}


}