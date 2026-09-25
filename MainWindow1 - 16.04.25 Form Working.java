//Rainbow Record Keeper Main Window----------------------------

import javax.swing.*;
import java.awt.*;


public class MainWindow1
{

public MainWindow1()
{
//Frame, Panel and Lables --------------------------
JFrame mwin= new JFrame("Rainbow Rcord Keeper (Blue Stampings and Forgings Limited)");
mwin.setSize( 700,500);
mwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

JPanel jp1=new JPanel();
jp1.setLayout(new GridBagLayout());

JLabel lbl1=new JLabel("Welcome to RAINBOW RECORD KEEPER");
JLabel lbl2=new JLabel();
lbl2.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\JMD\\OneDrive\\Pk System Backup\\BSFL QMS (Unit-2)\\_IT\\Java(BSFL)\\Logo.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));



//Menubar and Menu --------------------------------------------
JMenuBar mbar;
mbar=new JMenuBar();
JMenu filemenu=new JMenu("File");
JMenu dentrymenu=new JMenu("Data Entry");
JMenu reportmenu=new JMenu("Reports");
JMenu aboutmenu=new JMenu("About");

//Sub-Menu -------------------------------------------------------

JMenuItem rejpercentage=new JMenuItem("Rejection Percentage");
JMenuItem tonnage=new JMenuItem("Tonnage");
JMenuItem data=new JMenuItem("Production & Rejection Data");
reportmenu.add(rejpercentage);
reportmenu.add(tonnage);
reportmenu.add(data);
dentrymenu.add(new JMenuItem("Forging Entry"));
dentrymenu.add(new JMenuItem("Rejection Entry"));


//Adding objects to frame--------------------------------


jp1.add(lbl2);
jp1.add(lbl1);
mwin.add(jp1);
mwin.setJMenuBar(mbar);
mbar.add(filemenu);
mbar.add(dentrymenu);
mbar.add(reportmenu);
mbar.add(aboutmenu);
mwin.setVisible(true);


}


public static void main(String[] args)
{
new MainWindow1();
}


}