package com.axway.apim.appexport.lib;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import com.axway.apim.lib.StandardExportCLIOptions;

public class AppExportCLIOptions extends StandardExportCLIOptions {

	CommandLine cmd;

	public AppExportCLIOptions(String[] args) throws ParseException {
		super(args);
		// Define command line options required for Application export
		Option option = new  Option("n", "name", true, "Name of the application to export. You may use wildcards at the end or beginning. (e.g. *App*).");
		option.setRequired(false);
		options.addOption(option);
		
		option = new  Option("id", true, "The ID of the application.");
		option.setRequired(false);
		options.addOption(option);

		option = new  Option("state", true, "Export application with specific state: pending | approved");
		option.setRequired(false);
		options.addOption(option);

		option = new  Option("orgName", true, "Limit applications to this organization");
		option.setRequired(false);
		options.addOption(option);

		option = new Option("l", "localFolder", true, "Defines the local folder to store the application locally. Defaults to current folder.\n"
				+ "For each application a new folder is created automatically.");
		option.setRequired(false);
		option.setArgName("my/apps");
		options.addOption(option);
	}

	@Override
	public void printUsage(String message, String[] args) {
		super.printUsage(message, args);		
		System.out.println("You may run one of the following examples:");
		System.out.println("Using parameters provided in properties file stored in conf-folder:");
		System.out.println(getBinaryName()+" app get -n \"Client App\" -s api-env -f json");
		System.out.println(getBinaryName()+" app get -n \"Client App\" -l /tmp/exported_apps -deleteFolder -s api-env -f json");
		System.out.println(getBinaryName()+" app get -n \"App 123\" -l /tmp/exported_apps -s api-env -f json -deleteFolder");
		System.out.println();
		System.out.println();
		System.out.println(getBinaryName()+" app get -n \"Client App\" -h localhost -u apiadmin -p changeme -f json");
		System.out.println(getBinaryName()+" app get -n \"App 123\" -h localhost -u apiadmin -p changeme -s prod -f json -deleteFolder");
		System.out.println();
		System.out.println("For more information and advanced examples please visit:");
		System.out.println("https://github.com/Axway-API-Management-Plus/apim-cli/wiki");
	}

	@Override
	protected String getAppName() {
		return "Application-Export";
	}


}
