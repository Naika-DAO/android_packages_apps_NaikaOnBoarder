package org.lineageos.setupwizard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.lineageos.setupwizard.R;

import java.util.ArrayList;

public class LocaleView extends RecyclerView {

    private ArrayList<LocaleItem> items = new ArrayList();
    private LocaleCallback callback = null;

    public LocaleView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LocaleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LocaleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        setAdapter(new Adapter());
        setLayoutManager(new LinearLayoutManager(context));
    }

    public void setItems(String[] values) {
        items.clear();
        for (String value : values) {
            items.add(new LocaleItem(value));
        }
        if (getAdapter() != null) getAdapter().notifyDataSetChanged();
        else setAdapter(new Adapter());
    }

    public void setCallback(LocaleCallback callback) {
        this.callback = callback;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView localeTitle;
        LinearLayout localeItem;
        View divider;

        ViewHolder(View itemView) {
            super(itemView);
            localeTitle = itemView.findViewById(R.id.locale);
            localeItem = itemView.findViewById(R.id.localeItem);
            divider = itemView.findViewById(R.id.divider);
        }

    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.locale_picker_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LocaleItem item = items.get(position);
            holder.localeTitle.setText(item.localeName);
            holder.localeItem.setOnClickListener(v -> callback.onLocaleChanged(position));
            holder.divider.setVisibility(position == items.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public static class LocaleItem {
        
        LocaleItem(String localeName) {
            this.localeName = localeName;
        }
        String localeName;
    }

    public interface LocaleCallback {
        void onLocaleChanged(int value);
    }
}
