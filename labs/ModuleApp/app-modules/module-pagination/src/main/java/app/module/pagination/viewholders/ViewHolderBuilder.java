package app.module.pagination.viewholders;

@FunctionalInterface
public interface ViewHolderBuilder<A, R> {
    R create(A parent);
}
