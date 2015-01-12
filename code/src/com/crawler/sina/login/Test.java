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
		// ����Ҫpost�Ĳ���
		NameValuePair[] nameValuePairs = { new NameValuePair("username", ""), new NameValuePair("password", "") };
		// ģ��POST������������Ҫģ���POST��ַ����У����Ϊ��http://www.renren.com/PLogin.do
		PostMethod postMethod = new PostMethod("");
		// �������ύ
		postMethod.setRequestBody(nameValuePairs);
		// ���巵��״ֵ̬
		int status = 0;
		// ִ��post
		status = client.executeMethod(postMethod);
		// ���ִ�в��ɹ�
		if (status != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + postMethod.getStatusLine());
		} else {
			System.out.println("ִ�гɹ�");
		}
		// ����Ƿ��ض���
		int statuscode = postMethod.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			// �õ�ͷ
			Header testHead = postMethod.getResponseHeader("location");
			// titleΪ�õ�ת��ĵ�ַ
			String title = testHead.getValue();
			System.err.println(title);
		}
	}
}
