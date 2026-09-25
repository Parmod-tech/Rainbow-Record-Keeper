// Java Frame for cutting

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;




public class HeatEntry
{
static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;
static JFrame frame1;
static JPanel panel1, panel2, panel3;
static JTextField textHID, textheatNo;
static JComboBox<String> ComboSec, ComboMat;
static int idnot;


static int heat_id, heat_no;
static String section_name, material_name;
static int section_id, material_id;


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

//METHOD GET COMBO SECTION ID-----------------------
public static void getSectionId()
{
	
				
			
				try
				{
				dbcon();
				String querysec="SELECT section_ID FROM en_sections where section=?";
				psmt=con.prepareStatement(querysec);
				psmt.setString(1, section_name);
				rs=psmt.executeQuery();
				while (rs.next())
				{section_id=rs.getInt(1);}
				
				

				psmt.close();
				dbclose();
		
				}
				
				catch(Exception e1)
				{
				System.out.println(e1);
				}				

}
		



//METHOD GET COMBO MATERIAL SECTION SELECTED---------------
public static void getMaterialId()
{

				

				try
				{
				dbcon();
		
				String querymat="SELECT Mat_ID FROM en_materials where Mat_Grade =?";
				psmt=con.prepareStatement(querymat);
				psmt.setString((1), material_name);
				rs=psmt.executeQuery();
				while (rs.next())
				{material_id=rs.getInt(1); }
					
				dbclose();
			
				}
				catch(Exception e1)
				{
				System.out.println(e1);
				}
				

}
		

//METHOD GET HEAT ID---------------
static void getHeatId()
{
heat_id= Integer.parseInt(textHID.getText());
}


//Main Class Begins-------------------------------------------	
		public static void main(String[] arges) 
{

//Frame and panels--------------------------------------------------
	frame1 = new JFrame("BSFL (P-10): Heat Entry");
	frame1.setSize(800,300);
	frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	panel1 = new JPanel();
	panel1.setLayout(new GridLayout(7,2));	
	panel2 = new JPanel();	
	panel3 = new JPanel();	
	frame1.add(panel1, "North");
	frame1.add(panel2, "Center");
	frame1.add(panel3, "South");

//Heat id------------------------------------------------------------------------
	JLabel  lheat_id = new JLabel("Heat ID");
	panel1.add(lheat_id);
	textHID = new JTextField();
	panel1.add(textHID);

//Heat Number----------------------------------------------------------------	
	JLabel  lheatNo = new JLabel("Heat No.");
	panel1.add(lheatNo);
	textheatNo = new JTextField();
	panel1.add(textheatNo);


//Section Combo box--------------------------------------------	
	JLabel  lsection = new JLabel("Section ");
	panel1.add(lsection);
	ComboSec = new JComboBox<>();
	panel1.add(ComboSec);	
	ActionListener test= new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
			{	
				ComboSec = (JComboBox) e.getSource();
				String selected=(String) ComboSec.getSelectedItem();
				section_name=selected;
			}
		};
	ComboSec.addActionListener(test);

//Mateial Grade Combo box ---------------------------------------
	JLabel  lGrade = new JLabel("Material Grade");
	panel1.add(lGrade);
	ComboMat = new JComboBox<>();
	panel1.add(ComboMat);	
	
	ActionListener test1= new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
			{	
				ComboMat = (JComboBox) e.getSource();
				String selectedM=(String) ComboMat.getSelectedItem();
				material_name=selectedM;
			}
		};
	ComboMat.addActionListener(test1);



//Button Section Refress ----------------------------------------
	JButton btnRefSec = new JButton("Ref Sec");
	panel2.add(btnRefSec);	
	btnRefSec.addActionListener(new ActionListener()
	{

	public void actionPerformed(ActionEvent e)
		{	
		try
			{
			dbcon();
			smt=con.createStatement();
			rs=smt.executeQuery("SELECT section from en_sections order by section asc");
			while(rs.next()) 
				{
				ComboSec.addItem(rs.getString(1));
				}
			smt.close();
			dbclose();	
			}
		catch(Exception e1)
			{
			System.out.println(e1);
			}
				
		}
			
			
	}		
	);


