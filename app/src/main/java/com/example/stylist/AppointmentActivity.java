package com.example.stylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//класс для добавления записи
public class AppointmentActivity extends AppCompatActivity {
    //текущая запись после нажатия на его меню
    AppointmentItem current;

    //Все формы добавления записи
    public static TextInputLayout appointment_time_textinput, appointment_date_textinput;
    TextInputLayout appointment_name_textinput, appointment_phone_textinput, appointment_comment_textinput, appointment_cost_textinput;
    RadioGroup appointment_duration_radioGroup, appointment_hair_length_radioGroup;
    CheckBox [] checkBoxes;

    //инициализация всех полей отображения записи
    private void init(){
        appointment_time_textinput = findViewById(R.id.appointment_time_textinput);
        appointment_date_textinput = findViewById(R.id.appointment_date_textinput);
        appointment_name_textinput = findViewById(R.id.appointment_name_textinput);
        appointment_phone_textinput = findViewById(R.id.appointment_phone_textinput);
        appointment_comment_textinput = findViewById(R.id.appointment_comment_textinput);
        appointment_cost_textinput = findViewById(R.id.appointment_cost_textinput);
        appointment_duration_radioGroup = findViewById(R.id.appointment_duration_radioGroup);
        appointment_hair_length_radioGroup = findViewById(R.id.appointment_hair_length_radioGroup);
        checkBoxes = new CheckBox[]{
                findViewById(R.id.roots_coloring_checkbox),
                findViewById(R.id.haircut_checkbox),
                findViewById(R.id.coloring_checkbox),
                findViewById(R.id.tinting_checkbox),
                findViewById(R.id.mask_checkbox),
                findViewById(R.id.keratin_checkbox),
                findViewById(R.id.illumination_checkbox),
                findViewById(R.id.ampoule_checkbox)
        };
    }

    //проставляем все чекбоксы, которые соответсвуют списку строк
    void setCheckBoxes(CheckBox[] checkBoxes, List<String> values){
        for (CheckBox checkBox : checkBoxes){
            checkBox.setChecked(false);
            for (String value : values){
                if (checkBox.getText().equals(value)){
                    checkBox.setChecked(true);
                    break;
                }
            }
        }
    }

    //Проставляем радио батон по строковому значению
    void setRadioButton(RadioGroup rg, String value){
        for (int i = 0; i < rg.getChildCount(); i++){
            RadioButton radioButton = (RadioButton)rg.getChildAt(i);
            if (radioButton.getText().equals(value)){
                radioButton.setChecked(true);
            } else {
                radioButton.setChecked(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        init();

        //При нажатии кнопки возврата
        ImageView appointment_backoff_button = findViewById(R.id.appointment_backoff_button);
        if (appointment_backoff_button != null){
            appointment_backoff_button.setOnClickListener(v -> {
                onBackPressed();
            });
        }

        //Пробуем получить позицию выбранной записи и
        //если она существует
        int position = getIntent().getIntExtra("position", -1);
        current = null;
        if (position != -1){
            current = HomeFragment.adapter.getItem(position);
            if (current != null){
                //сохраняем все его поля для дальнейшего изменения
                String[] datetime = current.getDatetime().split(" ");
                if (datetime.length == 2){
                    appointment_date_textinput.getEditText().setText(datetime[0]);
                    appointment_time_textinput.getEditText().setText(datetime[1]);
                }

                appointment_name_textinput.getEditText().setText(current.getName());
                appointment_phone_textinput.getEditText().setText(current.getPhone());
                appointment_comment_textinput.getEditText().setText(current.getComment());
                appointment_cost_textinput.getEditText().setText(current.getCost());

                setRadioButton(appointment_duration_radioGroup, current.getDuration());
                setRadioButton(appointment_hair_length_radioGroup, current.getHair_length());

                setCheckBoxes(checkBoxes, current.getServices());
            }
        }

        //Событие при клике на поле выбора времени
        appointment_time_textinput.getEditText().setOnClickListener(
                v -> showTimePickerDialog(v)
        );
        //Событие при клике на поле выбора даты
        appointment_date_textinput.getEditText().setOnClickListener(
                v -> showDatePickerDialog(v)
        );
        //Событие при клике на кнопку сохранения
        Button appointment_add_button = findViewById(R.id.appointment_add_button);
        appointment_add_button.setOnClickListener(v -> {
            Map<String, String> items =  new HashMap<>();
            //получаем все формы заполнения записи и их значения
            String date = appointment_date_textinput.getEditText().getText().toString();
            String time = appointment_time_textinput.getEditText().getText().toString();
            items.put("datetime", date + " " + time);

            String name = appointment_name_textinput.getEditText().getText().toString();
            items.put("name", name);
            String phone = appointment_phone_textinput.getEditText().getText().toString();
            items.put("phone", phone);
            String comment = appointment_comment_textinput.getEditText().getText().toString();
            items.put("comment", comment);
            String cost = appointment_cost_textinput.getEditText().getText().toString();
            items.put("cost", cost);

            RadioButton appointment_duration_radioButton = findViewById(appointment_duration_radioGroup.getCheckedRadioButtonId());
            String duration = appointment_duration_radioButton.getText().toString();
            items.put("duration", duration);
            RadioButton appointment_hair_length_radioButton = findViewById(appointment_hair_length_radioGroup.getCheckedRadioButtonId());
            String hair_length = appointment_hair_length_radioButton.getText().toString();
            items.put("hair_length", hair_length);

            //значения выбранных услуг
            List<String> services = new ArrayList<String>();
            for (CheckBox checkBox : checkBoxes){
                if (checkBox.isChecked()){
                    services.add(checkBox.getText().toString());
                }
            }

            if (items.values().contains(null) || items.values().contains("") || services.size() == 0){
            }
            else {
                //записываем все поля записи в объект класса запись
                AppointmentItem appoinment = new AppointmentItem();
                appoinment.setDatetime(items.get("datetime"));
                appoinment.setName(items.get("name"));
                appoinment.setPhone(items.get("phone"));
                appoinment.setDuration(items.get("duration"));
                appoinment.setServices(services);
                appoinment.setHair_length(items.get("hair_length"));
                appoinment.setComment(items.get("comment"));
                appoinment.setCost(items.get("cost"));
                //если не была выбрана запись
                if (current == null){
                    //значит добавлем новую
                    HomeFragment.adapter.addItem(appoinment);
                } else {
                    //иначе меняем выбранную
                    HomeFragment.adapter.changeItem(position, appoinment);
                }
                onBackPressed();
            }
        });
    }

    //Окно выбора времени
    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            //при выборе времени заносим в виде строки
            if (appointment_time_textinput != null){
                appointment_time_textinput.getEditText().setText(
                        (hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute));
            }
        }
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    //Окно выбора даты
    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
            //при выборе даты заносим в виде строки
            if (appointment_date_textinput != null){
                appointment_date_textinput.getEditText().setText(
                        (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "." + (month < 10 ? "0" + month : month) + "." + year);
            }
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}