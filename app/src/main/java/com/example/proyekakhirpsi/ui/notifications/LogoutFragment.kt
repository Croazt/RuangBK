package com.example.proyekakhirpsi.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhirpsi.R
import com.example.proyekakhirpsi.databinding.FragmentLogoutBinding
import com.example.proyekakhirpsi.databinding.FragmentProfilBinding

class LogoutFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentLogoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val back = root.findViewById<Button>(R.id.arrowback)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}