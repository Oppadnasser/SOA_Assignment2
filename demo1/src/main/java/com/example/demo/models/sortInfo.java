package com.example.demo.models;

public class sortInfo {

    private String sortBy;
    private boolean desc;

    // Constructor
    public sortInfo() {
        sortBy = "";
        desc = false;
    }

    public sortInfo(String sortBy, boolean desc) {
        this.sortBy = sortBy;
        this.desc = desc;
    }

    // Getter and Setter for sortBy
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    // Getter and Setter for desc
    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SortInfo [sortBy=" + sortBy + ", desc=" + desc + "]";
    }
}

