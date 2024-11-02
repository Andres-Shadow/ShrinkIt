package co.shrinkit.shrinkit.Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveAllLinkParamsDto {

    private int page;
    private int size;

}
