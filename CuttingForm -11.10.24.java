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

		

static String url="jdbc:Mysql://localhost:3306/pkdb";
static String user="root";
static String pwd="Parmod@123";
static Connection con;
static Statement smt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JTextField textWt, textQty, textCID, textCDate;
static JFormattedTextField cdate;
static JComboBox combopart, ComboHeat;



static int cuttingid;
static LocalDate cuttingdate;
static String partname;
static int heatcode;
static Float cutwt;
static int cutqty;
static int partid;
static int heatid;

//Method to get present values of required variables --------
static void getValues()
{
	
	String htxt = String.valueOf(ComboHeat.getSelectedItem());
	
	cuttingid = Integer.parseInt(textCID.getText());
	cuttingdate = (LocalDate)cdate.getValue();
	partname = String.valueOf(combopart.getSelectedItem());
	heatcode = Integer.parseInt(htxt);
	cutwt = Float.parseFloat(textWt.getText());


	//Getting Part ID
	dbcon();
	try
		{
		smt=con.createStatement();
		rs=smt.executeQuery("SELECT Part_ID FROM en_parts where Part_Name=partname");
		partid=rs.getInt(1);
				
		dbclose();
		
		}
	catch(Exception e1)
		{
		System.out.println(e1);
		}
				
	//Getting Heat ID
	dbcon();
	try
		{
		smt=con.createStatement();
		rs=smt.executeQuery("SELECT Heat_ID FROM entry_heatcodes where 	Heat_No=heatcode");
		heatid=rs.getInt(1); 
					
		dbclose();
			
		}
	catch(Exception e1)
		{
		System.out.println(e1);
		}
}

//Method to Connect to Database -----------------------------
static void dbcon()
{
	try
	{Class.forName("com.mysql.cj.jdbc.Driver");
	con=DriverManager.getConnection(url,user,pwd);
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
	smt.close();
	con.close();
	}
	catch(Exception e)
	{System.out.println(e);}
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
		DateFormat dtype = new SimpleDateFormat("YYYY-MM-DD");
		cdate= new JFormattedTextField(dtype);
		panel1.add(cdate);

//Part Name		
		JLabel  PartName = new JLabel("PartName");
		panel1.add(PartName);
		JComboBox combopart = new JComboBox();
		panel1.add(combopart);	




//Heat Code 
		JLabel  HeatCode = new JLabel("HeatCode");
		HeatCode.setBounds(100,50,100,50);
		panel1.add(HeatCode);
		JComboBox ComboHeat = new JComboBox();
		panel1.add(ComboHeat);	

//Heat Code List Refresh Button

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
					rs=smt.executeQuery("SELECT Heat_ID, Heat_No FROM entry_heatcodes order by Heat_No asc");
						while(rs.next()) 
						{
						combopart.addItem(rs.getInt(2));
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


//Part List Refress Button
		JButton btnRefPart = new JButton("Ref Parts");
		panel2.add(btnRefPart);	
		btnRefPart.addActionListener(new ActionListener()
			{
			
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

		
//Buttons
		JButton btnChange = new JButton("Change");
		panel2.add(btnChange);

		JButton btnAdd = new JButton("Add New");
		panel2.add(btnAdd);

		JButton btnPrevious = new JButton("Previous");
		panel3.add(btnPrevious);

		JButton btnFind = new JButton("Find");
		panel3.add(btnFind );

		JButton btnNext = new JButton("Next");
		panel3.add(btnNext );	

		
		frame1.show();
		}


}