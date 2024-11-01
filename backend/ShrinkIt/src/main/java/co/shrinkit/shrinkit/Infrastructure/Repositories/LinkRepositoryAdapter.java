package co.shrinkit.shrinkit.Infrastructure.Repositories;

import java.util.List;
import java.util.Optional;

import co.shrinkit.shrinkit.Infrastructure.Entities.LinkEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import co.shrinkit.shrinkit.Domain.Models.Link;
import co.shrinkit.shrinkit.Domain.Ports.Out.LinkRepositoryPort;

@Component
@AllArgsConstructor
public class LinkRepositoryAdapter implements LinkRepositoryPort{

    private final LinkRepository linkRepository;

    @Override
    public Link save(Link link) {
        LinkEntity linkEntity = LinkEntity.fromDomainLinkModel(link);
        LinkEntity savedLinkEntity = linkRepository.save(linkEntity);
        return savedLinkEntity.toDomainLinkModel();
    }

    @Override
    public Optional<Link> findByShortUrl(String shortUrl) {
        return linkRepository.findByShortLink(shortUrl).map(LinkEntity::toDomainLinkModel);
    }

    @Override
    public Optional<Link> findByOriginalUrl(String originalUrl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByOriginalUrl'");
    }

    @Override
    public Optional<Link> findById(Long id) {
        return linkRepository.findById(id).map(LinkEntity::toDomainLinkModel);
    }

    @Override
    public List<Link> findAll(Pageable pageable) {
        // Obtener una página de entidades desde el repositorio
        Page<LinkEntity> entityPage = null;

        entityPage = linkRepository.findAll(pageable);

        List<Link>  links = entityPage.get().toList().stream().map(LinkEntity::toDomainLinkModel).toList();

        // Convertir cada LinkEntity a Link usando map
        //List<Link> links = entityPage
        //        .stream()
        //        .map((Link t) -> LinkEntity.toDomainLinkModel(t))
        //        .toList();

        // Devolver un nuevo Page con los resultados convertidos
        return  links;
    }

    @Override
    public boolean deleteLink(Long id) {
        if(linkRepository.existsById(id)) {
            linkRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Optional<Link> updateLink(Long id, Link link) {
        if (linkRepository.existsById(id)) {
            LinkEntity linkEntity = LinkEntity.fromDomainLinkModel(link);
            LinkEntity savedLinkEntity = linkRepository.save(linkEntity);
            return Optional.of(savedLinkEntity.toDomainLinkModel());
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Integer countShortLinks() {
        return linkRepository.countAllLinks();
    }

}
