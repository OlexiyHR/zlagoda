package ua.training.controller.filter;

import org.apache.log4j.Logger;
import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.locale.Message;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represent filter responsible for controlling unauthorized access
 * to app resources
 * 
 * @author Solomka
 *
 */
@WebFilter(urlPatterns = { "/controller/manager/*", "/controller/waiter/*" })
public class UrlUnauthorizedAccessFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(UrlUnauthorizedAccessFilter.class);
	private static final String UNAUTHORIZED_ACCESS = "Unauthorized access to the resource: ";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		User user = SessionManager.getInstance().getUserFromSession(httpRequest.getSession());

		if (!isUserRegistered(user) || !isUserAuthorizedForResource(httpRequest.getRequestURI(), user)) {
			logInfoAboutUnauthorizedAccess(httpRequest.getRequestURI());
			HttpWrapper httpWrapper = new HttpWrapper(httpRequest, httpResponse);
			Map<String, String> urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.UNAUTHORIZED_ACCESS_ERROR);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.HOME, urlParams);
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}

	private boolean isUserRegistered(User user) {
		return user != null;

	}

	private boolean isUserAuthorizedForResource(String servletPath, User user) {
		return (isManagerPage(servletPath) && user.getRole().equals(Role.MANAGER))
				|| (isWaiterPage(servletPath) && user.getRole().equals(Role.CASHIER));
	}

	private boolean isManagerPage(String requestURI) {
		return requestURI.contains(Role.MANAGER.getValue());
	}

	private boolean isWaiterPage(String requestURI) {
		return requestURI.contains(Role.CASHIER.getValue());
	}

	private void logInfoAboutUnauthorizedAccess(String uri) {
		LOGGER.info(UNAUTHORIZED_ACCESS + uri);
	}
}
