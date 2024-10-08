package com.example.sampleqrmerchantapp.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.Locale

class MIDAdapter(context: Context, val midList: List<String>) : ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, midList) {

    private var filteredMIDList: List<String> = midList

    override fun getCount(): Int {
        return filteredMIDList.size
    }

    override fun getItem(position: Int): String? {
        return filteredMIDList[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint != null && constraint.isNotEmpty()) {
                    val query = constraint.toString().toLowerCase(Locale.ROOT)
                    val filteredList = midList.filter { it.toLowerCase(Locale.ROOT).contains(query) }

                    filterResults.values = filteredList
                    filterResults.count = filteredList.size
                } else {
                    filterResults.values = midList
                    filterResults.count = midList.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredMIDList = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }
}
