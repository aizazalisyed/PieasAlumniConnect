package java.com.alumnimanagmentsystem.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.com.alumnimanagmentsystem.R;

public class EditUserInfoDialog extends AppCompatDialogFragment {

    EditText editTextPhoneNumber;
    EditText editTextCountry;
    EditUserInfoDialogListener editUserInfoDialogListener;
    CountryCodePicker countryCodePicker;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_personal_info_dialog, null);
        countryCodePicker = view.findViewById(R.id.cc);

        editTextPhoneNumber = view.findViewById(R.id.editPhoneNumber);
        editTextCountry = view.findViewById(R.id.editCountry);

        editTextPhoneNumber.setHint("Phone Number");

        builder.setView(view)
                .setTitle("Edit Information")
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber =  countryCodePicker.getFullNumberWithPlus();
                        String country = editTextCountry.getText().toString();
                        editUserInfoDialogListener.applyTexts(phoneNumber,country);
                    }
                });

        countryCodePicker.registerPhoneNumberTextView(editTextPhoneNumber);
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                editTextCountry.setText(selectedCountry.getName());
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            editUserInfoDialogListener = (EditUserInfoDialogListener) context;
        }
        catch (ClassCastException e){

        }

    }

    public interface EditUserInfoDialogListener{
        void applyTexts(String phoneNumber, String country);
    }
}
