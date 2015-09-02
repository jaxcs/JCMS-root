package org.jax.cs.apps.jcms.subsystems.jptConfig;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Properties;
import java.io.*;

/**
 *
 * @author daves
 */
public class JPTProperties {


        Properties JTPDefProp;
        
        public JPTProperties()
        {
            JTPDefProp = new Properties();
            this.loadProperties("JPT.conf");
        }

        public void loadProperties(String propertiesFile)
        {
            File oiFile = new File ( propertiesFile ) ; 
            try 
            {  
                JTPDefProp.clear();
                FileInputStream ioFIS = new FileInputStream ( oiFile ) ; 
                JTPDefProp.load(ioFIS);
            }
            catch ( java.io.IOException ioe )  { /*handle exception*/ }  
            finally 
            {  
                                
                // try { out.flush (  ) ;out.close (  ) ; }  
                // catch ( Exception e )  { /*handle exception*/ }  
                // out = null;    
                
             }  
            
        }
        
        public void saveProperties()
        {
            File ioFile = new File ( "JPT.conf" ) ; 
            try 
            {  
                FileOutputStream ioFOS = new FileOutputStream ( ioFile ) ; 
                JTPDefProp.store(ioFOS,"Default Properties for JPT");
            }
            catch ( java.io.IOException ioe )  { /*handle exception*/ }  
            finally 
            {                                  
                // try { out.flush (  ) ;out.close (  ) ; }  
                // catch ( Exception e )  { /*handle exception*/ }  
                // out = null;     
                ioFile = null;
             }  
        }
        
        public void setProperty(String propKey, String propValue)
        {
            JTPDefProp.put(propKey, propValue);
        }
        
        public String getProperty(String propKey)
        {
            return (JTPDefProp.getProperty(propKey));
        }
        
        
}
