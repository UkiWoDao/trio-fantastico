package com.triofantastico.practiceproject.constant;

public final class PropertyNameConstant {

    private PropertyNameConstant() {
        throw new AssertionError("Instantiation attempted from within class");
    }

    public static final String TARGET_ENV = "target_env";
    public static final String TESTRAIL = "testrail";
    public static final String PARALLEL = "parallel";
    public static final String QA = "qa";
    public static final String DEV = "dev";
    public static final String STAGE = "stage";
    public static final String PROD = "prod";
    public static final String NON_PROD_ENVIRONMENTS = "qa|dev|stage";
}
