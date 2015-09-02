package com.jcms.jcmsmobile;

import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;

import org.apache.cordova.*;

public class Home extends DroidGap {

	/**
	 * Called when the activity is first created.
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    CookieManager.setAcceptFileSchemeCookies(true);
		super.onCreate(savedInstanceState);
		super.loadUrl("file:///android_asset/www/index.html");
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WebView.setWebContentsDebuggingEnabled(true);
		}
	}
}
