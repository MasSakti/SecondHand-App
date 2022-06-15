package id.co.binar.secondhand.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}