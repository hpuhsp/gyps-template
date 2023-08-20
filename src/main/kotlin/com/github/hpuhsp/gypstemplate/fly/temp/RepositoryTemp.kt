package com.github.hpuhsp.gypstemplate.fly.temp

import com.github.hpuhsp.gypstemplate.fly.GypsPluginGeneratorProvider
import com.github.hpuhsp.gypstemplate.fly.config.commonAnnotation

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 18:18
 * @UpdateRemark:   更新说明：
 */
fun gypsRepository(
    provider: GypsPluginGeneratorProvider,
    pageName: String
) = """
package ${provider.targetPackageName.value}.repository

import com.swallow.fly.base.repository.BaseRepository
import javax.inject.Inject

${commonAnnotation(provider)}

class ${pageName}Repository @Inject constructor() : BaseRepository() {

}
    
""".trimIndent()
