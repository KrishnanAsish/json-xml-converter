package com.converter.validators;

import java.io.File;
import java.io.IOException;

import com.converter.constants.ConverterConstants;
import com.converter.exceptions.ValidationException;

public class ValidationUtils {

	public static void validateCliArguments(String[] args) throws ValidationException {
		if(args==null || args.length!=2)
			throw new ValidationException(ConverterConstants.invalidCliArguments);
	}

	public static File[] returnValidatedFilePointers(String inputJsonPath, String outputXmlPath) throws ValidationException {
		File[] validatedFilePointers = new File[2];
		File inputJsonFile = returnValidatedInputJsonPath(inputJsonPath);
		File outputXmlFile = returnFineTunedOutputXmlPath(outputXmlPath);
		validatedFilePointers[0]=inputJsonFile;
		validatedFilePointers[1]=outputXmlFile;
		return validatedFilePointers;
	}

	public static File returnValidatedInputJsonPath(String inputJsonPath) throws ValidationException {
		File inputJsonFile = new File(inputJsonPath);
		if(!inputJsonFile.exists())
			throw new ValidationException(ConverterConstants.filePathDoesNotExist+inputJsonPath);
		if(inputJsonFile.isDirectory())
			throw new ValidationException(ConverterConstants.pathShouldPointToFile);
		if(!inputJsonFile.canRead())
			throw new ValidationException(ConverterConstants.fileDoesNotHaveReadAccess+inputJsonPath);
		if(!inputJsonFile.getName().endsWith(".json")) 
			throw new ValidationException(ConverterConstants.pathShouldPointToFile);
		return inputJsonFile;
	}

	public static File returnFineTunedOutputXmlPath(String outputXmlPath) throws ValidationException {
		File outputXmlFile = new File(outputXmlPath);
		if(outputXmlFile.isDirectory()) {
			outputXmlFile = new File(outputXmlFile+"\\output.xml");
			System.out.println("Output file will be created as:"+outputXmlFile.getAbsolutePath());
		}
		try {
			if(!outputXmlFile.exists() && !outputXmlFile.createNewFile())
				throw new ValidationException(ConverterConstants.unableToCreateOutputXmlFile+outputXmlFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ValidationException(ConverterConstants.unableToCreateOutputXmlFile+outputXmlFile);
		}
		if(!outputXmlFile.canWrite())
			throw new ValidationException(ConverterConstants.fileDoesNotHaveWriteAccess+outputXmlFile);
		return outputXmlFile;
	}
}
