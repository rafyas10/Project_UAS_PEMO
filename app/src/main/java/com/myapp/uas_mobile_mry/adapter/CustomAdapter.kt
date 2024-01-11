package com.myapp.uas_mobile_mry.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.myapp.uas_mobile_mry.R
import com.myapp.uas_mobile_mry.data
import com.myapp.uas_mobile_mry.datpub.GempaItem
import com.myapp.uas_mobile_mry.datpub.ModelGempa

class CustomAdapter(val data: ModelGempa?, val fragment: data, val _g: List<GempaItem?>)
    : ArrayAdapter<GempaItem>(fragment.requireContext(), R.layout.custom_listview, _g){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)

        val inflater = fragment.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_listview, null, true)

        var _idx = rowView.findViewById<TextView>(R.id.list_nomor)
        var _tgl = rowView.findViewById<TextView>(R.id.list_tanggal)
        var _koordinat = rowView.findViewById<TextView>(R.id.list_koordinat)
        var _wilayah = rowView.findViewById<TextView>(R.id.list_wilayah)

        _idx.setText((position+1).toString())
        _tgl.setText(data?.infogempa?.gempa?.get(position)?.tanggal)
        _koordinat.setText(data?.infogempa?.gempa?.get(position)?.coordinates)
        _wilayah.setText(data?.infogempa?.gempa?.get(position)?.wilayah)

        return rowView
    }

    }