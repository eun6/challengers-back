package org.knulikelion.challengers_backend.data.dto.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ClubRequestDto {
    private String clubName;
    private String logoUrl;
    private String clubDescription;
    private String clubForm;
    private Integer clubApproved;
    private List<UserRequestDto> Members;
    private List<UserRequestDto> updateMembers;
}
