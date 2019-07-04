package com.chen.java8.example.httpUtils;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class M2M {

	private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	private static final String COOKIE = "POD18-pgw=2.56.e9.74483271.1f90; JSESSIONID=58F46AFC1A7414F9A9EB67A0F02A88C7; jsSessionCookie=aGV0ZWxpOjQwMjMyMzY6MTU2MjI1MjkzMjExOTpVM0hiR1QvWGdnb2c2TG4yOjpmYWxzZTo6OjExMy4xMTYuMTMyLjY4OnBvZDE4";
	
	public static void main(String[] args) {
		try {
			search(11);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void search(int page) throws Exception {
		System.out.println("正在执行第"+page+"页");
		SSLContext ctx = SSLContexts.custom().useProtocol("TLSv1.2").build();
		CloseableHttpClient httpclient = HttpClientBuilder.create().setSslcontext(ctx).build();
		HttpGet httpGet = new HttpGet("https://cc3.10646.cn/provision/api/v1/sims?_dc="+System.currentTimeMillis()+"&page="+page+"&limit=50&sort=dateAdded&dir=DESC&search=%5B%7B%22property%22%3A%22status%22%2C%22type%22%3A%22LONG_EQUALS%22%2C%22value%22%3A6%2C%22id%22%3A%22status%22%7D%5D");
		httpGet.addHeader("Cookie", COOKIE);
		httpGet.setHeader("referer", "https://cc3.10646.cn/provision/ui/terminals/sims/sims.html");
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		httpGet.setConfig(requestConfig);
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity, "UTF-8");
		JSONObject json = JSONObject.parseObject(result);
		Iterator<Object> it = json.getJSONArray("data").iterator();
		while(it.hasNext()) {
			final JSONObject item = (JSONObject) it.next();
			if (!item.getBooleanValue("inSession")) {
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						send(item.getLongValue("simId"), item.getString("iccid"));
					}
				});
			}
			//System.out.println(item.getLongValue("simId") + "，" + item.getString("iccid") + "，在线：" + (item.getBooleanValue("inSession") ? "是" : "否"));
		}
		search(page+1);
	}
	
	private static void send(long id, String iccid) {
		try {
			SSLContext ctx = SSLContexts.custom().useProtocol("TLSv1.2").build();
			CloseableHttpClient httpclient = HttpClientBuilder.create().setSslcontext(ctx).build();
			HttpGet httpGet = new HttpGet("https://cc3.10646.cn/provision/api/v1/simDiagnostic/cancelLocation/"+id+"?_dc="+System.currentTimeMillis());
			httpGet.addHeader("Cookie", COOKIE);
			httpGet.setHeader("referer", "https://cc3.10646.cn/provision/ui/terminals/sims/sims.html");
			httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");
			System.out.println("SIMID："+id+"，ICCID："+iccid+"，取消位置发送响应：" + result);
		} catch (Exception e) {
			System.out.println("ID："+id+"，取消位置发送异常：" + e.getMessage());
		}
	}
}
