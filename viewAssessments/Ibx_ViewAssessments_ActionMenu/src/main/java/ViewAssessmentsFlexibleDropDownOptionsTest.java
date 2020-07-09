import Controllers.ViewAssessmentsController;
import Helpers.BrowserInitHelper;
import Helpers.DriverHelper;
import Helpers.JavascriptHelper;
import Pom.*;
import Utils.ConsoleLogger;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@GraphWalker(value = "quick_random(edge_coverage(100))")
public class ViewAssessmentsFlexibleDropDownOptionsTest extends ExecutionContext implements ViewAssessmentsFlexibleDropDownOptions {
    Dashboard dashboardPOM = new Dashboard();
    BrowserInitHelper browserHelper = new BrowserInitHelper();
    ViewAssessmentsPage viewAssessmentsPage = new ViewAssessmentsPage();
    ViewAssessmentsController viewAssessmentsController = new ViewAssessmentsController();
    ViewAssessments_AddFilters viewAssessments_AddFilters = new ViewAssessments_AddFilters();
    OnlineTestingAssessmentAdministrations onlineTesting = new OnlineTestingAssessmentAdministrations();
    OnlineAssessmentPreviewWindow onlineTestingPreviewWindow = new OnlineAssessmentPreviewWindow();
    CameraGraderPage cameraGraderPage = new CameraGraderPage();
    ViewStudentResponsesPage viewStudentsResponsesPage = new ViewStudentResponsesPage();
    AssessmentSharePage assessmentSharePage = new AssessmentSharePage();

    boolean isAdminster, searchElementExist = true;
    boolean isPreview, isScan, isViewResult, isShare, isDuplicate, isDelete = false;
    static boolean isTraversed = false;

    public void e_ClickViewAssessments() {
        /*Click Assessments icon in Navigation sidebar*/
        DriverHelper.clickXpath(dashboardPOM.getAssessmentsIcon());

        /*Click View Assessments Link*/
        DriverHelper.clickXpath(dashboardPOM.getViewAssessmentsLink());

        /*Wait until View Assessments header appears*/
        BrowserInitHelper.waitUntil(dashboardPOM.getViewAssessmentsHeader());
    }

    public void v_VerifyAssessmentTypeFlexibleDataTable() {
        /*Validate the display of Assessment Data Table*/
        //WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessmentsPage.getAssessmentsDataTable()));
        //Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));

    }

    public void v_VerifyDropDownOptions() {
        int dropdownCount = 0;

        BrowserInitHelper.getInstance().findElements(By.xpath(viewAssessmentsPage.getItemBankActionDropDownOptionsList()));
        List<WebElement> actualOptions = BrowserInitHelper.getInstance().findElements(By.xpath(viewAssessmentsPage.getItemBankActionList()));

//        List<String> expectedOptions = new ArrayList<String>();
//        expectedOptions.add("Administer");
//        expectedOptions.add("Preview");
//        expectedOptions.add("Scan");
//        expectedOptions.add("View Results");
//        expectedOptions.add("Share");
//        expectedOptions.add("Duplicate");
//        expectedOptions.add("Delete");
//
//        boolean isExist = false;
//
//        for(int i=0;i<expectedOptions.size();i++){
//            if(actualOptions.get(i).getText().trim().equalsIgnoreCase(expectedOptions.get(i))){
//                isExist = true;
//            }
//            else{
//                isExist = false;
//                break;
//            }
//        }
//
//        Assert.assertTrue(isExist);

        boolean isExist = false;
        try {
            for (int i = 0; i < actualOptions.size(); i++) {
                String dropdownText = actualOptions.get(i).getText();
                switch (dropdownText) {
                    case "Administer":
                    case "Preview":
                    case "Scan":
                    case "View Results":
                    case "Share":
                    case "Duplicate":
                    case "Delete":
                        dropdownCount++;
                        break;
                }
            }
            if (actualOptions.size() == dropdownCount) {
                isExist = true;
            }
            System.out.println(actualOptions.size() + "actualsize");
            System.out.println(dropdownCount + "dropdownCount");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isExist);
    }

    public void e_ClickCreatedByMe() {
        /*Click Created By Me link*/
        DriverHelper.waitForPageLoadComplete();
        DriverHelper.clickXpath(viewAssessmentsPage.getCreatedByMe());
    }

    public void v_VerifyCreatedByMeHeader() {
        /*Validate Created By me Header*/
        browserHelper.assertion(viewAssessmentsPage.getCreatedByMeHeader(), "Created By Me");
    }

    public void e_ClickAddFilters() {
        /*Click Add Filter button*/
        DriverHelper.waitUntilLoaderInvisible();
        DriverHelper.clickXpath(viewAssessmentsPage.getAddFilters());
    }

