package co.shrinkit.shrinkit.Domain.Ports.In;

import java.util.List;
import java.util.Optional;

import co.shrinkit.shrinkit.Domain.Models.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RetrieveShortLinkUseCase {
    Optional<Link> retrieveShortLink(Long linkId);

    List<Link> retrieveAllShortLinks(int page, int size);

    Optional<Link> retrieveShortLinkByShortUrl(String shortUrl);

    Optional<Link> retrieveShortLinkByOriginalUrl(String originalUrl);

    Integer countShortLinks();

}
