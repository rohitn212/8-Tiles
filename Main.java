/* ------------------------------------------------
 * 8 Tiles GUI
 * Class: CS 342, Fall 2016
 * System: Windows 10, IntelliJ IDE
 * Author Code Number: 1636N
 * -------------------------------------------------
 */

package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void printAuthorInfo()   {
        System.out.println("Author Code Name:Heal");
        System.out.println("Class: CS342, Fall 2016");
        System.out.println("Program 4:8 Tiles GUI");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("8 Tiles");
        printAuthorInfo();
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
