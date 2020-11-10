import processing.core.PApplet;

public class Tile extends PApplet{

    public int i;
    public int j;
    public boolean[] walls;
    public boolean visited;

    public Tile(int i, int j) {
        this.i = i;
        this.j = j;
        this.visited = false;
        this.walls = new boolean[]{ true, true, true, true };

    }
}
