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
import android.widget.EditText;
import android.widget.Spinner;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.setting.PreferenceHelper;

/**
 * Created by пользователь on 01.06.2017.
 */

public class DialogFragmentAddComment extends DialogFragment {

    private Activity activity_context;
    private int rating;
    private String messageText;
    private Spinner mSpinner;
    private EditText mEditText;
    private isDialogFragmentAddComment mIsDialogFragmentAddComment;
    PreferenceHelper preferenceHelper;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity_context = getActivity();
        preferenceHelper = PreferenceHelper.getInstance();
        //preferenceHelper.getUserId()  //UserID
        //TODOApplication.getDetail_product_Id() -- Product_ID

        //  activity_context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewMess = inflater.inflate(R.layout.dialog_fragment_add_comment, null);
        viewMess.setBackgroundResource(R.color.primary_color);
        mSpinner = (Spinner) viewMess.findViewById(R.id.dialog_fragment_add_comment_spinner);
        mSpinner.setSelection(0);
        mEditText = (EditText) viewMess.findViewById(R.id.dialog_fragment_add_comment_ediutext);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewMess).setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mIsDialogFragmentAddComment== null) {
                    mIsDialogFragmentAddComment = (isDialogFragmentAddComment) activity_context;
                }
                if (mEditText.getText() == null || TextUtils.isEmpty(mEditText.getText())){
                    messageText = " без сообщения..";
                } else {
                    messageText = mEditText.getText().toString().trim();
                }
                mIsDialogFragmentAddComment.startCartProcessing((int) mSpinner.getSelectedItemId(), messageText );
                //   mIsDialogFragmentAddComment(editText.getText().toString(), paySystems);
            }
        }).setNegativeButton("Отказаться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();

    }

    public interface isDialogFragmentAddComment {
        void startCartProcessing(int rating, String messageComment);
    }

}