    public void v_VerifyFilterModal() {
        /*Validate the display of Filters Modal*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessments_AddFilters.getAddFilterPopup()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_SelectAssessmentTypeAndSearch() {
        /*Click Type filter*/
        DriverHelper.clickXpath(viewAssessments_AddFilters.getTypeFilter());


        /*Enter Assessment in Type Filter*/
        DriverHelper.sendKeysXpath(viewAssessments_AddFilters.getTypeFilter(), "Assessment", BrowserInitHelper.getWaiter(), BrowserInitHelper.getInstance());

        /*Select Item Bank option*/
        DriverHelper.clickXpath(viewAssessments_AddFilters.getTypeOption("Assessment"));

        /*Click Search button*/
        JavascriptHelper.clickID_JS(viewAssessments_AddFilters.getSearchButton_AddFilters());
        DriverHelper.waitUntilLoaderInvisible();

        searchElementExist = DriverHelper.verifyDisplayByXpath(viewAssessmentsPage.getTypeAssessment());
        if (searchElementExist) {
            System.out.println("searchElementExist ==>" + searchElementExist);
        } else {
            v_VerifyViewAssessmentsPage();
            e_ClickCreatedByMe();
            v_VerifyCreatedByMeHeader();
            e_ClickAddFilters();
            v_VerifyFilterModal();
            e_SelectAssessmentTypeAndSearch();
        }
    }

