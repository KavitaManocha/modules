package com.components.customAlertDialog

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.components.R
import com.components.databinding.ActivityCustomViewDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomViewDialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomViewDialogBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomViewDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        binding.viewDialog.setOnClickListener {
            getConfirmDialog(
                this,
                getString(R.string.sure_leave),  // other value checked for is sure_delete
                "",
                getString(R.string.yes), // delete
                getString(R.string.no),  // cancel
                object: DialoggInterface{
                    override fun PositiveMethod(dialog: DialogInterface?, id: Int) {
                        Toast.makeText(this@CustomViewDialogActivity,getString(R.string.yes), Toast.LENGTH_SHORT).show()
                    }

                    override fun NegativeMethod(dialog: DialogInterface?, id: Int) {
                        Toast.makeText(this@CustomViewDialogActivity,getString(R.string.no), Toast.LENGTH_SHORT).show()

                    }

                }
            )
        }

    }
}