package com.keren;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;


import com.baidu.apistore.sdk.ApiStoreSDK;

import java.util.List;

public class MyApplication extends Application{

	
	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getWindowParams() {
		return windowParams;
	}

	@Override
	public void onCreate() {
		ApiStoreSDK.init(this, "3ced289b7b8cb0f10edf8a272884aa78");
		super.onCreate();

	}
}