    public void e_ClickActionDropDown() {

        //JavascriptHelper.ScrollTillElement_Javascript(viewAssessmentsPage.getFlexibleActionDropDown());
        BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessmentsPage.getSearchText())).sendKeys(viewAssessmentsPage.getFlexibleTitle());
        //DriverHelper.clickXpath(viewAssessmentsPage.getSearchButton());
        DriverHelper.clickId(viewAssessmentsPage.getSearchButton(), BrowserInitHelper.getWaiter(), BrowserInitHelper.getInstance());
        DriverHelper.waitUntilLoaderInvisible();
        /*Click Action drop down*/
        DriverHelper.clickXpath(viewAssessmentsPage.getFlexibleActionList());
    }

    public void e_ClickActionDropDownAndSelectAdministerOption() {
        /*Click Administer option*/
        viewAssessmentsController.clickFlexibleAdministerOption("Administer");
    }

    public void v_VerifyOnlineTestingAssessmentAdministrationsPage() {
        isTraversed = true;
        /*Validate the Online Testing - Assessment Administrations page header*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(onlineTesting.getOnlineTestingHeader()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_ClickActionDropDownAndSelectPreviewOption() {
        /*Click Preview option*/
        viewAssessmentsController.clickFlexiblePreviewOption("Preview");
    }

    public void v_VerifyOnlineAssessmentPreviewModal() {
        /*Validate the Online Assessment Preview - Administration Options modal header*/
        //WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessmentsPage.getOnlineAssessmentPreviewHeader()));
        //Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
        browserHelper.assertion(viewAssessmentsPage.getOnlineAssessmentPreviewHeader(), "Online Assessment Preview - Administration Options");
    }

    public void e_ClickPreviewButton() {
        /*Click Preview button*/
        DriverHelper.clickXpath(viewAssessmentsPage.getPreviewButton());
    }

    public void v_VerifyOnlineAssessmentPreviewWindow() {
        /*Switch to Online Assessment Testing -Preview window*/
        ViewAssessmentsController.getWindowHandleAndSwitchWindow(BrowserInitHelper.getInstance());

        /*Validate the existence of Begin Test button in Online Assessment - Testing Preview window*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(onlineTestingPreviewWindow.getBeginTestButton()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_CloseOnlineAssessmentPreviewWindow() {
        /*Close the Online Assessment Preview Window and Switch back to main window*/
        ViewAssessmentsController.switchToMainWindow(BrowserInitHelper.getInstance());
        this.e_ClickViewAssessments();
    }

    public void e_ClickActionDropDownAndSelectScanOption() {
        /*Click Scan option*/
        DriverHelper.clickXpath(viewAssessmentsPage.getitemBankActionMenuOptionsScan());
    }

    public void v_VerifyCameraGraderPage() {
        //Forst alert
        DriverHelper.switchToAlert(BrowserInitHelper.getWaiter());
        /*Validate the existence of Camera canvas in Scanning page*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(cameraGraderPage.getCameraCanvas()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_ClosePopupAndClickViewAssessments() {
        /*Click Assessments icon in Navigation sidebar*/
        DriverHelper.clickXpath(dashboardPOM.getAssessmentsIcon());

        /*Click View Assessments Link*/
        DriverHelper.clickXpath(dashboardPOM.getViewAssessmentsLink());

        /*Close Browser popup*/
        DriverHelper.switchToAlert(BrowserInitHelper.getWaiter());

        /*Wait until View Assessments header appears*/
        BrowserInitHelper.waitUntil(dashboardPOM.getViewAssessmentsHeader());

        //setAttribute("viewResults", true);
    }

    public void e_SetShowAssessmentsWithDataToggleButton() {
        /*Set Show Assessments Without Data Toggle Button to Show mode*/
        JavascriptHelper.ClickByID_Javascript(viewAssessmentsPage.getShowAssessmentsWithoutDataToggleButton());

        /*Wait until loading icon disappears*/
        DriverHelper.waitUntilLoaderInvisible();
    }

    public void v_VerifyAssessmentHavingStudentResponse() {
        /*Wait until the display of Assessment data table*/
        BrowserInitHelper.waitUntil(viewAssessmentsPage.getAssessmentsDataTable());

        /*Validate the Assessment which has Student data*/
        WebElement currentDataElement = BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessmentsPage.getHasStudentResponse()));
        Assert.assertTrue(DriverHelper.elementExistence(currentDataElement, BrowserInitHelper.getInstance()));
    }

    public void e_ClickActionDropDownAndSelectViewResultsOption() {
        /*Click Action drop down*/
//        JavascriptHelper.ScrollTillElement_Javascript(viewAssessmentsPage.getFlexibleActionList());
        DriverHelper.clickXpath(viewAssessmentsPage.getFlexibleActionList());

        /*Click View Results option*/
        DriverHelper.clickXpath(viewAssessmentsPage.getitemBankActionMenuOptionsViewResult());
        //viewAssessmentsController.clickFlexibleView_ResultOption("View Results");
    }

    public void v_VerifyStudentResponsesPage() {
        //setAttribute("viewResults", false);
        //setAttribute("share", true);

        /*Validate the existence of Save button in Student Responses page*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(viewStudentsResponsesPage.getSaveButton()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_ClickActionDropDownAndSelectViewShareOption() {
        /*Scroll till Action drop down*/
        // JavascriptHelper.ScrollTillElement_Javascript(viewAssessmentsPage.getFlexibleActionDropDown());

        /*Click Share option*/
        viewAssessmentsController.clickFlexibleShareOption("Share");
    }

    public void v_VerifyAssessmentSharePage() {
        //setAttribute("share", false);
        //setAttribute("duplicate", true);

        /*Validate the existence of Share canvas in share Assessement page*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(assessmentSharePage.getShareCanvas()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_ClickActionDropDownAndSelectDuplicateOption() {
        /*Scroll till Action drop down*/
        // JavascriptHelper.ScrollTillElement_Javascript(viewAssessmentsPage.getFlexibleActionDropDown());

        /*Click Duplicate option*/
        viewAssessmentsController.clickFlexibleDuplicateOption("Duplicate");

        /*Click OK in Duplicate alert popup*/
        DriverHelper.switchToAlert(BrowserInitHelper.getWaiter());
    }

    public void v_VerifyAssessmentDuplication() {
        //setAttribute("duplicate", false);
        //setAttribute("deleteAssessment", true);
    }

    public void e_ClickActionDropDownAndSelectDeleteOption() {
        /*Scroll till Action drop down*/
        //JavascriptHelper.ScrollTillElement_Javascript(viewAssessmentsPage.getFlexibleActionDropDown());

        /*Click Delete option*/
        viewAssessmentsController.clickFlexibleDeleteOption("Delete");
    }

    public void v_VerifyAssessmentDeleteModal() {
        /*Validate the existence of Assessment Delete alert*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessmentsPage.getDeleteAlertMessage()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void e_ClickDeleteButton() {
        /*Check Yes, I am sure that the assessment is the one I want to delete checkbox*/
        DriverHelper.clickXpath(viewAssessmentsPage.getDeleteUnderstandCheckbox());

        /*Yes, Delete the assessment.*/
        DriverHelper.clickXpath(viewAssessmentsPage.getDeleteAgreeCheckbox());

        /*Click Delete Assessment button*/
        DriverHelper.clickXpath(viewAssessmentsPage.getDeleteAssessementButton());
    }

    public void v_VerifyAssessmentDelete() {
        //setAttribute("deleteAssessment", false);

        //setAttribute("functionDropDown", true);
        /*Validate the existence of Assessment Delete success message*/
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(viewAssessmentsPage.getAlertSuccess()));
        Assert.assertTrue(DriverHelper.elementExistence(Element, BrowserInitHelper.getInstance()));
    }

    public void v_VerifyViewAssessmentsPage() {
        /*Wait until the display of View Assessments header*/
        BrowserInitHelper.waitUntil(dashboardPOM.getViewAssessmentsHeader());

        browserHelper.assertion(dashboardPOM.getViewAssessmentsHeader(), "View Assessments");
    }

    public void v_VerifyFluenceTypeDataTable() {
        DriverHelper.waitUntilLoaderInvisible();
    }

    public void v_VerifyAssessmentTypeDataTable() {
        ConsoleLogger.SuccessLog("Completed the ViewAssessmentsFlexibleDropDownOptionsTest Java class");
    }
}
