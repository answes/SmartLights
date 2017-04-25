package com.bigshark.smartlight.http;

/**
 * 执行网络请求命令接口
 * @param <T>
 */
public interface IHttpCommand<T> {
	public String execute(String url, IRequestParam<T> requestParam);
}
