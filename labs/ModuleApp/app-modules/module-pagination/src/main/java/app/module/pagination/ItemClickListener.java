package app.module.pagination;

import androidx.annotation.IdRes;

public interface ItemClickListener<T> {
    /**
     * @param item the item binded
     * @param viewId which view clicked
     * */
    void onItemClick(T item, @IdRes int viewId);
}
