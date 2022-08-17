package com.briix.briixtesting.service;

import com.briix.briixtesting.entity.FixedIncomeBank;
import com.briix.briixtesting.model.BasicInfo;
import com.briix.briixtesting.model.FixedIncomeRequest;
import com.briix.briixtesting.model.GeneralResponse;
import com.briix.briixtesting.model.ImageData;
import com.briix.briixtesting.model.enumeration.MediaKind;
import com.briix.briixtesting.repository.FixedIncomeBankRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 19.54
 */
@Service
public class FixedIncomeBankServiceImpl implements FixedIncomeBankService {

    private final FixedIncomeBankRepository fixedIncomeBankRepository;

    public FixedIncomeBankServiceImpl(FixedIncomeBankRepository fixedIncomeBankRepository) {
        this.fixedIncomeBankRepository = fixedIncomeBankRepository;
    }

    @Override
    public GeneralResponse<List<FixedIncomeBank>> getAll() {
        var fixedIncomes = fixedIncomeBankRepository.findAll();
        return GeneralResponse.<List<FixedIncomeBank>>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(OK.getReasonPhrase())
                .statusCode(OK.value())
                .data(fixedIncomes)
                .build();
    }

    @Override
    public GeneralResponse<FixedIncomeBank> getSingle(Long id) {
        var fixedIncome = fixedIncomeBankRepository.findById(id);

        if (fixedIncome.isPresent())
            return GeneralResponse.<FixedIncomeBank>builder()
                    .apiVersion(BasicInfo.apiVersion)
                    .organization(BasicInfo.organization)
                    .serviceName(BasicInfo.serviceName)
                    .message(OK.getReasonPhrase())
                    .statusCode(OK.value())
                    .data(fixedIncome.get())
                    .build();

        return GeneralResponse.<FixedIncomeBank>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(NOT_FOUND.getReasonPhrase())
                .statusCode(NOT_FOUND.value())
                .data(null)
                .build();
    }

    @Override
    public GeneralResponse<FixedIncomeBank> insert(FixedIncomeRequest data) {
        var fixedIncome = fixedIncomeBankRepository
                .save(FixedIncomeBank.builder()
                        .code(UUID.randomUUID().toString())
                        .name(data.getName())
                        .description(data.getDescription())
                        .logo(ImageData
                                .builder()
                                .fileName("BCA.png")
                                .kind(MediaKind.PNG)
                                .url("images/6dnFwDDIQLXyd933TpTPKd.png")
                                .build()
                        ).build());

        return GeneralResponse.<FixedIncomeBank>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(OK.getReasonPhrase())
                .statusCode(OK.value())
                .data(fixedIncome)
                .build();
    }

    @Override
    public GeneralResponse<FixedIncomeBank> update(Long id, FixedIncomeRequest data) {
        var fixedIncomeFromDb = fixedIncomeBankRepository.findById(id);

        if (fixedIncomeFromDb.isPresent()) {
            var fixedIncome = fixedIncomeFromDb.get();
            fixedIncome.setName(data.getName());
            fixedIncome.setDescription(data.getDescription());

            return GeneralResponse.<FixedIncomeBank>builder()
                    .apiVersion(BasicInfo.apiVersion)
                    .organization(BasicInfo.organization)
                    .serviceName(BasicInfo.serviceName)
                    .message(OK.getReasonPhrase())
                    .statusCode(OK.value())
                    .data(fixedIncomeBankRepository.save(fixedIncome))
                    .build();

        }

        return GeneralResponse.<FixedIncomeBank>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(NOT_FOUND.getReasonPhrase())
                .statusCode(NOT_FOUND.value())
                .data(null)
                .build();
    }

    @Override
    public GeneralResponse<Map<String, Object>> delete(Long id) {
        var fixedIncomeFromDb = fixedIncomeBankRepository.findById(id);

        Map<String, Object> response = new HashMap<>();

        if (fixedIncomeFromDb.isPresent()) {
            // Do Delete (Set Deleted to True)
            fixedIncomeFromDb.get().setDeleted(Boolean.TRUE);
            fixedIncomeBankRepository.save(fixedIncomeFromDb.get());

            // Set Message
            response.put("deleteStatus", "Success");
            response.put("deleteMessage", "The data has been successfully deleted");

            return GeneralResponse.<Map<String, Object>>builder()
                    .apiVersion(BasicInfo.apiVersion)
                    .organization(BasicInfo.organization)
                    .serviceName(BasicInfo.serviceName)
                    .message(OK.getReasonPhrase())
                    .statusCode(OK.value())
                    .data(response)
                    .build();
        }

        // Set Message if Failed to delete or data Not Found
        response.put("deleteStatus", "Failed");
        response.put("deleteMessage", String.format("Data failed to delete, Fixed Income with ID [%d] not found.", id));

        return GeneralResponse.<Map<String, Object>>builder()
                .apiVersion(BasicInfo.apiVersion)
                .organization(BasicInfo.organization)
                .serviceName(BasicInfo.serviceName)
                .message(NOT_FOUND.getReasonPhrase())
                .statusCode(NOT_FOUND.value())
                .data(response)
                .build();
    }
}
