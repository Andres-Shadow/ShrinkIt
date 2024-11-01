package co.shrinkit.shrinkit.Domain.Models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    private Long linkId;
    private String originalLink;
    private String shortLink;
    private String linkAlias;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
}
