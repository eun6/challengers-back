package org.knulikelion.challengers_backend.data.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResultResponseDto {
    private Integer code;
    private String msg;
}
