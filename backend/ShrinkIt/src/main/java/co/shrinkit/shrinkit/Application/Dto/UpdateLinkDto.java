package co.shrinkit.shrinkit.Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateLinkDto {
    private Long linkId;
    private String linkAlias;
}

