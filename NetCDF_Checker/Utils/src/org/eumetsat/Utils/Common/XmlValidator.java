package org.eumetsat.Utils.Common;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

// Validate XML file using DOM and XSD defined in the XML file.
//
// Important notes:
//
// 1. In the XML file, the root element should defined the XSD schema location which is in the CLASSPATH e.g.
//
//    <howto xsi:noNamespaceSchemaLocation="howto.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
//
// 2. In the XML file, the 

// Singleton class - only one instance will be created.
public class XmlValidator
{
   //Set the logger.
   static Logger log = Logger.getLogger( XmlValidator.class );
   
	private static final String SUN_SCHEMA_LANGUAGE_STR = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String W3_SCHEMA_STR = "http://www.w3.org/2001/XMLSchema";
	
	// Singleton class instance is never used as there are only static functions.
	private static XmlValidator dummy = null;
	
	// DOM builder / Parser.
	private static DocumentBuilderFactory domFactory = null;
	private static DocumentBuilder domBuilder = null;
	
	// Private constructor - Singleton function, called ony once.
	private XmlValidator( ) throws ParserConfigurationException
	{
		// DOM initialisation.
		domFactory = DocumentBuilderFactory.newInstance( );
		domFactory.setValidating( true );
		domFactory.setNamespaceAware( true );
		domFactory.setAttribute( SUN_SCHEMA_LANGUAGE_STR, W3_SCHEMA_STR );
		domBuilder = domFactory.newDocumentBuilder( );
      
		domBuilder.setErrorHandler(
          new ErrorHandler( ) {
            public void warning( SAXParseException ex ) throws SAXException 
            {
            	System.out.println( "WARNING: " + ex.getMessage( ) ); // do nothing
            }

            public void error( SAXParseException ex ) throws SAXException 
            {
            	System.out.println("ERROR: " + ex.getMessage( ) );
            	throw ex;
            }

            public void fatalError( SAXParseException ex ) throws SAXException 
            {
            	System.out.println( "FATAL: " + ex.getMessage( ) );
            	throw ex;
            }
          }
      );
   };
	
	/**
	 * validateXmlUsingDOM - function to validate XML file with XSD schemas defined within them.
	 * The XSD file should have the same name as the root element and located within the CLASSPATH.
	 * 
	 * @param xmlFile - String, the XML file to be validated
	 * 
	 * @return true if the XML can be validated by the XSD file.
	 * 
	 * @throws ParserConfigurationException - factory.newDocumentBuilder( ) fails
	 * @throws IOException - File IO problem i.e. source file does not exist etc.
	 */
	public static Document validateXmlUsingDOM( String xmlFile ) throws ParserConfigurationException
	{
		Document doc = null;
		
		try 
		{
			if ( domBuilder == null )
			{
				dummy = new XmlValidator( );
			}
			
			doc = domBuilder.parse( new InputSource( xmlFile ) );    
		}     
		catch( ParserConfigurationException pce ) 
		{
			// Exception caused by unable to create the builder.
			System.out.println( "Parse Configuration Error: " + pce.getMessage( ) );
	      throw pce; 
		}
		catch( IOException io )
		{
			System.out.println( "IO Error: Failed to read document " + io.getMessage( ) );
	      doc = null; 
		}
		catch( SAXException se )
		{
	      // Not exception, xml file is not valid.
			// Builder error handlers should have reported the problem.
			doc = null;
		}
		
		return( doc );
	}
	
	/**
	 * validateXmlUsingSax - validation xml file with xsd separate as input.
	 * 
	 * @param xmlFile - String, XML file to be validated.
	 * @param xsdFile - String, XSD file to validate the XML file.
	 * 
	 * @return - boolean true if valid, false otherwise.
	 * 
	 * @throws ParserConfigurationException - if the factory creation fails.
	 * @throws IOException - If there are IO issues.
	 */
	public static boolean validateXmlUsingSax( String xmlFile, String xsdFile ) throws ParserConfigurationException
	{
		boolean status = true;
		
		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance( );
	      factory.setValidating( false ); 
	      factory.setNamespaceAware( true );

	      SchemaFactory schemaFactory = SchemaFactory.newInstance( W3_SCHEMA_STR );
	      SAXParser parser = null;
	      
	      try 
	      {
	         factory.setSchema( schemaFactory.newSchema( new Source[ ] { new StreamSource( xsdFile ) } ) );
	         parser = factory.newSAXParser( );
	      }
	      catch( SAXException se ) 
	      {
	      	// Example is schema file is NOT there!
	      	System.out.println( "SCHEMA : " + se.getMessage( ) );  // problem in the XSD itself
	      	status = false;
	      }
	      
	      if ( status == true )
	      {
		      XMLReader reader = parser.getXMLReader( );
		      
		      reader.setErrorHandler(
		      	new ErrorHandler( ) 
		         {
		            public void warning( SAXParseException ex ) throws SAXException 
		            {
		              System.out.println( "WARNING: " + ex.getMessage( ) ); // do nothing
		            }

		            public void error( SAXParseException ex) throws SAXException 
		            {
		              System.out.println( "ERROR : " + ex.getMessage( ) );
		              throw ex;
		            }

		            public void fatalError( SAXParseException ex ) throws SAXException 
		            {
		              System.out.println( "FATAL : " + ex.getMessage( ) );
		              throw ex;
		            }
		         }
		      );
		      
		      reader.parse( new InputSource( xmlFile ) );		      
	      }
		}     
		catch( ParserConfigurationException pce ) 
		{
			System.out.println( "Parse Configuration Error: " + pce.getMessage( ) );
	   	throw pce;
		}  
		catch ( IOException io ) 
		{
			System.out.println( "IO Error: Failed to read document " + io.getMessage( ) );
	      status = false;
		}
		catch( SAXException se )
		{
	      status = false;
		}
		
		return( status );
	}
}
