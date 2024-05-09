using System.Threading;
using JetBrains.Application.BuildScript.Application.Zones;
using JetBrains.ReSharper.Feature.Services;
using JetBrains.ReSharper.Psi.CSharp;
using JetBrains.ReSharper.TestFramework;
using JetBrains.TestFramework;
using JetBrains.TestFramework.Application.Zones;
using NUnit.Framework;

[assembly: Apartment(ApartmentState.STA)]

namespace ReSharperPlugin.RiderPluginTemplate.Tests
{
    [ZoneDefinition]
    public class RiderPluginTemplateTestEnvironmentZone : ITestsEnvZone, IRequire<PsiFeatureTestZone>, IRequire<IRiderPluginTemplateZone> { }

    [ZoneMarker]
    public class ZoneMarker : IRequire<ICodeEditingZone>, IRequire<ILanguageCSharpZone>, IRequire<RiderPluginTemplateTestEnvironmentZone> { }

    [SetUpFixture]
    public class RiderPluginTemplateTestsAssembly : ExtensionTestEnvironmentAssembly<RiderPluginTemplateTestEnvironmentZone> { }
}
