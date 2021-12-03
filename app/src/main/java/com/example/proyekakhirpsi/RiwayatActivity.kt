package com.example.proyekakhirpsi

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.riwayat.*

class RiwayatActivity : BaseSearchActivity() {

    override fun onStart() {

        super.onStart()
            // 1
            val searchTextObservable = createButtonClickObservable()

            searchTextObservable.distinctUntilChanged()
                // 2
                .subscribe { query ->
                    // 3
                    Log.e("Fachry","DATA $query")
                    fetchData("topic", "", query)
                }

            btnberjalan.setOnClickListener{
                fetchData("berjalan", "berjalan", searchView.text.toString())
            }
            btnselesai.setOnClickListener{
                fetchData("berjalan", "selesai", searchView.text.toString())
            }

            arrowback.setOnClickListener{
                this.finish()
            }
    }

    private fun createButtonClickObservable(): Observable<String> {
        // 2
        return PublishSubject.create{ emitter ->

            searchView.addTextChangedListener {
                // 4
                emitter.onNext(searchView.text.toString())
            }

        }
    }


}
