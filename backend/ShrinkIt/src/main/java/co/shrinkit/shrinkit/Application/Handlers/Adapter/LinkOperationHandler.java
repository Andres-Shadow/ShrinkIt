package co.shrinkit.shrinkit.Application.Handlers.Adapter;

public interface LinkOperationHandler<T, R> {
    R handle(T request);
}
