package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R
import kotlin.collections.ArrayList


class CustomizeDesignActivity: AppCompatActivity() {
    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.exp_list) lateinit var expListView: ExpandableListView

    private var listDataHeader: MutableList<String> = arrayListOf();
    private var listDataChild = hashMapOf<String, MutableList<String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_designer)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Customized Design Listing"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listAdapter = DesignerExpendableAdaptor(this, listDataHeader, listDataChild)
        expListView.setAdapter(listAdapter)

        expListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            false
        }

        expListView.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(
                applicationContext,
                listDataHeader.get(groupPosition).toString() + " Expanded",
                Toast.LENGTH_SHORT
            ).show()
        }

        expListView.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(
                applicationContext,
                listDataHeader.get(groupPosition).toString() + " Collapsed",
                Toast.LENGTH_SHORT
            ).show()
        }

        expListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            // TODO Auto-generated method stub
            Toast.makeText(
                applicationContext,
                listDataHeader.get(groupPosition)
                    .toString() + " : "
                        + listDataChild.get(
                    listDataHeader.get(groupPosition)
                )?.get(
                    childPosition
                ), Toast.LENGTH_SHORT
            )
                .show()
            false
        }
    }


    private fun prepareListData() {

        // Adding child data
        listDataHeader.add("Top 250")
        listDataHeader.add("Now Showing")
        listDataHeader.add("Coming Soon..")
        // Adding child data
        val top250: MutableList<String> = ArrayList()
        top250.add("The Shawshank Redemption")
        top250.add("The Godfather")
        top250.add("The Godfather: Part II")
        top250.add("Pulp Fiction")
        top250.add("The Good, the Bad and the Ugly")
        top250.add("The Dark Knight")
        top250.add("12 Angry Men")
        val nowShowing: MutableList<String> = ArrayList()
        nowShowing.add("The Conjuring")
        nowShowing.add("Despicable Me 2")
        nowShowing.add("Turbo")
        nowShowing.add("Grown Ups 2")
        nowShowing.add("Red 2")
        nowShowing.add("The Wolverine")
        val comingSoon: MutableList<String> = ArrayList()
        comingSoon.add("2 Guns")
        comingSoon.add("The Smurfs 2")
        comingSoon.add("The Spectacular Now")
        comingSoon.add("The Canyons")
        comingSoon.add("Europa Report")

        listDataChild.put(listDataHeader.get(0), top250) // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing)
        listDataChild.put(listDataHeader.get(2), comingSoon)
    }
}