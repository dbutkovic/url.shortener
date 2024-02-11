package com.infobip.assessment.url.shortener.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum RedirectType {
    MOVED_PERMANENTLY(302),
    FOUND(301);

    private final int statusCode;

    RedirectType(int statusCode) {
        this.statusCode = statusCode;
    }

    public static RedirectType getDefault() {
        return MOVED_PERMANENTLY; // Default value
    }

    public static RedirectType fromStatusCode(String statusCode) {
        if (StringUtils.isNumeric(statusCode)) {
            return fromStatusCode(Integer.parseInt(statusCode));
        }
        return getDefault();
    }

    public static RedirectType fromStatusCode(int statusCode) {
        for (RedirectType type : RedirectType.values()) {
            if (type.getStatusCode() == statusCode) {
                return type;
            }
        }
        return getDefault();
    }
}
