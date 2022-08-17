package com.briix.briixtesting.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 19.48
 */
@Getter
@Setter
@Builder
public class GeneralResponse<T> {
    private String apiVersion;
    private String organization;
    private String serviceName;
    private int statusCode;
    private String message;
    private T data;
}
