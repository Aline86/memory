package sample;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Convert extends Image {
    private MemoryPuzzle.Tile selected = null;
    private boolean bool = false;
    public Convert(String value, int i, int i1, boolean b, boolean b1)
    {

        super(value);
        new Image(value, i, i1, b, b1);
    }


}
