package org.hayo.finance.loanbook.models.exceptions;



import org.hayo.finance.loanbook.dto.response.ApiErrorCauses;
import org.hayo.finance.loanbook.dto.response.ApiErrorSchema;

import java.util.List;

public class ExceptionUtility {
    public static ApiErrorSchema getApiErrorSchema(AbstractWebExceptions e) {
        String type = Object.class.getSimpleName();
        List<ApiErrorCauses> causes = List.of(
                ApiErrorCauses.builder().type(type)
                        .detail(e.getMessage()).build()
        );
        return ApiErrorSchema.builder()
                .code(String.valueOf(e.getStatus().value()))
                .type(e.getStatus().toString())
                .detail(e.getReason())
                .causes(causes).build();
    }

}
