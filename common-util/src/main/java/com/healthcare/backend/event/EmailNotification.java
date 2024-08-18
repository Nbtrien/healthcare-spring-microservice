package com.healthcare.backend.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class EmailNotification {
    private List<String> emails;
    private HashMap<String, String> data;
    private String type;
}