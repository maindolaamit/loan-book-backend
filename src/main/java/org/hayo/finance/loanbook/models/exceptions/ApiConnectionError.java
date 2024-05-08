package org.hayo.finance.loanbook.models.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hayo.finance.loanbook.dto.response.ApiErrorSchema;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ApiConnectionError extends AbstractWebExceptions {

    private final ApiErrorSchema apiErrorSchema;

    public ApiConnectionError(String reason, HttpStatus httpStatus, String apiResponseError) {
        super(apiResponseError, httpStatus, reason);
        ApiErrorSchema apiErrorSchema1;

        // change the optionValue of apiErrorSchema to the new optionValue
        if (apiResponseError == null) {
            apiErrorSchema1 = ApiErrorSchema.builder()
                    .detail(Optional.ofNullable(reason).orElse("Connection Error"))
                    .type(httpStatus.getReasonPhrase())
                    .causes(null)
                    .build();
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                apiErrorSchema1 = objectMapper.readValue(apiResponseError, ApiErrorSchema.class);
            } catch (Exception e) {
                apiErrorSchema1 = ApiErrorSchema.builder()
                        .detail(Optional.of(apiResponseError).orElse("Connection Error"))
                        .type(httpStatus.getReasonPhrase())
                        .causes(null)
                        .build();
            }
        }
        this.apiErrorSchema = apiErrorSchema1;
    }


}
