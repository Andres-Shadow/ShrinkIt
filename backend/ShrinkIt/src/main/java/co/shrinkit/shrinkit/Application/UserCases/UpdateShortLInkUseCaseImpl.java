package co.shrinkit.shrinkit.Application.UserCases;

import java.util.Optional;

import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.UpdateShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.Out.LinkRepositoryPort;

public class UpdateShortLInkUseCaseImpl implements UpdateShortLinkUseCase {

    private final LinkRepositoryPort linkRepository;

    public UpdateShortLInkUseCaseImpl(LinkRepositoryPort linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Optional<Link> updateShortLink(Long linkId, Link link) {
        return linkRepository.updateLink(linkId, link);

    }

}
