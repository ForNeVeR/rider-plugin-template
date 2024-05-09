using JetBrains.Application.BuildScript.Application.Zones;
using JetBrains.ReSharper.Feature.Services.Daemon;
using JetBrains.ReSharper.Psi;
using JetBrains.ReSharper.Psi.CSharp;

namespace Rider.Plugins.Template
{
    [ZoneDefinition]
    // [ZoneDefinitionConfigurableFeature("Title", "Description", IsInProductSection: false)]
    public interface IRiderPluginsTemplateZone : IZone
    {
    }
}
