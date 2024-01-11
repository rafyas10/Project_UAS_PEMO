package com.myapp.uas_mobile_mry

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.myapp.uas_mobile_mry.adapter.CustomAdapter
import com.myapp.uas_mobile_mry.datpub.ModelGempa
import com.myapp.uas_mobile_mry.network.DataConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class data : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.f_data, container, false)

        val _listView = view.findViewById<ListView>(R.id.list_gempa)

        DataConfig()
            .getService()
            .getDataGempa()
            .enqueue(object : Callback<ModelGempa> {
                override fun onResponse(
                    call: Call<ModelGempa>,
                    response: Response<ModelGempa>
                ) {
                    Log.d("mry", "json data: " + response.body())
                    _listView.adapter = CustomAdapter(response.body(), this@data,
                        response.body()?.infogempa?.gempa!!
                    )
                    Log.d("mry ", "Adapter beres")
                }

                override fun onFailure(call: Call<ModelGempa>, t: Throwable) {
                    Log.d("mry", "error: " + t.message.toString())
                }

            })

        return view
    }

}