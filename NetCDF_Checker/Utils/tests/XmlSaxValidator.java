package org.eumetsat.Utils.Tools;

import java.io.IOException;

//SAX
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

//SAX and external XSD
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;

// Validate XML file using DOM and XSD defined in the XML file.
// Adapted tutorial down-loaded from http://www.rgagnon.com/javadetails/java-0669.html 

// Singleton class - only one instance will be created.
public class XmlSaxValidator
{
	private static final String W3_SCHEMA_STR = "http://www.w3.org/2001/XMLSchema";
	
	private static final String XML_FILE = "f:/F-Work/workspace/Utils/tests/howto_err3.xml";
	private static final String XSD_FILE = "f:/F-Work/workspace/Utils/tests/howto_err3.xsd";

	// Private constructor - not used.
	private XmlSaxValidator( ) 
	{
	};
	
   // validate SAX and external XSD 
	public static boolean validateWithExtXSDUsingSAX( String xml, String xsd ) throws ParserConfigurationException, IOException 
	{
		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance( );
	      factory.setValidating( false ); 
	      factory.setNamespaceAware( true );

	      SchemaFactory schemaFactory = SchemaFactory.newInstance( W3_SCHEMA_STR );
	      SAXParser parser = null;
	      
	      try 
	      {
	         factory.setSchema( schemaFactory.newSchema( new Source[ ] { new StreamSource( xsd ) } ) );
	         parser = factory.newSAXParser( );
	      }
	      catch( SAXException se ) 
	      {
	      	// Example is schema file is NOT there!
	      	System.out.println( "SCHEMA : " + se.getMessage( ) );  // problem in the XSD itself
	      	return false;
	      }
	      
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
	      
	      reader.parse( new InputSource( xml ) );
	      
	      return true; 
		}     
		catch( ParserConfigurationException pce ) 
		{
	   	 throw pce;
		}  
		catch ( IOException io ) 
		{
	      throw io; 
		}
		catch( SAXException se )
		{
	      return false;
		}
	}
	
	public static void main( String args[ ] ) throws Exception
	{ 
		System.out.println( XmlSaxValidator.validateWithExtXSDUsingSAX( XML_FILE, XSD_FILE ) );
	} 		  
}
