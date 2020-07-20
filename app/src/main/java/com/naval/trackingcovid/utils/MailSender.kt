package com.naval.trackingcovid.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.naval.trackingcovid.model.User
import com.naval.trackingcovid.ui.TakeReadingActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailSender {

    companion object{

        val sMail: String = "<Enter your email>"
        val passWord : String = "<Enter your password>"
        val recipientMail = "<Enter recipient email>"
        val openingText = "Following person is Suspicious :"
        val subjectOfMail = "URGENT!!!Suspicious Reading Recorded"
        var bodyOfMail = ""

        @RequiresApi(Build.VERSION_CODES.O)
        fun sendEmailToAuthorities(takeReadingActivity: TakeReadingActivity, tempReading: Int, oxygenReading: Int, user: User?, dateTimeOfReading: LocalDateTime) {

            bodyOfMail = openingText + "\n ${user?.fullName}" +
                    "\n His Mobile Number is : ${user?.mobileNo}" +
                    "\n His Temperature Reading is : ${tempReading}" +
                    "\n His Oxygen Reading is : ${oxygenReading}" +
                    "\n Time at which reading was taken is : " +
                    "${dateTimeOfReading.format(
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))}"

            val properties = Properties()
            properties.put("mail.smtp.auth","true")
            properties.put("mail.smtp.starttls.enable","true")
            properties.put("mail.smtp.host","smtp.gmail.com")
            properties.put("mail.smtp.port","587")


            val session = Session.getInstance(properties, object: Authenticator(){

                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(sMail,passWord)
                }
            })

            val message = MimeMessage(session)
            message.setFrom(InternetAddress(sMail))
            message.setRecipients(Message.RecipientType.TO, recipientMail)
            message.setSubject(subjectOfMail)
            message.setText(bodyOfMail)

            SendMail(takeReadingActivity).execute(message)

        }

    }

}
