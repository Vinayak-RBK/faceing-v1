package com.iss.dao;

public class SortByFieldsDao {
	private String sortBy;
	private String sortType;

	public SortByFieldsDao() {
		super();
	}

	public SortByFieldsDao(String sortBy, String sortType) {
		super();
		this.sortBy = sortBy;
		this.sortType = sortType;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	@Override
	public String toString() {
		return "SortByFieldsDao [sortBy=" + sortBy + ", sortType=" + sortType + "]";
	}

}
