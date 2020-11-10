import processing.core.PApplet;

import java.util.ArrayList;

public class GridGen extends PApplet {

    int clearRectX, clearRectY;      // Position of square button
    int undoRectX, undoRectY;
    int clearRectHeight = 30;
    int clearRectWidth = 90; // Diameter of rect
    int rectColor;
    int baseColor;
    int rectHighlight;
    boolean rectOver = false;
    boolean undoRectOver = false;

    public int width = 800;
    public int height = 800;

    public void settings() {
        size(width + 150, height);
    }

    public ArrayList<Tile> wantedMoves = new ArrayList<>();
    public ArrayList<Tile> instantChangeDeny = new ArrayList<>();
    public ArrayList<Tile> currentPath = new ArrayList<>();
    public int rows = 25;
    public int cols = 25;
    public int s = (height / rows);
    public Tile[][] grid = new Tile[rows][cols];


    public void setup() {
        background(0);

        rectColor = color(0);
        rectHighlight = color(51);
        baseColor = color(102);
        clearRectX = height + 30;
        clearRectY = 30;
        undoRectX = height + 30;
        undoRectY = 110;

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                grid[i][j] = new Tile(i, j);
            }
        }
        generate();
    }

    public void draw() {

        strokeWeight(1);
        update();
        if (rectOver) {
            fill(rectHighlight);
        } else {
            fill(rectColor);
        }
        rect(clearRectX, clearRectY, clearRectWidth, clearRectHeight);
        fill(255);
        text("CLEAR", height + 54, 50);

        if (undoRectOver) {
            fill(rectHighlight);
        } else {
            fill(rectColor);
        }
        rect(undoRectX, undoRectY, clearRectWidth, clearRectHeight);
        fill(255);
        text("UNDO", height + 57, 130);

        generate();
        noStroke();
        //
        fill(24, 145, 10);
        rect(24 * s, 12 * s, s, s);
        fill(171, 0, 0);
        rect(0, 12 * s, s, s);
        //
    }

    void update() {

        rectOver = overRect(clearRectX, clearRectY, clearRectWidth, clearRectHeight);
        undoRectOver = overRect(undoRectX, undoRectY, clearRectWidth, clearRectHeight);
    }

    public void generate() {
        stroke(0);
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (grid[i][j].visited) {
                    fill(40, 136, 215);
                } else {
                    fill(215, 119, 40);
                }
                //stroke(0);
                noStroke();
                //rect(i * s, j * s, s - 1, s - 1);

                int x = grid[i][j].i * s;
                int y = grid[i][j].j * s;


                try{
                    if(grid[i + 1][j].visited){
                        grid[i][j].walls[1] = false;
                    }
                    if(grid[i - 1][j].visited){
                        grid[i][j].walls[3] = false;
                    }
                    if(grid[i][j + 1].visited){
                        grid[i][j].walls[0] = false;
                    }
                    if(grid[i][j - 1].visited){
                        grid[i][j].walls[2] = false;
                    }
                }catch(Exception ignored){

                }
                if(grid[i][j].visited){
                    noStroke();
                    //stroke(40, 136, 215);
                    fill(40, 136, 215);
                    rect(x, y, s  , s );
                    stroke(0);
                }

            }
        }
    }

    public void mousePressed() {

        if (rectOver) {
            for (int j = 0; j < cols; j++) {
                for (int i = 0; i < rows; i++) {
                    grid[i][j].visited = false;
                    stroke(40, 136, 215);
                    fill(215, 119, 40);
                    strokeWeight(1);
                    stroke(0);
                    //noStroke();
                    rect(i * s, j * s, s - 1, s - 1);
                }
            }
        } else if (undoRectOver) {
            try {
                for (int i = 0; i < 5; i++) {
                    wantedMoves.get(wantedMoves.size() - 1).visited = !(wantedMoves.get(wantedMoves.size() - 1).visited);
                    wantedMoves.remove(wantedMoves.size() - 1);

                }
            } catch (Exception ignored) {

            }
        }

        try {
            if (mousePressed) {
                int mX = floor(mouseX / (s));
                int mY = floor(mouseY / (s));
                Tile spot = grid[mX][mY];
                instantChangeDeny.add(spot);
                wantedMoves.add(spot);
                spot.visited = !spot.visited;
            }
        } catch (Exception e) {
            print("\nMouse just kinda left tab\n");
            print(e.toString());
        }
    }

    public void mouseReleased() {
        instantChangeDeny.clear();
    }

    boolean overRect(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + height;
    }

    public void mouseDragged() {
        try {
            if (mousePressed) {
                if (!instantChangeDeny.contains(grid[floor(mouseX / (s))][floor(mouseY / (s))])) {
                    instantChangeDeny.add(grid[floor(mouseX / (s))][floor(mouseY / (s))]);
                    mousePressed();
                }
            }
        } catch (Exception e) {
            print("\nMouse just kinda left tab\n");
            print(e.toString());
        }
    }
}
