package co.shrinkit.shrinkit.Infrastructure.Entities;

import java.time.LocalDateTime;

import co.shrinkit.shrinkit.Domain.Models.Link;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    private String originalLink;
    private String shortLink;
    private String linkAlias;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public static LinkEntity fromDomainLinkModel(Link link) {
        return new LinkEntity(
                link.getLinkId(),
                link.getOriginalLink(),
                link.getShortLink(),
                link.getLinkAlias(),
                link.getCreationDate(),
                link.getExpirationDate());
    }

    public Link toDomainLinkModel() {
        return new Link(
                this.linkId,
                this.originalLink,
                this.shortLink,
                this.linkAlias,
                this.creationDate,
                this.expirationDate);
    }
}
