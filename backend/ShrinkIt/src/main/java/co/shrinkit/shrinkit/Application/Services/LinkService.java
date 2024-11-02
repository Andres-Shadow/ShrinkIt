package co.shrinkit.shrinkit.Application.Services;


import java.util.List;

import co.shrinkit.shrinkit.Application.Dto.*;
import co.shrinkit.shrinkit.Application.Handlers.Impl.*;

import co.shrinkit.shrinkit.Domain.Models.Link;

import org.springframework.stereotype.Service;


@Service
public class LinkService{

    // Dependency injection

    private final CreateShortLinkHandler createShortLinkHandler;
    private final UpdateShortLinkHandler updateShortLinkHandler;
    private final RetriveAllLinkHandler retriveAllLinkHandler;
    private final GetOriginalLinkHandler getOriginalLinkHandler;
    private final DeleteShortLinkHandler deleteShortLinkHandler;

    public LinkService(CreateShortLinkHandler createShortLinkHandler,
                       UpdateShortLinkHandler updateShortLinkHandler,
                       RetriveAllLinkHandler retriveAllLinkHandler,
                       GetOriginalLinkHandler getOriginalLinkHandler,
                       DeleteShortLinkHandler deleteShortLinkHandler) {
        this.createShortLinkHandler = createShortLinkHandler;
        this.updateShortLinkHandler = updateShortLinkHandler;
        this.retriveAllLinkHandler = retriveAllLinkHandler;
        this.getOriginalLinkHandler = getOriginalLinkHandler;
        this.deleteShortLinkHandler = deleteShortLinkHandler;
    }


    /*
    * Methods used by the link controller
    *
    */


    public ApiResponseRetriveAllLinksDto<List<Link>> getAllLinks(int page, int size) {
        return retriveAllLinkHandler.handle(new RetrieveAllLinkParamsDto(page, size));
    }

    public ApiResponseDto<Link> updateLink(UpdateLinkDto updateLinkDto) {
        return updateShortLinkHandler.handle(updateLinkDto);
    }


    public ApiResponseDto<Boolean> destroyShortLink(Long linkid){
        return deleteShortLinkHandler.handle(linkid);
    }


    public ApiResponseDto<Link> createShortLink(LinkDto createShortLinkDTO) {
        return createShortLinkHandler.handle(createShortLinkDTO);
    }


    public ApiResponseDto<Link> getOriginalLink(String shortUrl) {
        return getOriginalLinkHandler.handle(shortUrl);
    }



}
