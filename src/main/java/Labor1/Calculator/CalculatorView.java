package Labor1.Calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorView extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(this.getClass().getResourceAsStream("/labor1/calculator.fxml"));
        primaryStage.setTitle("Swapper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
