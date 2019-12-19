package app.module.pagination;

@FunctionalInterface
public interface ViewHolderBuilder<A, R> {
    R create(A parent);
}
