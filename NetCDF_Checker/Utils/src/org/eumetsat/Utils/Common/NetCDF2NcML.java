package org.eumetsat.Utils.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import ucar.nc2.NetcdfFile;
import ucar.nc2.dataset.NetcdfDataset;

public class NetCDF2NcML
{
	private String netCDFFile = null;
	private String ncmlOutputFilename = null;
	
	// Constructor.
	private void netCDFtoNcML( String netCDFlocation, String ncmlOutputFilename ) throws IOException
	{
		this.ncmlOutputFilename = ncmlOutputFilename;
		
		File nfn = new File( netCDFlocation );
		
		if ( nfn.exists( ) == true )
		{
			this.netCDFFile = netCDFlocation;
		}
		else
		{
			IOUtils.pr( "NetCDF file " + netCDFlocation + " does not exists, abort." );
			throw Constants.IO_EXP;
		}
		
		File mfn = new File( this.ncmlOutputFilename );
		
		if ( mfn.exists( ) == true )
		{
			IOUtils.pr( "NCML file: " + this.ncmlOutputFilename + " already exists, abort." );
			throw Constants.IO_EXP;
		}
	}
	
	// Constructor.
	public NetCDF2NcML( String netCDFlocation, String ncmlOutputFilename ) throws IOException
	{
		netCDFtoNcML( netCDFlocation, ncmlOutputFilename );
	}
	
	// Constructor.
	public NetCDF2NcML( String netCDFlocation ) throws IOException
	{
		int dotExtensionPos = netCDFlocation.lastIndexOf( Constants.DOT );
		String outputfilename = netCDFlocation.substring( 0, dotExtensionPos + 1 ) + Constants.NCML_SUFFIX;
		
		netCDFtoNcML( netCDFlocation, outputfilename );
	}
			
	/**
	 * genNCML - convert the netCDF file into its NCML equivalent.
	 * 
	 * @return boolean - true if a ncml file was generated, false otherwise.
	 */
	public boolean genNCML( )
	{
		boolean status = false;
		NetcdfFile ncfile = null;
		
		try 
		{
			OutputStream os = new FileOutputStream( this.ncmlOutputFilename );
			
			ncfile = NetcdfDataset.openFile( this.netCDFFile, null );
		   
		   ncfile.writeNcML( os, null );
		   
			IOUtils.pr( "Successfully converted " + this.netCDFFile + " to " + this.ncmlOutputFilename + ", terminated." );
			
			status = true;
		} 
		catch( IOException ioe ) 
		{
			IOUtils.pr( "Error trying to open " + this.netCDFFile + " - " + ioe.getLocalizedMessage( ) );
		} 
		finally 
		{ 
		   if ( null != ncfile ) 
		   {
		   	try 
		   	{
		   		ncfile.close( );
		      } 
		   	catch( IOException ioe ) 
		   	{
		   		IOUtils.pr( "trying to close " + this.netCDFFile + " - " + ioe.getLocalizedMessage( ) );
		      }
		   }
		}
		
		return( status );
	}
}
