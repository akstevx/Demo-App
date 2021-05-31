package com.qucoon.demoapp.utils;

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.chip.Chip
import timber.log.Timber
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher

fun Fragment.hideKeyboard(){
    val imm: InputMethodManager =
        context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.view?.windowToken, 0)
}
fun delayFor(millseconds:Long,action:() -> Unit ){
    Handler().postDelayed({
        action()
    }, millseconds)
}

fun View.hide(){
    this.visibility = View.GONE
}

fun addNYearsToDate(currentDate: Date, noOfYears: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = currentDate
    cal.add(Calendar.YEAR, noOfYears)
    return cal.time
}

fun Cursor.closeCursor(){
    if(!this.isClosed){
        this.close()
    }
}
fun String?.getString(): String{
    return this ?: ""
}


fun Long.convertToDate(format:String?):String{
    val date = Date(this)
    val simpleFormat = if (format == null) SimpleDateFormat("dd-MMM-yyyy HH:mm") else SimpleDateFormat(format)
    return simpleFormat.format(date)
}
fun String.convertToDate(format:String?):Date{
    var formatter = SimpleDateFormat(format)
    var date = formatter.parse(this) as Date
    return  date
}

fun String.formatDate():String{
    if(this.isNullOrEmpty()) return  ""
    val originalFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = originalFormat.parse(this)
    val targetFormat = SimpleDateFormat("dd MMM yy")
    return  targetFormat.format(date)
}

fun String.formatDate(originalFormat:String, targetFormart:String):String{
    val originalFormat = SimpleDateFormat(originalFormat)
    val targetFormat = SimpleDateFormat(targetFormart)
    return   targetFormat.format(originalFormat.parse(this))
}
fun String.formatDate(orginalFormat:String):String{
    val originalFormat = SimpleDateFormat(orginalFormat)
    val date = originalFormat.parse(this)
    val targetFormat = SimpleDateFormat("dd MMM yy")
    return  targetFormat.format(date)
}


fun String.toDoublee():Pair<Boolean,Any>{
    return if(this.trim().toDoubleOrNull() == null){
        Pair(false,this)
    }else{
        Pair(true,this.trim().toDouble())
    }
}

fun TextView.getString():String{
    return this.text.toString().trim()
}
fun String.normalcase(): String{
    return this.toLowerCase().capitalize()
}

//fun ImageView.loadImage2(fullImageUrl: String, defaultImage:Int, view: ProvidersAdaptor) {
//
//    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
//    Glide.with(context).load(fullImageUrl).apply(requestOptions).error(defaultImage).into(this)
//}



fun String.toDateUpdate(): Date {
    val targetFormat = SimpleDateFormat("yyyy-MM-dd")
    return  targetFormat.parse(this)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}
fun String.toDate(orginalFormat:String): Date {
    val originalFormat = SimpleDateFormat(orginalFormat)
    val date = originalFormat.parse(this)
    // val targetFormat = SimpleDateFormat("dd MMM yy")
    return  originalFormat.parse(this)
}
fun String.toDate(): Date {
    val targetFormat = SimpleDateFormat("yyyy-MM-dd")
    return  targetFormat.parse(this)
}




fun Matcher.findAndPrint(){
    while (this.find()){
        Timber.i(this.group())
    }
}
fun Matcher.findAndPrint(bankName: String):String{
    while (this.find()){
        Timber.i("Transaction1: ${bankName}: ${this.group()}")
        return this.group()
    }
    return ""
}
fun String.formartDate(originalFormat:String,targetFormart:String):String{
    return try{
        val originalFormat = SimpleDateFormat(originalFormat)
        val targetFormat = SimpleDateFormat(targetFormart)
        targetFormat.format(originalFormat.parse(this))
    }catch (ex:Exception){
        this
    }

}
fun RecyclerView.Adapter<*>?.reloadRecycler(){
    this?.notifyDataSetChanged()
}

fun ImageView.loadImage(fullImageUrl: String, defaultImage:Int, view: Fragment) {
    Glide.with(view)
        .load(fullImageUrl)
        .signature(ObjectKey(System.currentTimeMillis().toString()))
        .error(defaultImage)
        .into(this)
}

