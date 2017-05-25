package nwts.ru.autoshop.fragment.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;

/**
 * Created by пользователь on 25.05.2017.
 */

public class DialogFragmentCartProcessing extends DialogFragment {

    private Activity activity_context;
    private String sourceURL;
    private String messageText;
    private int itemSpinnerDostavka, itemSpinnerPaying;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity_context = getActivity();
        sourceURL = TODOApplication.getDialogImageSourceUrl();
        messageText = TODOApplication.getDialogMessageText();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewMess = inflater.inflate(R.layout.dialog_fragment_cart_processing, null);
        viewMess.setBackgroundResource(R.color.primary_color);

        TextView txtView = (TextView) viewMess.findViewById(R.id.dialog_fragment_cart_processing_txtview_title);
        final TextView txtViewAddrTitle = (TextView) viewMess.findViewById(R.id.dialog_fragment_cart_processing_address);
        ImageView imageView = (ImageView) viewMess.findViewById(R.id.dialog_fragment_cart_processing__image);
        final Spinner spinnerDostavka = (Spinner) viewMess.findViewById(R.id.dialog_fragment_cart_processing_dostavka);
        spinnerDostavka.setSelection(0);
        final RadioGroup radioGroup = (RadioGroup) viewMess.findViewById(R.id.dialog_fragment_cart_processing_radiogruop);
        radioGroup.clearCheck();
        Spinner spinnerPaying = (Spinner) viewMess.findViewById(R.id.dialog_fragment_cart_processing_paying);
        final EditText editTextAddress = (EditText) viewMess.findViewById(R.id.dialog_fragment_cart_processing_editTextMulti);

        //Spinner listener
        spinnerDostavka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSpinnerDostavka = spinnerDostavka.getSelectedItemPosition();
                if (itemSpinnerDostavka == 0) {
                    editTextAddress.setVisibility(View.VISIBLE);
                    txtViewAddrTitle.setVisibility(View.VISIBLE);
                    txtViewAddrTitle.setText(R.string.cart_processing_title_input_address);
                } else {
                    editTextAddress.setVisibility(View.GONE);
                }
                if (itemSpinnerDostavka == 1) {
                    radioGroup.setVisibility(View.VISIBLE);
                    txtViewAddrTitle.setText(R.string.cart_processing_choice_point);
                } else {
                    radioGroup.setVisibility(View.GONE);
                }
                //String size = spinner.getSelectedItem().toString();
                /*
                    String[] size_values = getResources().getStringArray(R.array.size_values);
                    int size = Integer.valueOf(size_values[spinner_pos]);
                 */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewMess).setPositiveButton(R.string.button_cart_balance, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //positive
                }

            })
                .setNegativeButton(R.string.button_not_add_balance, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //negative
                        DialogFragmentCartProcessing.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
