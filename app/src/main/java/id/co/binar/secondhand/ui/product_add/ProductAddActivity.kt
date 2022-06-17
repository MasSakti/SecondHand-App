package id.co.binar.secondhand.ui.product_add

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductAddBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.ui.login.LoginActivity
import id.co.binar.secondhand.ui.product_add.dialog.CategoryDialogFragment
import id.co.binar.secondhand.ui.product_add.dialog.TAG_CATEGORY_DIALOG

@AndroidEntryPoint
class ProductAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAddBinding
    private val viewModel by viewModels<ProductAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Lengkapi Detail Produk"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (viewModel.getTokenId().isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.list.observe(this) {
            binding.txtInputLayoutCategory.setText(it.toNameOnly())
        }
    }

    private fun List<GetCategoryResponseItem>.toNameOnly(): String {
        var str = ""
        this.forEach {
            str += "${it.name}, "
        }
        return str
    }

    private fun bindView() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.txtInputLayoutCategory.setOnClickListener {
            val dialog = CategoryDialogFragment()
            dialog.show(supportFragmentManager, TAG_CATEGORY_DIALOG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}