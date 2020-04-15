package apps.synchronytelemed.myapplication.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.synchronytelemed.myapplication.R
import apps.synchronytelemed.myapplication.extensions.inflate
import apps.synchronytelemed.myapplication.extensions.loadFromUrl
import apps.synchronytelemed.myapplication.helpers.standardFormatFull
import apps.synchronytelemed.myapplication.helpers.toDayOfWeek
import apps.synchronytelemed.myapplication.repository.data.ImageFile
import kotlinx.android.synthetic.main.item_image.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by msaycon on 15,Apr,2020
 */
class ImageListAdapter
@Inject constructor() : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    internal var collection = ArrayList<ImageFile>()

    internal var clickListener: (ImageFile, View) -> Unit = { _: ImageFile, _: View -> }

    internal var onLongClickListener: (ImageFile, Int) -> Boolean = { _: ImageFile, _: Int -> true }

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(
            collection[position],
            clickListener,
            onLongClickListener,
            showHeader(position),
            position
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            parent.inflate(R.layout.item_image)
        )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            imageFile: ImageFile,
            clickListener: (ImageFile, View) -> Unit,
            onLongClickListener: (ImageFile, Int) -> Boolean,
            showHeader: Boolean,
            position: Int
        ) {
            if (showHeader) {
                itemView.header.visibility = View.VISIBLE
                itemView.header.text = imageFile.time.toDayOfWeek
            } else {
                itemView.header.visibility = View.GONE
                itemView.header.text = null
            }

            itemView.imageThumb.loadFromUrl(imageFile.filePath)
            itemView.caption.text = if (imageFile.caption.isNullOrEmpty()) {
                itemView.context.getString(R.string.long_tap)
            } else {
                imageFile.caption
            }
            itemView.time.text = imageFile.time.standardFormatFull

            itemView.imageThumb.setOnClickListener { clickListener(imageFile, itemView.imageThumb) }
            itemView.setOnLongClickListener { onLongClickListener(imageFile, position) }
        }
    }

    private fun showHeader(position: Int): Boolean {
        when (position) {
            RecyclerView.NO_POSITION -> {
                return false
            }
            0 -> {
                return true
            }
            else -> {
                val topImage = collection[position]
                val bottomImage = collection[position - 1]

                val topImageTime = Calendar.getInstance()
                topImageTime.timeInMillis = topImage.time

                val bottomImageTime = Calendar.getInstance()
                bottomImageTime.timeInMillis = bottomImage.time

                return bottomImageTime.get(Calendar.DATE) != topImageTime.get(Calendar.DATE)
            }
        }
    }
}