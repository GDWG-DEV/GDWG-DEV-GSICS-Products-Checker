package org.eumetsat.Utils.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;

public class IOUtils
{
   //Set the logger.
   static Logger log = Logger.getLogger( IOUtils.class );
   
   private static boolean debug = false;
   
   private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
   private static final String YEAR_FORMAT_NOW = "yyyy";
   
   /**
    * setDebug - sets the debug flag.
    * 
    * @param flag - boolean, true or false.
    */
   public static void setDebug( boolean flag )
   {
   	debug = flag;
   }
   
   /**
    * now
    * 
    * Returns the current workstation time in a string.
    * 
    * @return - String, time.
    */
   public static String now( )
   {
      Calendar cal = Calendar.getInstance( );
      SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT_NOW );
      return( sdf.format( cal.getTime( ) ) );
   }
   
   /**
    * getYear - get the current year string.
    * 
    * @return - String, the current year in string format.
    */
   public static String getYearStr( )
   {
      SimpleDateFormat sdf = new SimpleDateFormat( YEAR_FORMAT_NOW );
      
      return( sdf.format( Calendar.getInstance( ).getTime( ) ) );
   }
   
   // Simple print to standard output.
   public static void pr( String msg )
   {
      System.out.println( msg );
   }

   // Simple print to standard output with a time string.
   public static void prT( String msg )
   {
      pr( now( ) + " : " + msg );
   }

   // Print a message 
   public static void prTprefix( String prefix, String msg )
   {
      prT( prefix + msg );
   }
   
   // Print a testing message.
   public static void prTestMethod( String methodName )
   {
      pr( "------------------------------------------" );
      prTprefix( "Testing ", methodName );
   }
   
   // Print a completed message.
   public static void prDone( )
   {
      prT( "Completed" );
   }
   
   // Print a error message.
   public static void prErr( String errStr )
   {
      prTprefix( "ERROR ", errStr );
   }

   // Print a warning message.
   public static void prWarn( String errStr )
   {
      prTprefix( "WARN  ", errStr );
   }
   
   // Print a information message.
   public static void prInfo( String errStr )
   {
      prTprefix( "INFO  ", errStr );
   }
   
   // Print a information message.
   public static void prDebug( String debugStr )
   {
      if ( debug == true )
      {
         prTprefix( "DEBUG  ", debugStr );
      }
   }
   
   // Set debug on.
   public static void debugOn( )
   {
      debug = true;
   }
   
   // Set debug off.
   public static void debugOff( )
   {
      debug = false;
   }
   
   /**
    * getDebugFlag
    * 
    * Get the debug flag.
    * 
    * @return - boolean, the debug flag.
    */
   public static boolean getDebugFlag( )
   {
      return( debug );
   }

   
   /**
    * setDebugFlag
    * 
    * Set the debug flag.
    * 
    * @param debugFlag - boolean, flag to set the debug flag.
    * 
    */
   public static void setDebugFlag( boolean debugFlag )
   {
      debug = debugFlag;
   }

   /**
    * getMyMethodName
    * 
    * Get the name of the method calling this method.
    * 
    * @return - String, the method name.
    */
   public static String getMyMethodName( )
   {
      return( new Exception( ).getStackTrace( )[ 1 ].getMethodName( ) );
   }

   /**
    * getMyClassName
    * 
    * Get the class name of the method invoking this method.
    * 
    * @return - String, the class name.
    */
   public static String getMyClassName( )
   {
      return( new Exception( ).getStackTrace( )[ 1 ].getClassName( ) );
   }
   
   /**
    * getId
    * 
    * Get an unique identifier for the method invoking his method.
    * Generated from Class and method name.
    * 
    * @return - String, unique id for the instance of the runtime executable.
    */
   public static String getId( )
   {
      Exception ex = new Exception( );
      
      return( ex.getStackTrace( )[ 1 ].getClassName( ) + "." + ex.getStackTrace( )[ 1 ].getMethodName( ) + " - " );
   }

   /**
    * prStrings
    * 
    * Utility function to print the values in a string array.
    * 
    * @param strArr - String[ ], string array.
    */
   public static void prStrings( String[ ] strArr )
   {
      int numStrs = strArr.length;
      pr( "--------------- Printing " + numStrs + " strings ---------------" );
      for ( int indx = 0; indx < numStrs; indx++ )
      {
         pr( indx + ") " + strArr[ indx ] );
      }
   }
   
   /**
    * prDebugSep
    * 
    * Utility function to print a separator line.
    *
    */
   public static void prDebugSep( )
   {
      if ( debug == true )
         pr( "----------------------------------------------------------------" );     
   }
   
   /**
    * prSep
    * 
    * Utility function to print a separator line.
    *
    */
   public static void prSep( )
   {
      pr( "----------------------------------------------------------------" );     
   }

   /**
    * getTimeStr
    * 
    * @return String, the current time.
    */
   public static String getTimeStr( )
   {
      return( now( ) );
   }
   
   /**
    * formatToISO8601 - formats the time string format generated by the IOUtil functions to the ISO8601 format i.e.
    * 
    * IOUtils date format is: DATE_FORMAT_NOW => "dd-MM-yyyy HH:mm:ss"
    * ISO8601 date format is: YYYY-MM-DDTHH:MI:SSZ e.g. 2015-01-13T23:01:22Z
    * 
    * @param dateStr - String, the incoming date format.
    * 
    * @return String, in the ISO8601 format.
    */
   public static String formatToISO8601( String dateStr ) throws Exception
   {
   	String retStr = "";
   	int len = dateStr.length( );
   	String dash = Constants.MINUS;
   	String timeChar = "T";
   	String colon = Constants.COLON;
   	String zulu = "Z";
   	
      if ( len == 19 )
      {
      	String ddStr = dateStr.substring( 0, 2 );
         String MMStr = dateStr.substring( 3, 5 );
         String yyyyStr = dateStr.substring( 6, 10 );
         
         String hhStr = dateStr.substring( 11, 13 );
         String mmStr = dateStr.substring( 14, 16 );
         String ssStr = dateStr.substring( 17, 19 );
         
         retStr = ddStr + dash + MMStr + dash + yyyyStr + timeChar + hhStr + colon + mmStr + colon + ssStr + zulu;
      }
      else
      {
      	IOUtils.prDebug( "Incorrect incoming date format; expecteed dd-MM-yyyy HH:mm:ss, got " + dateStr );
      	log.error( "Incorrect incoming date format; expecteed dd-MM-yyyy HH:mm:ss, got " + dateStr );
      	throw Constants.EXP;
      }
   	
   	return( retStr );
   }
}

