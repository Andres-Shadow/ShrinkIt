package co.shrinkit.shrinkit.Infrastructure.Config;

import co.shrinkit.shrinkit.Application.Adapters.LinkAdapter;
import co.shrinkit.shrinkit.Application.Handlers.Impl.*;
import co.shrinkit.shrinkit.Domain.Ports.In.CreateShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.DeleteShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.RetrieveShortLinkUseCase;
import co.shrinkit.shrinkit.Domain.Ports.In.UpdateShortLinkUseCase;
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

//    @Bean
//    public LinkService linkService(LinkRepositoryPort linkRepositoryPort) {
//        return new LinkService(
//                new CreateShortLinkUseCaseImpl(linkRepositoryPort),
//                new RetrieveShortLinkUseCaseImpl(linkRepositoryPort),
//                new UpdateShortLInkUseCaseImpl(linkRepositoryPort),
//                new DeleteShortLinkUseCaseImpl(linkRepositoryPort),
//                new LinkAdapter());
//    }

    @Bean
    public CreateShortLinkHandler createShortLinkHandler(CreateShortLinkUseCase createShortLinkUseCase,
                                                         UpdateShortLinkUseCase updateShortLinkUseCase,
                                                         LinkAdapter linkAdapter) {
        return new CreateShortLinkHandler(createShortLinkUseCase, updateShortLinkUseCase, linkAdapter);
    }

    @Bean
    public DeleteShortLinkHandler deleteShortLinkHandler(RetrieveShortLinkUseCase retrieveShortLinkUseCase,
                                                         DeleteShortLinkUseCase deleteShortLinkUseCase){
        return new DeleteShortLinkHandler(retrieveShortLinkUseCase, deleteShortLinkUseCase);
    }

    @Bean
    public GetOriginalLinkHandler getOriginalLinkHandler(RetrieveShortLinkUseCase retrieveShortLinkUseCase){
        return new GetOriginalLinkHandler(retrieveShortLinkUseCase);
    }

    @Bean
    public RetriveAllLinkHandler retriveAllLinkHandler(RetrieveShortLinkUseCase retrieveShortLinkUseCase) {
        return new RetriveAllLinkHandler(retrieveShortLinkUseCase);
    }

    @Bean
    public UpdateShortLinkHandler updateShortLinkHandler(CreateShortLinkUseCase createShortLinkUseCase,
                                                         RetrieveShortLinkUseCase retrieveShortLinkUseCase,
                                                         LinkAdapter linkAdapter) {
        return new UpdateShortLinkHandler(createShortLinkUseCase, retrieveShortLinkUseCase, linkAdapter);
    }

    @Bean
    public LinkService linkService(CreateShortLinkHandler createShortLinkHandler,
                                   UpdateShortLinkHandler updateShortLinkHandler,
                                   RetriveAllLinkHandler retrieveAllLinksHandler,
                                   GetOriginalLinkHandler getOriginalLinkHandler,
                                   DeleteShortLinkHandler deleteShortLinkHandler) {
        return new LinkService(createShortLinkHandler, updateShortLinkHandler, retrieveAllLinksHandler,
                getOriginalLinkHandler, deleteShortLinkHandler);
    }

    // Definir casos de uso también como beans
    @Bean
    public CreateShortLinkUseCase createShortLinkUseCase(LinkRepositoryPort linkRepositoryPort) {
        return new CreateShortLinkUseCaseImpl(linkRepositoryPort);
    }

    @Bean
    public RetrieveShortLinkUseCase retrieveShortLinkUseCase(LinkRepositoryPort linkRepositoryPort) {
        return new RetrieveShortLinkUseCaseImpl(linkRepositoryPort);
    }

    @Bean
    public UpdateShortLinkUseCase updateShortLinkUseCase(LinkRepositoryPort linkRepositoryPort) {
        return new UpdateShortLInkUseCaseImpl(linkRepositoryPort);
    }

    @Bean
    public DeleteShortLinkUseCase deleteShortLinkUseCase(LinkRepositoryPort linkRepositoryPort) {
        return new DeleteShortLinkUseCaseImpl(linkRepositoryPort);
    }

    @Bean
    public LinkAdapter linkAdapter() {
        return new LinkAdapter();
    }

    @Bean
    public LinkRepositoryPort linkRepositoryPort(LinkRepositoryAdapter linkRepositoryAdapter) {
        return linkRepositoryAdapter;
    }
}
