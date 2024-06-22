package ua.training.controller.command.user;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.validator.entity.UserDtoValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAddUserCommand implements Command {

	private final UserService userService;

	public PostAddUserCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {

		UserDto userDto = getUserInput(request);
		List<String> errors = validateUserInput(userDto);

		if (errors.isEmpty()) {
			userService.createUser(userDto);
			redirectToAllUsersPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequestAttributes(request, userDto, errors);
		return Page.ADD_UPDATE_USER_VIEW;
	}

	private UserDto getUserInput(HttpServletRequest request) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return new UserDto.Builder().setName(request.getParameter(Attribute.NAME))
				.setSurname(request.getParameter(Attribute.SURNAME))
				.setPathronymic(request.getParameter(Attribute.PATHRONYMIC))
				.setPhone(request.getParameter(Attribute.PHONE))
				.setSalary(new BigDecimal(request.getParameter(Attribute.SALARY)))
				.setBirth(new java.sql.Date(dateFormat.parse(request.getParameter(Attribute.BIRTH)).getTime()))
		    	.setStart(new java.sql.Date(dateFormat.parse(request.getParameter(Attribute.START)).getTime()))
				.setPhone(request.getParameter(Attribute.CITY))
				.setPhone(request.getParameter(Attribute.STREET))
				.setPhone(request.getParameter(Attribute.CODE))
				.setEmail(request.getParameter(Attribute.EMAIL))
				.setRole(Role.forValue(request.getParameter(Attribute.ROLE)))
				.setPassword(request.getParameter(Attribute.PASSWORD))
				.build();
	}

	private List<String> validateUserInput(UserDto user) {
		return UserDtoValidator.getInstance().validate(user);
	}

	private void redirectToAllUsersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_USER_ADDITION);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_USERS, urlParams);
	}

	private void addRequestAttributes(HttpServletRequest request, UserDto userDto, List<String> errors) {
		request.setAttribute(Attribute.ROLES, Role.values());
		request.setAttribute(Attribute.USER_DTO, userDto);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
