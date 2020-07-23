package com.finderbar.innox.model

import android.os.Parcel
import android.os.Parcelable
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem

class ImageItem(
    var urls: String,
    var _columnSpan: Int = 1,
    var _rowSpan: Int = 1,
    var _position: Int = 0
) : AsymmetricItem {

    override fun getColumnSpan(): Int = _columnSpan
    override fun getRowSpan(): Int = _rowSpan

    private constructor(parcel: Parcel) : this(
        urls = parcel.readString()!!,
        _columnSpan = parcel.readInt(),
        _rowSpan = parcel.readInt(),
        _position = parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(urls)
        dest.writeInt(_columnSpan)
        dest.writeInt(_rowSpan)
        dest.writeInt(_position)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<ImageItem> = object : Parcelable.Creator<ImageItem> {
            override fun createFromParcel(parcel: Parcel): ImageItem {
                return ImageItem(parcel)
            }

            override fun newArray(size: Int): Array<ImageItem> {
                return newArray(size)
            }
        }
    }
}