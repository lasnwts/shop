package nwts.ru.autoshop.fragment.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import nwts.ru.autoshop.R;

/**
 * Created by пользователь on 22.05.2017.
 */

public class DialogFragmentAddBalance extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

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
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_fragment_addbalance, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogFragmentAddBalance.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
