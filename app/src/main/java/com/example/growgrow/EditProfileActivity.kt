package com.example.growgrow

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.growgrow.Model.User
import com.example.growgrow.databinding.ActivityEditProfileBinding
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
    private lateinit var region: String
    private lateinit var major: String
    private lateinit var apiUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        major = ""

        _binding =  ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val urlHum = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100391"
        val urlSoc = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100392"
        val urlEdu = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100393"
        val urlEng = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100394"
        val urlNat = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100395"
        val urlMed = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100396"
        val urlArt = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100397"


         */
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        setupSpinnerRegion()
        setupSpinnerDepart()
        setupSpinnerHandler()

        userInfo()






            getJson()








        binding.saveEditBtn.setOnClickListener{
            updateUserInfo()

        }

        binding.closeEditBtn.setOnClickListener {
            onBackPressed()
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
                        binding.majorProfile.setText(user!!.getMajor())

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
        user["depart"] = binding.selectedDepart.text.toString()
        user["major"] = binding.majorProfile.text.toString()

        db.collection("Users").document(firebaseUser.uid).update(user)

        val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
        startActivity(intent)
        finish()



    }

    private fun setupSpinnerRegion(){
        val region = resources.getStringArray(R.array.region_array)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, region)

        binding.regionSpinner.adapter = adapter



    }

    private fun setupSpinnerDepart(){
        val depart = resources.getStringArray(R.array.depart_array)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, depart)

        binding.departSpinner.adapter = adapter



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

        binding.departSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                val currentItem = binding.departSpinner.getItemAtPosition(position)


                if( !currentItem.equals("계열")){
                    binding.selectedDepart.text = currentItem.toString()
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

            var stream = URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8fa1b6fffaf969b85712d6ea45a921fd&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=univ&subject=100392").openStream()
            val read = BufferedReader(InputStreamReader(stream, "UTF-8"))
            major  = read.readLine()

            runOnUiThread{

            val jsonObject = JSONObject(major)
            val json2Object = jsonObject.getJSONObject("dataSearch")
            val jsonArray = json2Object.getJSONArray("content")

           // val jsonTest = jsonArray.getJSONObject(0).getString("mClass")



                for(i in 0 until jsonArray.length()) {

                    val json3Object = jsonArray.getJSONObject(i)



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










