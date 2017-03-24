package top.lmoon.myftp.main;

import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

public class Main {

	public static void main(String[] args) throws FtpException {

		FtpServerFactory serverFactory = new FtpServerFactory();

		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		userManagerFactory.setFile(new File("conf/users.properties"));

		serverFactory.setUserManager(userManagerFactory.createUserManager());

		FtpServer server = serverFactory.createServer();
		server.start();

	}

}
