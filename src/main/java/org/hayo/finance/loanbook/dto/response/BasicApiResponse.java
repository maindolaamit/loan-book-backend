package org.hayo.finance.loanbook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicApiResponse {
    private String message;
    private LocalDateTime timestamp;
    private String additionalDetails;
}
