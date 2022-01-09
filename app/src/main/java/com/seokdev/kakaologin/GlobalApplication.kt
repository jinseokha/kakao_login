package com.seokdev.kakaologin

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

/**
 * @author Ha Jin Seok
 * @email seok270@gmail.com
 * @created 2022-01-09
 * @desc
 */

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "a44dbbde821cc4ee30bbd9bb029f6c6f")
    }
}