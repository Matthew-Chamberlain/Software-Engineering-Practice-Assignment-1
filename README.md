# Software-Engineering-Practice-Assignment-2

Execution Paramters
User: The name of the user
Host: The name of the host
Port: The port needed to connect to the server
Language: the language code used to define what locale resourcepack is used
Country: the Country code used to define what locale resourcepack is used

Command Pattern
The application implemetns the command design pattern making it especially easy to implement new commands. in order to implement a new command the following steps must be taken:
1. A new class named after the command should be creted which inherits from the command interface
2  The command name and an object of new command class should be added to the HashMap inside the CommandChooser class
3. The command name should be added to either the formatMainMenuPrompt or the formatDraftingMenuPormpt methods inside the CLFormatter class
4. Any addition methods needed to complete the action should be added to the CLFormatter class

To Add new language support to the client the following steps should be taken
Create a new java resource file named MessageBundle_langugecode_countrycode.properties
Look inside one of the existing files to get the list of strings that is needed to be translated
translate phrases and paste them into new resource file next to relavent heading
If any other hardcoded strings are found throughout extensions simple add a new heading to all the resource files, translate the phrase into all the necessary languages and add the following line of code messages.getString(STRING HEADING);
