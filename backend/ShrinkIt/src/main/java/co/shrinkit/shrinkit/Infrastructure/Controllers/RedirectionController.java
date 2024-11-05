package co.shrinkit.shrinkit.Infrastructure.Controllers;

import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Optional;

@RestController
public class RedirectionController {

    private final RetrieveShortLinkUseCase retrieveShortLinkUseCase;

    public RedirectionController(RetrieveShortLinkUseCase retrieveShortLinkUseCase) {
        this.retrieveShortLinkUseCase = retrieveShortLinkUseCase;
    }

    // Propertines injection
    @Value("${app.link.prefix}")
    private String linkPrefix;

    @Value("${app.link.server}")
    private String linkServerAddress;

    @Value("${server.port}")
    private String linkServerPort;

    @GetMapping("/shrinkit.dev/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {

        // Construye la URL completa de la URL corta
        String url = this.linkServerAddress + ":" + this.linkServerPort + "/" + this.linkPrefix +"/"+ shortCode;

        // Busca la URL original en el caso de uso
        Optional<Link> foundLink = retrieveShortLinkUseCase.retrieveShortLinkByShortUrl(url);
        if (foundLink.isPresent()) {
            String originalLink = foundLink.get().getOriginalLink();

            // Verifica si el enlace original comienza con "http://" o "https://"
            if (!originalLink.startsWith("http://") && !originalLink.startsWith("https://")) {
                // Si no comienza con http o https, lo concatena con "http://"
                originalLink = "http://" + originalLink;
            }

            // Redirige a la URL original
            response.sendRedirect(originalLink);
        } else {
            // Lanza un error 404 si no se encuentra el enlace
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found");
        }
    }
}
