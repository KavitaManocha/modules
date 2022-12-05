package com.components.qrcodeScanner

import android.app.SearchManager
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.components.R
import com.components.databinding.FragmentQRScanningBinding
import com.google.zxing.integration.android.IntentIntegrator

class QRScanningFragment : Fragment() {

    private var _binding: FragmentQRScanningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQRScanningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.ivQrscan.setOnClickListener {
            val intentIntegrator =
                IntentIntegrator.forSupportFragment(this@QRScanningFragment)//IntentIntegrator(requireActivity())
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
//            intentIntegrator.initiateScan()
            activityResultLauncher.launch(intentIntegrator.createScanIntent())
        }

        return root
    }


    var activityResultLauncher  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data

        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null) {

            // for custom alertdialog
//            val builder = androidx.appcompat.app.AlertDialog.Builder(
//                requireActivity(),
//                androidx.transition.R.style.AlertDialog_AppCompat
//            )
//                .create()
//
//            val view = layoutInflater.inflate(R.layout.dialog_display_details, null)
//            var scan_result = view.findViewById<TextView>(R.id.tv_scan_result)
//            var back = view.findViewById<Button>(R.id.back)
//            builder.setView(view)
//            scan_result.text = result.contents
//            back.setOnClickListener {
//                builder.dismiss()
//            }
//            builder.setCanceledOnTouchOutside(true)
//            builder.show()


            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setMessage("Would you like to go to ${result.contents}?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
                    intent.putExtra(SearchManager.QUERY,result.contents)
                    startActivity(intent)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->  })
                .create()
                .show()

        }
    }
}