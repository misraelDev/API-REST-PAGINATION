package com.api.dto;

public class PageResponseDTO<T> {
    private int recordCount;
    private T response;

    public PageResponseDTO(int recordCount, T response) {
        this.recordCount = recordCount;
        this.response = response;
    }

    // Getters and setters
    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}