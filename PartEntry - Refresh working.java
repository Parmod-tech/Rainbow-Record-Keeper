// Java Frame for Part Entry

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class PartEntry 
{

static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JTextField txtpartid, txtpartname, txtcutwt, txtpartnumber, txtforgwt, txtremarks; 
static JComboBox<String> combocustname, combostdmat, combosection;
static int partid, custid, matid, secid;
static float cutwt;
static String partname, custname, stdmatname, partnumber, stdsecname, remarks;
static Vector columns, data, row;
static JTable parttable;



public PartEntry()
{

//Frame and panels
		frame1 = new JFrame("BSFL (P-10): Part Entry");
		frame1.setSize(550,730);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(9,2));	
		panel2 = new JPanel();	
		panel3 = new JPanel();	
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");
		frame1.add(panel3, "South");

// Part Id
 	 	JLabel  lblpartid = new JLabel("   Part ID:");
  		panel1.add(lblpartid);
 		txtpartid = new JTextField(15);
  		panel1.add(txtpartid);
  


// Part Name
		JLabel  lblpartname = new JLabel("   Part Name:");
		panel1.add(lblpartname);
		txtpartname = new JTextField(15);
		panel1.add(txtpartname);

// Cut Wt.
		JLabel  lblcutwt = new JLabel("   Cutt Wt:");
		panel1.add(lblcutwt);
		txtcutwt = new JTextField(15);
		panel1.add(txtcutwt);


//Combo Customer Name		
		JLabel  lblcustomername = new JLabel("   Customer Name:");
		panel1.add(lblcustomername);
		combocustname = new JComboBox();
		panel1.add(combocustname);
			
		ActionListener selectedCustomerName = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				combocustname = (JComboBox) e.getSource();	
				custname= (String) combocustname.getSelectedItem();
				getCustId(custname);

				


				}
			};

		combocustname.addActionListener(selectedCustomerName);

		
//Combo std Mateial		
		JLabel  lblstdmat = new JLabel("   Material Grade:");
		panel1.add(lblstdmat);
		combostdmat = new JComboBox();
		panel1.add(combostdmat);
			
		ActionListener selectedmat = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				combostdmat = (JComboBox) e.getSource();	
				stdmatname= (String) combostdmat.getSelectedItem();
				getMatId(stdmatname);
				}
			};

		combostdmat.addActionListener(selectedmat);



//Part Number
		JLabel  lblpartnumber = new JLabel("   Part Number");
		panel1.add(lblpartnumber);
		txtpartnumber = new JTextField(1);
		panel1.add(txtpartnumber);


//Combo Section		
		JLabel  lblsection = new JLabel("   Section:");
		panel1.add(lblsection);
		combosection = new JComboBox();
		panel1.add(combosection);
	
		ActionListener selectedsection = new ActionListener()
			{

			@Override	
			public void actionPerformed(ActionEvent e)
				{
				combosection = (JComboBox) e.getSource();	
				stdsecname= (String) combosection.getSelectedItem();	
				getSecId(stdsecname);
				}
			};

		combosection.addActionListener(selectedsection);


//As Forge Weight

		JLabel  lblforgwt = new JLabel("   As Forged Wt:");
		panel1.add(lblforgwt);
		txtforgwt = new JTextField(6);
		panel1.add(txtforgwt);

		

//Remarks
		JLabel  lblremarks = new JLabel("   Remarks");
		panel1.add(lblremarks);
		txtremarks= new JTextField(50);
		panel1.add(txtremarks);




//Button Add New Part  
		
		JButton btnAdd = new JButton("Add New Part");
		panel2.add(btnAdd);
		
		btnAdd.addActionListener(addPart);


//Button Refresh  
		
		JButton btnRefresh = new JButton("Refresh List");
		panel2.add(btnRefresh);
		
		btnRefresh.addActionListener(RefreshList);





