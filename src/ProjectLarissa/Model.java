package ProjectLarissa;

import java.net.*;

public class Model {


	
//////////////////////////////////////////////////////
/////////////   NETWORK METHODS    ///////////////////
//////////////////////////////////////////////////////
	//NETWORK VARS
	private static InetAddress inetHostObj;
	private static InetAddress inetGuestObj;
	private static NetworkInterface localNetInt;

	// init empty host InetAddress obj; Probably should be done first thing.
	public static void inetHostObjInit() throws UnknownHostException{
		inetHostObj = InetAddress.getLocalHost();
	
}
	
	public static void netIntObj() throws SocketException {
		localNetInt = NetworkInterface.getByInetAddress(getHostObj());
	}
	
	//get ref to instance of either Host or Guest InetAddress object
	public static InetAddress getInetObj(String objName) {
		return (objName == "Host") ? inetHostObj : inetGuestObj;
}
	
	public static InetAddress getHostObj() {
		return inetHostObj;
	}

	public static NetworkInterface getNi() {
		return localNetInt;
	}
	
	public static String getInetLocalHostName(InetAddress localInetObj) {
		return localInetObj.getHostName();
	}
	
	public static String getInetLocalHostAddr(InetAddress localInetObj) {
		return localInetObj.getHostAddress();
	}
	
	public static String getNetIntMac(NetworkInterface nI) throws SocketException{
		byte[] ipArray;
		String returnStr = "";
		ipArray = nI.getHardwareAddress();
		for(byte b : ipArray) {
			returnStr += String.format("%02x", b);
		}
		return returnStr;
	}
//////////////////////////////////////////////////////
///////////// END NETWORK METHODS    /////////////////
//////////////////////////////////////////////////////
}
