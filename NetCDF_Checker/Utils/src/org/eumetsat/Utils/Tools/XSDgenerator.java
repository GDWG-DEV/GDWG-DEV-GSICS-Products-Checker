package org.eumetsat.Utils.Tools;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eumetsat.Utils.Common.Constants;

public class XSDgenerator
{
   //Set the logger.
   static Logger log = Logger.getLogger( XSDgenerator.class );
   
   private String filename = null; // XML and netCDF
   
   private XSDgenerator( String filename ) throws IOException
   {
   	this.filename = filename;
   	
   	File fn = new File( this.filename );
   	
   	if ( fn.exists( ) == false )
   	{
   		throw Constants.IO_EXP;
   	}
   }
   
   public boolean generateXSD( )
   {
   	boolean status = true;
   	
   	return( status );
   }
   
   
}
