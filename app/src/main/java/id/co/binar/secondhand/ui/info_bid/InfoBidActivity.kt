package id.co.binar.secondhand.ui.info_bid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityInfoBidBinding

const val ARGS_PASSING_SEE_DETAIL_ID_ORDER = "ID_ORDER"

@AndroidEntryPoint
class InfoBidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBidBinding
    private val viewModel by viewModels<InfoBidViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Info Penawar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}