//Button Material Grade List Refresh-------------------------------

	JButton btnRefGrade = new JButton("Ref Grade");
	panel2.add(btnRefGrade);
		
	btnRefGrade.addActionListener(new ActionListener()
		{
			
		public void actionPerformed(ActionEvent e)
			{	
				
				dbcon();
				try
				{
				
				smt=con.createStatement();
				rs=smt.executeQuery("SELECT Mat_Grade FROM en_materials order by Mat_Grade asc");
				while(rs.next()) 
					{
					ComboMat.addItem(rs.getString(1));
					}
				smt.close();
				dbclose();				
				
				}
				
				catch(Exception e1)
					{
					System.out.println(e1);
					}
				
			}
				

		}
		);


		

		
//Buttons Change Data --------------------------------------------------
		JButton btnChange = new JButton("Change");
		panel2.add(btnChange);
		btnChange.addActionListener(new ActionListener()
			{
			
			public void actionPerformed(ActionEvent e)
				{	
			getSectionId();
			getMaterialId();
			getHeatId();	
				try
					{
					
					String heat=textheatNo.getText();
					heat_no = Integer.parseInt(heat);

					dbcon();
					String query="update entry_heatcodes set Heat_No=?, Section_ID=?, Mat_ID=? where Heat_ID=?";
					
					psmt=con.prepareStatement(query);
					
					psmt.setInt((1), heat_no);
					psmt.setInt((2), section_id);
					psmt.setInt((3), material_id);
					psmt.setInt((4), heat_id);
					
					psmt.executeUpdate();
					
					JOptionPane.showMessageDialog(frame1," Heat  "+ heat_id +"  updated");
					
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
		


		

//Button Add Data to Database---------------------------------------------
		
		JButton btnAdd = new JButton("Add New");
		panel2.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener()
			{
			
			public void actionPerformed(ActionEvent e)
				{	
			getSectionId();
			getMaterialId();	
				try
					{
					
					String heat=textheatNo.getText();
					heat_no = Integer.parseInt(heat);

					dbcon();
					String query="Insert into entry_heatcodes (Heat_No, Section_ID, Mat_ID) Values (?, ?, ?)";
					
					psmt=con.prepareStatement(query);
					
					psmt.setInt((1), heat_no);
					psmt.setInt((2), section_id);
					psmt.setInt((3), material_id);
					
					psmt.executeUpdate();
					
					JOptionPane.showMessageDialog(frame1," Heat  "+ heat_no +"  Added");
					
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


//Button Previsou ----------------------------
		ActionListener previous=new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				String ttx=textHID.getText();
				int idnot=Integer.parseInt(ttx);
				if(idnot>0)
					{
								
					idnot=idnot-1;
					String tid=Integer.toString(idnot);
					textHID.setText(tid);
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
			




//Find Button Heat by ID Data----------------------------------------------
		JButton btnFind = new JButton("Find");
		panel3.add(btnFind );
		ActionListener acfind=new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e)
			{
			String ttx=textHID.getText();
			idnot=Integer.parseInt(ttx);
				if(idnot>0)
				{
				try
				{
				dbcon();
				String qryfind=" SELECT * FROM heat_entry_readable where Heat_ID=?";
				psmt=con.prepareStatement(qryfind);
				psmt.setInt(1,idnot);
				rs=psmt.executeQuery();
				while(rs.next())
				{
				textHID.setText(Integer.toString(rs.getInt(1)));
				textheatNo.setText(Integer.toString(rs.getInt(2)));
				ComboSec.setSelectedItem(rs.getString(5));
				ComboMat.setSelectedItem(rs.getString(6));
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

//Button Next----------------------------

		ActionListener next=new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent e)
				{
				String ttx=textHID.getText();
				int idnot=Integer.parseInt(ttx);
				if(idnot>0)
					{
					
					idnot=idnot+1;
					String tid=Integer.toString(idnot);
					textHID.setText(tid);
					}
				else
					{
													JOptionPane.showMessageDialog(frame1,"Please enter Valid ID No.");
					}				


				}	
			};
		JButton btnNext = new JButton("Next");
		panel3.add(btnNext );	
		btnNext.addActionListener(next);

		


		
		
		frame1.setVisible(true);
}


}