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
import android.widget.ImageView;
import android.widget.TextView;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;

/**
 * Created by пользователь on 23.05.2017.
 */

public class DialogFragmentMessage extends DialogFragment {
    private Activity activity_context;
    private String sourceURL;
    private String messageText;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity_context = getActivity();
        sourceURL = TODOApplication.getDialogImageSourceUrl();
        messageText = TODOApplication.getDialogMessageText();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewMess = inflater.inflate(R.layout.dialog_frgament_message,null);
        viewMess.setBackgroundResource(R.color.primary_color);

        TextView txtView = (TextView) viewMess.findViewById(R.id.dialog_fragment_txtview);
        ImageView imageView = (ImageView) viewMess.findViewById(R.id.dialog_fragment_image);

        if (sourceURL != null && TextUtils.isEmpty(sourceURL)) {
            //imageView.setImageResource(sourceURL);
        }
        if (messageText != null && TextUtils.isEmpty(messageText)) {
            txtView.setText(messageText);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewMess).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        return builder.create();
    }
}
