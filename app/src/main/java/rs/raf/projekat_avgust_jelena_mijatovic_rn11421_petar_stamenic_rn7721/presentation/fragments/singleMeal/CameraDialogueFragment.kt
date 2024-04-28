package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CameraDialogueFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Take image with camera?")
                .setPositiveButton("Yes"
                ) { _, _ ->
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    try {
                        activity?.startActivityForResult(takePictureIntent, 1)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            this.requireContext(),
                            "Could not access camera",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    dismiss()
                }
                .setNegativeButton("No"
                ) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}