package com.converter.factory;

import com.converter.constants.ConverterConstants;
import com.converter.implementations.CswConverter;
import com.converter.interfaces.XMLJSONConverterI;

public class ConverterFactory {

	public static XMLJSONConverterI createXMLJSONConverter(String convIdentifier) {
		if(convIdentifier.equals(ConverterConstants.convIdentifierCsw))
				return new CswConverter();
		else return null;
	}
}
