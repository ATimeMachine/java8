package com.chen.java8.example.httpUtils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 *
	 * 发送 post（json）
	 *          请求访问
	 * @param jsonData
	 *         json数据
	 * @param url
	 *         	商城URL
	 * @return 请求返回数据
	 */
	public static String postJson(String jsonData,String url){
		String result = null;
		HttpEntity respEntity = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpEntity reqEntity = null;
			if (jsonData != null) {
				reqEntity = new ByteArrayEntity(jsonData.getBytes("UTF-8"), ContentType.APPLICATION_JSON);
			}
			httpclient = HttpClients.createDefault();

			if(!"".equals(url)){
				HttpPost httppost = createHttpPost(url);
				if (reqEntity != null) {
					httppost.setEntity(reqEntity);
				}
				response = httpclient.execute(httppost);
				respEntity = response.getEntity();
				if (respEntity != null) {
					result = EntityUtils.toString(respEntity, "UTF-8");
				}
				if (200 != response.getStatusLine().getStatusCode()) {// 失败
					logger.error("Receive http response:" + response.getStatusLine().getStatusCode() + result);
					return null;
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				EntityUtils.consume(respEntity);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			close(response, httpclient);
		}
		return result;
	}

	
	public static String post(String url, Map<String, Object> parameters) {
		String result = null;
		HttpEntity respEntity = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			logger.info("post" + url);
			httpclient = HttpClients.createDefault();
			HttpPost httppost = createHttpPost(url);
			if (parameters != null && !parameters.isEmpty()) {
				List<BasicNameValuePair> nvps = new ArrayList<>();
				for (Entry<String, Object> entry : parameters.entrySet()) {
					if (null != entry.getValue()) {
						if (entry.getValue() instanceof List) {
							nvps.add(new BasicNameValuePair(entry.getKey(), JSON.toJSONString(entry.getValue())));
						} else {
							nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
						}

					}
				}
				httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			
			}
			response = httpclient.execute(httppost);
			respEntity = response.getEntity();
			if (respEntity != null) {
				result = EntityUtils.toString(respEntity, "UTF-8");
			}
			if (200 != response.getStatusLine().getStatusCode()) {// 失败
				logger.error("Receive http response:" + response.getStatusLine().getStatusCode() + result);
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				EntityUtils.consume(respEntity);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			close(response, httpclient);
		}
		return result;
	}
	
	/**
	 * 创建请求
	 * 
	 * @param url
	 * @return
	 */
	public static HttpPost createHttpPost(String url) {
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("Content-type","application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(300000)
				.setConnectTimeout(300000).setConnectionRequestTimeout(300000).build();
		httppost.setConfig(defaultRequestConfig);
		return httppost;
	}
	
	
	/**
	 * 关闭http请求及返回
	 * 
	 * @param response
	 * @param httpclient
	 */
	public static void close(CloseableHttpResponse response, CloseableHttpClient httpclient) {
		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		if (httpclient != null) {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 发送get 请求访问
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String get(String url) {
		String result = null;
		HttpEntity respEntity = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			logger.info("get" + url);
			httpclient = HttpClients.createDefault();
			HttpGet httpGet=createHttpGet(url);
			response = httpclient.execute(httpGet);
			respEntity = response.getEntity();
			if (respEntity != null) {
				result = EntityUtils.toString(respEntity, "UTF-8");
			}
			if (200 != response.getStatusLine().getStatusCode()) {// 失败
				logger.error("Receive http response:" + response.getStatusLine().getStatusCode() + result);
				return null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			//throw ErrorUtils.wrap(GenericError.REQUEST_ERROR, e);
		} finally {
			try {
				EntityUtils.consume(respEntity);
			} catch (IOException e) {
				logger.error("EntityUtils close exception!");
			}
			close(response, httpclient);
		}
		return result;
	}
	/**
	 * 发送get 请求访问  带sessionId
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url,String sessionId) {
		String result = null;
		HttpEntity respEntity = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			logger.info("httpGet" + url);
			httpclient = HttpClients.createDefault();
			HttpGet httpGet=createHttpGet(url);
			httpGet.addHeader("sessionId",sessionId);
			response = httpclient.execute(httpGet);
			respEntity = response.getEntity();
			if (respEntity != null) {
				result = EntityUtils.toString(respEntity, "UTF-8");
			}
			if (200 != response.getStatusLine().getStatusCode()) {// 失败
				logger.error("Receive http response:" + response.getStatusLine().getStatusCode() + result);
				return null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				EntityUtils.consume(respEntity);
			} catch (IOException e) {
				logger.error("EntityUtils close exception!");
			}
			close(response, httpclient);
		}
		return result;
	}
	/**
	 * 创建GET请求
	 * 
	 * @param url
	 * @return
	 */
	private static HttpGet createHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
		httpGet.addHeader("Connection", "Keep-Alive");  
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");  
		httpGet.addHeader("Cookie", "");  
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(300000)
				.setConnectTimeout(300000).setConnectionRequestTimeout(300000).build();
		httpGet.setConfig(defaultRequestConfig);
		return httpGet;
	}
	
	
	/**
	 * httpget 带参数 
	 * @param url url
	 * @param params 参数
	 * @return null 表示没有数据，不是null,表示有数据
	 * @throws IOException
	 */
	public static String httpGet(String url,Map<String, Object> params) throws IOException {
		String result = null;
		HttpEntity respEntity = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		
        try {
        	if (params != null && !params.isEmpty()) {
        		List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
        		for (Entry<String, Object> entry : params.entrySet()) {
        			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        		}
        		url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));
        	}
        	HttpGet httpGet = new HttpGet(url);
			RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(300000)
					.setConnectTimeout(300000).setConnectionRequestTimeout(300000).build();
			httpGet.setConfig(defaultRequestConfig);
			logger.info("httpGet" + url);
        	httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			respEntity = response.getEntity();
			if (respEntity != null) {
				result = EntityUtils.toString(respEntity, "UTF-8");
			}
			// 获取响应信息
			if (200 != response.getStatusLine().getStatusCode()) {// 失败
				logger.error("Receive http response:" + response.getStatusLine().getStatusCode() + result);
				return null;
			}
		} finally {
			try {
				EntityUtils.consume(respEntity);
			} catch (IOException e) {
				logger.error("EntityUtils close exception!");
			}
			close(response, httpClient);
		}
        return result;
	}

}
