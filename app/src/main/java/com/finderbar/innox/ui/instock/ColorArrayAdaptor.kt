package com.finderbar.innox.ui.instock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.finderbar.innox.R
import com.finderbar.innox.repository.Color


class ColorArrayAdaptor(mContext: Context, private val textViewResourceId: Int, val arrays: MutableList<Color>) : ArrayAdapter<Color>(mContext, 0, arrays) {

    private var inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dropdownItemView = convertView

        if (dropdownItemView == null)
            dropdownItemView = inflater.inflate(textViewResourceId, parent,false)

        val datum = arrays[position]
        val txtName: TextView = dropdownItemView!!.findViewById(R.id.txt_name)
        txtName.text = datum.name

        return dropdownItemView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = super.getDropDownView(position, convertView, parent)
        val txtName: TextView = view.findViewById(R.id.txt_name)
        txtName.text = arrays[position].name
        return view;
    }

    override fun getFilter(): Filter {
        return filterName
    }

    private val filterName = object : Filter() {
        override fun convertResultToString(resultValue: Any?): CharSequence {
            val color: Color = resultValue as Color
            return color.name.toString()
        }
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            if (constraint != null) {
                val suggestions: ArrayList<Color> = ArrayList<Color>()
                for (arr in arrays) {
                    if (arr.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(arr)
                    }
                }
                results.values = suggestions
                results.count = suggestions.size
            }

            return results

        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            clear()
            if (results != null && results.count > 0) {
                addAll((results.values as ArrayList<Color?>))
            } else {
                addAll(arrays)
            }
            notifyDataSetChanged()
        }
    }

}
