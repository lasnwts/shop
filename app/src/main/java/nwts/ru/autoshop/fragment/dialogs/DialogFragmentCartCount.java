package nwts.ru.autoshop.fragment.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.ui.ProductDetailView;

/**
 * Created by пользователь on 27.05.2017.
 */

public class DialogFragmentCartCount extends DialogFragment {
    private Activity activity_context;
    DecimalFormat formatData = new DecimalFormat("0.00");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       activity_context = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        activity_context = getActivity();
        final View viewMess = inflater.inflate(R.layout.dialog_fragment_cart_count, null);
        viewMess.setBackgroundResource(R.color.primary_color);
        final NumberPicker numberPicker = (NumberPicker) viewMess.findViewById(R.id.numberPicker);
        final TextView txtSumma = (TextView) viewMess.findViewById(R.id.dialog_fragment_cart_count_txtview_summa);
        numberPicker.setMaxValue(TODOApplication.getDetail_quantity());
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
        txtSumma.setText("Итого : " + formatData.format(numberPicker.getValue() * TODOApplication.getDetail_price()) + " руб.");
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Toast.makeText(getActivity(), "Вы выбрали * 5  = "+numberPicker.getValue() * 5,Toast.LENGTH_SHORT).show();
                txtSumma.setText("Итого : " + formatData.format(numberPicker.getValue() * TODOApplication.getDetail_price()) + " руб.");
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewMess).setPositiveButton(R.string.button_cart_balance, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (numberPicker.getValue() > 0) {
                    ((ProductDetailView) getActivity()).startCartInput(numberPicker.getValue(),TODOApplication.getDetail_product_Id());
                }
            }
        }).setNegativeButton(R.string.button_not_add_balance, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
