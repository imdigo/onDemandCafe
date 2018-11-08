package ondemandcafe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ondemandcafe.view.IngredientEditController;
import ondemandcafe.view.IngredientOverview;
import ondemandcafe.model.Ingredient;
import ondemandcafe.model.IngredientHandler;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("OOP Cafe");

        initRootLayout();

        showIngredientOverview();

	}
	public void initRootLayout() {
        try {
            // fxml 파일에서 상위 레이아웃을 가져온다.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // 상위 레이아웃을 포함하는 scene을 보여준다.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	 public void showIngredientOverview() {
	        try {
	            
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/IngredientOverview.fxml"));
	            AnchorPane IngredientOverview = (AnchorPane) loader.load();

	            
	            
	            // 연락처 요약을 상위 레이아웃 가운데로 설정한다.
	            rootLayout.setCenter(IngredientOverview);
	            
	            IngredientHandler temp=new IngredientHandler();
	            IngredientOverview controller = loader.getController();
	            controller.setMainApp(temp);

	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	   public boolean showIngredientEditDialog(Ingredient ingredient) {
		
		 try {
			 	FXMLLoader loader=new FXMLLoader();
			 	loader.setLocation(MainApp.class.getResource("view/IngredientEditDialog.fxml"));
			 	AnchorPane page=(AnchorPane) loader.load();
			 
			 
			 	Stage dialogStage = new Stage();
			 	
		        dialogStage.setTitle("Edit Ingredient");
		        dialogStage.initModality(Modality.APPLICATION_MODAL);
		        dialogStage.initOwner(primaryStage);
		        
		      
		        Scene scene = new Scene(page);
			 	dialogStage.setScene(scene);
		       

		        IngredientEditController controller = loader.getController();
		        controller.setDialogStage(dialogStage);
		        controller.setIngredient(ingredient);

		        
		        dialogStage.showAndWait();
		        
		        return controller.isOkClicked();
		 }
		 
		 catch(IOException e) {
			 e.printStackTrace();
			
			 return false;
		 }
	 }

	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }

	
	public static void main(String[] args) {
		launch(args);
	}
}
