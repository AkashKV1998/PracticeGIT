// Generated by GraphWalker (http://www.graphwalker.org)
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;
import org.graphwalker.java.annotation.Edge;

@Model(file = "ViewAssessmentsSidebar.graphml")
public interface ViewAssessmentsSidebar {

    @Vertex()
    void v_Dashboard();

    @Edge()
    void e_ClickNavbar();

    @Edge()
    void e_ViewAssessments();

    @Edge()
    void e_StartBrowser();

    @Vertex()
    void v_VerifyViewAssessmentsSidebar();

    @Edge()
    void e_EnterBaseURL();

    @Vertex()
    void v_BaseURL();

    @Vertex()
    void v_Navbar();

    @Edge()
    void e_DirectLogin();

    @Vertex()
    void v_BrowserStarted();

    @Vertex()
    void v_ViewAssessments();
}
