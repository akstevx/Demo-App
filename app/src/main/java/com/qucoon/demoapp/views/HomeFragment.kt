package com.qucoon.demoapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qucoon.demoapp.R
import com.qucoon.demoapp.base.BaseFragment
import com.qucoon.demoapp.base.observeChange
import com.qucoon.demoapp.models.Car
import com.qucoon.demoapp.utils.formatNumberDollars
import com.qucoon.demoapp.utils.loadImage
import com.qucoon.demoapp.utils.updateRecycler
import com.qucoon.demoapp.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment() {
    private val carViewModel: CarViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        setObservers()
    }

    private fun updateUI() {
        carViewModel.getCarList()
    }

    private fun setObservers() {
        setUpObservers(carViewModel)
        carViewModel.getCarsResponse.observeChange(viewLifecycleOwner){
            updateRecycler(it.data)
        }
    }

    private fun updateRecycler(carList: List<Car>) {
        println("listOfCars: ${carList.size}")
        recyclerView.updateRecycler(requireContext(), carList.take(10), R.layout.item_car, listOf(R.id.txtPriceTag,R.id.txtCarModel, R.id.txtHorsepower,
            R.id.txtReleaseYear, R.id.ivCarPicture), { innerViews, position ->
            val amount = innerViews[R.id.txtPriceTag] as TextView
            val model = innerViews[R.id.txtCarModel] as TextView
            val horsePower = innerViews[R.id.txtHorsepower] as TextView
            val year = innerViews[R.id.txtReleaseYear] as TextView
            val image = innerViews[R.id.ivCarPicture] as ImageView
            val item = carList[position]

            amount.text = item.price.toString().formatNumberDollars()
            model.text = "${item.make} ${item.model}"
            horsePower.text = "${item.horsepower } Mph"
            year.text = item.year.toString()
            image.loadImage(fullImageUrl = item.img_url, defaultImage = R.drawable.dummy_car, view = this)
        }, { position ->
            val item = carList[position]
            mFragmentNavigation.pushFragment(ViewDetailsFragment.newInstance(item))
        }, listOf(imageView2,textView2 ))
    }

}