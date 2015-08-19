package org.eumetsat.Utils.Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.eumetsat.Utils.Common.IOUtils;
import org.eumetsat.Utils.Common.NetCDF2NcML;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetCDF2NcMLTests
{
	private static final String GEOLEOIR_netCDF_TESTFILE1 = "f:/F-Work/git/NetCDF_Checker/Utils/tests/testData/W_XX-EUMETSAT-Darmstadt,SATCAL+RAC+GEOLEOIR,MSG3+SEVIRI-MetOpA+IASI_C_EUMG_20130110000000_preop_01.nc";

	private static final String GEOLEOIR_netCDF_TESTFILE2 = "f:/F-Work/git/NetCDF_Checker/Utils/tests/testData/GEOLEOIR.nc";
	
	private static final String GEOLEOIR_ncml_OUTPUT_FILE2 = "f:/F-Work/git/NetCDF_Checker/Utils/tests/testData/GEOLEOIR_RES.ncml";
	
	private static final String WEB_FILE = "f:/F-Work/git/NetCDF_Checker/Utils/tests/testData/web.xmdl";
	private static final String WEB_OUTPUT_FILE = "f:/F-Work/git/NetCDF_Checker/Utils/tests/testData/web.ncml";
	
	@Before
	public void setUp( ) throws Exception
	{
	}

	@After
	public void tearDown( ) throws Exception
	{
	}

	/**
	 * runTest1 - test single argument constructor.
	 * 
	 * @param s1 - String, netCDF filename
	 * @return - boolean, true if successful, false otherwise.
	 */
	private boolean runTest1( String s1 )
	{
		boolean status = false;
		
		// Create an instance.
		try
		{
			NetCDF2NcML net2ncml = new NetCDF2NcML( s1 );
			
			status = net2ncml.genNCML( );			
		}
		catch( IOException ex )
		{
			IOUtils.pr( ex.toString( ) );
		}
		finally
		{
			return( status );			
		}		
	}
	
	/**
	 * runTest2 - test 2 argument constructor.
	 * 
	 * @param s1 - String, netCDF filename
	 * @param s2 - String, NcML filename
	 * @return - boolean, true if successful, false otherwise.
	 */
	private boolean runTest2( String s1, String s2 )
	{
		boolean status = false;
		
		// Create an instance.
		try
		{
			NetCDF2NcML net2ncml = new NetCDF2NcML( s1, s2 );
			
			status = net2ncml.genNCML( );			
		}
		catch( IOException ex )
		{
			IOUtils.pr( ex.toString( ) );
		}
		finally
		{
			return( status );			
		}
	}
	
	@Test
	public void testGenNCML( )
	{
		boolean status = runTest1( GEOLEOIR_netCDF_TESTFILE1 );
		
		assertTrue( status );
		
		status = runTest2( GEOLEOIR_netCDF_TESTFILE2, GEOLEOIR_ncml_OUTPUT_FILE2 );
		
		assertTrue( status );
	}
}
