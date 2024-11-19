@file:Suppress("PackageDirectoryMismatch")

package com.gabreucast.projetotodolist.Fragmets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gabreucast.projetotodolist.R

class FragmentPrincipalEdit : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_principal_edit, container, false)

        val cancelTextView = view.findViewById<TextView>(R.id.cancel)

        cancelTextView.setOnClickListener {
            dismiss()
        }

        return view
    }
}
