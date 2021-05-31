package com.qucoon.demoapp.utils;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.widget.DatePicker
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun tryAction(action:()->Unit){
        return try {
            action()
        }catch (ex:Exception){
            Timber.e(ex)
        }
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
    fun getPhoneDeviceName():String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

    fun getFirstLetters(text: String): String? {
        var text = text
        return try {
            if (text.length > 1) {
                var firstLetters = ""
                text = text.replace("[.,]".toRegex(), "") // Replace dots, etc (optional)
                var noofLetters = 0
                for (s in text.split(" ".toRegex()).toTypedArray()) {
                    firstLetters += s[0]
                    noofLetters += 1
                    if (noofLetters == 2) {
                        break
                    }
                }
                return firstLetters
            }
            ""
        } catch (e: java.lang.Exception) {
            " "
        }
    }


    fun getFormartedTime(date:String):String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat = SimpleDateFormat("KK:mm a")
        return outputFormat.format(inputFormat.parse(date))
    }

    fun loadImage(context: Context,imageUrl:String,placeholderImage:Int,imageView: ImageView){
        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions()
                .placeholder(ContextCompat.getDrawable(context,placeholderImage))
                .fitCenter()
            )
            .into(imageView)
    }

    fun loadImage(fastLoadUrl: String, fullImageUrl: String, imageView: CircleImageView, context: Context, defaultImage:Int) {
        println("I LOADED")
        val requestOption = RequestOptions()
            .placeholder(defaultImage).centerCrop()
        Glide.with(context).load(fullImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(Glide.with(context)
                .load(fastLoadUrl)
                .apply(requestOption))
            .apply(requestOption)
            .into(imageView)
    }


    @JvmStatic
    fun formatDouble(string:String):Pair<Boolean,Any>{
        return string.toDoublee()
    }
//    fun tryActionOrNull(action:()->Alerts?):Alerts?{
//        return try {
//            action()
//        }catch (ex:Exception){
//            Timber.e(ex)
//            null
//        }
//    }
fun requestIdGenerator(): String {
    return (Math.floor(Math.random() * 9_000_000_000L).toLong() + 1_000_000_000L).toString()
}

    fun <T:Any> tryOperation(action:()-> UseCaseResult<T>): UseCaseResult<T> {
        return try {
            action()
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    fun  tryBackgroundOperation(action:()->Boolean):Boolean{
        return try {
            action()
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }
    private fun saveBitmap(bitmap: Bitmap?, path: String): File? {
        var file: File? = null
        if (bitmap != null) {
            file = File(path)
            try {
                var outputStream: FileOutputStream? = null
                try {
                    outputStream =
                        FileOutputStream(path) //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    ) // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        outputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return file
    }


    fun formatNumber(double:String):String{
        return DecimalFormat("#,###.##").format(double.toDouble())
    }
    fun formatNumber(number: Double): String? {
        return DecimalFormat("#,##0.00").format(number)
    }




    fun getRealPathFromURI(contentUri: Uri,context: Context): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor!!.moveToFirst()
        return cursor.getString(column_index!!)
    }
    fun getDateTimeTime(date:String):Date{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            Date()
        }
    }


    fun getPath(uri: Uri, context: Context): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getContentResolver().query(uri, projection, null, null, null) ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(column_index)
        cursor.close()
        return s
    }



    @Throws(IOException::class)
    fun rotateBitmap(photoPath: String, bitmap: Bitmap): Bitmap? {
        val ei = ExifInterface(photoPath)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        var rotatedBitmap: Bitmap? = null
        when (orientation) {

            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap =
                rotateImage(bitmap, 90f)

            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap =
                rotateImage(bitmap, 180f)

            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap =
                rotateImage(bitmap, 270f)

            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
            else -> rotatedBitmap = bitmap
        }

        return rotatedBitmap
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    fun formatPhone(phonenumber: String): String {
        var phonenumber = phonenumber.replace("\\s".toRegex(),"")
        phonenumber = phonenumber.replace("+", "")
        if (phonenumber.startsWith("0")) {
            phonenumber = "234" + phonenumber.substring(1)
        } else if (phonenumber.length <= 10) {
            phonenumber = "234$phonenumber"
        }
        return phonenumber
    }


    @JvmStatic
    fun getDate2(date: Date?): String {
        val sdf = SimpleDateFormat(" dd-MMM-yyyy")
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        return sdf.format(calendar.time)
    }
    @JvmStatic
    fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    class MyViewPageStateAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){
        val fragmentList:MutableList<Fragment> = ArrayList<Fragment>() as MutableList<Fragment>
        val fragmentTitleList:MutableList<String> = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList.get(position)
        }

        fun addFragment(fragment: Fragment, title:String){
            fragmentList.add(fragment)
            fragmentTitleList.add(title)

        }


    }
}
