package com.iss.dao;

public class PagerRequestDao {

	private String pageNo;
	private String pageSize;
	private String searchBy;
	private SortByFieldsDao sort;
	private String userId;

	public PagerRequestDao() {
		super();
	}

	public PagerRequestDao(String pageNo, String pageSize, String searchBy, SortByFieldsDao sort, String userId) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.searchBy = searchBy;
		this.sort = sort;
		this.userId = userId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public SortByFieldsDao getSort() {
		return sort;
	}

	public void setSort(SortByFieldsDao sort) {
		this.sort = sort;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PagerRequestDao [pageNo=" + pageNo + ", pageSize=" + pageSize + ", searchBy=" + searchBy + ", sort="
				+ sort + ", userId=" + userId + "]";
	}

}
