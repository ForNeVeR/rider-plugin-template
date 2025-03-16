package com.jetbrains.rider.plugins.plugintemplate.test.cases

import com.intellij.openapi.components.service
import com.jetbrains.rd.platform.diagnostics.RdLogTraceScenarios
import com.jetbrains.rider.plugins.plugintemplate.ProtocolCaller
import com.jetbrains.rider.protocol.protocol
import com.jetbrains.rider.test.OpenSolutionParams
import com.jetbrains.rider.test.annotations.Solution
import com.jetbrains.rider.test.annotations.TestEnvironment
import com.jetbrains.rider.test.asserts.shouldBe
import com.jetbrains.rider.test.base.PerClassSolutionTestBase
import com.jetbrains.rider.test.env.enums.BuildTool
import com.jetbrains.rider.test.env.enums.SdkVersion
import com.jetbrains.rider.test.facades.solution.RiderSolutionApiFacade
import com.jetbrains.rider.test.facades.solution.SolutionApiFacade
import com.jetbrains.rider.test.scriptingApi.runBlockingWithProtocolPumping
import org.testng.annotations.Test

@TestEnvironment(sdkVersion = SdkVersion.AUTODETECT, buildTool = BuildTool.AUTODETECT)
@Solution("MyTestSolution")
class MyTestCase : PerClassSolutionTestBase() {
    override val traceScenarios = setOf(RdLogTraceScenarios.Commands)

    override val solutionApiFacade: SolutionApiFacade = object : RiderSolutionApiFacade() {
        override fun waitForSolution(params: OpenSolutionParams) {
            // This may sometimes take a long time on CI agents.
            params.projectModelReadyTimeout = params.projectModelReadyTimeout.multipliedBy(10L)

            return super.waitForSolution(params)
        }
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
