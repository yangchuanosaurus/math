package app.module.pagination;

public interface ItemClickListener<T> {
    /**
     * @param item the item binded
     * @param actionId which action triggered
     * */
    void onItemActionClick(T item, int actionId);
}
