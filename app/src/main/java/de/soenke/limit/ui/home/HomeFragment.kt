package de.soenke.limit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import de.soenke.limit.R
import de.soenke.limit.data.Limit
import de.soenke.limit.data.Payment
import de.soenke.limit.databinding.FragmentHomeBinding
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel>{viewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        val adapter = PaymentListAdapter(PaymentClickListener {
            viewModel.delete(it)
        })
        binding.paymentList.adapter = adapter
        viewModel.payments.observe(viewLifecycleOwner, Observer {
            adapter.addAndSubmitList(it)
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPayments()
            viewModel.getActiveLimit()
        }
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })
        viewModel.activeLimit.observe(viewLifecycleOwner, Observer { actitveLimit ->

        })
        binding.addButton.setOnClickListener {
            showPaymentDialog()
        }
        binding.limitText.setOnClickListener {
            showLimitDialog()
        }
        viewModel.getPayments()
        return binding.root
    }

    private fun showLimitDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
        builder.setTitle("Set your limit")
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_input_limit, view as ViewGroup?, false)
        val nameEditText = viewInflated.findViewById<EditText>(R.id.name_tiet)
        val startEditText = viewInflated.findViewById<EditText>(R.id.start_tiet)
        val endEditText = viewInflated.findViewById<EditText>(R.id.end_tiet)
        val limitEditText = viewInflated.findViewById<EditText>(R.id.limit_tiet)

        builder.setView(viewInflated)
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            val limit = Limit(
                nameEditText.text.toString(),
                limitEditText.text.toString().toFloat(),
                System.currentTimeMillis(),
                System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L,
                true
            )
            viewModel.setLimit(limit)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun showPaymentDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
        builder.setTitle("Title")
        val viewInflated: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_input_payment, view as ViewGroup?, false)
        val nameEditText = viewInflated.findViewById<View>(R.id.name) as EditText
        val valueEditText = viewInflated.findViewById<View>(R.id.value) as EditText
        builder.setView(viewInflated)
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            viewModel.addPayment(
                Payment(
                    nameEditText.text.toString(),
                    valueEditText.text.toString().toFloat(),
                    System.currentTimeMillis()
                )
            )
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}