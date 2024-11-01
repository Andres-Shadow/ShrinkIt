package co.shrinkit.shrinkit.Domain.Ports.In;

import co.shrinkit.shrinkit.Domain.Models.Link;

public interface CreateShortLinkUseCase {
    Link createShortLink(Link link);
}
