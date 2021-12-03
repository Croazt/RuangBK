package com.example.proyekakhirpsi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhirpsi.R
import com.example.proyekakhirpsi.RiwayatActivity
import com.example.proyekakhirpsi.TambahJanjiActivity
import com.example.proyekakhirpsi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val addJanji = root.findViewById<Button>(R.id.btntambahjanji)
        addJanji.setOnClickListener {
            startActivity(Intent(context, TambahJanjiActivity::class.java ))
        }
        val riwayat = root.findViewById<Button>(R.id.btnriwayat)
        riwayat.setOnClickListener {
            startActivity(Intent(context, RiwayatActivity::class.java ))
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}