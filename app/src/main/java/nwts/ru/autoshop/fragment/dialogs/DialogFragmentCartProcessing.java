package nwts.ru.autoshop.fragment.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;

import static nwts.ru.autoshop.R.string.alert;

/**
 * Created by пользователь on 25.05.2017.
 */

public class DialogFragmentCartProcessing extends DialogFragment {

    private Activity activity_context;
    private String sourceURL;
    private String messageText;
    private int itemSpinnerDostavka, itemSpinnerPaying;
    private boolean isPayRestricted = false;

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
        final Spinner spinnerPaying = (Spinner) viewMess.findViewById(R.id.dialog_fragment_cart_processing_paying);
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
                if (spinnerDostavka.getSelectedItemId() == 0) {
                    //if Ar Dtavka = empty
                    if (TextUtils.isEmpty(editTextAddress.getText())) {
                        isPayRestricted = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.show_dialog_title_addr_empty)
                                .setMessage(R.string.show_dialog_addr_remember)
                                .setIcon(R.drawable.remember)
                                .setCancelable(false)
                                .setNegativeButton(R.string.seyhow_dialog_ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertAddr = builder.create();
                        alertAddr.show();
                    }
                }
                if (!isPayRestricted) {
                    //check balans if pay from cabinet
                    if (spinnerPaying.getSelectedItemId() == 0) {
                        if (TODOApplication.getCartSumma() > TODOApplication.getBalSumma()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.sow_dialog_how_titile)
                                    .setMessage(R.string.show_dialog_message_no_money)
                                    .setIcon(R.drawable.piggy_bank)
                                    .setCancelable(false)
                                    .setNegativeButton(R.string.show_dialog_ok,
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alertBalance = builder.create();
                            alertBalance.show();
                        } else {
                            // this is Pay!!!!
                            Toast.makeText(getActivity(),"Заплати",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }).setNegativeButton(R.string.button_not_add_balance, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //negative
                DialogFragmentCartProcessing.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
