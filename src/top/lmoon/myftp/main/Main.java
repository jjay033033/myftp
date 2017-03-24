package top.lmoon.myftp.main;

import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import top.lmoon.myftp.util.FileUtil;
import top.lmoon.myftp.util.MD5Util;
import top.lmoon.myftp.util.StringUtil;

public class Main {
	
	static {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/res/log4j.properties");
	}

	private static final Logger logger = Logger.getLogger(Main.class);
	
	private static final String USERS_PROPERTIES_PATH = "./res/conf/users.properties";
	private static final String ADMIN_PASSWORD_KEY = "ftpserver.user.admin.userpassword=";

	public static void main(String[] args) {
		try {
			if (args != null && args.length > 0) {
				String psw = args[0].trim();
				if (!psw.isEmpty()) {
					String pswMd5 = MD5Util.getMD5(psw);
					if(StringUtil.isNotNullOrEmpty(pswMd5)){
						String temp = FileUtil.readFileAndReplaceValue(USERS_PROPERTIES_PATH, ADMIN_PASSWORD_KEY, pswMd5);
						if(StringUtil.isNotNullOrEmpty(temp)){
							FileUtil.writeFile(temp, USERS_PROPERTIES_PATH);
						}
					}

				}
			}

			FtpServerFactory serverFactory = new FtpServerFactory();

			PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
			userManagerFactory.setFile(new File(USERS_PROPERTIES_PATH));

			UserManager userManager = userManagerFactory.createUserManager();
			// BaseUser user = new BaseUser();
			// user.setName("abc");
			// user.setPassword("efg");
			// user.setHomeDirectory("e:/");
			// userManager.save(user);

			serverFactory.setUserManager(userManager);

			FtpServer server = serverFactory.createServer();

			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("出错了：", e);
		}

	}

}
