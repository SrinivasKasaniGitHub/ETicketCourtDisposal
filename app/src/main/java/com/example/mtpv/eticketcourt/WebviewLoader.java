package com.example.mtpv.eticketcourt;

import android.util.Log;
import android.webkit.WebView;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class WebviewLoader {

	private Map<WebView, String> webviews = Collections
			.synchronizedMap(new WeakHashMap<WebView, String>());

	public void DisplayImage(String url, WebView wv) {
		// if (url.equals("")) {
		// webviews.put(wv, url);
		// String page = "<html><body><center></center></body></html>";
		// wv.loadDataWithBaseURL("fake", page, "text/html", "UTF-8", "");
		// } else {
		Log.i("**WebviewLoader class**", "" + url);
		
		webviews.put(wv, url);
		String page = "<html><body><center><img src=\"" + url
				+ "\" width=\"100%\"/></center></body></html>";
		wv.loadDataWithBaseURL("fake", page, "text/html", "UTF-8", "");
		// }
	}

}
