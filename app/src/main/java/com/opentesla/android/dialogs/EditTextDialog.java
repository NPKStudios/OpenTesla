package com.opentesla.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.opentesla.android.R;
import com.opentesla.android.database.DbTask;

/**
 * Created by Nick on 11/29/2016.
 */

public class EditTextDialog extends DialogFragment {
    private String value;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Label");

// Set up the input
        final EditText input = new EditText(getActivity());
        input.setText(value);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
/*
// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.set_label(input.getText().toString());
                updateTask(task);
                textView.setText(input.getText());
                parent.refreshDrawableState();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        */
        // Create the AlertDialog object and return it
        return builder.create();

    }
}