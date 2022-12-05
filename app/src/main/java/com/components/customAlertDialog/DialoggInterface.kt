package com.components.customAlertDialog

import android.content.DialogInterface

interface DialoggInterface {
    fun PositiveMethod(dialog: DialogInterface?, id: Int)
    fun NegativeMethod(dialog: DialogInterface?, id: Int)
}