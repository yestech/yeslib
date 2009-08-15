package org.yestech.lib.i18n;

/**
 * The list of all Canadian provinces.
 */
public enum CanadaProvinceEnum {
    ALBERTA("AB"),
    BRITISH_COLUMBIA("BC"),
    MANITOBA("MB"),
    NEW_BRUNSWICK("NB"),
    NEW_FOUNDLAND_AND_LABRADOR("NL"),
    NORTHWEST_TERRITORIES("NT"),
    NOVA_SCOTIA("NS"),
 	NUNAVUT("NU"),
    ONTARIO("ON"),
    PRINCE_EDWARD_ISLAND("PE"),
    QUEBEC("QC"),
    SASKATCHEWAN("SK"),
    YUKON("YT");

    private String code;

    CanadaProvinceEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
