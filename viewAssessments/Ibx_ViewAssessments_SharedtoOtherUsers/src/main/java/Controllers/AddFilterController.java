package Controllers;

import Helpers.BrowserInitHelper;
import Helpers.DriverHelper;
import Pom.Dashboard;
import Pom.ViewAssessmentsPage;
import Pom.ViewAssessments_AddFilters;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static Helpers.JavascriptHelper.*;

//import org.openqa.selenium.*;

/**** Add Filter Controller *****/
public class AddFilterController {

    DriverHelper Helper = new DriverHelper();
    ViewAssessmentsPage viewassessmentspage = new ViewAssessmentsPage();
    ViewAssessments_AddFilters viewAssessmentspage_addFilters = new ViewAssessments_AddFilters();
    ViewAssessments_AddFilters viewAssessmentsPage_AddFilters = new ViewAssessments_AddFilters();
    Dashboard dashboardPOM = new Dashboard();

    /**
     * LoggerFactory
     */
    private static final Logger log = LoggerFactory.getLogger(BrowserInitHelper.class);

    public static void login(String un, String pw) {
        // logger(BrowserInitHelper.getInstance());
        highlight(BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("username"))));
        BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("username"))).clear();
        BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("username"))).sendKeys(un);
        highlight(BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("password"))));
        BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("password"))).clear();
        BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("password"))).sendKeys(pw);
        BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.id("button_next"))).click();
    }

    //Get all the Values of any Filter inside Add Filters
    public List<String> getFilterValues(String FilterListXpath) {
        //BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(FilterListXpath)));

        //List to store values and return
        List<String> FilterValues = new ArrayList<String>();
        waitUntilAjaxLoaded();

        //List of WebElement - Filter Dropdown
        List<WebElement> FilterList = BrowserInitHelper.getInstance().findElements(By.xpath(FilterListXpath));
        //FilterList = BrowserInitHelper.getInstance().findElements(By.xpath("//div[@id='select2-drop']//ul//li/div"));

        for (WebElement e : FilterList) {
            FilterValues.add(e.getText().trim());
        }

        System.out.println("FilterValues -->" + FilterValues);
        return FilterValues;
    }

    //Select Value from any Filter inside Add Filters
    public void setFilterValue(String FilterListXpath, String valuetoSet) {
        //Set Value to Filter
        System.out.println("inside setFilterValue....");

        WebElement filterElement = BrowserInitHelper.getInstance().findElement(By.xpath(FilterListXpath));
        filterElement.sendKeys(valuetoSet);
        Helper.clickXpath(viewAssessmentspage_addFilters.getFilteredValue(valuetoSet));
    }

    //Click Search Button
    public void clickSearch() {
        try {
            //Click using Javascript
            scrollintoView(viewAssessmentspage_addFilters.getSearchButton_AddFilters());
            clickID_JSJQuery("select2-drop-mask");

            BrowserInitHelper.getWaiter().until(ExpectedConditions.invisibilityOfElementLocated(By.id("select2-drop-mask")));
            BrowserInitHelper.getInstance().findElement(By.id(viewAssessmentspage_addFilters.getSearchButton_AddFilters())).click();
            //Wait until Page loads
            waitUntilAjaxLoaded();

        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    //Check Assertion for Text
    public boolean CheckAssertion(String Actual, String Expected) {
        boolean value = false;

        if (Expected.contains(Actual)) {
            value = true;
            log.info("Expected Text -->" + Expected);
            log.info("Actual Text -->" + Actual);
        }
        return value;
    }

    //Assertion for Created Date when sorted in Ascending Order
    public boolean AssertCreatedDate_AscendingOrder(String YearToCheck) {
        boolean value = false;

        //Check if class contains asc
        waitUntilAjaxLoaded();
        boolean checkEmptyTable = checkViewAssessments_EmptyTable(viewassessmentspage.getDataTableEmpty());
        if (checkEmptyTable) {
            value = true;
        } else {
            WebElement DateCreated = BrowserInitHelper.getInstance().findElement(By.xpath(viewassessmentspage.getDateCreated()));

            //Click Date Created
            DateCreated.click();
            waitUntilAjaxLoaded();

            //Wait until Date Created is clickable
            BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(viewassessmentspage.getFirstLinkDateCreatedAfterSearch())));
            WebElement DateCreatedAfterClick = BrowserInitHelper.getInstance().findElement(By.xpath(viewassessmentspage.getDateCreated()));
            String className = DateCreatedAfterClick.getAttribute("class");
            log.info("className - ascending order-->" + className);

            if (className.contains("sorting_asc")) {
                log.info("Already in Ascending Order..");

                //Assert Year to Check
                String CreatedDateFirstLink = Helper.getText(viewassessmentspage.getFirstLinkDateCreatedAfterSearch());
                log.info("Created Date - First Link -->" + CreatedDateFirstLink);
                boolean check = CheckAssertion(YearToCheck, CreatedDateFirstLink);
                if (check) {
                    value = true;
                }
            } else {
                //Click on Date Created
                DateCreated.click();
                waitUntilAjaxLoaded();

                //Wait until Date Created is clickable
                BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(viewassessmentspage.getFirstLinkDateCreatedAfterSearch())));

                //Assert Year to Check
                String CreatedDateFirstLink = Helper.getText(viewassessmentspage.getFirstLinkDateCreatedAfterSearch());
                boolean check = CheckAssertion(YearToCheck, CreatedDateFirstLink);
                if (check) {
                    value = true;
                }
            }
        }
        return value;
    }

    //Assertion for Created Date when sorted in Descending Order
    public boolean AssertCreatedDate_DescendingOrder(String YearToCheck) {
        boolean value = false;

        //Check if class contains desc
        waitUntilAjaxLoaded();
        boolean checkEmptyTable = checkViewAssessments_EmptyTable(viewassessmentspage.getDataTableEmpty());
        if (checkEmptyTable) {
            value = true;
        } else {
            WebElement DateCreated = BrowserInitHelper.getInstance().findElement(By.xpath(viewassessmentspage.getDateCreated()));

            //Click DateCreated
            DateCreated.click();
            waitUntilAjaxLoaded();

            //Wait until Date Created is clickable
            BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(viewassessmentspage.getFirstLinkDateCreatedAfterSearch())));

            WebElement DateCreatedAfterClick = BrowserInitHelper.getInstance().findElement(By.xpath(viewassessmentspage.getDateCreated()));
            String className = DateCreatedAfterClick.getAttribute("class");
            log.info("className - descending order -->" + className);

            if (className.contains("sorting_desc")) {
                log.info("Already in Descending Order...");

                //Assert Year to Check
                String CreatedDateFirstLink = Helper.getText(viewassessmentspage.getFirstLinkDateCreatedAfterSearch());
                log.info("Created Date - First Link..." + CreatedDateFirstLink);
                boolean check = CheckAssertion(YearToCheck, CreatedDateFirstLink);
                if (check) {
                    value = true;
                }
            } else {
                //Click on Date Created
                DateCreated.click();
                waitUntilAjaxLoaded();

                //Wait until Date Created is clickable
                BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(viewassessmentspage.getFirstLinkDateCreatedAfterSearch())));

                //Assert Year to Check
                String CreatedDateFirstLink = Helper.getText(viewassessmentspage.getFirstLinkDateCreatedAfterSearch());
                boolean check = CheckAssertion(YearToCheck, CreatedDateFirstLink);
                if (check) {
                    value = true;
                }
            }
        }
        return value;
    }

    //To get Grade Value for assertion, based on csv value of grade
    public String getGradeValue(String Grade) {
        String gradeValue = "";

        if (Grade.equalsIgnoreCase("Kindergarten")) {
            gradeValue = "K";
        }

        if (Grade.matches(".*\\d.*")) {
            gradeValue = Grade.replaceAll("[*a-zA-Z]", "");
            gradeValue = gradeValue.trim();
            log.info("Grade Value..." + gradeValue);
        }

        return gradeValue;
    }

    //To get Year Value from Date
    public String getYearfromDate(String DateValue) {
        String Year = "";
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(DateValue);

            System.out.println("date ---------->" + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            Year = Integer.toString(year);
            log.info("YEAR VALUE ==>" + Year);
        } catch (ParseException e) {

        }
        return Year;
    }

    //To check if View Assessments Table is Empty OR Not
    public boolean checkViewAssessments_EmptyTable(String XPath) {
        boolean value = false;
        waitUntilAjaxLoaded();

        try {
            Helper.getElementByXpath(XPath);
            value = true;
        } catch (ElementNotInteractableException e) {
            log.info("ElementNotInteractableException handled for checkViewAssessments_EmptyTable...");
        } catch (NoSuchElementException e) {
            log.info("NoSuchElementException handled for checkViewAssessments_EmptyTable...");
        } catch (Exception e) {
            log.info("Exception handled for checkViewAssessments_EmptyTable...");
        }
        return value;
    }

    //To verify display of Date Message - when End Date is less than Start Date in Add Filters
    public boolean checkMessage_WhenEndDate_LessThanStartDate(String XPath) {
        boolean value = false;

        try {
            BrowserInitHelper.getWaiter().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPath)));
            value = true;

            //Click Reset
            clickID_JS(viewAssessmentsPage_AddFilters.getResetButton_AddFilters());
        } catch (ElementNotInteractableException e) {
            log.info("ElementNotInteractableException handled for checkMessage_WhenEndDate_LessThanStartDate...");
        } catch (NoSuchElementException e) {
            log.info("NoSuchElementException handled for checkMessage_WhenEndDate_LessThanStartDate...");
        } catch (TimeoutException e) {
            log.info("TimeoutException handled for checkMessage_WhenEndDate_LessThanStartDate...");
        } catch (Exception e) {
            log.info("Exception handled for checkMessage_WhenEndDate_LessThanStartDate...");
        }
        return value;
    }

    //To Verify Date Created From OR To, in View Assessments Table after Search, from Date Created Filter
    public boolean verifyDateCreated(String DateCreated_From, String DateCreated_To) {
        boolean value = false;

        clickID_JS(viewAssessmentsPage_AddFilters.getResetButton_AddFilters());

        Helper.sendKeysXpath(viewAssessmentsPage_AddFilters.getDateCreated_Start(), DateCreated_From);
        Helper.sendKeysXpath(viewAssessmentsPage_AddFilters.getDateCreated_End(), DateCreated_To);
        clickSearch();

        //Check for No Data available in Table
        boolean checkEmptyTable = checkViewAssessments_EmptyTable(viewassessmentspage.getDataTableEmpty());
        if (checkEmptyTable) {
            // No Data Available is shown
            value = true;
        } else {
            //Check if Date Created contains one of the year
            boolean checkYear_From = AssertCreatedDate_AscendingOrder(getYearfromDate(DateCreated_From));
            boolean checkYear_To = AssertCreatedDate_DescendingOrder(getYearfromDate(DateCreated_To));

            if (checkYear_From || checkYear_To) {
                value = true;
            }
        }
        //Click Filter after Search
        Helper.clickXpath(viewassessmentspage.getFilterButtonAfterSearch());

        return value;
    }

    //To Verify Date Created Message (Alert message for user, when end date is less than start date in add filters)
    public boolean verifyDateCreated_Message(String DateCreated_From, String DateCreated_To) {
        boolean value = false;

        clickID_JS(viewAssessmentsPage_AddFilters.getResetButton_AddFilters());
        Helper.sendKeysXpath(viewAssessmentsPage_AddFilters.getDateCreated_Start(), DateCreated_From);
        Helper.sendKeysXpath(viewAssessmentsPage_AddFilters.getDateCreated_End(), DateCreated_To);
        clickSearch();

        //Check for End date must be after start date - Message
        boolean checkDateCreatedMessage = checkMessage_WhenEndDate_LessThanStartDate(viewassessmentspage.getDateCreatedMessage());
        if (checkDateCreatedMessage) {
            value = true;
        }

        return value;
    }

    //To Verify Favorite icon in Overview page
    public boolean checkFavoriteinOverview(String Xpath) {
        boolean check = false;

        try {
            WebElement FavoriteIcon = BrowserInitHelper.getInstance().findElement(By.xpath(Xpath));
            check = true;
        } catch (ElementNotInteractableException e) {
            log.info("ElementNotInteractableException handled for checkFavoriteinOverview...");
        } catch (NoSuchElementException e) {
            log.info("NoSuchElementException handled for checkFavoriteinOverview...");
        } catch (TimeoutException e) {
            log.info("TimeoutException handled for checkFavoriteinOverview...");
        } catch (Exception e) {
            log.info("Exception handled for checkFavoriteinOverview...");
        }

        return check;
    }
}