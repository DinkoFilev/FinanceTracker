package com.budgetbeat.pojo;

public  class KeyValue {
    String key;
    String value;
    String value2;

    public KeyValue(String key, String value,String value2) {
        super();
        this.key = key;
        this.value = value;
        this.value2 = value;
    }

    public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
