// Java Frame for RejPercentage


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;


public class RejPercentage
{

static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JLabel lbl1, lbl2;
static JComboBox<String> cbmonth;
static String selectedrejmonth;
static JTable rejtable;
static JButton getrej;
static JScrollPane scrollpane;
static Vector columns;
static Vector row;
static Vector data;



public RejPercentage()
{
//Frame and panels---------------------------
		frame1 = new JFrame("BSFL (P-10): Rejection Percentage");
		frame1.setSize(600,550);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel1 = new JPanel();
		panel2 = new JPanel();	
		panel3 = new JPanel();	
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");
		frame1.add(panel3, "South");


//Combo Month ------------------------		
		JLabel  lbl1 = new JLabel(" Select Month (YYYY.MM): ");
		panel1.add(lbl1);
		cbmonth = new JComboBox();
		panel1.add(cbmonth);	
		cbmonth.addActionListener(selectrejmonth);

//Get month list for combo box-------------------
		dbcon(); 
		try
			{
			smt=con.createStatement();
			rs=smt.executeQuery("SELECT distinct monthcode FROM pkdb.rejpercent2f");
				while(rs.next())
				{
				cbmonth.addItem(rs.getString(1));
				};		
			smt.close();
			dbclose();
			}
		catch(Exception e1)
			{
			System.out.println(e1);
			}
			

//JTable contents----------------------------------------------

lbl2=new JLabel();
panel2.add(lbl2);


getrej=new JButton("Get Data");
panel1.add(getrej);
getrej.addActionListener(getrejpercentage);
frame1.show();

} // constructor close---------------------


ActionListener getrejpercentage= new ActionListener()
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
	lbl2.setText("Rejection Data for Month : "+selectedrejmonth);
	dbcon(); 
	try
		{
		String rejquery=("SELECT * FROM pkdb.rejpercent2f where monthcode= ?");
		psmt=con.prepareStatement(rejquery);
		psmt.setString(1,selectedrejmonth);
		rs=psmt.executeQuery();
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
		psmt.close();
		dbclose();
		rejtable=new JTable(data,columns);
		scrollpane=new JScrollPane(rejtable);
		panel3.add(scrollpane);
		


		}
	catch(Exception e1)
			{
			System.out.println(e1);
			}
		
	
	}
};	

ActionListener selectrejmonth = new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				cbmonth = (JComboBox) e.getSource();	
				selectedrejmonth= (String) cbmonth.getSelectedItem();
				
				}
			};

//Method to Connect to Database -----------------------------
static void dbcon()
{
	try
	{
	Class.forName("com.mysql.cj.jdbc.Driver");
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
	{
	System.out.println(e);
	}
}




//Main Class Begins==================================================	
		public static void main(String[] arges) 
		{
		new RejPercentage();
		}
}