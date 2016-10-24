package com.blues.money_saver.UI;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blues.money_saver.AdviewActivity;
import com.blues.money_saver.Rest.OverviewUpdateIntent;
import com.blues.money_saver.R;
import com.blues.money_saver.Rest.Utility;
import com.blues.money_saver.data.MoneyContract.MoneyEntry;

import java.util.Calendar;
import java.util.Vector;

/**
 * Created by Blues on 04/09/2016.
 */
public class CreateNewMoneyFragment extends Fragment {
    static private Button insertbtn;
    private String LOG_TAG = this.getTag();
    private int mYear, mMonth, mDay, newYear,newMonth,newDay;
    private String categoryStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        final EditText editAmount;
        final EditText editDate;
        final EditText editDetails;
        final Spinner spinnerCategory;

        final Calendar c;
        final DatePickerDialog datePickerDialog;
        final String[] categoryarr;

        rootView = inflater.inflate(R.layout.fragment_create_new_money, container, false);
        editAmount = (EditText) rootView.findViewById(R.id.edit_new_amount);
        editDate = (EditText) rootView.findViewById(R.id.edit_new_date);
        editDetails = (EditText) rootView.findViewById(R.id.edit_new_details);
        spinnerCategory = (Spinner) rootView.findViewById(R.id.CategorySpinner);

        //editCategory = (EditText) rootView.findViewById(R.id.edit_new_category);

        //editCategory.t
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editDate.setText(mDay+"-"+ Utility.monthConvert(mMonth)+"-"+mYear);
        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int date)
                    {
                        editDate.setText(date+"-"+Utility.monthConvert(month)+"-"+year);
                        mDay = date;
                        mMonth = month;
                        mYear = year;
                        editDetails.requestFocus();
                    }
                }, mYear, mMonth, mDay);


        editDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                datePickerDialog.show();
            }
        });


        categoryarr = getResources().getStringArray(R.array.categoryarray);
        spinnerCategory.setPrompt(getString(R.string.category_spin_title));
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, categoryarr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String[] categoryarr = getResources().getStringArray(R.array.categoryarray);
                categoryStr = categoryarr[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






//        MovieReview = new FetchMovieReview(rootView);
//        Bundle arguments = getArguments();

        //ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image);
        insertbtn = (Button) rootView.findViewById(R.id.btn_new_insert);
        insertbtn.setContentDescription(getString(R.string.a11y_insert_btn));
        insertbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(!editAmount.getText().toString().matches(""))
                insertNewTransaction(editAmount.getText().toString(),
                        mYear+"",Utility.monthConvert(mMonth),mDay+"",
                        categoryStr,
                        editDetails.getText().toString());
                else
                    Toast.makeText(getContext(),getString(R.string.invalid_type),Toast.LENGTH_SHORT).show();
            }
        });

        if(getResources().getString(R.string.flavors) == getResources().getString(R.string.freeflavors))
        {
            AdviewActivity adviewActivity = new AdviewActivity(rootView,getActivity());
            adviewActivity.showad();
        }
return rootView;
    }

    public void insertNewTransaction(String amount, String year, String month, String date, String category, String details)
    {
        Uri inserteduri;
        Vector<ContentValues> cVVector = new Vector<ContentValues>(1);
        ContentValues moneyValues = new ContentValues();
        moneyValues.put(MoneyEntry.COLUMN_MONEY_ID,"TEST");
        moneyValues.put(MoneyEntry.COLUMN_MONEY_DATE_Year,year);
        moneyValues.put(MoneyEntry.COLUMN_MONEY_DATE_Month,month);
        moneyValues.put(MoneyEntry.COLUMN_MONEY_DATE_Date,date);
        moneyValues.put(MoneyEntry.COLUMN_MONEY_AMOUNT,amount);
        moneyValues.put(MoneyEntry.COLUMN_MONEY_CATEGORY, category);
        moneyValues.put(MoneyEntry.COLUMN_MONEY_DETAILS , details);
        moneyValues.put(MoneyEntry.COLUMN_MONEY_CHANGE_ABLE,"Y");
        cVVector.add(moneyValues);
        int inserted = 0;

        inserteduri = getContext().getContentResolver().insert(MoneyEntry.CONTENT_URI,moneyValues);
        if(ContentUris.parseId(inserteduri) != -1) {
            Intent updateintent = new Intent(getActivity(),OverviewUpdateIntent.class);
            updateintent.putExtra("monthFragment",month);
            getActivity().startService(updateintent);
            Toast.makeText(getContext(),getString(R.string.toast_done),Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        else {
            Toast.makeText(getContext(),getString(R.string.toast_error),Toast.LENGTH_SHORT).show();
        }
    }

}
