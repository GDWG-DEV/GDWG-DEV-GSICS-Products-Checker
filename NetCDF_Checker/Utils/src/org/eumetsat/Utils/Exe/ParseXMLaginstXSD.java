package org.eumetsat.Utils.Exe;

import java.io.File;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;

public class ParseXMLaginstXSD
{

	public static void main( String[ ] args ) throws Exception
	{
//		URL schemaFile = new URL( "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd" );
		
//		Source xmlFile = new StreamSource( new File( "web.xml" ) );
		
		Source schemaFile = new StreamSource( new File( "employees.xsd" ) );
		
		Source xmlFile = new StreamSource( new File( "employees.xml" ) );

		SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		
		Schema schema = schemaFactory.newSchema( schemaFile );
		
//		Schema schema = schemaFactory.newSchema( schemaFile );
		
		Validator validator = schema.newValidator( );
		
		try 
		{
		  validator.validate( xmlFile );
		  
		  System.out.println( xmlFile.getSystemId( ) + " is valid" );
		} 
		catch ( SAXException ex ) 
		{
		  System.out.println( xmlFile.getSystemId( ) + " is NOT valid" );
		  System.out.println( "Reason: " + ex.getLocalizedMessage( ) );
		}
	}

}
