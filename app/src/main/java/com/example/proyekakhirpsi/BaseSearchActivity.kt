

package com.example.proyekakhirpsi


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekakhirpsi.adapter.JanjiAdapter
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.connect.Api
import com.example.proyekakhirpsi.models.ApiService
import com.example.proyekakhirpsi.models.JanjiList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
abstract class BaseSearchActivity : AppCompatActivity() {
    lateinit var storeManager : StoreManager

    private lateinit var janjiAdapter : JanjiAdapter
    private val compositeDisposable = CompositeDisposable()
    lateinit var list : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        storeManager = StoreManager(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.riwayat)

        janjiAdapter = JanjiAdapter(emptyList())
        list = findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = janjiAdapter


        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    private fun displayData(janji : List<JanjiList>){
        list.adapter = JanjiAdapter(janji)
    }

    protected fun fetchData(){
        var apiCon= Api().getRetrofit().create(ApiService::class.java)
        compositeDisposable.add(apiCon.fetchJanji("*", "eq."+storeManager.email)
            .subscribeOn(Schedulers.io())
            .doOnError{
                Log.e("Fachry", "Throwable " + it.message)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                displayData(it)
            }, {
                Log.e("Fachry", "Throwable " + it.message)
            },{

            })

        )
    }
    protected fun fetchData(type:String, berjalan : String , query : String){
        var apiCon= Api().getRetrofit().create(ApiService::class.java)
        var tes = apiCon.fetchJanjiTopic("*", "eq."+storeManager.email, "like.%$query%")
        if(type == "berjalan"){
            tes = apiCon.fetchJanjiBerjalan("*", "eq."+storeManager.email,"eq.$query", "eq.$berjalan")
        }
        compositeDisposable.add(tes
            .subscribeOn(Schedulers.io())
            .doOnError{
                Log.e("Fachry", "Throwable " + it.message)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                displayData(it)
            }, {
                Log.e("Fachry", "Throwable " + it.message)
            },{

            })

        )
    }
}