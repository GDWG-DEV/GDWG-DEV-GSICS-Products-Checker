package org.eumetsat.Utils.Tools;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;

// Validate XML file using DOM and XSD defined in the XML file.
// Adapted tutorial down-loaded from http://www.rgagnon.com/javadetails/java-0669.html 

// Singleton class - only one instance will be created.
public class XmlDOMValidator
{
	private static final String SUN_SCHEMA_LANGUAGE_STR = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String W3_SCHEMA_STR = "http://www.w3.org/2001/XMLSchema";
	
	private static final String XML_FILE = "f:/F-Work/workspace/Utils/tests/howto.xml";
	private static final String XML_FILE1 = "f:/F-Work/workspace/Utils/tests/howto_err1.xml";
	private static final String XML_FILE2 = "f:/F-Work/workspace/Utils/tests/howto_err2.xml";
	private static final String XML_FILE3 = "f:/F-Work/workspace/Utils/tests/howto_err3.xml";
	
	// Private constructor - not used.
	private XmlDOMValidator( ) 
	{
	};
	
	public static boolean validateXmlUsingXsd( String xmlFile ) throws ParserConfigurationException, IOException 
	{
		boolean status = true;
		
		try 
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
	      factory.setValidating( true );
	      factory.setNamespaceAware( true );
	      factory.setAttribute( SUN_SCHEMA_LANGUAGE_STR, W3_SCHEMA_STR );
	      DocumentBuilder builder = factory.newDocumentBuilder( );        

	      builder.setErrorHandler(
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

	      builder.parse( new InputSource( xmlFile ) );	      
		}     
		catch( ParserConfigurationException pce ) 
		{
	      throw pce; 
		}
		catch( IOException io )
		{
	      throw io; 
		}
		catch( SAXException se )
		{
	      status = false;
		}
		
		return( status );
	}
	
	/**
	 * Main
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main( String args[ ] ) throws Exception
	{  
		System.out.println( XmlDOMValidator.validateXmlUsingXsd( XML_FILE ) );
		System.out.println( XmlDOMValidator.validateXmlUsingXsd( XML_FILE1 ) );
		System.out.println( XmlDOMValidator.validateXmlUsingXsd( XML_FILE2 ) );
		System.out.println( XmlDOMValidator.validateXmlUsingXsd( XML_FILE3 ) );
	}
}
