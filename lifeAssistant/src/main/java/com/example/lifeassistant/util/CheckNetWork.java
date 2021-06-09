package com.example.lifeassistant.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检查网络
 * @author ChnAdo
 *
 */
public class CheckNetWork {
	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 */
	public boolean isNetWorkConnected(Context context){
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo!=null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}
	/**
	 * 判断FIWI是否可用
	 * @param context
	 * @return
	 */
	public boolean isWifiConnected(Context context){
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mWifiNetworkInfo!=null) {
			return mWifiNetworkInfo.isAvailable();
			
		}
		return false;
	}
	/**
	 * 判断3G网络是否可用
	 * @param context
	 * @return
	 */
	public boolean isMobileConnected(Context context){
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mmobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mmobileNetworkInfo!=null) {
			return mmobileNetworkInfo.isAvailable();
			
		}
		return false;
	}
}
