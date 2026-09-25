// Java Frame for RejPercentage


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class PRData
{

static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JLabel lbl1, lbl2, lbl3;
static JFormattedTextField ftf1, ftf2;
static JComboBox<String> cbmonth;
static String selectedrejmonth;
static JTable datatable;
static JButton getdata;
static JScrollPane scrollpane;
static Vector columns;
static java.sql.Date sqldate1;
static java.sql.Date sqldate2;
static Vector row;
static Vector data;




public PRData()
{
//Frame and panels---------------------------
		frame1 = new JFrame("BSFL (P-10): Rejection Percentage");
		frame1.setSize(700,550);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel1 = new JPanel();
		panel2 = new JPanel();	
		panel3 = new JPanel();	
		panel3.setLayout(new GridLayout(1,1));
		frame1.add(panel1, "North");
		frame1.add(panel2, "Center");
		frame1.add(panel3, "South");


//Panel Components-----------------------------	
		lbl1 = new JLabel(" Data From (YYYY-MM-DD): ");
		lbl2 = new JLabel(" To (YYYY-MM-DD): ");

		DateFormat dtype = new SimpleDateFormat("yyyy-MM-dd");
		ftf1=new JFormattedTextField(dtype);
		ftf1.setColumns(10);
		ftf2=new JFormattedTextField(dtype);
		ftf2.setColumns(10);
		getdata=new JButton("Get Data");
		lbl3= new JLabel();
		
		
		panel1.add(lbl1);
		panel1.add(ftf1);
		panel1.add(lbl2);
		panel1.add(ftf2);
		panel1.add(getdata);
		panel2.add(lbl3);


getdata.addActionListener(getpnrdata);
frame1.show();

} // constructor close---------------------


ActionListener getpnrdata= new ActionListener()
{
	@Override
	public void actionPerformed(ActionEvent e)
	{

sqldate1=new java.sql.Date(miliSec(ftf1));
sqldate2=new java.sql.Date(miliSec(ftf2));

lbl3.setText("Production and Rejection data from: " +sqldate1+ "    To: " +sqldate2 + " ");

	

	dbcon(); 
	try
		{
		String dataquery=("SELECT * FROM prod_n_rej where Date between ? and ?");
		psmt=con.prepareStatement(dataquery);
		psmt.setDate(1,sqldate1);
		psmt.setDate(2,sqldate2);
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
		datatable=new JTable(data,columns);
		datatable.setBounds(650,650,650,650);
		scrollpane=new JScrollPane(datatable);
		panel3.add(scrollpane);
		


		}
	catch(Exception e1)
			{
			System.out.println(e1);
			}
		
	
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

//Method to convert util date to sql date -------------------------------
static long miliSec(JFormattedTextField utldt1)
{
java.util.Date utldt2=(java.util.Date) utldt1.getValue(); 
long milisec=utldt2.getTime();
return milisec;
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
		new PRData();
		}
}