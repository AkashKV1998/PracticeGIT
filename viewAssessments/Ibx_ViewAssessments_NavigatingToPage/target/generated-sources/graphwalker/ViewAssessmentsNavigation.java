// Generated by GraphWalker (http://www.graphwalker.org)
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;
import org.graphwalker.java.annotation.Edge;

@Model(file = "ViewAssessmentsNavigation.graphml")
public interface ViewAssessmentsNavigation {

    @Edge()
    void e_EnterCredentials();

    @Vertex()
    void v_ValidateViewAssessments();

    @Vertex()
    void v_Dashboard();

    @Vertex()
    void v_VerifyViewAssessments();

    @Edge()
    void e_StartBrowser();

    @Vertex()
    void v_ValidateAssessmentsDashboard();

    @Edge()
    void e_EnterBaseURL();

    @Vertex()
    void v_BaseURL();

    @Vertex()
    void v_BrowserStarted();

    @Edge()
    void e_ClickViewAssessments();

    @Edge()
    void e_ClickIlluminateLogo();

    @Edge()
    void e_ClickViewAssessmentsTile();
}
