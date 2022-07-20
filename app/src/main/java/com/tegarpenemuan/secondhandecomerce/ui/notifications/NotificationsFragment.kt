package com.tegarpenemuan.secondhandecomerce.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentNotificationsBinding
import com.tegarpenemuan.secondhandecomerce.ui.detailnotification.DetailNotificationActivity
import com.tegarpenemuan.secondhandecomerce.ui.notifications.adapter.NotificationsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationsViewModel by viewModels()

    lateinit var notificationsAdapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getNotification()
        bindview()
        bindviewModel()
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotification()
    }

    private fun bindview() {
        binding.swipe.setOnRefreshListener {
            viewModel.getNotification()
            binding.swipe.isRefreshing = false
        }

        notificationsAdapter =
            NotificationsAdapter(listener = object : NotificationsAdapter.EventListener {
                override fun onClick(item: GetNotifResponseItem) {
                    val intent = Intent(context, DetailNotificationActivity::class.java)
                    intent.putExtra("id", item.id.toString())
                    startActivity(intent)
                }

                override fun onClickAcc(item: GetNotifResponseItem) {
                    viewModel.updateReadNotif(item.id)
                    viewModel.getNotification()
                    onResume()
                }

            }, emptyList())
        binding.rvNotifikasi.adapter = notificationsAdapter
    }

    private fun bindviewModel() {
        viewModel.shouldShowGetNotification.observe(viewLifecycleOwner) {
            notificationsAdapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}