package com.converter.constants;

public interface ConverterConstants {
		
		String convIdentifierCsw = "CSW";
		String invalidCliArguments = "Please provide the right set of input arguments:\\r\\n"+
									 "java -jar <Path to input Json> <Path for output XML>";
		String filePathDoesNotExist = "The path doesn't exist:";
		String pathShouldPointToFile = "The input file path should point to a json file";
		String inputFileIsNotJsonType = "The input file should be of JSON type (\".json\")";
		String fileDoesNotHaveReadAccess = "The file does not seem to have read access:";
		String fileDoesNotHaveWriteAccess = "The file does not seem to have write access:";
		String unableToCreateOutputXmlFile = "Unable to create an output XML file at:";
		String exceptionOccurred = "Unexpected exception occurred:";
		
		String XML_OBJECT_TAG = "object";
		String XML_NUMBER_TAG = "number";
		String XML_NULL_TAG = "null";
		String XML_STRING_TAG = "string";
		String XML_BOOLEAN_TAG = "boolean";
		String XML_ARRAY_TAG = "array";
		String XML_NAME_ATTRIBUTE = "name";
}
