package org.eumetsat.Utils.Exe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eumetsat.Utils.Common.IOUtils;

import ucar.nc2.NetcdfFile;
import ucar.nc2.dataset.NetcdfDataset;

public class genNCMLfromNetCDF
{
	private static String filename = "F:\\F-Work\\W_XX-EUMETSAT-Darmstadt,SATCAL+RAC+GEOLEOIR,MSG3+SEVIRI-MetOpA+IASI_C_EUMG_20130110000000_preop_01.nc";
	
	private static final String OUTPUT_FILE = "F:\\F-Work\\testFile.ncml";
	
	public static void main( String[ ] args ) throws Exception
	{
		NetcdfFile ncfile = null;
		
		try 
		{
			OutputStream os = new FileOutputStream( OUTPUT_FILE );
			
		   ncfile = NetcdfDataset.openFile( filename, null );
		   
		   ncfile.writeNcML( os, null );
		   
			IOUtils.pr( "Done writing " + OUTPUT_FILE + ", terminated." );		   
		} 
		catch( IOException ioe ) 
		{
			IOUtils.pr( "trying to open " + filename + " - " + ioe.getLocalizedMessage( ) );
		} 
		finally 
		{ 
		   if ( null != ncfile ) 
		   {
		   	try 
		   	{
		   		ncfile.close();
		      } 
		   	catch( IOException ioe ) 
		   	{
		   		IOUtils.pr( "trying to close " + filename + " - " + ioe.getLocalizedMessage( ) );
		      }
		   }
		}
	}
}
