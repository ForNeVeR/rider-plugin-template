package com.jetbrains.rider.plugins.plugintemplate

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.jetbrains.rider.plugins.plugintemplate.model.RdCallRequest
import com.jetbrains.rider.plugins.plugintemplate.model.rdPluginTemplateModel
import com.jetbrains.rider.projectView.solution

@Service(Service.Level.PROJECT)
class ProtocolCaller(private val project: Project) {

    suspend fun doCall(input: String): Int {
        val model = project.solution.rdPluginTemplateModel
        val request = RdCallRequest(input)
        val response = model.myCall.startSuspending(request)
        return response.myResult
    }
}
