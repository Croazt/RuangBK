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
import com.example.proyekakhirpsi.models.UserList
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    lateinit var email : EditText
    lateinit var pass : EditText

    val PREF_NAME = "Shared"
    val EMAIL_VAL = "Email"
    val PASS_VAL = "Pass"
    lateinit var sharedPreference : SharedPreferences

    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false
        addSource(emailLiveData) { email ->
            val password = passwordLiveData.value
            this.value = validateForm(email, password)
        }

        addSource(passwordLiveData) { password ->
            val email = emailLiveData.value
            this.value = validateForm(email, password)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signInButton = findViewById<Button>(R.id.button)

        email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        pass = findViewById<EditText>(R.id.editTextTextPassword)

        sharedPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        email?.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }
        pass?.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        isValidLiveData.observe(this) { isValid ->
            signInButton.isEnabled = isValid
            if (!isValid) {
                signInButton.setBackgroundColor(Color.parseColor("#FCECE2"))
            } else {
                signInButton.setBackgroundColor(Color.parseColor("#506D84"))
            }
        }

        val loginText = findViewById<TextView>(R.id.textView6)
        loginText.setOnClickListener{
            startActivity(Intent(applicationContext, RegisterActivity::class.java ))
        }
//        val root = findViewById<View>(android.R.id.content).getRootView()
        signInButton.setOnClickListener(){
            val apiSet = Api().getRetrofit()
            val api = apiSet.create(ApiService::class.java)
            api.fetchUserLogin("*","eq."+emailLiveData.value+"","eq."+passwordLiveData.value+"").enqueue(object : Callback<List<UserList>> {
                override fun onResponse(
                    call: Call<List<UserList>>,
                    response: Response<List<UserList>>
                ) {
//                    val storeManager = StoreManager(applicationContext);
                    Log.d("fachry", "body: ${response.body()}")
                    if(response.code()!=200 || response.body()!!.isEmpty()){
                        val snackbar = Snackbar.make(findViewById<View>(android.R.id.content).getRootView(), "User Not Found",Snackbar.LENGTH_LONG).setAction("Action",null)
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
                        storeManager.setData(emailLiveData.value+"",passwordLiveData.value+"", response.body()?.get(0)?.image+"")
                        Log.d("FACHRY", storeManager.email+"")
                        startActivity(Intent(applicationContext, MainActivity::class.java ))
                        this@LoginActivity.finish()
                    }
                }

                override fun onFailure(call: Call<List<UserList>>, t: Throwable) {
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
        editor.commit()
        editor.apply()
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        sharedPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onRestoreInstanceState(savedInstanceState)

        val email1 = sharedPreference.getString(EMAIL_VAL, "")
        val pass1 = sharedPreference.getString(PASS_VAL, "")

        email.setText(email1.toString())
        pass.setText(pass1.toString())
    }

    private fun validateForm(email: String?, password: String?): Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword =
            password != null && password.isNotBlank() && password.length >= 8
        return isValidEmail && isValidPassword
    }
}