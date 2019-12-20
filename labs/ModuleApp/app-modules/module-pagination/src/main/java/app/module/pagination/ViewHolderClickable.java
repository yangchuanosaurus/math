package app.module.pagination;

/*package*/interface ViewHolderClickable<T> {
    boolean isClickable();
    ItemClickListener<T> getItemClickListener();
}
