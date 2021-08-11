package com.example.stylist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//окно списка расхода/дохода
public class FinanceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View root;
    //Инициализируем список расходов/доходов
    static FinanceAdapter adapter = new FinanceAdapter();
    static {
        adapter.addItem(new FinanceItem("Краски для волос", 15000, false));
        adapter.addItem(new FinanceItem("Клиенты", 6450, true));
    }
    RecyclerView financeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_finance, container, false);
        Context context = root.getContext();

        if (adapter != null){
            financeList = root.findViewById(R.id.finance_list);
            if (financeList != null){
                financeList.setAdapter(adapter);
                financeList.setLayoutManager(new LinearLayoutManager(context));

                updateTotal(context);

                //При нажатии кнопки добавления дохода
                ImageView finance_up_add_button = root.findViewById(R.id.finance_up_add_button);
                if (finance_up_add_button != null){
                    finance_up_add_button.setOnClickListener(v -> {
                        //Открываем окно
                        FinanceAddDialog financeAddDialog = new FinanceAddDialog(true);
                        financeAddDialog.show(getChildFragmentManager(), "finance add dialog");
                    });
                }
                //При нажатии кнопки добавления расхода
                ImageView finance_down_add_button = root.findViewById(R.id.finance_down_add_button);
                if (finance_down_add_button != null){
                    finance_down_add_button.setOnClickListener(v -> {
                        //Открываем окно
                        FinanceAddDialog financeAddDialog = new FinanceAddDialog(false);
                        financeAddDialog.show(getChildFragmentManager(), "finance add dialog");
                    });
                }
            }
        }
        return root;
    }

    //Обновление итога
    void updateTotal(Context context){
        TextView financeTitle = root.findViewById(R.id.finance_title);
        if (financeTitle != null){
            financeTitle.setText(context.getString(
                    R.string.fragment_title)
                    + " " + adapter.getTotal());
        }
    }

    public FinanceFragment() { }

    public static FinanceFragment newInstance(String param1, String param2) {
        FinanceFragment fragment = new FinanceFragment();
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