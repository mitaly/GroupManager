package com.techeducation.groupmanager;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//DialogFragment IS-A Fragment
public class FirstFragment extends Fragment{
    Button btnChooseDate,btnChooseTime;
    TextView txtDate,txtTime;
    EditText etxtTitle, etxtVenue, etxtDesc;
    Button btnpost;
    String evntTitle, evntVenue, evntDesc, evntDate, evntTime;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_first, container, false);


        txtDate=(TextView)view.findViewById(R.id.txtDate);
        btnChooseDate=(Button)view.findViewById(R.id.btnChooseDate);
        btnChooseTime=(Button)view.findViewById(R.id.btnChooseTime);
        txtTime=(TextView)view.findViewById(R.id.txtTime);
        etxtTitle = (EditText)view.findViewById(R.id.evntName);
        etxtVenue = (EditText)view.findViewById(R.id.evntVenue);
        etxtDesc = (EditText)view.findViewById(R.id.evntDesc);
        btnpost = (Button)view.findViewById(R.id.btnPost);

        initTimeAndDate();

        btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evntTitle = etxtTitle.getText().toString().trim();
                evntVenue = etxtVenue.getText().toString().trim();
                evntDesc = etxtDesc.getText().toString().trim();
                evntDate = txtDate.getText().toString().trim();
                evntTime = txtTime.getText().toString().trim();

            }
        });
        return view;
    }

    void  initTimeAndDate(){
        Calendar c =Calendar.getInstance();
        txtTime.setText(String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE)));
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        txtDate.setText(formattedDate);
    }

    private  void showTimePicker(){
        TimePickerFragment time = new TimePickerFragment();
        Calendar c = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour",c.get(Calendar.HOUR_OF_DAY));
        args.putInt("min",c.get(Calendar.MINUTE));
        time.setArguments(args);
        time.setCallBack(ontime);
        time.show(getFragmentManager(),"Time Picker");
    }

    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            txtTime.setText(String.format("%02d:%02d",hourOfDay,minute));
        }
    };


    public static class TimePickerFragment extends DialogFragment{

        TimePickerDialog.OnTimeSetListener onTimeSet;
        void setCallBack(TimePickerDialog.OnTimeSetListener onTime){
            onTimeSet = onTime;
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(),onTimeSet,hour,min,true);
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        Calendar c = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year",c.get(Calendar.YEAR));
        args.putInt("month", c.get(Calendar.MONTH));
        args.putInt("day", c.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(c.getTime());
            txtDate.setText(formattedDate);
        }
    };

    public static class DatePickerFragment extends DialogFragment {
        DatePickerDialog.OnDateSetListener ondateSet;
        private int year, month, day;

        public DatePickerFragment() {}

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
            ondateSet = ondate;
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        }
    }
}
