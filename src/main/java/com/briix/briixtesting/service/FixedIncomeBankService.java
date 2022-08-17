package com.briix.briixtesting.service;

import com.briix.briixtesting.entity.FixedIncomeBank;
import com.briix.briixtesting.model.GeneralResponse;
import com.briix.briixtesting.model.FixedIncomeRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 19.47
 */
public interface FixedIncomeBankService {
    GeneralResponse<List<FixedIncomeBank>> getAll();

    GeneralResponse<FixedIncomeBank> getSingle(Long id);

    GeneralResponse<FixedIncomeBank> insert(FixedIncomeRequest data);

    GeneralResponse<FixedIncomeBank> update(Long id, FixedIncomeRequest data);

    GeneralResponse<Map<String, Object>> delete(Long id);
}
