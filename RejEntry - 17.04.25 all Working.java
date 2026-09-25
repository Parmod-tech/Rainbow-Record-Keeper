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


public class RejEntry 
{

static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JTextField tb_rej_en_id, tbrejqty, txtheatcode;
static JFormattedTextField fbrejdate;
static JComboBox<String> cbrejarea, cbpartname, cbdefname, cbhtcode;



static int rejenid;
static String rejarea;
static int rejareaid;

static String partname;
static int partid;
static String defname;
static int defnameid;
static int rejqty;
static int HeatCode;
static int heatid;
static java.sql.Date rejdate;
static long milisec;
static int lastrejentryid;


//Method to get data--------------------
private static void getValues()
{
	rejqty=Integer.parseInt(tbrejqty.getText());
	HeatCode=Integer.parseInt(txtheatcode.getText());

	java.util.Date utldt=(java.util.Date) fbrejdate.getValue(); 
	milisec=utldt.getTime();
	rejdate=new java.sql.Date(milisec);
	
	
}

//Method to Connect to Database -----------------------------
static void dbcon()
{
	try
	{Class.forName("com.mysql.cj.jdbc.Driver");
	con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/pkdb","root","Parmod@123");
	
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
static void getLastRejEntryId()
{
			try
				{
				dbcon();
				smt=con.createStatement();
				rs=smt.executeQuery("select max(RejEntry_ID) from entry_rej");
				while (rs.next())
				{lastrejentryid=rs.getInt(1);}
				
				JOptionPane.showMessageDialog(frame1,"Entry ID: "+lastrejentryid+"  Added Successfully!");
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
				psmt.setInt(1, HeatCode);
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

//Method to get Defect Name Id---------------
static void getDefId()
{
			try
				{
				dbcon();
				String sdefnameid="SELECT Defect_ID FROM en_defects where Defect_Name=?";
				psmt=con.prepareStatement(sdefnameid);
				psmt.setString(1, defname);
				rs=psmt.executeQuery();
				while (rs.next())
				{defnameid=rs.getInt(1);}
				
				
				psmt.close();
				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}



}


//Method to get Rejection Area Id---------------
static void getRejAreaId()
{
			try
				{
				dbcon();
				String defareaid="SELECT RejArea_ID FROM en_rejareas where Rej_Area=?";
				psmt=con.prepareStatement(defareaid);
				psmt.setString(1, rejarea);
				rs=psmt.executeQuery();
				while (rs.next())
				{rejareaid=rs.getInt(1);}
				
				
				psmt.close();
				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}



}

//Method to Clear boxes
	static void getClear()
	{
	
	tbrejqty.setText("");
	txtheatcode.setText("");
		

	}



//Main Class Begins==================================================	
		public static void main(String[] arges) 
		{
//Frame and panels
		frame1 = new JFrame("BSFL (P-10): Rejection Entry");
		frame1.setSize(600,400);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(9,2));	
		panel2 = new JPanel();	
		panel3 = new JPanel();	
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");
		frame1.add(panel3, "South");

//Rej Entry id
		JLabel  lblrejenid = new JLabel("      Rejection Entry ID");
		panel1.add(lblrejenid);
		tb_rej_en_id = new JTextField(16);
		panel1.add(tb_rej_en_id);



//Combo Rejection Area ------------------------		
		JLabel  lblareaname = new JLabel("      Rej Area Name");
		panel1.add(lblareaname);
		cbrejarea = new JComboBox();
		panel1.add(cbrejarea);	
		ActionListener acrejarea = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				cbrejarea = (JComboBox) e.getSource();	
				rejarea= (String) cbrejarea.getSelectedItem();
				}
			};

		cbrejarea.addActionListener(acrejarea);

//Rejection Date
		JLabel  lblrejdate = new JLabel("      Rejection Date ( YYYY-MM-DD )");
		panel1.add(lblrejdate);
		DateFormat dtype = new SimpleDateFormat("yyyy-MM-dd");
		fbrejdate= new JFormattedTextField(dtype);
		panel1.add(fbrejdate);


//Combo Part Name		
		JLabel  PartName = new JLabel("      PartName");
		panel1.add(PartName);
		cbpartname = new JComboBox();
		panel1.add(cbpartname);	
		ActionListener partlist = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				cbpartname= (JComboBox) e.getSource();	
				partname= (String) cbpartname.getSelectedItem();
				}
			};

		cbpartname.addActionListener(partlist);



//Combo Defect Name		
		JLabel  lbldefname = new JLabel("      Defect Name");
		panel1.add(lbldefname);
		cbdefname = new JComboBox();
		panel1.add(cbdefname);	
		ActionListener acdefname = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				cbdefname = (JComboBox) e.getSource();	
				defname= (String) cbdefname.getSelectedItem();
				}
			};

		cbdefname.addActionListener(acdefname);



		

//Rejection Qty		
		JLabel  lblrqty = new JLabel("      Rejection Qty");
		lblrqty.setBounds(100,50,100,50);
		panel1.add(lblrqty);
		tbrejqty = new JTextField(16);
		panel1.add(tbrejqty);

