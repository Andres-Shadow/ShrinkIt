package co.shrinkit.shrinkit.Application.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import co.shrinkit.shrinkit.Application.Adapters.LinkAdapter;
import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;
import co.shrinkit.shrinkit.Application.Dto.ApiResponseRetriveAllLinksDto;
import co.shrinkit.shrinkit.Application.Dto.LinkDto;
import co.shrinkit.shrinkit.Application.Dto.UpdateLinkDto;
import co.shrinkit.shrinkit.Application.Utils.ApiResponseFactory;
import co.shrinkit.shrinkit.Application.Utils.LinkShortenerUtils;
import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.CreateShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.DeleteShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.UpdateShortLinkUseCase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;


public class LinkService implements CreateShortLinkUseCase, RetrieveShortLinkUseCase, UpdateShortLinkUseCase, DeleteShortLinkUseCase {

    // Dependency injection
    private final CreateShortLinkUseCase createShortLinkUseCase;
    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;
    private final UpdateShortLinkUseCase updateShortLinkUseCase;
    private final DeleteShortLinkUseCase deleteShortLinkUseCase;
    private final LinkAdapter linkAdapter;

    // Propertines injection
    @Value("${app.link.prefix}")
    private String linkPrefix;

    @Value("${app.link.server}")
    private String linkServerAddress;

    @Value("${server.port}")
    private String linkServerPort;

    public LinkService(CreateShortLinkUseCase createShortLinkUseCase,
                       RetrieveShortLinkUseCase retrieveShortLinkUseCase,
                       UpdateShortLinkUseCase updateShortLinkUseCase,
                       DeleteShortLinkUseCase deleteShortLinkUseCase,
                       LinkAdapter linkAdapter) {
        this.createShortLinkUseCase = createShortLinkUseCase;
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
        this.updateShortLinkUseCase = updateShortLinkUseCase;
        this.deleteShortLinkUseCase = deleteShortLinkUseCase;
        this.linkAdapter = linkAdapter;
    }


    @Override
    public Link createShortLink(Link link) {
        return createShortLinkUseCase.createShortLink(link);
    }

    @Override
    public boolean deleteShortLink(Long linkId) {
        return deleteShortLinkUseCase.deleteShortLink(linkId);
    }

    @Override
    public Optional<Link> updateShortLink(Long linkId, Link link) {
        return updateShortLinkUseCase.updateShortLink(linkId, link);
    }

    @Override
    public Optional<Link> retrieveShortLink(Long linkId) {
        return retrieveShortLinkUseCase.retrieveShortLink(linkId);
    }

    @Override
    public List<Link> retrieveAllShortLinks(int page, int size) {
        return retrieveShortLinkUseCase.retrieveAllShortLinks(page, size);
    }

    @Override
    public Optional<Link> retrieveShortLinkByShortUrl(String shortUrl) {
        return retrieveShortLinkUseCase.retrieveShortLinkByShortUrl(shortUrl);
    }

    @Override
    public Optional<Link> retrieveShortLinkByOriginalUrl(String originalUrl) {
        return retrieveShortLinkUseCase.retrieveShortLinkByOriginalUrl(originalUrl);
    }

    @Override
    public Integer countShortLinks() {
        return retrieveShortLinkUseCase.countShortLinks();
    }


    /*
    * Methods used by the link controller
    *
    */


    public ApiResponseRetriveAllLinksDto<List<Link>> getAllLinks(int page, int size) {

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
        apiResponseDto.setCount(countShortLinks());
        apiResponseDto.setMessage("Links found");

        return apiResponseDto;
    }

    public ApiResponseDto<Link> updateLink(UpdateLinkDto updateLinkDto) {
        // Obtain DTO variables
        Long linkId = updateLinkDto.getLinkId();
        String newAlias = updateLinkDto.getLinkAlias();

        // Search the link with the ID provided
        Optional<Link> optionalLink = retrieveShortLink(linkId);

        if (optionalLink.isEmpty()) {
            return ApiResponseFactory.errorResponse(null,"Link not found", 404);
        }

        // Update the alias and change the reference
        Link linkToUpdate = optionalLink.get();
        linkToUpdate.setLinkAlias(newAlias);
        Link updatedLink = createShortLink(linkToUpdate);


        return ApiResponseFactory.successResponse(updatedLink, "Link updated");
    }


    public ApiResponseDto<Boolean> destroyShortLink(Long linkid){

        // Search the link with the ID provided
        Optional<Link> optionalLink = retrieveShortLink(linkid);

        if (optionalLink.isEmpty()) {
            // Return an error body
            return ApiResponseFactory.errorResponse(false, "Link not found", 404);
        }

        Boolean deleted = deleteShortLink(linkid);

        // Returns a successful response
        return ApiResponseFactory.successResponse(deleted, "Link deleted");
    }


    public ApiResponseDto<Link> createShortLink(LinkDto createShortLinkDTO) {
        // Uses the adapter to instance a new Link objecto
        Link link = linkAdapter.fromDTO(createShortLinkDTO);
        link.setShortLink("No-ShortLink-Yet");
        // It calls the method implemented due to the interface
        Link saved = createShortLink(link);

        if (saved == null || saved.getLinkId() == null) {
            // Returns an error message in the body
            return ApiResponseFactory.errorResponse(null, "Error saving the link", 500);
        }

        // Generates the short link and then is set to the previously saved link
        saved.setShortLink(LinkShortenerUtils.generateShortUrl(saved.getLinkId(), saved.getOriginalLink()));


        Optional<Link> updated = updateShortLink(saved.getLinkId(), saved);

        if (updated.isEmpty()) {
            // Returns an error message in the body
            return ApiResponseFactory.errorResponse(null, "Error saving the link", 500);
        }

        return ApiResponseFactory.successResponse(saved, "Link created successfully");
    }


    public ApiResponseDto<Link> getOriginalLink(String shortUrl) {

        // Build the complete URL
        shortUrl = this.linkServerAddress + ":" + this.linkServerPort + "/" + this.linkPrefix + "/" + shortUrl;

        // Try to obtain the entity from the database
        Optional<Link> optionalLink = retrieveShortLinkByShortUrl(shortUrl);

        // if the link exists, it'll return the entity as a response
        // if the link doesn't exist or isn't founded then, the response will be
        // a 404 and a null link
        return optionalLink.map(link -> ApiResponseFactory.successResponse(link,
                "Link retrieved successfully")).orElseGet(() ->
                ApiResponseFactory.errorResponse(null, "Link not found", 404));
    }



}
