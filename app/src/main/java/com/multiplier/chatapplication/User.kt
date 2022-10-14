package com.multiplier.chatapplication

import android.provider.ContactsContract

class User {
    var name: String? = null
    var email: String? = null
    var user_id: String? = null

    constructor(){}

    constructor(name: String?, email: String?, user_id: String?){
        this.name=name
        this.user_id=user_id
        this.email=email
    }
}