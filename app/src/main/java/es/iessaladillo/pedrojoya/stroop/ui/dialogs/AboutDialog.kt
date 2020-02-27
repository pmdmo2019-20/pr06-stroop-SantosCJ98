import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AboutDialog(val title: String, val message: String) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        isCancelable = false

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =

        AlertDialog.Builder(requireContext())

            .setTitle(title)

            .setMessage(message)

            .setNeutralButton("OK") { _, _ ->

            }

                    .create()





}