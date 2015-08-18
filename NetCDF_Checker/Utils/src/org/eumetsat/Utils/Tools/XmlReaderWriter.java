package org.eumetsat.Utils.Tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eumetsat.Utils.Common.Constants;
import org.eumetsat.Utils.Common.IOUtils;
import org.eumetsat.Utils.Common.XmlValidator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 










import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XmlReader Class validates XML files using the DOM method.
 * Validate XML are then returned as a document which is accessible via XPATH strings.
 * 
 * Note, it requires the XSD schema to be defined in the file and the actual file should be located in the CLASS_PATH.
 * Parsing and other exceptions will throw a general exception.
 * 
 * Due to the constraints of using DOM i.e. XML is loaded into memory, the class is suitable for the reading of small'ish XML file.
 * 
 * 
 * @author Peter Miu - Copyright EUMETSAT 2015
 * @version 1.0
 * @category Utility Software
 *
 */
public class XmlReaderWriter
{
   //Set the logger.
   static Logger log = Logger.getLogger( XmlReaderWriter.class );
   
	private String xmlFile = null;
	private Document XmlDoc = null;
	
	/**
	 * Constructor will perform the validation and saving the dom if generated.
	 * 
	 * @param xmlFile - String, the XML file.
	 * 
	 * @throws ParserConfigurationException - if there is a problem with instantiating the DOM builder.
	 */
	public XmlReaderWriter( String xmlFile ) throws ParserConfigurationException, Exception
	{
		// Store the incoming XML file.
		this.xmlFile = xmlFile;
		this.XmlDoc = XmlValidator.validateXmlUsingDOM( xmlFile );
		
		if ( this.XmlDoc == null )
		{
			log.fatal( "Throw exception due to XML file problem: " + xmlFile );
			throw Constants.EXP;
		}
		else
		{
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			this.XmlDoc.getDocumentElement().normalize();
		}
	}
	
	/**
	 * queryDoc - Query the information from the XML document using a XPATH expression.
	 * 
	 * @param expression - String, the XPATH string
	 * 
	 * @return - array of strings holding the result.
	 */
	public ArrayList<String> queryDoc( String expression )
	{
		ArrayList<String> data = new ArrayList<String>( );
		
		IOUtils.prDebug( "XPATH: " + expression );
		
		try
		{
			XPath xPath =  XPathFactory.newInstance( ).newXPath( );
			String nodeValue;
			
			NodeList nodeList = ( NodeList )xPath.compile( expression ).evaluate( this.XmlDoc, XPathConstants.NODESET );
			
			for ( int indx = 0; indx < nodeList.getLength( ); indx++ )
			{
				nodeValue = nodeList.item( indx ).getFirstChild( ).getNodeValue( );
				data.add( nodeValue );
				
			   IOUtils.pr( "Node " + indx + ": " + nodeValue );
			}
		} 
		catch ( XPathExpressionException xpe )
		{
			// TODO: handle exception
		}
		
		return( data );
	}
	
	/**
	 * writeXmlFile - write document in memory to XML file.
	 * 
	 * @param outputXmlFile - String, the full path filename for the XML file.
	 * 
	 * @return - boolean, true if successful, false otherwise.
	 */
	public boolean writeXmlFile( String outputXmlFile )
	{
		boolean status = false;

		try 
		{
	      Transformer tr = TransformerFactory.newInstance( ).newTransformer( );
	      
	      tr.setOutputProperty( OutputKeys.INDENT, "yes" );
	      tr.setOutputProperty( OutputKeys.METHOD, "xml" );
	      tr.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
	      tr.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "roles.dtd" );
	      tr.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4");

	      // end DOM to file
	      tr.transform( new DOMSource( this.XmlDoc ), new StreamResult( new FileOutputStream( outputXmlFile ) ) );
		} 
		catch( TransformerException te ) 
		{
			System.out.println( te.getMessage( ) );
		} 
		catch( IOException ioe) 
		{
			System.out.println( ioe.getMessage( ) );
		} 
		
		return( status );
	}
	
	/**
	 * getXmlDoc - get the initialised Xml document.
	 * 
	 * @return - Document, the xml document if initialised, otherwise null.
	 */
	public Document getXmlDoc( )
	{
		return( this.XmlDoc );
	}
}
