package com.briix.briixtesting.service;

import com.briix.briixtesting.entity.FixedIncomeBank;
import com.briix.briixtesting.model.BasicInfo;
import com.briix.briixtesting.model.GeneralResponse;
import com.briix.briixtesting.repository.FixedIncomeBankRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 22.11
 */
@ExtendWith(MockitoExtension.class)
class FixedIncomeBankServiceImplTest {

    @Mock
    private FixedIncomeBankRepository fixedIncomeRepoUnderTest;

    private FixedIncomeBankService fixedIncomeBankServiceUnderTest;

    @BeforeEach
    void setUp() {
        fixedIncomeBankServiceUnderTest = new FixedIncomeBankServiceImpl(fixedIncomeRepoUnderTest);
    }

    @Test
    void getAllShouldReturnEmptyList() {
        // Given
        List<FixedIncomeBank> fixedIncomeFromDB = Collections.emptyList();

        var fixedIncomes = GeneralResponse.<List<FixedIncomeBank>>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(OK.getReasonPhrase())
                .statusCode(OK.value())
                .data(fixedIncomeFromDB)
                .build();

        // When
        Mockito.when(fixedIncomeRepoUnderTest.findAll())
                .thenReturn(fixedIncomeFromDB);

        var result = fixedIncomeBankServiceUnderTest.getAll();

        // Then
        Mockito.verify(fixedIncomeRepoUnderTest).findAll();

        Assertions.assertThat(fixedIncomes.getData().isEmpty()).isTrue();
        Assertions.assertThat(result.getData()).isEqualTo(fixedIncomeFromDB);

    }

    @Test
    void getAllShouldReturnData() {
        // Given
        var localDateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 13, 30);

        List<FixedIncomeBank> fixedIncomeFromDB = List.of(
                FixedIncomeBank.builder()
                        .id(1L)
                        .code("7bdbfb10-825b-4f84-836a-bce698842486")
                        .logo(null)
                        .name("ALLO")
                        .description("ALLO BANK INDONESIA Edited Ok")
                        .createdAt(localDateTime)
                        .lastUpdatedAt(localDateTime)
                        .build()
        );

        var fixedIncomes = GeneralResponse.<List<FixedIncomeBank>>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(OK.getReasonPhrase())
                .statusCode(OK.value())
                .data(fixedIncomeFromDB)
                .build();

        // When
        Mockito.when(fixedIncomeRepoUnderTest.findAll())
                .thenReturn(fixedIncomeFromDB);

        var result = fixedIncomeBankServiceUnderTest.getAll();

        // Then
        Mockito.verify(fixedIncomeRepoUnderTest).findAll();
        Assertions.assertThat(fixedIncomes.getData().isEmpty()).isFalse();
        Assertions.assertThat(result.getData()).isEqualTo(fixedIncomeFromDB);

        result.getData().forEach(fixedIncomeBank -> System.out.println(fixedIncomeBank.getCode()));
    }

    @Test
    void getSingle() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}