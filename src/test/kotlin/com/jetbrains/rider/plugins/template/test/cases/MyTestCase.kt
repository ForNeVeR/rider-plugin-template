package com.jetbrains.rider.plugins.plugintemplate.test.cases

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.jetbrains.rd.platform.diagnostics.RdLogTraceScenarios
import com.jetbrains.rider.plugins.plugintemplate.ProtocolCaller
import com.jetbrains.rider.protocol.protocol
import com.jetbrains.rider.test.OpenSolutionParams
import com.jetbrains.rider.test.annotations.TestEnvironment
import com.jetbrains.rider.test.asserts.shouldBe
import com.jetbrains.rider.test.base.BaseTestWithSolution
import com.jetbrains.rider.test.env.enums.BuildTool
import com.jetbrains.rider.test.env.enums.SdkVersion
import com.jetbrains.rider.test.scriptingApi.runBlockingWithProtocolPumping
import org.testng.annotations.Test
import java.io.File
import java.time.Duration

@TestEnvironment(sdkVersion = SdkVersion.AUTODETECT, buildTool = BuildTool.AUTODETECT)
class MyTestCase : BaseTestWithSolution() {
    override fun getSolutionDirectoryName() = "MyTestSolution"
    override val restoreNuGetPackages = true
    override val backendLoadedTimeout: Duration = Duration.ofMinutes(2L)
    override val traceScenarios = setOf(RdLogTraceScenarios.Commands)

    override fun openSolution(solutionFile: File, params: OpenSolutionParams): Project {
        // This may take a long time sometimes on CI agents.
        params.projectModelReadyTimeout = params.projectModelReadyTimeout.multipliedBy(10L)

        return super.openSolution(solutionFile, params)
    }

    @Test
    fun protocolCallTest() {
        runBlockingWithProtocolPumping(project.protocol, "protocolCallTest") {
            val myService = project.service<ProtocolCaller>()
            val result = myService.doCall("test-string")
            result.shouldBe(11)
        }
    }
}
