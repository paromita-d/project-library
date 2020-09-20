package org.library.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class MetaData {
    private String metaKey;
    private String metaValue;
}
