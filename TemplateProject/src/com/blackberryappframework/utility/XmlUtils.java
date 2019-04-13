/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0420
 * 
 * @history    Date        author         reason
 *             2011/09/19  Xuetao Liu     Modify comments of getNodebyName() && getChildNodeByName()
 *             2011/04/20  Xuetao Liu     added the comments
 *             2011/03/22  Xuetao Liu     created                 
 **/

package com.blackberryappframework.utility;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.*;

import net.rim.device.api.xml.parsers.*;

/**
 * 
 */
public class XmlUtils {

	private XmlUtils() {    }
	
	/**
	 * To generate the XML DOCUMENT object via a given xmlString
	 * @
	 * @param xmlString the xml String
	 * @return the xml Document
	 * */
    public static Document createDocumentFromXml(String xmlString) {
        Document xmlDocument = null;
        DocumentBuilderFactory xmlFactory = null;
        DocumentBuilder xmlBuilder = null;

        String stringXMLValue = ((xmlString == null) || (xmlString.equals(""))) ? "<empty/>" : xmlString;
        try {
            xmlFactory = DocumentBuilderFactory.newInstance();
            xmlBuilder = xmlFactory.newDocumentBuilder();
            xmlBuilder.isValidating();
            xmlDocument = xmlBuilder.parse(XmlUtils.getInputStreamFromXml(stringXMLValue));
        } catch (Exception ex) {
        }
        return xmlDocument;
    }
    
	/**
	 * To generate the XML DOCUMENT object via a given file name
	 * 
	 * @param fileName  the full name of the xml file
	 * @return the xml Document
	 * */
    public static Document getXMLDocumentFromFile (String xmlFileName) {
    	Document document = null;

    	if ( ( xmlFileName != null ) && (xmlFileName != "" )) {
    		try {
	            InputStream inputStream = XmlUtils.class.getResourceAsStream( xmlFileName );

	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        document = builder.parse( inputStream );
		        document.getDocumentElement().normalize();
    		} catch (Exception e) {
    			document = null;
    		}
    	}
    	
        return document;
	}
	
	/**
	 * To generate the XML DOCUMENT object via a given file name
	 * */
    public static Document getXMLDocumentFromStream (InputStream inputStream) {
    	Document document = null;
    	
    	if ( inputStream != null ) {
    		try {
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		
		        document = builder.parse( inputStream );
		        document.getDocumentElement().normalize();
    		} catch (Exception e) {
    			document = null;
    		}
    	}
    	
        return document;
	}
    
    /**
     * Transfer an XML string to the InputStream format
     * 
     * @param xmlString the XML string to be transferred
     * @return the InputStream object
     * 
     * */
    public static InputStream getInputStreamFromXml(String xmlString) {
        InputStream inputStream = null;
        try{
            inputStream = xmlString == null ? null : new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
        }catch (Exception e){
            inputStream = null;
        }
        return inputStream;
    }

    /**
     * To search for the first node with a given name in the child nodes (including child nodes' children nodes)
     * The name is not capital sensitive
     * 
     * @param xmlNode the node where we starts to search for
     * @param name    the name of the node we are going to search
     * 
     * @return null   if the node is not found
     *         Node   if a node with the given name is found
     * */
    public static Node getNodeByName(Node xmlNode, String name)
    {
    	Node result = null;
    	
    	if ( (xmlNode == null) || (xmlNode.getNodeName().equalsIgnoreCase(name)))
    		return xmlNode;
    	
    	Node childNode = xmlNode.getFirstChild();
    	while (childNode != null) {
    		result = getNodeByName(childNode, name);
    		if (result != null)
    			break;
    		
    		childNode = childNode.getNextSibling();
    	}
    	
    	return result;
     }
    
    /**
     * To search for ALL the nodes with a given name in the child nodes (child only)
     * The name is not capital sensitive
     * 
     * @param xmlNode the node where we starts to search for
     * @param name    the name of the node we are going to search
     * 
     * @return null   if the node is not found
     *         Node   if a node with the given name is found
     * */
    public static Vector getChildNodesByName(Node xmlNode, String name)
    {
    	Vector result = new Vector();
    	
    	if (xmlNode != null) {
    		
	    	Node childNode = xmlNode.getFirstChild();
	    	while (childNode != null) {
	    		if (childNode.getNodeName().equalsIgnoreCase(name))
	    			result.addElement(childNode);
	    		
	    		childNode = childNode.getNextSibling();
	    	}
    	}
    	
    	return result;
     }
    
