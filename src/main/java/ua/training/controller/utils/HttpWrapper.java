package ua.training.controller.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class HttpWrapper {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public HttpWrapper() {
	}

	public HttpWrapper(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
