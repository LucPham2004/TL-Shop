package com.e_shop.Shoe_Shop.Entity.admin.dto;

public class CustomerSummary {
	private long totalCustomers;
	private long enabledCustomersCount;
	private long disabledCustomersCount;
	private long lockedCustomersCount;
	private long nonLockedCustomersCount;
    
    public long getEnabledCustomersCount() {
        return enabledCustomersCount;
    }

    public void setEnabledCustomersCount(long enabledCustomersCount) {
        this.enabledCustomersCount = enabledCustomersCount;
    }

    public long getDisabledCustomersCount() {
        return disabledCustomersCount;
    }

    public void setDisabledCustomersCount(long disabledCustomersCount) {
        this.disabledCustomersCount = disabledCustomersCount;
    }

    public long getLockedCustomersCount() {
        return lockedCustomersCount;
    }

    public void setLockedCustomersCount(long lockedCustomersCount) {
        this.lockedCustomersCount = lockedCustomersCount;
    }

    public long getNonLockedCustomersCount() {
        return nonLockedCustomersCount;
    }

    public void setNonLockedCustomersCount(long nonLockedCustomersCount) {
        this.nonLockedCustomersCount = nonLockedCustomersCount;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }
}
