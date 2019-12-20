package app.module.pagination;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.Map;

/**
 * load more & load more retry have embed inside the {@link ViewHolderFactory}
 * provides the embed view holders, also provide
 * {@link ViewHolderFactory#register(int, int, ViewHolderBuilder)} to
 * register a external view holder
 *
 * */
public class ViewHolderFactory {

    public static class LoadMoreViewHolder extends PaginationViewHolder {
        public final static int VIEW_TYPE = Integer.MAX_VALUE;

        LoadMoreViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
        }
    }

    public static class LoadMoreRetryViewHolder extends PaginationViewHolder {
        public static final int VIEW_TYPE = Integer.MAX_VALUE - 1;

        LoadMoreRetryViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
        }
    }

    private Map<Integer, ViewHolderBuilder<View, PaginationViewHolder>> mRegistry;
    private Map<Integer, Integer> mRegistryLayout;

    ViewHolderFactory() {
        mRegistry = new ArrayMap<>();
        mRegistryLayout = new ArrayMap<>();

        mRegistry.put(LoadMoreViewHolder.VIEW_TYPE, LoadMoreViewHolder::new);
        mRegistryLayout.put(LoadMoreViewHolder.VIEW_TYPE, R.layout.view_pagination_load_more);

        mRegistry.put(LoadMoreRetryViewHolder.VIEW_TYPE, LoadMoreRetryViewHolder::new);
        mRegistryLayout.put(LoadMoreRetryViewHolder.VIEW_TYPE, R.layout.view_pagination_load_more_retry);
    }

    /**
     * register a view holder
     * @param viewType of the view
     * @param layout of the view
     * @param viewHolderBuilder of the ViewHolder
     * */
    void register(int viewType,
                  int layout,
                  ViewHolderBuilder<View, PaginationViewHolder> viewHolderBuilder) {
        mRegistry.put(viewType, viewHolderBuilder);
        mRegistryLayout.put(viewType, layout);
    }

    @NonNull
    PaginationViewHolder createViewHolder(PaginationAdapter adapter,
                                          int viewType,
                                          @NonNull ViewGroup parent) {
        if (LoadMoreRetryViewHolder.VIEW_TYPE == viewType) {
            // support load more retry
            return createLoadMoreRetryViewHolder(
                    adapter.getLoadMoreRetryListener(), viewType, parent);
        } else {
            return createLoadMoreRetryViewHolder(null, viewType, parent);
        }
    }

    @NonNull
    private PaginationViewHolder createLoadMoreRetryViewHolder(LoadMoreRetryListener listener,
                                                               int viewType,
                                                               @NonNull ViewGroup parent) {
        int layout = mRegistryLayout.get(viewType);
        View view = createView(parent, layout);
        if (null != listener) {
            View retryView = view.findViewById(R.id.pagination_load_more_retry);
            if (null != retryView) {
                retryView.setOnClickListener(v -> {
                    listener.onLoadMoreRetry();
                });
            }
        }
        return mRegistry.get(viewType).create(view);
    }

    @NonNull
    PaginationViewHolder createGridViewHolder(PaginationAdapter adapter,
                                              int viewType,
                                              int measuredSize,
                                              @NonNull ViewGroup parent) {
        int layout = mRegistryLayout.get(viewType);
        View view = createView(parent, layout);
        view.setLayoutParams(new ViewGroup.LayoutParams(measuredSize, measuredSize));
        PaginationViewHolder paginationViewHolder = mRegistry.get(viewType).create(view);

        if (null != adapter.getItemClickListener()) {
            paginationViewHolder.setItemClickListener(adapter.getItemClickListener());
        }
        return paginationViewHolder;
    }

    private View createView(@NonNull ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
    }
}
