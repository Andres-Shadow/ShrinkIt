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
        ApiResponseDto<Link> apiResponseDto = new ApiResponseDto<>();

        if (optionalLink.isEmpty()) {
            // Return an error body
            apiResponseDto.setStatus(404);
            apiResponseDto.setMessage("Link not found");
            apiResponseDto.setData(null);
            return apiResponseDto;
        }

        // Update the alias and change the reference
        Link linkToUpdate = optionalLink.get();
        linkToUpdate.setLinkAlias(newAlias);
        Link updatedLink = createShortLink(linkToUpdate);

        // Returns a successful response
        apiResponseDto.setStatus(200);
        apiResponseDto.setMessage("Link updated");
        apiResponseDto.setData(updatedLink);

        return apiResponseDto;
    }


    public ApiResponseDto<Boolean> destroyShortLink(Long linkid){

        // Search the link with the ID provided
        Optional<Link> optionalLink = retrieveShortLink(linkid);
        ApiResponseDto<Boolean> apiResponseDto = new ApiResponseDto<>();

        if (optionalLink.isEmpty()) {
            // Return an error body
            apiResponseDto.setStatus(404);
            apiResponseDto.setMessage("Link not found");
            apiResponseDto.setData(false);
            return apiResponseDto;
        }

        Boolean deleted = deleteShortLink(linkid);

        // Returns a successful response
        apiResponseDto.setStatus(200);
        apiResponseDto.setMessage("Link deleted");
        apiResponseDto.setData(deleted);

        return apiResponseDto;
    }


    public ApiResponseDto<Link> createShortLink(LinkDto createShortLinkDTO) {
        // Uses the adapter to instance a new Link objecto
        Link link = linkAdapter.fromDTO(createShortLinkDTO);
        link.setShortLink("No-ShortLink-Yet");
        // It calls the method implemented due to the interface
        Link saved = createShortLink(link);

        saved.setShortLink(generateShortUtl(saved.getLinkId(), saved.getOriginalLink()));

        updateShortLink(saved.getLinkId(), saved);

        // Build a response object that will be returned to the user
        ApiResponseDto<Link> response = new ApiResponseDto<>();
        response.setData(saved);
        response.setMessage("Link created successfully");
        response.setStatus(200);
        return response;
    }


    public ApiResponseDto<Link> getOriginalLink(String shortUrl) {

        // Build the complete URL
        shortUrl = this.linkServerAddress + ":" + this.linkServerPort + "/" + this.linkPrefix + "/" + shortUrl;

        // Try to obtain the entity from the database
        Optional<Link> optionalLink = retrieveShortLinkByShortUrl(shortUrl);
        ApiResponseDto<Link> response = new ApiResponseDto<>();

        if (optionalLink.isPresent()) {
            // if the link exists, it'll return the entity as a response
            response.setData(optionalLink.get());
            response.setMessage("Link retrieved successfully");
            response.setStatus(200);
        } else {
            // if the link doesn't exist or isn't founded then, the response will be
            // a 404 and a null link
            response.setData(null);
            response.setMessage("Link not found");
            response.setStatus(404);
        }

        return response;
    }


    /*
    *
    * Logic methods used by the service
    * 
    * */


    private String generateShortUtl(Long linkId, String originalUrl) {
        String urlPrefix = "localhost:8080/shrinkit.dev/";
        String encodeLink, hashFragment, finalUrl;
        encodeLink = encodeLinkId(linkId);
        hashFragment = generateHashFragment(originalUrl);
        finalUrl = urlPrefix + encodeLink + hashFragment;
        return finalUrl;
    }

    private String encodeLinkId(Long linkId) {
        String base62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder shortUrl = new StringBuilder();

        while (linkId > 0) {
            int remainder = (int) (linkId % 62);
            shortUrl.append(base62.charAt(remainder));
            linkId /= 62;
        }

        return shortUrl.toString();
    }

    private String generateHashFragment(String originalUrl) {
        try {
            // Crear instancia de MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalUrl.getBytes());

            // Convertir a hexadecimal y extraer los primeros caracteres deseados
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.substring(0, 5); // Extraer el fragmento deseado

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash SHA-256", e);
        }
    }

}
