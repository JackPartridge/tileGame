import processing.core.PApplet;

public class Launch extends PApplet {

    public static void main(String[] args) {
        GridGen grid = new GridGen();
        PApplet.runSketch(new String[]{ "Tile Game" }, grid);
    }
}