//Text Box Heat Code 
		JLabel  lblHeatCode = new JLabel("      HeatCode");
		lblHeatCode.setBounds(100,50,100,50);
		panel1.add(lblHeatCode);
		txtheatcode = new JTextField(4);
		panel1.add(txtheatcode);	
		

//Button Add Data to Database 
		
		JButton btnAdd = new JButton("Add New");
		panel2.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{	
				
				getValues();
				
				getPartId();
				getRejAreaId();
				getDefId();			
				dbcon();
				
				try
					{
					
					String queryadd="Insert into entry_rej (RejArea_ID, RejEntry_Date, Part_ID, Defect_ID, Rej_Qty, Heat_Code) Values (?, ?, ?, ?, ?, ?)";
					
					
					
					psmt=con.prepareStatement(queryadd);
					psmt.setInt(1, rejareaid);
					psmt.setDate(2, rejdate);
					psmt.setInt(3, partid);
					psmt.setInt(4, defnameid);
					psmt.setInt(5, rejqty);
					psmt.setInt(6, HeatCode);


					psmt.executeUpdate();
					psmt.close();
					dbclose();
					getLastRejEntryId();
					getClear();
					cbpartname.requestFocus();
					
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			}
			);




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
						cbpartname.addItem(rs.getString(2));
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
						cbhtcode.addItem(rs.getString(1));
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
*/

//Button Rej Area List Refresh

		JButton btnrefarea = new JButton("Ref rej area");
		panel2.add(btnrefarea);
		
		btnrefarea.addActionListener(new ActionListener()
			{
			
			public void actionPerformed(ActionEvent e)
				{	
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Rej_Area FROM en_rejareas");


					while(rs.next()) 
						{
						cbrejarea.addItem(rs.getString(1));
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

//Button Defect Name List Refresh

		JButton btnrefdef = new JButton("Ref Defects");
		panel2.add(btnrefdef);
		
		btnrefdef.addActionListener(new ActionListener()
			{
			
			public void actionPerformed(ActionEvent e)
				{	
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Defect_Name FROM en_defects");


					while(rs.next()) 
						{
						cbdefname.addItem(rs.getString(1));
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
		btnChange.addActionListener(new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				
				getValues();
				
				getPartId();
				getRejAreaId();
				getDefId();	
						
				dbcon();
				String ttx=tb_rej_en_id.getText();
				int idnot=Integer.parseInt(ttx);
				try
					{
					
					String querychange="update entry_rej set RejEntry_ID=?, RejArea_ID=?, RejEntry_Date=?, Part_ID=?, Defect_ID=?, Rej_Qty=?, Heat_Code=? where RejEntry_ID=?";
					
					
					
					psmt=con.prepareStatement(querychange);
					psmt.setInt(1, idnot);
					psmt.setInt(2, rejareaid);
					psmt.setDate(3, rejdate);
					psmt.setInt(4, partid);
					psmt.setInt(5, defnameid);
					psmt.setInt(6, rejqty);
					psmt.setInt(7, HeatCode);
					psmt.setInt(8, idnot);

					psmt.executeUpdate();
					psmt.close();
					dbclose();

					JOptionPane.showMessageDialog(frame1,"Entry ID: "+idnot+"  updated Successfully!");
					getClear();
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
				String ttx=tb_rej_en_id.getText();
				int idnot=Integer.parseInt(ttx);
				if(idnot>0)
					{
								
					idnot=idnot-1;
					String tid=Integer.toString(idnot);
					tb_rej_en_id.setText(tid);
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
		





//Find Data----------------------------------------
		JButton btnFind = new JButton("Find");
		panel3.add(btnFind );
		ActionListener acfind=new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
			{
			String ttx=tb_rej_en_id.getText();
			int idnum=Integer.parseInt(ttx);
				if(idnum>0)
				{
				try
				{
				dbcon();
				String qryfind=" SELECT * FROM combined_rej where RejEntry_ID=?";
				psmt=con.prepareStatement(qryfind);
				psmt.setInt(1,idnum);
				rs=psmt.executeQuery();
				while(rs.next())
				{
				tb_rej_en_id.setText(Integer.toString(rs.getInt(1)));


				java.sql.Date sdt1=(java.sql.Date) rs.getDate(3);
				long ldt=sdt1.getTime();
				java.util.Date retriveddt= new java.util.Date(ldt);
				fbrejdate.setValue(retriveddt);
								
				tbrejqty.setText(Integer.toString(rs.getInt(7)));
				
						
				cbrejarea.setSelectedItem(rs.getString(8));
				cbpartname.setSelectedItem(rs.getString(9));
				cbdefname.setSelectedItem(rs.getString(10));

				txtheatcode.setText(Integer.toString(rs.getInt(6)));
				
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
				String ttx=tb_rej_en_id.getText();
				int idnot=Integer.parseInt(ttx);
									
					idnot=idnot+1;
					String tid=Integer.toString(idnot);
					tb_rej_en_id.setText(tid);
					
					dbcon();
				}
								


			};	
			
		
		btnNext.addActionListener(next);
		
		frame1.show();
		}


}