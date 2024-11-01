package co.shrinkit.shrinkit.Infrastructure.Config;

import co.shrinkit.shrinkit.Application.Adapters.LinkAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.shrinkit.shrinkit.Application.Services.LinkService;
import co.shrinkit.shrinkit.Application.UserCases.CreateShortLinkUseCaseImpl;
import co.shrinkit.shrinkit.Application.UserCases.DeleteShortLinkUseCaseImpl;
import co.shrinkit.shrinkit.Application.UserCases.RetrieveShortLinkUseCaseImpl;
import co.shrinkit.shrinkit.Application.UserCases.UpdateShortLInkUseCaseImpl;
import co.shrinkit.shrinkit.Domain.Ports.Out.LinkRepositoryPort;
import co.shrinkit.shrinkit.Infrastructure.Repositories.LinkRepositoryAdapter;

@Configuration
public class ApplicationConfig {

    @Bean
    public LinkService linkService(LinkRepositoryPort linkRepositoryPort) {
        return new LinkService(
                new CreateShortLinkUseCaseImpl(linkRepositoryPort),
                new RetrieveShortLinkUseCaseImpl(linkRepositoryPort),
                new UpdateShortLInkUseCaseImpl(linkRepositoryPort),
                new DeleteShortLinkUseCaseImpl(linkRepositoryPort),
                new LinkAdapter());
    }

    @Bean
    public LinkRepositoryPort linkRepositoryPort(LinkRepositoryAdapter linkRepositoryAdapter) {
        return linkRepositoryAdapter;
    }
}
