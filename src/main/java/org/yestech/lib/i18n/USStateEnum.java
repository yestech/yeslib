package org.yestech.lib.i18n;


/**
 * A list of states to be used in a drop downlist
 */
public enum USStateEnum {

    ALASKA("AK"),
    ALABAMA("AL"),
    ARKANSAS("AR"),
    ARIZONA("AZ"),
    CALIFORNIA("CA"),
    COLORADO("CO"),
    CONNECTICUT("CT"),
    DISTRICT_OF_COLUMBIA("DC"),
    DELAWARE("DE"),
    FLORIDA("FL"),
    GEORGIA("GA"),
    HAWAII("HI"),
    IOWA("IA"),
    IDAHO("ID"),
    ILLINOIS("IL"),
    INDIANA("IN"),
    KANSAS("KS"),
    KENTUCKY("KY"),
    LOUISIANA("LA"),
    MASSACHUSETTS("MA"),
    MARYLAND("MD"),
    MAINE("ME"),
    MICHIGAN("MI"),
    MINNESOTA("MN"),
    MISSOURI("MO"),
    MISSISSIPPI("MS"),
    MONTANA("MT"),
    NORTH_CAROLINA("NC"),
    NORTH_DAKOTA("ND"),
    NEBRASKA("NE"),
    NEW_HAMPSHIRE("NH"),
    NEW_JERSEY("NJ"),
    NEW_MEXICO("NM"),
    NEVADO("NV"),
    NEW_YORK("NY"),
    OHIO("OH"),
    OKLAHOMA("OK"),
    OREGON("OR"),
    PENNSYLVANIA("PA"),
    RHODE_ISLAND("RI"),
    SOUTH_CAROLINA("SC"),
    SOUTH_DAKOTA("SD"),
    TENNESSEE("TN"),
    TEXAS("TX"),
    UTAH("UT"),
    VIRGINIA("VA"),
    VERMONT("VT"),
    WASHINGTON("WA"),
    WISCONSIN("WI"),
    WEST_VIRGINIA("WV"),
    WYOMING("WY");

    private String code;

    USStateEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

