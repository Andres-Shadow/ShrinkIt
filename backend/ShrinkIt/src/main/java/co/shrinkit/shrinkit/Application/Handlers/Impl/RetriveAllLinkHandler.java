package co.shrinkit.shrinkit.Application.Handlers.Impl;

import co.shrinkit.shrinkit.Application.Dto.ApiResponseRetriveAllLinksDto;
import co.shrinkit.shrinkit.Application.Dto.RetrieveAllLinkParamsDto;
import co.shrinkit.shrinkit.Application.Handlers.Adapter.LinkOperationHandler;
import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RetriveAllLinkHandler implements LinkOperationHandler<RetrieveAllLinkParamsDto, ApiResponseRetriveAllLinksDto<List<Link>>> {

    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;

    public RetriveAllLinkHandler(RetrieveShortLinkUseCase retrieveShortLinkUseCase) {
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
    }


    @Override
    public ApiResponseRetriveAllLinksDto<List<Link>> handle(RetrieveAllLinkParamsDto request) {
        int page = request.getPage();
        int size = request.getSize();
        int count = retrieveShortLinkUseCase.countShortLinks();
        List<Link> links = retrieveShortLinkUseCase.retrieveAllShortLinks(page, size);
        ApiResponseRetriveAllLinksDto<List<Link>> apiResponseDto = new ApiResponseRetriveAllLinksDto<>();

        if(links.isEmpty()){
            apiResponseDto.setStatus(200);
            apiResponseDto.setMessage("No links found");
            apiResponseDto.setData( links);
            apiResponseDto.setCount(0);
            return apiResponseDto;
        }

        apiResponseDto.setStatus(200);
        apiResponseDto.setData( links);
        apiResponseDto.setCount(count);
        apiResponseDto.setMessage("Links found");

        return apiResponseDto;

    }
}