fun String.formatNumberDollars():String{
    return try {
        "$${DecimalFormat("#,##0.00").format(java.lang.Double.parseDouble(this.stripAmount()))}"
    } catch (ex:java.lang.Exception){
        "$0.00"
    }
}


//fun ImageView.loadProfileImage(view: Fragment,paperPrefs: PaperPrefs){
//    this.loadImage("${Constants.BUCKETURL}${paperPrefs.getPhoneNumber()}_profilePic.png",
//        R.drawable.default_profile_image,view)
//}

fun ImageView.loadImage(fullImageUrl: String, defaultImage:Int, view: Context) {
    val requestOption = RequestOptions()
        .signature(ObjectKey(System.currentTimeMillis().toString()))
        .placeholder(defaultImage).centerCrop()
    Glide.with(view).load(fullImageUrl)
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(requestOption)
        .error(defaultImage)
        .into(this)
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(view).load(fullImageUrl).apply(requestOptions).error(defaultImage).into(this)
}


fun loadImage(context: Context,imagePath:String,imageView: ImageView){
    Glide.with(context)
        .asBitmap()
        .load(imagePath)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        })
}
fun getCategoryImage(tag:String):String{
    return "https://veezah.s3.amazonaws.com/drawable-xxxhdpi/${tag}.png"
}
fun loadChip(context: Context,imagePath:String,chip: Chip){
    Glide.with(context)
        .asBitmap()
        .load(imagePath)
//        .error(R.drawable.defaultbanklogo)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                chip.chipIcon = BitmapDrawable(context.resources,resource)

            }
            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        })
}
fun View.show(){this.visibility = View.VISIBLE}
fun View.invisible(){this.visibility = View.INVISIBLE}

fun EditText.getString():String{
    return this.text.toString()
}
fun String.formatNumber():String{
    return try {
        "₦${DecimalFormat("#,##0.00").format(java.lang.Double.parseDouble(this.stripAmount()))}"
    } catch (ex:java.lang.Exception){
        "₦0.00"
    }
}

fun String.formatNumberr():String{
    return try {
        "₦${DecimalFormat("#,##.00").format(java.lang.Double.parseDouble(this.stripAmount()))}"
    } catch (ex:java.lang.Exception){
        "₦0.00"
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}

fun String.normalCase():String{
    return this.toLowerCase().capitalize()
}

fun String.formatNumberOnly():String{
    return try {
        "${DecimalFormat("#,##0.00").format(java.lang.Double.parseDouble(this.stripAmount()))}"
    } catch (ex:java.lang.Exception){
        "0.00"
    }
}
fun String.stripAmount():String{
    return this.toString().replace("[$,£N₦[a-zA-Z]]".toRegex(), "")
}

fun Double.formatNumber():String{
    return "₦${DecimalFormat("#,##0.00").format(this)}"
}
fun ViewPager.onPageChange(action:(position:Int)-> Unit){
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            action(position)
        }

    })
}

fun abbreviate(firstInit:String):String{
    if(firstInit.isNullOrBlank()){
        return ""
    }
    return "${firstInit[0].toUpperCase()}"
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.toLowerCase().capitalize() }

fun Date.toDateString(targetFormart: String): String {
    val sdf = SimpleDateFormat(targetFormart)
    val calendar = Calendar.getInstance()
    calendar.time = this
    return sdf.format(calendar.time)
}

fun DatePicker.getDate(dateFormat:String):String{
    val day = this.dayOfMonth
    val month: Int = this.month
    val year: Int = this.year
    val calendar = Calendar.getInstance()
    calendar[year, month] = day
    val date =  calendar.time
    return date.toDateString(dateFormat)
}

fun DatePicker.setToTodayDate(){
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val  month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    this.updateDate(year,month,day)
}

fun ImageView.setDrawableImage(@DrawableRes resource: Int, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(resource)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}

fun ImageView.setLocalImage(file: File, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(file)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}

