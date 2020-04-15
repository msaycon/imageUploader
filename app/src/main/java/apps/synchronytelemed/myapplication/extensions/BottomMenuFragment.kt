package apps.synchronytelemed.myapplication.extensions

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import apps.synchronytelemed.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_menu.*
import java.util.*

/**
 * Created by msaycon on 30,Dec,2019
 */
class BottomMenuFragment : BottomSheetDialogFragment() {

    interface OnOptionSelectedListener {
        fun onOptionSelected(dialog: DialogInterface?, option: String?)
    }

    interface OnDialogClosed {
        fun onDialogClosed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false)
    }

    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(
            bottomSheet: View,
            newState: Int
        ) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(
            bottomSheet: View,
            slideOffset: Float
        ) {
        }
    }

    private var mOnOptionSelectedListener: OnOptionSelectedListener? = null

    private var mOnDialogClosed: OnDialogClosed? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments!!
        val title = args.getString(ARG_TITLE)

        val optionsList: List<String>? = args.getStringArrayList(ARG_LIST_OPTIONS)

        if (isNotEmpty(title)) {
            toolbar.visibility = View.VISIBLE
            titleMenu.text = title
        } else {
            toolbar.visibility = View.GONE
        }

        if (optionsList != null && optionsList.isNotEmpty()) {
            for (option in optionsList) {
                if (isNotEmpty(option)) {
                    val linearLayout = LayoutInflater.from(context).inflate(
                        R.layout.item_bottom_menu,
                        bottomMenu,
                        false
                    ) as LinearLayout
                    val relativeLayout =
                        linearLayout.findViewById<RelativeLayout>(R.id.optionContainer)
                    val optionTextView =
                        relativeLayout.findViewById<TextView>(R.id.optionText)
                    optionTextView.text = option
                    linearLayout.setOnClickListener {
                        val option1 = optionTextView.text.toString()
                        mOnOptionSelectedListener!!.onOptionSelected(
                            dialog,
                            option1
                        )
                    }
                    bottomMenu.addView(linearLayout)
                }
            }
        }

        if (view.layoutParams != null) {
            val params =
                view.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior

            if (behavior is BottomSheetBehavior<*>) {
                behavior.addBottomSheetCallback(mBottomSheetBehaviorCallback)
            }
        }
    }

    fun setOnOptionSelectedListener(onOptionSelectedListener: OnOptionSelectedListener?) {
        mOnOptionSelectedListener = onOptionSelectedListener
    }

    fun setOnDialogClosed(onDialogClosed: OnDialogClosed?) {
        mOnDialogClosed = onDialogClosed
    }

    private fun isNotEmpty(str: CharSequence?): Boolean {
        return str != null && str.toString().trim { it <= ' ' }.isNotEmpty()
    }

    override fun onDestroy() {
        if (mOnDialogClosed != null) {
            mOnDialogClosed!!.onDialogClosed()
        }
        super.onDestroy()
    }

    companion object {
        const val ARG_LIST_OPTIONS = "list_options"
        const val ARG_TITLE = "title"
        const val TAG = "BottomMenuFragment"

        fun newInstance(
            onOptionSelectedListener: OnOptionSelectedListener?,
            optionsList: List<String>?,
            title: String?
        ): BottomMenuFragment {
            val args = Bundle()
            args.putStringArrayList(
                ARG_LIST_OPTIONS,
                ArrayList(optionsList!!)
            )
            args.putString(ARG_TITLE, title)
            val fragment = BottomMenuFragment()
            fragment.setOnOptionSelectedListener(onOptionSelectedListener)
            fragment.arguments = args
            return fragment
        }
    }
}
