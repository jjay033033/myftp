package top.lmoon.myftp.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	private static final String KEY_ADMIN_PASSWORD = "ftpserver.user.admin.userpassword=";
	private static final String KEY_ADMIN_DIRECTORY = "ftpserver.user.admin.homedirectory=";
	private static final String PREFIX = "-";
	private static final String PREFIX_ADMIN_PASSWORD = PREFIX + "p";
	private static final String PREFIX_ADMIN_DIRECTORY = PREFIX + "d";

	public static void main(String[] args) {
		try {
			Map<String,String> valueMap = getValuesMapFromArgs(args);
			if(valueMap!=null&&!valueMap.isEmpty()){
				String temp = FileUtil.readFileAndReplaceValues(USERS_PROPERTIES_PATH, valueMap);
				if(StringUtil.isNotNullOrEmpty(temp)){
					FileUtil.writeFile(temp, USERS_PROPERTIES_PATH);
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
	
	private static Map<String,String> getValuesMapFromArgs(String[] args){
		Map<String,String> map = new HashMap<String,String>();
		if (args != null && args.length > 0) {
			for(int i=1;i<args.length;i++){
				if(!args[i].startsWith(PREFIX)){
					if(PREFIX_ADMIN_PASSWORD.equals(args[i-1])){
						String pswMd5 = MD5Util.getMD5(args[i]);
						if(StringUtil.isNotNullOrEmpty(pswMd5)){
							map.put(KEY_ADMIN_PASSWORD, pswMd5);
						}
					}else if(PREFIX_ADMIN_DIRECTORY.equals(args[i-1])){
						map.put(KEY_ADMIN_DIRECTORY, args[i]);
					}
				}
				
			}
			
		}
		return map;
	}

}
