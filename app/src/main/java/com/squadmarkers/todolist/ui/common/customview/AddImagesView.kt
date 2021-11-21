package com.squadmarkers.todolist.ui.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.squadmarkers.todolist.R
import com.squadmarkers.todolist.databinding.AddImageLayoutBinding

class AddImagesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: AddImageLayoutBinding? = AddImageLayoutBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DsAddPhoto,
            DEFAULT_VALUE, DEFAULT_VALUE

        ).apply {
            try {
                getText(R.styleable.DsAddPhoto_ds_text)?.toString()?.let { text ->
                    setMessage(text)
                }
            } finally {
                recycle()
            }
        }
    }

    @JvmOverloads
    constructor(context: Context, parentView: ConstraintLayout) : this(context) {
        setListener()
    }

    var onAddImageClicked: (() -> Unit)? = null

    private fun setMessage(text: String) {
        _binding?.textMessage?.text = text
    }

    private fun setListener() {
        _binding?.content?.setOnClickListener {
            onAddImageClicked?.invoke()
        }
    }

    companion object {
        private const val DEFAULT_VALUE = 0
    }

}