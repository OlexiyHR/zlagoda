package ua.training.controller.command.i18n;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.locale.MessageLocale;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setLocale(request);
		return Page.HOME_VIEW;
	}

	private void setLocale(HttpServletRequest request) {
		String selectedLanguage = request.getParameter(Attribute.LANG);
		Locale chosenLocale = AppLocale.forValue(selectedLanguage);
		
		request.getSession().setAttribute(Attribute.LOCALE, chosenLocale);
		MessageLocale.setResourceBundleLocale(chosenLocale);
	}
}
