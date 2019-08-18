package com.simmorsal.smartnode.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simmorsal.smartnode.R
import kotlinx.android.synthetic.main.activity_socket.*
import org.jetbrains.anko.doAsync
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

class ActivitySocket : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)


        connect()
    }

    @SuppressLint("SetTextI18n")
    private fun connect() {
        btnConnect.setOnClickListener {

            doAsync {

                val socket = Socket(
                    ip.text.toString(),
                    port.text.toString().toInt()
                )
                val outputStream = DataOutputStream(socket.getOutputStream())
                val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))

                while (true) {
                    txtLog.text = txtLog.text.toString() + "/n" + inputStream.readLine()
                }
//                uiThread {
//
//                }
            }

        }
    }
}
