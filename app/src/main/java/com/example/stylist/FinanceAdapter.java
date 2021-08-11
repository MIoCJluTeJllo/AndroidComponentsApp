package com.example.stylist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//класс списка расходов/доходов
public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder> {
    private List<FinanceItem> financeList;

    public FinanceAdapter(){
        financeList = new ArrayList<FinanceItem>();
    }

    public void addItem(FinanceItem finance){
        financeList.add(finance);
        notifyDataSetChanged();
    }

    //подсчет итога
    public int getTotal(){
        Integer total = 0;
        for (FinanceItem financeItem : financeList){
            total += financeItem.getUp() ? financeItem.getSum() : -financeItem.getSum();
        }
        return total;
    }

    //инициализация полей элемента списка расхода/дохода
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, sum;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            this.icon = view.findViewById(R.id.finance_item_icon);
            this.name = view.findViewById(R.id.finance_item_name);
            this.sum = view.findViewById(R.id.finance_item_sum);
        }
    }

    @Override
    public FinanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finance_item, parent, false);
        return new FinanceAdapter.ViewHolder(view);
    }

    //формат полей элемента списка расхода/дохода
    @Override
    public void onBindViewHolder(FinanceAdapter.ViewHolder holder, int position) {
        FinanceItem finance = financeList.get(position);
        holder.name.setText(finance.getName());
        if (finance.getUp()){
            holder.sum.setText("+ " + finance.getSum());
            holder.sum.setTextColor(Color.GREEN);
            holder.icon.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
        } else {
            holder.sum.setText("- " + finance.getSum());
            holder.sum.setTextColor(Color.RED);
            holder.icon.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
        }
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }
}
