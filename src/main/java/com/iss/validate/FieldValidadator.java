package com.iss.validate;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.iss.dao.AdminUserDao;
import com.iss.dao.EmployeeDao;
import com.iss.dao.GuestDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PersonalDetailsDao;
import com.iss.dao.UserDao;
import com.iss.properties.FieldNames;
import com.iss.properties.ValidationMessages;

public class FieldValidadator {

	static Class<?> clazz = null;

	@SuppressWarnings("deprecation")
	public static Map<String, String> validateFields(Object obj) throws Exception {
		Map<String, String> valMap = new LinkedHashMap<String, String>();
		clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		if (obj instanceof UserDao) {
			for (Field field : fields) {

				String fieldName = field.getName();
				Object value = getFieldValue(obj, fieldName);

				if (FieldNames.EMAIL_ID.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.EMAIL_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.PASSWORD.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.PASSWORD_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.SDK_TYPE.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.SDK_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				}

			}
		} else if (obj instanceof GuestDao) {
			for (Field field : fields) {

				String fieldName = field.getName();
				Object value = getFieldValue(obj, fieldName);

				if (FieldNames.NAME.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.NAME_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.GENDER.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.GENDER_NOT_SELECTED);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.DOB.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.DOB_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.WEIGHT.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.WEIGHT_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.HEIGHT.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.HEIGHT_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				}
			}
		} else if (obj instanceof PersonalDetailsDao) {
			for (Field field : fields) {

				String fieldName = field.getName();
				Object value = getFieldValue(obj, fieldName);

				if (FieldNames.USER_NAME.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.NAME_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.USER_GENDER.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.GENDER_NOT_SELECTED);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.USER_DOB.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.DOB_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.USER_WEIGHT.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.USER_WEIGHT);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.USER_HEIGHT.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.USER_HEIGHT);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				}
			}
		} else if (obj instanceof AdminUserDao) {
			for (Field field : fields) {

				String fieldName = field.getName();
				Object value = getFieldValue(obj, fieldName);

				if (FieldNames.EMAIL.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.EMAIL_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.PASSWORD.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.PASSWORD_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				}

			}

		} else if (obj instanceof EmployeeDao) {
			for (Field field : fields) {

				String fieldName = field.getName();
				Object value = getFieldValue(obj, fieldName);

				if (FieldNames.NAME.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.NAME_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.JOB_ROLE.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.JOB_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.EMAIL.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.EMAIL_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.PASSWORD.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.PASSWORD_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.MOB_NO.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.MOBILENO_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				}

			}

		} else if (obj instanceof PagerRequestDao) {
			for (Field field : fields) {

				String fieldName = field.getName();
				Object value = getFieldValue(obj, fieldName);

				if (FieldNames.PAGE_NO.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.START_INDEX_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.PAGE_SIZE.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.END_INDEX_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				} else if (FieldNames.SIZE.equals(fieldName)) {
					if (StringUtils.isEmpty(value)) {
						valMap.put("msg", ValidationMessages.SIZE_EMPTY);
						valMap.put("success", Boolean.toString(false));
						return valMap;
					}

				}

			}

		}

		valMap.put("success", Boolean.toString(true));

		return valMap;
	}

	public static Object getFieldValue(Object obj, String fieldName) throws Exception {
		Class<?> clazz = obj.getClass();
		Field field = clazz.getDeclaredField(fieldName); // Get field by name
		field.setAccessible(true); // Make the private field accessible
		return field.get(obj); // Get the value of the field
	}

}
