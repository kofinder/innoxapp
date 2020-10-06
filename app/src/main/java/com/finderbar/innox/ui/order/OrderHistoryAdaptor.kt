package com.finderbar.innox.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemOrderClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ItemOrderHistoryBinding
import com.finderbar.innox.repository.OrderHistory
import com.finderbar.innox.utilities.OrderHistoryType

class OrderHistoryAdaptor(val context: Context, val arrays: MutableList<OrderHistory>, private val orderType: OrderHistoryType, private val itemOrderClick: ItemOrderClick) :
    RecyclerView.Adapter<OrderHistoryAdaptor.OrderHistoryViewHolder>() {

//    companion object: DiffUtil.ItemCallback<OrderHistory>() {
//        override fun areItemsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean = oldItem === newItem
//        override fun areContentsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean = oldItem.id == newItem.id
//    }

    class OrderHistoryViewHolder(val binding: ItemOrderHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderHistoryBinding.inflate(inflater, parent, false)
        return OrderHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val datum: OrderHistory = arrays[position]
        when (orderType) {
            OrderHistoryType.ACTIVE -> {
                holder.binding.civThumb.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.order_active_small))
            }
            OrderHistoryType.PENDING -> {
                holder.binding.civThumb.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.order_pending_small))
            }
            else -> {
                holder.binding.civThumb.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.order_past_small))
            }
        }

        holder.binding.txtInvoice.text = datum.invoiceNo
        holder.binding.txtDate.text = datum.orderDate
        holder.binding.txtPrice.text = datum.totalCostText

        holder.itemView.setOnClickListener{
            itemOrderClick.onItemClick(datum.id!!, datum.orderDate!!)
        }
    }

    override fun getItemCount(): Int  = arrays.size

    private fun add(order: OrderHistory) {
        arrays.add(order)
        notifyItemInserted(arrays.size - 1)
    }

    fun addAll(array: List<OrderHistory>) {
        for (result in array) {
            add(result)
        }
    }
}