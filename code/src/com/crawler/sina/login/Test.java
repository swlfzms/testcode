package com.crawler.sina.login;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Test {
	
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		// 输入要post的参数
		NameValuePair[] nameValuePairs = { new NameValuePair("username", ""), new NameValuePair("password", "") };
		// 模拟POST，引号内输入要模拟的POST地址，例校内则为：http://www.renren.com/PLogin.do
		PostMethod postMethod = new PostMethod("");
		// 将参数提交
		postMethod.setRequestBody(nameValuePairs);
		// 定义返回状态值
		int status = 0;
		// 执行post
		status = client.executeMethod(postMethod);
		// 如果执行不成功
		if (status != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + postMethod.getStatusLine());
		} else {
			System.out.println("执行成功");
		}
		// 检查是否重定向
		int statuscode = postMethod.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			// 得到头
			Header testHead = postMethod.getResponseHeader("location");
			// title为得到转向的地址
			String title = testHead.getValue();
			System.err.println(title);
		}
	}
}
