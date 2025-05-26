package com.iss.dao;

public class DashboardDataDao {

	private String totalUserCount;
	private String totalGuestCount;
	private String totalAnuraScanCount;
	private String totalBinahScanCount;
	private String msg;
	private String success;

	public DashboardDataDao() {
		super();
	}

	public DashboardDataDao(String totalUserCount, String totalGuestCount, String totalAnuraScanCount,
			String totalBinahScanCount, String msg, String success) {
		super();
		this.totalUserCount = totalUserCount;
		this.totalGuestCount = totalGuestCount;
		this.totalAnuraScanCount = totalAnuraScanCount;
		this.totalBinahScanCount = totalBinahScanCount;
		this.msg = msg;
		this.success = success;
	}

	public String getTotalUserCount() {
		return totalUserCount;
	}

	public void setTotalUserCount(String totalUserCount) {
		this.totalUserCount = totalUserCount;
	}

	public String getTotalGuestCount() {
		return totalGuestCount;
	}

	public void setTotalGuestCount(String totalGuestCount) {
		this.totalGuestCount = totalGuestCount;
	}

	public String getTotalAnuraScanCount() {
		return totalAnuraScanCount;
	}

	public void setTotalAnuraScanCount(String totalAnuraScanCount) {
		this.totalAnuraScanCount = totalAnuraScanCount;
	}

	public String getTotalBinahScanCount() {
		return totalBinahScanCount;
	}

	public void setTotalBinahScanCount(String totalBinahScanCount) {
		this.totalBinahScanCount = totalBinahScanCount;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "DashboardDataDao [totalUserCount=" + totalUserCount + ", totalGuestCount=" + totalGuestCount
				+ ", totalAnuraScanCount=" + totalAnuraScanCount + ", totalBinahScanCount=" + totalBinahScanCount
				+ ", msg=" + msg + ", success=" + success + "]";
	}
}