// Jtable ---------------------------------------------

	dbcon(); 
	try
		{
		String partquery=("SELECT * FROM pkdb.en_parts order by Part_Name asc");
		smt=con.createStatement();
		rs=smt.executeQuery(partquery);
		ResultSetMetaData rsmd=rs.getMetaData();
		int colcount =rsmd.getColumnCount();
		columns=new Vector(colcount);
		for(int i=1; i<=colcount; i++)
			{
			columns.add(rsmd.getColumnName(i));
			}
		data=new Vector();
			while(rs.next())
			{
			row=new Vector(colcount);
				for(int i=1; i<=colcount; i++)
				{
				row.add(rs.getString(i));			
				}
			data.add(row);
			}
		smt.close();
		dbclose();
		parttable=new JTable(data,columns);
		JScrollPane scrollpane=new JScrollPane(parttable);
		panel3.add(scrollpane);
		}
	catch(Exception e1)
			{
			System.out.println(e1);
			}	
	


frame1.show();



} //Constructer Closed

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



//Add Part----------------------------------------------------------
ActionListener addPart = new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{	
				dbcon();
				
				try
					{
					
					String queryadd="Insert into en_parts (Part_Name, Std_CutWt, cust_id, Std_matID, Part_No, Std_SecID, asForged_Wt, Remarks) Values (?, ?, ?, ?, ?, ?, ?,?)";
					
					
					
					psmt=con.prepareStatement(queryadd);
					psmt.setString(1, txtpartname.getText());
					psmt.setFloat(2, Float.parseFloat(txtcutwt.getText()));
					psmt.setInt(3, custid);
					psmt.setInt(4, matid);
					psmt.setString(5, txtpartnumber.getText());
					psmt.setInt(6, secid);
					psmt.setFloat(7, Float.parseFloat(txtforgwt.getText()));
					psmt.setString(8, txtremarks.getText());
					psmt.executeUpdate();
					psmt.close();
					dbclose();
					JOptionPane.showMessageDialog(frame1,"Part Name" +custname+ "added Successfully!");
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
				}
			

			};
			


//Refresh List----------------------------------------------------------
ActionListener RefreshList = new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{	
				loadCustomerNames();
				loadMatNames();
				loadSecNames();
				
				}
			

			};
			




//Methdo Load customer Names------------------------------------------------------------
static void loadCustomerNames()
{
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Cust_name FROM en_customers order by Cust_name asc");
					while(rs.next()) 
						{
						combocustname.addItem(rs.getString(1));
						}

					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}	
}
			

//Method Load Material Names--------------------------------------------------------
static void loadMatNames()
{
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT Mat_Grade FROM pkdb.en_materials order by Mat_Grade asc");
					while(rs.next()) 
						{
						combostdmat.addItem(rs.getString(1));
						}

					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
}


//Method Load Section Names--------------------------------------------------------
static void loadSecNames()
{
				dbcon();
				try
					{
					smt=con.createStatement();
					rs=smt.executeQuery("SELECT section  FROM pkdb.en_sections order by section asc");
					while(rs.next()) 
						{
						combosection.addItem(rs.getString(1));
						}

					dbclose();
					}
				catch(Exception e1)
					{
					System.out.println(e1);
					}
}

//Method to get cust Id---------------
static int getCustId(String custname1)
{
			try
				{
				dbcon();
				String querycustid="SELECT Cust_ID FROM pkdb.en_customers where Cust_name=?";
				psmt=con.prepareStatement(querycustid);
				psmt.setString(1, custname1);
				rs=psmt.executeQuery();
				while (rs.next())
				{custid=rs.getInt(1);}
				

				dbclose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}

				return custid;

}

//Method to get material Id---------------
static int getMatId(String material)
{
			try
				{
				dbcon();
				String querymatid="SELECT mat_id FROM pkdb.en_materials where mat_grade=?";
				psmt=con.prepareStatement(querymatid);
				psmt.setString(1, material);
				rs=psmt.executeQuery();
				while (rs.next())
				{matid=rs.getInt(1);}
				dbclose();

				}
			catch(Exception e1)
				{
				System.out.println(e1);
				}

				return matid;

}

//Method to get section Id---------------
static int getSecId(String section)
{
			try
				{
				dbcon();
				String querysecid="SELECT section_ID FROM pkdb.en_sections where section=?";
				psmt=con.prepareStatement(querysecid);
				psmt.setString(1, section);
				rs=psmt.executeQuery();
				while (rs.next())
				{secid=rs.getInt(1);}

				psmt.close();
				dbclose();
				
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}
return secid;
}

//Main Class Begins==================================================	
		public static void main(String[] arges) 
		{
		new PartEntry();
		}
}