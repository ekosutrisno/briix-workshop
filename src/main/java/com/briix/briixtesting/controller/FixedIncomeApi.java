package com.briix.briixtesting.controller;

import com.briix.briixtesting.entity.FixedIncomeBank;
import com.briix.briixtesting.model.FixedIncomeRequest;
import com.briix.briixtesting.model.GeneralResponse;
import com.briix.briixtesting.service.FixedIncomeBankService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Eko Sutrisno
 * Rabu, 17/08/2022 20.32
 */
@RestController
@RequestMapping(path = "/api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class FixedIncomeApi implements FixedIncomeBankApi {
    private final FixedIncomeBankService fixedIncomeBankService;

    public FixedIncomeApi(FixedIncomeBankService fixedIncomeBankService) {
        this.fixedIncomeBankService = fixedIncomeBankService;
    }

    @Override
    @GetMapping
    public GeneralResponse<List<FixedIncomeBank>> getAll() {
        return fixedIncomeBankService.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public GeneralResponse<FixedIncomeBank> getSingle(@PathVariable Long id) {
        return fixedIncomeBankService.getSingle(id);
    }

    @Override
    @PostMapping
    public GeneralResponse<FixedIncomeBank> insert(@RequestBody FixedIncomeRequest data) {
        return fixedIncomeBankService.insert(data);
    }

    @Override
    @PutMapping("/{id}")
    public GeneralResponse<FixedIncomeBank> update(@PathVariable Long id, @RequestBody FixedIncomeRequest data) {
        return fixedIncomeBankService.update(id, data);
    }

    @Override
    @DeleteMapping("/{id}")
    public GeneralResponse<Map<String, Object>> delete(@PathVariable Long id) {
        return fixedIncomeBankService.delete(id);
    }
}
