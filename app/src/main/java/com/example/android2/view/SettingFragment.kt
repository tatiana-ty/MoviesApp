package com.example.android2.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import com.example.android2.R

const val INCLUDE_ADULT = "INCLUDE_ADULT"
var ADULT = false

class SettingFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ADULT = activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(INCLUDE_ADULT, false)!!
        val buttonAply = view.findViewById<Button>(R.id.buttonApply)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        checkBox.setChecked(ADULT)
        buttonAply.setOnClickListener() {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref!!.edit()
            if (checkBox.isChecked) {
                editor.putBoolean(INCLUDE_ADULT, true)
            } else {
                editor.putBoolean(INCLUDE_ADULT, false)
            }
            editor.apply()
        }
    }

    companion object {
        fun newInstance() = SettingFragment()
    }

}