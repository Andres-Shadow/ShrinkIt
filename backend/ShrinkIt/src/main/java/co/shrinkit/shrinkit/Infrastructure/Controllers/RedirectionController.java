package co.shrinkit.shrinkit.Infrastructure.Controllers;

import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class RedirectionController {

    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;

    public RedirectionController(RetrieveShortLinkUseCase retrieveShortLinkUseCase) {
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
    }

    @GetMapping("/shrinkit.dev/{shortCode}")
    public RedirectView redirectToOriginalUrl(@PathVariable String shortCode) {
        String url = "localhost:8080/shrinkit.dev/" + shortCode;
        Optional<Link> founded = retrieveShortLinkUseCase.retrieveShortLinkByShortUrl(url);
        String originalUrl = "";
        if (founded.isPresent()) {
            originalUrl = founded.get().getOriginalLink();
        }

        originalUrl = "https://"+ originalUrl;
        // Redirige a la URL original
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }
}
