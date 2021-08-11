package com.example.stylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//окно списка услуг
public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View root;

    //инициализация списка записей
    static AppointmentAdapter adapter = new AppointmentAdapter();
    RecyclerView appointmentList;
    static {
        AppointmentItem item1 = new AppointmentItem();
        item1.setName("Максим");
        item1.setPhone("89999999999");
        item1.setServices(new String[]{"стрижка"});
        item1.setComment("Тороплюсь, необходимо сделать быстро стрижку");
        item1.setCost("850");
        item1.setDuration("0:30");
        item1.setDatetime("19.07.2021 16:17");
        item1.setHair_length("средние");
        adapter.addItem(item1);

        AppointmentItem item2 = new AppointmentItem();
        item2.setName("Кристина");
        item2.setPhone("89881234567");
        item2.setServices(new String[]{"стрижка","иллюминирование","окрашивание корней","окрашивание корней","кератирование"});
        item2.setComment("Осветлить");
        item2.setCost("2200");
        item2.setDuration("1:30");
        item2.setDatetime("19.07.2021 16:37");
        item2.setHair_length("длинные");
        adapter.addItem(item2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = root.getContext();

        if (adapter != null){
            appointmentList = root.findViewById(R.id.appointment_list);
            if (appointmentList != null){
                appointmentList.setAdapter(adapter);
                appointmentList.setLayoutManager(new LinearLayoutManager(context));

                //Событие нажатия на кнопку добавления записи
                FloatingActionButton add_appointment_button = root.findViewById(R.id.add_appointment_button);
                if (add_appointment_button != null){
                    add_appointment_button.setOnClickListener(v -> startActivity(new Intent(context, AppointmentActivity.class)));
                }
            }
        }
        return root;
    }

    public HomeFragment() { }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}