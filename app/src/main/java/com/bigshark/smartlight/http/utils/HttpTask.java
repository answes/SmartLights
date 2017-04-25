package com.bigshark.smartlight.http.utils;

import android.os.AsyncTask;

import com.bigshark.smartlight.http.IHttpCommand;
import com.bigshark.smartlight.http.IRequestParam;


/**
 * 异步任务执行网络请求类---公共类
 */
public class HttpTask extends AsyncTask<String, Void, String> {

	private String url;
	private IRequestParam requestParam;
	private HttpUtils.OnHttpResultListener onHttpResultListener;
	private IHttpCommand httpCommand;

	public HttpTask(String url, IRequestParam requestParam,
					IHttpCommand httpCommand, HttpUtils.OnHttpResultListener onHttpResultListener) {
		this.url = url;
		this.requestParam = requestParam;
		this.httpCommand = httpCommand;
		this.onHttpResultListener = onHttpResultListener;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			return httpCommand.execute(url,requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (this.onHttpResultListener != null) {
			this.onHttpResultListener.onResult(result);
		}
	}

}
