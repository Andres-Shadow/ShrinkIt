package co.shrinkit.shrinkit.Infrastructure.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.shrinkit.shrinkit.Infrastructure.Entities.LinkEntity;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    @Query("select l from LinkEntity l where l.shortLink=:link")
    Optional<LinkEntity> findByShortLink(String link);

    @Query("select count(l) from LinkEntity  l")
    Integer countAllLinks();

    Page<LinkEntity> findAll(Pageable pageable);

}
