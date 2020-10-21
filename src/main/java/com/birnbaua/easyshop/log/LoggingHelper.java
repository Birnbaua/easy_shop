package com.birnbaua.easyshop.log;

import org.apache.commons.logging.Log;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public class LoggingHelper {
	
	public static void logAuthenticatedUser(Log log, HttpServletRequest request) {
		log.info("Authenticated user with username: " + request.getUserPrincipal().getName());
	}
	
	public static void logStackTrace(Log log, Exception e) {
		Arrays.stream(e.getStackTrace()).map(x -> x.toString()).forEach(x -> log.error(x));
	}
}
