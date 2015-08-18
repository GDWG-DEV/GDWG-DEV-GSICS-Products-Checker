package org.eumetsat.Utils.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.eumetsat.Utils.Common.IOUtils;
import org.eumetsat.Utils.Common.XmlValidator;
import org.eumetsat.Utils.Tools.XmlReaderWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;

public class XmlReaderWriterTests
{
	private static final String xmlFile = "f:/F-Work/workspace/Utils/tests/employees.xml";
	private static final String destFile = "f:/F-Work/workspace/Utils/newXML.xml";
	
	private static final String exp1 = "/employees/employee[@emplid='3333']/email";
	private static final String exp2 = "/employees/employee/firstname";
	private static final String exp3 = "/employees/employee[@type='admin']/firstname";
	private static final String exp4 = "/employees/employee[@emplid='2222']";
	private static final String exp5 = "/employees/employee[age>40]/firstname";
	private static final String exp6 = "/employees/employee[1]/firstname";
	private static final String exp7 = "/employees/employee[position() <= 2]/firstname";
	private static final String exp8 = "/employees/employee[last()]/firstname";
	
	private static final String exp9 = "/employees/employee[last()]/@emplid";
	private static final String exp10 = "/employees/employee/@emplid";
	private static final String exp11 = "/employees/employee/email/@type";
	
	private static final String exp12 = "/employees/employee/rubbish";
	
	@Before
	public void setUp( ) throws Exception
	{
	}

	@After
	public void tearDown( ) throws Exception
	{
	}

	@Test
	public void testQueryDoc( ) throws ParserConfigurationException, Exception
	{
		IOUtils.prTestMethod( IOUtils.getMyMethodName( ) );
		IOUtils.setDebugFlag( true );
		
		XmlReaderWriter reader = new XmlReaderWriter( xmlFile );
		
		ArrayList<String> myVal = reader.queryDoc( exp1 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp2 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp3 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp4 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp5 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp6 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp7 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp8 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp9 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp10 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp11 );
		IOUtils.prSep( );
		myVal = reader.queryDoc( exp12 );
		IOUtils.prSep( );
	}

	@Test
	public void testWriteXmlFile( ) throws ParserConfigurationException, Exception
	{
		IOUtils.prTestMethod( IOUtils.getMyMethodName( ) );
		
		XmlReaderWriter readerWriter = new XmlReaderWriter( xmlFile );
		
		Document myDoc = readerWriter.getXmlDoc( );
		
		// Get the root element.
		Element root = myDoc.getDocumentElement( );
		
		// Create another employee
		//
		// <employee emplid="1212" type="superuser">
		// <firstname>Captain</firstname>
	   // <lastname>America</lastname>
	   // <age>800</age>
	   // <email type="local">captain@gmail.com</email>
		Element employee = myDoc.createElement( "employee" );
		root.appendChild( employee );
		Attr attr = myDoc.createAttribute( "emplid" );
		attr.setValue( "1212" );
		employee.setAttributeNode( attr );
		Attr attr2 = myDoc.createAttribute( "type" );
		attr2.setValue( "superuser" );
		employee.setAttributeNode( attr2 );
		
		Element firstname = myDoc.createElement( "firstname" );
		firstname.appendChild( myDoc.createTextNode( "Peter" ) );
		employee.appendChild( firstname );
		
		Element lastname = myDoc.createElement( "lastname" );
		lastname.appendChild( myDoc.createTextNode( "Miu" ) );
		employee.appendChild( lastname );
		
		Element age = myDoc.createElement( "age" );
		age.appendChild( myDoc.createTextNode( "50" ) );
		employee.appendChild( age );
		
		Element email = myDoc.createElement( "email" );
		Attr attr3 = myDoc.createAttribute( "type" );
		attr3.setValue( "Specialized" );
		email.setAttributeNode( attr3 );
		email.appendChild( myDoc.createTextNode( "pm@gmail.com" ) );
		employee.appendChild( email );
		
		readerWriter.writeXmlFile( destFile );
	}
}
