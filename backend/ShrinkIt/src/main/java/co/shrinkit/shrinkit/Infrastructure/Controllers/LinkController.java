package co.shrinkit.shrinkit.Infrastructure.Controllers;

import java.util.List;

import co.shrinkit.shrinkit.Application.Dto.ApiResponseDto;
import co.shrinkit.shrinkit.Application.Dto.ApiResponseRetriveAllLinksDto;
import co.shrinkit.shrinkit.Application.Dto.LinkDto;
import co.shrinkit.shrinkit.Application.Dto.UpdateLinkDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.shrinkit.shrinkit.Application.Services.LinkService;
import co.shrinkit.shrinkit.Domain.Models.Link;

@RestController
@RequestMapping("/api/v1/link")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseRetriveAllLinksDto<List<Link>>> retrieveAllShortLinks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ApiResponseRetriveAllLinksDto<List<Link>> foundedLinks = linkService.getAllLinks(page, size);
        return new ResponseEntity<>(foundedLinks, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto<Link>> CreateLink(@RequestBody LinkDto link) {
        ApiResponseDto<Link> response = linkService.createShortLink(link);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/shrinkit.dev/{shortLink}")
    public ResponseEntity<ApiResponseDto<Link>> GetLink(@PathVariable String shortLink) {
        ApiResponseDto<Link> response = linkService.getOriginalLink(shortLink);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponseDto<Link>> UpdateLink(@RequestBody UpdateLinkDto link) {
        ApiResponseDto<Link> response = linkService.updateLink(link);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{linkId}")
    public ResponseEntity<ApiResponseDto<Boolean>> DeleteLink(@PathVariable Long linkId) {
        ApiResponseDto<Boolean> response = linkService.destroyShortLink(linkId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
