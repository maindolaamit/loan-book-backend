package org.hayo.finance.loanbook.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
//@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorSchema {
    private String code;
    private String type;
    private String detail;
    private List<ApiErrorCauses> causes;
}
