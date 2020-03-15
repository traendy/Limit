package de.soenke.limit.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.soenke.limit.data.Payment
import de.soenke.limit.databinding.ListPaymentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentListAdapter(
    private val paymentClickListener: PaymentClickListener
) :
    ListAdapter<Payment, RecyclerView.ViewHolder>(PaymentDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addAndSubmitList(list: List<Payment>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PaymentViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PaymentViewHolder).bind(
            getItem(position),
            paymentClickListener
        )
    }

    class PaymentViewHolder private constructor(private val binding: ListPaymentBinding) :
        RecyclerView.ViewHolder(binding.root), AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.d("Miami", "onNothingSelected")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            Log.d("Miami", "onItemSelected pos: " + pos + ", id: " + id)
        }

        fun bind(
            item: Payment,
            paymentClickListener: PaymentClickListener
        ) {
            binding.payment = item
            binding.listener = paymentClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PaymentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListPaymentBinding.inflate(layoutInflater, parent, false)

                return PaymentViewHolder(
                    binding
                )
            }
        }
    }
}

class PaymentDiffCallback : DiffUtil.ItemCallback<Payment>() {
    override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem == newItem
    }
}

class PaymentClickListener(val clickListener: (payment: Payment) -> Unit) {
    fun onClick(payment: Payment) = clickListener(payment)
}
