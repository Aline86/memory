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

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javafx.scene.text.TextAlignment.CENTER;
/*
"chat_violet.jpg", "cochon.jpg", "jungle.jpg", "koala.jpg", "poule.jpg", "poule.jpg", "question.jpg", "pingouin.jpg",
            "abeille.jpg", "chat_violet.jpg", "cochon.jpg", "jungle.jpg", "koala.jpg", "poule.jpg", "poule.jpg", "question.jpg", "pingouin.jpg",
            "picsou.jpg", "picsou.jpg"
 */
public class MemoryPuzzle extends Application {
    // Initialisation avec des variables globales pour toutes les classes
    private int NUM_OF_PAIRS = 0;
    private int NUM_PER_ROW = 0;
    private int taille = 0;
    private int tailleboard = 0;
    private static String images[] = {"chat.jpg", "chat2.jpg", "chien.jpg", "chien2.jpg", "lapin.jpg",
            "lapin2.jpg", "ourson.jpg", "ourson2.jpg", "chat.jpg", "chat2.jpg", "chien.jpg", "chien2.jpg", "lapin.jpg",
            "lapin2.jpg", "ourson.jpg", "ourson2.jpg", "abeille.jpg",
            "chat_violet.jpg", "cochon.jpg", "jungle.jpg", "koala.jpg", "poule.jpg", "poule.jpg", "question.jpg", "pingouin.jpg",
            "abeille.jpg", "chat_violet.jpg", "cochon.jpg", "jungle.jpg", "koala.jpg", "poule.jpg", "question.jpg", "question.jpg", "pingouin.jpg",
            "picsou.jpg", "picsou.jpg" };
    private String lurl = "";
    private Rectangle tile = null;
    private Object nbofplayers = null;
    private Object difficulte = null;
    int nbofplayesconvertion = 0;

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
    int player = 0;
    int PlayerOne = 0;
    int PlayerTwo = 0;
    int begin = 0;
    int coups = 0;

