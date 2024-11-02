package co.shrinkit.shrinkit.Application.Handlers.Impl;

import co.shrinkit.shrinkit.Application.Adapters.LinkAdapter;
import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;
import co.shrinkit.shrinkit.Application.Dto.UpdateLinkDto;
import co.shrinkit.shrinkit.Application.Handlers.Adapter.LinkOperationHandler;
import co.shrinkit.shrinkit.Application.Utils.ApiResponseFactory;
import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.CreateShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.UpdateShortLinkUseCase;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UpdateShortLinkHandler implements LinkOperationHandler<UpdateLinkDto, ApiResponseDto<Link>> {


    private final CreateShortLinkUseCase createShortLinkUseCase;
    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;
    private final LinkAdapter linkAdapter;

    public UpdateShortLinkHandler(CreateShortLinkUseCase createShortLinkUseCase, RetrieveShortLinkUseCase retrieveShortLinkUseCase, LinkAdapter linkAdapter) {
        this.createShortLinkUseCase = createShortLinkUseCase;
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
        this.linkAdapter = linkAdapter;
    }

    @Override
    public ApiResponseDto<Link> handle(UpdateLinkDto request) {
        // Obtain DTO variables
        Long linkId = request.getLinkId();
        String newAlias = request.getLinkAlias();

        // Search the link with the ID provided
        Optional<Link> optionalLink = retrieveShortLinkUseCase.retrieveShortLink(linkId);

        if (optionalLink.isEmpty()) {
            return ApiResponseFactory.errorResponse(null,"Link not found", 404);
        }

        // Update the alias and change the reference
        Link linkToUpdate = optionalLink.get();
        linkToUpdate.setLinkAlias(newAlias);
        Link updatedLink = createShortLinkUseCase.createShortLink(linkToUpdate);


        return ApiResponseFactory.successResponse(updatedLink, "Link updated");
    }
}
