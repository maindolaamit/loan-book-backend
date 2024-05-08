package org.hayo.finance.loanbook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiErrorCauses {
    private String type;
    private String detail;
    private String location;
    private String name;
    private String value;
}
