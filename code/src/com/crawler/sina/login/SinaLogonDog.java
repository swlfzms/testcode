package com.crawler.sina.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.sonatype.mavenbook.weather.Main;





/**
 * @author TonyJ
 * @Time 2013-7-1 ����02:08:49
 * 
 */
public class SinaLogonDog {
	
	private static final Log logger = LogFactory.getLog(SinaLogonDog.class);
	
	static String SINA_PK = "EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB2D24"
		+ "5A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C55FE2F08A49B353F444AD39"
		+ "93CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0E41B9B8F73A928EE0CCEE"
		+ "1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6442443";
	
	MultiThreadedHttpConnectionManager connectionManager =new MultiThreadedHttpConnectionManager();
	HttpClient client = new HttpClient(connectionManager);  
	
	public HttpClient logonAndValidate(String userName,String pwd) throws HttpException, IOException {
		
		String su = encodeAccount(userName);
		String initPageURL = "http://login.sina.com.cn/sso/prelogin.php?entry=sso&"
				+ "callback=sinaSSOController.preloginCallBack&su=" + su
				+ "&rsakt=mod&client=ssologin.js(v1.4.5)" + "&_=" + getServerTime();
		
		logger.error(initPageURL);
		
		GetMethod getMethod = newGetMethod(initPageURL);

		client.executeMethod(getMethod);
		InputStream is = getMethod.getResponseBodyAsStream();

		String response = inputStreamToString(is, null);

		String jsonBody = StringUtils.substringBetween(response, "(", ")");

		logger.error(jsonBody);
		
		String nonce = "";
		long servertime = 0L;
		String rsakv = "";
		nonce = StringUtils.substringBetween(jsonBody, "nonce\":\"", "\"");
		rsakv = StringUtils.substringBetween(jsonBody, "rsakv\":\"", "\"");
		servertime = Long.parseLong(StringUtils.substringBetween(jsonBody, "servertime\":", ","));

		getMethod.releaseConnection();

		String logonPageURL = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.5)";
		PostMethod postMethod2Logon = newPostMethod(logonPageURL);
		String pwdString = servertime + "\t" + nonce + "\n" + pwd;
		String sp = "";
		try {
			sp = new BigIntegerRSA().rsaCrypt(SINA_PK, "10001", pwdString);
		} catch (InvalidKeyException e) {
			logger.error("AES������Կ����128��", e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e);
		} catch (BadPaddingException e) {
			logger.error(e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		} catch (InvalidKeySpecException e) {
			logger.error(e);
		} catch (NoSuchPaddingException e) {
			logger.error(e);
		}
		// SinaSSOEncoder sso = new SinaSSOEncoder();
		// String sp = sso.encode(account.getPasswd(), servertime, nonce);
		logger.info("su=" + su);
		logger.info("servertime=" + servertime);
		logger.info("nonce=" + nonce);
		logger.info("sp=" + sp);
		logger.info("su=" + su + "&servertime=" + servertime + "&nonce=" + nonce + "&sp=" + sp);
		postMethod2Logon
				.setRequestBody(new NameValuePair[] {
						new NameValuePair("entry", "weibo"),
						new NameValuePair("gateway", "1"),
						new NameValuePair("from", ""),
						new NameValuePair("savestate", "7"),
						new NameValuePair("useticket", "1"),
						new NameValuePair("ssosimplelogin", "1"),
						new NameValuePair("useticket", "1"),
						new NameValuePair("vsnf", "1"),
						new NameValuePair("vsnval", ""),
						new NameValuePair("su", su),
						new NameValuePair("service", "miniblog"),
						new NameValuePair("servertime", servertime + ""),
						new NameValuePair("nonce", nonce),
						new NameValuePair("pwencode", "rsa2"),
						new NameValuePair("rsakv", rsakv),
						new NameValuePair("sp", sp),
						new NameValuePair("encoding", "UTF-8"),
						new NameValuePair("prelt", "115"),
						new NameValuePair("url",
								"http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"),
						new NameValuePair("returntype", "META") });
		int statusCode = client.executeMethod(postMethod2Logon);
		if (statusCode == HttpStatus.SC_OK) {
			logger.error(sp);
			System.out.println("ok");
		}
		postMethod2Logon.releaseConnection();

		logger.info(postMethod2Logon.getResponseHeaders());
		// ��֤�Ƿ��¼�ɹ�
		
		Cookie cookie = new Cookie("weibo.com", "wvr", "3.6", "/", new Date(2099, 12, 31), false);
		client.getState().addCookie(cookie);

		Cookie[] cookies = client.getState().getCookies();
		boolean containsSue = false;
		boolean containsSup = false;
		for (Cookie singleCookie : cookies) {
			String domain = singleCookie.getDomain();
			logger.info(domain);
			if (domain.equals(".sina.com.cn")) {
				singleCookie.setDomain("weibo.com");
			}
			logger.info(singleCookie);
			if (singleCookie.getName().equals("SUE")) {
				containsSue = true;
			}
			if (singleCookie.getName().equals("SUP")) {
				containsSup = true;
			}
		}
		return client;
	}

	
	public void init(){
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		client = new HttpClient(connectionManager);
		client.getParams().setCookiePolicy("compatibility");
		client.getParams().setParameter("http.protocol.single-cookie-header",
				Boolean.valueOf(true));
		client.getParams().setSoTimeout(20000);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				20000);
		
	}
	
	// inputstreamת��ΪString����
	public static String inputStreamToString(InputStream is, String charset) throws IOException {
		int i = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		if (null == charset) {
			return baos.toString();
		} else {
			return baos.toString(charset);
		}
	}

	protected PostMethod newPostMethod(String url) {
		PostMethod postMethod = new PostMethod(url);
		postMethod
				.setRequestHeader(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-ms-application, application/x-ms-xbap, application/vnd.ms-xpsdocument, application/xaml+xml, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		postMethod.setRequestHeader("Referer", "http://weibo.com/");
		postMethod.setRequestHeader("Accept-Language", "zh-cn");
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
		postMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; InfoPath.2)");
		postMethod.setRequestHeader("Connection", "Keep-Alive");
		postMethod.setRequestHeader("Cache-Control", "no-cache");
		return postMethod;
	}

	public static String decode(String string) {
		return string.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%3D", "=")
				.replaceAll("%26", "&");
	}

	// �û�������
	@SuppressWarnings("deprecation")
	private String encodeAccount(String account) {
		return (new sun.misc.BASE64Encoder()).encode(URLEncoder.encode(account).getBytes());
	}

	// ��λ�����nonce�Ĳ��������ã�
	public String makeNonce(int len) {
		String x = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str = "";
		for (int i = 0; i < len; i++) {
			str += x.charAt((int) (Math.ceil(Math.random() * 1000000) % x.length()));
		}
		return str;
	}

	// servertime�Ĳ��������ã�
	public String getServerTime() {
		long servertime = new Date().getTime() / 1000;
		return String.valueOf(servertime);
	}
	
	protected GetMethod newGetMethod(String url) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		getMethod.setRequestHeader("Accept-Language", "zh-CN");
		getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		getMethod.setRequestHeader("Accept-Encoding", "deflate");
		getMethod.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; CIBA; MAXTHON 2.0)");
		getMethod.setRequestHeader("Connection", "Keep-Alive");
		getMethod.setRequestHeader("Cache-Control", "no-cache");
		return getMethod;
	}
	
	public static void main(String[] args) throws HttpException, IOException{
		PropertyConfigurator.configure(SinaLogonDog.class.getClassLoader().getResource("resources/log4j.properties"));
		SinaLogonDog sinaLogonDog = new SinaLogonDog();
		sinaLogonDog.init();
		sinaLogonDog.logonAndValidate("", "");
	}
	
}

