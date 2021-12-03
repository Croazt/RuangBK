package com.example.proyekakhirpsi.ui.logout

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhirpsi.LoginActivity
import com.example.proyekakhirpsi.R
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.databinding.FragmentLogoutBinding

class LogoutFragment : Fragment() {

    private lateinit var logoutViewModel: LogoutViewModel
    private var _binding: FragmentLogoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logoutViewModel =
            ViewModelProvider(this)[LogoutViewModel::class.java]

        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button: Button = binding.logoutBtn

        button.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val logout = StoreManager(requireContext())
                    logout.setEmail("")

                    startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        val back = root.findViewById<Button>(R.id.arrowback)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}