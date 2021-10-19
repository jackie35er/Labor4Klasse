package Labor1.Calculator;

import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private Slider number1;

    @FXML
    private ChoiceBox<Integer> number2;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    @FXML
    private Label result;

    private ToggleGroup calcOperations;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i = 0; i <= 9;i++){
            number2.getItems().add(i);
        }
        number2.setValue(0);
        calcOperations = new ToggleGroup();
        calcOperations.getToggles().add(radio1);
        calcOperations.getToggles().add(radio2);
        calcOperations.getToggles().add(radio3);
        calcOperations.getToggles().add(radio4);
        calcOperations.selectToggle(radio1);

        result.textProperty().bind(new ObjectBinding<>() {
            {
                super.bind(number1.valueProperty(),number2.valueProperty());
                super.bind(radio1.selectedProperty(),radio2.selectedProperty(),radio3.selectedProperty(),radio4.selectedProperty());
            }
            private String operation;

            @Override
            protected String computeValue() {
                updateOperation();
                if(number2.getValue() == 0 && radio4.isSelected())
                    return "Cannot divide through 0";
                return (int)number1.getValue() + " " + operation + " " + number2.getValue() + " = " + erg();
            }

            private void updateOperation(){
                if(radio1.isSelected()){
                    operation = "+";
                }
                else if(radio2.isSelected()){
                    operation = "-";
                }
                else if(radio3.isSelected()){
                    operation = "*";
                }
                else if(radio4.isSelected()){
                    operation = "/";
                }
            }

            private double erg(){
                switch (operation){
                    case "+" -> {return number1.getValue() + number2.getValue();}
                    case "-" -> {return number1.getValue() - number2.getValue();}
                    case "*" -> {return number1.getValue() * number2.getValue();}
                    case "/" -> {return number1.getValue() / number2.getValue();}
                    default -> {return 0;}
                }
            }
        });
    }


}
