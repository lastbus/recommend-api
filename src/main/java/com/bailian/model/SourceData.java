package com.bailian.model;

public class SourceData {
    private String souceCode;

    private String sourceMeans;

    private String keyPrefix;

    private String inputParameter;
    
    

    @Override
	public String toString() {
		return "SourceData [souceCode=" + souceCode + ", sourceMeans="
				+ sourceMeans + ", keyPrefix=" + keyPrefix
				+ ", inputParameter=" + inputParameter + "]";
	}

	public String getSouceCode() {
        return souceCode;
    }

    public void setSouceCode(String souceCode) {
        this.souceCode = souceCode;
    }

    public String getSourceMeans() {
        return sourceMeans;
    }

    public void setSourceMeans(String sourceMeans) {
        this.sourceMeans = sourceMeans;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getInputParameter() {
        return inputParameter;
    }

    public void setInputParameter(String inputParameter) {
        this.inputParameter = inputParameter;
    }
}