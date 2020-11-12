package com.converter.start;

import java.io.File;

import com.converter.constants.ConverterConstants;
import com.converter.exceptions.ValidationException;
import com.converter.factory.ConverterFactory;
import com.converter.interfaces.XMLJSONConverterI;
import com.converter.validators.ValidationUtils;

public class ConversionStarter {

	public static void main(String[] args) {
		try {
			ValidationUtils.validateCliArguments(args);
			File[] validatedFilePointers = ValidationUtils.returnValidatedFilePointers(args[0],args[1]);
			XMLJSONConverterI converter = ConverterFactory.createXMLJSONConverter(ConverterConstants.convIdentifierCsw);
			converter.convertJSONtoXML(validatedFilePointers[0], validatedFilePointers[1]);
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
