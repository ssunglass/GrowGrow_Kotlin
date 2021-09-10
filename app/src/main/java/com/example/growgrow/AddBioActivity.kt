package com.example.growgrow

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import com.example.growgrow.Fragments.ProfileFragment
import com.example.growgrow.databinding.ActivityAddBioBinding
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class AddBioActivity : AppCompatActivity() {

    private var _binding: ActivityAddBioBinding? = null
    private val binding get() = _binding!!
    private lateinit var date: String
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId: String
    private lateinit var description: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =  ActivityAddBioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid.toString()




        binding.yearText.text = Calendar.getInstance().get(Calendar.YEAR).toString()


        binding.yearPickerBtn.setOnClickListener {

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.bio_dialog, null)
                val yearDate = dialogView.findViewById<NumberPicker>(R.id.year_picker)

                yearDate.wrapSelectorWheel = false

                yearDate.minValue = 2000
                yearDate.maxValue = year

                builder.setView(dialogView)
                        .setPositiveButton("등록", DialogInterface.OnClickListener { dialog, id ->

                            binding.yearText.text = (yearDate.value).toString()

                        }
                        )

                        .setNegativeButton("취소", null)

                        .create()

                builder.setCancelable(false)



                builder.show()


        }

        binding.addBioBtn.setOnClickListener {

            description = binding.bioText.text.toString()
            date = binding.yearText.text.toString()

            val bio = HashMap<String, Any>()
            bio["date"] = date
            bio["description"] = description

            db.collection("Users").document(userId)
                .collection("Bios").document(date).set(bio)

             val intent = Intent(this@AddBioActivity, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()





        }
    }
}