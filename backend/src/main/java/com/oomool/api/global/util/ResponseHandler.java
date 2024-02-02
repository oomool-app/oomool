package com.oomool.api.global.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object response) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("data", response);
        return new ResponseEntity<Object>(map, status);
    }
}
