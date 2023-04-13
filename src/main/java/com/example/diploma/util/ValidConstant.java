package com.example.diploma.util;

public class ValidConstant {
    public static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z\\d._\\-]+[@][A-Za-z]+\\.[A-Za-z]{2,4}$";
    public static final String PASSWORD_PATTERN = "^(?=\\w*\\d)(?=\\w*[a-z])(?=\\w*[A-Z])\\w{8,45}$";
    public static final String FIRST_AND_LAST_NAME_PATTERN = "^[A-Z][a-z]{2,45}$|^[A-Z][a-z]{2,21}[-][A-Z][a-z]{2,21}$";
    public static final String PHONE_NUMBER_PATTERN = "^(\\+375)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$";
    public static final String NAME_ITEM_PATTERN = "[a-zA-Z]+";
    public static final String NAME_MASTER_PATTERN = "^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{2,})?)";
}
