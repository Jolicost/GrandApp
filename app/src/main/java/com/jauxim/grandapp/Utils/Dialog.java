package com.jauxim.grandapp.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jauxim.grandapp.R;

/**
 * Created by Xavi on 05/10/2017.
 */

public class Dialog {

    private static android.app.Dialog dialog;

    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;
    private View.OnClickListener negativeOnClickListener;
    private View.OnClickListener positiveOnClickListener;
    private boolean isCancelable = true;
    private int iconResource = -1;
    private DialogInterface.OnDismissListener dismissListener;
    private Context context;
    private static Dialog d;

    public static Dialog createDialog(Context ctx) {
        d = new Dialog();
        d.context = ctx;
        return d;
    }

    public Dialog title(String title) {
        this.title = title;
        return this;
    }

    public Dialog title(int resId) {
        if (context != null) {
            this.title = context.getString(resId);
        }
        return this;
    }

    public Dialog description(String message) {
        this.message = message;
        return this;
    }

    public Dialog description(int message) {
        if (context != null) {
            this.message = context.getString(message);
        }
        return this;
    }

    public Dialog positiveButtonText(String positiveText) {
        this.positiveButtonText = positiveText;
        return this;
    }

    public Dialog negativeButtonText(String negativeText) {
        this.negativeButtonText = negativeText;
        return this;
    }

    public Dialog positiveButtonText(int positiveText) {
        if (context != null) {
            this.positiveButtonText = context.getString(positiveText);
        }
        return this;
    }

    public Dialog negativeButtonText(int negativeText) {
        if (context != null) {
            this.negativeButtonText = context.getString(negativeText);
        }
        return this;
    }

    public Dialog positiveButtonClick(View.OnClickListener listener) {
        this.positiveOnClickListener = listener;
        return this;
    }

    public Dialog negativeButtonClick(View.OnClickListener listener) {
        this.negativeOnClickListener = listener;
        return this;
    }

    public Dialog cancelable(boolean iCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public Dialog iconResource(int id) {
        this.iconResource = id;
        return this;
    }

    public Dialog onDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public void build() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = null;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        dialogView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.tv_dialog_title);
        TextView descriptionView = dialogView.findViewById(R.id.tv_dialog_message);
        AppCompatButton positiveTextView = dialogView.findViewById(R.id.b_dialog_positive);
        AppCompatButton negativeTextView = dialogView.findViewById(R.id.b_dialog_negative);
        ImageView iconImageView = dialogView.findViewById(R.id.i_dialog_icon);

        titleTextView.setText(title);
        if (!title.isEmpty()) titleTextView.setVisibility(View.VISIBLE);
        descriptionView.setText(message);
        descriptionView.setMovementMethod(new ScrollingMovementMethod());

        if (TextUtils.isEmpty(negativeButtonText) && TextUtils.isEmpty(positiveButtonText)){
            negativeButtonText = context.getString(R.string.accept);
        }

        if (!TextUtils.isEmpty(positiveButtonText)) {
            positiveTextView.setText(positiveButtonText);
            positiveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (positiveOnClickListener != null) positiveOnClickListener.onClick(v);
                }
            });
        } else positiveTextView.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(negativeButtonText)) {
            negativeTextView.setText(negativeButtonText);
            negativeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (negativeOnClickListener != null) negativeOnClickListener.onClick(view);
                }
            });
        } else negativeTextView.setVisibility(View.GONE);

        if (iconResource != -1) iconImageView.setImageResource(iconResource);

        builder.setCancelable(isCancelable);
        dialog = builder.create();
        dialog.show();

        if (dismissListener != null) {
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dismissListener.onDismiss(null);
                }
            });
        }
    }
}
