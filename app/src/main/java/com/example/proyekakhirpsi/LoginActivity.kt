package com.example.proyekakhirpsi


import android.app.Activity;
import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.connect.Api
import com.example.proyekakhirpsi.models.ApiService
import com.example.proyekakhirpsi.models.UserList
import com.google.android.material.snackbar.Snackbar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
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

        val emailLayout = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val signInButton = findViewById<Button>(R.id.button)

        emailLayout?.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }
        password?.doOnTextChanged { text, _, _, _ ->
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
            Log.d("MEMEK", "onCreate: ")
            api.fetchUserLogin("*","eq."+emailLiveData.value+"","eq."+passwordLiveData.value+"").enqueue(object : Callback<List<UserList>> {
                override fun onResponse(
                    call: Call<List<UserList>>,
                    response: Response<List<UserList>>
                ) {
//                    val storeManager = StoreManager(applicationContext);
                    Log.d("fachry", "onResponse: $response ")
                    if(response.code()!=200){
                        val snackbar = Snackbar.make(findViewById<View>(android.R.id.content).getRootView(), "User Has Created",Snackbar.LENGTH_LONG).setAction("Action",null)
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
                        storeManager.setData(emailLiveData.value+"",passwordLiveData.value+"")
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

    private fun validateForm(email: String?, password: String?): Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword =
            password != null && password.isNotBlank() && password.length >= 8
        return isValidEmail && isValidPassword
    }
}