    /**
     * To search for ALL the nodes with a given name in the child nodes (child only)
     * The name is not capital sensitive
     * 
     * @param xmlNode the node where we starts to search for
     * @param name    the name of the node we are going to search
     * 
     * @return null   if the node is not found
     *         Node   if a node with the given name is found
     * */
    public static String getChildNodeValue(Node xmlNode, String nodeName)
    {
		String value = null;
		if (xmlNode != null) {
			Node node = XmlUtils.getNodeByName(xmlNode, nodeName);
			if (node != null) {
				value = XmlUtils.getNodeTextValue(node);
			}
		}
		
		return value;
     }
    
    
    /**
     * Obtain the value of a given node. The value in <node> value </node>
     * 
     * @param node the given node ... it should be a TextNode
     * 
     * @return return the node value as a string. 
     *                By default, the value is an empty string
     *            
     * Note:
     *    The node value should be Trimmed before return. Otherwise, it will add "\n" 
     *    mark at both the beginning and the end of the string
     * 
     * */
	public static String getNodeValue(Node node) {
		String value = null;
		
		if ( (node != null) && (node.getNodeValue() != null))
			value = node.getNodeValue().trim();
		
		return value;
	}
	
    /**
     * Obtain the value of a given node. The value in <node> value </node>
     * 
     * @param node the given node
     * 
     * @return return the node value as a string. 
     *                By default, the value is an empty string
     *            
     * Note:
     *    The node value should be Trimmed before return. Otherwise, it will add "\n" 
     *    mark at both the beginning and the end of the string
     * 
     * */
	public static String getNodeTextValue(Node node) {
		String value = null;
		
		if (node.getNodeType() == Node.TEXT_NODE) {
			value = getNodeValue(node);
		}else{
			Node childNode = node.getFirstChild();
			if (childNode != null && childNode.getNodeType() == Node.TEXT_NODE) {
				value = getNodeValue(childNode);
			}
		}
		return value;
	}
	/**
	 * Get an attribute node of a node via the name of the attribute
	 * 
	 * @param node the node where the attribute might be in
	 * @param attributename  the name of the attribute
	 * 
	 * @return null if the given node doesn't have such an attribute
	 *         the node
	 * */
	public static Node getNodeAttribute(Node node, String attributeName) {
		Node attNode = null;
		if (node != null) {
			NamedNodeMap attributes = node.getAttributes();
			if (attributes != null) {
				String name = (attributeName==null)? "" : attributeName;
				attNode = attributes.getNamedItem(name);
			}
		}
		
		return attNode;		
	}
	
	/**
	 * Get an attribute value of a node via the name of the attribute 
	 * @param node the node where the attribute might be in
	 * @param attributename  the name of the attribute
	 * 
	 * @return ""  if the given node doesn't have such an attribute
	 *         the node value
	 *         
	 * */
	public static String getNodeAttributeValue(Node node, String attributeName) {
		Node attNode = getNodeAttribute(node, attributeName);
		return getNodeValue(attNode);
	}

	/**
	 * Set the value of a node
	 * 
	 * @param node the node
	 * @param value value to be set
	 * 
	 * */
	public static void setNodeValue(Node node, String value) {
		if ( (node != null) && (value != null)) {
			node.setNodeValue(value);
		}
	}
	
	/**
	 * replace the special entities(", ', <, >, &) in XML syntax.
	 * @param xmlContent
	 * @return
	 */
	public static String replaceSpecialXMLEntity(String xmlContent)
	{
		Hashtable specialCharHashtable = new Hashtable();
		specialCharHashtable.put("&quot;" , "\"");
		specialCharHashtable.put("&amp;"  , "&");
		specialCharHashtable.put("&lt;"   , "<");
		specialCharHashtable.put("&gt;"   , ">");
		specialCharHashtable.put("&circ;" , "^");
		specialCharHashtable.put("&tilde;", "~");
		specialCharHashtable.put("&ndash;", "-");
		specialCharHashtable.put("&lsquo;", "'");
		specialCharHashtable.put("&rsquo;", "'");
		specialCharHashtable.put("&#8230", "...");
		specialCharHashtable.put("&nbsp;", " ");
		specialCharHashtable.put("&Acirc;", " ");
		specialCharHashtable.put("&Aacute;", " ");
		
		String[] specialEntity={"&quot;", "&amp;", "&lt;", "&gt;", "&circ;", 
				"&tilde;", "&ndash;", "&lsquo;", "&rsquo;", "&#8230", "&nbsp;","&Acirc;",
				"&Aacute;"};
    	
		int beginIndex = xmlContent.indexOf("&#", 0);
		int endIndex;
		while(beginIndex != -1)
		{
			endIndex = xmlContent.indexOf(";", beginIndex);
			int value = Integer.parseInt((xmlContent.substring(beginIndex+2, endIndex)));
			char entity = (char) value;
			xmlContent = xmlContent.substring(0, beginIndex) + entity + xmlContent.substring(endIndex+1);
		    beginIndex = xmlContent.indexOf("&#", beginIndex);
		}
		
		for(int i=0; i<specialEntity.length; i++)
    	{
    		int index=0;
    		String entity = specialEntity[i];
        	while(xmlContent.indexOf(entity, index) != -1)
        	{
        		index = xmlContent.indexOf(entity, index);
        		xmlContent = xmlContent.substring(0, index) + specialCharHashtable.get(entity) 
        		            + xmlContent.substring(index + entity.length());
        	}
    	}
    	
    	return xmlContent;
	}

} 
