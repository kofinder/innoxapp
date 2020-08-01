package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.finderbar.innox.R
import com.google.android.material.button.MaterialButton


class AccountFragment : Fragment() {

    private lateinit var unkinder: Unbinder

    @BindView(R.id.btn_login) lateinit var loginButton: MaterialButton
    @BindView(R.id.btn_register) lateinit var registerButton: MaterialButton
   // @BindView(R.id.lst_menu) lateinit var listMenu: ListView

    private val arrayList: ArrayList<String> = arrayListOf<String>("Account", "Order", "Notification", "Logout")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        unkinder = ButterKnife.bind(this, root)

        loginButton.setOnClickListener{startActivity(Intent(activity, LoginActivity::class.java))}
        registerButton.setOnClickListener{startActivity(Intent(activity, RegisterActivity::class.java))}
        //listMenu.adapter = AccountMenuAdaptor(requireContext(), arrayList);

        return root;
    }

    override fun onDestroy() {
        super.onDestroy()
        unkinder.unbind()
    }
}