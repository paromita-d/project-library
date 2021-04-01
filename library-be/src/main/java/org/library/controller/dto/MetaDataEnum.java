package org.library.controller.dto;

import lombok.Getter;

public enum MetaDataEnum {

    CHECKOUT_DURATION("checkout_duration");

    @Getter
    private String metaKey;

    MetaDataEnum(String metaKey) {
        this.metaKey = metaKey;
    }
}
