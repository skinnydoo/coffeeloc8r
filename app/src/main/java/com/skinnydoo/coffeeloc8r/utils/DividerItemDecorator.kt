package com.skinnydoo.coffeeloc8r.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorator(
    private val divider: Drawable?,
    private val firstDividerPos: Int = -1,
    private val showFirstDivider: Boolean = false,
    private val showLastDivider: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (divider == null) {
            super.onDrawOver(c, parent, state)
            return
        }

        // Initialization needed to avoid compiler warning
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        val size: Int
        val orientation = getOrientation(parent)
        val childCount = parent.childCount

        if (orientation == LinearLayoutManager.VERTICAL) {
            size = divider.intrinsicHeight
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
        } else { //horizontal
            size = divider.intrinsicWidth
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
        }

        if (firstDividerPos <= -1) {
            for (i in (if (showFirstDivider) 0 else 1) until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                if (orientation == LinearLayoutManager.VERTICAL) {
                    top = child.top - params.topMargin
                    bottom = top + size
                } else { //horizontal
                    left = child.left - params.leftMargin
                    right = left + size
                }
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        } else {
            for (i in firstDividerPos until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                if (orientation == LinearLayoutManager.VERTICAL) {
                    top = child.top - params.topMargin
                    bottom = top + size
                } else { //horizontal
                    left = child.left - params.leftMargin
                    right = left + size
                }
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }

        // show last divider
        if (showLastDivider && childCount > 0) {
            val child = parent.getChildAt(childCount - 1)
            val params = child.layoutParams as RecyclerView.LayoutParams
            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.bottom + params.bottomMargin
                bottom = top + size
            } else { // horizontal
                left = child.right + params.rightMargin
                right = left + size
            }
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    private fun getOrientation(parent: RecyclerView): Int {
        if (parent.layoutManager is LinearLayoutManager) {
            val layoutManager = parent.layoutManager as LinearLayoutManager?
            return layoutManager!!.orientation
        } else {
            throw IllegalStateException(
                "DividerItemDecoration can only be used with a LinearLayoutManager."
            )
        }
    }
}
