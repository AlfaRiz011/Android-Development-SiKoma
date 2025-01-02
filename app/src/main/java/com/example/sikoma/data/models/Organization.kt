package com.example.sikoma.data.models

import com.example.sikoma.R

data class Organization(
    var organizationName: String? = null,
    var organizationBio:  String? = null,
    var organizationPic: Int? = null
)

object OrganizationProvider{
    fun createDummy(): List<Organization>{
        val organizations = mutableListOf<Organization>()

        val names = listOf("Multimedia", "Android", "Cyber Security", "Robotics")
        val pic = listOf(
            R.drawable.profile_pic_1,
            R.drawable.profile_pic_2,
            R.drawable.profile_pic_3,
            R.drawable.profile_pic_4)

        for (i in 0 until names.count()){
            organizations.add(
                Organization(
                    organizationName = names[i],
                    organizationBio = R.string.lorem_ipsum_2.toString(),
                    organizationPic = pic[i]
                )
            )
        }
        return organizations
    }
}