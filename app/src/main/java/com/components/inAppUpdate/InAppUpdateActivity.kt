package com.components.inAppUpdate

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.components.R
import com.components.databinding.ActivityInAppUpdateBinding
import com.components.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class InAppUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInAppUpdateBinding
    private var mAppUpdateManager: AppUpdateManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInAppUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        mAppUpdateManager = AppUpdateManagerFactory.create(this)

        // we will get the updated information from playstore
        mAppUpdateManager?.getAppUpdateInfo()?.addOnSuccessListener { result ->
            // checking for the app update availability
            // will block the user from accessing the app if update is of immediate type
            // will allow the user to access the app while the update will install in background
            // for flexible update pass AppUpdateType.FLEXIBLE
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    mAppUpdateManager?.startUpdateFlowForResult(
                        result,
                        AppUpdateType.IMMEDIATE,
                        this@InAppUpdateActivity,
                        InAppUpdateActivity.RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }

        // registered the InstallStateUpdatedListener to show the status of update
        mAppUpdateManager?.registerListener(mInstallStateUpdateListener!!)
    }

    //To check the status of the update whether downloaded, failed
    private var mInstallStateUpdateListener: InstallStateUpdatedListener? =
        InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate()
            }
        }

    override fun onStop() {
        if(mAppUpdateManager!= null) mAppUpdateManager?.unregisterListener(mInstallStateUpdateListener!!);
        super.onStop()
    }

    // creating a snackbar with a button to install
    private fun showCompletedUpdate() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content), "New App is Ready!!",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(
            "Install"
        ) { mAppUpdateManager!!.completeUpdate() }
        snackbar.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        mAppUpdateManager!!.appUpdateInfo.addOnSuccessListener { result ->
            if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    mAppUpdateManager!!.startUpdateFlowForResult(
                        result,
                        AppUpdateType.IMMEDIATE, // for flexible update we may specify AppUpdateType.FLEXIBLE
                        this@InAppUpdateActivity,
                        RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val RC_APP_UPDATE = 100
    }
}