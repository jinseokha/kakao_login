package com.seokdev.kakaologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.seokdev.kakaologin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 해시 값 확인
        /*val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)*/

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                toast(getString(R.string.token_info_error))
            } else if (tokenInfo != null) {
                toast(getString(R.string.token_info_success))
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() ->
                        toast(getString(R.string.access_denied))

                    error.toString() == AuthErrorCause.InvalidClient.toString() ->
                        toast(getString(R.string.invalid_client))

                    error.toString() == AuthErrorCause.InvalidGrant.toString() ->
                        toast(getString(R.string.invalid_grant))

                    error.toString() == AuthErrorCause.InvalidRequest.toString() ->
                        toast(getString(R.string.invalid_request))

                    error.toString() == AuthErrorCause.InvalidScope.toString() ->
                        toast(getString(R.string.invalid_scope))

                    error.toString() == AuthErrorCause.Misconfigured.toString() ->
                        toast(getString(R.string.mis_configured))

                    error.toString() == AuthErrorCause.ServerError.toString() ->
                        toast(getString(R.string.server_error))

                    error.toString() == AuthErrorCause.Unauthorized.toString() ->
                        toast(getString(R.string.unauthorized))

                    else ->
                        toast(getString(R.string.etc_error))
                }
            } else if (token != null) {
                toast(getString(R.string.success_login))
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        binding.kakaoLoginButton.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

    }

    private fun toast(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}