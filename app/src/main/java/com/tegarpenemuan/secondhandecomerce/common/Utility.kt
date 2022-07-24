package com.tegarpenemuan.secondhandecomerce

import android.app.ActionBar
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.io.*
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


val listCategory: MutableList<String> = ArrayList()
val listCategoryId: MutableList<Int> = ArrayList()

fun setFullScreen(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun lightStatusBar(window: Window, light: Boolean = true) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = light
}

fun currency(angka: Int): String {
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp "
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(angka).dropLast(3)
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

private val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

private fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(
    selectedImage: Uri,
    context: Context,
): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()

    return myFile
}

fun convertDate(date: String): String {
    var kotlin = date
    var tahun = kotlin.take(4)
    kotlin = kotlin.drop(5)
    var bulan = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val tanggal = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val jam = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val menit = kotlin.take(2)

    when (bulan) {
        "01" -> {
            bulan = "Jan"
        }
        "02" -> {
            bulan = "Feb"
        }
        "03" -> {
            bulan = "Mar"
        }
        "04" -> {
            bulan = "Apr"
        }
        "05" -> {
            bulan = "Mei"
        }
        "06" -> {
            bulan = "Jun"
        }
        "07" -> {
            bulan = "Jul"
        }
        "08" -> {
            bulan = "Agu"
        }
        "09" -> {
            bulan = "Sep"
        }
        "10" -> {
            bulan = "Okt"
        }
        "11" -> {
            bulan = "Nov"
        }
        "12" -> {
            bulan = "Des"
        }
    }

    return "$jam:$menit, $tanggal $bulan $tahun"


}

fun striketroughtText(tv: TextView, textChange: String): String {
    tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    return textChange
}

fun showToastSuccess(view: View, message: String, color: Int) {
    val snackBarView =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
    snackBarView.setAction(" ") {
        snackBarView.dismiss()
    }
    val textView =
        snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_back, 0)
    textView.compoundDrawablePadding = 16
    layoutParams.gravity = Gravity.TOP
    layoutParams.setMargins(32, 150, 32, 0)
    snackBarView.view.setPadding(24, 16, 0, 16)
    snackBarView.view.setBackgroundColor(color)
    snackBarView.view.layoutParams = layoutParams
    snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
    snackBarView.show()
}

class MoneyTextWatcher(editText: EditText?) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText>
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        val editText: EditText? = editTextWeakReference.get()
        if (editText == null || editText.text.toString() == "") {
            return
        }
        editText.removeTextChangedListener(this)
        val parsed: BigDecimal = parseCurrencyValue(editText.text.toString())
        val formatted: String = numberFormat.format(parsed)
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    companion object {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        fun parseCurrencyValue(value: String): BigDecimal {
            try {
                val replaceRegex = java.lang.String.format(
                    "[%s,.\\s]", Objects.requireNonNull(
                        numberFormat.currency
                    ).displayName
                )
                val currencyValue = value.replace(replaceRegex.toRegex(), "")
                return BigDecimal(currencyValue)
            } catch (e: Exception) {}
            return BigDecimal.ZERO
        }
    }

    init {
        editTextWeakReference = WeakReference(editText)
        numberFormat.maximumFractionDigits = 0
        numberFormat.roundingMode = RoundingMode.FLOOR
    }
}