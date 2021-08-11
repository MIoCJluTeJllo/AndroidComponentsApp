package com.example.stylist;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

//Окно добавления расхода/дохода
public class FinanceAddDialog extends AppCompatDialogFragment {
    Boolean up;
    public FinanceAddDialog(Boolean up){
        this.up = up;//доход или расход
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.finance_add_dialog, null);

        builder.setView(view)
                .setPositiveButton("OK", (dialog, id) -> {
                    //Получаем текст наименования дохода
                    TextInputLayout finance_name_textinput = view.findViewById(R.id.finance_name_textinput);
                    String finance_name = finance_name_textinput.getEditText().getText().toString();
                    //Получаем текст суммы дохода
                    TextInputLayout finance_sum_textinput = view.findViewById(R.id.finance_sum_textinput);
                    String finance_sum = finance_sum_textinput.getEditText().getText().toString();
                    if (!finance_name.isEmpty() && !finance_sum.isEmpty()){
                        //Добавляем доход или расход
                        FinanceFragment.adapter.addItem(new FinanceItem(finance_name, Integer.parseInt(finance_sum), up));
                        //Обновляем истог
                        TextView finance_title = getActivity().findViewById(R.id.finance_title);
                        if (finance_title != null){
                            finance_title.setText(getContext().getString(
                                    R.string.fragment_title)
                                    + " " + FinanceFragment.adapter.getTotal());
                        }
                        //Добавим элемент в список
                    }
                })
                .setNegativeButton("CANCEL", (dialog, id) -> {
                });
        TextView title = view.findViewById(R.id.finance_dialog_title);
        title.setText(up ?
                getString(R.string.income_add_title) :
                getString(R.string.expense_add_title));
        return builder.create();
    }
}
