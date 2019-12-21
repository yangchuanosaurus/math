package app.module.pagination;

public interface ItemClickListener<T> {
    /**
     * @param itemPosition position of the item
     * @param item the item binded
     * @param actionId which action triggered
     * */
    void onItemActionClick(int itemPosition, T item, int actionId);
}
