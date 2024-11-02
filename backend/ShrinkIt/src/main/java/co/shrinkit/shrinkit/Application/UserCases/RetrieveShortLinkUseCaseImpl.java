package co.shrinkit.shrinkit.Application.UserCases;

import java.util.List;
import java.util.Optional;

import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.Out.LinkRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RetrieveShortLinkUseCaseImpl implements RetrieveShortLinkUseCase {

    private final LinkRepositoryPort linkRepository;

    public RetrieveShortLinkUseCaseImpl(LinkRepositoryPort linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Optional<Link> retrieveShortLink(Long linkId) {
        return linkRepository.findById(linkId);
    }

    @Override
    public List<Link> retrieveAllShortLinks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return linkRepository.findAll(pageable);
    }

    @Override
    public Optional<Link> retrieveShortLinkByShortUrl(String shortUrl) {
        return linkRepository.findByShortUrl(shortUrl);
    }

    @Override
    public Optional<Link> retrieveShortLinkByOriginalUrl(String originalUrl) {
        return linkRepository.findByOriginalUrl(originalUrl);
    }

    @Override
    public Integer countShortLinks() {
        return linkRepository.countShortLinks();
    }

}
