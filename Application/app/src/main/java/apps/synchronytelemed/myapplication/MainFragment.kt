package apps.synchronytelemed.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import apps.synchronytelemed.myapplication.adapters.ImageListAdapter
import apps.synchronytelemed.myapplication.base.BaseFragment
import apps.synchronytelemed.myapplication.extensions.BottomMenuFragment
import apps.synchronytelemed.myapplication.extensions.observe
import apps.synchronytelemed.myapplication.extensions.viewModel
import apps.synchronytelemed.myapplication.repository.data.ImageFile
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.jakewharton.rxbinding3.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_main.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by msaycon on 15,Apr,2020
 */
class MainFragment : BaseFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var adapter: ImageListAdapter

    private lateinit var imagePicker: EasyImage

    private lateinit var rxPermissions: RxPermissions

    private lateinit var fragment: Fragment

    override fun layoutId() = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initializeViewModel()

        imagePicker = EasyImage.Builder(context!!)
            .allowMultiple(true)
            .build()
        rxPermissions = RxPermissions(this)
        fragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        //Retrieve Image Files
        mainViewModel.getImageFiles()
    }

    //Initialize View Model
    //Handle View Model Results
    override fun initializeViewModel() {
        mainViewModel = viewModel(viewModelFactory) {
            observe(imageList, ::handleImageList)
            observe(failure, {
                notify(it?.getMessage!!)
            })
        }
    }

    @SuppressLint("CheckResult")
    private fun init() {
        //Initialize List View
        imageListView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        imageListView.adapter = adapter
        adapter.clickListener = { imageFile: ImageFile, view: View ->
            when (view.id) {
                R.id.imageThumb -> {
                    val action = MainFragmentDirections.navMainToNavImageViewer(imageFile)
                    findNavController().navigate(action)
                }
            }
        }

        adapter.onLongClickListener = { imageFile, position ->
            val options = ArrayList<String>()
            options.add(getString(R.string.label_delete))

            if (imageFile.caption.isNullOrEmpty()) {
                options.add(getString(R.string.label_add_caption))
            } else {
                options.add(getString(R.string.label_edit_caption))
            }

            val imageSelection =
                BottomMenuFragment.newInstance(object :
                    BottomMenuFragment.OnOptionSelectedListener {
                    override fun onOptionSelected(dialog: DialogInterface?, option: String?) {
                        dialog?.dismiss()
                        when (option) {
                            getString(R.string.label_delete) -> {
                                mainViewModel.deleteImageFile(imageFile)
                            }
                            getString(R.string.label_add_caption) -> {
                                MaterialDialog(context!!).show {
                                    title(R.string.label_add_caption)
                                    input { _, charSequence ->
                                        val newImageFile = ImageFile(
                                            imageFile.id,
                                            imageFile.time,
                                            imageFile.filePath,
                                            charSequence.toString()
                                        )
                                        mainViewModel.saveImageFile(newImageFile)
                                    }
                                    getInputField().background = null
                                    positiveButton(R.string.label_save)
                                    negativeButton(R.string.label_cancel)
                                }
                            }
                            getString(R.string.label_edit_caption) -> {
                                MaterialDialog(context!!).show {
                                    title(R.string.label_edit_caption)
                                    input(
                                        prefill = imageFile.caption,
                                        callback = { _, charSequence ->
                                            val newImageFile = ImageFile(
                                                imageFile.id,
                                                imageFile.time,
                                                imageFile.filePath,
                                                charSequence.toString()
                                            )
                                            mainViewModel.saveImageFile(newImageFile)
                                        })
                                    getInputField().background = null
                                    positiveButton(R.string.label_save)
                                    negativeButton(R.string.label_cancel)
                                }
                            }
                        }
                    }
                }, options, null)
            imageSelection.show(activity?.supportFragmentManager!!, BottomMenuFragment.TAG)
            true
        }

        // Function to Add Image
        actionAddImage.clicks()
            .throttleFirst(1, TimeUnit.SECONDS)
            // Check required permissions
            .concatMap {
                rxPermissions.requestEachCombined(
                    Manifest.permission.CAMERA
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
            .subscribe {
                if (it.granted) {
                    // Show image options
                    showAddImageOptions()
                } else {
                    // Show error message
                    notify(getString(R.string.media_permission))
                }
            }
    }

    //Image Options
    //Camera or Gallery
    private fun showAddImageOptions() {
        val imageOptions = ArrayList<String>()
        imageOptions.add(getString(R.string.label_take_photo))
        imageOptions.add(getString(R.string.label_choose_gallery))

        val imageSelection =
            BottomMenuFragment.newInstance(object : BottomMenuFragment.OnOptionSelectedListener {
                override fun onOptionSelected(dialog: DialogInterface?, option: String?) {
                    dialog?.dismiss()
                    when (option) {
                        getString(R.string.label_take_photo) -> imagePicker.openCameraForImage(
                            fragment
                        )
                        getString(R.string.label_choose_gallery) -> imagePicker.openGallery(fragment)
                    }
                }
            }, imageOptions, null)
        imageSelection.show(activity?.supportFragmentManager!!, BottomMenuFragment.TAG)
    }

    private fun handleImageList(imageList: ArrayList<ImageFile>?) {
        if (imageList != null) {
            emptyView.visibility = View.GONE
            imageListView.visibility = View.VISIBLE
            adapter.collection = imageList
            adapter.notifyDataSetChanged()
        } else {
            imageListView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker.handleActivityResult(
            requestCode,
            resultCode,
            data,
            activity!!,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    imageFiles.forEach { pickedImage ->
                        val pickedFile = pickedImage.file
                        val imageFile = ImageFile(
                            UUID.randomUUID().toString(),
                            pickedFile.lastModified(),
                            pickedFile.absolutePath,
                            null
                        )
                        mainViewModel.saveImageFile(imageFile)
                    }
                }

                override fun onImagePickerError(@NonNull error: Throwable, @NonNull source: MediaSource) {
                    notify(error.message!!)
                }

                override fun onCanceled(@NonNull source: MediaSource) {

                }
            })
    }
}