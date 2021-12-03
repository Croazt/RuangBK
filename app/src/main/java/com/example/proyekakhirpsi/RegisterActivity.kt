package com.example.proyekakhirpsi


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.connect.Api
import com.example.proyekakhirpsi.models.ApiService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val passwordConfirmLiveData = MutableLiveData<String>()


    lateinit var email : EditText
    lateinit var pass : EditText
    lateinit var conf : EditText

    val PREF_NAME = "Shared"
    val EMAIL_VAL = "Email"
    val PASS_VAL = "Pass"
    val CONF_VAL = "Confirm"
    lateinit var sharedPreference : SharedPreferences

    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false
        addSource(emailLiveData) { email ->
            val password = passwordLiveData.value
            val confirm = passwordConfirmLiveData.value
            this.value = validateForm(email, password, confirm)
        }

        addSource(passwordLiveData) { password ->
            val email = emailLiveData.value
            val confirm = passwordConfirmLiveData.value
            this.value = validateForm(email, password, confirm)
        }
        addSource(passwordConfirmLiveData) { confirm ->
            val email = emailLiveData.value
            val password = passwordLiveData.value
            this.value = validateForm(email, password, confirm)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)


        sharedPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        editTextTextEmailAddress?.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }
        editTextTextPassword?.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }
        editTextTextPassword2?.doOnTextChanged { text, _, _, _ ->
            passwordConfirmLiveData.value = text?.toString()
        }

        isValidLiveData.observe(this) { isValid ->
            registerbutt.isEnabled = isValid
            if (!isValid) {
                registerbutt.setBackgroundColor(Color.parseColor("#FCECE2"))
            } else {
                registerbutt.setBackgroundColor(Color.parseColor("#506D84"))
            }
        }

        textView6.setOnClickListener{
            startActivity(Intent(applicationContext, LoginActivity::class.java ))
            this.finish()
        }

        val root = findViewById<View>(android.R.id.content).rootView
        registerbutt.setOnClickListener(){
            val apiSet = Api().getRetrofit()
            val api = apiSet.create(ApiService::class.java)
            api.createUser(emailLiveData.value+"",passwordLiveData.value+"").enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.d("fachry", "onResponse: $response ")
                    if(response.code()!=200 && response.code()!=201){
                        val snackbar = Snackbar.make(root, "User Has Created",Snackbar.LENGTH_LONG).setAction("Action",null)
                        snackbar.setActionTextColor(Color.BLUE)
                        val snackbarView = snackbar.view
                        snackbarView.setBackgroundColor(Color.LTGRAY)
                        val textView =
                            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                        textView.setTextColor(Color.BLUE)
                        textView.textSize = 28f
                        snackbar.show()
                    }else {
                        val storeManager = StoreManager(applicationContext);
                        storeManager.setData(emailLiveData.value+"",passwordLiveData.value+ "","")

                        startActivity(Intent(applicationContext, RegisterActivity::class.java ))
                        this@RegisterActivity.finish()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Something error",  Toast.LENGTH_LONG).show()
                }
            })
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(EMAIL_VAL, email.text.toString())
        editor.putString(PASS_VAL, pass.text.toString())
        editor.putString(CONF_VAL, conf.text.toString())
        editor.commit()
        editor.apply()
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        sharedPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onRestoreInstanceState(savedInstanceState)

        val email1 = sharedPreference.getString(EMAIL_VAL, "")
        val pass1 = sharedPreference.getString(PASS_VAL, "")
        val conf1 = sharedPreference.getString(CONF_VAL, "")

        email.setText(email1.toString())
        pass.setText(pass1.toString())
        conf.setText(conf1.toString())
    }
    private fun validateForm(email: String?, password: String?, confirm: String?): Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword =
            password != null && password.isNotBlank() && password.length >= 8 && password == confirm
        return isValidEmail && isValidPassword
    }
}
