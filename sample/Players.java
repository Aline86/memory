package sample;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


class Players extends VBox {
    Object nbofplayers = null;
    int nbofplayesconvertion = 0;
    VBox vbox = new VBox(10);
    Stage window;
    public Players(Object nbofplayer )
    {
       /* if(nbofplayer != null)
        {

        }
        if(nbofplayer.toString() == "1 Player")
        {
            this.nbofplayesconvertion = 1;
        }
        else if (nbofplayer.toString() == "2 Players")
        {
            this.nbofplayesconvertion = 2;
        }*/
        this.window = window;
        for ( int i =0; i < 4; i++ )
        {
            StackPane rec = new StackPane();
            Text t = new Text(250, 50, "Player" + i);
            rec.getChildren().add(t);
            this.vbox.getChildren().add(rec);
        }

    }




}