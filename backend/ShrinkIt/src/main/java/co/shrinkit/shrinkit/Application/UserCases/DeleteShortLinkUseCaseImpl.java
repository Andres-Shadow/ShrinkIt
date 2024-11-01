package co.shrinkit.shrinkit.Application.UserCases;

import co.shrinkit.shrinkit.Domain.Ports.In.DeleteShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.Out.LinkRepositoryPort;

public class DeleteShortLinkUseCaseImpl implements DeleteShortLinkUseCase {

    private final LinkRepositoryPort linkRepository;

    public DeleteShortLinkUseCaseImpl(LinkRepositoryPort linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public boolean deleteShortLink(Long linkId) {
        return linkRepository.deleteLink(linkId);
    }

}
