package ru.aselit;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class BaseAppRun {

	/**
	 * 
	 * @param prefix
	 * @return
	 */
	private static String getArchFileName(String prefix) {
	    
		return prefix.concat("_").concat(getOSName()).concat("_").concat(getArchName()).concat(".jar");
	}
	
	/**
	 * 
	 * @return
	 */
	private static String getOSName() {
		
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (null == osName)
			throw new RuntimeException("\"os.name\" property is not set.");
		else
			osName = osName.toLowerCase();
		if (osName.contains("win"))
			return "win";
		else if (osName.contains("mac"))
			return "mac";
		else if (osName.contains("linux") || osName.contains("nix"))
			return "linux";
		else
			throw new RuntimeException(String.format("Unknown OS name: %s.", osName));
	}
	
	private static String getArchName() {
		
	    String osArch = System.getProperty("os.arch");
	    return ((null != osArch) && (osArch.contains("64"))) ? "64" : "32";
	}
	
	private static void addJarToClasspath(String jarFile) {
	    
	    try {

	        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
	        method.setAccessible(true);
	    	URL url = new URL("rsrc:" + jarFile);
	        method.invoke(urlClassLoader, url);
	    } catch (Throwable t) {
	        
	    	t.printStackTrace();
	    }
	}
}
