package com.zhaopin.thrift.rpc.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public class IPResolver {

	public static Logger LOGGER = LoggerFactory.getLogger(IPResolver.class);

	public static String getIP() {
		String ip = getLocalIp();
		String osName = System.getProperties().getProperty("os.name");
		if (osName != null) {
			osName = osName.toLowerCase();
			if (osName.startsWith("linux")) {
				try {
					Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
					while (allNetInterfaces.hasMoreElements()) {
						NetworkInterface netInterface = allNetInterfaces.nextElement();
						Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
						if (netInterface.getName().startsWith("eth")) {
							while (addresses.hasMoreElements()) {
								InetAddress netAddr = addresses.nextElement();
								if (netAddr != null && netAddr instanceof Inet4Address) {
									return netAddr.getHostAddress();
								}
							}
						}
					}
				} catch (SocketException exp) {
					LOGGER.error("resole local ip fail", exp);
				}
			} else {
				return getLocalIp();
			} 
		}
		return ip;
	}
	
	public static String getLocalIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException exp) {
			LOGGER.error("resole local ip fail", exp);
		}
		return null;
	}
}
