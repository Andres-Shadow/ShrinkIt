package co.shrinkit.shrinkit.Application.Handlers.Impl;

import co.shrinkit.shrinkit.Application.Adapters.LinkAdapter;
import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;
import co.shrinkit.shrinkit.Application.Dto.LinkDto;
import co.shrinkit.shrinkit.Application.Handlers.Adapter.LinkOperationHandler;
import co.shrinkit.shrinkit.Application.Utils.ApiResponseFactory;
import co.shrinkit.shrinkit.Application.Utils.LinkShortenerUtils;
import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.CreateShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.UpdateShortLinkUseCase;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateShortLinkHandler implements LinkOperationHandler<LinkDto, ApiResponseDto<Link>> {

    private final CreateShortLinkUseCase createShortLinkUseCase;
    private final UpdateShortLinkUseCase updateShortLinkUseCase;
    private final LinkAdapter linkAdapter;

    public CreateShortLinkHandler(CreateShortLinkUseCase createShortLinkUseCase, UpdateShortLinkUseCase updateShortLinkUseCase ,LinkAdapter linkAdapter) {
        this.createShortLinkUseCase = createShortLinkUseCase;
        this.updateShortLinkUseCase = updateShortLinkUseCase;
        this.linkAdapter = linkAdapter;
    }

    @Override
    public ApiResponseDto<Link> handle(LinkDto request) {

        // Uses the adapter to instance a new Link objecto
        Link link = linkAdapter.fromDTO(request);
        link.setShortLink("No-ShortLink-Yet");

        // It calls the method implemented due to the interface
        Link saved = createShortLinkUseCase.createShortLink(link);

        if (saved == null || saved.getLinkId() == null) {
            // Returns an error message in the body
            return ApiResponseFactory.errorResponse(null, "Error saving the link", 500);
        }

        // Generates the short link and then is set to the previously saved link
        saved.setShortLink(LinkShortenerUtils.generateShortUrl(saved.getLinkId(), saved.getOriginalLink()));

        Optional<Link> updated = updateShortLinkUseCase.updateShortLink(saved.getLinkId(), saved);

        if (updated.isEmpty()) {
            // Returns an error message in the body
            return ApiResponseFactory.errorResponse(null, "Error saving the link", 500);
        }

        return ApiResponseFactory.successResponse(saved, "Link created successfully");
    }
}
