package com.mobithink.cyclobase.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mobithink.velo.carbon.R
import kotlinx.android.synthetic.main.fab_with_textview.view.*

class FabWithTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.fab_with_textview, this, true)

        orientation = HORIZONTAL

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.FabWithTextView, defStyle, defStyleRes)

            fab_tv.setText(
                    resources.getText(typedArray.getResourceId(
                            R.styleable
                            .FabWithTextView_eventName,
                            R.string.default_event_label)))

            typedArray.recycle()
        }
    }


    fun getEvent() : String {
        return  fab_tv.text.toString()
    }
}