package id.co.binar.secondhand.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

fun Context.onToast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}
