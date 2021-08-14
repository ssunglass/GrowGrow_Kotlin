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
                keywords: List<String> )
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

    fun getKeywords(): List<String> {
        return keywords
    }

    fun getDate():String{
        return date


    }

    fun getDescription(): String{
        return description
    }





}

