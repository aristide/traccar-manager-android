@file:Suppress("DEPRECATION")
package org.atmmotors.maaeko

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import org.atmmotors.maaeko.utils.UrlChecker

class NoInternetFragment : Fragment(), View.OnClickListener, UrlChecker.OnUrlCheckListener {

    private lateinit var tryAgainButton: Button
    private lateinit var loadingBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_no_internet, container, false)
        loadingBar = view.findViewById(R.id.NoInternetLoadingBar)
        tryAgainButton = view.findViewById(R.id.noInternetTryButton)
        tryAgainButton.setOnClickListener(this)
        return view
    }

    @SuppressLint("StaticFieldLeak")
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.noInternetTryButton->{
                loadingBar.visibility = View.VISIBLE
                tryAgainButton.isEnabled = false
                UrlChecker(this, view.context).execute(BuildConfig.PREFERENCE_URL)
            }
        }
    }

    override fun onUrlCheckCompleted(isWorking: Boolean, context: Context?) {
        loadingBar.visibility = View.GONE
        if(isWorking){
            openMainFragment()
        }else{
            tryAgainButton.isEnabled = true
        }
    }

    private fun openMainFragment() {
        activity.fragmentManager
            .beginTransaction().replace(android.R.id.content, MainFragment())
            .commitAllowingStateLoss()
    }
}