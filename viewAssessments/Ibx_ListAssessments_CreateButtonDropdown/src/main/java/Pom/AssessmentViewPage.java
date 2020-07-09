package Pom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


//Page Object Model Class for AssessmentViewPage
public class AssessmentViewPage {
    private String titleInputField;
    private String descriptionInputField;
    private String chooseAssessmentsButton;
    private String saveButton;
    String dir = null;
    Properties properties = new Properties();
    InputStream input = null;
    private static String os = null;

    public AssessmentViewPage() {
        try {
            dir = System.getProperty("user.dir");
            dir = dir.replace("Ibx_ListAssessments_CreateButtonDropdown", "");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\Properties\\AssessmentViewPage.properties");
            } else {
                input = new FileInputStream(dir + "/Properties/AssessmentViewPage.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getTitleInputField() {
        titleInputField = properties.getProperty("TITLE_INPUT_FIELD");
        return titleInputField;
    }

    public String getDescriptionInputField() {
        descriptionInputField = properties.getProperty("DESCRIPTION_INPUT_FIELD");
        return descriptionInputField;
    }

    public String getChooseAssessmentsButton() {
        chooseAssessmentsButton = properties.getProperty("CHOOSE_ASSESSMENTS_BUTTON");
        return chooseAssessmentsButton;
    }

    public String getSaveButton() {
        saveButton = properties.getProperty("SAVE_BUTTON");
        return saveButton;
    }
}
