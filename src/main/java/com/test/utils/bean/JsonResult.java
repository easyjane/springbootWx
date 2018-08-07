package com.test.utils.bean;

import java.util.List;

public class JsonResult {

    private String status;
    private List<Words> words;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Words> getWords() {
        return words;
    }

    public void setWords(List<Words> words) {
        this.words = words;
    }


    @Override
    public String toString() {
        return "JsonResult{" +
                "status='" + status + '\'' +
                ", words=" + words +
                '}';
    }
}
