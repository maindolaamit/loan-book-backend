package org.hayo.finance.loanbook.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplicationRequest;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.hayo.finance.loanbook.repository.LoanApplicationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
@Profile({"local", "test"})
public class LoanApplicationsLoader implements CommandLineRunner {
    private final LoanApplicationRepository repository;
    private final ObjectMapper objectMapper;
    private final LoanApplicationMapper mapper;

    public void run(String... args) throws Exception {
        log.info("Loading Application data...");
        if (repository.count() > 0) {
            log.info("Loan Application data already loaded");
        }

        try {
            log.info("Loan data not loaded, loading ...");
            List<LoanApplicationRequest> applications = objectMapper.readValue(
                    getPathname(),
                    new TypeReference<>() {
                    });
            val list = applications.stream().map(mapper::toNewEntity).toList();
            repository.saveAll(list);
            log.info("Loan data loaded,  no. of records {}", repository.count());
        } catch (IOException e) {
            log.error("Error loading SupportForm data", e);
            throw e;
        }
    }

    public static File getPathname() {
        return new File("src/main/resources/data/loan-applications.json");
    }
}
