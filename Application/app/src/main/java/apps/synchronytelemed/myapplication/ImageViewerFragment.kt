package apps.synchronytelemed.myapplication

import android.os.Bundle
import android.view.View
import apps.synchronytelemed.myapplication.base.BaseFragment
import apps.synchronytelemed.myapplication.extensions.loadFromUrl
import apps.synchronytelemed.myapplication.repository.data.ImageFile
import kotlinx.android.synthetic.main.fragment_image_viewer.*

/**
 * Created by msaycon on 26,Jan,2020
 */
class ImageViewerFragment : BaseFragment() {

    lateinit var imageFile: ImageFile

    override fun layoutId() = R.layout.fragment_image_viewer

    override fun initializeViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageFile = arguments?.getParcelable("imageFile")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        image_holder.loadFromUrl(imageFile.filePath)
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}