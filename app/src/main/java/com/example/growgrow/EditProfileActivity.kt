package com.example.growgrow

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.User
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.recyclerview.MajorAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.concurrent.thread


class EditProfileActivity : AppCompatActivity() {


    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!



    private lateinit var firebaseUser: FirebaseUser
  //  private lateinit var region: String
    private lateinit var major: String
    private lateinit var apiUrl: String
    private lateinit var majorArray: ArrayList<String>
    private lateinit var recyclerView : RecyclerView
    private lateinit var majorAdapter: MajorAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        major = ""
        apiUrl = ""
        majorArray = arrayListOf()


        _binding =  ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        setupSpinnerRegion()
        setupSpinnerHandler()

        userInfo()


        binding.saveEditBtn.setOnClickListener{
            updateUserInfo()

        }

        binding.closeEditBtn.setOnClickListener {
            onBackPressed()
        }



        binding.selectDepart.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.depart_dialog, null)
            val chipGroup = dialogView.findViewById<ChipGroup>(R.id.select_depart_chipgroup)



            builder.setView(dialogView)
                .setPositiveButton("선택", DialogInterface.OnClickListener{ dialog, id ->


                    val chip = dialogView.findViewById<Chip>(chipGroup.checkedChipId)

                    if(chip != null) {

                        majorArray.clear()

                        binding.selectDepart.text = chip.text.toString()


                        val urlHum = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100391"
                        val urlSoc = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100392"
                        val urlEdu = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100393"
                        val urlEng = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100394"
                        val urlNat = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100395"
                        val urlMed = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100396"
                        val urlArt = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100397"



                        when (binding.selectDepart.text) {
                            "인문" -> apiUrl = urlHum
                            "사회" -> apiUrl = urlSoc
                            "공학" -> apiUrl = urlEdu
                            "자연" -> apiUrl = urlEng
                            "교육" -> apiUrl = urlNat
                            "의약" -> apiUrl = urlMed
                            "예체능" -> apiUrl = urlArt
                        }

                        getJson()
                    }


                })
                .setNegativeButton("취소", null)
                .create()



            builder.show()

        }


        binding.selectMajor.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val dialogView: View = layoutInflater.inflate(R.layout.major_search_dialog, null)
            val majorSearchBar =  dialogView.findViewById<EditText>(R.id.major_search_bar)


            recyclerView = dialogView.findViewById<RecyclerView>(R.id.recycler_view_major)
            recyclerView.layoutManager = LinearLayoutManager(this)

            majorAdapter = MajorAdapter(majorArray)
            recyclerView.adapter = majorAdapter

            majorSearchBar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {

                    majorAdapter.filter.filter(s.toString())

                }


            })
            builder.setView(dialogView)
                .setPositiveButton("선택", DialogInterface.OnClickListener{ dialog, id ->


                    binding.selectMajor.text = majorAdapter.getSelectedMajor().toString()




                })
                .setNegativeButton("취소", null)
                .create()



            builder.show()


        }

        binding.logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()



        }



    }

    private fun userInfo(){

        FirebaseFirestore.getInstance()
                .collection("Users").document(firebaseUser.uid).get().addOnSuccessListener{ document->

                    if(document != null) {

                        val user = document.toObject<User>(User::class.java)

                        binding.fullnameProfile.setText(user!!.getFullname())
                        binding.usernameProfile.setText(user!!.getUsername())
                        binding.summaryProfile.setText(user!!.getSummary())
                        binding.selectDepart.text = user!!.getDepart()
                        binding.selectMajor.text = user!!.getMajor()
                        binding.selectedRegion.text = user!!.getRegion()

                        if(user!!.getDepart() != "계열"){

                            val urlHum = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100391"
                            val urlSoc = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100392"
                            val urlEdu = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100393"
                            val urlEng = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100394"
                            val urlNat = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100395"
                            val urlMed = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100396"
                            val urlArt = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100397"


                            when (binding.selectDepart.text) {
                                "인문" -> apiUrl = urlHum
                                "사회" -> apiUrl = urlSoc
                                "공학" -> apiUrl = urlEdu
                                "자연" -> apiUrl = urlEng
                                "교육" -> apiUrl = urlNat
                                "의약" -> apiUrl = urlMed
                                "예체능" -> apiUrl = urlArt
                            }
                            getJson()

                        }



                    }

                }





    }

    private fun updateUserInfo(){

        val db = FirebaseFirestore.getInstance()

        val user = HashMap<String, Any>()

        user["fullname"] = binding.fullnameProfile.text.toString()
        user["username"] = binding.usernameProfile.text.toString()
        user["summary"] = binding.summaryProfile.text.toString()
        user["region"] = binding.selectedRegion.text.toString()
        user["depart"] = binding.selectDepart.text.toString()
        user["major"] = binding.selectMajor.text.toString()

        db.collection("Users").document(firebaseUser.uid).update(user)

        val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()



    }

    private fun setupSpinnerRegion(){
        val region = resources.getStringArray(R.array.region_array)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, region)

        binding.regionSpinner.adapter = adapter



    }
    private fun setupSpinnerHandler() {
        binding.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                val currentItem = binding.regionSpinner.getItemAtPosition(position)
                if(!currentItem.equals("지역")){
                    binding.selectedRegion.text = currentItem.toString()
                }



            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun getJson(){

        thread(start = true){

            val stream = URL(apiUrl).openStream()
            val read = BufferedReader(InputStreamReader(stream, "UTF-8"))
            major  = read.readLine()

            runOnUiThread{

            val jsonObject = JSONObject(major)
            val jsonSecObject = jsonObject.getJSONObject("dataSearch")
            val jsonArray = jsonSecObject.getJSONArray("content")


                for(i in 0 until jsonArray.length()) {

                    val jsonMajorObject = jsonArray.getJSONObject(i)

                    val majorName = jsonMajorObject.getString("mClass")

                    majorArray.add(majorName)

                }








        }






        }

    }




    /*
    private fun getDangerGrade(startLat: String, startLng: String, endLat: String, endLng: String) {
        lateinit var temp : String
        class getDangerGrade : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                val stream = URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list").openStream()
                val read = BufferedReader(InputStreamReader(stream, "UTF-8"))
                temp  = read.readLine()

                return null
            }

            override fun onPostExecute(result: Void?) {
                val grade : String
                super.onPostExecute(result)
                val json = JSONObject(temp)
                if(json.get("resultCode") != "10") {
                    val chiefObject = (json["items"] as JSONObject)
                    val upperArray : JSONArray = chiefObject.getJSONArray("item")
                    val upperObject = upperArray.getJSONObject(0)
                    grade = upperObject.getString("anals_grd")

                }
                else
                    grade = "none"
            }




        }
        getDangerGrade().execute()
    }


     */



}










