package com.example.stylist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//класс для списка записей
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<AppointmentItem> appoinmentList;

    public void addItem(AppointmentItem appoinment){
        appoinmentList.add(appoinment);
        notifyDataSetChanged();
    }

    public void changeItem(int position, AppointmentItem appoinment){
        appoinmentList.set(position, appoinment);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        appoinmentList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, appoinmentList.size());
    }

    public AppointmentItem getItem(int position) {
        return appoinmentList.get(position);
    }

    public AppointmentAdapter() {
        this.appoinmentList = new ArrayList<AppointmentItem>();
    }

    //инициализация полей записи
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView name, phone, comment, services;
        private final ImageView menu_button;

        public ViewHolder(View view) {
            super(view);
            this.icon = view.findViewById(R.id.appointment_item_icon);
            this.name = view.findViewById(R.id.appointment_item_name);
            this.phone = view.findViewById(R.id.appointment_item_phone);
            this.comment = view.findViewById(R.id.appointment_item_comment);
            this.services = view.findViewById(R.id.appointment_item_services);

            this.menu_button = view.findViewById(R.id.appointment_item_menu_button);
            this.menu_button.setImageResource(R.drawable.ic_baseline_more_vert_24);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создание новой записи
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new ViewHolder(view);
    }

    //функция отображения записи
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AppointmentItem appointment = appoinmentList.get(position);
        viewHolder.name.setText(appointment.getName());
        viewHolder.phone.setText(appointment.getPhone());
        viewHolder.comment.setText(appointment.getComment());
        //выводим все услуги записи через нумерацию (1. ___ и т.д)
        List<String> servicesList = appointment.getServices();
        String services = "";
        for (int i = 0; i < appointment.getServices().toArray().length; i++){
            services += (i + 1) + ". " + servicesList.get(i) + "\n";
        }
        viewHolder.services.setText(services);
        viewHolder.icon.setImageResource(R.drawable.ic_baseline_account_circle_24);
        //меню удаления изменения и просмотра
        viewHolder.menu_button.setOnClickListener(v ->{
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            menu.inflate(R.menu.appointment_menu);
            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.view_appointment_item:
                        Intent intent = new Intent(v.getContext(), AppointmentActivity.class);
                        intent.putExtra("position", position);
                        v.getContext().startActivity(intent);
                        return true;
                    case R.id.remove_appointment_item:
                        showRemoveDialog(this, v, position);
                        return true;
                    default:
                        return false;
                }
            });
            menu.show();
        });
    }

    @Override
    public int getItemCount() {
        return appoinmentList.size();
    }

    //Окно при удалении записи
    public static class RemoveAlert extends DialogFragment {
        Integer position;
        AppointmentAdapter adapter;
        public RemoveAlert(Integer position, AppointmentAdapter adapter){
            this.position = position;
            this.adapter = adapter;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Удалить запись?")
                    .setPositiveButton("OK", (dialog, id) -> adapter.removeItem(position))
                    .setNegativeButton("CANCEL", (dialog, id) -> {});
            return builder.create();
        }
    }
    public void showRemoveDialog(AppointmentAdapter adapter, View view, Integer position) {
        RemoveAlert alert = new RemoveAlert(position, adapter);
        alert.show(((FragmentActivity)view.getContext()).getSupportFragmentManager(), "removeAlert");
    }
}

