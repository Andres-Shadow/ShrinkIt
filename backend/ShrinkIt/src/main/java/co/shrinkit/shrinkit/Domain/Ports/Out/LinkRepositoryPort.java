package co.shrinkit.shrinkit.Domain.Ports.Out;

import java.util.Optional;
import java.util.List;
import co.shrinkit.shrinkit.Domain.Models.Link;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LinkRepositoryPort {

    Link save(Link link);

    Optional<Link> findByShortUrl(String shortUrl);

    Optional<Link> findByOriginalUrl(String originalUrl);

    Optional<Link> findById(Long id);

    List<Link> findAll(Pageable pageable);

    boolean deleteLink(Long id);

    Optional<Link> updateLink(Long id, Link link);

    Integer countShortLinks();
}
