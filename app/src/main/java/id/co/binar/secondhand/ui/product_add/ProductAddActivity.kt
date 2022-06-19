package id.co.binar.secondhand.ui.product_add

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductAddBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.ui.login.LoginActivity
import id.co.binar.secondhand.ui.product_add.dialog.CategoryDialogFragment
import id.co.binar.secondhand.ui.product_add.dialog.TAG_CATEGORY_DIALOG
import id.co.binar.secondhand.util.onToast

@AndroidEntryPoint
class ProductAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAddBinding
    private val viewModel by viewModels<ProductAddViewModel>()
    private var chooseList = mutableListOf<GetCategoryResponseItem>()

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
        viewModel.categoryProduct()
        viewModel.list.observe(this) {
            binding.txtInputLayoutCategory.setText(it.toNameOnly())
            chooseList = it
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
            viewModel.list(chooseList)

            /**
             * get update adapter current list
             * untuk fitur update product
             * */
            /*val update = mutableListOf(
                GetCategoryResponseItem(id = 4, name = "Electronic", createdAt = "2022-06-13T13:29:38.052Z", updatedAt = "2022-06-13T13:29:38.052Z", check = true),
                GetCategoryResponseItem(id = 8, name = "newCategory", createdAt = "2022-06-13T15:04:11.350Z", updatedAt = "2022-06-13T15:04:11.350Z", check = true)
            )
            update.addAll(
                mutableListOf(
                    GetCategoryResponseItem(id = 4, name = "Electronic", createdAt = "2022-06-13T13:29:38.052Z", updatedAt = "2022-06-13T13:29:38.052Z"),
                    GetCategoryResponseItem(id = 8, name = "newCategory", createdAt = "2022-06-13T15:04:11.350Z", updatedAt = "2022-06-13T15:04:11.350Z"),
                    GetCategoryResponseItem(id = 9, name = "Electronic", createdAt = "2022-06-13T15:12:49.128Z", updatedAt = "2022-06-13T15:12:49.128Z"),
                    GetCategoryResponseItem(id = 10, name = "Electronic", createdAt = "2022-06-13T15:15:34.323Z", updatedAt = "2022-06-13T15:15:34.323Z")
                )
            )
            viewModel.lastList(update)*/
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}