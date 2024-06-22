package ua.training.locale;

public final class Message {	
	
	private Message() {
	}


	public static final String ROLE_MANAGER = "supermarket.user.role.manager";

	public static final String ROLE_CASHIER = "supermarket.user.role.cashier";

	public static final String STATUS_NEW = "supermarket.order.status.new";
	public static final String STATUS_IN_PROGRESS = "supermarket.order.status.inProgress";
	public static final String STATUS_PREPARED = "supermarket.order.status.prepared";
	public static final String STATUS_PAID = "supermarket.order.status.paid";
	
	public static final String LOGGED_IN_AS = "supermarket.loggedIn";
	public static final String PAGE_NOT_FOUND_ERROR = "supermarket.error.pageNotFoundError";
	public static final String SERVER_ERROR = "supermarket.error.serverError";
	public static final String DIRECT_VIEW_ACCESS_ERROR = "supermarket.error.directViewAccessError";
	public static final String UNAUTHORIZED_ACCESS_ERROR = "supermarket.error.authorizedAccessError";
	
	public static final String USER_IS_NOT_FOUND = "supermarket.user.isNotFound";
	public static final String CATEGORY_IS_NOT_FOUND = "supermarket.category.isNotFound";
	public static final String DISH_IS_NOT_FOUND = "supermarket.dish.isNotFound";
	public static final String ORDER_IS_NOT_FOUND = "supermarket.order.isNotFound";
		
	public static final String INVALID_NAME = "supermarket.error.invalidName";
	public static final String INVALID_SURNAME = "supermarket.error.invalidSurname";
	public static final String INVALID_EMAIL = "supermarket.error.invalidEmail";
	public static final String INVALID_PASS = "supermarket.error.invalidPass";
	public static final String INVALID_CREDENTIALS = "supermarket.error.invalidCredentials";
	public static final String INVALID_PHONE = "supermarket.error.invalidPhone";
	public static final String INVALID_ADDRESS = "supermarket.error.invalidAddress";
	public static final String INVALID_ROLE = "supermarket.error.invalidRole";
	public static final String INVALID_DATE = "supermarket.error.invalidDate";
	public static final String INVALID_NEW_PASSWORD = "supermarket.error.invalidNewPassword";
	public static final String INVALID_CONFIRM_PASSWORD = "supermarket.error.invalidConfirmPassword";
	public static final String INVALID_NEW_CONFIRM_PASSWORD = "supermarket.error.invalidNewConfirmPassword";
	public static final String INVALID_CATEGORY = "supermarket.error.invalidCategory";
	public static final String INVALID_DESCRIPTION = "supermarket.error.invalidDescription";
	public static final String INVALID_WEIGHT = "supermarket.error.invalidWeight";
	public static final String INVALID_COST = "supermarket.error.invalidCost";
	public static final String INVALID_DISHES = "supermarket.error.invalidDishes";
	
	public static final String SUCCESS_USER_ADDITION = "supermarket.success.userSuccessfullyAdded";
	public static final String SUCCESS_USER_UPDATE = "supermarket.success.userSuccessfullyUpdated";
	public static final String SUCCESS_USER_DELETE = "supermarket.success.userSuccessfullyDeleted";
	public static final String SUCCESS_CATEGORY_ADDITION = "supermarket.success.categorySuccessfullyAdded";
	public static final String SUCCESS_CATEGORY_UPDATE = "supermarket.success.categorySuccessfullyUpdated";
	public static final String SUCCESS_CATEGORY_DELETE = "supermarket.success.categorySuccessfullyDeleted";
	public static final String SUCCESS_DISH_DELETE = "supermarket.success.dishSuccessfullyDeleted";
	public static final String SUCCESS_DISH_ADDITION = "supermarket.success.dishSuccessfullyAdded";
	public static final String SUCCESS_DISH_UPDATE = "supermarket.success.dishSuccessfullyUpdated";
	public static final String SUCCESS_ORDER_ADDITION = "supermarket.success.orderSuccessfullyAdded";
	public static final String SUCCESS_ORDER_UPDATE = "supermarket.success.orderSuccessfullyUpdated";
	public static final String SUCCESS_ORDER_DELETE = "supermarket.success.orderSuccessfullyDeleted";
}
