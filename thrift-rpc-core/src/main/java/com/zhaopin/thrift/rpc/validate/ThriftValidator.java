package com.zhaopin.thrift.rpc.validate;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import com.zhaopin.thrift.rpc.util.NumberUtil;

public class ThriftValidator extends AbstractValidator {

	private Validator propValidator;

	private ExecutableValidator validator;

	public ThriftValidator() {
		initValidate();
	}

	public void validate(Object target, Method method, Object[] params) {
		validateProperty(params);
		// 对请求参数的方法进行验证
		Set<ConstraintViolation<Object>> validateResult = null;
		validateResult = validator.validateParameters(target, method, params);
		if (validateResult != null && validateResult.size() > 0) {
			Iterator<ConstraintViolation<Object>> it = validateResult.iterator();
			if (it.hasNext()) {
				ConstraintViolation<Object> cv = it.next();
				final String msg = cv.getMessage();
				final String path = cv.getPropertyPath().toString();
				final int index = paramIndex(path);
				String message = "方法" + method.getName() + "第" + index + "参数:" + msg;
				LOGGER.error("验证没有通过{}", cv);
				throw new IllegalArgumentException(message);
			}
		}
	}

	private void initValidate() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		this.validator = vf.getValidator().forExecutables();
		this.propValidator = vf.getValidator();
	}

	private void validateProperty(Object[] parameterValues) {
		// 对参数进行Dto进行校验
		Set<ConstraintViolation<Object>> validateResult = null;
		for (int t = 0; t < parameterValues.length; ++t) {
			if (parameterValues[t] == null) {
				continue;
			}
			validateResult = propValidator.validate(parameterValues[t]);
			if (validateResult != null && validateResult.size() > 0) {
				Iterator<ConstraintViolation<Object>> it = validateResult.iterator();
				if (it.hasNext()) {
					ConstraintViolation<Object> cv = it.next();
					final String msg = cv.getMessage();
					final String beanName = cv.getRootBeanClass().getName();
					final String prop = cv.getPropertyPath().toString();
					final String message = beanName + "属性" + prop + " " + msg;
					LOGGER.error("验证没有通过{}", cv);
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	private int paramIndex(final String val) {
		final int index = val.lastIndexOf("arg");
		return NumberUtil.getInt(val.substring(index + "arg".length()), 0);
	}
}
