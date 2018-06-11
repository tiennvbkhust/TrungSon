package com.skynetsoftware.trungson.ui.tabproduct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.CheckHolder> {
    List<Category> list;
    Context context;
    SparseBooleanArray sparseBooleanArray;
    public FilterAdapter(List<Category> list, Context context) {
        this.list = list;
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
        for (int i = 0; i < this.list.size(); i++) {
            sparseBooleanArray.put(i, false);
        }
    }

    @NonNull
    @Override
    public CheckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item_check, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckHolder holder, final int position) {
        holder.cb.setChecked(sparseBooleanArray.get(position));
        holder.cb.setText(list.get(position).getName());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setChecked(isChecked);
                sparseBooleanArray.put(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearCheck() {
        for (int i = 0; i < getItemCount(); i++) {
            list.get(i).setChecked(false);
            sparseBooleanArray.put(i, false);
        }
        notifyDataSetChanged();
    }

    class CheckHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb)
        CheckBox cb;

        public CheckHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
