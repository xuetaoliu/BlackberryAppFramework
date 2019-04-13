package com.blackberryappframework.utility.googleSearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.io.HttpConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.blackberryappframework.utility.ConnectionUtils;
import com.blackberryappframework.utility.StringUtils;
import com.blackberryappframework.utility.XmlUtils;


public class GoogleAddressSearch {
	private static final String googleAddSearch = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
	private static final String googleAddSearchRegion="&sensor=true&region=";
	
	public static Vector searchGoogleAddress(String address, String region) {

		Vector result = null;
		
		String noSpaceAddress = StringUtils.replace(address, " ", "%20", true);
		String urlStr = googleAddSearch.concat(noSpaceAddress);
		if (region == null || region.trim().length() == 0) {
			urlStr = urlStr.concat(googleAddSearchRegion+"ca");
		} else {
			urlStr = urlStr.concat(googleAddSearchRegion+region);
		}
		
		HttpConnection httpCon = ConnectionUtils.getHttpConnection(urlStr);
		if (httpCon != null) {
			try {
				httpCon.setRequestMethod(HttpConnection.GET);
				InputStream is = httpCon.openInputStream();
				if (is!= null) {
					Document document = XmlUtils.getXMLDocumentFromStream(is);
					if (document != null) {
						result = parseGoogleSearchAddress(document);
					}
					
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		   	   	try {
					httpCon.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	
	/**
	 * google search XML format:
	 * 
	 * <GeocodeResponse>
	 *    <status>OK</status>
	 *    <result> </result>
	 *    <result> </result>
	 * </GeocodeResponse>
	 * 
	 * */
	private static Vector parseGoogleSearchAddress(Document document) {
		Vector result = new Vector();
		
		String nodeValue;
		Node node = document.getFirstChild();
		if (node != null) {
			Node geocodeNode = XmlUtils.getNodeByName(node, "GeocodeResponse");
			if (geocodeNode != null) {
				node = XmlUtils.getNodeByName(geocodeNode, "status");
				if (node != null) {
					nodeValue = XmlUtils.getNodeTextValue(node);
					if (nodeValue != null && nodeValue.equalsIgnoreCase("OK")) {
						
						Vector searchResults = XmlUtils.getChildNodesByName(geocodeNode, "result");
						for (int addIdx = 0; addIdx < searchResults.size(); addIdx++) {
							Node locNode = (Node)searchResults.elementAt(addIdx);
							
							GoogleAddress address = new GoogleAddress();
							result.addElement(address);
							
							Vector addressComponents = XmlUtils.getChildNodesByName(locNode, "address_component");
							for (int compIdx = 0; compIdx < addressComponents.size(); compIdx++) {
								Node addComponentNode = (Node)addressComponents.elementAt(compIdx);
								
								Node typeNode = XmlUtils.getNodeByName(addComponentNode, "type");
								if (typeNode != null) {
									Node longNameNode = XmlUtils.getNodeByName(addComponentNode, "long_name");
									Node shortNameNode = XmlUtils.getNodeByName(addComponentNode, "short_name");
									
									String longName = XmlUtils.getNodeTextValue(longNameNode);
									String shortName = XmlUtils.getNodeTextValue(shortNameNode);
									String type = XmlUtils.getNodeTextValue(typeNode); 
									if (type.equalsIgnoreCase("street_number")) {
										address.setStreetNumber(longName);
									} else if (type.equalsIgnoreCase("route")) {
										address.setStreet(longName);
									} else if (type.equalsIgnoreCase("locality")) {
										address.setCity(longName);
									} else if (type.equalsIgnoreCase("administrative_area_level_1")) {
										address.setProvince(shortName);
									} else if (type.equalsIgnoreCase("country")) {
										address.setCountry(shortName);
									} else if (type.equalsIgnoreCase("postal_code_prefix")) {
										address.setZipcode(longName);
									}
								}
							}
							
							Node geometryNode = XmlUtils.getNodeByName(locNode, "geometry");
							if (geometryNode != null) {
								Node location = XmlUtils.getNodeByName(geometryNode, "location");
								Node latitudeNode = XmlUtils.getNodeByName(location, "lat");
								Node longtitudeNode = XmlUtils.getNodeByName(location, "lng");
								
								String latitude = null;
								String longtitude = null;
								if (latitudeNode != null) {
									latitude = XmlUtils.getNodeTextValue(latitudeNode);
								}
								if (longtitudeNode != null) {
									longtitude = XmlUtils.getNodeTextValue(longtitudeNode);
								}
								
								address.setLatitude(Float.valueOf(latitude).floatValue());
								address.setLongtitude(Float.valueOf(longtitude).floatValue());
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
}
