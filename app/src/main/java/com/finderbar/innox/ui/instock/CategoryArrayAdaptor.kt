package com.finderbar.innox.ui.instock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.finderbar.innox.R
import com.finderbar.innox.repository.Category


class CategoryArrayAdaptor(mContext: Context, private val textViewResourceId: Int, val arrayList: MutableList<Category>) : ArrayAdapter<Category>(mContext, 0, arrayList) {

    private var inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dropdownItemView = convertView

        if (dropdownItemView == null)
            dropdownItemView = inflater.inflate(textViewResourceId, parent,false)

        val datum = arrayList[position]
        val txtName: TextView = dropdownItemView!!.findViewById(R.id.txt_name)
        txtName.text = datum.name

        return dropdownItemView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = super.getDropDownView(position, convertView, parent)
        val txtName: TextView = view.findViewById(R.id.txt_name)
        txtName.text = arrayList[position].name
        return view;
    }

    override fun getFilter(): Filter {
        return filterName
    }

    private val filterName = object : Filter() {
        override fun convertResultToString(resultValue: Any?): CharSequence {
            val category: Category = resultValue as Category
            return category.name.toString()
        }
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            if (constraint != null) {
                val suggestions: ArrayList<Category> = ArrayList<Category>()
                for (customer in arrayList) {
                    if (customer.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer)
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
                addAll((results.values as ArrayList<Category?>))
            } else {
                addAll(arrayList)
            }
            notifyDataSetChanged()
        }
    }

    private fun add(r: Category) {
        arrayList.add(r)
        notifyDataSetChanged()
    }

    fun addAll(resultList: MutableList<Category>) {
        for (result in resultList) {
            add(result)
        }
    }
}
