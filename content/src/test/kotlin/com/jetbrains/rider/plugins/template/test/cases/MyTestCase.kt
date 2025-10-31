package com.jetbrains.rider.plugins.plugintemplate.test.cases

import com.intellij.openapi.components.service
import com.jetbrains.rd.platform.diagnostics.RdLogTraceScenarios
import com.jetbrains.rider.plugins.plugintemplate.MyIcons
import com.jetbrains.rider.plugins.plugintemplate.ProtocolCaller
import com.jetbrains.rider.protocol.protocol
import com.jetbrains.rider.test.OpenSolutionParams
import com.jetbrains.rider.test.annotations.Solution
import com.jetbrains.rider.test.annotations.TestSettings
import com.jetbrains.rider.test.asserts.shouldBe
import com.jetbrains.rider.test.base.PerClassSolutionTestBase
import com.jetbrains.rider.test.enums.BuildTool
import com.jetbrains.rider.test.enums.sdk.SdkVersion
import com.jetbrains.rider.test.facades.solution.RiderSolutionApiFacade
import com.jetbrains.rider.test.facades.solution.SolutionApiFacade
import com.jetbrains.rider.test.scriptingApi.runBlockingWithProtocolPumping
import org.testng.annotations.Test
import java.awt.image.BufferedImage

@TestSettings(sdkVersion = SdkVersion.AUTODETECT, buildTool = BuildTool.AUTODETECT)
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

    @Test
    fun iconCallTest() {
        runBlockingWithProtocolPumping(project.protocol, "iconCallTest") {
            val myService = project.service<ProtocolCaller>()

            val iconFromBackend = myService.doIconCall()
            val iconFromFrontend = MyIcons.RiderIcon

            val imageFrontend = BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)
            val graphicsFrontend = imageFrontend.createGraphics()
            iconFromFrontend.paintIcon(null, graphicsFrontend, 0, 0)

            val imageBackend = BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)
            val graphicsBackend = imageBackend.createGraphics()
            iconFromBackend.paintIcon(null, graphicsBackend, 0, 0)

            for (x in 0..<imageFrontend.width) {
                for (y in 0..<imageFrontend.height) {
                    val frontendPixel = imageFrontend.getRGB(x, y)
                    val backendPixel = imageBackend.getRGB(x, y)
                    backendPixel.shouldBe(frontendPixel)
                }
            }
        }
    }

}
