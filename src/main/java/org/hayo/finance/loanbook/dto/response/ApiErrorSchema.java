package org.hayo.finance.loanbook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ApiErrorSchema {
    private String type;
    private String detail;
    private List<ApiErrorCauses> causes;
}
