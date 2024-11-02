package co.shrinkit.shrinkit.Application.UserCases;

import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.CreateShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.Out.LinkRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateShortLinkUseCaseImpl implements CreateShortLinkUseCase {

    private final LinkRepositoryPort linkRepository;

    public CreateShortLinkUseCaseImpl(LinkRepositoryPort linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link createShortLink(Link link) {
        return linkRepository.save(link);
    }



}
