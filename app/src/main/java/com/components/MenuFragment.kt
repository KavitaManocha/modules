package com.components

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.components.customAlertDialog.CustomViewDialogActivity
import com.components.databinding.FragmentMenuBinding
import com.components.inAppUpdate.InAppUpdateActivity

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_menu, container, false)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvQrScan.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_QRScanningFragment)
        }

        binding.tvInAppUpdate.setOnClickListener {
            val intent = Intent(requireContext(),InAppUpdateActivity::class.java)
            startActivity(intent)
        }

        binding.tvCustomAlertDialog.setOnClickListener {
            val intent = Intent(requireContext(),CustomViewDialogActivity::class.java)
            startActivity(intent)
        }

        return root
    }

}