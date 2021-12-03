package com.example.proyekakhirpsi

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.connect.Api
import com.example.proyekakhirpsi.models.ApiService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.addjanji.*
import kotlinx.android.synthetic.main.janjiberhasil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahJanjiActivity : AppCompatActivity() {

    private lateinit var binding: TambahJanjiActivity
    private val namaLiveData = MutableLiveData<String>()
    private val kelasLiveData = MutableLiveData<String>()
    private val topicLiveData = MutableLiveData<String>()
    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false
        addSource(namaLiveData) { nama ->
            val kelas = kelasLiveData.value
            val topic = topicLiveData.value
            this.value = validateForm(nama, kelas, topic)
        }

        addSource(kelasLiveData) { kelas ->
            val nama = namaLiveData.value
            val topic = topicLiveData.value
            this.value = validateForm(nama, kelas, topic)
        }
        addSource(topicLiveData) { topic ->
            val nama = namaLiveData.value
            val kelas = kelasLiveData.value
            this.value = validateForm(nama, kelas, topic)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storeManager = StoreManager(this)


        if (storeManager.email != null && storeManager.email!!.isNotEmpty()) {
            setContentView(R.layout.addjanji)

            arrowback.setOnClickListener{
                this.finish()
            }


            etnama?.doOnTextChanged { text, _, _, _ ->
                namaLiveData.value = text?.toString()
            }
            etkelas?.doOnTextChanged { text, _, _, _ ->
                kelasLiveData.value = text?.toString()
            }
            kelasradio.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener{group, checkedId ->
                    val radio : RadioButton = findViewById(checkedId)
                    topicLiveData.value = radio.text?.toString()
                }
            )

            isValidLiveData.observe(this) { isValid ->
                btndetail.isEnabled = isValid
            }

            val root = findViewById<View>(android.R.id.content).rootView
            btndetail.setOnClickListener {
                val apiSet = Api().getRetrofit()
                val api = apiSet.create(ApiService::class.java)
                api.createJanji(topicLiveData.value+"", namaLiveData.value+"", kelasLiveData.value+"").enqueue(object :
                    Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if(response.code()!=201 && response.code()!=200){
                            val snackbar = Snackbar.make(root, "TIDAK BERHASIL MENAMBAHKAN DATA", Snackbar.LENGTH_LONG).setAction("Action",null)
                            snackbar.setActionTextColor(Color.YELLOW)

                            snackbar.show()
                        }else {

                            val mdIalog : Dialog = Dialog(this@TambahJanjiActivity)
                            val layout = R.layout.janjiberhasil
                            mdIalog.setContentView(layout)

                            mdIalog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            button2.setOnClickListener{
                                this@TambahJanjiActivity.finish()
                                mdIalog.dismiss()
                            }
                            mdIalog.show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("fachry", "onResponse: $t ")
                        Toast.makeText(applicationContext, "Something error",  Toast.LENGTH_LONG).show()
                    }
                })
            }
        } else {
            Log.d("FACHRY", storeManager.email+"")
            startActivity(Intent(applicationContext, LoginActivity::class.java ))
            this.finish()
        }
    }
    private fun validateForm(nama: String?, kelas: String?, topic: String?): Boolean {
        val isValidEmail = nama != null && nama.isNotBlank()
        val isValidPassword =
            kelas != null && kelas.isNotBlank()
        val isValidTopic =
            topic != null && topic.isNotBlank()
        return isValidEmail && isValidPassword
    }
}