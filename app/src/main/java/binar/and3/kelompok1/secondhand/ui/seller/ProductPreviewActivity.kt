package binar.and3.kelompok1.secondhand.ui.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import binar.and3.kelompok1.secondhand.databinding.ActivityProductPreviewBinding

class ProductPreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}