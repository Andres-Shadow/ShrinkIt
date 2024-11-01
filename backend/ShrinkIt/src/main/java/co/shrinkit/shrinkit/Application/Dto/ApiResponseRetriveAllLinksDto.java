package co.shrinkit.shrinkit.Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseRetriveAllLinksDto <T> {
    private int status;
    private int count;
    private T data;
    private String message;
}

