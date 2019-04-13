package com.blackberryappframework.TemplateProject.model;

import org.w3c.dom.Node;

import com.blackberryappframework.utility.XmlUtils;

import net.rim.device.api.util.Persistable;

public class Province implements Persistable{
	public static Province createProvinceFromXml(Node xmlNode) {
		Province province = new Province();
		
		String value;
		value = XmlUtils.getChildNodeValue(xmlNode, "Province_ID");
		if (value != null) {
			province.id = value;
		} else 
			return null;

		value = XmlUtils.getChildNodeValue(xmlNode, "Province_Name");
		if (value != null) {
			province.name = value;
		} else 
			return null;
		
		value = XmlUtils.getChildNodeValue(xmlNode, "Province_Code");
		if (value != null) {
			province.code = value;
		} else 
			return null;
		
		return province;
	}
	
	public String id;
	public String name;
	public String code;
}
