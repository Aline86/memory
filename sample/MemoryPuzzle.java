package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javafx.scene.text.TextAlignment.CENTER;

public class MemoryPuzzle extends Application {
    // Initialisation avec des variables globales pour toutes les classes
    private static final int NUM_OF_PAIRS = 8;
    private static final int NUM_PER_ROW = 4;
    private static String images[] = {"chat.jpg", "chat2.jpg", "chien.jpg", "chien2.jpg", "lapin.jpg",
            "lapin2.jpg", "ourson.jpg", "ourson2.jpg", "chat.jpg", "chat2.jpg", "chien.jpg", "chien2.jpg", "lapin.jpg",
            "lapin2.jpg", "ourson.jpg", "ourson2.jpg"};
    private String lurl = "";
    private Rectangle tile = null;
    private Object nbofplayers = null;
    int nbofplayesconvertion = 0;
    BorderPane root = new BorderPane();
    GridPane sp = new GridPane();
    int player1 = 1;
    int player2 = 2;
    int nbpoints = 0;
    int saveNbPoints = 0;
    int pos = 0;
    int compteurNbPaires = 0;
    int compteurTours = 0;
    final Label lbl = new Label();
    StackPane sp2 = new StackPane();
    int nbJoueurs = 0;
    private Parent createContent(BorderPane border, Button button) throws MalformedURLException {
        GridPane root = new GridPane();
        VBox vbox = new VBox(50);

        root.setPrefSize(800, 800);
        List<Tile> tiles = new ArrayList<>();
        Rectangle rectangle;

        // Création des tuiles
        for(int i = 0; i < images.length; i++)
        {
            rectangle = new Rectangle(200, 200);
            String imageURI = new File(images[i]).toURI().toString();
            Tile tile = new Tile(rectangle, imageURI);
            tiles.add(tile);
        }

        Collections.shuffle(tiles);
        // Création du board qui va accueillir les tuiles
        // Insertion des tuiles
        int compteur = 0;
        for(int j = 0; j < 4; j++)
        {
            for(int k = 0; k < 4; k++) {
                Tile tile = tiles.get(compteur);
                root.setConstraints(tile, j, k);
                root.setGridLinesVisible(true);
                root.getChildren().add(tile);
                compteur ++;
            }
        }
        sp.setMinWidth(250);

        // Création des Players en display none
        for ( int i =1; i < 5; i++ )
        {
            StackPane rec = new StackPane();
            Text t = new Text(250, 150, "Player " + i);
            t.setFont(new Font(25));

            rec.getChildren().add(t);
            sp.setConstraints(rec, 0, i);
            sp.setMaxWidth(250);
            sp.setAlignment(Pos.CENTER);
            rec.setOpacity(0);
            sp.getChildren().add(rec);

        }
            border.setRight(sp);
            border.setCenter(root);
            border.setTop(button);

        return border;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button button = new Button();
        button.setText("Start Playing");
        // Gestion de la combobox au clic du bouton
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(compteurNbPaires);
                // Si le compteur affiche 8 paires, rafraichissement du board
                if(compteurNbPaires == 1){
                    compteurTours++;
                    compteurNbPaires = 0;
                    System.out.println(compteurNbPaires);
                    if(compteurTours == 1){
                        System.out.println(compteurTours);
                        lbl.setText("Player 1 : "+nbpoints+" points");
                        lbl.setFont(new Font(20));
                        saveNbPoints = nbpoints;
                    }
                    else if(compteurTours == 2)
                    {
                        System.out.println(compteurTours);
                        lbl.setText("Player 1 : "+nbpoints+" points and Player 2 : "+saveNbPoints+" points");
                        lbl.setFont(new Font(15));
                    }
                    else if(compteurTours == 3)
                    {
                        primaryStage.close();
                    }

                    BorderPane root = new BorderPane();
                try {

                    primaryStage.setScene(new Scene(createContent(root, button)));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    primaryStage.show();
                } else {
                    ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "1 Player",
                            "2 Players"
                    );

                    // Création de la combobox
                    final ComboBox comboBox = new ComboBox(options);

                    StackPane secondaryLayout = new StackPane();
                    secondaryLayout.getChildren().add(comboBox);

                    Scene secondScene = new Scene(secondaryLayout, 400, 400);
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Second Stage");
                    newWindow.setScene(secondScene);
                    // Set position of second window, related to primary window.
                    newWindow.setX(primaryStage.getX() + 200);
                    newWindow.setY(primaryStage.getY() + 100);
                    // Récupération de la valeur sélectionnée dans la comboBox
                    comboBox.valueProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                            Object c = comboBox.getSelectionModel().getSelectedItem();
                            nbofplayers = c;

                            newWindow.hide();
                            // Affichichage des joueurs
                            return_players();

                        }
                    });
                    newWindow.show();
                }
            }
        });
            primaryStage.setScene(new Scene(this.createContent(root, button)));
            primaryStage.show();
    }
    public void return_players()
    {
        if(nbofplayers.toString() == "1 Player")
        {
            nbofplayesconvertion = 1;
            nbJoueurs = 1;
        }
        else if (nbofplayers.toString() == "2 Players")
        {
            nbofplayesconvertion = 2;
            nbJoueurs = 2;
        }
        for ( int i =0; i < nbofplayesconvertion; i++ )
        {
            System.out.println(nbofplayesconvertion);
            StackPane rec = new StackPane();
            Text t = new Text(250, 50, "Player" + i);
            sp.getChildren().get(i).setOpacity(1);
        }
    }
    class Tile extends StackPane
    {
        private Rectangle selected = null;
        private boolean bool = false;
        public Tile(Rectangle img, String url) {
            // Initialisation de la tuile
            Image image = new Image(url, 200,200,false,true);
            ImageView imageView = new ImageView(image);

            img.setStroke(Color.BLACK);
            img.setFill(new ImagePattern(imageView.getImage()));
            img.setStrokeType(StrokeType.INSIDE);

            setAlignment(Pos.CENTER);
            this.getChildren().add(img);
            this.setWidth(200);
            this.setHeight(200);
            this.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
            // Gestion de l'ouverture et du maintien des tuiles si paire trouvée
            setOnMouseClicked(event ->
            {
                sp.getChildren().remove(sp2);
                sp2.getChildren().remove(lbl);
                if(isOpen(img))
                {
                    return;
                }
                if(lurl == "" )
                {
                    lurl = url;
                    tile = img;
                    open(() -> {});
                }else{
                    open(() -> {

                    if(!hasSavedValue(lurl, url)){
                        nbpoints--;
                            close1(tile);
                            close1(img);
                        }else{
                        nbpoints++;
                        if(pos == 0){
                            compteurNbPaires++;
                            if(compteurTours == 0){
                                lbl.setText("Player " + player1 + " a " + nbpoints + " points");
                                lbl.setFont(new Font(20));
                                sp2.getChildren().add(lbl);
                                sp.getChildren().add(sp2);
                            }
                            else if(compteurTours == 1){
                                lbl.setText("Player " + player2 + " a " + nbpoints + " points");
                                lbl.setFont(new Font(20));
                                sp2.getChildren().add(lbl);
                                sp.getChildren().add(sp2);
                            }
                        }
                            open(tile);
                            open(img);
                    }
                    tile = null;
                    lurl = "";
                    });
                }
            });
            close(img);
        }
        public void open(Runnable action)
        {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.1), tile);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }
        public void open(Rectangle img)
        {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), img);
            ft.setToValue(1);
            ft.play();
        }
        public void close(Rectangle img)
        {
            img.setOpacity(0);
        }
        public void close1(Rectangle img)
        {
            img.setOpacity(1);
            FadeTransition ft = new FadeTransition(Duration.seconds(2), img);
            ft.setToValue(0);
            ft.play();
        }

        public boolean isOpen(Rectangle img)
        {
            return img.getOpacity() == 1;
        }

        public boolean hasSavedValue(String str, String str2)
        {
            return str.equals(str2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
