package sample;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.util.Duration;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryPuzzle extends Application {

    private static final int NUM_OF_PAIRS = 8;
    private static final int NUM_PER_ROW = 4;
    private static String images[] = {"chat.jpg", "chat2.jpg", "chien.jpg", "chien2.jpg", "lapin.jpg",
            "lapin2.jpg", "ourson.jpg", "ourson2.jpg", "chat.jpg", "chat2.jpg", "chien.jpg", "chien2.jpg", "lapin.jpg",
            "lapin2.jpg", "ourson.jpg", "ourson2.jpg"};
    private String lurl = "";
    private Rectangle tile = null;
    private Parent createContent() throws MalformedURLException {
        GridPane root = new GridPane();
        root.setPrefSize(800, 800);
        List<Tile> tiles = new ArrayList<>();
        Rectangle rectangle;

        for(int i = 0; i < images.length; i++)
        {
            rectangle = new Rectangle(200, 200);

            String imageURI = new File(images[i]).toURI().toString();

            Tile tile = new Tile(rectangle, imageURI);
            tiles.add(tile);
        }
        Collections.shuffle(tiles);
     //   System.out.println(Arrays.asList(tiles));
        int compteur = 0;
            for(int j = 0; j < 4; j++)
            {
                for(int k = 0; k < 4; k++) {
                    Tile tile = tiles.get(compteur);
                   // tile.setTranslateX(200 * (compteur% NUM_PER_ROW));
                  //  tile.setTranslateY(200 * (compteur% NUM_PER_ROW));
                    root.setConstraints(tile, j, k);
                    root.setGridLinesVisible(true);
                    root.getChildren().add(tile);
                    compteur ++;

                }
            }
        return root;
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

    }

    class Tile extends StackPane
    {


        private Rectangle selected = null;
        private boolean bool = false;


        public Tile(Rectangle img, String url) {

            Image image = new Image(url, 200,200,false,true);
            ImageView imageView = new ImageView(image);

            img.setStroke(Color.BLACK);
            img.setFill(new ImagePattern(imageView.getImage()));
            img.setStrokeType(StrokeType.INSIDE);

            setAlignment(Pos.CENTER);
            this.getChildren().add(img);
         //   System.out.println(this.getChildren());
            this.setWidth(200);
            this.setHeight(200);
            this.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
            setOnMouseClicked(event ->
            {

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
                            open(tile);
                            open(img);
                    }
                    tile = null;
                    lurl = "";
                    });
                }

              //
            });

            close(img);

        }

        public void open(Runnable action)
        {
           //
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
          //
            img.setOpacity(0);
        }
        public void close1(Rectangle img)
        {
            img.setOpacity(1);
            FadeTransition ft = new FadeTransition(Duration.seconds(2), img);
            ft.setToValue(0);
            ft.play();
           // img.setOpacity(0);
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
