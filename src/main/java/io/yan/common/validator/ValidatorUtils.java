package io.yan.common.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import io.yan.common.exception.RRException;



/**
 * hibernate-validator校验工具类
 */
public class ValidatorUtils {
	private static Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 * 
	 * @param object
	 *            待校验对象
	 * @param groups
	 *            待校验的组
	 * @throws CustomException
	 *             校验不通过，则报RRException异常
	 */
	public static void validateEntity(Object object, Class<?>... groups) throws RRException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<Object> constraint = constraintViolations.iterator()
					.next();
			throw new RRException(constraint.getMessage());
		}
	}

	public static boolean isNotNull(Object obj) {
		try {
			if (obj == null || obj.toString().equals("null") || obj.toString().equals("")) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return true;
		}
	}
}
