package nwts.ru.autoshop.fragment.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import nwts.ru.autoshop.R;

/**
 * Created by пользователь on 22.05.2017.
 */

public class DialogFragmentAddBalance extends DialogFragment {

    DialogPositiveClick mDialogPositiveClick;
    private Activity activity_context;
    private String paySystems;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity_context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewMessEdit = inflater.inflate(R.layout.dialog_fragment_addbalance,null);
        viewMessEdit.setBackgroundResource(R.color.primary_color);
        RadioGroup radioGroup = (RadioGroup) viewMessEdit.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case  -1:
                        paySystems = "Не определно";
                        break;
                    case R.id.radio_webmoney:
                        paySystems = "Web Money";
                        break;
                    case R.id.radio_qiwi:
                        paySystems = "Qiwi";
                        break;
                    case R.id.radio_yandex:
                        paySystems = "Яндекс.Деньги";
                        break;
                    case R.id.radio_paypal:
                        paySystems = "PayPal";
                        break;
                    default:
                        paySystems = "Не определно";
                        break;
                }
            }
        });





//        final String[] payNamesArray = {"WebMoney", "Paypal", "QIWI", "Яндекс. Деньги"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Выберите платежную систему:")
//                // добавляем переключатели
//                .setSingleChoiceItems(payNamesArray, -1,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int item) {
//                                Toast.makeText(
//                                        getActivity(),
//                                        "Любимое имя кота: "
//                                                + payNamesArray[item],
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User clicked OK, so save the mSelectedItems results somewhere
//                        // or return them to the component that opened the dialog
//
//                    }
//                })
//                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//
//        return builder.create();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
  //      LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //null - viewgroup = parent
     //   final View viewMessEdit = inflater.inflate(R.layout.dialog_fragment_addbalance,null);
        viewMessEdit.setBackgroundResource(R.color.primary_color);
        builder.setView(viewMessEdit)
                // Add action buttons
                .setPositiveButton(R.string.button_add_balance, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        EditText editText = (EditText) viewMessEdit.findViewById(R.id.summa_pay_add);

                        if (TextUtils.isEmpty(editText.getText())) {
                            Toast.makeText(getActivity(), "Необходимол ввести суииу для перевода :(",Toast.LENGTH_SHORT).show();
                        } else {
                            if (mDialogPositiveClick == null) {
                                mDialogPositiveClick = (DialogPositiveClick) activity_context;
                            }
                            mDialogPositiveClick.getMoney(editText.getText().toString(), paySystems);
                        }
                    }
                })
                .setNegativeButton(R.string.button_not_add_balance, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogFragmentAddBalance.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }

    public interface DialogPositiveClick{
        void getMoney(String money, String pay);
    }
}
