1) This project uses Maven as the build tool
2) Please find the below steps for installing Maven in a debian system:
3) Instructions for running the project:
	- Navigate to the folder having the source code of the project
	- Execute the commands:
		* mvn -q clean
		* mvn -q package
	- Naviage to the target folder under the current source code directory
	- Execute the jar with the command:
		* java -jar json-xml-converter-0.0.1-SNAPSHOT-jar-with-dependencies.jar C:\\Users\krish\Desktop\input.json C:\\Users\krish\Desktop 
4) External libraries used:
	- jackson-databind (for JSON parsing)
		* https://mkyong.com/java/how-to-convert-java-map-to-from-json-jackson/
	- commons-lang3 (Apache commons for utility functions)
		* https://www.baeldung.com/java-check-string-number
5) Repository is available at
	- https://github.com/KrishnanAsish/json-xml-converter