package com.briix.briixtesting.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 20.10
 */
@Getter
@Setter
@Builder
public class FixedIncomeRequest {

    private String name;

    private String description;

    private ImageData logo;
}
