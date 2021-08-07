package com.example.growgrow.Model

class User {

    private var username:String = ""
    private var fullname:String = ""
    private var uid:String = ""



    constructor()

    constructor(username: String, fullname: String, uid: String)
    {
        this.username = username
        this.fullname = fullname
        this.uid = uid
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


}