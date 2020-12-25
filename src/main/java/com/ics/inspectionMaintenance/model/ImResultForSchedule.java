package com.ics.inspectionMaintenance.model;

import java.util.Date;

public class ImResultForSchedule {
	private int noChackCount;
	private int successCount;
	private int failureCount;
	private boolean hasSchedule;

	public int getNoChackCount() {
		return noChackCount;
	}

	public void setNoChackCount(int noChackCount) {
		this.noChackCount = noChackCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public boolean isHasSchedule() {
		return hasSchedule;
	}

	public void setHasSchedule(boolean hasSchedule) {
		this.hasSchedule = hasSchedule;
	}
}
