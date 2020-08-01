package com.example.runtimepermissiontest

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeCall.setOnClickListener{
            // ContextCompat.checkSelfPermission 判断是否授权
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                // requestPermissions 3个参数
                //Activity实例  String数组：权限名  请求码
                ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE), 1)
            }else{
                call()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //授权结果 会封装在 grantResults中
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                }else{
                    Toast.makeText(this, "You denied the permission",
                    Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //打电话的逻辑
    private fun call(){
        try {
            //隐式intent  ACTION_CALL 系统内置的打电话动作
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }catch (e: SecurityException){
            e.printStackTrace()
        }
    }
}