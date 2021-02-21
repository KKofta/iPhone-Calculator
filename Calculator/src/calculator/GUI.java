package calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Calculator");
        stage.show();
    }

    private float data = 0;
    private float dataMultiOrder = 0;
    boolean clickedPlus = false;
    boolean clickedMinus = false;
    boolean clickedMulti = false;
    boolean clickedDivide = false;
    boolean clickedEquals = false;
    boolean newOperation = true;
    boolean isNegative = false;
    boolean isActive = false;

    @FXML
    private Button zero;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    
    @FXML
    private Button plus;
    @FXML
    private Button minus;
    @FXML
    private Button multi;
    @FXML
    private Button divide;
    @FXML
    private Button equals;
    
    @FXML
    private Button cl;
    @FXML
    private Button plusmin;
    @FXML
    private Button percent;
    @FXML
    private Button coma;
    
    @FXML
    private TextField display;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(zero)) {
            displayClickedNumber(0);
        } else if (event.getSource().equals(one)) {
            displayClickedNumber(1);
        } else if (event.getSource().equals(two)) {
            displayClickedNumber(2);
        } else if (event.getSource().equals(three)) {
            displayClickedNumber(3);
        } else if (event.getSource().equals(four)) {
            displayClickedNumber(4);
        } else if (event.getSource().equals(five)) {
            displayClickedNumber(5);
        } else if (event.getSource().equals(six)) {
            displayClickedNumber(6);
        } else if (event.getSource().equals(seven)) {
            displayClickedNumber(7);
        } else if (event.getSource().equals(eight)) {
            displayClickedNumber(8);
        } else if (event.getSource().equals(nine)) {
            displayClickedNumber(9);
        } else if (event.getSource().equals(plusmin)) {
            if (!checkError() && newOperation == false) {
                float opposite = Float.parseFloat(display.getText()) * (-1);
                displayCalculatedNumber(opposite);
            }
        } else if (event.getSource().equals(coma)) {
            if (!checkError() && newOperation == false && !display.getText().contains(".")) {
                String text = display.getText();
                display.setText(text + ".");
            }
        } else if (event.getSource().equals(percent)) {
            if (!checkError() && newOperation == false || !checkError() && clickedEquals == true) {
                float makePercent = Float.parseFloat(display.getText()) / 100;
                display.setText(String.valueOf(makePercent));
                newOperation = true;
                clickedEquals = false;
            }
        } else if (event.getSource().equals(plus)) {
            if (!checkError()) {
                checkConditionsForPlusOrMinusOrEqualsClick();

                data = Float.parseFloat(display.getText());
                setVariables("clickedPlus");
            }
        } else if (event.getSource().equals(minus)) {
            if (!checkError()) {
                checkConditionsForPlusOrMinusOrEqualsClick();

                data = Float.parseFloat(display.getText());
                setVariables("clickedMinus");
            }
        } else if (event.getSource().equals(multi)) {
            if (!checkError()) {
                checkConditionsForMultiOrDivideClick();

                data = Float.parseFloat(display.getText());
                setVariables("clickedMulti");
            }
        } else if (event.getSource().equals(divide)) {
            if (!checkError()) {
                checkConditionsForMultiOrDivideClick();

                data = Float.parseFloat(display.getText());
                setVariables("clickedDivide");
            }
        } else if (event.getSource().equals(cl)) {
            display.setText("0");
            clearVariables();
        } else if (event.getSource().equals(equals)) {
            if (!checkError()) {
                checkConditionsForPlusOrMinusOrEqualsClick();
            }
            clearVariables();
            clickedEquals = true;
        }
    }

    private void checkConditionsForPlusOrMinusOrEqualsClick() {
        Float secondOperand = Float.parseFloat(display.getText());
        if (clickedPlus == true && isActive == false) {
            Float plusResult = data + secondOperand;
            displayCalculatedNumber(plusResult);
        } else if (clickedMinus == true && isActive == false) {
            Float minusResult = data - secondOperand;
            displayCalculatedNumber(minusResult);
        } else if (clickedMulti == true && isActive == false) {
            Float multiResult = dataMultiOrder + checkMultiOrderResult(secondOperand);
            displayCalculatedNumber(multiResult);
            dataMultiOrder = 0;
        } else if (clickedDivide == true && isActive == false) {
            if (secondOperand == 0) {
                setError();
            } else {
                Float divideResult = dataMultiOrder + checkDivideOrderResult(secondOperand);
                displayCalculatedNumber(divideResult);
            }
            dataMultiOrder = 0;
        }
    }

    private void checkConditionsForMultiOrDivideClick() {
        if (clickedPlus == true && isActive == false) {
            dataMultiOrder = data;
            isNegative = false;
        } else if (clickedMinus == true && isActive == false) {
            dataMultiOrder = data;
            isNegative = true;
        } else if (clickedMulti == true && isActive == false) {
            Float secondOperand = Float.parseFloat(display.getText());
            Float multiResult = data * secondOperand;
            displayCalculatedNumber(multiResult);
        } else if (clickedDivide == true && isActive == false) {
            Float secondOperand = Float.parseFloat(display.getText());
            if (secondOperand == 0) {
                setError();
            } else {
                float divideResult = data / secondOperand;
                displayCalculatedNumber(divideResult);
            }
        }
    }

    private boolean checkError() {
        return display.getText().equals("Error");
    }

    private void setError() {
        display.setText("Error");
        clearVariables();
    }

    private void clearVariables() {
        isActive = false;
        newOperation = true;
        clickedPlus = false;
        clickedMinus = false;
        clickedMulti = false;
        clickedDivide = false;
        clickedEquals = false;
        isNegative = false;
        data = 0;
        dataMultiOrder = 0;
    }

    private void setVariables(String variable) {
        isActive = true;
        newOperation = true;
        clickedEquals = false;
        clickedPlus = false;
        clickedMinus = false;
        clickedMulti = false;
        clickedDivide = false;
        if (variable.equalsIgnoreCase("clickedPlus")) {
            clickedPlus = true;
        } else if (variable.equalsIgnoreCase("clickedMinus")) {
            clickedMinus = true;
        } else if (variable.equalsIgnoreCase("clickedMulti")) {
            clickedMulti = true;
        } else if (variable.equalsIgnoreCase("clickedDivide")) {
            clickedDivide = true;
        }
    }

    private void displayClickedNumber(int button) {
        String number = Integer.toString(button);
        if (newOperation == true || checkError()) {
            display.setText(number);
            newOperation = false;
        } else {
            display.setText(display.getText() + number);
        }
        isActive = false;
    }

    private void displayCalculatedNumber(float text) {
        String stringText = String.valueOf(text);
        if (stringText.contains(".")) {
            String[] splitDot = stringText.split("\\.", 2);

            if (splitDot[1].equalsIgnoreCase("0")) {
                display.setText(String.format("%.0f", text));
            } else {
                display.setText(String.valueOf(text));
            }
        } else {
            display.setText(String.valueOf(text));
        }
    }

    private float checkMultiOrderResult(float secondOperand) {
        if (!isNegative) {
            return data * secondOperand;
        } else {
            return data * secondOperand * (-1);
        }
    }

    private float checkDivideOrderResult(float secondOperand) {
        if (!isNegative) {
            return data / secondOperand;
        } else {
            return data / secondOperand * (-1);
        }
    }
}
