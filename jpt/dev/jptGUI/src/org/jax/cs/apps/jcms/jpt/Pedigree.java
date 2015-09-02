package org.jax.cs.apps.jcms.jpt;

import org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccessJDBC;
import java.sql.*;
import javax.swing.tree.*;

import java.util.*;
//import org.jdesktop.application.TaskMonitor;


//Import Apache String Utils because replace string doesn't work well with | char
import org.apache.commons.lang.*;


/** JTree with missing or custom icons at the tree nodes.
 *  1999 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class Pedigree
{
    int MAX_DEPTH = 3;
    Connection dbConnect;
    // This will needs to be user configurable.
    String myDSN;
    String myURL;
    String myReport;
    

    DataAccessJDBC dbJCMS;
    String reportHeader = "";
    
    
    public Pedigree(DataAccessJDBC dbJCMS)
    {
        this.dbJCMS = dbJCMS;
        myReport = new String();
    }

    public void clearReport()
    {
        myReport = "";
       
    }
   
    public String getReport()
    {
        String Report;
        java.util.Date date = new java.util.Date();   
        
        Report = "JCMS  Pedigree Tracker TEXT Report\n";
        Report += date + "\n";
        Report += "Report Type : " + reportHeader + "\n";
        Report += "Number of Generation : " + Integer.toString(MAX_DEPTH) + "\n";
        
        
        return (Report + "\n\n" + myReport);
    }

    public String getHTMLReport()
    {
        String strReport;
        String HTMLReport;
    
        strReport = myReport;
        
        java.util.Date date = new java.util.Date();   
        
        // Create Header 
        HTMLReport = "<HTML><HEAD><TITLE>" + reportHeader + "</TITLE><br />";
        HTMLReport += "</HEAD>";
        HTMLReport += "<BODY background=http://colonymanagement.jax.org/jpt/cream.jpg>";
        HTMLReport += "<h2>JCMS  Pedigree Tracker HTML Report</h2>";
        HTMLReport += date + "<br />";
        HTMLReport += "Report Type : " + reportHeader + "<br />";
        HTMLReport += "Number of Generation : " + Integer.toString(MAX_DEPTH) + "<br />";
        
        HTMLReport += "<div>\n<br/><br/>";
        HTMLReport += StringUtils.replace(strReport, "|", "<img src=\"http://colonymanagement.jax.org/jpt/cream-indent.jpg\" /> ");
        HTMLReport = StringUtils.replace(HTMLReport, "\n", "<br />\n");
        
        HTMLReport += "</div> \n </BODY> </HTML>";
        
        // Create Footer
        return (HTMLReport);
    }

    
    public void FindAncestors(int depth, String MouseID, DefaultMutableTreeNode myNode)
    {
        String Mousey;
        ArrayList mouseArrList;
        DefaultMutableTreeNode child;

        // data from our table
        try
        {
            // Add one to the depth
            depth++;
   
            if (MAX_DEPTH < depth )
                return;

            mouseArrList = dbJCMS.getPartent(MouseID);
            int i = 0;
            int arrSize = mouseArrList.size();
            
            // Display the contents
            while (i < arrSize)
            {
                if (null == mouseArrList.get(i))
                    System.out.print("Pedigree:FindAncestors:Null value returned from database."); 
                
                System.out.print(mouseArrList.get(i).toString() + "\n");
                child = new DefaultMutableTreeNode(mouseArrList.get(i).toString());
                int j = mouseArrList.get(i).toString().indexOf(":");
                if (j > 0) 
                    Mousey = mouseArrList.get(i).toString().substring(0, j - 1).trim();
                else
                    Mousey = mouseArrList.get(i).toString().trim();
                
                FindAncestors(depth, Mousey, child);
                myNode.add(child);
               
                i++;
            }
        }
        // Close all database resources to make sure they're free
        catch (Exception e)
        {
            System.out.println("Pedigree:FindAncestors:" + e);
        }
    }


    //
    //
    public void ReportAncestors(int depth, String MouseID)
    {
        String Mousey;
        ArrayList mouseArrList;

   
        reportHeader = "Ancestors";
        // data from our table
        try
        {
  
            if (0 == depth)
                PrintReport(depth, MouseID);
            
            // Add one to the depth
            depth++;
   
            if (MAX_DEPTH < depth )
                return;

            mouseArrList = dbJCMS.getPartent(MouseID);
            int i = 0;
            int arrSize = mouseArrList.size();
            
            // Display the contents
            while (i < arrSize)
            {
                if (null == mouseArrList.get(i))
                    System.out.print("zzz zzz");
                
                System.out.print(mouseArrList.get(i).toString() + "\n");
                int j = mouseArrList.get(i).toString().indexOf(":");
                if (j > 0) 
                    Mousey = mouseArrList.get(i).toString().substring(0, j - 1).trim();
                else
                    Mousey = mouseArrList.get(i).toString().trim();
                
                PrintReport(depth, mouseArrList.get(i).toString() );
                ReportAncestors(depth, Mousey);
                i++;
            }
        }
        // Close all database resources to make sure they're free
        catch (Exception e)
        {
            System.out.println("Data Access Error Doh!: " + e);
        }
    }

    
    
    
    // 
    // 
    public void FindDescendants(int depth, String MouseID, DefaultMutableTreeNode myNode)
    {
        
        String Mousey;
        ArrayList mouseArrList;
        DefaultMutableTreeNode child;
        
        // Add one to the depth
        depth++;
   
        if (MAX_DEPTH < depth )
            return;

        // data from our table
        try
        {
            mouseArrList = dbJCMS.getChildren(MouseID);
            int i = 0;
            int arrSize = mouseArrList.size();
            
            // Display the contents
            while (i < arrSize)
            {
              	child = new DefaultMutableTreeNode(mouseArrList.get(i).toString());
                Mousey = mouseArrList.get(i).toString().trim();

                int j = mouseArrList.get(i).toString().indexOf(":");
                if (j > 0) 
                    Mousey = mouseArrList.get(i).toString().substring(0, j - 1).trim();
                else
                    Mousey = mouseArrList.get(i).toString().trim();

                FindDescendants(depth, Mousey, child);
                PrintMyLine(depth, Mousey);
                myNode.add(child);
               
                i++;
            }
        }
        // Close all database resources to make sure they're free
        catch (Exception e)
        {
            System.out.println("Data Access Error Doh!: " + e);
        }
  
    }

    public void ReportDescendants(int depth, String MouseID)
    {
        
        String Mousey;
        ArrayList mouseArrList;

       reportHeader = "Progeny";

       if (0 == depth)
           PrintReport(depth, MouseID);

       // Add one to the depth
        depth++;
   
        if (MAX_DEPTH < depth )
            return;

        // data from our table
        try
        {
            mouseArrList = dbJCMS.getChildren(MouseID);
            int i = 0;
            int arrSize = mouseArrList.size();
            
            // Display the contents
            while (i < arrSize)
            {
                Mousey = mouseArrList.get(i).toString().trim();

                int j = mouseArrList.get(i).toString().indexOf(":");
                if (j > 0) 
                    Mousey = mouseArrList.get(i).toString().substring(0, j - 1).trim();
                else
                    Mousey = mouseArrList.get(i).toString().trim();
               
                PrintReport(depth, mouseArrList.get(i).toString().trim());
                ReportDescendants(depth, Mousey);
                i++;
            }
        }
        // Close all database resources to make sure they're free
        catch (Exception e)
        {
            System.out.println("Data Access Error Doh!: " + e);
        }
  
    }
    
    
    public String getMouseID(int mouseKey)
    {
        String strTemp = "";

        // data from our table
        try
        {
            String MySQLStatement = "SELECT ID FROM mouse" +
                    " WHERE mouse.[_mouse_key] = " + mouseKey + ";"; 
            
            Statement sqlStatement = dbConnect.createStatement();
            ResultSet tD = sqlStatement.executeQuery(MySQLStatement);

            tD.next();
            strTemp = tD.getString("ID");

            // blah
            tD.close();
            sqlStatement.close();

        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }

        return(strTemp);
    }

    private void PrintMyLine(int spaces, String mouseyName)
    {
        int i;
        for (i = 0; i < spaces; i++)
            System.out.print("|   ");
        System.out.print(mouseyName + "\n");
    }
    private void PrintReport(int spaces, String mouseyName)
    {
        int i;
        for (i = 0; i < spaces; i++)
            myReport += "|   ";
        myReport = myReport + mouseyName + "\n";
    }

}
