package com.iss.test;

public class TestDao {
	
	private String name ;
	private int age;
	
	public TestDao() {
		super();
	}

	public TestDao(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestDao [name=" + name + ", age=" + age + "]";
	}
	
}
