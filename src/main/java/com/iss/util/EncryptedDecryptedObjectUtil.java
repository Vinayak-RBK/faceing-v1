package com.iss.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.iss.dao.SortByFieldsDao;

public class EncryptedDecryptedObjectUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncryptedDecryptedObjectUtil.class);

	static Class<?> clazz = null;

	public static void setFieldValue(Object obj, String fieldName, Object value, boolean isTypeString)
			throws Exception {
		clazz = obj.getClass();
		Field field = clazz.getDeclaredField(fieldName); // get the field by name
		field.setAccessible(true); // allow access to private/protected fields
		if (isTypeString) {
			field.set(obj, value); // set the value
		}
	}

	@SuppressWarnings("deprecation")
	public static Object getEncryptedObject(Object obj, String secretkey, String secretIv,
			boolean isEncryptDecryptEnable) throws Exception {

		logger.info("Started Encrypting the object {} by getEncryptedObject method", obj);
		logger.info("Encryption is enabled - {}", isEncryptDecryptEnable);
		if (!isEncryptDecryptEnable) {
			return obj;
		}

		Object enValue = null;
		clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		boolean isTypeString = false;

		for (Field field : fields) {
			String fieldName = field.getName();
			logger.info("Field name for object {} is {} - ", obj, fieldName);

			field.setAccessible(true);
			Object value = getFieldValue(obj, fieldName);

			logger.info("Value for Field name {} for object {} is {} - ", fieldName, obj, value);
			if (value == null || StringUtils.isEmpty(value)) {
				continue;
			}

			if (value instanceof String) {
				logger.info("Value {}  for Field name {} is of type String - ", value, fieldName);
//				byte[] strArray = ((String) value).getBytes(StandardCharsets.UTF_8);
				enValue = EncryptDecryptDataUtil.encrypt((String) value, secretkey, secretIv);
				logger.info("Encrypted Value for {} is - ", value, enValue);
				isTypeString = true;
			} else if (value instanceof Long) {
				logger.info("Value {}  for Field name {} is of type Long - ", value, fieldName);
				enValue = value;
			} else if (value instanceof String[]) {
				logger.info("Value {}  for Field name {} is of type String[] - ", value, fieldName);
				List<String> strList = new ArrayList<String>();
				String[] strarray = null;
				for (String str : (String[]) value) {
					enValue = EncryptDecryptDataUtil.encrypt((String) str, secretkey, secretIv);
					isTypeString = true;
					strList.add((String) enValue);
					strarray = strList.toArray(new String[0]);
				}
				enValue = strarray;
				logger.info("Encrypted Value for {} is - ", value, enValue);
			}
			else if (value instanceof SortByFieldsDao)
			{
				logger.info("Value {}  for Field name {} is of type Long - ", value, fieldName);
				enValue = value;
			}
			else {

				logger.info("Value{}  for Field name {} is of type User defined class - ", value, fieldName);
				clazz = value.getClass();
				Field[] objFields = clazz.getDeclaredFields();
				isTypeString = false;

				for (Field objField : objFields) {
					String objFieldName = objField.getName();
					logger.info("Field name for object {} is {} - ", obj, fieldName);

					field.setAccessible(true);
					Object objValue = getFieldValue(obj, objFieldName);
					logger.info("Value for Field name {} for object {} is {} - ", fieldName, obj, value);

					if (objValue == null || StringUtils.isEmpty(objValue)) {
						continue;
					}

					if (objValue instanceof String) {
						logger.info("Value {}  for Field name {} is of type String - ", value, fieldName);
//						byte[] strArray = ((String) value).getBytes(StandardCharsets.UTF_8);
						enValue = EncryptDecryptDataUtil.encrypt((String) objValue, secretkey, secretIv);
						logger.info("Encrypted Value for {} is - ", value, enValue);
						isTypeString = true;
					} else if (objValue instanceof String[]) {
						logger.info("Value {}  for Field name {} is of type String[] - ", value, fieldName);
						List<String> strList = new ArrayList<String>();
						String[] strarray = null;
						for (String str : (String[]) objValue) {
							enValue = EncryptDecryptDataUtil.encrypt((String) str, secretkey, secretIv);
							isTypeString = true;
							strList.add((String) enValue);
							strarray = strList.toArray(new String[0]);
						}
						enValue = strarray;
						logger.info("Encrypted Value for {} is - ", value, enValue);
					}

					enValue = objValue;
				}
			}

			setFieldValue(obj, fieldName, enValue, isTypeString);
		}
		logger.info("End Encrypting the object {} by getEncryptedObject method", obj);

		return obj;
	}

	public static Object getFieldValue(Object obj, String fieldName) throws Exception {
		Class<?> clazz = obj.getClass();
		Field field = clazz.getDeclaredField(fieldName); // Get field by name
		field.setAccessible(true); // Make the private field accessible
		return field.get(obj); // Get the value of the field
	}

	@SuppressWarnings("deprecation")
	public static Object getDecryptedObject(Object obj, String secretkey, String secretIv,
			boolean isEncryptDecryptEnable) throws Exception {

		logger.info("Started Decrypting the object {} by getDecryptedObject method", obj);
		logger.info("Decryption is enabled - {}", isEncryptDecryptEnable);
		if (!isEncryptDecryptEnable) {
			return obj;
		}

		Object enValue = null;
		clazz = obj.getClass();
		boolean isTypeString = false;
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			String fieldName = field.getName();
			logger.info("Field name for object {} is {} - ", obj, fieldName);

			field.setAccessible(true);
			Object value = getFieldValue(obj, fieldName);
			logger.info("Value for Field name {} for object {} is {} - ", fieldName, obj, value);

			if (value == null || StringUtils.isEmpty(value)) {
				continue;
			}
			if (value instanceof String) {
				logger.info("Value {}  for Field name {} is of type String", value, fieldName);
				enValue = EncryptDecryptDataUtil.decrypt((String) value, secretkey, secretIv);

				logger.info("Decrypted Value for {} is - ", value, enValue);
				isTypeString = true;
			} else if (value instanceof String[]) {
				logger.info("Value {}  for Field name {} is of type String[] - ", value, fieldName);
				List<String> strList = new ArrayList<String>();
				String[] strarray = null;
				for (String str : (String[]) value) {
					enValue = EncryptDecryptDataUtil.decrypt((String) str, secretkey, secretIv);
					isTypeString = true;
					strList.add((String) enValue);
					strarray = strList.toArray(new String[0]);
				}
				enValue = strarray;
			} else {
				enValue = value;
			}
			logger.info("Decrypted Value for {} is - ", value, enValue);

			setFieldValue(obj, fieldName, enValue, isTypeString);
		}
		logger.info("End Decrypting the object {} by getDecryptedObject method", obj);
		return obj;
	}

	@SuppressWarnings("deprecation")
	public static Object getEncryptedString(Object obj, String secretkey, String secretIv,
			boolean isEncryptDecryptEnable) throws Exception {
		Object enValue = null;

		logger.info("Started Encrypting the object {} by getEncryptedString method", obj);
		logger.info("Encryption is enabled - {}", isEncryptDecryptEnable);

		if (!isEncryptDecryptEnable) {
			return obj;
		}

		if (obj == null || StringUtils.isEmpty(obj)) {
			return "";
		}

		if (obj instanceof String) {
			logger.info("Value is {}", obj);
//			byte[] strArray = ((String) obj).getBytes(StandardCharsets.UTF_8);
			enValue = EncryptDecryptDataUtil.encrypt((String) obj, secretkey, secretIv);
			logger.info("Encrypted Value for {} is - ", obj, enValue);
		} else if (obj instanceof Integer) {
//			byte[] ontArray = ByteBuffer.allocate(4).putInt((int) obj).array();
			enValue = EncryptDecryptDataUtil.encrypt((String) obj, secretkey, secretIv);
		} else if (obj instanceof Double) {
//			byte[] ontArray = ByteBuffer.allocate(4).putDouble((double) obj).array();
			enValue = EncryptDecryptDataUtil.encrypt((String) obj, secretkey, secretIv);
			logger.info("Encrypted Value for {} is - ", obj, enValue);
		} else if (obj instanceof LinkedHashMap) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			Map<String, Object> enMap = new LinkedHashMap<String, Object>();

			for (Map.Entry<?, ?> entry : map.entrySet()) {
				Object key = entry.getKey();
				Object val = entry.getValue();
				if (val instanceof String) {
					String value = (String) EncryptDecryptDataUtil.encrypt((String) val, secretkey, secretIv);
					enMap.put((String) key, value);
				} else {
					Object enObj = getEncryptedObject(val, secretkey, secretIv, isEncryptDecryptEnable);
					enValue = enObj;
					enMap.put((String) key, enObj);
				}

			}
			enValue = enMap;
			logger.info("Encrypted Value for {} is - ", obj, enValue);
		} else if (obj instanceof String[]) {
			List<String> strList = new ArrayList<String>();
			String[] strarray = null;
			for (String str : (String[]) obj) {
				enValue = EncryptDecryptDataUtil.encrypt((String) str, secretkey, secretIv);
				strList.add((String) enValue);
				strarray = strList.toArray(new String[0]);
			}
			enValue = strarray;
			logger.info("Encrypted Value for {} is - ", obj, enValue);
		} else if (obj instanceof Wrapper) {
			Object enObj = getEncryptedObject(obj, secretkey, secretIv, isEncryptDecryptEnable);
			enValue = enObj;
			logger.info("Encrypted Value for {} is - ", obj, enValue);
		}

		logger.info("End Encrypting the object {} by getEncryptedString method", obj);
		return enValue;
	}

	@SuppressWarnings("deprecation")
	public static Object getDecryptedString(Object obj, String secretkey, String secretIv,
			boolean isEncryptDecryptEnable) throws Exception {

		logger.info("Started Encrypting the object {} by getDecryptedString method", obj);
		logger.info("Decryption is enabled - {}", isEncryptDecryptEnable);
		if (!isEncryptDecryptEnable) {
			return obj;
		}

		Object enValue = null;

		if (obj == null || StringUtils.isEmpty(obj)) {
			return "";
		}

		if (obj instanceof String) {
			logger.info("Value is {}", obj);
			enValue = EncryptDecryptDataUtil.decrypt(obj.toString(), secretkey, secretIv);
			logger.info("Decrypted Value for {} is - ", obj, enValue);
		} else if (obj instanceof Integer) {
			enValue = obj;
		} else if (obj instanceof String[]) {
			logger.info("Value is {}", obj);
			List<String> strList = new ArrayList<String>();
			String[] strarray = null;
			for (String str : (String[]) obj) {
				enValue = EncryptDecryptDataUtil.decrypt((String) str, secretkey, secretIv);
				strList.add((String) enValue);
				strarray = strList.toArray(new String[0]);
			}
			enValue = strarray;
			logger.info("Decrypted Value for {} is - ", obj, enValue);
		}
		logger.info("End Decrypting the object {} by getDecryptedString method", obj);

		return enValue;
	}

}
