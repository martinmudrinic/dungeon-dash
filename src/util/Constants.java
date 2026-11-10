/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package util;


public final class Constants {

    private Constants(){

    }
    
    public static final int TILE_SIZE = 40;

    public static final int MAP_ROWS = 15;
    public static final int MAP_COLS = 20;

    public static final int CANVAS_WIDTH = MAP_COLS * TILE_SIZE;
    public static final int CANVAS_HEIGHT = MAP_ROWS * TILE_SIZE;

    public static final int WINDOW_WIDTH = CANVAS_WIDTH + 300;
    public static final int WINDOW_HEIGHT = CANVAS_HEIGHT;

    public static final int PLAYER_SIZE = 28;
    public static final int PLAYER_SPEED = 4;
    public static final int PLAYER_MAX_HEALTH = 100;

    public static final int GUARD_SIZE = 28;
    public static final int GUARD_SPEED = 2;
    public static final int GUARD_SIGHT_RADIUS = 160;

    public static final int TIGER_KING_HEALTH = 200;
    public static final int TIGER_KING_SIGHT_RADIUS = 240;

    public static final int STARTING_KEYS_REQUIRED = 3;

    public static final int SERVER_PORT = 5050;

    public static final int KEY_POINTS = 50;
    public static final int GUARD_STUN_POINTS = 25;
    public static final int TIGER_KING_POINTS = 200;
    public static final int REVIVE_POINTS = 100;
    public static final int EXIT_POINTS = 150;
}
