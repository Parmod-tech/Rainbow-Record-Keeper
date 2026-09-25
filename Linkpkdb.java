// Class for getting connection and data to pkdb
// Method pkdbCon() for connectin
// Method pkdbClose() for closing connection
// Method getMaterialId(String material)
// Method getSectionId(String secname)
// Method getPartId(String partname)
// Method getHeatId(int heat_no)

import java.sql.*;


public class Linkpkdb
{
static Connection con;
static Statement smt;
static PreparedStatement psmt;
static ResultSet rs;

//Method to Connect to Database -----------------------------
public static void pkdbCon()
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
public static void pkdbClose()
{ 	try 
	{
	rs.close();
	con.close();
	}
	catch(Exception e)
	{System.out.println(e);}
}

//METHOD SECTION ID-----------------------
public static int getSectionId(String secname)
{
				String section_name=secname;
				int section_id=0;
			
				try
				{
				pkdbCon();
				String querysec="SELECT section_ID FROM en_sections where section=?";
				psmt=con.prepareStatement(querysec);
				psmt.setString(1, section_name);
				rs=psmt.executeQuery();
					while (rs.next())
					{
					section_id=rs.getInt(1);
					};
						
				
				psmt.close();
				pkdbClose();
		
				}
				
				catch(Exception e1)
				{
				System.out.println(e1);
				}				
				return section_id;			
}
		



//METHOD GET MATERIAL ID---------------------------------
public static int getMaterialId(String material)
{

				String material_name=material;
				int material_id=0;
				try
				{
				pkdbCon();
		
				String querymat="SELECT Mat_ID FROM en_materials where Mat_Grade =?";
				psmt=con.prepareStatement(querymat);
				psmt.setString((1), material_name);
				rs=psmt.executeQuery();
				while (rs.next())
				{material_id=rs.getInt(1); }
					
				
				
				pkdbClose();

				}
				catch(Exception e1)
				{
				System.out.println(e1);
				}
				
				return material_id;
}


//Method partid --------------------------------------------		
public static int getPartId(String partname)
{
			String part_name=partname;
			int partid=0;
			try
				{
				pkdbCon();
				String querypart="SELECT Part_ID FROM en_parts where Part_Name=?";
				psmt=con.prepareStatement(querypart);
				psmt.setString(1, part_name);
				rs=psmt.executeQuery();
				while (rs.next())
					{partid=rs.getInt(1);}
				
				
				psmt.close();
				pkdbClose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}
			return partid;
}


//Method heatid ----------------------------------
public static int getHeatId(int heat_no)
{
			int heatcode=heat_no;
			int heatid=0;
			try
				{
				pkdbCon();
				String queryheatid="SELECT Heat_ID FROM entry_heatcodes where Heat_No=?";
				psmt=con.prepareStatement(queryheatid);
				psmt.setInt(1, heatcode);
				rs=psmt.executeQuery();
				while (rs.next())
				{heatid=rs.getInt(1);}
				
				
				psmt.close();
				pkdbClose();
		
				}
				
			catch(Exception e1)
				{
				System.out.println(e1);
				}

			return heatid;

}
			


}