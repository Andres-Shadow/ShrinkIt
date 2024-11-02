package co.shrinkit.shrinkit.Application.Adapters;

import co.shrinkit.shrinkit.Application.Dto.LinkDto;
import co.shrinkit.shrinkit.Domain.Models.Link;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LinkAdapter {

    public Link fromDTO(LinkDto dto) {
        Link link = new Link();
        link.setOriginalLink(dto.getLink());
        link.setLinkAlias(dto.getLinkAlias());
        link.setCreationDate(LocalDateTime.now());
        link.setExpirationDate(LocalDateTime.now().plusDays(10));
        return link;
    }


}
