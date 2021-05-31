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
import com.qucoon.demoapp.models.Car
import com.qucoon.demoapp.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_view_details.*
import java.io.Serializable

class ViewDetailsFragment : BaseFragment() {
    private val car:Car by argument(CAR)

    data class Comment(
        val comment:String,
        val name: String,
        val hours: String
    )

    val listOfComments = listOf<Comment>(
        Comment("Really nice car", "Angel Benita", "2hrs"),
        Comment("Best car deals here !!", "Steven David","16hrs" ),
        Comment("I want this right now", "Temi Olakunle", "2 days")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener { mFragmentNavigation.popFragment() }
        btnbookProduct.setOnClickListener { showToast("Product has been booked") }
        btnContactAgent.setOnClickListener { showToast("Agent has been contacted") }
        txtProductAmount.text = car.price.toString().formatNumberDollars()
        txtProductTitle.text = "${car.make} ${car.model}".capitalizeWords()
        setUpCommentRecycler()
    }

    private fun setUpCommentRecycler() {
        recyclerComments.updateRecycler(requireContext(), listOfComments, R.layout.comment_item,
            listOf(R.id.ivCommentDp, R.id.txtCommentName, R.id.txtCommentText, R.id.txtCommentTime),
            { innerViews, position ->
                val name = innerViews[R.id.txtCommentName] as TextView
                val text = innerViews[R.id.txtCommentText] as TextView
                val time = innerViews[R.id.txtCommentTime] as TextView
                val comment = listOfComments[position]

                name.text = comment.name
                text.text = comment.comment
                time.text = "${comment.hours}"
            },
            { position ->

            }
        )
    }


    companion object {
        val CAR = "car"
        fun newInstance(car:Car): ViewDetailsFragment{
            return ViewDetailsFragment().withArguments(CAR to car as Serializable)
        }
    }
}