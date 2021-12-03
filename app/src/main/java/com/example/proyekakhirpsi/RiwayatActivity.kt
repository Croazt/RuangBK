package com.example.proyekakhirpsi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class RiwayatActivity : BaseSearchActivity() {
    lateinit var queryEditText : EditText

    override fun onStart() {

        super.onStart()
            var buttonBerjalan = findViewById<Button>(R.id.btnberjalan)
            var buttonselesai = findViewById<Button>(R.id.btnselesai)
            var buttonback = findViewById<Button>(R.id.arrowback)
            queryEditText = findViewById<EditText>(R.id.searchView)
            // 1
            val searchTextObservable = createButtonClickObservable()

            searchTextObservable.distinctUntilChanged()
                // 2
                .subscribe { query ->
                    // 3
                    Log.e("Fachry","DATA $query")
                    fetchData("topic", "", query)
                }

            buttonBerjalan.setOnClickListener{
                fetchData("berjalan", "berjalan", queryEditText.text.toString())
            }
            buttonselesai.setOnClickListener{
                fetchData("berjalan", "selesai", queryEditText.text.toString())
            }

            buttonback.setOnClickListener{
                this.finish()
            }
    }

    private fun createButtonClickObservable(): Observable<String> {
        // 2
        return PublishSubject.create{ emitter ->

            queryEditText.addTextChangedListener {
                // 4
                emitter.onNext(queryEditText.text.toString())
            }

        }
    }

}
