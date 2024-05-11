package org.hayo.finance.loanbook.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.hayo.finance.loanbook.models.entity.LoanApplicationEntity;
import org.hayo.finance.loanbook.models.mapper.LoanApplicationMapper;
import org.hayo.finance.loanbook.repository.LoanApplicationRepository;
import org.mapstruct.factory.Mappers;
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

    public void run(String... args) throws Exception {
        log.info("Loading Application data...");
        if (repository.count() > 0) {
            log.info("Loan Application data already loaded");
            return;
        }

        try {
            log.info("Loan data not loaded, loading ...");
            final var list = getLoanApplicationEntitiesFromDataFile();
            repository.saveAll(list);
            log.info("Loan data loaded,  no. of records {}", repository.count());
        } catch (IOException e) {
            log.error("Error loading SupportForm data", e);
            throw e;
        }
    }

    public static List<LoanApplicationEntity> getLoanApplicationEntitiesFromDataFile() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        val instance = Mappers.getMapper(LoanApplicationMapper.class);
        List<NewLoanApplicationRequest> applications = objectMapper.readValue(
                getDataFilePathname(),
                new TypeReference<>() {
                });
        return applications.stream().map(instance::toNewEntity).toList();
    }

    public static File getDataFilePathname() {
        return new File("src/main/resources/data/loan-applications.json");
    }
}
