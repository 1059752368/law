package com.biyesheji.law.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResultVO {

    private boolean status = true;

    private int errorCode = 0;

    private String errorMsg = "";

    private int ver = 1;

    private Map<String, Object> data;

    public void addData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

}
