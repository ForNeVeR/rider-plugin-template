using System.Threading.Tasks;
using JetBrains.Lifetimes;
using JetBrains.ProjectModel;
using JetBrains.Rd.Tasks;
using JetBrains.ReSharper.Feature.Services.Protocol;
using Rider.Plugins.Template.Model;

namespace Rider.Plugins.Template;

[SolutionComponent]
public class MyRdHost
{
    public MyRdHost(ISolution solution)
    {
        var model = solution.GetProtocolSolution().GetTemplatePluginModel();
        model.MyCall.SetAsync(HandleCall);
    }

    private async Task<RdCallResponse> HandleCall(Lifetime lt, RdCallRequest request)
    {
        await Task.Delay(1000, lt);
        return lt.Execute(() => new RdCallResponse(request.MyField.Length));
    }
}