    private Parent createContent(BorderPane border, Button button) throws MalformedURLException {
        System.out.println("create");
        GridPane root1 = new GridPane();
        VBox vbox = new VBox(50);
        System.out.println(images.length);
        root1.setPrefSize(tailleboard, tailleboard);
        List<Tile> tiles = new ArrayList<>();
        Rectangle rectangle;

        // Création des tuiles
        for(int i = 0; i < (NUM_PER_ROW*NUM_PER_ROW); i++)
        {

            rectangle = new Rectangle(taille, taille);
            String imageURI = new File(images[i]).toURI().toString();
            Tile tile = new Tile(rectangle, imageURI);
            tiles.add(tile);
        }

        Collections.shuffle(tiles);
        // Création du board qui va accueillir les tuiles
        // Insertion des tuiles
        int compteur = 0;
        for(int j = 0; j < NUM_PER_ROW; j++)
        {
            for(int k = 0; k < NUM_PER_ROW; k++) {
                System.out.println(j);
                Tile tile = tiles.get(compteur);
                root1.setConstraints(tile, j, k);
                root1.setGridLinesVisible(true);
                root1.getChildren().add(tile);

                compteur ++;
            }
        }
        sp.setMinWidth(250);

        // Création des Players en display none
        for ( int i =1; i < 5; i++ )
        {
            StackPane rec = new StackPane();
            Label t = new Label("Player " + i);
            t.setFont(new Font(25));

            rec.getChildren().add(t);
            sp.setConstraints(rec, 0, i);
            sp.setMaxWidth(250);
            sp.setAlignment(Pos.CENTER);
            rec.setOpacity(0);
            sp.getChildren().add(rec);

        }
       for ( int i =0; i < nbofplayesconvertion; i++ )
        {
           // System.out.println(nbofplayesconvertion);
            StackPane rec = new StackPane();
            Label t = new Label("Player" + i);
            sp.getChildren().get(i).setOpacity(1);
        }
            border.setRight(sp);
            border.setCenter(root1);
            border.setTop(button);
            border.setAlignment(button, Pos.CENTER);

        return border;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button  button = new Button("Reset");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                reset(primaryStage, button);
            }
        });
      //  primaryStage.setScene(new Scene(this.createContent(root, button)));
        display_combo(primaryStage, button);


    }
    public void reset(Stage primaryStage, Button button)
    {
        if(compteurNbPaires == NUM_OF_PAIRS){
            //   compteurTours++;
            compteurNbPaires = 0;
            nbpoints = 0;
            saveNbPoints = 0;
            player = 0;
            BorderPane root = new BorderPane();
            try {

                primaryStage.setScene(new Scene(createContent(root, button)));
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            primaryStage.show();
        }
        else if(compteurNbPaires > 0 && compteurNbPaires < 8) {
            compteurNbPaires = 0;
            nbpoints = 0;
            saveNbPoints = 0;
            player = 0;
            nbJoueurs = 0;
            sp.getChildren().remove(sp2);
            sp2.getChildren().remove(lbl);
            sp.getChildren().get(1).setOpacity(0);
            BorderPane root = new BorderPane();
            try {

                primaryStage.setScene(new Scene(createContent(root, button)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    public int display_combo(Stage primaryStage, Button button){
        // Combo 1 pour l'alternance des joueurs
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1 Player",
                        "2 Players"
                );


        final ComboBox comboBox = new ComboBox(options);

        // Combo 2 pour le choix de la difficulté
        ObservableList<String> options2 =
                FXCollections.observableArrayList(
                        "facile",
                        "difficile"
                );

        final ComboBox comboBox2 = new ComboBox(options2);

        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().add(comboBox);
        secondaryLayout.getChildren().add(comboBox2);
        secondaryLayout.setAlignment(Pos.CENTER);
        Scene secondScene = new Scene(secondaryLayout, 400, 400);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);
        // Récupération de la valeur sélectionnée dans la comboBox
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                Object c = comboBox.getSelectionModel().getSelectedItem();
                nbofplayers = c;
                nextcombo(comboBox2, newWindow, c, button);
              //  newWindow.hide();
            //    player = 1;
                // Affichichage des joueurs
              //  return_players();

            }
        });


        newWindow.show();
        return 1;
    }
    public void nextcombo(ComboBox comboBox2, Stage newWindow, Object c, Button button)
    {
        comboBox2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                Object d = comboBox2.getSelectionModel().getSelectedItem();
                difficulte = d;

                newWindow.hide();
                //    player = 1;
                // Affichichage des joueurs
                return_players();
                Stage primaryStage = new Stage();
                BorderPane root = new BorderPane();
                try {
                    primaryStage.setScene(new Scene(createContent(root, button)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                primaryStage.show();
            }
        });

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
            player = 1;
        }

        if(difficulte.toString() == "facile")
        {
            NUM_OF_PAIRS = 8;
            NUM_PER_ROW = 4;
            taille = 200;
            tailleboard = 800;
        }
        else if (difficulte.toString() == "difficile")
        {
            NUM_OF_PAIRS = 18;
            NUM_PER_ROW = 6;
            taille = 100;
            tailleboard = 600;
        }
    }
    class Tile extends StackPane
    {
        private Rectangle selected = null;
        private boolean bool = false;
        public Tile(Rectangle img, String url) {
            // Initialisation de la tuile
            Image image = new Image(url, taille,taille,false,true);
            System.out.println(url);
            ImageView imageView = new ImageView(image);

            img.setStroke(Color.BLACK);
            img.setFill(new ImagePattern(imageView.getImage()));
            img.setStrokeType(StrokeType.INSIDE);

            setAlignment(Pos.CENTER);
            this.getChildren().add(img);
            this.setWidth(taille);
            this.setHeight(taille);
            this.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
            // Gestion de l'ouverture et du maintien des tuiles si paire trouvée
            setOnMouseClicked(event ->
            {
                coups++;
                if(coups == 2)
                {
                    coups = 0;
                    if(nbJoueurs != 1){
                        if(player == 1) {
                           player = 2;
                            sp.getChildren().get(0).setOpacity(0);
                            sp.getChildren().get(1).setOpacity(1);
                        }
                        else if(player == 2)
                        {
                            player = 1;
                            sp.getChildren().get(1).setOpacity(0);
                            sp.getChildren().get(0).setOpacity(1);
                        }
                    }
                }

               /* if(begin == 0)
                {
                    display_combo();
                }*/
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
                        close1(tile);
                        close1(img);

                        }else{
                        compteurNbPaires++;
                        if(compteurNbPaires == NUM_OF_PAIRS){
                            nbpoints = 0;
                            saveNbPoints = 0;
                            lbl.setText("");
                            StackPane thirdLayout = new StackPane();
                            Label lb = new Label();
                            if(nbJoueurs == 2)
                            {
                                if (PlayerOne >= 5) {
                                    lb.setText("Le joueur 1 a gagné");
                                } else if (PlayerOne <= 3) {
                                    lb.setText("Le joueur 2 a gagné");
                                } else {
                                    lb.setText("égalité");
                                }
                            }
                            else if (nbJoueurs == 1)
                            {
                                lb.setText("gagné");
                            }
                            thirdLayout.getChildren().add(lb);

                            Scene secondScene = new Scene(thirdLayout, 400, 400);
                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);
                            newWindow.show();
                        }

                        else
                        {
                            sp.getChildren().remove(sp2);
                            sp2.getChildren().remove(lbl);
                            if(nbJoueurs == 2)
                            {
                                if(player == 2){
                                    nbpoints++;
                                    lbl.setText("Player " + player1 + " a " + nbpoints + " points \n Player " + player2 + " a " + saveNbPoints + " points");
                                    lbl.setFont(new Font(20));
                                    sp2.getChildren().add(lbl);
                                    sp.getChildren().add(sp2);
                                    player = 2;
                                    PlayerTwo = saveNbPoints;
                                    sp.getChildren().get(0).setOpacity(0);
                                    sp.getChildren().get(1).setOpacity(1);
                                }
                                else if(player == 1)
                                {
                                    saveNbPoints++;
                                    lbl.setText("Player " + player1 + " a " + nbpoints + " points \n Player " + player2 + " a " + saveNbPoints + " points");
                                    lbl.setFont(new Font(20));
                                    sp2.getChildren().add(lbl);
                                    sp.getChildren().add(sp2);
                                    sp.getChildren().get(1).setOpacity(0);
                                    sp.getChildren().get(0).setOpacity(1);
                                    player = 1;
                                    PlayerOne = nbpoints;
                                }
                            }
                            else if(nbJoueurs == 1)
                            {
                                nbpoints++;
                                lbl.setText("Player " + player1 + " a " + nbpoints + " points");
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
