package com.example.ws13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ws13.utils.Contacts;

@SpringBootApplication
public class Ws13Application {

	// private static final String BASE_DIR = "src";

	public static void main(String[] args) {
		// SpringApplication.run(Ws13Application.class, args);

		// TODO: remove after testing
		String[] myArgs = {"--dataDir", "/opt/tmp/data"}; 
		args = myArgs;

		// Check exactly two arguments
		if(args.length != 2) System.exit(-1);

		// Check arg[0] is exactly "--dataDir"
		if(!args[0].equals("--dataDir")) System.exit(-1);

		// Try to create directories
		if(!Contacts.setupDirectory(args[1])) System.exit(-1);

		SpringApplication app = new SpringApplication(Ws13Application.class);
		app.run(args[0]+"="+args[1]);
	}

}
