package com.vietgurus.common.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RelativeLayout

class ViewUtils {
    companion object {
        //*******************************************
        //* All functions properties, listener
        //*******************************************
        fun setOnCheckedChangeListener(pCheckChangeLst: List<View>? = null, pCheckChange : CompoundButton.OnCheckedChangeListener?){
            pCheckChangeLst?.forEach {
                when(it){
                    is RadioButton -> {
                        var mRadioButton: RadioButton = it
                        mRadioButton.setOnCheckedChangeListener(pCheckChange)
                    }
                }
            }
        }

        fun setCheckFalse(pCheckChangeLst: List<View>? = null, pValueFale: Boolean = false ){
            pCheckChangeLst?.forEach {
                when(it){
                    is RadioButton -> {
                        var mRadioButton: RadioButton = it
                        mRadioButton.isChecked = pValueFale
                    }
                }
            }
        }

        //*******************************************
        //* All functions Check Validate
        //*******************************************
        fun checkValidateEditText(context: Context, pEditText: EditText, pResId: Int): Boolean{
            val value = pEditText.text.toString()
            if (value.isEmpty()){
                pEditText.error = context.getString(pResId)
                pEditText.requestFocus()
                return false
            }
            return true
        }

        fun checkValidateEditText(fragment: Fragment, pEditText: EditText, pResId: Int): Boolean{
            val value = pEditText.text.toString()
            if (value.isEmpty()){
                pEditText.error = fragment.getString(pResId)
                pEditText.requestFocus()
                return false
            }
            return true
        }

        fun checkValidateEditTextWithOption(context: Context, pRadioButton: RadioButton, pEditText: EditText, pResId: Int): Boolean{
            val value = pEditText.text.toString()
            if (pRadioButton.isChecked && value.isEmpty()){
                pEditText.error = context.getString(pResId)
                pEditText.requestFocus()
                return false
            }
            return true
        }

        //*******************************************
        //* All functions key board, fragment
        //*******************************************
        fun hideKeyboard(context: Context?, root: RelativeLayout) {
            context?.let {
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(root.windowToken, 0)
            }

        }

        fun hideFragment(activity: FragmentActivity?, fragment: Fragment) {
            activity?.supportFragmentManager?.beginTransaction()?.hide(fragment)?.commit()
        }

        fun hiddenKeyBoard(main_comment: ViewGroup) {
            // Set a click listener for CoordinatorLayout
            main_comment.setOnClickListener { view ->
                // Get the input method manager
                val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                // Hide the soft keyboard
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}