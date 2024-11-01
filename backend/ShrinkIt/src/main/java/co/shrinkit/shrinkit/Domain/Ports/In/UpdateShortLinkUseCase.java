package co.shrinkit.shrinkit.Domain.Ports.In;

import java.util.Optional;

import co.shrinkit.shrinkit.Domain.Models.Link;

public interface UpdateShortLinkUseCase {
    Optional<Link> updateShortLink(Long linkId, Link link);
}
