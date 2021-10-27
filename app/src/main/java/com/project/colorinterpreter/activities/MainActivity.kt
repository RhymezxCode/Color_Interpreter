package com.project.colorinterpreter.activities

/**
 * Created by Awodire babajide samuel on 23/05/21.
 * rhymezxcode.github.io/rhymezxcode
 */

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.project.colorinterpreter.BuildConfig
import com.project.colorinterpreter.R
import com.project.colorinterpreter.utils.ProgressLoader
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val context = this@MainActivity
    private lateinit var image: ImageView
    private lateinit var bottom: View
    private lateinit var informationHeading: TextView
    private lateinit var information: TextView
    private lateinit var selectImage: Button
    private lateinit var imageFile: File
    private lateinit var help: Button
    private var bitmap: Bitmap? = null
    private var currentPhotoPath = ""
    private lateinit var progress: ProgressLoader

    //keep track of camera intent
    private val REQUEST_TAKE_PHOTO = 2
    private val REQUEST_SELECT_IMAGE = 1
    private var backPass: Long? = 0
    private var filePath: String? = null

    private var colorHash : HashMap<String, String?> = HashMap()


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image)
        informationHeading = findViewById(R.id.information_heading)
        information = findViewById(R.id.information)
        selectImage = findViewById(R.id.select_image)
        bottom = findViewById(R.id.bottom)
        help = findViewById(R.id.help)
        selectImage.setOnClickListener(context)
        progress = ProgressLoader(this)

        image.isDrawingCacheEnabled = true
        image.buildDrawingCache(true)



        colorHash.put("ff826464", "0.1 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff886324", "0.2 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa8807e", "0.3 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa88c88", "0.4 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9d7c75", "0.5 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa2817c", "0.6 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff988984", "0.7 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9b8985", "0.8 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff988984", "0.9 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff957d79", "1.0 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9b6058", "1.1 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff966056", "1.2 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9b5f54", "1.3 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9d6a59", "1.4 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9b6f66", "1.5 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9ebd5e", "1.6 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9f665f", "1.7 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff937471", "1.8 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9a7e7d", "1.9 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9c7d7b", "2.0 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff94766e", "2.1 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff9e7c70", "2.2 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff96e6a", "2.3 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff987265", "2.4 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff946450", "2.5 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff988682", "2.6 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff948985", "2.7 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff947875", "2.8 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff927f7b", "2.9 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff8055fc", "3.0 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa37e6b", "3.1 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa47966", "3.2 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffaea8a8", "3.3 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa3969", "3.4 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff959190", "3.5 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa19997", "3.6 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff936d64", "3.7 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff7a7879", "3.8 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffaa827e", "3.9 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff956875", "4.0 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff956f5c", "4.1 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff85675f", "4.2 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff85534c", "4.3 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff946esb", "4.4 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff956d61", "4.5 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff96756e", "4.6 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff976d5d", "4.7 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff8b5247", "4.8 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff8f6360", "4.9 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff895446", "5.0 (low cholesterol levels. Healthy condition)")
        colorHash.put("ff045e56", "5.1 (low cholesterol levels. Healthy condition)")
        colorHash.put("ffa15e58", "5.2 (low cholesterol levels. Healthy condition)")

        colorHash.put("ffa25648", "5.3 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9a5653", "5.4 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9b6a6a", "5.5 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff965956", "5.6 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff95b58", "5.7 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff985c5c", "5.8 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff92412c", "5.9 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9c7b72", "6.0 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9b6251", "6.1 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff837a7d", "6.2 (High cholesterol level, but within healthy limit)")

        colorHash.put("ffac8a7e", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa06047", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff8f7f80", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff8e7169", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa56a58", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa78385", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9f6057", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa78586", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa49898", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa3706d", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff8ebdd64", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff8dbf64", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa58181", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffaa8c8a", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa99d9d", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa16a65", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa85c52", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa45e45", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa9b8c5", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff6e5c58", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff7e7272", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff696461", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff725952", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff8c7a7a", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff855d4c", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff7e5f5bc", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9c8782", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff764e34", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa59d9b", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff96827b", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffb2b0b1", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff887274", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff907268", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ff9f725d", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa36f61", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa17977", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa2685a", "> 6.2 (High cholesterol level, but within healthy limit)")
        colorHash.put("ffa29e9f", "> 6.2 (High cholesterol level, but within healthy limit)")






        help.setOnClickListener {
            TapTargetSequence(context)
                .targets(
                    TapTarget.forView(
                        findViewById(R.id.select_image),
                        "Select Image!",
                        "This button is used to take a picture using the photo's camera."
                    )
                        .outerCircleColor(R.color.black)
                        .descriptionTextColor(R.color.white)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextSize(20)
                        .textColor(R.color.white)
                        .dimColor(R.color.white)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),

                    TapTarget.forView(
                        findViewById(R.id.image),
                        " ",
                        "This is where the photo taken will appear, to get the color from" +
                                " this photo, you can touch the exact spot."
                    )
                        .outerCircleColor(R.color.black)
                        .descriptionTextColor(R.color.white)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextSize(20)
                        .textColor(R.color.white)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),

                    TapTarget.forView(
                        findViewById(R.id.bottom),
                        "Color from the image!",
                        "This is where the color from the image will appear, but" +
                                " you will be required to touch the spot you want to get the color from on that image."
                    )
                        .outerCircleColor(R.color.black)
                        .descriptionTextColor(R.color.white)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextSize(20)
                        .textColor(R.color.white)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),

                    TapTarget.forView(
                        findViewById(R.id.information_heading),
                        "Hex code!",
                        "This is where the hex code of the color is going to appear"
                    )
                        .outerCircleColor(R.color.black)
                        .descriptionTextColor(R.color.white)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextSize(20)
                        .textColor(R.color.white)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60),
                    TapTarget.forView(
                        findViewById(R.id.information),
                        "Full description!",
                        "This is where the information about the color will appear."
                    )
                        .outerCircleColor(R.color.black)
                        .descriptionTextColor(R.color.white)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextSize(20)
                        .textColor(R.color.white)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .targetRadius(60)



                )
                .listener(object : TapTargetSequence.Listener {
                    override fun onSequenceFinish() {}
                    override fun onSequenceStep(lastTarget: TapTarget, targetClicked: Boolean) {}
                    override fun onSequenceCanceled(lastTarget: TapTarget) {}
                }).start()
        }


        image.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    try {
                        bitmap = image.getDrawingCache()
                        val pixel = bitmap!!.getPixel(event.x.toInt(), event.y.toInt())

                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)

                        var color = Color.TRANSPARENT

                        val background = bottom.background

                        if (background is ColorDrawable) {
                            color = background.color
                        }

                        bottom.setBackgroundColor(Color.rgb(r, g, b))
                        informationHeading.setText(
                            "Result (mmol/L) : #" + Integer.toHexString(color),
                            TextView.BufferType.EDITABLE
                        )
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }

                MotionEvent.ACTION_MOVE -> {
                    try {
                        bitmap = image.getDrawingCache()
                        val pixel = bitmap!!.getPixel(event.x.toInt(), event.y.toInt())

                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)

                        var color = Color.TRANSPARENT

                        val background = bottom.background

                        if (background is ColorDrawable) {
                            color = background.color
                        }

                        bottom.setBackgroundColor(Color.rgb(r, g, b))
                        informationHeading.setText(
                            "Result (mmol/L) : #" + Integer.toHexString(color),
                            TextView.BufferType.EDITABLE
                        )
                        information.setText(
                            "Result (mmol/L) : #" + Integer.toHexString(color),
                            TextView.BufferType.EDITABLE
                        )
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

                MotionEvent.ACTION_UP -> {


                }

                else -> {


                }
            }
            true
        }


    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.select_image -> {
                    Dexter.withActivity(context)
                        .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        .withListener(object : MultiplePermissionsListener {
                            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                                report?.let {
                                    if (report.areAllPermissionsGranted()) {
                                        takePhotoFromCamera()
                                    } else if (report.isAnyPermissionPermanentlyDenied) {
                                        showSettingsDialog()
                                    }
                                }
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                                token: PermissionToken?
                            ) {
                                token?.continuePermissionRequest()
                            }
                        })
                        .withErrorListener {
                            snackShow(it.name)
                        }
                        .check()
                }
            }
        }

    }

    @Suppress("DEPRECATION")
    private fun getImageFile(): File {
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
//        val storageDir = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
//            "Camera"
//        )
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file: File = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPhotoPath = "file:" + file.absolutePath
        return file
    }

    private fun snackShow(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
//            .setAspectRatio(4, 3)
            .setCropShape(CropImageView.CropShape.RECTANGLE) // default is rectangle
            .start(this)
    }

    fun selectDialog() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_selector, null, false)
        val selectDialog = Dialog(context)
        selectDialog.setCancelable(true)
        selectDialog.setContentView(view)
        val selectImage = view.findViewById<TextView>(R.id.select_image)
        val takePhoto = view.findViewById<TextView>(R.id.take_photo)
        selectImage.setOnClickListener {
            selectDialog.cancel()
            takePhotoFromPhone()
        }
        takePhoto.setOnClickListener {
            selectDialog.cancel()
            takePhotoFromCamera()
        }
        selectDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        selectDialog.show()
    }

    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog, _ ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun takePhotoFromCamera() = try {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = getImageFile()
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
        } else {
            Uri.fromFile(file)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    } catch (err: ActivityNotFoundException) {
        Log.v("error:", err.toString())
        Snackbar.make(
            findViewById(android.R.id.content),
            "error: $err",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun takePhotoFromPhone() = try {
        val intent = Intent(
            Intent.ACTION_PICK
        )
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_SELECT_IMAGE)
    } catch (err: ActivityNotFoundException) {
        Log.v("error:", err.toString())
        Snackbar.make(
            findViewById(android.R.id.content),
            "error: $err",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = Uri.parse(currentPhotoPath)
                    launchImageCrop(uri)
                } else {
                    Log.v("Image error:", "Couldn't select that image from camera.")
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Image selection error: Couldn't select that image from camera.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            REQUEST_SELECT_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri? = data!!.data
                    launchImageCrop(uri!!)
                } else {
                    Log.v("Image error:", "Couldn't select that image from camera.")
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Image selection error: Couldn't select that image from camera.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    if (result != null) {
                        try {

                            image.setImageBitmap(null)
                            image.destroyDrawingCache()
                            image.setImageURI(result.uri)

                            try {
                                bitmap = image.getDrawingCache()
                                val pixel = bitmap!!.getPixel(image.x.toInt(), image.y.toInt())

                                val r = Color.red(pixel)
                                val g = Color.green(pixel)
                                val b = Color.blue(pixel)

                                var color = Color.TRANSPARENT

                                val background = bottom.background

                                if (background is ColorDrawable) {
                                    color = background.color
                                }
                                bottom.setBackgroundColor(Color.rgb(r, g, b))
                                informationHeading.setText(
                                    "Result (mmol/L) : #" + Integer.toHexString(color),
                                    TextView.BufferType.EDITABLE
                                )
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }

                            filePath = getFilePath(context, result.uri)

                            imageFile = File(filePath!!)
//                            Snackbar.make(
//                                    findViewById(android.R.id.content),
//                                    "file path: $file_path",
//                                    Snackbar.LENGTH_LONG
//                            ).show()
//
//                            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                        }

                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.v("Crop error: ", result.error.toString())
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Crop error: ${result.error}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    @SuppressLint("NewApi")
    @Throws(URISyntaxException::class)
    fun getFilePath(context: Context, uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
                context.applicationContext,
                uri
            )
        ) {
            when {
                isExternalStorageDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
                isDownloadsDocument(uri) -> {
                    val id = DocumentsContract.getDocumentId(uri)
                    uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                }
                isMediaDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    when (split[0]) {
                        "image" -> {
                            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }
                        "video" -> {
                            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        }
                        "audio" -> {
                            uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                    }
                    selection = "_id=?"
                    selectionArgs = arrayOf(
                        split[1]
                    )
                }
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            if (isGooglePhotosUri(uri)) {
                return uri.lastPathSegment
            }
            val projection = arrayOf(
                MediaStore.Images.Media.DATA
            )
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                    .query(uri, projection, selection, selectionArgs, null)
                val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    override fun onBackPressed() {
        if (backPass!! + 2000 > System.currentTimeMillis()) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
            finishAffinity()
        } else {
            snackShow("Touch again to exit")
            backPass = System.currentTimeMillis()
        }
    }

}