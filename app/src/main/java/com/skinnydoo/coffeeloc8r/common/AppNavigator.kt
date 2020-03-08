package com.skinnydoo.coffeeloc8r.common

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.material.button.MaterialButton
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.utils.extensions.onClick
import javax.inject.Inject

class AppNavigator @Inject constructor(private val activity: AppCompatActivity) {

    fun showErrorDialog(
        titleRes: Int? = null,
        titleText: String? = null,
        @StringRes msgRes: Int? = null,
        msgText: String? = null,
        onConfirm: (() -> Unit)? = null
    ) {
        val titleResourceId = titleRes ?: 0
        val msgResourceId = msgRes ?: 0

        val title =
            titleText ?: if (titleResourceId == 0) activity.getString(R.string.dialog_error_title)
            else activity.getString(titleResourceId)

        val msg = msgText ?: if (msgResourceId == 0) "" else activity.getString(msgResourceId)
        showCustomModalDialog(title, msg, onConfirm)
    }

    fun showToastDialog(
        titleRes: Int? = null,
        titleText: String? = null,
        @StringRes msgRes: Int? = null,
        msgText: String? = null,
        onConfirm: (() -> Unit)? = null
    ) {
        val titleResourceId = titleRes ?: 0
        val msgResourceId = msgRes ?: 0

        val title =
            titleText ?: if (titleResourceId == 0) "" else activity.getString(titleResourceId)
        val msg = msgText ?: if (msgResourceId == 0) "" else activity.getString(msgResourceId)
        showCustomModalDialog(title, msg, onConfirm)
    }

    private fun showCustomModalDialog(
        titleText: String,
        msgText: String,
        onConfirm: (() -> Unit)? = null
    ) {
        val dialog = MaterialDialog(activity).show {
            lifecycleOwner(activity)
            customView(R.layout.custom_dialog_view, noVerticalPadding = true)
            cancelable(false)
            cancelOnTouchOutside(false)
        }

        val dialogView = dialog.getCustomView()
        dialogView.findViewById<TextView>(R.id.dialog_title).apply {
            this.text = titleText
        }

        dialogView.findViewById<TextView>(R.id.dialog_msg).apply {
            this.text = msgText
        }

        val button = dialogView.findViewById<MaterialButton>(R.id.ok_button)
        button.onClick {
            onConfirm?.invoke()
            dialog.dismiss()
        }
    }
}
