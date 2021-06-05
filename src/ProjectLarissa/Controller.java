package ProjectLarissa;

import javax.swing.*;
import java.net.*;
import java.util.*;

public class Controller 
{
	
	private static Model model;
	private static Viewer viewer;
	

	Controller() throws UnknownHostException, SocketException{
		this.model = new Model();
		this.viewer = new Viewer();
		model.inetHostObjInit();
		model.netIntObj();
		populateFields();
	}
	
	//As a test, set the Local Host name field
	
	
	public static void populateFields() throws SocketException{
		viewer.setJLabel(viewer.hostNameLabelData, model.getInetLocalHostName(model.getHostObj()));
		viewer.setJLabel(viewer.localHostIpLabelData, model.getInetLocalHostAddr(model.getHostObj()));
		viewer.setJLabel(viewer.localHostMacAddrLabelData, model.getNetIntMac(model.getNi()));
	}
	

	
}
