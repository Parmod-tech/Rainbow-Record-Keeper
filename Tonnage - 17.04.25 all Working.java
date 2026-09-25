// Java Frame for Tonnage


import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Tonnage
{

static Connection con;
static Statement smt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2;
static JLabel lbl1;
static JTable rejtable;
static JScrollPane scrollpane;
static Vector columns;
static Vector row;
static Vector data;



public Tonnage()
{
//Frame and panels---------------------------
		frame1 = new JFrame("BSFL (P-10): Production Tonnage");
		frame1.setSize(600,550);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel1 = new JPanel();
		panel2 = new JPanel();	
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");

		JLabel  lbl1 = new JLabel(" Production Tonnage Month Wise - ");
		panel1.add(lbl1);


//Getting Tonnage ---------------------------------------------
	dbcon(); 
	try
		{
		String rejquery=("SELECT * FROM pkdb.tonnagemonthwise");
		smt=con.createStatement();
		rs=smt.executeQuery(rejquery);
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
		rejtable=new JTable(data,columns);
		scrollpane=new JScrollPane(rejtable);
		panel2.add(scrollpane);
	
		}
	catch(Exception e1)
			{
			System.out.println(e1);
			}
		
	frame1.show();
	
} // Constructor Closed	


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
		new Tonnage();
		}
}