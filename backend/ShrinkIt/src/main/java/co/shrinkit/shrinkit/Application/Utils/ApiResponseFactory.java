package co.shrinkit.shrinkit.Application.Utils;

import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;

public class ApiResponseFactory {

    public static <T> ApiResponseDto<T> successResponse(T data, String message) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(200);
        return response;
    }

    public static <T> ApiResponseDto<T> errorResponse(T data, String message, int status) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }
}
