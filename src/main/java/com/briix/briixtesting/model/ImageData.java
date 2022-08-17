package com.briix.briixtesting.model;

import com.briix.briixtesting.model.enumeration.MediaKind;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 19.16
 */
@Getter
@Setter
@Builder
public class ImageData {
    private String fileName;
    private MediaKind kind;
    private String url;
}
