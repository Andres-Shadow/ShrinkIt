package co.shrinkit.shrinkit.Application.Handlers.Impl;

import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;
import co.shrinkit.shrinkit.Application.Handlers.Adapter.LinkOperationHandler;
import co.shrinkit.shrinkit.Application.Utils.ApiResponseFactory;
import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class GetOriginalLinkHandler implements LinkOperationHandler<String, ApiResponseDto<Link>> {

    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;

    public GetOriginalLinkHandler(RetrieveShortLinkUseCase retrieveShortLinkUseCase) {
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
    }

    // Propertines injection
    @Value("${app.link.prefix}")
    private String linkPrefix;

    @Value("${app.link.server}")
    private String linkServerAddress;

    @Value("${server.port}")
    private String linkServerPort;

    @Override
    public ApiResponseDto<Link> handle(String request) {
        // Build the complete URL
        request = this.linkServerAddress + ":" + this.linkServerPort + "/" + this.linkPrefix + "/" + request;

        // Try to obtain the entity from the database
        Optional<Link> optionalLink = retrieveShortLinkUseCase.retrieveShortLinkByShortUrl(request);

        // if the link exists, it'll return the entity as a response
        // if the link doesn't exist or isn't founded then, the response will be
        // a 404 and a null link
        return optionalLink.map(link -> ApiResponseFactory.successResponse(link,
                "Link retrieved successfully")).orElseGet(() ->
                ApiResponseFactory.errorResponse(null, "Link not found", 404));
    }
}
