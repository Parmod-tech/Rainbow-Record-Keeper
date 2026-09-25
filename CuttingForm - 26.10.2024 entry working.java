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


public class CuttingForm 
{

static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JTextField textWt, textQty, textCID, textCDate;
static JFormattedTextField cdate;
static JComboBox<String> combopart, ComboHeat;



static int cuttingid;
static String partname;
static int heatcode;
static Float cutwt;
static int cutqty;
static int partid;
static int heatid;
static java.sql.Date sqlctdt;
static long milisec;


//Method to get data--------------------
private static void getValues()
{
	cutwt = Float.parseFloat(textWt.getText());	
	cutqty=Integer.parseInt(textQty.getText());	

	java.util.Date utldt=(java.util.Date) cdate.getValue(); 
	milisec=utldt.getTime();
	sqlctdt=new java.sql.Date(milisec);
	
	
	System.out.println("Cutt wt is: "+cutwt);
	System.out.println("Cutt Qty is: "+cutqty);	
	System.out.println("util Date is: "+utldt);
	System.out.println("milisec is: "+milisec);
	System.out.println("sql Date is : "+sqlctdt);
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

//Main Class Begins-------------------------------------------	
		public static void main(String[] arges) 
		{
//Frame and panels
		frame1 = new JFrame("BSFL (P-10): Cutting Entry");
		frame1.setSize(800,300);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(7,2));	
		panel2 = new JPanel();	
		panel3 = new JPanel();	
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");
		frame1.add(panel3, "South");

//Cutting id
		JLabel  cuttid = new JLabel("Cutting ID");
		panel1.add(cuttid);
		textCID = new JTextField(16);
		panel1.add(textCID);

//Cutting Date
		JLabel  cuttdate = new JLabel("Cutting Date ( YYYY-MM-DD )");
		panel1.add(cuttdate);
		DateFormat dtype = new SimpleDateFormat("yyyy-MM-dd");
		cdate= new JFormattedTextField(dtype);
		panel1.add(cdate);
		


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

//Cut Wt		

		JLabel  CuttWt = new JLabel("Cutt Wt");
		CuttWt.setBounds(100,50,100,50);
		panel1.add(CuttWt);
		textWt = new JTextField(16);
		panel1.add(textWt);
		

//Cut Qty		
		JLabel  CuttQty = new JLabel("Cutt Qty");
		CuttQty.setBounds(100,50,100,50);
		panel1.add(CuttQty);
		textQty = new JTextField(16);
		panel1.add(textQty);


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


		

		
//Buttons Change Data 
		JButton btnChange = new JButton("Change");
		panel2.add(btnChange);

		

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
				dbcon();
				
				try
					{
					
					String queryadd="Insert into entry_cutting (Cutt_Date, Part_ID, Heat_ID, Cutt_Wt, Cutt_qty) Values (?, ?, ?, ?, ?)";
					
					
					
					psmt=con.prepareStatement(queryadd);
					psmt.setDate(1, sqlctdt);
					psmt.setInt(2, partid);
					psmt.setInt(3, heatid);
					psmt.setFloat(4, cutwt);
					psmt.setInt(5, cutqty);


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

//Button Previsou 
	ActionListener previous=new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				String ttx=textCID.getText();
				int idnot=Integer.parseInt(ttx);
				if(idnot>0)
					{
								
					idnot=idnot-1;
					String tid=Integer.toString(idnot);
					textCID.setText(tid);
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
		





//Find Entry ID Data
		JButton btnFind = new JButton("Find");
		panel3.add(btnFind );
		ActionListener acfind=new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
			{
			String ttx=textCID.getText();
			int idnot=Integer.parseInt(ttx);
				if(idnot>0)
				{
				try
				{
				dbcon();
				String qryfind=" SELECT * FROM cuttingentry_readable where Cutt_ID=?";
				psmt=con.prepareStatement(qryfind);
				psmt.setInt(1,idnot);
				rs=psmt.executeQuery();
				while(rs.next())
				{
				textCID.setText(Integer.toString(rs.getInt(1)));
				cdate.setValue((rs.getDate(1)));
				combopart.setSelectedItem(rs.getString(7));
				ComboHeat.setSelectedItem(rs.getString(8));
				textWt.setText(String.valueOf(rs.getInt(4)));
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


//Button  Next

		JButton btnNext = new JButton("Next");
		panel3.add(btnNext );	

		ActionListener next=new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				String ttx=textCID.getText();
				int idnot=Integer.parseInt(ttx);
				if(idnot>0)
					{
					
					idnot=idnot+1;
					String tid=Integer.toString(idnot);
					textCID.setText(tid);
					}
				else
					{
													JOptionPane.showMessageDialog(frame1,"Please enter Valid ID No.");
					}				


				}	
			};
		
		btnNext.addActionListener(next);
		
		frame1.show();
		}


}