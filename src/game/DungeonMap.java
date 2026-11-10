/*
@ASSESSME.USERID: rl3805
@ASSESSME.AUTHOR: Roko Lovreček
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package game;

import entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Constants;

public class DungeonMap {

    private final TileType[][] map;
    private final boolean[][] discovered;
    private boolean exitUnlocked;

    public DungeonMap() {
        map = new TileType[Constants.MAP_ROWS][Constants.MAP_COLS];
        discovered = new boolean[Constants.MAP_ROWS][Constants.MAP_COLS];
        exitUnlocked = false;

        generateMap();
    }

    private void generateMap() {
        for (int row = 0; row < Constants.MAP_ROWS; row++) {
            for (int col = 0; col < Constants.MAP_COLS; col++) {

                if (row == 0 || row == Constants.MAP_ROWS - 1
                        || col == 0 || col == Constants.MAP_COLS - 1) {
                    map[row][col] = TileType.WALL;
                } else if ((row % 4 == 0) && (col > 2 && col < 15)) {
                    map[row][col] = TileType.WALL;
                } else {
                    map[row][col] = TileType.FLOOR;
                }
            }
        }

        map[13][18] = TileType.EXIT;

        map[2][2] = TileType.KEY;
        map[10][5] = TileType.KEY;
        map[7][16] = TileType.KEY;
    }

    public void draw(GraphicsContext gc) {
        for (int row = 0; row < Constants.MAP_ROWS; row++) {
            for (int col = 0; col < Constants.MAP_COLS; col++) {

                double x = col * Constants.TILE_SIZE;
                double y = row * Constants.TILE_SIZE;

                TileType tile = map[row][col];

                switch (tile) {
                    case WALL:
                        gc.setFill(Color.DARKSLATEGRAY);
                        break;

                    case EXIT:
                        gc.setFill(exitUnlocked ? Color.LIMEGREEN : Color.DARKRED);
                        break;

                    case KEY:
                        gc.setFill(Color.GOLD);
                        break;

                    default:
                        gc.setFill(Color.LIGHTGRAY);
                        break;
                }

                gc.fillRect(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);

                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);

                if (!discovered[row][col]) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
                }
            }
        }
    }

    public boolean isWall(double x, double y, double width, double height) {
        int leftTile = (int) x / Constants.TILE_SIZE;
        int rightTile = (int) (x + width - 1) / Constants.TILE_SIZE;
        int topTile = (int) y / Constants.TILE_SIZE;
        int bottomTile = (int) (y + height - 1) / Constants.TILE_SIZE;

        if (leftTile < 0 || rightTile >= Constants.MAP_COLS
                || topTile < 0 || bottomTile >= Constants.MAP_ROWS) {
            return true;
        }

        return map[topTile][leftTile] == TileType.WALL
                || map[topTile][rightTile] == TileType.WALL
                || map[bottomTile][leftTile] == TileType.WALL
                || map[bottomTile][rightTile] == TileType.WALL;
    }

    public void revealTilesAroundPlayer(Player player) {
        int playerCol = (int) player.getX() / Constants.TILE_SIZE;
        int playerRow = (int) player.getY() / Constants.TILE_SIZE;

        int radius = 2;

        for (int row = playerRow - radius; row <= playerRow + radius; row++) {
            for (int col = playerCol - radius; col <= playerCol + radius; col++) {

                if (row >= 0 && row < Constants.MAP_ROWS
                        && col >= 0 && col < Constants.MAP_COLS) {
                    discovered[row][col] = true;
                }
            }
        }
    }

    public boolean collectKeyAt(double x, double y) {
        int col = (int) x / Constants.TILE_SIZE;
        int row = (int) y / Constants.TILE_SIZE;

        if (row < 0 || row >= Constants.MAP_ROWS
                || col < 0 || col >= Constants.MAP_COLS) {
            return false;
        }

        if (map[row][col] == TileType.KEY) {
            map[row][col] = TileType.FLOOR;
            return true;
        }

        return false;
    }

    public boolean isExitAt(double x, double y) {
        int col = (int) x / Constants.TILE_SIZE;
        int row = (int) y / Constants.TILE_SIZE;

        if (row < 0 || row >= Constants.MAP_ROWS
                || col < 0 || col >= Constants.MAP_COLS) {
            return false;
        }

        return map[row][col] == TileType.EXIT;
    }

    public void unlockExit() {
        exitUnlocked = true;
    }

    public boolean isExitUnlocked() {
        return exitUnlocked;
    }

    public int getTotalKeys() {
        return Constants.STARTING_KEYS_REQUIRED;
    }
}