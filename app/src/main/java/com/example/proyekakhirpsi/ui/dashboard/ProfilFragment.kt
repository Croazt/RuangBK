package com.example.proyekakhirpsi.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.proyekakhirpsi.EditProfileActivity
import com.example.proyekakhirpsi.R
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.databinding.FragmentProfilBinding
import kotlinx.android.synthetic.main.fragment_profil.*

class ProfilFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvtgl
        textView.text = "Tanggal lahir"

        val storeManager = StoreManager(requireContext())
        val imageView : ImageView = root.findViewById(R.id.imgprofil)
        Glide.with(this)
            .load(storeManager.image.toString())
            .into(imageView)
        val nama : TextView = root.findViewById(R.id.tvnamauser)
        nama.text = storeManager.email.toString().split("@")[0]


        root.findViewById<Button>(R.id.btndetail).setOnClickListener{
            startActivity(Intent(context, EditProfileActivity::class.java ))
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}