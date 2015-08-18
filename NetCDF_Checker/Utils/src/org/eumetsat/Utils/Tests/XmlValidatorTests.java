package org.eumetsat.Utils.Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eumetsat.Utils.Common.IOUtils;
import org.eumetsat.Utils.Common.XmlValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlValidatorTests
{
	private static final String TEST_FILE_OK = "f:/F-Work/workspace/Utils/tests/howto.xml";
	private static final String TEST_FILE_OK_XSD = "f:/F-Work/workspace/Utils/tests/howto.xsd";
	
	private static final String TEST_FILE1 = "f:/F-Work/workspace/Utils/tests/howto_err1.xml";
	private static final String TEST_FILE1_XSD = "f:/F-Work/workspace/Utils/tests/howto_err1.xsd";
	
	private static final String TEST_FILE2 = "f:/F-Work/workspace/Utils/tests/howto_err2.xml";
	private static final String TEST_FILE2_XSD = "f:/F-Work/workspace/Utils/tests/howto_err2.xsd";
	
	private static final String TEST_FILE3 = "f:/F-Work/workspace/Utils/tests/howto_err3.xml";
	private static final String TEST_FILE3_XSD = "f:/F-Work/workspace/Utils/tests/howto_err3.xsd";
	
	private static final String TEST_FILE_EMPLOYEES = "f:/F-Work/workspace/Utils/tests/employees.xml";
	
	private static short testCount = 0;
	
	@Before
	public void setUp( ) throws Exception
	{
	}

	@After
	public void tearDown( ) throws Exception
	{
	}

	/**
	 * runDomValidation - utility function to reduce the length of the tests code.
	 * 
	 * @param testFile - String, the XML file.
	 * 
	 * @throws ParserConfigurationException - if there builder fails to instantiate.
	 * @throws IOException - if there are IO problems.
	 */
	private Document runDomValidation( String testFile ) throws ParserConfigurationException
	{
		Document doc = null;
		
		try
		{
			doc = XmlValidator.validateXmlUsingDOM( testFile );		
		}
		catch ( ParserConfigurationException pce )
		{
			IOUtils.pr( "Error in instantiating the SAX parse classes." );
			throw pce;
		}
		
		return( doc );
	}
	
	/**
	 * runSaxValidation - utility function to reduce the length of the tests code.
	 * 
	 * @param xmlFile - String, the XML file.
	 * @param xsdFile - String, the XSD file.

	 * 
	 * @throws ParserConfigurationException - if there builder fails to instantiate.
	 * @throws IOException - if there are IO problems.
	 */
	private void runSaxValidation( String xmlFile, String xsdFile ) throws ParserConfigurationException
	{
		testCount++;
		
		try
		{
			// Test file 1 has an incorrect root tag.
			// Expected SAX Exception with parse error:
			//
			// ERROR: cvc-elt.1: Cannot find the declaration of element 'howto'.
			IOUtils.pr( "Test " + testCount + ": SAX validation result = " + XmlValidator.validateXmlUsingSax( xmlFile, xsdFile ) );			
		}
		catch ( ParserConfigurationException pce )
		{
			IOUtils.pr( "Error in instantiating the SAX parse classes." );
			throw pce;
		}
	}
	
	@Test
	public void validateXmlUsingDOM( ) throws ParserConfigurationException, IOException, SAXException
	{
		IOUtils.prTestMethod( IOUtils.getMyMethodName( ) );

		// Test file OK has no errors and will return the DOM document.
		Document doc = XmlValidator.validateXmlUsingDOM( TEST_FILE_OK );
		String uriStr = doc.getBaseURI( );
		System.out.println( "URI: " + uriStr );
		assert( uriStr != null );
		
		// No XSD file defined here.
		doc = XmlValidator.validateXmlUsingDOM( TEST_FILE_EMPLOYEES );
		uriStr = doc.getBaseURI( );
		System.out.println( "URI: " + uriStr );
		assert( uriStr != null );

		// Test file 1 has an incorrect root tag.
		// Expected SAX Exception with parse error:
		//
		// ERROR: cvc-elt.1: Cannot find the declaration of element 'howto'.
		runDomValidation( TEST_FILE1 );
		assert( doc == null );

		// Test file 2 contains parsing errors.
		// 
		// Expected SAX Exception with parse error:
		//
		// FATAL: The end-tag for element type "topic" must end with a '>' delimiter.
		// There are many more errors but only the first is detected.
		runDomValidation( TEST_FILE2 );
		assert( doc == null );
		
		// Test file 3 does not exist, IO Exception expected.		
		runDomValidation( TEST_FILE3 );
		assert( doc == null );
	}
	
	@Test
	public void validateXmlUsingSAX( ) throws ParserConfigurationException, IOException, SAXException
	{
		IOUtils.prTestMethod( IOUtils.getMyMethodName( ) );
		
		// Test file OK is valid.
		runSaxValidation( TEST_FILE_OK, TEST_FILE_OK_XSD );
		
		// Test file 1 has an incorrect root tag.val
		// Expected SAX Exception with parse error:
		//
		// ERROR: cvc-elt.1: Cannot find the declaration of element 'howto'.
		runSaxValidation( TEST_FILE1, TEST_FILE1_XSD );
		
		// Test file 2 is not formulated correctly.
		// Expected SAX Exception with parse error:
		//
		// FATAL: The end-tag for element type "topic" must end with a '>' delimiter.
		// There are many more errors but only the first is detected.
		runSaxValidation( TEST_FILE2, TEST_FILE2_XSD );

		// Test file 3 xsd does not exist.
		// Expected IO Exception.
		runSaxValidation( TEST_FILE3, TEST_FILE3_XSD );		
	}
}
