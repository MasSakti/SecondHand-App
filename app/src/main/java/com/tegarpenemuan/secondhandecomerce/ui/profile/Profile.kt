package com.tegarpenemuan.secondhandecomerce.ui.profile

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val languages = resources.getStringArray(R.array.daftar_kota)
//
//        // access the spinner
//        val spinner = findViewById<Spinner>(R.id.et_pilih_kota)
//        if (spinner != null) {
//            val adapter = ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item, languages
//            )
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View, position: Int, id: Long
//                ) {
//                    Toast.makeText(
//                        this@Profile,
//                        getString(R.string.selected_item) + " " +
//                                "" + languages[position], Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }

        viewModel.getProfile()

        bindView()
        bindviewModel()
    }

    private fun bindviewModel() {
        viewModel.shouldShowUser.observe(this) {
            Glide.with(this)
                .load(it.image_url)
                .circleCrop()
                .into(binding.ivProfile)
            binding.etAlamat.setText(it.address)
            binding.etNama.setText(it.full_name)
            binding.etPilihKota.setText(it.city.toString())
            binding.etNoHandphone.setText(it.phone_number)
        }

        viewModel.showResponseError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.showResponseSuccess.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

    }

    private fun bindView() {
        binding.etNama.doAfterTextChanged {
            viewModel.onChangeName(it.toString())
        }
        binding.etPilihKota.doAfterTextChanged {
            viewModel.onChangeKota(it.toString())
        }
        binding.etAlamat.doAfterTextChanged {
            viewModel.onChangeAlamat(it.toString())
        }
        binding.etNoHandphone.doAfterTextChanged {
            viewModel.onChangeNoHandphone(it.toString())
        }
        binding.ivProfile.setOnClickListener {
            picImage()
        }
        binding.btnSignIn.setOnClickListener {
            viewModel.onValidate()
        }
    }

    private fun picImage() {
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                viewModel.getUriPath(uri)

                Glide.with(applicationContext)
                    .load(uri)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        }
}