package com.example.growgrow

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.growgrow.databinding.ActivityInviteBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.iosParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import java.util.jar.Manifest

class InviteActivity : AppCompatActivity() {

    private var _binding: ActivityInviteBinding? = null
    private val binding get() = _binding!!
    private lateinit var displayName: String
    private val permissionRequest = 101
    private lateinit var linkText: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityInviteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.hasExtra("displayName")) {
            this.displayName = intent.getStringExtra("displayName").toString()

        }

        binding.createDynamiclinkBtn.setOnClickListener {



            val user = Firebase.auth.currentUser!!
            val uid = user.uid
            val invitationLink = "https://growgrow.com/?invitedby=$uid"
            Firebase.dynamicLinks.shortLinkAsync {
                link = Uri.parse(invitationLink)
                domainUriPrefix = "https://growgrow.page.link"
                androidParameters("com.example.android") {
                    minimumVersion = 125
                }
              /*  iosParameters("com.example.ios") {
                    appStoreId = "123456789"
                    minimumVersion = "1.0.1"
                } */
            }.addOnSuccessListener { shortDynamicLink ->
                val mInvitationUrl = shortDynamicLink.shortLink
                val invitationLink = mInvitationUrl.toString()
                linkText = invitationLink

                sendMessage(linkText)






            }
        }


        }


    fun sendMessage(linkText: String) {
        val permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myMessage(linkText)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),
                permissionRequest)
        }
    }
    private fun myMessage(linkText: String) {
        val myNumber: String = binding.invitePhone.text.toString()
        if (myNumber == "") {
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(myNumber, null,"커커에 초대합니다: $linkText" , null, null)
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter the correct number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults:
    IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myMessage(linkText)
            } else {
                Toast.makeText(this, "You don't have required permission to send a message",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}





