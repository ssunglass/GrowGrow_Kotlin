package com.example.growgrow.Model

import android.icu.text.StringPrepParseException
import java.util.*

class User {

    private var username:String = ""
    private var fullname:String = ""
    private var summary:String = ""
    private var uid:String = ""
    private var depart:String = ""
    private var major:String = ""
    private var region:String = ""
    private var date: String = ""
    private var description: String = ""
    private var keywords: List<String> = arrayListOf()
    private var referTo: List<String> = arrayListOf()
    private var referredBy: String = ""




    constructor()

    constructor(username: String,
                fullname: String,
                summary: String,
                uid: String,
                depart: String,
                region: String,
                major: String,
                date: String,
                description: String,
                keywords: List<String>,
                referTo: List<String>,
                referredBy: String

               )
    {
        this.username = username
        this.fullname = fullname
         this.summary = summary
        this.uid = uid
        this.region = region
        this.major = major
        this.depart = depart
        this.keywords = keywords
        this.date = date
        this.description = description
        this.referTo = referTo
        this.referredBy = referredBy


    }

    fun getUsername(): String {

        return username
    }

    fun getFullname(): String {

        return fullname
    }

    fun getUid(): String {

        return uid
    }

    fun getSummary(): String {
        return summary
    }

    fun getRegion(): String {
        return region
    }

    fun getDepart(): String {
        return depart
    }

    fun getMajor(): String {
        return major
    }

    fun getReferredBy(): String {
        return referredBy
    }
    fun getKeywords(): List<String> {
        return keywords
    }

    fun getReferTo(): List<String> {
        return referTo
    }

   /* fun getDate():String{
        return date


    }

    fun getDescription(): String{
        return description
    }

    */







}

 class Bio {

    private var date: String = ""
    private var description: String = ""

    constructor()

    constructor(
            date: String,
            description: String,
    ){

        this.date = date
        this.description = description
    }

    fun getDate():String{
        return date


    }

    fun getDescription(): String{
        return description
    }




}






