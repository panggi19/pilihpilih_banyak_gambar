package com.panggi.pilihbanyakgambar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var gambar: ArrayList<Uri>? = null
    private val KODE_AMBIL_GAMBAR = 123
    private var posisi = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gambar = ArrayList()
        Is_pengalihGambar.setFactory { ImageView(applicationContext)  }


        Btn_ambilGambar.setOnClickListener(){
            ambilgambarTujuan()
        }

        Btn_selanjutnya.setOnClickListener(){
            if (posisi < gambar!!.size - 1){
                posisi++
                Is_pengalihGambar.setImageURI(gambar!![posisi])
            } else {
                Toast.makeText(
                    this,
                    "Tidak ada gambar lagi..",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        Btn_sebelumnya.setOnClickListener(){
            if (posisi > 0){
                posisi--
                Is_pengalihGambar.setImageURI(gambar!![posisi])
            }else {
                Toast.makeText(
                    this,
                    "Tidak ada gambar lagi..",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private  fun ambilgambarTujuan(){
        val tujuan = Intent()
        tujuan.type = "image/*"
        tujuan.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        tujuan.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(tujuan, "pilih gambar"),
            KODE_AMBIL_GAMBAR
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KODE_AMBIL_GAMBAR) {
            if (resultCode == Activity.RESULT_OK){
                if (data!!.clipData != null){
                    val jumlahGambar = data.clipData!!.itemCount
                    for (i in 0 until jumlahGambar){
                        val gambarUri = data.clipData!!.getItemAt(i).uri
                        gambar!!.add(gambarUri)
                    }
                    Is_pengalihGambar.setImageURI(gambar!![0])
                    posisi = 0
                } else {
                    val  gambarUri = data.data
                    Is_pengalihGambar.setImageURI(gambarUri)
                    posisi = 0
                }
            }
        }
    }
}