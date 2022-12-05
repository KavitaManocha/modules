package com.components.customAlertDialog

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable

fun getConfirmDialog(
    mContext: Context,
    title: String?,
    msg: String?,
    positiveBtnCaption: String?,
    negativeBtnCaption: String?,
    target: DialoggInterface
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
    val imageResource = R.drawable.ic_dialog_alert
    val image: Drawable = mContext.getResources().getDrawable(imageResource)
    builder.setTitle(title).setMessage(msg).setIcon(image).setCancelable(false)
        .setPositiveButton(positiveBtnCaption,
            DialogInterface.OnClickListener { dialog, id -> target.PositiveMethod(dialog, id) })
        .setNegativeButton(negativeBtnCaption,
            DialogInterface.OnClickListener { dialog, id -> target.NegativeMethod(dialog, id) })
    val alert: AlertDialog = builder.create()
    alert.show()
}