package co.shrinkit.shrinkit.Application.Dto;

import co.shrinkit.shrinkit.Domain.Models.Link;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto <T>{
    private int status;
    private T data;
    private String message;
}
