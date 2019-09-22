/*
 * Copyright (c)-- Created By JUNJIE Lin -> On 2019/9/22
 */

package com.junjie.bookplatform.Model;

public enum Type {
    ECONOMICS("Economics"),
    FINANCE("Finance"),
    MANAGEMENT("Management"),
    ARTS("Arts"),
    IT("Information Technology"),
    ENGINEERING("Engineering"),
    BIOLOGY("Biology"),
    LAW("Law"),
    ENVIRONMENT("Environment"),
    LANGUAGE("Language"),
    MEDICINE("Medicine"),
    JOURNALISM("Journalism"),
    OTHER("Other");

    private  final String displayValue;

    Type(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
