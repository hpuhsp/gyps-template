package com.github.hpuhsp.gypstemplate.fly.config

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 18:38
 * @UpdateRemark:   更新说明：
 */
import com.github.hpuhsp.gypstemplate.fly.GypsPluginGeneratorProvider
import java.text.SimpleDateFormat
import java.util.*

fun commonAnnotation(provider: GypsPluginGeneratorProvider) = """
/**
 * Created on ${SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(System.currentTimeMillis()))}
 * @Author
 * ${if (provider.needActivity.value) "${provider.activityName}Activity" else "${provider.fragmentName}Fragment"}
 */
""".trimIndent()