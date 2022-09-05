package com.pwnjeswani.locusassignment.ui.activity

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.gson.Gson
import com.pwnjeswani.locusassignment.R
import com.pwnjeswani.locusassignment.data.Constants.READ_REQUEST_CODE
import com.pwnjeswani.locusassignment.data.FieldData
import com.pwnjeswani.locusassignment.data.SharedPreferences
import com.pwnjeswani.locusassignment.databinding.ActivityMainBinding
import com.pwnjeswani.locusassignment.ui.adapter.ActionsListener
import com.pwnjeswani.locusassignment.ui.adapter.FieldsAdapter
import com.pwnjeswani.locusassignment.ui.fragment.LogsBottomSheet
import com.pwnjeswani.locusassignment.utils.Utils.loadJson
import com.pwnjeswani.locusassignment.utils.hasAllPermissionsGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, ActionsListener {

    val adapter by lazy { FieldsAdapter(this) }
    var fieldsList = arrayListOf<FieldData.FieldDataItem>()
    private lateinit var binding: ActivityMainBinding
    private var capturedImageUri: Uri? = null
    private var imgPath: String = ""
    private var camera_open_field_id = ""
    private lateinit var mPrefs: SharedPreferences
    private var logsBottomSheet: LogsBottomSheet? =
        null
    val imageCaptureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                capturedImageUri?.let { it ->
                    try {
                        val newUri = Uri.fromFile(File(imgPath))
                        for (i in fieldsList.indices) {
                            if (fieldsList[i].id == camera_open_field_id) {
                                fieldsList[i].localPhotoUrl = newUri.toString()
                                break
                            }
                        }
                        adapter.submitList(fieldsList)
                    } catch (ex: java.lang.Exception) {
                        Toast.makeText(
                            this,
                            "Error in camera, please try again..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPrefs = SharedPreferences(this.applicationContext)
        mPrefs.getSharePrefs()
        binding.rvFields.adapter = adapter
        val jsonString = loadJson(applicationContext)
        fieldsList = Gson().fromJson(jsonString, FieldData::class.java)
        adapter.submitList(fieldsList)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_REQUEST_CODE ->
                if (hasAllPermissionsGranted(grantResults)) {
                    openCamera()
                } else {
                    openCameraAndCapture()
                }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        checkIfPermanentlyDenied(perms as ArrayList<String>)
    }

    private fun checkIfPermanentlyDenied(list: ArrayList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            AppSettingsDialog.Builder(this)
                .setTitle(R.string.perm_required_title)
                .setRationale(R.string.perm_required)
                .setPositiveButton(R.string.perm_required_positive)
                .setNegativeButton(R.string.perm_required_negative)
                .build()
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.navigation_logs -> {
                val fieldData = mPrefs.fetchLogs()
                fieldData?.let {
                    openLogsBottomSheet(it)
                }
                if(fieldData.isNullOrEmpty()){
                    Toast.makeText(this, "Please Submit first", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.navigation_submit -> {
                mPrefs.saveLogs(list = fieldsList)
                Toast.makeText(this, "Saved Logs Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openCameraAndCapture() {
        if (EasyPermissions.hasPermissions(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        ) {
            openCamera()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    READ_REQUEST_CODE
                )
            }
        }
    }

    private fun openCamera() {
        capturedImageUri = setImageUri()
        imageCaptureLauncher.launch(capturedImageUri)
    }

    private fun setImageUri(): Uri {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val folder = File("${getExternalFilesDir(null)}")
        folder.mkdirs()
        val filename = "locus_image${System.currentTimeMillis()}.jpg"
        val file = File(folder, filename)
        file.delete()
        file.createNewFile()
        imgPath = file.absolutePath
        return FileProvider.getUriForFile(
            this,
            getString(R.string.content_provider),
            file
        )
    }

    private fun openLogsBottomSheet(fieldData: FieldData) {
        logsBottomSheet =
            LogsBottomSheet.newInstance(
                fieldData = fieldData
            )

        logsBottomSheet?.show(
            supportFragmentManager,
            LogsBottomSheet::class.simpleName,
        )
    }

    override fun imageClicked(id: String) {
        camera_open_field_id = id
        openCameraAndCapture()
    }

    override fun removeImageClicked(id: String) {
        for (i in fieldsList.indices) {
            if (fieldsList[i].id == id) {
                fieldsList[i].localPhotoUrl = null
            }
            adapter.submitList(fieldsList)
        }
    }

    override fun dataUpdated(id: String, value: String) {
        for (i in fieldsList.indices) {
            if (fieldsList[i].id == id) {
                fieldsList[i].enteredData = value
                break
            }
        }
    }
}
