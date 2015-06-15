package com.zms.request;

import java.io.PrintWriter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zms.mycompany.HomeController;


@Controller
public class WebRequest {

	private static final Logger logger = LoggerFactory.getLogger(WebRequest.class);
	private static final Logger errorLogger = LoggerFactory.getLogger(WebRequest.class);
	
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public void process(Locale locale, PrintWriter printWriter){
		printWriter.println("welcome back");
		printWriter.flush();
	}
}
