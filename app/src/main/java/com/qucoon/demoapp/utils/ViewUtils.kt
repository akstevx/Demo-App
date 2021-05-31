package com.qucoon.demoapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.PageTransformer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil
import kotlin.math.abs


data class ViewPagerObject(val fragment: Fragment, val title:String)

fun <T:Any> RecyclerView.updateRecycler(context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit,noImageViews:List<View>): RecyclerView.Adapter<*>? {
    if(listOfItems.isNullOrEmpty()){
        this.visibility = View.GONE
        noImageViews.forEach { it.visibility = View.VISIBLE }
    }else{
        this.visibility = View.VISIBLE
        noImageViews.forEach { it.visibility = View.GONE }
    }
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}


fun RecyclerView.updateRecycler(context: Context, listOfItems:List<Any>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor

    return adapter
}


fun RecyclerView.updateRecycler(context: Context, listOfItems:List<Any>, layout:Int, layoutManager: GridLayoutManager, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager =layoutManager
    val reyclerAdaptor = RecyclerAdapterUtil(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor

    return adapter
}
fun RecyclerView.updateRecycler(context: Context, listOfItems:List<Any>, layout:Int, layoutManager: GridLayoutManager, itemDecoration: GridSpacingItemDecoration, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager =layoutManager
    this.addItemDecoration(itemDecoration)
    val reyclerAdaptor = RecyclerAdapterUtil(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor

    return adapter
}

/**
 * Start an Activity for given class T and allow to work on intent with "run" lambda function
 */
fun   <T:Fragment> T.withArguments(vararg arguments: Pair<String, java.io.Serializable>): T {
    val bundle = Bundle()
    arguments.forEach { bundle.putSerializable(it.first, it.second) }
    this.arguments = bundle
    return this
}
/**
 * Start an Activity for given class T and allow to work on intent with "run" lambda function
 */
fun BottomSheetDialogFragment.withArguments(arguments: Array<Pair<String, java.io.Serializable>>): BottomSheetDialogFragment {
    val bundle = Bundle()
    arguments.forEach { bundle.putSerializable(it.first, it.second) }
    this.arguments = bundle
    return this
}
/**
 * Retrieve property from intent
 */
fun <T : Any> FragmentActivity.argument(key: String) = lazy { intent.extras?.get(key) as T }
/**
 * Retrieve property with default value from intent
 */
fun <T : Any> FragmentActivity.argument(key: String, defaultValue: T? = null) = lazy {
    intent.extras?.get(key) as? T ?: defaultValue
}
/**
 * Retrieve property from intent
 */
fun <T : Any> Fragment.argument(key: String) = lazy { arguments?.get(key) as T }
fun <T : Any> BottomSheetDialogFragment.argument(key: String) = lazy { arguments?.get(key) as T }
/**
 * Retrieve property with default value from intent
 */
fun <T : Any> Fragment.argumentNullable(key: String) = lazy { arguments?.get(key) as T? }

fun <T : Any> Fragment.argument(key: String, defaultValue: T? = null) = lazy {
    arguments?.get(key)  as? T ?: defaultValue
}



fun ViewPager.setUpViewPager(viewPagerObjectList:List<ViewPagerObject>, fragmentStateManager: FragmentManager){
    val pagerAdapter = MyViewPageStateAdapter(fragmentStateManager)
    viewPagerObjectList.forEach {
        pagerAdapter.addFragment(it.fragment,it.title)
    }
    this.apply {
        adapter = pagerAdapter
    }
}

fun ViewPager.setUpViewPager(viewPagerObjectList:List<ViewPagerObject>, fragmentStateManager: FragmentManager, transform: ViewPager.PageTransformer){
    val pagerAdapter = MyViewPageStateAdapter(fragmentStateManager)
    viewPagerObjectList.forEach {
        pagerAdapter.addFragment(it.fragment,it.title)
    }
    this.apply {
        adapter = pagerAdapter
        setPageTransformer(true,transform)
    }
}
fun ViewPager.setUpViewPager(viewPagerObjectList:List<ViewPagerObject>, transform: ViewPager.PageTransformer, onPageChangeListener: ViewPager.OnPageChangeListener, fragmentStateManager: FragmentManager){
    val pagerAdapter = MyViewPageStateAdapter(fragmentStateManager)
    viewPagerObjectList.forEach {
        pagerAdapter.addFragment(it.fragment,it.title)
    }
    this.apply {
        adapter = pagerAdapter
        setPageTransformer(true,transform)
        addOnPageChangeListener(onPageChangeListener)
    }
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


fun ViewPager.moveForward(){
    val nextIndex = this.currentItem + 1
    this.setCurrentItem(nextIndex,true)
}
fun ViewPager.moveBackward(){
    val nextIndex = this.currentItem - 1
    this.setCurrentItem(nextIndex,true)
}

class DepthTransformation : PageTransformer {
    override fun transformPage(
        page: View,
        position: Float
    ) {
        when {
            position < -1 -> {    // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0f
            }
            position <= 0 -> {    // [-1,0]
                page.alpha = 1f
                page.translationX = 0f
                page.scaleX = 1f
                page.scaleY = 1f
            }
            position <= 1 -> {    // (0,1]
                page.translationX = -position * page.width
                page.alpha = 1 - abs(position)
                page.scaleX = 1 - abs(position)
                page.scaleY = 1 - abs(position)
            }
            else -> {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f
            }
        }
    }
}

class ZoomOutTransformation : PageTransformer {
    override fun transformPage(
        page: View,
        position: Float
    ) {
        if (position < -1) {  // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0f
        } else if (position <= 1) { // [-1,1]
            page.scaleX = Math.max(
                MIN_SCALE,
                1 - Math.abs(position)
            )
            page.scaleY = Math.max(
                MIN_SCALE,
                1 - Math.abs(position)
            )
            page.alpha = Math.max(
                MIN_ALPHA,
                1 - Math.abs(position)
            )
        } else {  // (1,+Infinity]
            // This page is way off-screen to the right.
            page.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.65f
        private const val MIN_ALPHA = 0.3f
    }
}

class HingeTransformation : PageTransformer {
    override fun transformPage(
        page: View,
        position: Float
    ) {
        page.translationX = -position * page.width
        page.pivotX = 0f
        page.pivotY = 0f
        if (position < -1) {    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0f
        } else if (position <= 0) {    // [-1,0]
            page.rotation = 90 * Math.abs(position)
            page.alpha = 1 - Math.abs(position)
        } else if (position <= 1) {    // (0,1]
            page.rotation = 0f
            page.alpha = 1f
        } else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.alpha = 0f
        }
    }
}
 class LinearLayoutThatDetectsSoftKeyboard(
    context: Context?,
    attrs: AttributeSet?
) :
    LinearLayout(context, attrs) {
    interface Listener {
        fun onSoftKeyboardShown(isShowing: Boolean)
    }

    private var listener: Listener? = null
    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val activity = context as Activity
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        val statusBarHeight: Int = rect.top
        val screenHeight = activity.windowManager.defaultDisplay.height
        val diff = screenHeight - statusBarHeight - height
        if (listener != null) {
            listener!!.onSoftKeyboardShown(diff > 120) // assume all soft keyboards are at least 128 pixels high
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}









