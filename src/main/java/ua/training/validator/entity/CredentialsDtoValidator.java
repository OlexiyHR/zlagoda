package ua.training.validator.entity;

import ua.training.dto.CredentialsDto;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import java.util.ArrayList;
import java.util.List;

public final class CredentialsDtoValidator implements Validator<CredentialsDto> {

	private AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	CredentialsDtoValidator() {
	}

	private static class Holder {
		static final CredentialsDtoValidator INSTANCE = new CredentialsDtoValidator();
	}

	public static CredentialsDtoValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(CredentialsDto dto) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.EMAIL, dto.getEmail(), errors);
		fieldValidator.validateField(FieldValidatorKey.PASSWORD, dto.getPassword(), errors);

		return errors;
	}
}