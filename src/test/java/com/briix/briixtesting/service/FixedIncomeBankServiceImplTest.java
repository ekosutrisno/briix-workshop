package com.briix.briixtesting.service;

import com.briix.briixtesting.entity.FixedIncomeBank;
import com.briix.briixtesting.model.BasicInfo;
import com.briix.briixtesting.model.FixedIncomeRequest;
import com.briix.briixtesting.model.GeneralResponse;
import com.briix.briixtesting.repository.FixedIncomeBankRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("UT: Should return an Empty List")
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
    @DisplayName("UT: Should return a List with Data")
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
    @DisplayName("UT: Should return single Data")
    void getSingle() {
        // Given
        var localDateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 13, 30);

        var fixedIncome = FixedIncomeBank.builder()
                .id(1L)
                .code("7bdbfb10-825b-4f84-836a-bce698842486")
                .logo(null)
                .name("ALLO")
                .description("ALLO BANK INDONESIA Edited Ok")
                .createdAt(localDateTime)
                .lastUpdatedAt(localDateTime)
                .build();

        // When
        Mockito.when(fixedIncomeRepoUnderTest.findById(fixedIncome.getId()))
                .thenReturn(Optional.of(fixedIncome));

        var result = fixedIncomeBankServiceUnderTest
                .getSingle(fixedIncome.getId());

        // Then
        Mockito.verify(fixedIncomeRepoUnderTest).findById(fixedIncome.getId());
        Assertions.assertThat(result.getData()).isEqualTo(fixedIncome);
    }

    @Test
    @DisplayName("UT: Should success add new Data")
    void insert() {
        // Given
        var payload = FixedIncomeRequest.builder()
                .name("Briix Financial")
                .description("Briix Financial Description")
                .build();

        // When
        fixedIncomeBankServiceUnderTest.insert(payload);

        // Then
        ArgumentCaptor<FixedIncomeBank> fixedArgCaptor = ArgumentCaptor
                .forClass(FixedIncomeBank.class);

        Mockito.verify(fixedIncomeRepoUnderTest).save(fixedArgCaptor.capture());

        FixedIncomeBank fixedIncomeBank = fixedArgCaptor.getValue();

        Assertions.assertThat(fixedIncomeBank.getCode()).isNotNull();
    }

    @Test
    @DisplayName("UT: Should success update Data")
    void update() {
        // Given
        long fixedIncomeId = 1L;

        var localDateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 13, 30);

        var fixedIncome = FixedIncomeBank.builder()
                .id(fixedIncomeId)
                .code("7bdbfb10-825b-4f84-836a-bce698842486")
                .logo(null)
                .name("ALLO")
                .description("ALLO BANK INDONESIA Edited Ok")
                .createdAt(localDateTime)
                .lastUpdatedAt(localDateTime)
                .build();

        BDDMockito.given(fixedIncomeRepoUnderTest
                .findById(fixedIncomeId))
                .willReturn(Optional.of(fixedIncome));

        var payload = FixedIncomeRequest.builder()
                .name("Briix Financial For Update")
                .description("Briix Financial Description")
                .build();

        // When
        fixedIncomeBankServiceUnderTest.update(fixedIncomeId, payload);

        // Then
        ArgumentCaptor<FixedIncomeBank> fixedArgCaptor = ArgumentCaptor
                .forClass(FixedIncomeBank.class);

        Mockito.verify(fixedIncomeRepoUnderTest).save(fixedArgCaptor.capture());

        FixedIncomeBank fixedIncomeBank = fixedArgCaptor.getValue();

        Assertions.assertThat(fixedIncomeBank.getCode()).isNotNull();
        Assertions.assertThat(fixedIncomeBank.getName()).isEqualTo("Briix Financial For Update");
    }

    @Test
    @DisplayName("UT: Should success delete Data")
    void delete() {
        // Given
        long fixedIncomeId = 1L;

        var localDateTime = LocalDateTime.of(2019, Month.AUGUST, 18, 13, 30);

        var fixedIncome = FixedIncomeBank.builder()
                .id(fixedIncomeId)
                .code("7bdbfb10-825b-4f84-836a-bce698842486")
                .logo(null)
                .name("ALLO")
                .description("ALLO BANK INDONESIA Edited Ok")
                .createdAt(localDateTime)
                .deleted(Boolean.FALSE)
                .lastUpdatedAt(localDateTime)
                .build();

        BDDMockito.given(fixedIncomeRepoUnderTest
                        .findById(fixedIncomeId))
                .willReturn(Optional.of(fixedIncome));

        // When
        fixedIncomeBankServiceUnderTest.delete(fixedIncomeId);

        // Then
        ArgumentCaptor<FixedIncomeBank> fixedArgCaptor = ArgumentCaptor
                .forClass(FixedIncomeBank.class);

        Mockito.verify(fixedIncomeRepoUnderTest).save(fixedArgCaptor.capture());

        FixedIncomeBank fixedIncomeBank = fixedArgCaptor.getValue();

        Assertions.assertThat(fixedIncomeBank.getCode()).isNotNull();
        Assertions.assertThat(fixedIncomeBank.getDeleted()).isTrue();
    }
}