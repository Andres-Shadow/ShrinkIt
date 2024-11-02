package co.shrinkit.shrinkit.Application.Handlers.Impl;

import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;
import co.shrinkit.shrinkit.Application.Handlers.Adapter.LinkOperationHandler;
import co.shrinkit.shrinkit.Application.Utils.ApiResponseFactory;
import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.DeleteShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class DeleteShortLinkHandler implements LinkOperationHandler<Long, ApiResponseDto<Boolean>> {

    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;
    private final DeleteShortLinkUseCase deleteShortLinkUseCase;

    public DeleteShortLinkHandler(RetrieveShortLinkUseCase retrieveShortLinkUseCase, DeleteShortLinkUseCase deleteShortLinkUseCase) {
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
        this.deleteShortLinkUseCase = deleteShortLinkUseCase;
    }

    @Override
    public ApiResponseDto<Boolean> handle(Long request) {
        // Search the link with the ID provided
        Optional<Link> optionalLink = retrieveShortLinkUseCase.retrieveShortLink(request);

        if (optionalLink.isEmpty()) {
            // Return an error body
            return ApiResponseFactory.errorResponse(false, "Link not found", 404);
        }

        Boolean deleted = deleteShortLinkUseCase.deleteShortLink(request);

        // Returns a successful response
        return ApiResponseFactory.successResponse(deleted, "Link deleted");
    }
}
