package com.iss.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class EncryptionDecryptionOfBooleanValues {

	public static void main(String[] args) {
		boolean isTrue = true;
		System.out.println("1).Converting boolean into byte array and byte array into boolean...");
		System.out.println("Original value of boolean is : " + isTrue);
		byte[] byteArray = booleanToBytes(isTrue);
		System.out.println("Byte Array of boolean is : " + byteArray);

		boolean conValue = bytesToBoolean(byteArray);

		System.out.println("Converted byte[] into boolean value is : " + conValue);
		System.out.println();

		System.out.println("2).Converting int into byte array and byte array into int...");

		int no = 156;
		System.out.println("Original value of int is : " + no);

		byte[] noArr = intToBytes(no);
		System.out.println("Byte Array of int is : " + noArr);

		int conIntValue = bytesToInt(noArr);
		System.out.println("Converted byte[] into int value is : " + conIntValue);
		System.out.println();

		System.out.println("3).Converting Bigdecimal into byte array and byte array into Bigdecimal...");

		BigDecimal decValue = new BigDecimal(1892.3788);
		System.out.println("Original value of BigDecimal is : " + decValue);

		byte[] conDecToByteArr = bigDecimalToBytes(decValue);

		System.out.println("Byte Array of BigDecimal is : " + conDecToByteArr);

		BigDecimal conDecValue = bytesToBigDecimal(conDecToByteArr);

		System.out.println("Converted byte[] into BigDecimal value is : " + conDecValue);
		System.out.println();

		System.out.println("4).Converting String into byte array and byte array into String...");

		String text = "Hello, world!";
		System.out.println("Original value of String is : " + text);

		byte[] strByteArr = text.getBytes(StandardCharsets.UTF_8);

		System.out.println("Byte Array of String is : " + strByteArr);

		String conStrValue = new String(strByteArr, StandardCharsets.UTF_8);

		System.out.println("Converted byte[] into String value is : " + conStrValue);
	}

	public static byte[] booleanToBytes(boolean value) {
		return new byte[] { (byte) (value ? 1 : 0) };
	}

	public static boolean bytesToBoolean(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("Byte array is null or empty");
		}
		return bytes[0] != 0;
	}

	public static byte[] intToBytes(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}

	public static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static byte[] bigDecimalToBytes(BigDecimal value) {
		return value.toString().getBytes(StandardCharsets.UTF_8);
	}

	public static BigDecimal bytesToBigDecimal(byte[] bytes) {

		BigDecimal limited = new BigDecimal(new String(bytes, StandardCharsets.UTF_8)).setScale(4, RoundingMode.DOWN);
		return limited;
	}
}
